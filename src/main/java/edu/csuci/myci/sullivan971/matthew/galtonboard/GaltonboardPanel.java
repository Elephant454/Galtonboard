package edu.csuci.myci.sullivan971.matthew.galtonboard;

import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class GaltonboardPanel extends JPanel implements KeyListener, Runnable {

    private int scalar = 1;

    private Galtonboard galtonboard;
    private BallPosition[][] layers;

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
        this.layers = galtonboard.getLayers();

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
        drawBoard(g);

        /**
         // numberOfBins - 1 is the number of divisions between bins
         //int xDelta = this.getWidth() / (galtonboard.getNumberOfBins() - 1);
         int xDelta = this.getWidth() / (galtonboard.getNumberOfBins());
         for(int y=layers.length-1; y>0; y--) {
         for(int x=0; x<layers[y].length; x++) {
         //g2.drawLine(x*this.getWidth() / layers[y].length, getHeight() - (y * getHeight() / layers.length), x*this.getWidth() / layers[y].length, getHeight() - ((y+1) * getHeight() / layers.length));
         g2.drawLine(x*this.getWidth() / layers[y].length, getHeight() - ((layers.length - y) * getHeight() / layers.length), x*this.getWidth() / layers[y].length, getHeight() - ((layers.length - y + 1) * getHeight() / layers.length));

         System.out.println("(" + x + "," + y + ")");
         }

         }

         //for(int i=0; i<galtonboard.getNumberOfBins(); i++) {
         //System.out.println(i + "," + i*xDelta);
         //g2.drawLine(i*this.getWidth() / galtonboard.getNumberOfBins(), getHeight(), i*this.getWidth() / galtonboard.getNumberOfBins(), getHeight() - binHeight);
         //}
         **/
    }

    public void drawBoard(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        g2.clearRect(0, 0, getWidth(), getHeight());

        g2.drawLine((getWidth()/2)-(10*scalar), 0, (getWidth()/2)-(10*scalar), (20*scalar));
        g2.drawLine((getWidth()/2)+(10*scalar), 0, (getWidth()/2)+(10*scalar), (20*scalar));

        g2.drawLine((getWidth()/2)-(10*scalar), (20*this.scalar), 0, getHeight()-(20*scalar));
        g2.drawLine((getWidth()/2)+(10*scalar), (20*this.scalar), getWidth(), getHeight()-(20*scalar));



    }

    private void drawPeg(int x, int y, Graphics2D g2){
        g2.drawOval(x-(2*scalar), y-(2*scalar), x+(2*scalar), y+(2*scalar));
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
