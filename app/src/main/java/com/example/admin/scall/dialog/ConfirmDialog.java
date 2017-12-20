package com.example.admin.scall.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.scall.R;

/**
 * Created by Admin on 12/20/2017.
 */

public class ConfirmDialog extends Dialog {
    private String content;
    private TextView tvContent;
    private Button btnOk;
    private clickConfirm clickConfirm;

    public ConfirmDialog(@NonNull Context context, String content, clickConfirm clickConfirm) {
        super(context);
        this.content = content;
        this.clickConfirm = clickConfirm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);
        tvContent = findViewById(R.id.tv_content_dialog);
        btnOk = findViewById(R.id.btn_ok);
        tvContent.setText(content);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickConfirm.click();
            }
        });
    }

    public interface clickConfirm {
        void click();
    }
}
