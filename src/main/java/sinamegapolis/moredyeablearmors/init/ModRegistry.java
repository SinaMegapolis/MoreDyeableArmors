package sinamegapolis.moredyeablearmors.init;

import com.google.common.collect.ImmutableMap;
import knightminer.inspirations.library.InspirationsRegistry;
import knightminer.inspirations.library.recipe.cauldron.ICauldronRecipe;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.armors.ItemDyeableArmor;
import sinamegapolis.moredyeablearmors.config.ModConfig;
import sinamegapolis.moredyeablearmors.util.Integrations;
import sinamegapolis.moredyeablearmors.util.Utils;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = MoreDyeableArmors.MODID)
public class ModRegistry {
    public static final List<Item> ITEMS = new ArrayList<>();
    /**
     * All values used to register Vanilla ArmorMaterials are extracted from ItemArmor.ArmorMaterial
     */
    private static final ItemDyeableArmor.ArmorMaterial DyeableDiamondArmor = EnumHelper.addArmorMaterial("DYEABLEDIAMOND",
            MoreDyeableArmors.MODID+":diamond", 33, new int[]{3, 6, 8, 3},
            10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,2.0f);
    private static final ItemDyeableArmor.ArmorMaterial DyeableChainArmor = EnumHelper.addArmorMaterial("DYEABLECHAIN",
            MoreDyeableArmors.MODID+":chainmail", 15, new int[]{1, 4, 5, 2},
            12, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,0.0f);
    private static final ItemDyeableArmor.ArmorMaterial DyeableIronArmor = EnumHelper.addArmorMaterial("DYEABLEIRON",
            MoreDyeableArmors.MODID+":iron", 15, new int[]{2, 5, 6, 2},
            9, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0.0f);
    private static final ItemDyeableArmor.ArmorMaterial DyeableGoldArmor = EnumHelper.addArmorMaterial("DYEABLEGOLD",
            MoreDyeableArmors.MODID+":gold", 7, new int[]{1, 3, 5, 2},
            25, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,0.0f);
    /**
     * These are "Leather-ic" versions of ArmorMaterials, enabled by "leathericArmor" config option
     */
    private static final ItemDyeableArmor.ArmorMaterial DyeableLeathericDiamondArmor = EnumHelper.addArmorMaterial("DYEABLELEATHERICDIAMOND",
            MoreDyeableArmors.MODID+":leatheric_diamond", 33, new int[]{3, 6, 8, 3},
            10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,2.0f);
    private static final ItemDyeableArmor.ArmorMaterial DyeableLeathericChainArmor = EnumHelper.addArmorMaterial("DYEABLELEATHERICCHAIN",
            MoreDyeableArmors.MODID+":leatheric_chainmail", 15, new int[]{1, 4, 5, 2},
            12, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,0.0f);
    private static final ItemDyeableArmor.ArmorMaterial DyeableLeathericIronArmor = EnumHelper.addArmorMaterial("DYEABLELEATHERICIRON",
            MoreDyeableArmors.MODID+":leatheric_iron", 15, new int[]{2, 5, 6, 2},
            9, SoundEvents.ITEM_ARMOR_EQUIP_IRON,0.0f);
    private static final ItemDyeableArmor.ArmorMaterial DyeableLeathericGoldArmor = EnumHelper.addArmorMaterial("DYEABLELEATHERICGOLD",
            MoreDyeableArmors.MODID+":leatheric_gold", 7, new int[]{1, 3, 5, 2},
            25, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,0.0f);
    /**
     * Now actual ItemArmors
     * these are set according to config file using Utils#getItemDyeableArmorBasedOnConfig
     */
    private static final ItemDyeableArmor chainHelmet = Utils.getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot.HEAD, DyeableChainArmor, DyeableLeathericChainArmor, "dyeablechainmail_helmet");
    private static final ItemDyeableArmor chainChestplate = Utils.getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot.CHEST, DyeableChainArmor, DyeableLeathericChainArmor, "dyeablechainmail_chestplate");
    private static final ItemDyeableArmor chainLeggings = Utils.getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot.LEGS, DyeableChainArmor, DyeableLeathericChainArmor, "dyeablechainmail_leggings");
    private static final ItemDyeableArmor chainBoots = Utils.getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot.FEET, DyeableChainArmor, DyeableLeathericChainArmor, "dyeablechainmail_boots");

    private static final ItemDyeableArmor ironHelmet = Utils.getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot.HEAD, DyeableIronArmor, DyeableLeathericIronArmor, "dyeableiron_helmet");
    private static final ItemDyeableArmor ironChestplate = Utils.getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot.CHEST, DyeableIronArmor, DyeableLeathericIronArmor, "dyeableiron_chestplate");
    private static final ItemDyeableArmor ironLeggings = Utils.getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot.LEGS, DyeableIronArmor, DyeableLeathericIronArmor, "dyeableiron_leggings");
    private static final ItemDyeableArmor ironBoots = Utils.getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot.FEET, DyeableIronArmor, DyeableLeathericIronArmor, "dyeableiron_boots");

    private static final ItemDyeableArmor diamondHelmet = Utils.getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot.HEAD, DyeableDiamondArmor, DyeableLeathericDiamondArmor, "dyeablediamond_helmet");
    private static final ItemDyeableArmor diamondChestplate = Utils.getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot.CHEST, DyeableDiamondArmor, DyeableLeathericDiamondArmor, "dyeablediamond_chestplate");
    private static final ItemDyeableArmor diamondLeggings = Utils.getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot.LEGS, DyeableDiamondArmor, DyeableLeathericDiamondArmor, "dyeablediamond_leggings");
    private static final ItemDyeableArmor diamondBoots = Utils.getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot.FEET, DyeableDiamondArmor, DyeableLeathericDiamondArmor, "dyeablediamond_boots");

    private static final ItemDyeableArmor goldHelmet = Utils.getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot.HEAD, DyeableGoldArmor, DyeableLeathericGoldArmor, "dyeablegold_helmet");
    private static final ItemDyeableArmor goldChestplate = Utils.getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot.CHEST, DyeableGoldArmor, DyeableLeathericGoldArmor, "dyeablegold_chestplate");
    private static final ItemDyeableArmor goldLeggings = Utils.getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot.LEGS, DyeableGoldArmor, DyeableLeathericGoldArmor, "dyeablegold_leggings");
    private static final ItemDyeableArmor goldBoots = Utils.getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot.FEET, DyeableGoldArmor, DyeableLeathericGoldArmor, "dyeablegold_boots");

     private static final ImmutableMap<ItemArmor, ItemDyeableArmor> armorsMap = ImmutableMap.<ItemArmor,ItemDyeableArmor>builder().
             put(Items.IRON_BOOTS,ironBoots)
             .put(Items.IRON_CHESTPLATE,ironChestplate)
             .put(Items.IRON_HELMET,ironHelmet)
             .put(Items.IRON_LEGGINGS,ironLeggings)
             .put(Items.CHAINMAIL_BOOTS,chainBoots)
             .put(Items.CHAINMAIL_CHESTPLATE,chainChestplate)
             .put(Items.CHAINMAIL_HELMET,chainHelmet)
             .put(Items.CHAINMAIL_LEGGINGS,chainLeggings)
             .put(Items.GOLDEN_BOOTS,goldBoots)
             .put(Items.GOLDEN_CHESTPLATE,goldChestplate)
             .put(Items.GOLDEN_HELMET,goldHelmet)
             .put(Items.GOLDEN_LEGGINGS,goldLeggings)
             .put(Items.DIAMOND_BOOTS,diamondBoots)
             .put(Items.DIAMOND_CHESTPLATE,diamondChestplate)
             .put(Items.DIAMOND_LEGGINGS,diamondLeggings)
             .put(Items.DIAMOND_HELMET,diamondHelmet).build();

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
        armorsMap.forEach((itemArmor, itemDyeableArmor) -> {
            if(ModConfig.leathericArmor) {
                GameRegistry.addShapelessRecipe(new ResourceLocation(itemDyeableArmor.getRegistryName().toString() + "_recipe"), new ResourceLocation(""),
                        new ItemStack(itemDyeableArmor, 1), Ingredient.fromStacks(new ItemStack(Items.STRING, 3)),
                        Ingredient.fromItems(Items.LEATHER),
                        Ingredient.fromItems(itemArmor),
                        Ingredient.fromItems(Items.SLIME_BALL));
            }else if(!ModConfig.leathericArmor){
                GameRegistry.addShapelessRecipe(new ResourceLocation(itemDyeableArmor.getRegistryName().toString()+"_recipe"), new ResourceLocation(""),
                        new ItemStack(itemDyeableArmor, 1),Ingredient.fromItems(itemArmor));
            }
        });
    }

    @SubscribeEvent
    public static void colorItemArmors(ColorHandlerEvent.Item event){
        armorsMap.forEach((itemArmor, itemDyeableArmor)-> event.getItemColors().registerItemColorHandler((itemStack, tintIndex)->{
            if(tintIndex==0){
                if(itemDyeableArmor.hasColor(itemStack))
                    return itemDyeableArmor.getColor(itemStack);
                else
                    return itemDyeableArmor.getColorBasedOnType();
            }
            return 0xFFFFFF;
        },itemDyeableArmor));
    }

    @SubscribeEvent
    public static void inspirationsIntegration(RegistryEvent.Register<IRecipe> event){
        if(Loader.isModLoaded("inspirations")) {
            if(!Integrations.integrateWithInspirations())
                MoreDyeableArmors.logger.warn("Inspirations is present but for some reason the mod can't integrate with it, please make an issue in github with full log if you want it to get fixed");
        }
    }
}
