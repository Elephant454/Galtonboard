package edu.csuci.myci.sullivan971.matthew.galtonboard;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayDeque;

public class GraphicsPanel extends JPanel implements KeyListener, Runnable {
    private static final long serialVersionUID = 42L;

    boolean running = true;

    float oldTime = System.nanoTime();
    float currentTime = oldTime;
    float deltaTime = 0;
    float fps = 0;

    final int NANOSECONDS_PER_MILLISECOND = 1000000;
    final int MILLISECONDS_PER_SECOND = 1000;
    // there should be 1000 frames every 30 seconds, or 30 frames per second
    final double TARGET_FRAMERATE = 1.0/30 * MILLISECONDS_PER_SECOND;


    //RandomPalette randomPalette = new RandomPalette(0.5f, 0.6f);
    RandomPalette randomPalette = new RandomPalette(0.8f, 0.7f);

    ArrayDeque<Character> charQueue = new ArrayDeque<>();
    ArrayDeque<Color> colorQueue = new ArrayDeque<>();

    JLabel label = new JLabel();
    String labelText = "";

    int fontSize = 12;

    Thread drawingThread = new Thread(this);

    public GraphicsPanel () {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        this.add(label, gbc);

        colorQueue.add(Color.BLACK);

        setFocusable(true);
        addKeyListener(this);
        this.setSize(800, 600);
        this.requestFocusInWindow(true);
        drawingThread.start();
    }

    @Override
    public void paintComponent (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.clearRect(0, 0, getWidth(), getHeight());

        g2.setFont(new Font("Consolas", Font.PLAIN, fontSize));
    }

    long sleepLength;
    public void run() {
        while(running) {
            currentTime = System.nanoTime();

            // do the actual logic for this tick
            parseInput();
            repaint();

            // use the difference in time to determine how long we should sleep
            // for this tick
            deltaTime = currentTime-oldTime;
            oldTime = currentTime;
            fps = 1/deltaTime;
            sleepLength = (long) TARGET_FRAMERATE
                    - (long) (deltaTime/NANOSECONDS_PER_MILLISECOND);
            try {
                if(sleepLength > -1) drawingThread.sleep(sleepLength);
                else drawingThread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    boolean quoteExists = false;
    public void parseInput() {
        while(!charQueue.isEmpty()) {
            char charToParse = charQueue.pop();
            switch(charToParse) {
                case '(':
                case '{':
                case '[':
                    addOpening(charToParse);
                    break;
                case ')':
                case '}':
                case ']':
                    addClosing(charToParse);
                    break;

                case '\"':
                    if(!quoteExists){
                        quoteExists = true;
                        addOpening(charToParse);
                    }else {
                        quoteExists = false;
                        addClosing(charToParse);
                    }
                    break;


                case '\b':
                    labelText = labelText.substring(0, labelText.length()-1);
                    break;
                case '\n':
                    labelText += "<br>";
                    break;
                case '\t':
                    // tabs are broken at the moment
                    labelText += "&nbsp;&nbsp;&nbsp;&nbsp;";
                    break;

                default:
                    labelText += charToParse;
                    break;
            }
            label.setText("<html><font>" + labelText + "</html>");
        }
    }

    public void addOpening(char charToParse) {
        colorQueue.push(randomPalette.next());
        labelText += "</font>" + charToParse + "<font color=\"rgb("
                + colorQueue.peek().getRed() + ","
                + colorQueue.peek().getBlue() + ","
                + colorQueue.peek().getGreen() + ")\">";
    }

    public void addClosing(char charToParse) {
        colorQueue.pop();
        labelText += "</font>" + charToParse + "<font color=\"rgb("
                + colorQueue.peek().getRed() + ","
                + colorQueue.peek().getBlue() + ","
                + colorQueue.peek().getGreen() + ")\">";
    }

    public void keyTyped(KeyEvent e){
        charQueue.push(e.getKeyChar());
        System.out.println(labelText);
    }
    public void keyPressed(KeyEvent e){}
    public void keyReleased(KeyEvent e){}
}
