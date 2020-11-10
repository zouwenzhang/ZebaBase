package com.zeba.base.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonObj {

    private JSONObject jo;


    public JsonObj(String json) throws JSONException {
        jo=new JSONObject(json);
    }

    public int getInt(String path){
        return getInt(path,0);
    }

    public int getInt(String path,int def){
        try {
            String v= get(toPath(path),jo);
            return Integer.parseInt(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public long getLong(String path){
        return getLong(path,0);
    }

    public long getLong(String path,long def){
        try {
            String v= get(toPath(path),jo);
            return Long.parseLong(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public float getFloat(String path){
        return getFloat(path,0);
    }

    public float getFloat(String path,float def){
        try {
            String v= get(toPath(path),jo);
            return Float.parseFloat(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public double getDouble(String path){
        return getDouble(path,0);
    }

    public double getDouble(String path,double def){
        try {
            String v= get(toPath(path),jo);
            return Double.parseDouble(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public String get(String path){
        try {
            return get(toPath(path),jo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getObj(String path){
        try {
            return getObj(toPath(path),jo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean has(String path){
        try {
            return has(toPath(path),jo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String get(List<String> ps,JSONObject obj) throws JSONException {
        if(obj.has(ps.get(0))){
            String name=ps.get(0);
            if(ps.size()==1){
                return obj.getString(name);
            }
            ps.remove(0);
            return get(ps,obj.getJSONObject(name));
        }
        return null;
    }

    private JSONObject getObj(List<String> ps,JSONObject obj) throws JSONException {
        if(obj.has(ps.get(0))){
            String name=ps.get(0);
            if(ps.size()==1){
                return obj.getJSONObject(name);
            }
            ps.remove(0);
            return getObj(ps,obj.getJSONObject(name));
        }
        return null;
    }

    private boolean has(List<String> ps,JSONObject obj) throws JSONException {
        if(obj.has(ps.get(0))){
            String name=ps.get(0);
            if(ps.size()==1){
                return obj.has(name);
            }
            ps.remove(0);
            return has(ps,obj.getJSONObject(name));
        }
        return false;
    }

    private List<String> toPath(String path){
        String[] names=path.split("[.]");
        List<String> list=new ArrayList<>();
        for(String n:names){
            list.add(n);
        }
        return list;
    }
}
