package com.zeba.base.utils;

import android.content.Context;

import java.lang.Thread.UncaughtExceptionHandler;

public class CrashedHandler implements UncaughtExceptionHandler {
	private UncaughtExceptionHandler defaultHandler;
	private static CrashedHandler instance = new CrashedHandler();
	private Context context;

	// 单例模式
	private CrashedHandler() {

	}

	public static CrashedHandler getInstance() {
		return instance;
	}

	/**
	 *
	 * @param context
	 */
	public void init(Context context,CrashedHandlerListener listener) {
		this.context = context;
		mListener=listener;
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 发生未捕获的异常后进入该函数处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// 如果没有处理则让系统默认异常处理器来处理
		if (!handleException(ex) && defaultHandler != null) {
			defaultHandler.uncaughtException(thread, ex);
		}
	}

	/**
	 * 处理异常
	 *
	 * @param ex
	 * @return
	 */
	private boolean handleException(Throwable ex) {
		if(mListener!=null){
			return mListener.handleException(ex);
		}
		return false;
	}

	public interface CrashedHandlerListener{
		boolean handleException(Throwable ex);
	}

	private CrashedHandlerListener mListener;

}
