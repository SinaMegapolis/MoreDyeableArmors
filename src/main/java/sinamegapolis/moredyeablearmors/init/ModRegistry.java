package sinamegapolis.moredyeablearmors.init;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sinamegapolis.moredyeablearmors.MoreDyeableArmors;
import sinamegapolis.moredyeablearmors.armors.ItemDyeableArmor;

import java.util.ArrayList;
import java.util.List;

public class ModRegistry {
    public static final List<net.minecraft.block.Block> BLOCKS = new ArrayList<>();
    public static final List<Item> ITEMS = new ArrayList<>();
    /**
     * All values used to register Vanilla ItemDyeableArmor are extracted from ItemArmor.ArmorMaterial
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
     * Now actual ItemArmors
     */
    private static final ItemDyeableArmor chainHelmet = new ItemDyeableArmor(DyeableChainArmor, EntityEquipmentSlot.HEAD,"dyeablechainmail_helmet");
    private static final ItemDyeableArmor chainChestplate = new ItemDyeableArmor(DyeableChainArmor, EntityEquipmentSlot.CHEST,"dyeablechainmail_chestplate");
    private static final ItemDyeableArmor chainLeggings = new ItemDyeableArmor(DyeableChainArmor, EntityEquipmentSlot.LEGS, "dyeablechainmail_leggings");
    private static final ItemDyeableArmor chainBoots = new ItemDyeableArmor(DyeableChainArmor,EntityEquipmentSlot.FEET, "dyeablechainmail_boots");

    private static final ItemDyeableArmor ironHelmet = new ItemDyeableArmor(DyeableIronArmor, EntityEquipmentSlot.HEAD,"dyeableiron_helmet");
    private static final ItemDyeableArmor ironChestplate = new ItemDyeableArmor(DyeableIronArmor, EntityEquipmentSlot.CHEST,"dyeableiron_chestplate");
    private static final ItemDyeableArmor ironLeggings = new ItemDyeableArmor(DyeableIronArmor, EntityEquipmentSlot.LEGS, "dyeableiron_leggings");
    private static final ItemDyeableArmor ironBoots = new ItemDyeableArmor(DyeableIronArmor,EntityEquipmentSlot.FEET, "dyeableiron_boots");

    private static final ItemDyeableArmor diamondHelmet = new ItemDyeableArmor(DyeableDiamondArmor, EntityEquipmentSlot.HEAD,"dyeablediamond_helmet");
    private static final ItemDyeableArmor diamondChestplate = new ItemDyeableArmor(DyeableDiamondArmor, EntityEquipmentSlot.CHEST,"dyeablediamond_chestplate");
    private static final ItemDyeableArmor diamondLeggings = new ItemDyeableArmor(DyeableDiamondArmor, EntityEquipmentSlot.LEGS, "dyeablediamond_leggings");
    private static final ItemDyeableArmor diamondBoots = new ItemDyeableArmor(DyeableDiamondArmor,EntityEquipmentSlot.FEET, "dyeablediamond_boots");

    private static final ItemDyeableArmor goldHelmet = new ItemDyeableArmor(DyeableGoldArmor, EntityEquipmentSlot.HEAD,"dyeablegold_helmet");
    private static final ItemDyeableArmor goldChestplate = new ItemDyeableArmor(DyeableGoldArmor, EntityEquipmentSlot.CHEST,"dyeablegold_chestplate");
    private static final ItemDyeableArmor goldLeggings = new ItemDyeableArmor(DyeableGoldArmor, EntityEquipmentSlot.LEGS, "dyeablegold_leggings");
    private static final ItemDyeableArmor goldBoots = new ItemDyeableArmor(DyeableGoldArmor,EntityEquipmentSlot.FEET, "dyeablegold_boots");

    public static final ItemDyeableArmor[] listOfDyeableArmors = new ItemDyeableArmor[]{
            ironHelmet, ironChestplate, ironLeggings, ironBoots
            ,chainHelmet, chainChestplate, chainLeggings, chainBoots
            ,diamondHelmet, diamondChestplate, diamondLeggings, diamondBoots
            ,goldHelmet, goldChestplate, goldLeggings, goldBoots};
    public static final ItemArmor[] listOfArmors = new ItemArmor[]{
              Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS
            , Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS
            , Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS
            , Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS};

    @SubscribeEvent
    public void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
    }


    @SubscribeEvent
    public void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
        for(int i = 0; i<listOfArmors.length; i++) {
            ItemDyeableArmor armor = listOfDyeableArmors[i];
            ItemArmor armor1 = listOfArmors[i];
            GameRegistry.addShapelessRecipe(new ResourceLocation(armor.getRegistryName().toString() + "_recipe"), new ResourceLocation(""),
                    new ItemStack(armor, 1), Ingredient.fromStacks(new ItemStack(Items.STRING, 3)),
                    Ingredient.fromItems(Items.LEATHER),
                    Ingredient.fromItems(armor1),
                    Ingredient.fromItems(Items.SLIME_BALL));
        }
    }

    @SubscribeEvent
    public void colorItemArmors(ColorHandlerEvent.Item event){
        for (ItemDyeableArmor armor: listOfDyeableArmors) {
            event.getItemColors().registerItemColorHandler((stack, tintindex)->{
                if (armor.hasColor(stack) && tintindex==0)
                    return armor.getColor(stack);
                else if(tintindex==0)
                    return 10511680;
                return 0xFFFFFF;
            },armor);
        }
    }
}
