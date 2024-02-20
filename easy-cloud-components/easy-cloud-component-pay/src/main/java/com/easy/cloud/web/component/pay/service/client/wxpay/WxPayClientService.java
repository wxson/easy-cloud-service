package com.easy.cloud.web.component.pay.service.client.wxpay;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.pay.domain.PayRequestBody;
import com.easy.cloud.web.component.pay.domain.PayResponseBody;
import com.easy.cloud.web.component.pay.enums.PayStatusEnum;
import com.easy.cloud.web.component.pay.enums.PayTypeEnum;
import com.easy.cloud.web.component.pay.service.IPayClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 微信支付
 *
 * @author GR
 * @date 2021-11-12 14:41
 */
@Slf4j
@Service
@AllArgsConstructor
public class WxPayClientService implements IPayClientService {

    /**
     * 应用ID
     */
    private final String APP_ID = "xxx";
    /**
     * 商户号
     */
    private final String MCH_ID = "xxxx";
    /**
     * 秘钥
     */
    private final String SECRET_KEY = "xxxxx";

    /**
     * 服务地址
     */
    private final String SERVICE_HOST = "https://IP:PORT";

    @Override
    public PayTypeEnum type() {
        return PayTypeEnum.WX_PAY;
    }

    @Override
    public PayResponseBody pay(PayRequestBody payRequestBody) {
        log.info("接收到微信支付请求：{}", payRequestBody);
        // 构建预支付订单请求参数
        WxPrePayRequestBody wxPrePayRequestBody = this.generatePayOrderRequestBody(payRequestBody);
        log.info("生成微信预支付请求参数：{}", wxPrePayRequestBody);
        // 获取预支付订单信息
        String unifiedOrderBody = this.unifiedOrder(wxPrePayRequestBody);
//        String unifiedOrderBody = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><result_code><![CDATA[SUCCESS]]></result_code><mch_id><![CDATA[1618520046]]></mch_id><appid><![CDATA[wx73c43374df7eb819]]></appid><nonce_str><![CDATA[iOOCMglZ7fG8RoD0]]></nonce_str><sign><![CDATA[526307A3184B28125705BE5CC8CEAD73]]></sign><prepay_id><![CDATA[wx30110209439699a053877b75da45190000]]></prepay_id><trade_type><![CDATA[APP]]></trade_type></xml>";
        log.info("获取预支付订单：{}", unifiedOrderBody);

        // 构建预支付订单返回数据
        return this.generatePayResponseBody(unifiedOrderBody);
    }

    /**
     * 构建预支付订单返回数据
     *
     * @param responseBody 预支付信息
     * @return com.easy.cloud.web.service.pay.api.vo.PayVO
     */
    private PayResponseBody generatePayResponseBody(String responseBody) {
        // 预支付信息不能为空
        if (StrUtil.isBlank(responseBody)) {
            throw new BusinessException("获取微信支付预付订单异常");
        }
        // 解析预支付订单
        JSONObject responseBodyJsonObject = JSONUtil.parseFromXml(responseBody);
        // 获取预支付结果
        JSONObject resultJsonObject = responseBodyJsonObject.getJSONObject("xml");
        WxPrePayResponseBody wxPrePayResponseBody = JSONUtil.toBean(resultJsonObject, WxPrePayResponseBody.class);
        log.info("获取预支付订单ID：{}", wxPrePayResponseBody.getPrepayId());
        wxPrePayResponseBody.setAppId(APP_ID);
        wxPrePayResponseBody.setPackageValue("Sign=WXPay");
        wxPrePayResponseBody.setPartnerId(MCH_ID);
        wxPrePayResponseBody.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOpt("appid", wxPrePayResponseBody.getAppId());
        jsonObject.putOpt("noncestr", wxPrePayResponseBody.getNonceStr());
        jsonObject.putOpt("package", wxPrePayResponseBody.getPackageValue());
        jsonObject.putOpt("partnerid", wxPrePayResponseBody.getPartnerId());
        jsonObject.putOpt("prepayid", wxPrePayResponseBody.getPrepayId());
        jsonObject.putOpt("timestamp", wxPrePayResponseBody.getTimeStamp());
        // 构建Map对象
        Map<String, Object> payBeanMap = BeanUtil.beanToMap(jsonObject);
        log.info("待签名对象：{}", jsonObject);
        // 参数签名
        wxPrePayResponseBody.setSign(this.createSign(payBeanMap));
        wxPrePayResponseBody.setPayStatus(PayStatusEnum.PAY_NO);
        log.info("返回发起微信支付信息：{}", wxPrePayResponseBody);
        return wxPrePayResponseBody;
    }

    /**
     * 构建预支付订单请求参数
     *
     * @param payRequestBody 支付请求数据
     * @return com.easy.cloud.web.service.pay.api.PrePayBody
     */
    private WxPrePayRequestBody generatePayOrderRequestBody(PayRequestBody payRequestBody) {
        // 构建预支付对象
        WxPrePayRequestBody prePayRequestBody = WxPrePayRequestBody.build()
                .setAppid(APP_ID)
                .setMch_id(MCH_ID)
                .setBody(payRequestBody.getGoodsName())
                .setNonce_str(RandomUtil.randomString(18))
                .setNotify_url(SERVICE_HOST + "/pay/callback/handler")
                // 暂时移除订单号中的 “.” 字符
                .setOut_trade_no(payRequestBody.getOrderNo())
                .setTrade_type("APP")
                .setTotal_fee(NumberUtil.mul(payRequestBody.getAmount(), 100).intValue());
        // 构建Map对象
        Map<String, Object> beanToMap = BeanUtil.beanToMap(prePayRequestBody);
        // 参数签名
        prePayRequestBody.setSign(this.createSign(beanToMap));
        return prePayRequestBody;
    }

    /**
     * 获取预付订单
     *
     * @param wxPrePayRequestBody 订单内容
     * @return java.lang.String
     */
    private String unifiedOrder(WxPrePayRequestBody wxPrePayRequestBody) {
        // 预付订单API
        String preOrderApi = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String requestBody = JSONUtil.toXmlStr(JSONUtil.parse(wxPrePayRequestBody));
        StringBuilder builder = new StringBuilder();
        builder.append("<xml>").append(requestBody).append("</xml>");
        requestBody = builder.toString();
        log.info("获取预支付订单请求参数：{}", requestBody);
        // 获取预支付返回信息
        return HttpUtil.post(preOrderApi, requestBody);
    }

    /**
     * 获取签名
     *
     * @param beanToMap 支付内容
     * @return java.lang.String
     */
    private String createSign(Map<String, Object> beanToMap) {
        // 构建字符串载体
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : beanToMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()).collect(Collectors.toList())) {
            if (Objects.nonNull(entry.getValue())) {
                builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        // 移除最后一位
        String md5Str = builder.append("key").append("=").append(SECRET_KEY)
                .toString().trim();
        log.info("md5Str= {}", md5Str);
        // MD5加密
        return DigestUtil.md5Hex(md5Str).toUpperCase();
    }
}
