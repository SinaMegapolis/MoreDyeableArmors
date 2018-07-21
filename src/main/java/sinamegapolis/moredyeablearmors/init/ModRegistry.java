package sinamegapolis.moredyeablearmors.init;

import com.google.common.collect.ImmutableMap;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.armors.ItemDyeableArmor;
import sinamegapolis.moredyeablearmors.capability.Capabilities;
import sinamegapolis.moredyeablearmors.capability.CapabilityProvider;
import sinamegapolis.moredyeablearmors.capability.DyeableCapability;
import sinamegapolis.moredyeablearmors.capability.IDyeable;
import sinamegapolis.moredyeablearmors.config.ModConfig;
import sinamegapolis.moredyeablearmors.model.ItemArmorWithOverlay;
import sinamegapolis.moredyeablearmors.texture.ItemArmorOverlayTextureHandler;
import sinamegapolis.moredyeablearmors.texture.layer.LayerArmorDyeableBase;
import sinamegapolis.moredyeablearmors.util.ColorArmorRecipe;
import sinamegapolis.moredyeablearmors.util.IntegrateInspirations;
import sinamegapolis.moredyeablearmors.util.IntegrationHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = MoreDyeableArmors.MODID)
public class ModRegistry {

    public static final List<Item> ITEMS = new ArrayList<>(16);
    public static Item GOLD_HELMET = new ItemDyeableArmor(ArmorMaterial.GOLD,EntityEquipmentSlot.HEAD,new ResourceLocation("minecraft","golden_helmet"));
    private static ArrayList<ItemArmorOverlayTextureHandler> textureHandlerList;
    public static ArrayList<ItemArmor> armorList = new ArrayList<>();

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
    }
    
    @SubscribeEvent
    public static void onRecipeRegister(RegistryEvent.Register<IRecipe> event) {
    	Ingredient string = Ingredient.fromItem(Items.STRING);
    	Ingredient leather = Ingredient.fromItems(Items.LEATHER);
    	Ingredient slime = Ingredient.fromItems(Items.SLIME_BALL);
    	Ingredient water = Ingredient.fromItems(Items.WATER_BUCKET);

        event.getRegistry().register(new ColorArmorRecipe().setRegistryName(MoreDyeableArmors.MODID, "armor_coloring"));

        if(Loader.isModLoaded(IntegrationHelper.INSPIRATIONS)) {
            if(!IntegrateInspirations.tryLoading())
                MoreDyeableArmors.LOGGER.warn("Inspirations is present but for some reason the mod can't integrate with it, please make a github issue with the full log if you want it to get fixed");
        }
    }

    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<ItemStack> event){
        ItemStack stack = event.getObject();
        if(stack.getItem() instanceof ItemArmor){
            event.addCapability(new ResourceLocation(MoreDyeableArmors.MODID, "dyeable"), new CapabilityProvider<>(Capabilities.DYEABLE));
        }
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event){
        registerArmorSetModel("minecraft", "diamond", event, new ItemArmor[]{Items.DIAMOND_HELMET,Items.DIAMOND_CHESTPLATE,Items.DIAMOND_LEGGINGS,Items.DIAMOND_BOOTS});
        registerArmorSetModel("minecraft", "chainmail", event, new ItemArmor[]{Items.CHAINMAIL_HELMET,Items.CHAINMAIL_CHESTPLATE,Items.CHAINMAIL_LEGGINGS,Items.CHAINMAIL_BOOTS});
        registerArmorSetModel("minecraft", "iron", event, new ItemArmor[]{Items.IRON_HELMET,Items.IRON_CHESTPLATE,Items.IRON_LEGGINGS,Items.IRON_BOOTS});

    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event){
        textureHandlerList = new ArrayList<>();
        textureHandlerList.add(new ItemArmorOverlayTextureHandler(0x33EBCB, event.getMap(), "diamond", "minecraft"));
        textureHandlerList.add(new ItemArmorOverlayTextureHandler(0xC6C6C6, event.getMap(), "chainmail", "minecraft"));
        textureHandlerList.add(new ItemArmorOverlayTextureHandler(0xEAEE57, event.getMap(), "gold", "minecraft"));
        textureHandlerList.add(new ItemArmorOverlayTextureHandler(0xC6C6C6, event.getMap(), "iron", "minecraft"));
    }

    public static ItemArmorOverlayTextureHandler getItemTextureHandler(String name){
        for (ItemArmorOverlayTextureHandler handler : textureHandlerList) {
            if(handler.getArmorName().equalsIgnoreCase(name))
                return handler;
        }
        return null;
    }

    private static void registerArmorSetModel(String modId,String armorName, ModelBakeEvent notActuallyAGoodParameter, ItemArmor[] armors){
        ModelResourceLocation modelLoc = new ModelResourceLocation(new ResourceLocation(modId,armorName+"_helmet"),"inventory");
        IBakedModel model = notActuallyAGoodParameter.getModelManager().getModel(modelLoc);
        ItemArmorWithOverlay overlay = new ItemArmorWithOverlay(model,armorName,EntityEquipmentSlot.HEAD);
        notActuallyAGoodParameter.getModelRegistry().putObject(modelLoc, overlay);
        armorList.add(armors[0]);

        modelLoc = new ModelResourceLocation(new ResourceLocation(modId,armorName+"_chestplate"),"inventory");
        model = notActuallyAGoodParameter.getModelManager().getModel(modelLoc);
        overlay = new ItemArmorWithOverlay(model,armorName,EntityEquipmentSlot.CHEST);
        notActuallyAGoodParameter.getModelRegistry().putObject(modelLoc, overlay);
        armorList.add(armors[1]);

        modelLoc = new ModelResourceLocation(new ResourceLocation(modId,armorName+"_leggings"),"inventory");
        model = notActuallyAGoodParameter.getModelManager().getModel(modelLoc);
        overlay = new ItemArmorWithOverlay(model,armorName,EntityEquipmentSlot.LEGS);
        notActuallyAGoodParameter.getModelRegistry().putObject(modelLoc, overlay);
        armorList.add(armors[2]);

        modelLoc = new ModelResourceLocation(new ResourceLocation(modId,armorName+"_boots"),"inventory");
        model = notActuallyAGoodParameter.getModelManager().getModel(modelLoc);
        overlay = new ItemArmorWithOverlay(model,armorName,EntityEquipmentSlot.FEET);
        notActuallyAGoodParameter.getModelRegistry().putObject(modelLoc, overlay);
        armorList.add(armors[3]);
    }
}
