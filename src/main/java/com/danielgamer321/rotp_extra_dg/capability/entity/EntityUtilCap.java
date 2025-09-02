package com.danielgamer321.rotp_extra_dg.capability.entity;

import com.danielgamer321.rotp_extra_dg.network.PacketManager;
import com.danielgamer321.rotp_extra_dg.network.packets.fromserver.TrErasedPacket;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;

public class EntityUtilCap {
    private final Entity entity;
    private float erased;
    private boolean positionLocking;
    
    public EntityUtilCap(Entity entity) {
        this.entity = entity;
    }

    public void setErased(float erased) {
        erased = Math.max(erased, 0);
        if (this.erased != erased) {
            this.erased = erased;
            if (!entity.level.isClientSide()) {
                PacketManager.sendToClientsTrackingAndSelf(new TrErasedPacket(entity.getId(), erased), entity);
            }
        }
        this.erased = erased;
    }

    public float getErased() {
        return erased;
    }

    public void setPositionLocking(boolean locked) {
        this.positionLocking = locked;
    }

    public boolean getPositionLocking() {
        return positionLocking;
    }

    public CompoundNBT toNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putFloat("Erased", erased);
        nbt.putBoolean("PositionLocking", positionLocking);
        return nbt;
    }

    public void fromNBT(CompoundNBT nbt) {
        this.erased = nbt.getFloat("Erased");
        this.positionLocking = nbt.getBoolean("PositionLocking");
    }
}
