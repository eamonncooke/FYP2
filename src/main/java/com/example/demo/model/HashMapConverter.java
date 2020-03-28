/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.logging.Level;
import javax.persistence.AttributeConverter;


/**
 *
 * @author cooke
 */
public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> customerInfo) {

        String customerInfoJson = null;

        try {
            customerInfoJson = objectMapper.writeValueAsString(customerInfo);
        } catch (JsonProcessingException ex) {
            java.util.logging.Logger.getLogger(HashMapConverter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return customerInfoJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String customerInfoJSON) {

        Map<String, Object> customerInfo = null;

        try {
            customerInfo = objectMapper.readValue(customerInfoJSON, Map.class);
        } catch (JsonProcessingException ex) {
            java.util.logging.Logger.getLogger(HashMapConverter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return customerInfo;
    }

}
