package com.github.alexthe666.citadel.client.event;

import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

public class EventGetStarBrightness extends Event implements ICancellableEvent {
    private ClientLevel clientLevel;
    private float brightness;
    private float partialTicks;
    private boolean cancelled;

    public EventGetStarBrightness(ClientLevel clientLevel, float brightness, float partialTicks) {
        this.clientLevel = clientLevel;
        this.brightness = brightness;
        this.partialTicks = partialTicks;
        this.cancelled = false;
    }

    public ClientLevel getLevel() {
        return clientLevel;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    @Override
    public boolean isCanceled() {
        return cancelled;
    }

    @Override
    public void setCanceled(boolean cancel) {
        this.cancelled = cancel;
    }
}
