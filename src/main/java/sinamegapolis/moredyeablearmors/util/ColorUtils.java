package sinamegapolis.moredyeablearmors.util;

public class ColorUtils {
    public static int convertHexToInt(String hex){
        return Integer.parseInt(hex, 16);
    }
    public static String convertIntToHex(int color){
        return Integer.toHexString(color);
    }
    public static int combineColors(int color1, int color2, int scale) {
        if(scale == 0) {
            return color1;
        }
        int a = color1 >> 24 & 0xFF;
        int r = color1 >> 16 & 0xFF;
        int g = color1 >> 8 & 0xFF;
        int b = color1 & 0xFF;
        int a2 = color2 >> 24 & 0xFF;
        int r2 = color2 >> 16 & 0xFF;
        int g2 = color2 >> 8 & 0xFF;
        int b2 = color2 & 0xFF;

        for(int i = 0; i < scale; i++) {
            a = (int) Math.sqrt(a * a2);
            r = (int) Math.sqrt(r * r2);
            g = (int) Math.sqrt(g * g2);
            b = (int) Math.sqrt(b * b2);
        }
        return a << 24 | r << 16 | g << 8 | b;
    }
}
