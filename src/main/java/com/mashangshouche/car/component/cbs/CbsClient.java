package com.mashangshouche.car.component.cbs;

import com.chaboshi.signUtil.SignUtil;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class CbsClient {
    private final String userId;
    private final String keySecret;

    public CbsClient(String userId,String keySecret){
        this.userId = userId;
        this.keySecret = keySecret;
    }

    private String sign(HashMap<String, Object> params) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("userid").append("=").append(this.userId);
        if (params != null) {
            Iterator var3 = params.keySet().iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                Object value = params.get(key);
                if (value != null && !StringUtils.isBlank(String.valueOf(value))) {
                    sb.append("&").append(key).append("=").append(value);
                }
            }
        }

        long timestamp = System.currentTimeMillis();
        String nonce = UUID.randomUUID().toString();
        sb.append("&").append("timestamp").append("=").append(timestamp);
        sb.append("&").append("nonce").append("=").append(nonce);
        String signature = SignUtil.getSignature(this.keySecret, sb.toString());
        sb.append("&").append("signature").append("=").append(signature);
        return sb.toString();
    }
}
