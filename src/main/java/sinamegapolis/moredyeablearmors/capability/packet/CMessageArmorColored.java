package sinamegapolis.moredyeablearmors.capability.packet;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CMessageArmorColored implements IMessage {

    public CMessageArmorColored(){}

    private int color;
    public CMessageArmorColored(int color) {
        this.color = color;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        color = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(color);
    }

    public static class ArmorColoredMessageHandler implements IMessageHandler<CMessageArmorColored, IMessage> {
        public ArmorColoredMessageHandler(){
            //because forge requires this so ¯\_(ツ)_/¯
        }
        @Override
        public IMessage onMessage(CMessageArmorColored message, MessageContext ctx) {

            return null;
        }
    }
}
