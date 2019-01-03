package com.zeba.base.utils;

import java.util.Map;
import java.util.Set;

/**
 * 字符串方法类
 * Created by Administrator on 2017/11/2 0002.
 */

public class StringUtil {
    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 参数map转成string
     *
     * @param paraMap
     * @return
     */
    public static String paramsToString(Map<String, String> paraMap) {
        StringBuilder buf = new StringBuilder();
        if (paraMap != null && paraMap.size() > 0) {
            Set<String> keySet = paraMap.keySet();
            int i = 0;
            for (String key : keySet) {
                i++;
                buf.append(key).append("=").append(paraMap.get(key));
                if (i != keySet.size()) {
                    buf.append("&");
                }
            }
        }
        return buf.toString();
    }

    /***/
    public static String getStarString(String string){
        if(string==null||string.length()<=8){
            return string;
        }
        int end=string.lastIndexOf("@");
        if(end>=11){
            return string.substring(0,4)+"*****"+string.substring(end);
        }
        return string.substring(0,4)+"****"+string.substring(string.length()-4);
    }

    public static String getStarString(String string,int start,int end){
        if(string==null||string.length()==0){
            return string;
        }
        if(string.length()<=start+end){
            return string;
        }
        StringBuffer sb=new StringBuffer();
        sb.append(string.substring(0,start));
        int ss=string.length()-start-end;
        for(int i=0;i<ss;i++){
            sb.append("*");
        }
        sb.append(string.substring(start+ss));
        return sb.toString();
    }

    public static String getPhoneStarString(String string){
        if(string==null||string.length()!=11){
            return string;
        }
        return string.substring(0,3)+"****"+string.substring(string.length()-4);
    }
}
