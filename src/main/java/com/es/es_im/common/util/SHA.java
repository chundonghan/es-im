package com.es.es_im.common.util;

import java.security.MessageDigest;

public class SHA
{
    public static String sha(String str,String type) {
        try {
            MessageDigest md = MessageDigest.getInstance(type);
            md.update(str.getBytes());
            byte byteBuffer[] = md.digest();  
            
            StringBuffer strHexString = new StringBuffer();  
            // 遍歷 byte buffer  
            for (int i = 0; i < byteBuffer.length; i++)  
            {  
              String hex = Integer.toHexString(0xff & byteBuffer[i]);  
              if (hex.length() == 1)  
              {  
                strHexString.append('0');  
              }  
              strHexString.append(hex);  
            }  
            // 得到返回結果  
            str = strHexString.toString();  
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }
}
