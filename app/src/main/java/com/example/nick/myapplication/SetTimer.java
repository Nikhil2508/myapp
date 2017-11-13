package com.example.nick.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetTimer extends AppCompatActivity {

    @BindView(R.id.tv_timerText)
    TextView tvTimerText;
    @BindView(R.id.et_timer)
    EditText etTimer;
    @BindView(R.id.bt_setTime)
    Button btSetTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timer);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_setTime)
    public void onViewClicked() {

        startActivity(new Intent(SetTimer.this,MCQActivity.class).putExtra("mcqTimer",etTimer.getText().toString()).putExtra("moduleNumber",getIntent().getStringExtra("moduleNumber")));
        finish();

    }
}
