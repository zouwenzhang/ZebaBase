package com.zeba.base.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

public class SpannableUtils {
	SpannableString msp = null;
	public static SpannableString getForegroundColorSpan(String s,int color,int start,int end){
		SpannableString msp = new SpannableString(s);
		msp.setSpan(new ForegroundColorSpan(color),
				start, end, 
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return msp;
	}
	public static SpannableString getForegroundColorSpan(SpannableString msp,int color,int start,int end){
		msp.setSpan(new ForegroundColorSpan(color),
				start, end, 
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return msp;
	}

	public static SpannableString getForegroundColorSpan(String src,String[] r1,String[] r2,int[] colors){
		for(int i=0;i<r1.length;i++){
			src=src.replace(r1[i],r2[i]);
		}
		SpannableString msp = new SpannableString(src);
		for(int i=0;i<r2.length;i++){
			int start=src.indexOf(r2[i]);
			msp=getForegroundColorSpan(msp,colors[i],start,start+r2[i].length());
		}
		return msp;
	}


}
