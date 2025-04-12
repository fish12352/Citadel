package com.github.alexthe666.citadel.client.event;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

public class EventPosePlayerHand extends Event implements ICancellableEvent {
    private LivingEntity entityIn;
    private HumanoidModel model;
    private boolean left;
    private boolean canceled;

    public EventPosePlayerHand(LivingEntity entityIn, HumanoidModel model, boolean left) {
        this.entityIn = entityIn;
        this.model = model;
        this.left = left;
        this.canceled = false;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void setCanceled(boolean cancel) {
        this.canceled = cancel;
    }

    public Entity getEntityIn() {
        return entityIn;
    }

    public HumanoidModel getModel() {
        return model;
    }

    public boolean isLeftHand() {
        return left;
    }
}
