package me.rosskelso.utilities_remastered.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

public class TemporaryBlock extends Block {


	public TemporaryBlock(Settings block$Settings_1) {
		super(block$Settings_1);
//		this.setDefaultState((BlockState)((BlockState)this.stateFactory.getDefaultState()).with(TICKS_REMAINING, MAX_TICKS));
	}
	
	@Override
	public void onBlockAdded(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
		world_1.getBlockTickScheduler().schedule(blockPos_1, this, this.getTickRate(world_1));
	}

	
	@Override
	public void onScheduledTick(BlockState state, World world, BlockPos pos, Random rand) {
			world.breakBlock(pos, false);
	}
	
	@Override
	public int getTickRate(ViewableWorld world) {
		return 200;
	}
	

}
