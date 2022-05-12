package de.muc.vaulty.view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

public class MVC {

public static void createAndShowUI() {

	// model, view & control
    Model model = new Model();
    View view = new View(model);
    Control control = new Control(model);
    view.setGuiControl(control);

    // menu
    Menu menu = new Menu(control);

    // frame
    JFrame frame = new JFrame("Blockchain Transaction");
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(view.getMainPanel());
    frame.setJMenuBar(menu.getMenuBar());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setPreferredSize(new Dimension(850, 700));
	frame.setResizable(false);
	frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

 }

 // call Swing code in a thread-safe manner per the tutorials

 
}