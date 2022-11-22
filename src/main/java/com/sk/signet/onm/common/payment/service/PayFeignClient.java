package com.sk.signet.onm.common.payment.service;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name="${feign.api.payment.name}", url="${feign.api.smatro.url}")
public interface PayFeignClient {

    @RequestMapping(method=RequestMethod.POST, value="/payment/smartro/")
    public Map<String, Object> userAuthCheck(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, Map<String, Object> reqBody);
}
