package com.jawdata.hackjunction.jawdata.Serializable;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.Objects;

@IgnoreExtraProperties
public class Recipe {

    @PropertyName("calories")
    private int calories;

    @PropertyName("protein")
    private int protein;

    @PropertyName("title")
    private String title;

    public Recipe() {

        // serialized
    }

    public Recipe(int calories, int protein, String title) {
        this.calories = calories;
        this.protein = protein;
        this.title = title;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return calories == recipe.calories &&
                protein == recipe.protein &&
                Objects.equals(title, recipe.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(calories, protein, title);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "calories=" + calories +
                ", protein=" + protein +
                ", title='" + title + '\'' +
                '}';
    }
}
