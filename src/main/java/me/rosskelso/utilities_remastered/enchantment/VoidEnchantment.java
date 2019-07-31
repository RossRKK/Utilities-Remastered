package me.rosskelso.utilities_remastered.enchantment;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class VoidEnchantment extends DropReplaceEnchantment {

	//TODO setup available levels and rarity
	public VoidEnchantment(Weight weight, EnchantmentTarget target, EquipmentSlot[] slots, int priority) {
		super(weight, target, slots, priority);
	}

	@Override
	public List<ItemStack> replaceDrops(BlockState blockState, ServerWorld world, BlockPos blockPos,
			BlockEntity blockEntity, Entity entity, List<ItemStack> normalDrops, int level) {
		return new ArrayList<>();
	}

	@Override
	public boolean differs(Enchantment enchantment) {
	      return super.differs(enchantment) 
	    		  && enchantment != Enchantments.FORTUNE 
	    		  && enchantment != Enchantments.SILK_TOUCH 
	    		  && !(enchantment instanceof DropReplaceEnchantment); //void can't be combined with anything that replaces the drops
	}
}
