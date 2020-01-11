package com.zeba.base.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ZebaAM {
	private List<BaseActivityIml> activityStack;
	private Map<String,List<BaseActivityIml>> tabMap;
	private static ZebaAM instance;

	private ZebaAM() {
	}

	public static ZebaAM get() {
		if (instance == null) {
			instance = new ZebaAM();
		}
		return instance;
	}

	// 退出栈顶Activity
	public void pop(BaseActivityIml activity) {
		if (activity != null) {
			removeTab(activity);
			getQueue().remove(activity);
		}
	}

	// 获得当前栈顶Activity
	public BaseActivityIml current() {
		if( getQueue().size()==0){
			return null;
		}
		BaseActivityIml activity = activityStack.get(activityStack.size()-1);
		return activity;
	}

	/**从最新的开始，获取第几个*/
	public BaseActivityIml getByTopIndex(int index){
		int qIndex=getQueue().size()-index;
		if( qIndex<=0){
			return null;
		}
		return activityStack.get(qIndex);
	}
	/**
	 * activity是否在第index层
	 * */
	public boolean inByTopIndex(BaseActivityIml activity,int index){
		if(getByTopIndex(index)==null){
			return false;
		}
		return true;
	}

	public List<BaseActivityIml> getQueue(){
		if (activityStack == null) {
			activityStack = new ArrayList<>();
		}
		return activityStack;
	}

	public Map<String,List<BaseActivityIml>> getTabMap(){
		if(tabMap==null){
			tabMap=new HashMap<>();
		}
		return tabMap;
	}

	// 将当前Activity推入栈中
	public void push(BaseActivityIml activity) {
		getQueue().add(activity);
	}

	public void popBackAll(){
		while(true){
			BaseActivityIml activity = current();
			if (activity == null) {
				break;
			}
			pop(activity);
			activity.back();
		}
	}

	public void addTab(BaseActivityIml activity,String tab){
		List<BaseActivityIml> list=getTabMap().get(tab);
		if(list==null){
			list=new ArrayList<>();
			getTabMap().put(tab,list);
		}
		list.add(activity);
	}

	public void removeTab(BaseActivityIml activity){
		Iterator<String> it= getTabMap().keySet().iterator();
		while(it.hasNext()){
			String key=it.next();
			List<BaseActivityIml> list=getTabMap().get(key);
			if(list!=null&&list.contains(activity)){
				list.remove(activity);
				if(list.size()==0){
					it.remove();
				}
			}
		}
	}

	public void popBackByTab(String tab){

		List<BaseActivityIml> list=getTabMap().get(tab);
		if(list==null){
			return;
		}
		for(int i=list.size()-1;i>=0;i--){
			BaseActivityIml activity=list.get(i);
			pop(activity);
			activity.back();
		}
		list.clear();
		getTabMap().remove(tab);
	}

	public void popBackToIndex(int index){
		List<BaseActivityIml> list=getQueue();
		for(int i=list.size()-1;i>index;i--){
			list.get(i).back();
		}
	}
}
