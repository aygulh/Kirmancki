package com.xagnhay.kirmancki.model;

/**
 * Created by hidir on 23.02.2017.
 */

public class Guide {

    private String letter;
    private String wordl1;
    private String wordl2;
    private String sentence1;
    private String sentence2;
    private String audio1;

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public void setWordl1(String wordl1) {
        this.wordl1 = wordl1;
    }

    public void setWordl2(String wordl2) {
        this.wordl2 = wordl2;
    }

    public void setSentence1(String sentence1) {
        this.sentence1 = sentence1;
    }

    public void setSentence2(String sentence2) {
        this.sentence2 = sentence2;
    }

    public void setAudio1(String audio1) {
        this.audio1 = audio1;
    }

    public Guide(String l1, String w1, String w2, String s1, String s2, String au1) {

        letter = l1;
        wordl1 = w1;
        wordl2 = w2;
        sentence1 = s1;
        sentence2 = s2;

        audio1 = au1;
    }

    public String getLetter() {
        return letter;
    }

    public String getWordl1() {
        return wordl1;
    }

    public String getWordl2() {
        return wordl2;
    }

    public String getSentence1() {
        return sentence1;
    }

    public String getSentence2() {
        return sentence2;
    }

    public String getAudio1() {
        return audio1;
    }

    public Guide() {

        letter = "";
        wordl1 = "";
        wordl2 = "";
        sentence1 = "";
        sentence2 = "";
        audio1 = "";
    }


}
