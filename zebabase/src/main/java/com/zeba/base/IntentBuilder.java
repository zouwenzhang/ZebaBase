package com.zeba.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

public class IntentBuilder {
    private static WeakHashMap<Activity, List<IntentBuilder>> resultMap=new WeakHashMap<>();
    private Intent intent=new Intent();
    private WeakReference<Context> refContext;
    private IntentCallBack callBack;
    private int requestCode;

    public static void onActivityResult(Activity activity,int requestCode,int resultCode,Intent data){
        if(resultMap.containsKey(activity)){
            List<IntentBuilder> list=resultMap.get(activity);
            for(IntentBuilder builder:list){
                if(builder.requestCode==requestCode){
                    builder.callBack.onResult(resultCode,data);
                    list.remove(builder);
                    builder.callBack=null;
                    builder.refContext.clear();
                    break;
                }
            }
        }
    }

    public static void onDestroy(Activity activity){
        if(resultMap.isEmpty()){
            return;
        }
        resultMap.remove(activity);
    }

    public IntentBuilder setClass(Context context, Class cls){
        intent.setClass(context,cls);
        this.refContext=new WeakReference<>(context);
        return this;
    }

    /**json数据格式:{"className":"activity类名","params":[{"name":"参数名",
     * "type":"参数类型:String,int,float,long,double","value":"参数值"}]}*/
    public IntentBuilder setIntentBody(Context context,String json){
        this.refContext=new WeakReference<>(context);
        try {
            JSONObject obj=new JSONObject(json);
            if(obj.has("page")){
                return setIntentParams(context,json);
            }
            intent.setClassName(context,obj.getString("pageName"));
            JSONArray params=obj.getJSONArray("params");
            for(int i=0;i<params.length();i++){
                JSONObject jo=params.getJSONObject(i);
                String type=jo.getString("type");
                if("int".equals(type)){
                    intent.putExtra(jo.getString("name"),jo.getInt("value"));
                }else if("float".equals(type)){
                    double d=jo.getDouble("value");
                    intent.putExtra(jo.getString("name"),(float)d);
                }else if("long".equals(type)){
                    intent.putExtra(jo.getString("name"),jo.getLong("value"));
                }else if("double".equals(type)){
                    intent.putExtra(jo.getString("name"),jo.getDouble("value"));
                }else{
                    intent.putExtra(jo.getString("name"),jo.getString("value"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public IntentBuilder setIntentParams(Context context,String json){
        this.refContext=new WeakReference<>(context);
        try {
            JSONObject obj=new JSONObject(json);
            intent.setClassName(context,obj.getString("page"));
            JSONObject jp= obj.getJSONObject("params");
            Iterator<String> ks= jp.keys();
            while(ks.hasNext()){
                String name=ks.next();
                intent.putExtra(name,jp.getString(name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public IntentBuilder setClass(Context context,String className){
        if(className.startsWith("{")){
            return setIntentBody(context,className);
        }
        className=className.replace("?",",");
        String[] cn=className.split(",");
        if(cn!=null&&cn.length!=0){
            intent.setClassName(context,cn[0]);
            this.refContext=new WeakReference<>(context);
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

    public IntentBuilder put(String key, Object value){
        intent.putExtra(key,String.valueOf(value));
        return this;
    }

    public IntentBuilder putObj(String key, Object value){
        intent.putExtra(key, MIntent.putObj(value));
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
        this.refContext=new WeakReference<>(context);
        return this;
    }

    public void activity(){
        try{
            if(intent.getComponent()==null||intent.getComponent().getClassName()==null){
                return;
            }
            this.refContext.get().startActivity(intent);
            this.refContext.clear();
            this.refContext=null;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void activity(int code, IntentCallBack callBack){
        try{
            if(intent.getComponent()==null||intent.getComponent().getClassName()==null){
                return;
            }
            if(!(this.refContext.get() instanceof Activity)){
                return;
            }
            Activity activity=(Activity) this.refContext.get();
            if(resultMap.containsKey(activity)){
                List<IntentBuilder> list=resultMap.get(activity);
                for(IntentBuilder ib:list){
                    if(ib.requestCode==code){
                        return;
                    }
                }
            }else{
                resultMap.put(activity,new ArrayList<IntentBuilder>());
            }
            this.callBack=callBack;
            requestCode=code;
            activity.startActivityForResult(intent,code);
            resultMap.get(activity).add(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface IntentCallBack{
        void onResult(int resultCode,Intent data);
    }
}
