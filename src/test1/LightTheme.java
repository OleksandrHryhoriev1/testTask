package test1;

import java.awt.Color;

public class LightTheme implements Theme {

    @Override
    public Color getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    public Color getBorderColor() {
        return Color.BLUE;
    }

    @Override
    public Color getHighlightColor() {
        return Color.GREEN;
    }

    @Override
    public Color getTextColor() {
        return Color.BLACK;
    }
}
