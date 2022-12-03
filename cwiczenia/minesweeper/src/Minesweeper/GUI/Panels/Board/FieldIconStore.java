package Minesweeper.GUI.Panels.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class FieldIconStore extends HashMap<String, FieldIcon> {

    private static FieldIconStore store;

    public static void init() throws IOException {
        FieldIconStore.store = new FieldIconStore();
        // get all enum names
        String[] icons = Arrays.stream(FieldIcons.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        for (String icon:icons) {
            String path = String.format("assets/%s.png", icon);
            Image image = ImageIO.read(new File(path));
            FieldIconStore.addIcon(icon, image);
        }

    }

    public static void addIcon(String name, Image image) {
        FieldIconStore.store.put(name, new FieldIcon(image));
    }

    public static FieldIcon getIcon(String name) {
        return FieldIconStore.store.get(name);
    }

    public static void scaleAll(int size) {
        FieldIconStore.store.forEach((name, fieldIcon) -> fieldIcon.scale(size));
    }

}
