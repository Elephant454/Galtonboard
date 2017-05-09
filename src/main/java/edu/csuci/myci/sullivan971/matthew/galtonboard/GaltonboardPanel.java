package edu.csuci.myci.sullivan971.matthew.galtonboard;

import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class GaltonboardPanel extends JPanel implements KeyListener, Runnable {
    private int binWidth = 10;
    private int binHeight = 50;

    private int pegRadius = 5;
    private int pegDeltaX = 20;
    private int pegDeltaY = 20;

    private int ballRadius = 10;

    private Galtonboard galtonboard;

    // start of instance variables that I hastily copied over
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

    Thread drawingThread = new Thread(this);

    public GaltonboardPanel(Galtonboard galtonboard) {
        this.galtonboard = galtonboard;

        // start of parts of the constructor that I hastily copied over
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;

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

        // numberOfBins - 1 is the number of divisions between bins
        //int xDelta = this.getWidth() / (galtonboard.getNumberOfBins() - 1);
        int xDelta = this.getWidth() / (galtonboard.getNumberOfBins());
        for(int i=0; i<galtonboard.getNumberOfBins(); i++) {
            //System.out.println(i + "," + i*xDelta);
            g2.drawLine(i*xDelta, getHeight(), i*xDelta, getHeight() - binHeight);
        }
    }

    long sleepLength;
    public void run() {
        while(running) {
            currentTime = System.nanoTime();

            // do the actual logic for this tick
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

    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){}
    public void keyReleased(KeyEvent e){}
}
