package com.xagnhay.kirmancki.model;

/**
 * Created by hidir on 23.02.2017.
 */

public class W1d {
    private String letter;
    private String wl1;
    private String wl2;
    private String s1;
    private String s2;
    private String audio;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getWl1() {
        return wl1;
    }

    public void setWl1(String wl1) {
        this.wl1 = wl1;
    }

    public String getWl2() {
        return wl2;
    }

    public void setWl2(String wl2) {
        this.wl2 = wl2;
    }

    public String getS1() {
        return s1;
    }

    public W1d(String l1, String w1, String w2, String sn1, String sn2, String au1) {

        letter = l1;
        wl1 = w1;
        wl2 = w2;
        s1 = sn1;
        s2 = sn2;

        audio = au1;
    }

    public W1d() {

        letter = "";
        wl1 = "";
        wl2 = "";
        s1 = "";
        s2 = "";
        audio = "";
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}
