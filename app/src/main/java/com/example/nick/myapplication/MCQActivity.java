package com.example.nick.myapplication;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MCQActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_question_counter)
    TextView tvQuestionCounter;
    @BindView(R.id.tv_timeRemaining)
    TextView tvTimeRemaining;
    @BindView(R.id.tv_timerValue)
    TextView tvTimerValue;
    @BindView(R.id.ll_mcq_question_time)
    LinearLayout llMcqQuestionTime;
    @BindView(R.id.tv_question_number)
    TextView tvQuestionNumber;
    @BindView(R.id.tv_question)
    TextView tvQuestion;
    @BindView(R.id.iv_question)
    ImageView ivQuestion;
    @BindView(R.id.tv_option_1)
    TextView tvOption1;
    @BindView(R.id.rl_option1_circle)
    RelativeLayout rlOption1Circle;
    @BindView(R.id.tv_option1_text)
    TextView tvOption1Text;
    @BindView(R.id.ll_option1_text)
    RelativeLayout llOption1Text;
    @BindView(R.id.iv_option1)
    ImageView ivOption1;
    @BindView(R.id.rl_option1_container)
    LinearLayout rlOption1Container;
    @BindView(R.id.tv_option_2)
    TextView tvOption2;
    @BindView(R.id.rl_option2_circle)
    RelativeLayout rlOption2Circle;
    @BindView(R.id.tv_option2_text)
    TextView tvOption2Text;
    @BindView(R.id.ll_option2_text)
    RelativeLayout llOption2Text;
    @BindView(R.id.iv_option2)
    ImageView ivOption2;
    @BindView(R.id.rl_option2_container)
    LinearLayout rlOption2Container;
    @BindView(R.id.tv_option_3)
    TextView tvOption3;
    @BindView(R.id.rl_option3_circle)
    RelativeLayout rlOption3Circle;
    @BindView(R.id.tv_option3_text)
    TextView tvOption3Text;
    @BindView(R.id.ll_option3_text)
    RelativeLayout llOption3Text;
    @BindView(R.id.iv_option3)
    ImageView ivOption3;
    @BindView(R.id.rl_option3_container)
    LinearLayout rlOption3Container;
    @BindView(R.id.tv_option_4)
    TextView tvOption4;
    @BindView(R.id.rl_option4_circle)
    RelativeLayout rlOption4Circle;
    @BindView(R.id.tv_option4_text)
    TextView tvOption4Text;
    @BindView(R.id.ll_option4_text)
    RelativeLayout llOption4Text;
    @BindView(R.id.iv_option4)
    ImageView ivOption4;
    @BindView(R.id.rl_option4_container)
    LinearLayout rlOption4Container;
    @BindView(R.id.iv_back_arrow)
    ImageView ivBackArrow;
    @BindView(R.id.tv_submit_btn)
    TextView tvSubmitBtn;
    @BindView(R.id.iv_forward_arrow)
    ImageView ivForwardArrow;
    @BindView(R.id.ll_bottom_btns)
    LinearLayout llBottomBtns;
    private CountDownTimer elapsedTime;
    private List<PreAptModel> questionsList;
    int count, attempted, score = 0;
    int selectedMCQ = 1;

    @BindColor(R.color.blue)
    int colorBlue;
    @BindColor(R.color.black)
    int colorBlack;
    @BindColor(R.color.white)
    int colorWhite;
    @BindColor(R.color.green1)
    int colorGreen;
    @BindColor(R.color.red)
    int colorRed;
    @BindColor(R.color.transparent_green)
    int colorGreenTransparent;
    @BindColor(R.color.transparent_red)
    int colorRedTransparent;
    @BindColor(R.color.light_grey)
    int colorGrey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq);
        ButterKnife.bind(this);
        startTimer(Integer.valueOf(getIntent().getStringExtra("mcqTimer")) * 60000);


        new DataBaseHelper(this);
        DataBaseHelper myDbHelper;
        myDbHelper = new DataBaseHelper(this);

        try {

            myDbHelper.createDataBase();
            myDbHelper.copyDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();
            questionsList = myDbHelper.getPurchasedProducts(Integer.parseInt(getIntent().getStringExtra("moduleNumber")));
            setUpQuestions(questionsList, count);
            Log.d("TAGAG", "onCreate: dbdata---->  " + questionsList);

        } catch (SQLException sqle) {

            throw sqle;

        }

        tvSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitClicked();

            }
        });

        ivForwardArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Integer.parseInt(questionsList.get(count).correct) == selectedMCQ) {

                    score++;

                }
                count++;
                setUpQuestions(questionsList, count);

            }
        });

        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (count > 0) {

                    score--;
                    count--;

                    setUpQuestions(questionsList, count);
                }


            }
        });
        rlOption1Container.setOnClickListener(this);
        rlOption2Container.setOnClickListener(this);
        rlOption3Container.setOnClickListener(this);
        rlOption4Container.setOnClickListener(this);

    }

    private void setUpQuestions(List<PreAptModel> questionsList, int count) {

        if (count >= 0 && count <= 1) {


            tvQuestion.setText(questionsList.get(count).question);
            tvOption1Text.setText(questionsList.get(count).mcq1);
            tvOption2Text.setText(questionsList.get(count).mcq2);
            tvOption3Text.setText(questionsList.get(count).mcq3);
            tvOption4Text.setText(questionsList.get(count).mcq4);

        } else {

            submitClicked();


        }

    }

    private void startTimer(int mcqTimer) {

        elapsedTime = new CountDownTimer((long) mcqTimer, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.d("elapsedTime", "onTick: " + DateUtils.formatElapsedTime(millisUntilFinished / 1000));
                ;
                tvTimerValue.setText(DateUtils.formatElapsedTime(millisUntilFinished / 1000));
                if (millisUntilFinished < 5000) {
                    tvTimeRemaining.setTextColor(ContextCompat.getColor(MCQActivity.this, R.color.red));
                    tvTimerValue.setTextColor(ContextCompat.getColor(MCQActivity.this, R.color.red));
                    animateTimerView(tvTimeRemaining, tvTimerValue);
                }
            }

            public void onFinish() {

                submitClicked();

            }
        };
        elapsedTime.start();


    }

    private void submitClicked() {
        elapsedTime.cancel();

        startActivity(new Intent(MCQActivity.this, MCQResults.class).putExtra("score",score));
        finish();

    }

    private void animateTimerView(View view1, View view2) {

        view1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.pulse));
        view2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.pulse));


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_option1_container:
                selectedMCQ= 1;
