package com.danielgamer321.rotp_extra_dg.network.packets.fromserver;

import com.danielgamer321.rotp_extra_dg.capability.entity.EntityUtilCapProvider;
import com.danielgamer321.rotp_extra_dg.capability.entity.LivingUtilCapProvider;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.network.packets.IModPacketHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TrSetLockStatusPacket {
    private final int entityId;
    private final boolean status;
    private final boolean purpose;

    public TrSetLockStatusPacket(int entityId, boolean status, boolean purpose) {
        this.entityId = entityId;
        this.status = status;
        this.purpose = purpose;
    }
    
    
    
    public static class Handler implements IModPacketHandler<TrSetLockStatusPacket> {

        @Override
        public void encode(TrSetLockStatusPacket msg, PacketBuffer buf) {
            buf.writeInt(msg.entityId);
            buf.writeBoolean(msg.status);
            buf.writeBoolean(msg.purpose);
        }

        @Override
        public TrSetLockStatusPacket decode(PacketBuffer buf) {
            return new TrSetLockStatusPacket(buf.readInt(), buf.readBoolean(), buf.readBoolean());
        }

        @Override
        public void handle(TrSetLockStatusPacket msg, Supplier<NetworkEvent.Context> ctx) {
            Entity entity = ClientUtil.getEntityById(msg.entityId);
            if (entity != null) {
                if (msg.purpose) {
                    entity.getCapability(EntityUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.setPositionLocking(msg.status));
                }
                else {
                    if (entity instanceof LivingEntity) {
                        LivingEntity user = (LivingEntity) entity;
                        user.getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.setBlockingItemsStatus(msg.status));
                    }
                }
            }
        }

        @Override
        public Class<TrSetLockStatusPacket> getPacketClass() {
            return TrSetLockStatusPacket.class;
        }
    }

}
