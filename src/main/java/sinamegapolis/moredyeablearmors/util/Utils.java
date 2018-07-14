package sinamegapolis.moredyeablearmors.util;

import net.minecraft.inventory.EntityEquipmentSlot;
import sinamegapolis.moredyeablearmors.armors.ItemDyeableArmor;
import sinamegapolis.moredyeablearmors.config.ModConfig;

import java.awt.*;

public class Utils {
    public static int combineColors(int color1, int color2, int scale) {
        if(scale == 0) {
            return color1;
        }

        int[] argbColor1 = getARGBArrayFromInt(color1);
        int[] argbColor2 = getARGBArrayFromInt(color2);

        for(int i = 0; i < scale; i++) {
            argbColor1[0] = (int) Math.sqrt(argbColor1[0] * argbColor2[0]);
            argbColor1[1] = (int) Math.sqrt(argbColor1[1] * argbColor2[1]);
            argbColor1[2] = (int) Math.sqrt(argbColor1[2] * argbColor2[2]);
            argbColor1[3] = (int) Math.sqrt(argbColor1[3] * argbColor2[3]);
        }
        return getIntFromARGBArray(argbColor1);
    }
    public static int[] getARGBArrayFromInt(int color){
        return new int[]{
                color >> 24 & 0xFF,
                color >> 16 & 0xFF,
                color >> 8 & 0xFF,
                color & 0xFF};
    }

    public static int addHueDegreesToColor(int color, float hueDegree){
        float hue = hueDegree/360.0f;
        float[] hueSaturationBrightnessVals= new float[3];
        int[] argb = getARGBArrayFromInt(color);
        Color.RGBtoHSB(argb[1], argb[2], argb[3], hueSaturationBrightnessVals);
        if(hueSaturationBrightnessVals[1]==0)
            return Color.getHSBColor(hueSaturationBrightnessVals[0]+hue, hueSaturationBrightnessVals[1]+20, hueSaturationBrightnessVals[2]).getRGB();
        return Color.getHSBColor(hueSaturationBrightnessVals[0]+hue, hueSaturationBrightnessVals[1], hueSaturationBrightnessVals[2]).getRGB();
    }

    public static int changeHue(int color, float hueDegree){
        float hue = hueDegree/360.0f;
        float[] hueSaturationBrightnessVals= new float[3];
        int[] argb = getARGBArrayFromInt(color);
        Color.RGBtoHSB(argb[1], argb[2], argb[3], hueSaturationBrightnessVals);
        return Color.getHSBColor(hue, hueSaturationBrightnessVals[1], hueSaturationBrightnessVals[2]).getRGB();
    }

    public static int getIntFromARGBArray(int[] argb){
        return argb[0] << 24 | argb[1] << 16 | argb[2] << 8 | argb[3];
    }
    public static ItemDyeableArmor getItemDyeableArmorBasedOnConfig(EntityEquipmentSlot slot, ItemDyeableArmor.ArmorMaterial normalArmorMaterial,
                                                                    ItemDyeableArmor.ArmorMaterial leathericArmorMaterial, String name){
        if(ModConfig.leathericArmor && leathericArmorMaterial!=null){
            return new ItemDyeableArmor(leathericArmorMaterial,slot,name+"_leatheric");
        }else if(!ModConfig.leathericArmor && normalArmorMaterial!=null){
            return new ItemDyeableArmor(normalArmorMaterial,slot,name);
        }
        return null;
    }
}
