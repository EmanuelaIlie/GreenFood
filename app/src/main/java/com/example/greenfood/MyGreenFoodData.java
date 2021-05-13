package com.example.greenfood;

/**
 * Clasa contine datele utile pentru adapter, recyclerView
 */
public class MyGreenFoodData {
    private String foodImage;
    private String foodName;
    private String foodDescription;

    public MyGreenFoodData(String foodName, String foodDescription,String foodImage) {
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.foodDescription = foodDescription;
    }

    public MyGreenFoodData() {
        this.foodDescription="";
        this.foodName="";
        this.foodImage="";
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }
}
