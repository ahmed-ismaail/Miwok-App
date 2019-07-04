package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    private AudioManager mAudioManager;

    private MediaPlayer mMediaPlayer;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener(){
                public void onAudioFocusChange(int focusChange) {

                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mMediaPlayer.start();
                    } else if(focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                }};

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("father", "әpә", R.mipmap.family_father,R.raw.family_father));
        words.add(new Word("mother", "әṭa", R.mipmap.family_mother,R.raw.family_mother));
        words.add(new Word("son", "angsi", R.mipmap.family_son,R.raw.family_son));
        words.add(new Word("daughter", "tune", R.mipmap.family_daughter,R.raw.family_daughter));
        words.add(new Word("older brother", "taachi", R.mipmap.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("youngerbrother","chalitti",R.mipmap.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("older sister", "teṭe", R.mipmap.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.mipmap.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("grandmother ", "ama", R.mipmap.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("grandfather", "paapa", R.mipmap.family_grandfather,R.raw.family_grandfather));

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(new WordAdapter(FamilyActivity.this, words, R.color.category_family));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Word wordd = words.get(position);
                releaseMediaPlayer();

                if(FamilyActivity.this.mAudioManager.requestAudioFocus
                        (mOnAudioFocusChangeListener
                                ,AudioManager.STREAM_MUSIC,
                                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

//                    mAudioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);

                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, wordd.getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}