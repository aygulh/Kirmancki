package com.xagnhay.kirmancki.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hidir on 23.02.2017.
 */

public class W1m implements Parcelable {
    private String letter;

    public W1m(String s1) {
        this.letter = s1;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public W1m() {
    }

    public W1m(Parcel in) {
        letter = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(letter);
    }

    public static final Parcelable.Creator<W1m> CREATOR =
            new Parcelable.Creator<W1m>() {

                @Override
                public W1m createFromParcel(Parcel source) {
                    return new W1m(source);
                }

                @Override
                public W1m[] newArray(int size) {
                    return new W1m[size];
                }
            };

}
