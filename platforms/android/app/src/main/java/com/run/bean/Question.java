package com.run.bean;

/**
 * Created by Xiao on 2018/3/27.
 */

public class Question {
    private int questionInnerId;
    private String questionName;
    private String questionKey;

    public int getQuestionInnerId() {
        return questionInnerId;
    }

    public void setQuestionInnerId(int questionInnerId) {
        this.questionInnerId = questionInnerId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
    }
}
