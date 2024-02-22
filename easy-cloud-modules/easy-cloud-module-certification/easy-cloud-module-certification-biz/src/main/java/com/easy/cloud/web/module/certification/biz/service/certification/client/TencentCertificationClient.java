package com.easy.cloud.web.module.certification.biz.service.certification.client;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.certification.api.enums.CertificationClientEnum;
import com.easy.cloud.web.module.certification.biz.service.certification.CertificationProperties;
import com.easy.cloud.web.module.certification.biz.service.certification.ICertificationClient;
import com.easy.cloud.web.module.certification.biz.service.certification.entity.CertificationBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 腾讯云认证
 * <p>文档：https://cloud.tencent.com/document/product/1007/39765</p>
 *
 * @author GR
 * @date 2024/2/19 19:38
 */
@Slf4j
@Service
public class TencentCertificationClient implements ICertificationClient {

    @Autowired
    private CertificationProperties certificationProperties;

    @Override
    public CertificationClientEnum client() {
        return CertificationClientEnum.TENCENT;
    }

    /**
     * 签名
     *
     * @param signParamStr 签名参数
     * @param secretKey    应用秘钥
     * @param signMethod   签名方式
     * @return
     * @throws Exception
     */
    public String sign(String signParamStr, String secretKey, String signMethod) throws Exception {
        Mac mac = Mac.getInstance(signMethod);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(CharsetUtil.UTF_8), mac.getAlgorithm());
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(signParamStr.getBytes(CharsetUtil.UTF_8));
        return DatatypeConverter.printBase64Binary(hash);
    }

    /**
     * 构建签名参数字符串
     *
     * @param params 签名参数集合
     * @return
     */
    public String getSignParamStr(Map<String, Object> params) {
        StringBuilder s2s = new StringBuilder("GETfaceid.tencentcloudapi.com/?");
        // 签名时要求对参数进行字典排序，此处用TreeMap保证顺序
        for (String k : params.keySet()) {
            s2s.append(k).append("=").append(params.get(k).toString()).append("&");
        }
        return s2s.toString().substring(0, s2s.length() - 1);
    }

    public String getUrl(Map<String, Object> params) throws UnsupportedEncodingException {
        StringBuilder url = new StringBuilder("https://faceid.tencentcloudapi.com/?");
        // 实际请求的url中对参数顺序没有要求
        for (String k : params.keySet()) {
            // 需要对请求串进行urlencode，由于key都是英文字母，故此处仅对其value进行urlencode
            url.append(k).append("=").append(URLEncoder.encode(params.get(k).toString(), CharsetUtil.UTF_8)).append("&");
        }
        return url.toString().substring(0, url.length() - 1);
    }


    @Override
    public HttpResult<Object> certification(CertificationBody certificationBody) {
        String failMessage = "";
        try {
            // TreeMap可以自动排序
            Map<String, Object> params = new TreeMap<>();
            // 公共参数：Action
            params.put("Action", "PhoneVerification");
            // 公共参数：Version
            params.put("Version", "2018-03-01");
            // 公共参数：实际调用时应当使用随机数，例如：params.put("Nonce", new Random().nextInt(java.lang.Integer.MAX_VALUE));
            params.put("Nonce", RandomUtil.randomInt());
            // 公共参数：实际调用时应当使用系统当前时间，例如：   params.put("Timestamp", System.currentTimeMillis() / 1000);
            params.put("Timestamp", System.currentTimeMillis() / 1000);
            // 公共参数：需要设置环境变量 TENCENTCLOUD_SECRET_ID，值为示例的 AKIDz8krbsJ5yKBZQpn74WFkmLPx3*******
            params.put("SecretId", certificationProperties.getSecretId());
            // 业务参数：姓名
            params.put("Name", certificationBody.getUserName());
            // 业务参数：身份证
            params.put("IdCard", certificationBody.getIdCard());
            // 业务参数：电话
            params.put("Phone", certificationBody.getTel());
            // 公共参数：Signature
            params.put("Signature", sign(getSignParamStr(params), certificationProperties.getSecretKey(), "HmacSHA1"));
            String api = getUrl(params);
            String httpResponse = HttpUtil.get(api);
            // 解析返回结果：{"Response":{"Description":"认证通过","Isp":"移动","RequestId":"08923eb3-f774-426d-88b9-587e360ae43f","Result":"0"}}
            JSONObject httpResponseJSON = JSONUtil.parseObj(httpResponse);
            // 读取状态码
            JSONObject responseJSON = httpResponseJSON.getJSONObject("Response");
            if (Objects.nonNull(responseJSON)) {
                String result = responseJSON.getStr("Result");
                // 获取返回结果
                if (StrUtil.isNotBlank(result) && "0".equals(result)) {
                    return HttpResult.ok();
                } else {
                    failMessage = StrUtil.format("认证失败：{}", responseJSON.getStr("Description"));
                }
            } else {
                failMessage = StrUtil.format("认证失败，所给信息不匹配：{}:{}:{}", certificationBody.getUserName(),
                        certificationBody.getIdCard(), certificationBody.getTel());
            }
        } catch (Exception exception) {
            failMessage = StrUtil.format("实名认证失败：{}", exception.getMessage());
            log.error(failMessage);
        }
        return HttpResult.fail(failMessage);
    }
}
