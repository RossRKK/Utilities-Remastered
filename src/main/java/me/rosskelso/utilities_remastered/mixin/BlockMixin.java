package me.rosskelso.utilities_remastered.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import me.rosskelso.utilities_remastered.UtilitiesRemasteredMod;
import net.minecraft.block.Block;
import net.minecraft.block.Block.Settings;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.block.Material;
//import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.loot.context.LootContext;
import net.minecraft.world.loot.context.LootContextParameters;

@Mixin(Block.class)
public abstract class BlockMixin implements ItemConvertible {
	
	@Overwrite
	public static List<ItemStack> getDroppedStacks(BlockState blockState, ServerWorld world, BlockPos blockPos, /*@Nullable*/ BlockEntity blockEntity, Entity entity, ItemStack stack) {
		LootContext.Builder builder = (new LootContext.Builder(world))
				.setRandom(world.random).put(LootContextParameters.POSITION, blockPos)
				.put(LootContextParameters.TOOL, stack).put(LootContextParameters.THIS_ENTITY, entity)
				.putNullable(LootContextParameters.BLOCK_ENTITY, blockEntity);
		List<ItemStack> normalDrops = blockState.getDroppedStacks(builder);
		
		
		if (stack.hasEnchantments()) {
			boolean hasVoid = EnchantmentHelper.getLevel(UtilitiesRemasteredMod.VOID, stack) != 0;
			if (hasVoid) {
				return new ArrayList<>();
			}
			
			boolean hasSmelting = EnchantmentHelper.getLevel(UtilitiesRemasteredMod.SMELTING, stack) != 0;
			if (hasSmelting) {
				List<ItemStack> list = new ArrayList<ItemStack>();
				for (ItemStack drop:normalDrops) {
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
				}
			}
			
			boolean hasPulverize = EnchantmentHelper.getLevel(UtilitiesRemasteredMod.PULVERIZE, stack) != 0;
			if (hasPulverize) {
				List<ItemStack> list = new ArrayList<ItemStack>();
				
				Tag<Item> stoneTag = world.getTagManager().items().getOrCreate(new Identifier("utilities", "stone"));
				Tag<Item> redSandstoneTag = world.getTagManager().items().getOrCreate(new Identifier("utilities", "red_sandstone"));

				for (ItemStack drop:normalDrops) {
					if (drop.getItem().isIn(stoneTag)) {
						list.add(new ItemStack(Items.SAND, drop.getCount()));
					} else if (redSandstoneTag.contains(drop.getItem())) {
						list.add(new ItemStack(Items.RED_SAND, drop.getCount()));
					}
				}
				
				//let's not punish the player too much, drop the item if the list is empty
				if (!list.isEmpty()) {
					return list;
				}
			}
		}
		
		
		return normalDrops;
	}
}
