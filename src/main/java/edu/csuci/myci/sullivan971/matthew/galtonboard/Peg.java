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

    public String toString() {
        StringBuilder result = new StringBuilder("[Peg(");
        result.append(System.identityHashCode(this));
        result.append("):");
        result.append(super.toString());
        result.append("[Left:");
        //result.append(getLeft().toString());
        // this prevents us from going down a rabbithole of printing out every
        //  single possible path
        result.append(System.identityHashCode(getLeft()));
        result.append("]");
        result.append("[Right:");
        //result.append(getRight().toString());
        result.append(System.identityHashCode(getRight()));
        result.append("]]");

        return result.toString();
    }
}
