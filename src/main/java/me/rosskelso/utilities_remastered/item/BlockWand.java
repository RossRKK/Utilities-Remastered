package me.rosskelso.utilities_remastered.item;

import java.util.function.Consumer;

import me.rosskelso.utilities_remastered.UtilitiesRemasteredMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class BlockWand extends BlockItem {

	public BlockWand(Settings item$Settings_1) {
		super(UtilitiesRemasteredMod.TEMP_BLOCK, item$Settings_1);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		ActionResult res = super.useOnBlock(context);
		
		if (res == ActionResult.SUCCESS) {
			context.getStack().increment(1);
			PlayerEntity player = context.getPlayer();
			Hand hand = context.getHand();
			
			//consume some durability
	    	player.getStackInHand(hand).damage(1, (LivingEntity)player, (Consumer<LivingEntity>)((playerEntity_1x) -> {
	            ((LivingEntity) playerEntity_1x).sendToolBreakStatus(hand);
	        }));
		}
		
		return res;
	}

	
}
