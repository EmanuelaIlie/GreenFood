package com.example.greenfood;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyGreenFoodAdapter extends RecyclerView.Adapter<MyGreenFoodAdapter.ViewHolder> {
    MyGreenFoodData[] myGreenFoodData;
    OnItemClickListener mOnItemClickListener;
    public MyGreenFoodAdapter(MyGreenFoodData[] myGreenFoodData, OnItemClickListener onItemClickListener) {
        this.myGreenFoodData=myGreenFoodData;
        this.mOnItemClickListener=onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.my_food_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,mOnItemClickListener);

        return viewHolder;
    }


    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyGreenFoodData myGreenFoodDataList = myGreenFoodData[position];
        holder.textViewListName.setText(myGreenFoodDataList.getFoodName());
        holder.textViewListDescription.setText("");
        holder.movieListImage.setImageResource(myGreenFoodDataList.getFoodImage());

    }


    interface OnItemClickListener{
        void onItemClick(int position);
    }
    public int getItemCount() {
        return myGreenFoodData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView movieListImage;
        TextView textViewListName;
        TextView textViewListDescription;
        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener mOnItemClickListener) {
            super(itemView);
            movieListImage = itemView.findViewById(R.id.imageListview);
            textViewListName=itemView.findViewById(R.id.textListName);
            textViewListDescription = itemView.findViewById(R.id.textListDescription);
            this.onItemClickListener=mOnItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
