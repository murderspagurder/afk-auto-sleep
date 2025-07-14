package dev.spagurder.afkautosleep.core;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BedUtils {

    private static long lastTrySleep = 0L;
    private static final long TRY_SLEEP_COOLDOWN = 100;

    public static @Nullable BlockPos getTargetedBed(Minecraft client) {
        if (client.level == null) return null;
        HitResult target = client.hitResult;
        if (target != null && target.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockTarget = (BlockHitResult) target;
            BlockPos blockPos = blockTarget.getBlockPos();
            if (isBed(client, blockPos)) {
                return blockPos;
            }
        }
        return null;
    }

    public static boolean isBed(Minecraft client, BlockPos pos) {
        if (client.level == null) return false;
        return client.level.getBlockState(pos).is(BlockTags.BEDS);
    }

    public static @Nullable String checkBedError(Minecraft client, BlockPos pos) {
        if (client.level == null) return null;
        if (!client.level.isLoaded(pos)) {
            return "Selected block is no longer loaded!";
        }
        if (!BedUtils.isBed(client, pos)) {
            return "Selected block is no longer a bed!";
        }
        return null;
    }

    public static boolean isSleepTime(Minecraft client) {
        if (client.level == null) return false;
        boolean isNight = client.level.getDayTime() % 24000 > 12540;
        return isNight || client.level.isThundering();
    }

    public static void trySleep(Minecraft client, BlockPos blockPos) {
        if (client.player == null || client.level == null || client.gameMode == null) return;
        long timeSinceLastTrySleep = client.level.getGameTime() - lastTrySleep;
        if (timeSinceLastTrySleep > 0 && timeSinceLastTrySleep < TRY_SLEEP_COOLDOWN) return;
        if (!checkDistance(client, blockPos)) return;
        BlockHitResult targetBlock = new BlockHitResult(
                Vec3.atCenterOf(blockPos),
                Direction.UP,
                blockPos,
                false
        );
        client.gameMode.useItemOn(client.player, InteractionHand.MAIN_HAND, targetBlock);
        lastTrySleep = client.level.getGameTime();
    }

    public static boolean checkDistance(Minecraft client, BlockPos blockPos) {
        if (client.player == null) return false;
        Vec3 center = Vec3.atCenterOf(blockPos);
        if (client.player.distanceToSqr(center) <= 20.25) return true;
        Util.messageClient(client, "Too far to interact with bed!", false);
        return false;
    }

}
