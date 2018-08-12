package sinamegapolis.moredyeablearmors.init;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mcmoddev.basemetals.init.Materials;
import com.mcmoddev.lib.client.registrations.RegistrationHelper;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.item.ItemMMDArmor;
import com.mcmoddev.lib.material.MMDMaterial;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.armors.ItemDyeableArmor;
import sinamegapolis.moredyeablearmors.capability.Capabilities;
import sinamegapolis.moredyeablearmors.capability.CapabilityProvider;
import sinamegapolis.moredyeablearmors.capability.IDyeable;
import sinamegapolis.moredyeablearmors.config.ModConfig;
import sinamegapolis.moredyeablearmors.model.ItemArmorWithOverlay;
import sinamegapolis.moredyeablearmors.texture.ItemArmorOverlayTextureHandler;
import sinamegapolis.moredyeablearmors.util.ColorArmorRecipe;
import sinamegapolis.moredyeablearmors.util.IntegrateInspirations;
import sinamegapolis.moredyeablearmors.util.IntegrationHelper;
import sinamegapolis.moredyeablearmors.util.MakeArmorDyeableRecipe;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = MoreDyeableArmors.MODID)
public class ModRegistry {

    public static final List<Item> ITEMS = new ArrayList<>(16);
    private static final ItemDyeableArmor GOLD_HELMET = new ItemDyeableArmor(ArmorMaterial.GOLD,EntityEquipmentSlot.HEAD,new ResourceLocation(MoreDyeableArmors.MODID,"dyeablegold_helmet"));
    private static final ItemDyeableArmor GOLD_CHESTPLATE = new ItemDyeableArmor(ArmorMaterial.GOLD,EntityEquipmentSlot.CHEST,new ResourceLocation(MoreDyeableArmors.MODID,"dyeablegold_chestplate"));
    private static final ItemDyeableArmor GOLD_LEGGINGS = new ItemDyeableArmor(ArmorMaterial.GOLD,EntityEquipmentSlot.LEGS,new ResourceLocation(MoreDyeableArmors.MODID,"dyeablegold_leggings"));
    private static final ItemDyeableArmor GOLD_BOOTS = new ItemDyeableArmor(ArmorMaterial.GOLD,EntityEquipmentSlot.FEET,new ResourceLocation(MoreDyeableArmors.MODID,"dyeablegold_boots"));
    private static ArrayList<ItemArmorOverlayTextureHandler> textureHandlerList;
    public static List<ItemArmor> armorList = Lists.newArrayList();
    public static ImmutableMap<ItemArmor,ItemDyeableArmor> armorMap = ImmutableMap.<ItemArmor, ItemDyeableArmor>builder()
            .put(Items.GOLDEN_HELMET,GOLD_HELMET)
            .put(Items.GOLDEN_CHESTPLATE,GOLD_CHESTPLATE)
            .put(Items.GOLDEN_LEGGINGS,GOLD_LEGGINGS)
            .put(Items.GOLDEN_BOOTS,GOLD_BOOTS)
            .build();

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
    }
    
    @SubscribeEvent
    public static void onRecipeRegister(RegistryEvent.Register<IRecipe> event) {
    	for(ImmutableMap.Entry<ItemArmor,ItemDyeableArmor> entry : armorMap.entrySet()) {
    	    ItemStack stack = new ItemStack(entry.getValue());
            if(!ModConfig.leathericArmor) {
                GameRegistry.addShapelessRecipe(new ResourceLocation(entry.getKey().getRegistryName()+"_convertion"),null ,stack,
                        Ingredient.fromItems(entry.getKey()));
            }
        }

        event.getRegistry().register(new ColorArmorRecipe().setRegistryName(MoreDyeableArmors.MODID, "armor_coloring"));
    	if(ModConfig.leathericArmor)
    	    event.getRegistry().register(new MakeArmorDyeableRecipe().setRegistryName(MoreDyeableArmors.MODID,"armor_making_dyeable"));

    	//not yet, see DyeableCapability for the reason behind this
    	//if(Loader.isModLoaded(IntegrationHelper.PRIMITIVE_MOBS))
    	    //event.getRegistry().register(new CamouflageDyeArmorRecipe().setRegistryName(MoreDyeableArmors.MODID, "armor_camouflage"));

        if(Loader.isModLoaded(IntegrationHelper.INSPIRATIONS)) {
            if(!IntegrateInspirations.tryLoading())
                MoreDyeableArmors.LOGGER.warn("Inspirations is present but for some reason the mod can't integrate with it, please make a github issue with the full log if you want it to get fixed");
        }
    }

    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<ItemStack> event){
        ItemStack stack = event.getObject();
        if(stack.getItem() instanceof ItemArmor){
            if(Capabilities.DYEABLE==null) Capabilities.register();
            event.addCapability(new ResourceLocation(MoreDyeableArmors.MODID, "dyeable"), new CapabilityProvider<>(Capabilities.DYEABLE));
        }
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event){
        registerArmorSetModel(IntegrationHelper.MINECRAFT_ID, "diamond", event, new ItemArmor[]{Items.DIAMOND_HELMET,Items.DIAMOND_CHESTPLATE,Items.DIAMOND_LEGGINGS,Items.DIAMOND_BOOTS},ArmorMaterial.DIAMOND);
        registerArmorSetModel(IntegrationHelper.MINECRAFT_ID, "chainmail", event, new ItemArmor[]{Items.CHAINMAIL_HELMET,Items.CHAINMAIL_CHESTPLATE,Items.CHAINMAIL_LEGGINGS,Items.CHAINMAIL_BOOTS},ArmorMaterial.CHAIN);
        registerArmorSetModel(IntegrationHelper.MINECRAFT_ID, "iron", event, new ItemArmor[]{Items.IRON_HELMET,Items.IRON_CHESTPLATE,Items.IRON_LEGGINGS,Items.IRON_BOOTS},ArmorMaterial.IRON);
        registerArmorSetModel(IntegrationHelper.MINECRAFT_ID, "gold", event, new ItemArmor[]{Items.GOLDEN_HELMET,Items.GOLDEN_CHESTPLATE,Items.GOLDEN_LEGGINGS,Items.GOLDEN_BOOTS},ArmorMaterial.GOLD);
        if(Loader.isModLoaded(IntegrationHelper.BASEMETALS)){
            com.mcmoddev.lib.init.Materials.getAllMaterials().forEach(mmdMaterial -> {
                if(!mmdMaterial.hasItem(Names.CHESTPLATE))
                    return;
                registerArmorSetModel(IntegrationHelper.BASEMETALS,
                    mmdMaterial.getName(),
                    event,
                    new ItemArmor[]{
                            (ItemArmor)mmdMaterial.getItem(Names.HELMET),
                            (ItemArmor)mmdMaterial.getItem(Names.CHESTPLATE),
                            (ItemArmor)mmdMaterial.getItem(Names.LEGGINGS),
                            (ItemArmor)mmdMaterial.getItem(Names.BOOTS)
                    }, com.mcmoddev.lib.init.Materials.getArmorMaterialFor(mmdMaterial));
            });
        }
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event){
        textureHandlerList = new ArrayList<>();
        textureHandlerList.add(new ItemArmorOverlayTextureHandler(0x33EBCB, event.getMap(), "diamond", IntegrationHelper.MINECRAFT_ID));
        textureHandlerList.add(new ItemArmorOverlayTextureHandler(0xC6C6C6, event.getMap(), "chainmail", IntegrationHelper.MINECRAFT_ID));
        textureHandlerList.add(new ItemArmorOverlayTextureHandler(0xEAEE57, event.getMap(), "gold", IntegrationHelper.MINECRAFT_ID));
        textureHandlerList.add(new ItemArmorOverlayTextureHandler(0xFFFFFF, event.getMap(), "iron", IntegrationHelper.MINECRAFT_ID));
        if(Loader.isModLoaded(IntegrationHelper.BASEMETALS)){
            com.mcmoddev.lib.init.Materials.getMaterialsByMod(IntegrationHelper.BASEMETALS).forEach(mmdMaterial -> {
                if(!mmdMaterial.hasItem(Names.CHESTPLATE)
                        || mmdMaterial == Materials.getMaterialByName("iron")
                        || mmdMaterial == Materials.getMaterialByName("diamond")
                        || mmdMaterial == Materials.getMaterialByName("gold"))
                    return;
                textureHandlerList.add(new ItemArmorOverlayTextureHandler(
                    mmdMaterial.getTintColor(), event.getMap(), mmdMaterial.getName(), IntegrationHelper.BASEMETALS
            ));}
            );
        }
    }

    public static ItemArmorOverlayTextureHandler getItemTextureHandler(String name){
        for (ItemArmorOverlayTextureHandler handler : textureHandlerList) {
            if(handler.getArmorName().equalsIgnoreCase(name))
                return handler;
        }
        return null;
    }

    private static void registerArmorSetModel(String modId,String armorName, ModelBakeEvent notActuallyAGoodParameter, ItemArmor[] armors, ArmorMaterial material){
        ModelResourceLocation modelLoc = new ModelResourceLocation(new ResourceLocation(modId,armorName+"_helmet"),"inventory");
        IBakedModel model = notActuallyAGoodParameter.getModelManager().getModel(modelLoc);
        ItemArmorWithOverlay overlay = new ItemArmorWithOverlay(model,armorName,EntityEquipmentSlot.HEAD,material);
        notActuallyAGoodParameter.getModelRegistry().putObject(modelLoc, overlay);
        armorList.add(armors[0]);

        modelLoc = new ModelResourceLocation(new ResourceLocation(modId,armorName+"_chestplate"),"inventory");
        model = notActuallyAGoodParameter.getModelManager().getModel(modelLoc);
        overlay = new ItemArmorWithOverlay(model,armorName,EntityEquipmentSlot.CHEST,material);
        notActuallyAGoodParameter.getModelRegistry().putObject(modelLoc, overlay);
        armorList.add(armors[1]);

        modelLoc = new ModelResourceLocation(new ResourceLocation(modId,armorName+"_leggings"),"inventory");
        model = notActuallyAGoodParameter.getModelManager().getModel(modelLoc);
        overlay = new ItemArmorWithOverlay(model,armorName,EntityEquipmentSlot.LEGS,material);
        notActuallyAGoodParameter.getModelRegistry().putObject(modelLoc, overlay);
        armorList.add(armors[2]);

        modelLoc = new ModelResourceLocation(new ResourceLocation(modId,armorName+"_boots"),"inventory");
        model = notActuallyAGoodParameter.getModelManager().getModel(modelLoc);
        overlay = new ItemArmorWithOverlay(model,armorName,EntityEquipmentSlot.FEET,material);
        notActuallyAGoodParameter.getModelRegistry().putObject(modelLoc, overlay);
        armorList.add(armors[3]);
    }

    /**
     *looks like it will not work if inspirations is present because that mod's code cancels the event
     * see IntegrateInspirations for that case
     */
    @SubscribeEvent
    public static void onCauldronRightClick(PlayerInteractEvent.RightClickBlock event){
        if(!Loader.isModLoaded(IntegrationHelper.INSPIRATIONS)) {
            IBlockState cauldron = event.getWorld().getBlockState(event.getPos());
            ItemStack armor = event.getItemStack();
            if (cauldron == Blocks.CAULDRON &&
                    armor.hasCapability(Capabilities.DYEABLE, null) &&
                    armor.getCapability(Capabilities.DYEABLE, null).getColor() != 0 &&
                    cauldron.getValue(BlockCauldron.LEVEL) > 0) {
                armor.getCapability(Capabilities.DYEABLE, null).setColor(0);
                if (ModConfig.leathericArmor)
                    armor.getCapability(Capabilities.DYEABLE, null).setDyeable(true);
            }
        }
    }

    @SubscribeEvent
    public static void onArmorTick(TickEvent.PlayerTickEvent event){
        event.player.getArmorInventoryList().forEach(armorStack ->{
            if(!(armorStack.getItem() instanceof ItemArmor) || Capabilities.DYEABLE==null)
                return;
            IDyeable armorCap = armorStack.getCapability(Capabilities.DYEABLE, null);
            if(armorCap.getArmorStack()==ItemStack.EMPTY)
                armorCap.setArmorStack(armorStack);
            armorCap.tickPlease(event.player);
        });
    }

    @SubscribeEvent
    public static void addColorToToolTip(ItemTooltipEvent event){
        ItemStack itemArmor = event.getItemStack();
        List<String> tooltipTexts = event.getToolTip();
        if(itemArmor.hasCapability(Capabilities.DYEABLE, null) && itemArmor.getCapability(Capabilities.DYEABLE, null).getColor()!=0){
            IDyeable armorCap = itemArmor.getCapability(Capabilities.DYEABLE, null);
            tooltipTexts.add(new TextComponentString("§aColor: ").getText() + Integer.toHexString(armorCap.getColor()));
        }
    }

}
