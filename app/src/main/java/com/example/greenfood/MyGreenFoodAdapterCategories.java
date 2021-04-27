package com.example.greenfood;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyGreenFoodAdapterCategories extends RecyclerView.Adapter<MyGreenFoodAdapterCategories.ViewHolder> {
    MyGreenFoodData[] myGreenFoodData;
    OnItemClickListenerCategories mOnItemClickListenerCategories;
    public MyGreenFoodAdapterCategories(MyGreenFoodData[] myGreenFoodData,OnItemClickListenerCategories onItemClickListenerCategories) {
        this.myGreenFoodData=myGreenFoodData;
        this.mOnItemClickListenerCategories=onItemClickListenerCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.my_food_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,mOnItemClickListenerCategories);

        return viewHolder;
    }


    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyGreenFoodData myGreenFoodDataList = myGreenFoodData[position];

        holder.textViewListName.setText(myGreenFoodDataList.getFoodName());
        holder.textViewListDescription.setText(myGreenFoodDataList.getFoodDescription());
        holder.movieListImage.setImageResource(myGreenFoodDataList.getFoodImage());


    }

    interface OnItemClickListenerCategories{
        void onItemClickCategories(int position);
    }

    public int getItemCount() {
        return myGreenFoodData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView movieListImage;
        TextView textViewListName;
        TextView textViewListDescription;
        OnItemClickListenerCategories onItemClickListenerCategories;

        public ViewHolder(@NonNull View itemView, OnItemClickListenerCategories mOnItemClickListenerCategories) {
            super(itemView);
            movieListImage = itemView.findViewById(R.id.imageListview);
            textViewListName=itemView.findViewById(R.id.textListName);
            textViewListDescription = itemView.findViewById(R.id.textListDescription);

            this.onItemClickListenerCategories=mOnItemClickListenerCategories;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListenerCategories.onItemClickCategories(getAdapterPosition());
        }
    }
}
