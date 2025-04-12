package com.github.alexthe666.citadel.client.event;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.Event;

@OnlyIn(Dist.CLIENT)
public class EventGetFluidRenderType extends Event {
    private FluidState fluidState;
    private RenderType renderType;
    private Result result = Result.DEFAULT;

    public EventGetFluidRenderType(FluidState fluidState, RenderType renderType) {
        this.fluidState = fluidState;
        this.renderType = renderType;
    }

    public FluidState getFluidState() {
        return fluidState;
    }

    public RenderType getRenderType() {
        return renderType;
    }

    public void setRenderType(RenderType renderType) {
        this.renderType = renderType;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public enum Result {
        ALLOW,
        DENY,
        DEFAULT
    }
}
