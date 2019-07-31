package me.rosskelso.utilities_remastered.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import me.rosskelso.utilities_remastered.enchantment.DropReplaceEnchantment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
//import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.loot.context.LootContext;
import net.minecraft.world.loot.context.LootContextParameters;

@Mixin(Block.class)
public abstract class BlockMixin implements ItemConvertible {
	
	/***
	 * This mixin applies the drop replacement enchantments in the correct order
	 * @param blockState
	 * @param world
	 * @param blockPos
	 * @param blockEntity
	 * @param entity
	 * @param stack
	 * @return
	 */
	@Overwrite
	public static List<ItemStack> getDroppedStacks(BlockState blockState, ServerWorld world, BlockPos blockPos, /*@Nullable*/ BlockEntity blockEntity, Entity entity, ItemStack stack) {
		LootContext.Builder builder = (new LootContext.Builder(world))
				.setRandom(world.random).put(LootContextParameters.POSITION, blockPos)
				.put(LootContextParameters.TOOL, stack).put(LootContextParameters.THIS_ENTITY, entity)
				.putNullable(LootContextParameters.BLOCK_ENTITY, blockEntity);
		List<ItemStack> currentDrops = blockState.getDroppedStacks(builder);
		
		
		if (stack.hasEnchantments()) {
			Map<Enchantment, Integer> map =  EnchantmentHelper.getEnchantments(stack);
			Set<Entry<Enchantment, Integer>> entries = map.entrySet();
			
			List<Pair<DropReplaceEnchantment, Integer>> sortedEntries = new ArrayList<Pair<DropReplaceEnchantment, Integer>>(map.size());
			
			sortedEntries.addAll(entries.parallelStream()
					.filter((x) -> x.getKey() instanceof DropReplaceEnchantment)
					.map(x -> new Pair<DropReplaceEnchantment, Integer>((DropReplaceEnchantment)x.getKey(), x.getValue())).collect(Collectors.toList()));
			
			sortedEntries.sort((a, b) -> a.getLeft().getPriority() - b.getLeft().getPriority());
			
			for (Pair<DropReplaceEnchantment, Integer> entry:sortedEntries) {
				currentDrops = entry.getLeft().replaceDrops(blockState, world, blockPos, blockEntity, entity, currentDrops, entry.getRight());
			}
		}
		
		return currentDrops;
	}
}
