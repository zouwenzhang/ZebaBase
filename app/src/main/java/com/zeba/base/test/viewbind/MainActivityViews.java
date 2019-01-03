//ZebaViewBindClass
package com.zeba.base.test.viewbind;
import android.widget.TextView;
import android.app.Activity;
import com.zeba.base.test.R;
public class MainActivityViews{
public TextView tvText;
public TextView tvTitle;
public MainActivityViews(Activity v){
tvText=(TextView)v.findViewById(R.id.tv_text);
tvTitle=(TextView)v.findViewById(R.id.tv_title);
}
}