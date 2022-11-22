package com.sk.signet.onm.common.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CodeAspect {
    Jackson2JsonObjectMapper mapper = new Jackson2JsonObjectMapper();
    ObjectMapper objectMapper = new ObjectMapper();
    
    @Pointcut("execution(* *..*Controller.*(..))")
    public void commonPointcut() { }
    
    @Before("commonPointcut()")
    public void beforeMethod(JoinPoint jp) throws Exception {
//        log.debug("beforeMethod() called.....");

        // 호출될 메소드의 인자들으 얻을 수 있습니다.
        Object[] arg = jp.getArgs();
        for(int i=0; i<arg.length; i++) {
            if(arg[i] instanceof LinkedHashMap) {
                
                String json = new Gson().toJson(arg);
                log.debug("arg Json : {}", json);
                
                String xssStr = cleanXSS(json);
                log.debug("xssStr : {}", xssStr);
                
            } else if(arg[i] instanceof String) {
                log.debug("S Content[{}] : {}", i, arg[i]);
                
                String json = new Gson().toJson(arg[i]);
                log.debug("arg Json : {}", json); 
                
                String xssStr = cleanXSS(json);
                log.debug("xssStr : {}", xssStr);
            }
        }

        // 인자의 갯수 출력
//        log.debug("args length : " + arg.length);

        // 첫 번재 인자의 클래스 명 출력
//        log.debug("arg0 name : " + arg[0].getClass().getName());
        
        // 호출될 메소드 명 출력
        log.debug("CALL MethodName : {} - {}", jp.getSignature().getDeclaringTypeName(), jp.getSignature().getName());
        

    }
    
    // After Advice 입니다.
    @After("commonPointcut()")
    public void afterMethod(JoinPoint jp) throws Exception {
//        log.debug("afterMethod() called.....");
    }
    
    @AfterReturning(pointcut="commonPointcut()", returning="returnObj")
    public void around(JoinPoint joinPoint, ResponseEntity returnObj) {
//        log.debug("afterReturningMethod() called.....");
        
        Map<String, Object> data = (Map<String, Object>) returnObj.getBody();
        
        int status = returnObj.getStatusCodeValue();
        String retCode = null;
        
        if(status == 200) {
            retCode = "1000";
        } else if(status == 404){
            retCode = "4400";
        } else {
            retCode = "9999";
        }
        
        data.put("status", status);
        data.put("code", retCode);
        log.debug("returnBody : {}", returnObj.getBody());

    }
    
    private String cleanXSS(String value) {
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }
    

}
