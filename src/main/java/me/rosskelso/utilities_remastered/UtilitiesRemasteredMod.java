package me.rosskelso.utilities_remastered;

import java.lang.reflect.Field;
import java.util.Optional;

import me.rosskelso.utilities_remastered.item.EnderPouch;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class UtilitiesRemasteredMod implements ModInitializer {
	
	public static final Item ENDER_POUCH = new EnderPouch(new Item.Settings().group(ItemGroup.MISC));
	
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

        Registry.register(Registry.ITEM, new Identifier("utilities", "ender_pouch"), ENDER_POUCH);
	}
}
