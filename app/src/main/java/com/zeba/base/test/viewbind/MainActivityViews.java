//ZebaViewBindClass
package com.zeba.base.test.viewbind;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import com.zeba.base.test.R;
public class MainActivityViews{
public Button btChoose;
public Button btTake;
public TextView tvText;
public MainActivityViews(Activity v){
btChoose=(Button)v.findViewById(R.id.bt_choose);
btTake=(Button)v.findViewById(R.id.bt_take);
tvText=(TextView)v.findViewById(R.id.tv_text);
}
}