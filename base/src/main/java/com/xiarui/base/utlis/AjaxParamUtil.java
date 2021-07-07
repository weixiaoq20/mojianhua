package com.xiarui.base.utlis;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AjaxParamUtil {
    /**
     * 对所有ajax请求进行最后的封装，加上相应的签名等数据
     * 传入的参数一定是服务器需要的参数
     * 以及一个fields(参数1,参数2,参数3,参数4)
     *
     * @param param
     * @return
     */
    public static Map<String, String> getSuitParam(Map<String, String> param) {
        Map<String, String> reMap = new HashMap<String, String>();
        StringBuilder sbf = new StringBuilder();
        long currentTimestamp = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String timeStamp = sdf.format(new Date(currentTimestamp));
        reMap.put("timestamp", timeStamp);
        reMap.put("format", "json");
        reMap.put("app_secret", URLConst.SERVER_app_secret);
        reMap.put("v", "1.0");
        reMap.putAll(param);
        reMap.put("sign_method", "md5");
        reMap.put("app_key", URLConst.SERVER_app_key);
        StringBuilder reBf = new StringBuilder(sbf);
        List<Map.Entry<String, String>> infoIds = new ArrayList<>(reMap.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return (o1.getKey()).compareTo(o2.getKey());
            }
        });
        reBf.append(URLConst.SERVER_app_secret);
        for (int i = 0; i < infoIds.size(); i++) {
            if (!infoIds.get(i).getKey().equals("app_secret")) {
                reBf.append(infoIds.get(i).getKey());
                reBf.append(infoIds.get(i).getValue());
            }
        }
        reBf.append(URLConst.SERVER_app_secret);
        String strSig = encryption("1", reBf.toString());
        reMap.put("sign", strSig);
        return reMap;
    }

    public static String encryption(String type, String plainText) {
        String re_md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte aB : b) {
                i = aB;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
            if ("0".equals(type)) {
                return re_md5.toLowerCase();
            } else {
                return re_md5.toUpperCase();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }
}
