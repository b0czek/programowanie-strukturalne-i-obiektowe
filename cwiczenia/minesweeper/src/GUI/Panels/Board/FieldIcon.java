package GUI.Panels.Board;

import javax.swing.*;
import java.awt.*;

public class FieldIcon extends ImageIcon {
    private ImageIcon scaledIcon;

    public FieldIcon(Image image) {
        super(image);
    }

    public void scale(int size)  {
        this.scaledIcon = new ImageIcon(this.getImage().getScaledInstance(size, size, Image.SCALE_FAST));
    }


    public ImageIcon getScaledIcon() {
        if(scaledIcon == null) {
            return this;
        }
        return this.scaledIcon;
    }
}
