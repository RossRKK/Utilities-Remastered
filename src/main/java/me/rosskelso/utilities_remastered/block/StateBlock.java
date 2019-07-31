package me.rosskelso.utilities_remastered.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

/**
 * An example of a block with state
 * Left over of the temporary block after I realised it didn't need state
 * May be useful in the future as reference
 * Unused in the mod
 * @author rossrkk
 *
 */
public class StateBlock extends Block {

	public static final int MAX_TICKS = 20;
	
	public static final IntProperty TICKS_REMAINING;
	
	public StateBlock(Settings block$Settings_1) {
		super(block$Settings_1);
	}
	
	@Override
	public void onBlockAdded(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
		world_1.getBlockTickScheduler().schedule(blockPos_1, this, this.getTickRate(world_1));
	}
	
	@Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory) {
        stateFactory.add(TICKS_REMAINING);
    }
	
	@Override
	public void onScheduledTick(BlockState state, World world, BlockPos pos, Random rand) {
		int currentTicks = state.get(TICKS_REMAINING);
		System.out.println(currentTicks);
		if (currentTicks > 0) {
			//update the block state, the int is a number of flags by the looks of it
			world.setBlockState(pos, (BlockState)state.with(TICKS_REMAINING, currentTicks - 1), 2);
			
			//schedule the next tick
			world.getBlockTickScheduler().schedule(pos, this, this.getTickRate(world));
		} else {
			world.breakBlock(pos, false);
		}
	}
	
	@Override
	public int getTickRate(ViewableWorld world) {
		return 2; //how long to wait between ticks
	}

	static {
		TICKS_REMAINING = IntProperty.of("ticks", 0, MAX_TICKS);
	}
}
