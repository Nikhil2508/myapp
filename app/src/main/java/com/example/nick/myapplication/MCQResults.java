package com.example.nick.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MCQResults extends AppCompatActivity {

    @BindView(R.id.tv_goHome)
    TextView tvGoHome;
    @BindView(R.id.tv_score)
    TextView tvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcqresults);
        ButterKnife.bind(this);

        tvScore.setText("Your score is : " + getIntent().getIntExtra("score",0));

        tvGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MCQResults.this, HomeActivity.class));
                finish();
            }
        });
    }
}
