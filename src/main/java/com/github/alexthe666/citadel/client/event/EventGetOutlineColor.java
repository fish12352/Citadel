package com.github.alexthe666.citadel.client.event;

import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

public class EventGetOutlineColor extends Event implements ICancellableEvent {
    private Entity entityIn;
    private int color;
    private boolean isAllowed = false;

    public EventGetOutlineColor(Entity entityIn, int color) {
        this.entityIn = entityIn;
        this.color = color;
    }

    public Entity getEntityIn() {
        return entityIn;
    }

    public void setEntityIn(Entity entityIn) {
        this.entityIn = entityIn;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        this.isAllowed = true;
    }
    
    public boolean isAllowed() {
        return this.isAllowed;
    }
}
