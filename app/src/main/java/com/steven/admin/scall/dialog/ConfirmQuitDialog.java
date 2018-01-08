package com.steven.admin.scall.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.steven.admin.scall.R;

/**
 * Created by Admin on 11/23/2017.
 */

public class ConfirmQuitDialog extends Dialog {
    private String contentDialog;
    private TextView tvContent;
    private Button btnOk;
    private Button btnCancel;
    private String btn1;
    private String btn2;

    private clickBtn1 clickBtn1;
    private clickBtn2 clickBtn2;

    public ConfirmQuitDialog(@NonNull Context context, String contentDialog, String btn1, String btn2, clickBtn1 clickBtn1, clickBtn2 clickBtn2) {
        super(context);
        this.contentDialog = contentDialog;
        this.btn1 = btn1;
        this.btn2 = btn2;
        this.clickBtn1 = clickBtn1;
        this.clickBtn2 = clickBtn2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm_quit);
        iniUI();
    }

    private void iniUI() {
        tvContent = findViewById(R.id.tv_content_dialog);
        btnCancel = findViewById(R.id.btn_cancel);
        btnOk = findViewById(R.id.btn_ok);
        tvContent.setText(contentDialog);
        btnOk.setText(btn1);
        btnCancel.setText(btn2);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                clickBtn1.click();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                clickBtn2.click();
            }
        });
    }

    public interface clickBtn1 {
        void click();
    }

    public interface clickBtn2 {
        void click();
    }
}
