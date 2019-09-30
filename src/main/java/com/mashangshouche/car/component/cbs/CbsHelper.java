package com.mashangshouche.car.component.cbs;

import com.google.common.collect.Maps;

import com.chaboshi.builder.CBSBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashangshouche.car.common.JacksonUtils;
import com.mashangshouche.car.exception.CbsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CbsHelper {
    private final ObjectMapper objectMapper;
    private final CBSBuilder cbsBuilder;

    public CbsHelper(String userId, String keySecret, boolean online) {
        cbsBuilder = CBSBuilder.newCBSBuilder(userId, keySecret, false);
        objectMapper = new ObjectMapper();
    }

    /**
     * 开通城市
     */
    public List<OpenCityResp> opencity() {
        String result = cbsBuilder.sendGet("/insurance/opencity");
        TypeReference typeReference = new TypeReference<CbsPayload<List<OpenCityResp>>>() {
        };
        CbsPayload<List<OpenCityResp>> resp = JacksonUtils.readValue(result, typeReference);
        if (!"0".equals(resp.getCode())) {
            throw new CbsException(resp.getMessage());
        }
        return resp.getData();
    }

    /**
     * 创建检测订单
     * HashMap<String, Object> params = Maps.newHashMap();
     * params.put("name", "这是Name");
     * params.put("phone", "13212341234");
     * params.put("cityid", "35");
     * params.put("address", "这里是地址");
     * params.put("callbackurl", "http://www.baidu.com");
     * params.put("carno", "川A123456");
     * params.put("type", "101");
     * params.put("vin", "LFV2A2BS7G4012495");
     */
    public String insuranceOrder(HashMap<String, Object> params) {
        String result = cbsBuilder.sendPost("/insurance/order", params);
        TypeReference typeReference = new TypeReference<Map<String, String>>() {
        };
        Map<String, String> payload = JacksonUtils.readValue(result, typeReference);
        if (!"0".equals(payload.get("Code"))) {
            throw new CbsException(payload.get("Message"));
        }
        return payload.get("Orderno");
    }

    /**
     * 检测是否完成
     */
    public String insuranceOrderinfo(String orderno) {
        HashMap<String, Object> params = Maps.newHashMap();
        params.put("orderno", orderno);
        String result = cbsBuilder.sendPost("/insurance/orderinfo", params);
        //TypeReference typeReference = new TypeReference<Map<String, Object>>() {
        //};
        //Map<String, Object> payload = JacksonUtils.readValue(result, typeReference);
        //if("0".equals(payload.get("Code"))){
        //    return true;
        //}
        return result;
    }

    /**
     * 查询报告
     */
    public String getReportUrl(String orderno) {
        HashMap<String, Object> params = Maps.newHashMap();
        params.put("orderno", orderno);
        params.put("showvalue", 1);
        params.put("showvin", 1);
        return cbsBuilder.getReportUrl("/insurance/report", params);
    }


}
