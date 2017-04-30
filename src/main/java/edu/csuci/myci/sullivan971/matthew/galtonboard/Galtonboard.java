package edu.csuci.myci.sullivan971.matthew.galtonboard;

public class Galtonboard {
    private int numberOfBins;
    private BallPosition[][] layers;
    
    public Galtonboard (int numberOfBins){
        if(numberOfBins%2 == 1) throw new IllegalArgumentException("The number of bins must be even.");
        else this.numberOfBins = numberOfBins;

        // allocate an array of layers, with each layer being made up of
        //  BallPositions
        this.layers = new BallPosition[numberOfBins][];

        // populate the last layer of the galtonboard with bins
        for(int i=0; i<numberOfBins; i++) {
            this.layers[this.layers.length-1][i] = new Bin();
        }

        // based off of the number of bins, generate every layer in the board
        for(int i=layers.length-1; i>1; i--) {
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
    
}
