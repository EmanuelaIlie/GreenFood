package com.example.greenfood;

public class MyGreenFoodData {
    private Integer foodImage;
    private String foodName;
    private String foodDescription;

    public MyGreenFoodData(String foodName, String foodDescription,Integer foodImage) {
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.foodDescription = foodDescription;
    }

    public MyGreenFoodData() {
        this.foodDescription="";
        this.foodName="";
        this.foodImage=0;
    }

    public Integer getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(Integer foodImage) {
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
