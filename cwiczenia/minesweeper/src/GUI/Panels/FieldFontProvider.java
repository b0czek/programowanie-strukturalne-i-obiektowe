package GUI.Panels;

import java.awt.*;

public class FieldFontProvider {
    private static Font font;

    public static void init(int size) {
        font = new Font("Arial", Font.BOLD, size);
    }

    public static Font getFont() {
        return font;
    }
    public static void changeFontSize(int size) {
        init(size);
    }

}
