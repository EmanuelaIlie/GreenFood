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
    Context context;
    public MyGreenFoodAdapter(MyGreenFoodData[] myGreenFoodData, FirstActivity activity) {
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
        final String TAG="emanuela";
        holder.textViewListName.setText(myGreenFoodDataList.getFoodName());
        holder.textViewListDescription.setText("");
        holder.movieListImage.setImageResource(myGreenFoodDataList.getFoodImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("custom-message");
                intent.putExtra("numeCategorie",myGreenFoodDataList.getFoodName().toString());
               // Log.w(TAG,myGreenFoodDataList.getFoodName());
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                Intent intentFereastraNoua = new Intent(v.getContext(), CategoriesActivity.class);
                v.getContext().startActivity(intentFereastraNoua);
            }
        });
    }

    interface OnItemClickListener{
        void onItemClick(int position);
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
