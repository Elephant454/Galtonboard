package edu.csuci.myci.sullivan971.matthew.galtonboard;

import java.awt.BorderLayout;
import javax.swing.*;

public class Main {
    public static void main (String[] args) {
        // createGraphics();

        // create a new Galtonboard with 4 bins
        Galtonboard galtonboard = new Galtonboard(4);
        System.out.println(parseBracketedToString(galtonboard.toString()));
        //System.out.println(galtonboard.toString());

        galtonboard.dropABall();
        System.out.println(galtonboard.toString());
    }

    public static String parseBracketedToString(String str) {
        StringBuilder result = new StringBuilder();
        int level = -1;

        for(int i=0; i<str.length(); i++) {
            switch(str.charAt(i)) {
            case '[':
                for(int j=0; j<level; j++) result.append("    ");
                level++;
                System.out.print(level);
                break;
            case ']':
                level--;
                result.append("\n");
                System.out.print(level);
                //for(int j=0; j<level; j++) result.append("    ");
                break;
            case ':':
                //case ',':
                result.append("\n");
                for(int j=0; j<level; j++) result.append("    ");
                break;
            default:
                //for(int j=0; j<level; j++) result.append("    ");
                result.append(str.charAt(i));
            }
        }

        return result.toString();
    }

    public static void createGraphics() {
        // the graphics are from a previous project and currently don't do
        //  anything relevant
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
