package com.xiarui.base.utlis.utlis;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MD5Util {


    /**
     * 32位MD5加密
     *
     * @param content -- 待加密内容
     * @return
     */
    public static String md5Decode32(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
        //对生成的16字节数组进行补零操作
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }

    public static String createSign(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<String, String>(params);
        // 遍历排序的字典,并拼接"key=value"格式
        for (Map.Entry<String, String> entry : sortParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().trim();
            if (!TextUtils.isEmpty(value))
                sb.append("&").append(key).append("=").append(value);
        }
        String stringA = sb.toString().replaceFirst("&", "").toUpperCase();
        String signValue = md5Decode32(stringA);
        return signValue;
    }



    public static String createSignMM(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<String, String>(params);
        // 遍历排序的字典,并拼接"key=value"格式
        for (Map.Entry<String, String> entry : sortParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().trim();
                sb.append("&").append(key).append("=").append(value);
        }
        String stringA = sb.toString().replaceFirst("&", "").toUpperCase();
        String signValue = md5Decode32("WEBSECRET=DC8517C8-6C87-4AB7-9EEE-CC7A386D8430&"+stringA);
        return signValue;
    }




    public static String createJySign(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<String, String>(params);
        // 遍历排序的字典,并拼接"key=value"格式
        for (Map.Entry<String, String> entry : sortParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().trim();
            sb.append(key).append(value);
        }
        String stringA = "jyxt" + sb.toString();
        String signValue = md5Decode32(stringA);
        return signValue;
    }


    public static String createJySignNew(Map<String, String> params) {
        String sb = new String();
        // 将参数以参数名的字典升序排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        // 遍历排序的字典,并拼接"key=value"格式
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            sb=sb+key+value;

        }
        String stringA = "jyxt" + sb;
//        String stringA="jyxtappid329454022timestamp20191214101010zwid5";
        String signValue = md5Decode32(stringA);

        return signValue;

    }
}