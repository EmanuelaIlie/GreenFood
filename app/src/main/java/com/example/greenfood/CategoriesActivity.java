package com.example.greenfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        RecyclerView recyclerView=findViewById(R.id.recyclerViewCategories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyGreenFoodData[] myGreenFoodData = new MyGreenFoodData[]{

                new MyGreenFoodData("Sandvis","3 sandvisuri sanatoase, perfecte pentru micul dejun",R.drawable.poza01),
                new MyGreenFoodData("Tocanita de legume cu tofu","O portie plina de vitamine",R.drawable.poza02),
                new MyGreenFoodData("Tort de ciocolata","Tort  cu multa ciocolata",R.drawable.poza03),
                new MyGreenFoodData("Briose","3 feluri de briose, decorate cu frisca si fructe",R.drawable.poza04),
                new MyGreenFoodData("Pizza inima","Pizza in forma de inima cu multe rosii",R.drawable.poza05),
                new MyGreenFoodData("MilkShake","Milkshake cu lapte de migdale",R.drawable.poza06),

        };


        MyGreenFoodAdapterCategories myGreenFoodAdapterCategories = new MyGreenFoodAdapterCategories(myGreenFoodData, CategoriesActivity.this);
        recyclerView.setAdapter(myGreenFoodAdapterCategories);
    }

}