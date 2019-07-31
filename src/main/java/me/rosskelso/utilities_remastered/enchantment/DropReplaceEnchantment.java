package me.rosskelso.utilities_remastered.enchantment;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public abstract class DropReplaceEnchantment extends Enchantment {
	
	/***
	 * An integer used to sort the order the enchantments are applied, lower numbers go first.
	 */
	private int sortOrder;

	
	public DropReplaceEnchantment(Weight enchantment$Weight_1, EnchantmentTarget enchantmentTarget_1, EquipmentSlot[] equipmentSlots_1, int priority) {
		super(enchantment$Weight_1, enchantmentTarget_1, equipmentSlots_1);
		this.sortOrder = priority;
	}
	
	public int getPriority() {
		return sortOrder;
	}
	
	
	/**
	 * Given a list of dropped items, return the list of items that should actually be dropped.
	 * @param blockState The state the block was in.
	 * @param world The world the block was in.
	 * @param blockPos The position of the block.
	 * @param blockEntity Any block entity the block had.
	 * @param entity The entity that broke the block (I think).
	 * @param normalDrop The items that would be dropped normally.
	 * @param level The level of the enchantment.
	 * @return The items that the block should drop.
	 */
	public abstract List<ItemStack> replaceDrops(BlockState blockState, ServerWorld world, BlockPos blockPos, BlockEntity blockEntity, Entity entity, List<ItemStack> normalDrops, int level);
}
