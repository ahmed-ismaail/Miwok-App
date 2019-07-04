package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

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
        words.add(new Word("one", "lutti", R.mipmap.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.mipmap.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.mipmap.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.mipmap.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.mipmap.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.mipmap.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.mipmap.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.mipmap.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.mipmap.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'aacha", R.mipmap.number_ten, R.raw.number_ten));

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(new WordAdapter(NumbersActivity.this, words, R.color.category_numbers));

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Word wordd = words.get(position);
                releaseMediaPlayer();

                if(NumbersActivity.this.mAudioManager.requestAudioFocus
                        (mOnAudioFocusChangeListener
                         ,AudioManager.STREAM_MUSIC,
                         AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

//                    mAudioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);

                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, wordd.getAudioResourceId());
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
