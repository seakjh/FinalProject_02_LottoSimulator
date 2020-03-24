package com.example.finalproject_02_lottosimulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.finalproject_02_lottosimulator.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity{

    List<TextView> winNumTxtList = new ArrayList<>();
    int[] winLottoNumArr = new int[6];
    int bonusNum = 0;

    int[] myLottoNumArr = {12, 15, 22, 27, 40, 43};
    long useMoneyAmount = 0;
    long winMoneyAmount = 0;

    int firstRankCount = 0;
    int secondRankCount = 0;
    int thirdRankCount = 0;
    int fourthRankCount = 0;
    int fifthRankCount = 0;
    int noRankCount = 0;

    ActivityMainBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.buyOneLottoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeWinLottoNum();

                checkLottoRank();
            }
        });

    }

    @Override
    public void setValues() {

        winNumTxtList.add(binding.winLottoNumTxt01);
        winNumTxtList.add(binding.winLottoNumTxt02);
        winNumTxtList.add(binding.winLottoNumTxt03);
        winNumTxtList.add(binding.winLottoNumTxt04);
        winNumTxtList.add(binding.winLottoNumTxt05);
        winNumTxtList.add(binding.winLottoNumTxt06);

    }

    void makeWinLottoNum() {
        for (int i = 0; i < winLottoNumArr.length; i++) {
            winLottoNumArr[i] = 0;
        }
        bonusNum = 0;

        for (int i = 0; i < winLottoNumArr.length; i++) {

            while (true) {

                int randomNum = (int) (Math.random()*45 + 1);

                boolean isDuplOk = true;

                for (int winNum : winLottoNumArr) {

                    if (winNum == randomNum) {
                        isDuplOk = false;
                        break;
                    }

                }

                if (isDuplOk) {
                    winLottoNumArr[i] = randomNum;
                    Log.i("당첨번호", randomNum+"");
                    break; // 무한반복 탈출
                }

            }

        }

//        6개의 당첨번호를
        Arrays.sort(winLottoNumArr);

        for (int i = 0; i < winLottoNumArr.length; i++ ) {
            winNumTxtList.get(i).setText(winLottoNumArr[i]+"");
        }

        while (true) {
            int randomNum = (int) (Math.random()*45+1);

            boolean isDuplOk = true;
            for (int winNum : winLottoNumArr) {
                if (winNum == randomNum) {
                    isDuplOk = false;
                    break;
                }
            }

            if (isDuplOk) {
                bonusNum = randomNum;
                break;
            }
        }

        binding.bonusNumTxt.setText(bonusNum+"");



    }

    void checkLottoRank() {

        useMoneyAmount += 1000;

        binding.useMoneyTxt.setText(String.format("사용 금액 : %,d원", useMoneyAmount));

        int correctCount = 0;

        for (int myNum : myLottoNumArr) {
            for (int winNum : winLottoNumArr) {

                if (myNum == winNum) {
                    correctCount++;
                }

            }
        }

        if (correctCount == 6) {
//            1등
            winMoneyAmount += 1600000000;
        }
        else if (correctCount == 5) {
//            2등 / 3등 재검사 필요 => 보너스 번호가 맞췄는지 확인
//            내 번호중에 보너스 번호와 같은게 있나?
            boolean hasBonusNum = false;

            for (int mynum : myLottoNumArr){
                if (mynum == bonusNum) {
                    hasBonusNum = true;
                    break;
                }
            }
            if (hasBonusNum){
//                2등
                winMoneyAmount = 75000000;
            }
            else {
//                3등
                winMoneyAmount = 1500000;
            }

        }
        else if (correctCount == 4) {
//            4등
            winMoneyAmount += 50000;
        }
        else  if (correctCount == 3) {
//            5등
            useMoneyAmount -= 5000;
        }
        else  {
//            꽝
        }

        binding.winMoneyTxt.setText(String.format("당첨 금액 : %,d원", winMoneyAmount));

    }
}
