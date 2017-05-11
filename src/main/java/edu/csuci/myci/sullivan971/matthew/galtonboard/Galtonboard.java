package edu.csuci.myci.sullivan971.matthew.galtonboard;

import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;

public class Galtonboard {
    private int numberOfBins;
    private BallPosition[][] layers;

    private int currentBallId = 0;

    //private RandomPalette randomPalette = new RandomPalette(0.6f, 0.7f);
    private RandomPalette randomPalette = new RandomPalette(0.8f, 0.7f);
    
    public Galtonboard (int numberOfBins){
        setNumberOfBins(numberOfBins);
        generateGaltonBoard();
    }

    private void generateGaltonBoard() {
        // allocate an array of layers, with each layer being made up of
        //  BallPositions
        this.layers = new BallPosition[numberOfBins][];

        // populate the last layer of the galtonboard with bins
        this.layers[layers.length-1] = new BallPosition[numberOfBins];
        for(int i=0; i<numberOfBins; i++) {
            this.layers[this.layers.length-1][i] = new Bin();
        }

        // based off of the number of bins, generate every layer in the board
        for(int i=layers.length-1; i>0; i--) {
            layers[i - 1] = generatePreviousLayer(layers[i]);
        }
    }

    public BallPosition[] generatePreviousLayer(BallPosition[] layer) {
        BallPosition[] result = new BallPosition[layer.length - 1];

        for(int i=0; i<result.length; i++) {
            result[i] = new Peg(layer[i], layer[i+1]);
        }
        
        return result;
    }

    public String toString() {
        StringBuilder result = new StringBuilder("[Galtonboard:");
        
        for(int i=0; i<layers.length; i++) {
            result.append("[Layer");
            result.append(i);
            result.append(":");
            for(int j=0; j<layers[i].length; j++) {
                result.append(layers[i][j].toString());
            }
            result.append("]");
        }
        
        result.append("]");
        
        return result.toString();
    }

    public void dropABall() {
        Ball ball = new Ball(currentBallId, randomPalette.next());
        currentBallId++;
        layers[0][0].addBall(ball);

        BallPosition previousBallPosition = layers[0][0];
        BallPosition currentBallPosition = previousBallPosition.chooseDirection();

        try{
            while(currentBallPosition != null) {
                currentBallPosition.addBall(previousBallPosition.removeBall());
                previousBallPosition = currentBallPosition;
                currentBallPosition = previousBallPosition.chooseDirection();
            }
        }catch (NullPointerException npe) {}
    }

    public void dropBalls(int balls) {
        for(int i=0; i<balls; i++) {
            this.dropABall();
        }
    }

    public int getNumberOfBins() {return numberOfBins;}
    private void setNumberOfBins(int numberOfBins) {
        if(numberOfBins%2 == 1)
            throw new IllegalArgumentException("The number of bins must be"
                                               + " even.");
        else this.numberOfBins = numberOfBins;
    }

    public int[] getBallsPerBin() {
        int[] result = new int[layers[layers.length-1].length];
        
        for(int i=0; i<result.length; i++) {
            result[i] = layers[layers.length-1][i].getNumberOfBalls();
        }
        
        return result;
    }
    
    public static Integer[] createHistogramArray(int[] locationFrequencyData) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(int i=0; i<locationFrequencyData.length; i++) {
            for(int j=0; j<locationFrequencyData[i]; j++) {
                result.add(i);
            }
        }

        return result.toArray(new Integer[result.size()]);
    }

    public static String getBallsPerBinString(Integer[] ballsPerBin) {
        StringBuilder result = new StringBuilder("[");
        
        for(int i=0; i<ballsPerBin.length-2; i++) {
            result.append(ballsPerBin[i]);
            result.append(", ");
        }
        result.append(ballsPerBin[ballsPerBin.length-1]);
        result.append("]");

        return result.toString();
    }

    public BallPosition[][] getLayers() {
        return this.layers;
    }

    public int getCurrentBallId() {
        return this.currentBallId;
    }
    
}
