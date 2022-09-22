package com.example.playaudio;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playaudio.databinding.ItemLayoutBinding;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private ArrayList<ItemDataClass> items;
    private ItemLayoutBinding mBinding;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    boolean isPlaying = false;


    public interface OnItemClickListener{
        void onClick(int pos,String action,ImageView img,SeekBar playerSeek,TextView timer);
//        void Update(SeekBar playerSeek,TextView timer);

    }


    public PlayerAdapter(Context context, OnItemClickListener onItemClickListener, ArrayList<ItemDataClass> items) {
        layoutInflater = LayoutInflater.from(context);
        this.items = items;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ItemDataClass myItemData = items.get(position);
        holder.textView.setText(myItemData.getTimer());
        holder.playPauseImage.setImageResource(myItemData.getImageId());
        holder.seekBar.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ItemDataClass getItem(int position) {
        return items.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView playPauseImage;
        public SeekBar seekBar;
        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playPauseImage = itemView.findViewById(R.id.playImg);
            seekBar = itemView.findViewById(R.id.seekBar);
            textView = itemView.findViewById(R.id.tv_duration);

            playPauseImage.setOnClickListener(this);
//            seekBar.setOnSeekBarChangeListener(this);

        }

        @Override
        public void onClick(View v) {
        Log.i("ID ON CLICK", String.valueOf(v.getId()));
            switch (v.getId()) {
                case R.id.playImg:
                        onItemClickListener.onClick(getAdapterPosition(),"play",playPauseImage,seekBar,textView);
//                        playPauseImage.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + v.getId());

            }
        }

    }


}
