package com.examlpe.zf_android.util;

 
 

import com.example.zf_android.R;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogUtil {
    private Context context;
    private String text;
    private String telephone;
    private TextView dialog_tj_tv;
    private int aa;

    public interface CallBackChange {
        void change();
    }
    public DialogUtil(Context context, String text) {
    	  super();
        this.context = context;
        this.text = text;
    }

    public DialogUtil(Context context) {
        super();
        this.context = context;

    }
 


    public Dialog getCheck( final CallBackChange call) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        LinearLayout yes = (LinearLayout) v.findViewById(R.id.login_linear_yes);
        LinearLayout no = (LinearLayout) v.findViewById(R.id.login_linear_no);
       
        title.setText(text);
        
        final Dialog dialog = new Dialog(context, R.style.TanChuangDialog);
        dialog.setContentView(v);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);// ���Ͽ��Ե��dialog��Χ������ʧ
      //  window.setWindowAnimations(R.style.TanchuDialogAnim);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
     
                call.change();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }
}
