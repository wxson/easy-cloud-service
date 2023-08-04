package com.easy.cloud.web.service.upms.biz.social.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.service.upms.api.enums.SocialTypeEnum;
import com.easy.cloud.web.service.upms.biz.domain.UserDO;
import com.easy.cloud.web.service.upms.biz.social.ISocialService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Objects;

/**
 * 苹果
 *
 * @author GR
 * @date 2021-11-11 10:30
 */
@Slf4j
@Service
public class AppleSocialServiceImpl implements ISocialService {

    @Override
    public SocialTypeEnum getType() {
        return SocialTypeEnum.APPLE;
    }

    @Override
    public UserDO loadSocialUser(String code) {
        /*
         * https://juejin.cn/post/6844903914051993607
         * code格式：user_id&token
         */
        if (StrUtil.isBlank(code)) {
            throw new BusinessException("apple 授权登录码不能为空");
        }

        // 拆分
        String[] split = code.split("&");
        String appleId = split[0];
        String identityToken = split[1];

        if (identityToken.split("\\.").length > 1) {
            String firstDate = StrUtil.str(Base64.decodeBase64(identityToken.split("\\.")[0]), "UTF-8");
            String claim = StrUtil.str(Base64.decodeBase64(identityToken.split("\\.")[1]), "UTF-8");
            String kid = JSONUtil.parseObj(firstDate).getStr("kid");
            String aud = JSONUtil.parseObj(claim).getStr("aud");
            String sub = JSONUtil.parseObj(claim).getStr("sub");
            RSAPublicKey publicKey = getPublicKey(kid);
            if (publicKey == null) {
                throw new BusinessException("Apple have no info data!");
            }

            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("https://appleid.apple.com")
                    .withSubject(sub)
                    .withAudience(aud)
                    .build();
            DecodedJWT jwt = verifier.verify(identityToken);
        }

        return UserDO.builder().appleId(appleId).build();
    }

    /**
     * 获取公共Key
     *
     * @param kid 秘钥ID
     * @return java.security.RSAPublicKey
     */
    private RSAPublicKey getPublicKey(String kid) {
        try {
            String responseBody = HttpUtil.get("https://appleid.apple.com/auth/keys");
            JSONObject responseJsonObject = JSONUtil.parseObj(responseBody);
            if (Objects.isNull(responseJsonObject)) {
                throw new BusinessException("获取苹果keys失败");
            }
            String keys = responseJsonObject.getStr("keys");
            JSONArray keysArray = JSONUtil.parseArray(keys);
            if (keysArray.isEmpty()) {
                throw new BusinessException("获取苹果keys，解析keysArray失败");
            }
            for (Object object : keysArray) {
                JSONObject json = ((JSONObject) object);
                if (json.getStr("kid").equals(kid)) {
                    String n = json.getStr("n");
                    String e = json.getStr("e");
                    BigInteger modulus = new BigInteger(1, Base64.decodeBase64(n));
                    BigInteger publicExponent = new BigInteger(1, Base64.decodeBase64(e));
                    RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, publicExponent);
                    KeyFactory kf = KeyFactory.getInstance("RSA");
                    return (RSAPublicKey) kf.generatePublic(spec);
                }
            }
        } catch (Exception e) {
            log.error("getPublicKey异常：{}", e.getMessage());
        }
        return null;
    }
}
