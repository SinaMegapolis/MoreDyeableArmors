package sinamegapolis.moredyeablearmors;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = MoreDyeableArmors.MODID, name = MoreDyeableArmors.NAME, version = MoreDyeableArmors.VERSION, updateJSON = "https://raw.githubusercontent.com/SinaMegapolis/MoreDyeableArmors/master/update-checker.json")
public class MoreDyeableArmors
{
    public static final String MODID = "moredyeablearmors";
    public static final String NAME = "More Dyeable Armors";
    public static final String VERSION = "1.12.2-1.2.1";

    public static final Logger LOGGER = LogManager.getLogger(MoreDyeableArmors.MODID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    }
}
