package com.bluemoonl.gawebawebogame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Random;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageView hand_animation;
    AnimationDrawable animationDrawable;
    ImageView ImageView_set_hand;
    ImageButton ImageButton_gawe;
    ImageButton ImageButton_bawe;
    ImageButton ImageButton_bo;
    ImageButton ImageButton_replay;

    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hand_animation = findViewById(R.id.hand_animation);
        animationDrawable = (AnimationDrawable) hand_animation.getDrawable();

        ImageView_set_hand = findViewById(R.id.ImageView_set_hand);
        ImageButton_gawe = findViewById(R.id.ImageButton_gawe);
        ImageButton_bawe = findViewById(R.id.ImageButton_bawe);
        ImageButton_bo = findViewById(R.id.ImageButton_bo);
        ImageButton_replay = findViewById(R.id.ImageButton_replay);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.KOREAN);
                    textToSpeech.setPitch(1.0f);
                    textToSpeech.setSpeechRate(1.0f);
                }
            }
        });
    }

    public void btn_click(View view) {

        switch (view.getId()) {
            case R.id. ImageButton_replay :
                hand_animation.setVisibility(View.VISIBLE);
                ImageView_set_hand.setVisibility(View.GONE);
                animationDrawable.start();
                voicePlay("가위바위보");
                ImageButton_replay.setEnabled(false);
                ImageButton_gawe.setEnabled(true);
                ImageButton_bawe.setEnabled(true);
                ImageButton_bo.setEnabled(true);
                break;
            case R.id.ImageButton_gawe :
            case R.id.ImageButton_bawe :
            case R.id.ImageButton_bo :
                ImageButton_replay.setEnabled(true);
                ImageButton_gawe.setEnabled(false);
                ImageButton_bawe.setEnabled(false);
                ImageButton_bo.setEnabled(false);
                animationDrawable.stop();
                hand_animation.setVisibility(View.GONE);
                ImageView_set_hand.setVisibility(View.VISIBLE);

                int userHand =  Integer.parseInt(view.getTag().toString());
                int comHand = setHand();
                winCheck(userHand, comHand);
            default :
                break;
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
        textToSpeech.shutdown();
    }

    public void voicePlay(String voiceText)
    {
        textToSpeech.speak(voiceText, TextToSpeech.QUEUE_FLUSH, null, null);
    }
/*
    result=(3+you-com)%3;를 설명하자면...
    가위 = 1, 바위 = 2, 보 = 3 일때 (3+you-com)%3을 계산해서
    0이 나오면 비긴 것이고(예 가위(1) - 가위(1) = 0),
    1이 나오면 내가 이긴것이고(예 바위(2) - 가위(1) = 1),
    2가 나오면 내가 진 것입니다(예 보(3) - 가위(1) = 2).
*/
    public int setHand() {

        int getHand = new Random().nextInt(3) + 1;
        switch(getHand) {
            case 1:
                ImageView_set_hand.setImageResource(R.drawable.com_gawe);
                break;
            case 2:
                ImageView_set_hand.setImageResource(R.drawable.com_bawe);
                break;
            case 3:
                ImageView_set_hand.setImageResource(R.drawable.com_bo);
                break;
        }

        return getHand;
    }

    public void winCheck(int userHand, int comHand) {

        int result = (3 + userHand - comHand) % 3;

        switch (result) {
            case 0 : // Drew.
                voicePlay("비겼어요. 칙쇼.");
                break;
            case 1 : //Win.
                voicePlay("당신이 이겼어요. 한판 더 하시죠?.");
                break;
            case 2 : //Lose.
                voicePlay("제가 이겼어요. 쿠쿠루삥뽕.");
                break;

        }
    }
}
