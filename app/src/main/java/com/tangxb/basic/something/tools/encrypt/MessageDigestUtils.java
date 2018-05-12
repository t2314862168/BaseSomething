package com.tangxb.basic.something.tools.encrypt;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>信息摘要加密算法</p>
 * <p>
 * 信息摘要加密算法意为信息摘要算法，是一种不可逆的加密算法，
 * 主要包括了MD5、SHA1加密算法
 * 通过调用{@link #encrypt(String, String)}来实现加密
 * </p>
 *
 * @author 李熠
 * @version 0.1.3
 * @date 2014-7-8
 * @company 成都市映潮科技有限公司
 * @since 0.1.3
 */
public class MessageDigestUtils {


    /**
     * 对password进行加密
     *
     * @param password  要加密的明文
     * @param algorithm 加密算法名字，有MD5,SHA1
     * @return 加密后的密文
     * @throws Exception
     */
    public static String encrypt(String password, String algorithm) throws Exception {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] b = md.digest(password.getBytes());
        return ByteUtils.byte2HexString(b);
    }

    /**
     * @param data
     * @param token
     * @param timestamp
     * @return
     */
    public static String getSign(Map<String, String> data, String token, String timestamp) {

        Map<String,String> map = new TreeMap<String,String>();
        map.put("token", token);
        map.put("timestamp", timestamp);
        if(data!=null)map.putAll(data);

        List<Map.Entry<String, String>> paramList = new ArrayList<Map.Entry<String, String>>(map.entrySet());

        Collections.sort(paramList,new Comparator<Map.Entry<String,String>>() {
            //升序排序
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> item : paramList){
            if(!first){
                builder.append('&');
            }else{
                first = false;
            }
            builder.append(item.getKey()).append('=').append(item.getValue());
        }

        String sign = null;
        try {
            sign = MessageDigestUtils.encrypt(builder.toString(), Algorithm.SHA1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }

}
