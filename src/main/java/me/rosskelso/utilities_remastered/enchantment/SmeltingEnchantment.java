package me.rosskelso.utilities_remastered.enchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import me.rosskelso.utilities_remastered.UtilitiesRemasteredMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

/**
 * An enchantment that replaces block drops with the smelted version of themselves.
 * @author rossrkk
 *
 */
public class SmeltingEnchantment extends DropReplaceEnchantment {
	//TODO configure rarity and level
	public SmeltingEnchantment(Weight enchantment$Weight_1, EnchantmentTarget enchantmentTarget_1,
			EquipmentSlot[] equipmentSlots_1, int priority) {
		super(enchantment$Weight_1, enchantmentTarget_1, equipmentSlots_1, priority);
	}

	@Override
	public List<ItemStack> replaceDrops(BlockState blockState, ServerWorld world, BlockPos blockPos,
			BlockEntity blockEntity, Entity entity, List<ItemStack> normalDrops, int level) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (ItemStack drop:normalDrops) {
			//lookup the result of smelting the current item
			Optional<Recipe<?>> recipe = world.getRecipeManager().values().parallelStream().filter((recipe_1) -> {
				return recipe_1.getType() == RecipeType.SMELTING && recipe_1.getPreviewInputs().parallelStream().anyMatch((i) -> i.test(drop));
			}).findFirst();
			
			if (recipe.isPresent()) {
				list.add(recipe.get().getOutput());
			}
		}
		
		//let's not punish the player too much, drop the item if the list is empty
		if (!list.isEmpty()) {
			return list;
		} else {
			return normalDrops;
		}
	}

	//should not be allowed with silk touch, fortune or void, should chain with pulverize (i.e. stone becomes glass)
	@Override
	public boolean differs(Enchantment enchantment) {
	      return super.differs(enchantment) 
	    		  && enchantment != Enchantments.SILK_TOUCH
	    		  && enchantment != UtilitiesRemasteredMod.VOID;
	}
}