//                ((GradientDrawable) rlOption1Circle.getBackground()).setColor(colorBlue);
                tvOption1Text.setTextColor(colorBlue);
//                ((GradientDrawable) rlOption2Circle.getBackground()).setColor(colorBlack);
                tvOption2Text.setTextColor(colorBlack);
//                ((GradientDrawable) rlOption3Circle.getBackground()).setColor(colorBlack);
                tvOption3Text.setTextColor(colorBlack);
//                ((GradientDrawable) rlOption4Circle.getBackground()).setColor(colorBlack);
                tvOption4Text.setTextColor(colorBlack);
                break;
            case R.id.rl_option2_container:
                selectedMCQ= 2;

                tvOption1Text.setTextColor(colorBlack);

                tvOption2Text.setTextColor(colorBlue);

                tvOption3Text.setTextColor(colorBlack);

                tvOption4Text.setTextColor(colorBlack);
                break;
            case R.id.rl_option3_container:
                selectedMCQ= 3;

                tvOption1Text.setTextColor(colorBlack);

                tvOption2Text.setTextColor(colorBlack);

                tvOption3Text.setTextColor(colorBlue);

                tvOption4Text.setTextColor(colorBlack);
                break;
            case R.id.rl_option4_container:
                selectedMCQ= 4;

                tvOption1Text.setTextColor(colorBlack);

                tvOption2Text.setTextColor(colorBlack);

                tvOption3Text.setTextColor(colorBlack);

                tvOption4Text.setTextColor(colorBlue);
                break;
        }
    }
}
