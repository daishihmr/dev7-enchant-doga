package jp.dev7.enchant.doga.parser.data;

public class Color {

    public double red;
    public double green;
    public double blue;

    public Color() {
    }

    public Color(double col) {
        this.red = col;
        this.green = col;
        this.blue = col;
    }

    public Color(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public double getRed() {
        return red;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

    @Override
    public String toString() {
        return "Color [red=" + red + ", green=" + green + ", blue=" + blue
                + "]";
    }

}
