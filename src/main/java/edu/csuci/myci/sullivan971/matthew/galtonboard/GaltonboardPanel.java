package edu.csuci.myci.sullivan971.matthew.galtonboard;

import java.awt.geom.Line2D.Double;
import java.awt.geom.Line2D.Double;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class GaltonboardPanel extends JPanel implements KeyListener, Runnable {

    final private int scalar = 1;
    private double startHeight;
    private double screenHeightScalar;
    private double startWidth;
    private double screenWidthScalar;
    private int lineEndXL;
    private int lineEndXR;

    private Galtonboard galtonboard;
    private BallPosition[][] layers;

    RandomPalette randomPalette = new RandomPalette(0.5f, 0.6f);

    // start of instance variables that I hastily copied over
    private static final long serialVersionUID = 42L;

    boolean running = true;

    boolean drawBoardStage = false;

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
        this.startHeight = 600;
        this.startWidth = 800;
        this.setSize((int)startWidth, (int)startHeight);
        this.requestFocusInWindow(true);
        drawingThread.start();
    }

    
    @Override
    public void paintComponent (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.clearRect(0, 0, getWidth(), getHeight());
        
        g2.drawString(Float.toString(this.fps), 20, 20);
        
        screenHeightScalar = getHeight()/startHeight;
        screenWidthScalar = getWidth()/startWidth;
        
        if (drawBoardStage) drawBoard(g);
        else drawHistogram(g);
    }

    public void drawBoard(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        //g2.clearRect(0, 0, getWidth(), getHeight());

        int orgX = (getWidth()/2)-(10*scalar);
        int orgY = 20*scalar;
        int x=orgX;
        int y=orgY;





        for (int i=1; i<=layers.length; i++){
            x-= 10*scalar * screenWidthScalar;
            y+= 10*scalar * screenHeightScalar;
            for (int j=1; j<=i; j++){
                lineEndXL=x+(int)(20*scalar*j*screenWidthScalar);
                lineEndXR=y;
                drawPeg(lineEndXL, y, g2);
            }
        }

        for (int j=0; j<=layers.length+1; j++){
            g2.fillRect(x+(int)(20*scalar*j*screenWidthScalar), y, (int)(4 * screenWidthScalar * scalar), getHeight()*(int)screenHeightScalar);
        }

        g2.drawOval((int)(screenWidthScalar)*(getWidth()/2),20,10,10);

    }

    private void drawPeg(int x, int y, Graphics2D g2){
        g2.fillOval(x-(scalar), y-(scalar), 4*scalar, 4*scalar);
    }

    private void drawHistogram(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
         // numberOfBins - 1 is the number of divisions between bins
         //int xDelta = this.getWidth() / (galtonboard.getNumberOfBins() - 1);
         //int xDelta = this.getWidth() / (galtonboard.getNumberOfBins());
         //for(int y=layers.length-1; y>0; y--) {
         //for(int x=0; x<layers[y].length; x++) {
                 //g2.drawLine(x*this.getWidth() / layers[y].length, getHeight() - (y * getHeight() / layers.length), x*this.getWidth() / layers[y].length, getHeight() - ((y+1) * getHeight() / layers.length));
                 //g2.drawLine(x*this.getWidth() / layers[y].length, getHeight() - ((layers.length - y) * getHeight() / layers.length), x*this.getWidth() / layers[y].length, getHeight() - ((layers.length - y + 1) * getHeight() / layers.length));
                 //System.out.println("(" + x + "," + y + ")");
        //}
        //}

        
        //int[] ballsPerBin = galtonboard.getBallsPerBin();
        //int totalBalls = (int) Arrays.stream(ballsPerBin).summaryStatistics().getSum();
        //int max = Arrays.stream(ballsPerBin).summaryStatistics().getMax();

        //galtonboard.dropBalls(100);
        //System.out.println(galtonboard.getCurrentBallId());

        int scalar = 1;
        
        double xDelta = this.getWidth() / galtonboard.getNumberOfBins();
        double yDelta = this.getHeight() / galtonboard.getCurrentBallId();
        for(int i=0; i<galtonboard.getNumberOfBins(); i++) {
            //System.out.println(i + "," + i*xDelta);
            g2.setPaint(randomPalette.next());
            //g2.draw(new java.awt.geom.Line2D.Double(i*xDelta, getHeight(), i*xDelta, getHeight() - galtonboard.getBallsPerBin()[i]*yDelta*scalar));
            g2.drawLine((int)(i*xDelta), getHeight(), (int)(i*xDelta), (int)(getHeight() - galtonboard.getBallsPerBin()[i]*yDelta*scalar));

            System.out.println(getHeight() - galtonboard.getBallsPerBin()[i]*yDelta*scalar);
        }
    }

    long sleepLength;
    public void run() {
        while(running) {
            currentTime = System.nanoTime();

            // do the actual logic for this tick
            repaint();
            galtonboard.dropABall();

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
