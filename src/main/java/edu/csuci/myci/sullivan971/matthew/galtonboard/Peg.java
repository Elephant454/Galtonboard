package edu.csuci.myci.sullivan971.matthew.galtonboard;

import java.util.Random;

public class Peg extends BallPosition {
    private BallPosition left;
    private BallPosition right;
    
    public Peg() {}
    
    public Peg(BallPosition left, BallPosition right) {
        setLeft(left);
        setRight(right);
    }

    public BallPosition getLeft() {
        return this.left;
    }
    public void setLeft(BallPosition ballPosition) {
        this.left = ballPosition;
    }
    
    public BallPosition getRight() {
        return this.right;
    }
    public void setRight(BallPosition ballPosition) {
        this.right = ballPosition;
    }

    public BallPosition chooseDirection() {
        return (new Random()).nextBoolean() ? this.left : this.right;
    }
}
