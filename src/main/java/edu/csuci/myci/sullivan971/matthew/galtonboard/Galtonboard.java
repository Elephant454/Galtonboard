package edu.csuci.myci.sullivan971.matthew.galtonboard;

public class Galtonboard {
    private int numberOfBins;
    private BallPosition[][] layers;

    private int currentBallId = 0;

    private RandomPalette randomPalette = new RandomPalette(0.6f, 0.7f);
    
    public Galtonboard (int numberOfBins){
        if(numberOfBins%2 == 1) throw new IllegalArgumentException("The number of bins must be even.");
        else this.numberOfBins = numberOfBins;

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
}
