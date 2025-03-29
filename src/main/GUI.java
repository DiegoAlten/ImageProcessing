package main;

import javax.swing.*;

public class GUI {
	public static void start() {
        SwingUtilities.invokeLater(() -> new ImageProcessingApp());
    }
}
