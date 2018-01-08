package com.steven.admin.scall.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.steven.admin.scall.R;
import com.steven.admin.scall.model.InfoStyle;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtSearch;
    private RecyclerView rvData;
    private RecyclerView.LayoutManager layoutManager;
    private InfoStyle infoStyle;
    private Button btnAdd;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        iniUI();
    }

    private void iniUI() {
//        edtSearch = (EditText) findViewById(R.id.edt_search);
//        btnAdd = (Button) findViewById(R.id.btn_add);
//        rvData = (RecyclerView) findViewById(R.id.rv_data);
//        layoutManager = new LinearLayoutManager(this);
//        rvData.setLayoutManager(layoutManager);
//        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
