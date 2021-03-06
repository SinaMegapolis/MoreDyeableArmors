package sinamegapolis.moredyeablearmors.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import sinamegapolis.moredyeablearmors.capability.Capabilities;
import sinamegapolis.moredyeablearmors.capability.packet.CMessageArmorColored;
import sinamegapolis.moredyeablearmors.capability.packet.MDAPacketHandler;
import sinamegapolis.moredyeablearmors.capability.packet.MessageCamouflageColor;
import sinamegapolis.moredyeablearmors.init.ModRegistry;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new ModRegistry());
        MDAPacketHandler.INSTANCE.registerMessage(CMessageArmorColored.ArmorColoredMessageHandler.class, CMessageArmorColored.class, 1, Side.CLIENT);
        MDAPacketHandler.INSTANCE.registerMessage(MessageCamouflageColor.Handler.class, MessageCamouflageColor.class, 1, Side.SERVER);
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {

    }

    public void registerRenderers(){}
}
