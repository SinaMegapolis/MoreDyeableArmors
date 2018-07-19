package sinamegapolis.moredyeablearmors.init;

import com.google.common.collect.ImmutableMap;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelBakery;
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
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.armors.ItemDyeableArmor;
import sinamegapolis.moredyeablearmors.config.ModConfig;
import sinamegapolis.moredyeablearmors.util.ColorArmorRecipe;
import sinamegapolis.moredyeablearmors.util.IntegrateInspirations;
import sinamegapolis.moredyeablearmors.util.IntegrationHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = MoreDyeableArmors.MODID)
public class ModRegistry {

    public static final List<Item> ITEMS = new ArrayList<>(16);

    public static final ItemDyeableArmor CHAIN_HELMET = new ItemDyeableArmor(ArmorMaterial.CHAIN, EntityEquipmentSlot.HEAD, "dyeablechainmail_helmet");
    public static final ItemDyeableArmor CHAIN_CHESTPLATE = new ItemDyeableArmor(ArmorMaterial.CHAIN, EntityEquipmentSlot.CHEST, "dyeablechainmail_chestplate");
    public static final ItemDyeableArmor CHAIN_LEGGINGS = new ItemDyeableArmor(ArmorMaterial.CHAIN, EntityEquipmentSlot.LEGS, "dyeablechainmail_leggings");
    public static final ItemDyeableArmor CHAIN_BOOTS = new ItemDyeableArmor(ArmorMaterial.CHAIN, EntityEquipmentSlot.FEET, "dyeablechainmail_boots");

    public static final ItemDyeableArmor IRON_HELMET = new ItemDyeableArmor(ArmorMaterial.IRON, EntityEquipmentSlot.HEAD, "dyeableiron_helmet");
    public static final ItemDyeableArmor IRON_CHESTPLATE = new ItemDyeableArmor(ArmorMaterial.IRON, EntityEquipmentSlot.CHEST, "dyeableiron_chestplate");
    public static final ItemDyeableArmor IRON_LEGGINGS = new ItemDyeableArmor(ArmorMaterial.IRON, EntityEquipmentSlot.LEGS, "dyeableiron_leggings");
    public static final ItemDyeableArmor IRON_BOOTS = new ItemDyeableArmor(ArmorMaterial.IRON, EntityEquipmentSlot.FEET, "dyeableiron_boots");
    
    public static final ItemDyeableArmor GOLD_HELMET = new ItemDyeableArmor(ArmorMaterial.GOLD, EntityEquipmentSlot.HEAD, "dyeablegold_helmet");
    public static final ItemDyeableArmor GOLD_CHESTPLATE = new ItemDyeableArmor(ArmorMaterial.GOLD, EntityEquipmentSlot.CHEST, "dyeablegold_chestplate");
    public static final ItemDyeableArmor GOLD_LEGGINGS = new ItemDyeableArmor(ArmorMaterial.GOLD, EntityEquipmentSlot.LEGS, "dyeablegold_leggings");
    public static final ItemDyeableArmor GOLD_BOOTS = new ItemDyeableArmor(ArmorMaterial.GOLD, EntityEquipmentSlot.FEET, "dyeablegold_boots");

    public static final ItemDyeableArmor DIAMOND_HELMET = new ItemDyeableArmor(ArmorMaterial.DIAMOND, EntityEquipmentSlot.HEAD,  "dyeablediamond_helmet");
    public static final ItemDyeableArmor DIAMOND_CHESTPLATE = new ItemDyeableArmor(ArmorMaterial.DIAMOND, EntityEquipmentSlot.CHEST, "dyeablediamond_chestplate");
    public static final ItemDyeableArmor DIAMOND_LEGGINGS = new ItemDyeableArmor(ArmorMaterial.DIAMOND, EntityEquipmentSlot.LEGS, "dyeablediamond_leggings");
    public static final ItemDyeableArmor DIAMOND_BOOTS = new ItemDyeableArmor(ArmorMaterial.DIAMOND, EntityEquipmentSlot.FEET, "dyeablediamond_boots");

    public static final ImmutableMap<ItemArmor, ItemDyeableArmor> ARMOR_MAP = new ImmutableMap.Builder<ItemArmor, ItemDyeableArmor>()
             .put(Items.CHAINMAIL_HELMET, CHAIN_HELMET)
             .put(Items.CHAINMAIL_CHESTPLATE, CHAIN_CHESTPLATE)
             .put(Items.CHAINMAIL_LEGGINGS, CHAIN_LEGGINGS)
             .put(Items.CHAINMAIL_BOOTS, CHAIN_BOOTS)
    		 .put(Items.IRON_HELMET, IRON_HELMET)
             .put(Items.IRON_CHESTPLATE, IRON_CHESTPLATE)
             .put(Items.IRON_LEGGINGS, IRON_LEGGINGS)
             .put(Items.IRON_BOOTS, IRON_BOOTS)
             .put(Items.GOLDEN_HELMET, GOLD_HELMET)
             .put(Items.GOLDEN_CHESTPLATE, GOLD_CHESTPLATE)
             .put(Items.GOLDEN_LEGGINGS, GOLD_LEGGINGS)
             .put(Items.GOLDEN_BOOTS, GOLD_BOOTS)
             .put(Items.DIAMOND_HELMET, DIAMOND_HELMET)
             .put(Items.DIAMOND_CHESTPLATE, DIAMOND_CHESTPLATE)
             .put(Items.DIAMOND_LEGGINGS, DIAMOND_LEGGINGS)
             .put(Items.DIAMOND_BOOTS, DIAMOND_BOOTS)
             .build();

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

       	for(Map.Entry<ItemArmor, ItemDyeableArmor> entry : ARMOR_MAP.entrySet()) {
       		ItemArmor base = entry.getKey();
       		ItemDyeableArmor armor = entry.getValue();
            if(ModConfig.leathericArmor) 
                GameRegistry.addShapelessRecipe(armor.getRegistryName(), null, new ItemStack(armor), string, leather, Ingredient.fromItems(base), slime);
            else
                GameRegistry.addShapelessRecipe(armor.getRegistryName(), null, new ItemStack(armor), Ingredient.fromItems(base));
            GameRegistry.addShapelessRecipe(new ResourceLocation(armor.getRegistryName()+"_clean"), null, new ItemStack(armor),water,Ingredient.fromItems(armor));

            if(Loader.isModLoaded(IntegrationHelper.PRIMITIVE_MOBS)){
                GameRegistry.addShapelessRecipe(new ResourceLocation(armor.getRegistryName().toString() + "_camouflage"), null,
                        armor.setRainbow(true, new ItemStack(armor)),
                        Ingredient.fromItems(PrimitiveMobsItems.CAMOUFLAGE_DYE),
                        Ingredient.fromItems(armor));
            }
        }

        event.getRegistry().register(new ColorArmorRecipe().setRegistryName(MoreDyeableArmors.MODID, "armor_coloring"));

        if(Loader.isModLoaded(IntegrationHelper.INSPIRATIONS)) {
            if(!IntegrateInspirations.tryLoading())
                MoreDyeableArmors.LOGGER.warn("Inspirations is present but for some reason the mod can't integrate with it, please make a github issue with the full log if you want it to get fixed");
        }
    }

    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<ItemStack> event){

    }
}
