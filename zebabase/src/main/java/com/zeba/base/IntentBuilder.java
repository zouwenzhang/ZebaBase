package com.zeba.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class IntentBuilder {
    private Intent intent=new Intent();
    private Context context;
    public IntentBuilder setClass(Context context, Class cls){
        intent.setClass(context,cls);
        this.context=context;
        return this;
    }

    /**json数据格式:{"className":"activity类名","params":[{"name":"参数名",
     * "type":"参数类型:String,int,float,long,double","value":"参数值"}]}*/
    public IntentBuilder setIntentBody(Context context,String json){
        this.context=context;
        try {
            JSONObject obj=new JSONObject(json);
            intent.setClassName(context,obj.getString("className"));
            JSONArray params=obj.getJSONArray("params");
            for(int i=0;i<params.length();i++){
                JSONObject jo=params.getJSONObject(i);
                String type=jo.getString("type");
                if("String".equals(type)){
                    intent.putExtra(jo.getString("name"),jo.getString("value"));
                }else if("int".equals(type)){
                    intent.putExtra(jo.getString("name"),jo.getInt("value"));
                }else if("float".equals(type)){
                    double d=jo.getDouble("value");
                    intent.putExtra(jo.getString("name"),(float)d);
                }else if("long".equals(type)){
                    intent.putExtra(jo.getString("name"),jo.getLong("value"));
                }else if("double".equals(type)){
                    intent.putExtra(jo.getString("name"),jo.getDouble("value"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public IntentBuilder setClass(Context context,String className){
        className=className.replace("?",",");
        String[] cn=className.split(",");
        if(cn!=null&&cn.length!=0){
            intent.setClassName(context,cn[0]);
            this.context=context;
            if(cn.length>=2){
                String[] cn2=cn[1].split("&");
                if(cn2!=null){
                    for(int i=0;i<cn2.length;i++){
                        if(cn2[i].length()==0){
                            continue;
                        }
                        String[] cn3=cn2[i].split("=");
                        if(cn3.length<2){
                            continue;
                        }
                        String[] cn4=cn3[0].split("_");
                        if(cn4.length<2){
                            intent.putExtra(cn3[0],cn3[1]);
                        }else{
                            try {
                                if("int".equals(cn4[0])){
                                    intent.putExtra(cn4[1],Integer.parseInt(cn3[1]));
                                }else if("long".equals(cn4[0])){
                                    intent.putExtra(cn4[1],Long.parseLong(cn3[1]));
                                }else{
                                    intent.putExtra(cn4[1],cn3[1]);
                                }
                            }catch (Exception e){

                            }
                        }
                    }
                }
            }
        }
        return this;
    }

    public IntentBuilder add(String key, Object value){
        if(value!=null){
            if(value instanceof Integer){
                intent.putExtra(key,(int)value);
            }else if(value instanceof String){
                intent.putExtra(key,(String)value);
            }else if(value instanceof Long){
                intent.putExtra(key,(long)value);
            }else if(value instanceof Boolean){
                intent.putExtra(key,(boolean)value);
            }else if(value instanceof Serializable){
                intent.putExtra(key,(Serializable)value);
            }else if(value instanceof Parcelable){
                intent.putExtra(key,(Parcelable)value);
            }
        }
        return this;
    }

    public IntentBuilder add(Bundle bundle){
        intent.putExtras(bundle);
        return this;
    }

    public Intent getIntent(){
        return intent;
    }

    public IntentBuilder context(Context context){
        this.context=context;
        return this;
    }

    public void activity(){
        try{
            if(intent.getComponent()==null||intent.getComponent().getClassName()==null){
                return;
            }
            context.startActivity(intent);
            context=null;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
