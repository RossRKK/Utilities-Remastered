package me.rosskelso.utilities_remastered.enchantment;

import java.util.ArrayList;
import java.util.List;

import me.rosskelso.utilities_remastered.UtilitiesRemasteredMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

/***
 * An enchantment that replaces block drops with "pulverized" versions of themselves (like a macerator or pulverizer).
 * Mainly stone tpyes to sand. 
 * @author rossrkk
 *
 */
public class PulverizeEnchant extends DropReplaceEnchantment {
	//TODO setup available levels and rarity
	public PulverizeEnchant(Weight weight, EnchantmentTarget target, EquipmentSlot[] slots, int priority) {
		super(weight, target, slots, priority);
	}

	@Override
	public List<ItemStack> replaceDrops(BlockState blockState, ServerWorld world, BlockPos blockPos,
			BlockEntity blockEntity, Entity entity, List<ItemStack> normalDrops, int level) {
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
		} else {
			return normalDrops;
		}
	}
	
	//should not be allowed with silk touch or void, should chain with smelting
	@Override
	public boolean differs(Enchantment enchantment) {
	      return super.differs(enchantment) 
	    		  && enchantment != Enchantments.SILK_TOUCH 
	    		  && enchantment != UtilitiesRemasteredMod.VOID;
	}

}
