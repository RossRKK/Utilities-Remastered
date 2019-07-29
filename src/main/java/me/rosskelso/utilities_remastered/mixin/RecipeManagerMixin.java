package me.rosskelso.utilities_remastered.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;

import com.google.gson.Gson;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.util.Identifier;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin extends JsonDataLoader {

	public RecipeManagerMixin(Gson gson_1, String string_1) {
		super(gson_1, string_1);
	}
	
	protected abstract <C extends Inventory, T extends Recipe<C>> Map<Identifier, Recipe<C>> getAllForType(RecipeType<T> recipeType_1);
	
	public SmeltingRecipe getRecipeForInput(ItemStack stack) {
		return (SmeltingRecipe)this.getAllForType(RecipeType.SMELTING).values().stream().filter((recipe_1) -> {
			return recipe_1.getPreviewInputs().contains(Ingredient.ofItems(stack.getItem()));
		}).findFirst().get();
	}

}
