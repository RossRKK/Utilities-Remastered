package me.rosskelso.utilities_remastered.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

public class TemporaryBlock extends Block {
//	public static final int MAX_TICKS = 20;
	
//	public static final IntProperty TICKS_REMAINING;

	public TemporaryBlock(Settings block$Settings_1) {
		super(block$Settings_1);
//		this.setDefaultState((BlockState)((BlockState)this.stateFactory.getDefaultState()).with(TICKS_REMAINING, MAX_TICKS));
	}
	
	@Override
	public void onBlockAdded(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
		world_1.getBlockTickScheduler().schedule(blockPos_1, this, this.getTickRate(world_1));
	}
	
//	@Override
//    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory) {
//        stateFactory.add(TICKS_REMAINING);
//    }
	
	@Override
	public void onScheduledTick(BlockState state, World world, BlockPos pos, Random rand) {
//		int currentTicks = state.get(TICKS_REMAINING);
//		System.out.println(currentTicks);
//		if (currentTicks > 0) {
//			world.setBlockState(pos, (BlockState)state.with(TICKS_REMAINING, currentTicks - 1), 2);
//			
//			world.getBlockTickScheduler().schedule(pos, this, this.getTickRate(world));
//		} else {
			world.breakBlock(pos, false);
//		}
	}
	
	@Override
	public int getTickRate(ViewableWorld world) {
		return 200;
	}
	
//	static {
//		TICKS_REMAINING = IntProperty.of("level", 0, MAX_TICKS);
//	}
}
