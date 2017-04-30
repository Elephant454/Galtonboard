package edu.csuci.myci.sullivan971.matthew.galtonboard;

import java.awt.BorderLayout;
import javax.swing.*;

public class Main {
    public static void main (String[] args) {
        try {
            //System.out.println(UIManager.getSystemLookAndFeelClassName());
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
           // handle exception
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
           // handle exception
        }catch (InstantiationException e) {
            e.printStackTrace();
           // handle exception
        }catch (IllegalAccessException e) {
            e.printStackTrace();
           // handle exception
        }

        //Create the window
        JFrame window = new JFrame("galtonboard");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());

        // Create a panel for the game to run inside
        GraphicsPanel graphicspanel = new GraphicsPanel();
        window.add(graphicspanel, BorderLayout.CENTER);
        //JTextField input = new JTextField();
        //window.add(input);

        window.pack();
        window.setSize(800, 600);
        window.setVisible(true);
    }
}
