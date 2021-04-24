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
    Context context;
    public MyGreenFoodAdapterCategories(MyGreenFoodData[] myGreenFoodData, CategoriesActivity activity) {
        this.myGreenFoodData=myGreenFoodData;
        this.context=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.my_food_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyGreenFoodData myGreenFoodDataList = myGreenFoodData[position];

        holder.textViewListName.setText(myGreenFoodDataList.getFoodName());
        holder.textViewListDescription.setText(myGreenFoodDataList.getFoodDescription());
        holder.movieListImage.setImageResource(myGreenFoodDataList.getFoodImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ReceipesActivity.class);
                intent.putExtra("numeReteta",myGreenFoodDataList.getFoodName());
                v.getContext().startActivity(intent);
            }
        });
    }


    public int getItemCount() {
        return myGreenFoodData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView movieListImage;
        TextView textViewListName;
        TextView textViewListDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieListImage = itemView.findViewById(R.id.imageListview);
            textViewListName=itemView.findViewById(R.id.textListName);
            textViewListDescription = itemView.findViewById(R.id.textListDescription);
        }
    }
}
