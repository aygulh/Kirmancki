package com.xagnhay.kirmancki.model;

/**
 * Created by hidir on 09.02.2017.
 */

public class Quiz {
    private int id;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;

    public Quiz(String q, String a, String b, String c, String d, String ans) {

        question = q;
        option1 = a;
        option2 = b;
        option3 = c;
        option4 = d;
        answer = ans;
    }

    public Quiz() {
        id = 0;
        question = "";
        option1 = "";
        option2 = "";
        option3 = "";
        option4 = "";
        answer = "";
    }

   /* public int getId() {
        return id;
    }*/

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setId(int i) {
        id = i;
    }

    public void setQuestion(String q1) {
        question = q1;
    }

    public void setOption1(String o1) {
        option1 = o1;
    }

    public void setOption2(String o2) {
        option2 = o2;
    }

    public void setOption3(String o3) {
        option3 = o3;
    }

    public void setOption4(String o4) {
        option4 = o4;
    }

    public void setAnswer(String ans) {
        answer = ans;
    }

}
