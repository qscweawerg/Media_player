package com.example.oaob.music_player;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{

    TextView locate_time;
    TextView end_time;
    Button play_button;
    SeekBar time_bar;
    SeekBar volume_bar;
    MediaPlayer mediaPlayer;
    int totaltime;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locate_time = (TextView)findViewById(R.id.locate_time);
        end_time = (TextView)findViewById(R.id.end_time);
        play_button = (Button)findViewById(R.id.play_button);
        time_bar = (SeekBar)findViewById(R.id.time_bar);
        volume_bar = (SeekBar)findViewById(R.id.volume_bar);

        mediaPlayer = MediaPlayer.create(this,R.raw.music);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        mediaPlayer.setVolume(0.5f,0.5f);

        totaltime = mediaPlayer.getDuration();
        locate_time.setText("00:00");
        end_time.setText(createTimeString(totaltime));
        play_button.setText("Play");
        time_bar.setMax(totaltime);
        volume_bar.setProgress(volume_bar.getMax() / 2);

        play_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    play_button.setText("Play");
                }
                else
                {
                    mediaPlayer.start();
                    play_button.setText("Stop");
                }
            }
        });

        time_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int locate, boolean b)
            {
                if(b)
                {
                    mediaPlayer.seekTo(locate);
                    time_bar.setProgress(locate);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        volume_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int locate, boolean b)
            {
                float volumeNum = locate / 100f;
                mediaPlayer.setVolume(volumeNum,volumeNum);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        @SuppressLint("HandlerLeak") final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                time_bar.setProgress(msg.what);
                locate_time.setText(createTimeString(msg.what));
                end_time.setText("- " + createTimeString(totaltime - msg.what));
            }
        };

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(mediaPlayer != null)
                {
                    try
                    {
                        Message msg = new Message();
                        msg.what = mediaPlayer.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException ignored)
                    {

                    }
                }
            }
        }).start();
    }

    public String createTimeString(int time)
    {
        String s = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;
        s += min + ":";
        s = sec < 10 ? s + "0" + sec : s + sec;
        return s;
    }
}
