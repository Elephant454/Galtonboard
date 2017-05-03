package edu.csuci.myci.sullivan971.matthew.galtonboard;

public class Bin extends BallPosition {
    
    public BallPosition chooseDirection() {
        return null;
    }

    public String toString() {
        return "[Bin (" + System.identityHashCode(this) + "):" + super.toString() + "]";
    }
}
