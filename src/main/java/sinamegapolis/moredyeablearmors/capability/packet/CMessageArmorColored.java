package sinamegapolis.moredyeablearmors.capability.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sinamegapolis.moredyeablearmors.capability.Capabilities;
import sinamegapolis.moredyeablearmors.capability.IDyeable;

public class CMessageArmorColored implements IMessage {

    public CMessageArmorColored(){}

    private int color;
    private ItemStack stack;
    public CMessageArmorColored(int color, ItemStack stack) {
        this.color = color;
        this.stack = stack;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        color = buf.readInt();
        stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(color);
        ByteBufUtils.writeItemStack(buf, this.stack);
    }

    public static class ArmorColoredMessageHandler implements IMessageHandler<CMessageArmorColored, IMessage> {
        public ArmorColoredMessageHandler(){
            //because forge requires this so ¯\_(ツ)_/¯
        }
        @Override
        public IMessage onMessage(CMessageArmorColored message, MessageContext ctx) {
            message.stack.getCapability(Capabilities.DYEABLE, null).setColor(message.color);
            return null;
        }
    }
}
