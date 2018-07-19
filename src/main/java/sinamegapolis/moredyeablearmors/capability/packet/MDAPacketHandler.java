package sinamegapolis.moredyeablearmors.capability.packet;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;

public class MDAPacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MoreDyeableArmors.MODID);
}
