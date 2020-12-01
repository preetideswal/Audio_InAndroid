package com.example.audiofile;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.*;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Button backb,nextb,playb,pauseb;
    MediaPlayer mediaPlayer;
    double startTime=0;
    double finalTime=0;
    Handler myHandler=new Handler();
    int forwordedTime=10000;
    int backwordedTime=10000;
    SeekBar seekBar;
    TextView startt,endt,namet;
    public static int oneTimeOnly=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backb=(Button)findViewById(R.id.back);
        playb=(Button)findViewById(R.id.play);
        nextb=(Button)findViewById(R.id.next);
        pauseb=(Button)findViewById(R.id.pause);
        startt=(TextView)findViewById(R.id.songtime);
        endt=(TextView)findViewById(R.id.totaltime);
        namet=(TextView)findViewById(R.id.songname);
        namet.setText("Song.mp3");
        mediaPlayer=MediaPlayer.create(this, R.raw.soch);
        seekBar=(SeekBar)findViewById(R.id.bar);
        seekBar.setClickable(false);
        pauseb.setEnabled(false);
        playb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"playing sound",Toast.LENGTH_SHORT).show();
                mediaPlayer.start();
                finalTime=mediaPlayer.getDuration();
                startTime=mediaPlayer.getCurrentPosition();
                if(oneTimeOnly==0){
                    seekBar.setMax((int)finalTime);
                    oneTimeOnly=2;
                }
                endt.setText(String.format("%d min,%d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long)finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long)finalTime)-
                           TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)finalTime)))
                );
                startt.setText(String.format("%d min,%d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long)startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long)startTime)-
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)startTime)))
                );
                seekBar.setProgress((int)startTime);
                myHandler.postDelayed(UpdateSongTime,100);
                playb.setEnabled(false);
                pauseb.setEnabled(true);
            }
        });
        nextb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp =(int)startTime;
                if((temp+forwordedTime)<=finalTime){
                    startTime=startTime+forwordedTime;
                    mediaPlayer.seekTo((int)startTime);
                    Toast.makeText(getApplicationContext(),"Jump forword 5 Second",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"can't jump",Toast.LENGTH_SHORT).show();
                }
            }
        });
        backb.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp =(int)startTime;
                if((temp-forwordedTime)>0){
                    startTime=startTime-backwordedTime;
                    mediaPlayer.seekTo((int)startTime);
                    Toast.makeText(getApplicationContext(),"Jump backword 5 Second",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"can't jump",Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }
    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            startt.setText(String.format("%d min,%d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
            );
            seekBar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };
}