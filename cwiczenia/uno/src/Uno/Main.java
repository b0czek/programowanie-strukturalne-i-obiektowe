package Uno;

import Uno.GUI.GUI;
import Uno.Network.Server.Game.GameServer;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(() -> new GUI());
    }

}
