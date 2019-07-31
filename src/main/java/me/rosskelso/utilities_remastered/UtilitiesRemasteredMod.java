package me.rosskelso.utilities_remastered;

import me.rosskelso.utilities_remastered.block.TemporaryBlock;
import me.rosskelso.utilities_remastered.enchantment.PulverizeEnchant;
import me.rosskelso.utilities_remastered.enchantment.SmeltingEnchantment;
import me.rosskelso.utilities_remastered.enchantment.VoidEnchantment;
import me.rosskelso.utilities_remastered.item.EnderPouch;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BlockItem;
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
    	    },
    	    0
    	);
	
	public static final Enchantment PULVERIZE = new PulverizeEnchant(
    	    Enchantment.Weight.COMMON,
    	    EnchantmentTarget.DIGGER,
    	    new EquipmentSlot[] {
    	    		EquipmentSlot.MAINHAND
    	    },
    	    0
    	);
	
	public static final Enchantment SMELTING = new SmeltingEnchantment(
    	    Enchantment.Weight.COMMON,
    	    EnchantmentTarget.DIGGER,
    	    new EquipmentSlot[] {
    	    		EquipmentSlot.MAINHAND
    	    },
    	    1
    	);
	
	public static final Block TEMP_BLOCK = new TemporaryBlock(FabricBlockSettings.of(Material.AIR).breakInstantly().dropsNothing().build());
	
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registry.BLOCK, new Identifier("utilities", "temporary_block"), TEMP_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("utilities", "temporary_block"), new BlockItem(TEMP_BLOCK, new Item.Settings().group(ItemGroup.MISC)));

        Registry.register(Registry.ITEM, new Identifier("utilities", "ender_pouch"), ENDER_POUCH);
        
        Registry.register(Registry.ENCHANTMENT, new Identifier("utilities", "void"), VOID);
        Registry.register(Registry.ENCHANTMENT, new Identifier("utilities", "pulverize"), PULVERIZE);
        Registry.register(Registry.ENCHANTMENT, new Identifier("utilities", "smelting"), SMELTING);
	}
}
