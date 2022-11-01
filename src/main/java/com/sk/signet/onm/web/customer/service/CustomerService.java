package com.sk.signet.onm.web.customer.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.signet.onm.mapper.customer.CustomerMapper;
import com.sk.signet.onm.web.customer.domain.Customer;
import com.sk.signet.onm.web.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("customerService")
@SuppressWarnings({ "unchecked", "rawtypes", "unused" })    
public class CustomerService {

    @Autowired
    private CustomerMapper mapper;
    
    
    public List<Map<String, Object>> selectCustomerList() {
        List<Map<String, Object>> data = mapper.selectCustomerList();
        return data;
    }
    
    public Customer selectCustomer(Map<String, Object> searchInfo) {
        Customer customer = mapper.selectCustomer(searchInfo);
        return customer;
    }
    
       
    public Customer updateCustomer(Map<String, Object> customerInfo) {
        
        int result = mapper.updateCustomer(customerInfo);
        log.debug("update result : "+ result);
        Customer resultData = mapper.selectCustomer(customerInfo);     
        
        return resultData;
    }
}
