package com.github.alexthe666.citadel.mixin.client;

import com.github.alexthe666.citadel.CitadelConstants;
import com.github.alexthe666.citadel.client.event.EventGetStarBrightness;
import com.github.alexthe666.citadel.client.tick.ClientTickRateTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.mojang.blaze3d.systems.RenderSystem;

import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends Level {

    protected ClientLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> levelResourceKey, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeHolder, Supplier<ProfilerFiller> filler, boolean b1, boolean b2, long seed, int i) {
        super(writableLevelData, levelResourceKey, registryAccess, dimensionTypeHolder, filler, b1, b2, seed, i);
    }

    @Inject(method = "getSkyDarken", at = @At("HEAD"), cancellable = true)
    private void citadel_onGetStarBrightness(CallbackInfoReturnable<Float> cir) {
        float currentBrightness = cir.getReturnValue();
        EventGetStarBrightness event = new EventGetStarBrightness((ClientLevel)(Object)this, currentBrightness, 0.0f);
        NeoForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            cir.setReturnValue(event.getBrightness());
        }
    }

    @ModifyConstant(
            method = "Lnet/minecraft/client/multiplayer/ClientLevel;tickTime()V",
            remap = CitadelConstants.REMAPREFS,
            constant = @Constant(longValue = 1L),
            expect = 2)
    private long citadel_clientSetDayTime(long timeIn) {
        return ClientTickRateTracker.getForClient(Minecraft.getInstance()).getDayTimeIncrement(timeIn);
    }
}

