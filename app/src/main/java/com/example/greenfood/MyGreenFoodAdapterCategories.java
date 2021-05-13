package com.example.greenfood;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * aceasta clasa este adapterul pentru retetele dintr-o categorie
 */
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

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("receipes/"+myGreenFoodDataList.getFoodName()+".jpg");
        holder.textViewListName.setText(myGreenFoodDataList.getFoodName());
        holder.textViewListDescription.setText(myGreenFoodDataList.getFoodDescription());

        //Log.d("EMA",myGreenFoodDataList.getFoodImage()+" in adaptor");
        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext())
                        .load(uri)
                        .into(holder.foodListImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Toast.makeText(ShowActivity.this, "It didnt download", Toast.LENGTH_SHORT).show();
            }
        });


    }

    interface OnItemClickListenerCategories{
        void onItemClickCategories(int position);
    }

    public int getItemCount() {
        return myGreenFoodData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView foodListImage;
        TextView textViewListName;
        TextView textViewListDescription;
        OnItemClickListenerCategories onItemClickListenerCategories;

        public ViewHolder(@NonNull View itemView, OnItemClickListenerCategories mOnItemClickListenerCategories) {
            super(itemView);
            foodListImage = itemView.findViewById(R.id.imageListview);
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
