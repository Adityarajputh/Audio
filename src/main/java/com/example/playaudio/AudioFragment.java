package com.example.playaudio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.playaudio.databinding.FragmentAudioBinding;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class AudioFragment extends Fragment implements PlayerAdapter.OnItemClickListener {

    private RecyclerView.LayoutManager layoutManager;
    private FragmentAudioBinding binding;
    private RecyclerView recyclerView;
    private PlayerAdapter adapter;
    MediaPlayer mediaPlayer;
//    Handler handler = new Handler();
//    Runnable runnable;
    boolean isPlaying = false;
    boolean isTimerStarted = true;
    boolean isFirstClick = true;
    int previousPosition;
    CountDownTimer cTimer = null;
    private long milliLeft;
    private long min;
    private long sec;
    int count = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private void init(Context context) {
        //passing this fragment as OnItemClickListener to the adapter

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAudioBinding.inflate(getLayoutInflater(), container, false);

        ArrayList<ItemDataClass> myItemData = new ArrayList<>();
        myItemData.add(new ItemDataClass("01:30", R.drawable.ic_baseline_play_arrow_24, R.id.seekBar));
        myItemData.add(new ItemDataClass("01:30", R.drawable.ic_baseline_play_arrow_24, R.id.seekBar));
        myItemData.add(new ItemDataClass("01:30", R.drawable.ic_baseline_play_arrow_24, R.id.seekBar));
        myItemData.add(new ItemDataClass("01:30", R.drawable.ic_baseline_play_arrow_24, R.id.seekBar));
        myItemData.add(new ItemDataClass("01:30", R.drawable.ic_baseline_play_arrow_24, R.id.seekBar));
        myItemData.add(new ItemDataClass("01:30", R.drawable.ic_baseline_play_arrow_24, R.id.seekBar));

        recyclerView = binding.playerRecycler;
        adapter = new PlayerAdapter(requireActivity(), this, myItemData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);

        mediaPlayer = MediaPlayer.create(requireActivity(), R.raw.attack_on_titan);


        

//
////        int duration = mediaPlayer.getDuration();
////        String sDuration = convertFormat(duration);
//        binding.tvDuration.setText(convertFormat(mediaPlayer.getCurrentPosition()));
//
//
//
//        binding.playImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(!flag){
//                    binding.playImg.setImageResource(R.drawable.ic_baseline_pause_24);
//                    //start media player
//
//                    binding.seekBar.setMax(mediaPlayer.getDuration());
//                    //call the runnable function every 0.1 sec to update the seekbar position
//                    handler.postDelayed(runnable,500);
//                }else{
//                    pauseMediaPlayer();
////                    handler.postDelayed(runnable,100);
//                    //stop the handler from updating the seekbar position when the audio is paused
////                    handler.removeCallbacks(runnable);
//                }
//                flag=!flag;
//                Toast.makeText(requireActivity(), "Button Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if(fromUser){
//                    mediaPlayer.seekTo(progress);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mediaPlayer.seekTo(0);
//            }
//        });

        return binding.getRoot();
    }

    @SuppressLint("DefaultLocale")
    private String convertFormat(int duration) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

    public void timerStart(long timeLengthMilli,TextView timer,SeekBar playerSeek,ImageView img) {
//        if(mediaPlayer.isPlaying()) {
            cTimer = new CountDownTimer(timeLengthMilli, 1000) {

                @Override
                public void onTick(long milliTillFinish) {
                    milliLeft = milliTillFinish;
                    min = (milliTillFinish / (1000 * 60));
                    sec = ((milliTillFinish / 1000) - min * 60);
                    String countDown = String.format("%02d:%02d", min, sec);
                    timer.setText(countDown);
                    Log.i("Tick", "Tock");
                    playerSeek.setProgress(mediaPlayer.getCurrentPosition());
                }


                @SuppressLint("SetTextI18n")
                @Override
                public void onFinish() {
                    min = (mediaPlayer.getDuration() / (1000 * 60));
                    sec = ((mediaPlayer.getDuration() / 1000) - min * 60);
                    String timerReset = String.format("%02d:%02d",min,sec);
                    timer.setText(timerReset);
                    img.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    playerSeek.setProgress(0);
                    playerSeek.setEnabled(false);
                    isPlaying = false;
                }
            };
            cTimer.start();
//        }else{
//
//        }

    }

    public void timerPause() {
        cTimer.cancel();
    }

    private void timerResume(TextView timer,SeekBar playerSeek,ImageView img) {
        Log.i("min", Long.toString(min));
        Log.i("Sec", Long.toString(sec));
        int updatedTime =  mediaPlayer.getDuration()-mediaPlayer.getCurrentPosition();
        timerStart(updatedTime,timer,playerSeek,img);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//            int count = adapter.getItemCount();
//            Log.d("test", String.valueOf(count));
    }

    @Override
    public void onPause() {
        super.onPause();
        isPlaying = false;
        pauseMediaPlayer();
    }


    private void pauseMediaPlayer() {
//        binding.playImg.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        mediaPlayer.pause();
    }

//    private void updateMethod(SeekBar playerSeek, TextView timer){
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                playerSeek.setProgress(mediaPlayer.getCurrentPosition());
//                timer.setText(convertFormat(mediaPlayer.getCurrentPosition()));
//                handler.postDelayed(this,500);
//            }
//        };
//    }

    @Override
    public void onClick(int pos, String action, ImageView img, SeekBar playerSeek, TextView timer) {
        count++;


        if(isFirstClick) {
            previousPosition = pos;
            isFirstClick = false;
            playerSeek.setEnabled(true);
            playerSeek.setMax(mediaPlayer.getDuration());
            playMedia(img,playerSeek,timer);
        }else{
            if(previousPosition == pos){
                playMedia(img, playerSeek, timer);
            }else{
//                recyclerView.getLayoutManager().findViewByPosition(previousPosition);
//                previousPositionImg.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                playerSeek.setEnabled(true);
                playerSeek.setMax(mediaPlayer.getDuration());
                img.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                playerSeek.setProgress(0);
                resetMedia(img, playerSeek, timer);
                playMedia(img, playerSeek, timer);
            }
        }
//        Toast.makeText(requireActivity(), "click count" + String.valueOf(count), Toast.LENGTH_SHORT).show();
    }

    public void playMedia(ImageView img,SeekBar playerSeek,TextView timer){


        if (!isPlaying) {
            mediaPlayer.start();
            isPlaying = true;
            img.setImageResource(R.drawable.ic_baseline_pause_24);
            if (isTimerStarted) {
                timerStart((long) mediaPlayer.getDuration(), timer, playerSeek, img);
                isTimerStarted = false;
            } else {
                timerResume(timer, playerSeek, img);
            }
//            handler.postDelayed(runnable, 200);
        } else {
            mediaPlayer.pause();
            img.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            timerPause();
            isTimerStarted = false;
            isPlaying = false;
//            handler.postDelayed(runnable,200);
        }
        playerSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
//                    timer.setText(mediaPlayer.getCurrentPosition()+"");
                    timerPause();
                    int updatedTime = mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition();
                    int minutes = (updatedTime / (1000 * 60));
                    int second = (int) ((updatedTime / 1000) - minutes * 60);
                    String timerReset = String.format("%02d:%02d", minutes, second);

                    timer.setText(timerReset);
//                    timerResume(timer,playerSeek);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (isPlaying) {
                    mediaPlayer.start();
                    timerResume(timer, playerSeek, img);
                }

            }
        });
    }

    public void resetMedia(ImageView img,SeekBar playerSeek,TextView timer){
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
        int minReset = (mediaPlayer.getDuration() / (1000 * 60));
        int secReset = (int) ((mediaPlayer.getDuration() / 1000) - minReset * 60);
        String timerReset = String.format("%02d:%02d",minReset,secReset);
        timer.setText(timerReset);

    }

//    @Override
//    public void Update(SeekBar playerSeek, TextView timer) {
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                playerSeek.setProgress(mediaPlayer.getCurrentPosition());
//                timer.setText(convertFormat(mediaPlayer.getCurrentPosition()));
//                handler.postDelayed(this,500);
//            }
//        };
//    }

}