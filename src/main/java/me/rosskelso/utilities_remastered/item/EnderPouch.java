package me.rosskelso.utilities_remastered.item;

import java.util.function.Consumer;

import net.minecraft.client.network.ClientDummyContainerProvider;
import net.minecraft.container.GenericContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * An item that lets the player access their ender chest while they're on the move.
 * @author rossrkk
 *
 */
public class EnderPouch extends Item {

	public static final TranslatableText CONTAINER_NAME;
	
	static {
	      CONTAINER_NAME = new TranslatableText("item.utilities.ender_pouch", new Object[0]);
	   }

	public EnderPouch(Settings settings) {
		super(settings);
	}

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
    {
    	//play the ender chest opening sound
        player.playSound(SoundEvents.BLOCK_ENDER_CHEST_OPEN, 1.0F, 1.0F);
        //get the contents of the player's ender chest
    	EnderChestInventory enderChestInventory = player.getEnderChestInventory();
    	//open a container gui for the ender chest
    	player.openContainer(new ClientDummyContainerProvider((int_1, playerInventory, playerEntity) -> {
            return GenericContainer.createGeneric9x3(int_1, playerInventory, enderChestInventory);
         }, CONTAINER_NAME));
    	
    	//consume some durability
    	player.getStackInHand(hand).damage(1, (LivingEntity)player, (Consumer<LivingEntity>)((playerEntity_1x) -> {
            ((LivingEntity) playerEntity_1x).sendToolBreakStatus(hand);
         }));
    	
        return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
    }
}
