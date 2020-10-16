package com.zeba.base;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MIntent {

    private static Map<String,Object> objMap=new HashMap<>();

    public static String get(Activity activity,String name){
        return activity.getIntent().getStringExtra(name);
    }

    public static long int8(Activity activity,String name){
        return int8(activity,name,0);
    }

    public static long int8(Activity activity,String name,long def){
        Object obj= activity.getIntent().getExtras().get(name);
        if(obj==null){
            return def;
        }
        if(obj instanceof Long){
            return (Long)obj;
        }
        if(obj instanceof Integer){
            return (Integer)obj;
        }
        try{
            return Long.parseLong(obj.toString());
        }catch (Exception e){
            e.printStackTrace();
            try{
                Double d= Double.parseDouble(obj.toString());
                return d.longValue();
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return def;
    }

    public static int int4(Activity activity,String name){
        return int4(activity,name,0);
    }

    public static int int4(Activity activity,String name,int def){
        Object obj= activity.getIntent().getExtras().get(name);
        if(obj==null){
            return def;
        }
        if(obj instanceof Long){
            return ((Long)(obj)).intValue();
        }
        if(obj instanceof Integer){
            return (Integer)obj;
        }
        try{
            return Integer.parseInt(obj.toString());
        }catch (Exception e){
            e.printStackTrace();
            try{
                Double d= Double.parseDouble(obj.toString());
                return d.intValue();
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return def;
    }

    public static float float4(Activity activity,String name){
        return float4(activity,name,0f);
    }

    public static float float4(Activity activity,String name,float def){
        Object obj= activity.getIntent().getExtras().get(name);
        if(obj==null){
            return def;
        }
        try{
            return Float.parseFloat(obj.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return def;
    }

    public static double float8(Activity activity,String name){
        return float8(activity,name,0d);
    }

    public static double float8(Activity activity,String name,double def){
        Object obj= activity.getIntent().getExtras().get(name);
        if(obj==null){
            return def;
        }
        try{
            return Double.parseDouble(obj.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return def;
    }

    public static String putObj(Object obj){
        String key= UUID.randomUUID().toString();
        objMap.put(key,obj);
        return key;
    }

    public static <T> T getObj(String name){
        return (T)objMap.get(name);
    }
}
