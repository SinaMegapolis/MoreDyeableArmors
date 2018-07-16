package sinamegapolis.moredyeablearmors.util;

import java.awt.Color;
import java.util.EnumMap;

import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

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
    
    static EnumMap<EnumDyeColor, Integer> colors = new EnumMap<>(EnumDyeColor.class);
    static {
		for(EnumDyeColor e : EnumDyeColor.values())
			colors.put(e, ReflectionHelper.getPrivateValue(EnumDyeColor.class, e, "field_193351_w", "colorValue"));
    }
    
    public static int getColorFromColor(EnumDyeColor color) {
    	return colors.get(color);
    }
}
