package ch.dkrieger.friendsystem.spigot.api.inventory;

import org.apache.commons.lang.Validate;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 15:28
 *
 */

public class Color {

    public static final Color WHITE = fromRGB(16777215);
    public static final Color SILVER = fromRGB(12632256);
    public static final Color GRAY = fromRGB(8421504);
    public static final Color BLACK = fromRGB(0);
    public static final Color RED = fromRGB(16711680);
    public static final Color MAROON = fromRGB(8388608);
    public static final Color YELLOW = fromRGB(16776960);
    public static final Color OLIVE = fromRGB(8421376);
    public static final Color LIME = fromRGB(65280);
    public static final Color GREEN = fromRGB(32768);
    public static final Color AQUA = fromRGB(65535);
    public static final Color TEAL = fromRGB(32896);
    public static final Color BLUE = fromRGB(255);
    public static final Color NAVY = fromRGB(128);
    public static final Color FUCHSIA = fromRGB(16711935);
    public static final Color PURPLE = fromRGB(8388736);
    public static final Color ORANGE = fromRGB(16753920);

    private int red, green, blue;

    public Color(int red, int green, int blue) {
        Validate.isTrue(red >= 0 && red <= 255, "Red is not between 0-255: ", (long)red);
        Validate.isTrue(green >= 0 && green <= 255, "Green is not between 0-255: ", (long)green);
        Validate.isTrue(blue >= 0 && blue <= 255, "Blue is not between 0-255: ", (long)blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public static Color fromRGB(int red, int green, int blue) throws IllegalArgumentException {
        return new Color(red, green, blue);
    }

    public static Color fromRGB(int rgb) throws IllegalArgumentException {
        Validate.isTrue(rgb >> 24 == 0, "Extrenuous data in: ", (long)rgb);
        return fromRGB(rgb >> 16 & 255, rgb >> 8 & 255, rgb >> 0 & 255);
    }

}