package com.easy.cloud.web.module.certification.biz.service.certification;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.module.certification.api.enums.CertificationClientEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 腾讯云认证
 * <p>文档：https://market.cloud.tencent.com/products/33468</p>
 *
 * @author GR
 * @date 2024/2/19 19:38
 */
@Slf4j
@Service
public class TencentICertificationClient implements ICertificationClient {

    @Autowired
    private CertificationProperties certificationProperties;

    @Override
    public CertificationClientEnum client() {
        return CertificationClientEnum.TENCENT;
    }

    @Override
    public HttpResult<Object> certification(String userName, String identityCard) {
        String source = "market";
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String datetime = sdf.format(cd.getTime());
        String failMessage = "";
        try {
            // 签名
            String auth = calcAuthorization(source, certificationProperties.getSecretId(), certificationProperties.getSecretKey(), datetime);

            // 请求头
            Map<String, String> headers = new HashMap<>();
            headers.put("X-Source", source);
            headers.put("X-Date", datetime);
            headers.put("Authorization", auth);

            // 查询参数
            Map<String, String> queryParams = new HashMap<>();

            // body参数
            Map<String, String> bodyParams = new HashMap<>();
            bodyParams.put("name", userName);
            bodyParams.put("idcard", identityCard);
            // url参数拼接
            String url = "https://service-ngxzc2ub-1310072863.sh.apigw.tencentcs.com/release/web/interface/grsfyz";
            if (!queryParams.isEmpty()) {
                url += "?" + urlEncode(queryParams);
            }

            HttpRequest httpRequest = HttpUtil.createPost(url);
            // 设置请求体
            httpRequest.body(JSONUtil.toJsonStr(bodyParams));
            // 设置请求头
            httpRequest.headerMap(headers, true);
            // 获取返回结果
            HttpResponse httpResponse = httpRequest.execute();
            // 解析返回结果
            JSONObject httpResponseJSON = JSONUtil.parseObj(httpResponse.body());
            // 读取状态码
            Object status = httpResponseJSON.get("status");
            if (Objects.nonNull(status) && "0".equals(status.toString())) {
                JSONObject dataJSON = httpResponseJSON.getJSONObject("data");
                if (Objects.nonNull(dataJSON)) {
                    Object result = dataJSON.get("result");
                    // 获取返回结果
                    if (Objects.nonNull(result) && "1".equals(result.toString())) {
                        return HttpResult.ok();
                    }
                }
            } else {
                failMessage = StrUtil.format("认证失败，所给信息不匹配：%s：%s", userName, identityCard);
            }
        } catch (Exception exception) {
            failMessage = StrUtil.format("实名认证失败：%s", exception.getMessage());
            log.error(failMessage);
        }
        return HttpResult.fail(failMessage);
    }

    /**
     * 计算认证
     *
     * @param source
     * @param secretId
     * @param secretKey
     * @param datetime
     * @return
     * @throws Exception
     */
    public static String calcAuthorization(String source, String secretId, String secretKey, String datetime) throws Exception {
        String signStr = "x-date: " + datetime + "\n" + "x-source: " + source;
        Mac mac = Mac.getInstance("HmacSHA1");
        Key sKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(sKey);
        byte[] hash = mac.doFinal(signStr.getBytes("UTF-8"));
        String sig = Base64.encode(hash);
        String auth = "hmac id=\"" + secretId + "\", algorithm=\"hmac-sha1\", headers=\"x-date x-source\", signature=\"" + sig + "\"";
        return auth;
    }

    /**
     * URL 编码
     *
     * @param map
     * @return
     * @throws Exception
     */
    public static String urlEncode(Map<?, ?> map) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    URLEncoder.encode(entry.getKey().toString(), "UTF-8"),
                    URLEncoder.encode(entry.getValue().toString(), "UTF-8")
            ));
        }
        return sb.toString();
    }
}
