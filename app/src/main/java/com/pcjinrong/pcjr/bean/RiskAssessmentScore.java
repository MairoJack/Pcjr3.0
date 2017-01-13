package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Mario on 2016/12/21.
 */

public class RiskAssessmentScore implements Serializable {
    @Expose
    public int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
