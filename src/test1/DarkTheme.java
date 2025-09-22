package test1;

import java.awt.Color;

public class DarkTheme implements Theme {

    @Override
    public Color getBackgroundColor() {
        return Color.DARK_GRAY;
    }

    @Override
    public Color getBorderColor() {
        return Color.GRAY;
    }

    @Override
    public Color getHighlightColor() {
        return Color.ORANGE;
    }

    @Override
    public Color getTextColor() {
        return Color.WHITE;
    }

}
