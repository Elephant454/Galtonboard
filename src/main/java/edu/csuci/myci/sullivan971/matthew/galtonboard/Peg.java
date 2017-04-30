package edu.csuci.myci.sullivan971.matthew.galtonboard;

import java.util.Random;

public class Peg extends BallPosition {
    private BallPosition left;
    private BallPosition right;
    
    public Peg() {}
    
    public Peg(Peg left, Peg right) {
        setLeft(left);
        setRight(right);
    }

    public BallPosition getLeft() {
        return this.left;
    }
    public void setLeft(Peg peg) {
        this.left = peg;
    }
    
    public BallPosition getRight() {
        return this.right;
    }
    public void setRight(Peg peg) {
        this.right = peg;
    }

    public BallPosition chooseDirection() {
        return (new Random()).nextBoolean() ? this.left : this.right;
    }
}
