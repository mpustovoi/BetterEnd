package org.betterx.betterend.interfaces;

import net.minecraft.world.entity.Entity;

public interface ISlime {
    void be_setSlimeSize(int size, boolean heal);

    void be_entityRemove(Entity.RemovalReason removalReason);
}
