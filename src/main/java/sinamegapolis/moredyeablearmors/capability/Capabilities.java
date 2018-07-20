package sinamegapolis.moredyeablearmors.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;



public class Capabilities{

    @CapabilityInject(IDyeable.class)
    public static Capability<IDyeable> DYEABLE = null;

    public static void register(){
        CapabilityManager.INSTANCE.register(IDyeable.class, new DyeableStorage(), () -> new DyeableCapability(0xFBFBFB));
    }
}
