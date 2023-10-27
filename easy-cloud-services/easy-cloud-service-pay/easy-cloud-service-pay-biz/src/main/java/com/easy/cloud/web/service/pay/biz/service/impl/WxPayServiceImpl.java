package com.easy.cloud.web.service.pay.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.easy.cloud.web.component.core.constants.GlobalCommonConstants;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.service.order.api.domain.vo.OrderVO;
import com.easy.cloud.web.service.order.api.enums.PayStatusEnum;
import com.easy.cloud.web.service.order.api.feign.OrderFeignClientService;
import com.easy.cloud.web.service.pay.biz.constants.PayConstants;
import com.easy.cloud.web.service.pay.biz.domain.PrePayBody;
import com.easy.cloud.web.service.pay.biz.domain.dto.BillDTO;
import com.easy.cloud.web.service.pay.biz.domain.dto.PayDTO;
import com.easy.cloud.web.service.pay.biz.domain.vo.BillVO;
import com.easy.cloud.web.service.pay.biz.domain.vo.PayVO;
import com.easy.cloud.web.service.pay.biz.service.IPayProxyService;
import com.easy.cloud.web.service.pay.biz.utils.MD5Util;
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
public class WxPayServiceImpl implements IPayProxyService {

    private final OrderFeignClientService orderFeignClientService;

    /**
     * 应用ID
     */
    private final String APP_ID = "wx73c43374df7eb819";
    /**
     * 商户号
     */
    private final String MCH_ID = "1618520046";

    @Override
    public PayVO pay(PayDTO payDTO) {
        log.info("接收到微信支付订单：{}", payDTO);
        // 获取订单详情
        OrderVO orderVO = orderFeignClientService.getOrderDetailByNo(payDTO.getOrderNo()).getData();
        // 构建预支付订单请求参数
        PrePayBody prePayBody = this.buildPreOrderRequestBody(orderVO);
        // 获取预支付订单信息
        String responseBody = this.postPreOrderInfo(prePayBody);
//        String responseBody = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><result_code><![CDATA[SUCCESS]]></result_code><mch_id><![CDATA[1618520046]]></mch_id><appid><![CDATA[wx73c43374df7eb819]]></appid><nonce_str><![CDATA[iOOCMglZ7fG8RoD0]]></nonce_str><sign><![CDATA[526307A3184B28125705BE5CC8CEAD73]]></sign><prepay_id><![CDATA[wx30110209439699a053877b75da45190000]]></prepay_id><trade_type><![CDATA[APP]]></trade_type></xml>";
        log.info("获取预支付订单：{}", responseBody);

        // 构建预支付订单返回数据
        return this.buildPreOrderResponseBody(payDTO, responseBody);
    }

    /**
     * 构建预支付订单返回数据
     *
     * @param payDTO       请求支付参数
     * @param responseBody 预支付信息
     * @return com.easy.cloud.web.service.pay.biz.domain.vo.PayVO
     */
    private PayVO buildPreOrderResponseBody(PayDTO payDTO, String responseBody) {
        // 预支付信息不能为空
        if (StrUtil.isBlank(responseBody)) {
            throw new BusinessException("获取微信支付预付订单异常");
        }
        JSONObject responseBodyJsonObject = JSONUtil.parseFromXml(responseBody);
        JSONObject resultJsonObject = responseBodyJsonObject.getJSONObject("xml");
        PayVO payVO = JSONUtil.toBean(resultJsonObject, PayVO.class);
        log.info("获取预支付订单ID：{}", payVO.getPrepayId());
        payVO.setAppId(APP_ID);
        payVO.setPackageValue("Sign=WXPay");
        payVO.setPartnerId(MCH_ID);
//        payVO.setTimeStamp(payDTO.getTimeStamp());
        payVO.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOpt("appid", payVO.getAppId());
        jsonObject.putOpt("noncestr", payVO.getNonceStr());
        jsonObject.putOpt("package", payVO.getPackageValue());
        jsonObject.putOpt("partnerid", payVO.getPartnerId());
        jsonObject.putOpt("prepayid", payVO.getPrepayId());
        jsonObject.putOpt("timestamp", payVO.getTimeStamp());
        // 构建Map对象
        Map<String, Object> payBeanMap = BeanUtil.beanToMap(jsonObject);
        log.info("待签名对象：{}", jsonObject);
        // 参数签名
        payVO.setSign(this.createSign(payBeanMap));
        payVO.setPayStatus(PayStatusEnum.PAY_NO);
        log.info("返回发起微信支付信息：{}", payVO);
        return payVO;
    }

    /**
     * 构建预支付订单请求参数
     *
     * @param orderVO 订单详情
     * @return com.easy.cloud.web.service.pay.biz.domain.PrePayBody
     */
    private PrePayBody buildPreOrderRequestBody(OrderVO orderVO) {
        // 构建预支付对象
        PrePayBody prePayBody = PrePayBody.build()
                .setAppid(APP_ID)
                .setMch_id(MCH_ID)
                .setBody(orderVO.getGoodsName())
                .setNonce_str(RandomUtil.randomString(18))
                .setNotify_url("https://www.huyouwlkj.com/pay/callback/handler")
                // 暂时移除订单号中的 “.” 字符
                .setOut_trade_no(orderVO.getNo())
                .setTrade_type("APP")
                .setTotal_fee(NumberUtil.mul(orderVO.getAmount(), 100).intValue());
        // 如果是DEBUG模式，支付0.01元
        if (PayConstants.DEBUG) {
            // 设置一分的支付
            prePayBody.setTotal_fee(GlobalCommonConstants.ONE);
        }
        // 构建Map对象
        Map<String, Object> beanToMap = BeanUtil.beanToMap(prePayBody);
        // 参数签名
        prePayBody.setSign(this.createSign(beanToMap));
        return prePayBody;
    }

    @Override
    public BillVO bill(BillDTO billDTO) {
        return null;
    }

    /**
     * 获取预付订单
     *
     * @param prePayBody 订单内容
     * @return java.lang.String
     */
    private String postPreOrderInfo(PrePayBody prePayBody) {
        // 预付订单API
        String preOrderApi = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String requestBody = JSONUtil.toXmlStr(JSONUtil.parse(prePayBody));
        StrBuilder builder = new StrBuilder();
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
        StrBuilder builder = StrBuilder.create();
        for (Map.Entry<String, Object> entry : beanToMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toList())) {
            if (Objects.nonNull(entry.getValue())) {
                builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        // 移除最后一位
        String md5Str = builder.append("key").append("=").append("chenhushishuaige19920705panmingu").toString().trim();
        log.info("md5Str= {}", md5Str);
        // MD5加密
        return MD5Util.MD5Encode(md5Str, CharsetUtil.UTF_8).toUpperCase();
    }
}
