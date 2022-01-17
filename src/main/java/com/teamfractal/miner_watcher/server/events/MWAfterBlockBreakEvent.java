package com.teamfractal.miner_watcher.server.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MWAfterBlockBreakEvent {
    public static final Event<After> AFTER = EventFactory.createArrayBacked(After.class,
            (listeners) -> (world, player, block, pos, state, entity) -> {
                for (After event : listeners) {
                    event.afterBlockBreak(world, player, block, pos, state, entity);
                }
            }
    );

    @FunctionalInterface
    public interface After {
        void afterBlockBreak(World world, PlayerEntity player, Block block, BlockPos pos, BlockState state, /* Nullable */ BlockEntity blockEntity);
    }
}
