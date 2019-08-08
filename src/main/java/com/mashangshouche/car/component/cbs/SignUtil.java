package com.mashangshouche.car.component.cbs;

import com.chaboshi.signUtil.Base64;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignUtil {
    public static final String ENCODING = "UTF-8";

    public SignUtil() {
    }

    public static String getSignature(String keySecret, String params) throws Exception {
        if (null != keySecret && !keySecret.isEmpty() && null != params && !params.isEmpty()) {
            Map<String, String[]> paramMap = new HashMap();
            String[] para = params.split("&");
            String[] var4 = para;
            int var5 = para.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String str = var4[var6];
                String[] paramm = str.split("=");
                if (paramm.length == 2) {
                    paramMap.put(paramm[0], paramm[1].split(","));
                }
            }

            return URLEncoder.encode(paramsMakeSignature(paramMap, keySecret), "UTF-8");
        } else {
            throw new Exception("keySecret and param must NOT (null or empty)");
        }
    }

    private static String paramsMakeSignature(Map<String, String[]> paramMap, String keySecert) throws Exception {
        if (null != paramMap && null != paramMap.get("userid") && paramMap.size() != 0) {
            String signature = validateRequestToSignStr(paramMap);
            SecretKeySpec signingKey = new SecretKeySpec(keySecert.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(signature.getBytes());
            return Base64.encode(rawHmac);
        } else {
            return null;
        }
    }

    private static String validateRequestToSignStr(Map<String, String[]> paramMap) throws Exception {
        if (null == paramMap) {
            return "";
        } else {
            Collection<String> keyset = paramMap.keySet();
            List<String> list = new ArrayList(keyset);
            Collections.sort(list);
            StringBuilder content = new StringBuilder();

            for(int i = 0; i < list.size(); ++i) {
                String split = i == 0 ? "" : "&";
                if ("userid".equalsIgnoreCase((String)list.get(i))) {
                    content.append(split).append("userid").append("=").append(((String[])paramMap.get(list.get(i)))[0]);
                } else if ("nonce".equalsIgnoreCase((String)list.get(i))) {
                    content.append(split).append("nonce").append("=").append(((String[])paramMap.get(list.get(i)))[0]);
                } else if ("timestamp".equalsIgnoreCase((String)list.get(i))) {
                    content.append(split).append("timestamp").append("=").append(((String[])paramMap.get(list.get(i)))[0]);
                } else if (!"signature".equalsIgnoreCase((String)list.get(i))) {
                    String[] values = (String[])paramMap.get(list.get(i));
                    content.append(split).append((String)list.get(i)).append("=");

                    for(int j = 0; j < values.length; ++j) {
                        String split1 = j == 0 ? "" : ",";
                        content.append(split1 + values[j]);
                    }
                }
            }

            return URLEncoder.encode(content.toString(), "UTF-8");
        }
    }
}
