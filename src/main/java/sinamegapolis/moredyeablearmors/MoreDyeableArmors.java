package sinamegapolis.moredyeablearmors;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinamegapolis.moredyeablearmors.capability.packet.CMessageArmorColored;
import sinamegapolis.moredyeablearmors.capability.packet.MDAPacketHandler;

@Mod(modid = MoreDyeableArmors.MODID, name = MoreDyeableArmors.NAME, version = MoreDyeableArmors.VERSION, updateJSON = "https://raw.githubusercontent.com/SinaMegapolis/MoreDyeableArmors/master/update-checker.json")
public class MoreDyeableArmors
{
    public static final String MODID = "moredyeablearmors";
    public static final String NAME = "More Dyeable Armors";
    public static final String VERSION = "1.12.2-1.3.0";

    public static final Logger LOGGER = LogManager.getLogger(MoreDyeableArmors.MODID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        MDAPacketHandler.INSTANCE.registerMessage(CMessageArmorColored.ArmorColoredMessageHandler.class, CMessageArmorColored.class, 1, Side.CLIENT);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    }
}
