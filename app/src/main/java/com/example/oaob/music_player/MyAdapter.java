package com.example.oaob.music_player;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    public ArrayList<Music> mData;
    private int playing = 0;

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTextView;
        ViewHolder(View v)
        {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.title);;
        }
    }

    MyAdapter(ArrayList<Music> data)
    {
        mData = data;
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position)
    {
        holder.mTextView.setText(mData.get(position).name);
        if(position == playing)
        {
            holder.mTextView.setTextColor(0xff00ff00);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                playing = position;
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }
}