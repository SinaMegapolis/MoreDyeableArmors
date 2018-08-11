package sinamegapolis.moredyeablearmors.capability.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sinamegapolis.moredyeablearmors.capability.Capabilities;
import sinamegapolis.moredyeablearmors.util.IntegrationHelper;

import java.util.UUID;

/**
 * a copy from net.daveyx0.primitivemobs.message, used for compatibility stuff
 */
public class MessageCamouflageColor implements IMessage {
    private int color;
    private EntityEquipmentSlot slot;
    private String id;

    public MessageCamouflageColor() {
    }

    public MessageCamouflageColor(int color, EntityEquipmentSlot slot, String id) {
        this.color = color;
        this.slot = slot;
        this.id = id;
    }

    public void fromBytes(ByteBuf buf) {
        this.color = buf.readInt();
        this.slot = EntityEquipmentSlot.values()[buf.readByte()];
        this.id = ByteBufUtils.readUTF8String(buf);
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.color);
        buf.writeByte(this.slot.ordinal());
        ByteBufUtils.writeUTF8String(buf, this.id);
    }

    public static class Handler implements IMessageHandler<MessageCamouflageColor, IMessage> {
        public Handler() {
        }

        public IMessage onMessage(MessageCamouflageColor message, MessageContext ctx) {
            if(!Loader.isModLoaded(IntegrationHelper.PRIMITIVE_MOBS))
                return null;
            EntityLivingBase entity = (EntityLivingBase)ctx.getServerHandler().player.getServerWorld().getEntityFromUuid(UUID.fromString(message.id));
            if (entity != null) {
                ItemStack armor = entity.getItemStackFromSlot(message.slot);
                if (armor.hasCapability(Capabilities.DYEABLE, null)) {
                    armor.getCapability(Capabilities.DYEABLE, null).setColor(message.color);
                }
            }

            return null;
        }
    }
}
