package com.sk.signet.onm.mapper.customer;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.sk.signet.onm.web.customer.domain.Customer;

@MapperScan
@SuppressWarnings("rawtypes")
public interface CustomerMapper {

    /**
     * 고객정보 리스트 조회
     */
    List<Map<String, Object>> selectCustomerList();
    
    /**
     * 고객정보 조회
     * @param param
     * @return
     */
    Customer selectCustomer(Map param);
    
    /**
     * 고객 결제카드 조회
     * @param map
     * @return
     */
    List<Map<String, Object>> selectCustomerPayCard(Map map);
    
    /**
     * 고객정보 수정
     * @param param
     */
    int updateCustomer(Map param);
}
