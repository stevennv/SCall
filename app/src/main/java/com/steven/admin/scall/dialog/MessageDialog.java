package com.steven.admin.scall.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.steven.admin.scall.R;

/**
 * Created by Admin on 12/14/2017.
 */

public class MessageDialog extends Dialog {
    private TextView tvName;
    private EditText edtMessage;
    private TextView tvSend;
    private String phone;
    private String name;
    public MessageDialog(@NonNull Context context, String phone, String name) {
        super(context);
        this.phone = phone;
        this.name = name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        setContentView(R.layout.dialog_message);
        iniUI();
    }

    private void iniUI() {
        tvName = findViewById(R.id.tv_name);
        edtMessage = findViewById(R.id.edt_message);
        tvSend = findViewById(R.id.tv_send);
        tvName.setText(name);
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = edtMessage.getText().toString();
                sendSMS(phone, content);
                dismiss();
            }
        });
    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
