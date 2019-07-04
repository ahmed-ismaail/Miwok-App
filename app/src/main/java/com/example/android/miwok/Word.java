package com.example.android.miwok;

public class Word {
    private static final int No_Image = -1;
    private String mMiwokTranslation;
    private int mAudioResourceId;
    private String mEnglishTranslation;
    private int mImageResourceId = No_Image;

    public Word(String EnglishTranslation, String MiwokTranslation, int audioResourceId) {
        this.mEnglishTranslation = EnglishTranslation;
        this.mMiwokTranslation = MiwokTranslation;
        this.mAudioResourceId = audioResourceId;
    }

    public Word(String EnglishTranslation, String MiwokTranslation, int ImageResourceId, int audioResourceId) {
        this.mEnglishTranslation = EnglishTranslation;
        this.mMiwokTranslation = MiwokTranslation;
        this.mImageResourceId = ImageResourceId;
        this.mAudioResourceId = audioResourceId;
    }

    public String getEnglishTranslation() {
        return this.mEnglishTranslation;
    }

    public String getMiwokTranslation() {
        return this.mMiwokTranslation;
    }

    public int getImageResourceId() {
        return this.mImageResourceId;
    }

    public int getAudioResourceId() {
        return this.mAudioResourceId;
    }

    public boolean hasImage() {
        return this.mImageResourceId != No_Image;
    }
}
