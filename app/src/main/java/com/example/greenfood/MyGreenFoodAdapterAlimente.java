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
 * aceasta clasa este adapterul pentru lista de alimente
 */

public class MyGreenFoodAdapterAlimente extends RecyclerView.Adapter<MyGreenFoodAdapterAlimente.ViewHolder> {
    MyGreenFoodData[] myGreenFoodData;
    OnItemClickListenerAlimente mOnItemClickListenerAlimente;
    public MyGreenFoodAdapterAlimente(MyGreenFoodData[] myGreenFoodData,OnItemClickListenerAlimente OnItemClickListenerAlimente) {
        this.myGreenFoodData=myGreenFoodData;
        this.mOnItemClickListenerAlimente=OnItemClickListenerAlimente;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.my_food_list_categorii,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,mOnItemClickListenerAlimente);

        return viewHolder;
    }


    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyGreenFoodData myGreenFoodDataList = myGreenFoodData[position];

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("alimente/"+myGreenFoodDataList.getFoodName()+".jpg");
        holder.textViewListName.setText(myGreenFoodDataList.getFoodName());

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

    interface OnItemClickListenerAlimente{
        void onItemClickAlimente(int position);
    }

    public int getItemCount() {
        return myGreenFoodData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView foodListImage;
        TextView textViewListName;

        OnItemClickListenerAlimente OnItemClickListenerAlimente;

        public ViewHolder(@NonNull View itemView, OnItemClickListenerAlimente mOnItemClickListenerAlimente) {
            super(itemView);
            foodListImage = itemView.findViewById(R.id.imageListCategoriiview);
            textViewListName=itemView.findViewById(R.id.textListCategoriiName);

            this.OnItemClickListenerAlimente=mOnItemClickListenerAlimente;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            OnItemClickListenerAlimente.onItemClickAlimente(getAdapterPosition());
        }
    }
}
