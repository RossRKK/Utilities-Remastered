package me.rosskelso.utilities_remastered;

import me.rosskelso.utilities_remastered.enchantment.VoidEnchantment;
import me.rosskelso.utilities_remastered.item.EnderPouch;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class UtilitiesRemasteredMod implements ModInitializer {
	
	public static final Item ENDER_POUCH = new EnderPouch(new Item.Settings().group(ItemGroup.MISC).maxCount(1).maxDamage(1024));
	
	public static final Enchantment VOID = new VoidEnchantment(
    	    Enchantment.Weight.COMMON,
    	    EnchantmentTarget.DIGGER,
    	    new EquipmentSlot[] {
    	    		EquipmentSlot.MAINHAND
    	    }
    	);
	
	public static final Enchantment PULVERIZE = new VoidEnchantment(
    	    Enchantment.Weight.COMMON,
    	    EnchantmentTarget.DIGGER,
    	    new EquipmentSlot[] {
    	    		EquipmentSlot.MAINHAND
    	    }
    	);
	
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

        Registry.register(Registry.ITEM, new Identifier("utilities", "ender_pouch"), ENDER_POUCH);
        
        Registry.register(Registry.ENCHANTMENT, new Identifier("utilities", "void"), VOID);
        Registry.register(Registry.ENCHANTMENT, new Identifier("utilities", "pulverize"), PULVERIZE);
	}
}
