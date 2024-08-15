package mczme.lingshi.common.recipe;

import mczme.lingshi.client.recipebook.CookingFoodRecipeLabel;

public abstract class CookingFoodRecipe {

    private final CookingFoodRecipeLabel label;

    protected CookingFoodRecipe(CookingFoodRecipeLabel label) {
        this.label = label;
    }

    public CookingFoodRecipeLabel getLabel(){
        return label;
    }

    public String getLabelName(){
        return label.toString();
    }

}
