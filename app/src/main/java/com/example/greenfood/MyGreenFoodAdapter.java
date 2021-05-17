package com.example.greenfood;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * aceasta clasa este adapterul categoriilor de retete
 */

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
        View view=layoutInflater.inflate(R.layout.my_food_list_categorii,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,mOnItemClickListener);

        return viewHolder;
    }


    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyGreenFoodData myGreenFoodDataList = myGreenFoodData[position];
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("category/"+myGreenFoodDataList.getFoodName()+".jpg");


        holder.textViewListCategoriiName.setText(myGreenFoodDataList.getFoodName());

        //Log.d("EMA",myGreenFoodDataList.getFoodImage()+" in adaptor");
        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext())
                        .load(uri)
                        .into(holder.foodListCategoriiImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Toast.makeText(ShowActivity.this, "It didnt download", Toast.LENGTH_SHORT).show();
            }
        });
        //Glide.with(holder.itemView.getContext()).load(myGreenFoodDataList.getFoodImage()).into(holder.foodListImage);

    }


    interface OnItemClickListener{
        void onItemClick(int position);
    }
    public int getItemCount() {
        return myGreenFoodData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView foodListCategoriiImage;
        TextView textViewListCategoriiName;
        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener mOnItemClickListener) {
            super(itemView);
            foodListCategoriiImage = itemView.findViewById(R.id.imageListCategoriiview);
            textViewListCategoriiName=itemView.findViewById(R.id.textListCategoriiName);
            this.onItemClickListener=mOnItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
