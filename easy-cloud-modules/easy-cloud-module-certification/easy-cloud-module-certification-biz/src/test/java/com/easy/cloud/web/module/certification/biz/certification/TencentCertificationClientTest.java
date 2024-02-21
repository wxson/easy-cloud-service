package com.easy.cloud.web.module.certification.biz.certification;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * 腾讯云客户端实名认证测试
 *
 * @author GR
 * @date 2024/2/21 9:48
 */
@Slf4j
@SpringBootTest
public class TencentCertificationClientTest {

    private final String CHARSET = "UTF-8";

    public String sign(String s, String key, String method) throws Exception {
        Mac mac = Mac.getInstance(method);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(CHARSET), mac.getAlgorithm());
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(s.getBytes(CHARSET));
        return DatatypeConverter.printBase64Binary(hash);
    }

    public String getStringToSign(Map<String, Object> params) {
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
            url.append(k).append("=").append(URLEncoder.encode(params.get(k).toString(), CHARSET)).append("&");
        }
        return url.toString().substring(0, url.length() - 1);
    }


    @Test
    public void certification() throws Exception {
        String secretId = "secretId";
        String secretKey = "secretKey";

        Map<String, Object> params = new TreeMap<>(); // TreeMap可以自动排序
        params.put("Action", "PhoneVerification"); // 公共参数
        params.put("Version", "2018-03-01"); // 公共参数
        // 实际调用时应当使用随机数，例如：params.put("Nonce", new Random().nextInt(java.lang.Integer.MAX_VALUE));
        params.put("Nonce", RandomUtil.randomInt()); // 公共参数
        // 实际调用时应当使用系统当前时间，例如：   params.put("Timestamp", System.currentTimeMillis() / 1000);
        params.put("Timestamp", System.currentTimeMillis() / 1000); // 公共参数
        // 需要设置环境变量 TENCENTCLOUD_SECRET_ID，值为示例的 AKIDz8krbsJ5yKBZQpn74WFkmLPx3*******
        params.put("SecretId", secretId); // 公共参数
//        params.put("Region", "ap-guangzhou"); // 公共参数
        params.put("Name", "张三"); // 业务参数
        params.put("IdCard", "522501198901201621"); // 业务参数
        params.put("Phone", "18288888888"); // 业务参数
        // 需要设置环境变量 TENCENTCLOUD_SECRET_KEY，值为示例的 Gu5t9xGARNpq86cd98joQYCN3*******
        params.put("Signature", sign(getStringToSign(params), secretKey, "HmacSHA1")); // 公共参数
        String api = getUrl(params);
        String httpResponse = HttpUtil.get(api);
        log.info("read tencent certification：{}", httpResponse);
    }
}
