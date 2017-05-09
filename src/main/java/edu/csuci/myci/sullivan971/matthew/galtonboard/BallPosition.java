package edu.csuci.myci.sullivan971.matthew.galtonboard;

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class BallPosition implements Iterable<Ball>{
    private Deque<Ball> ballQueue;
    
    public BallPosition() {
        // this is not thread safe, but can be swapped out later
        ballQueue = new ArrayDeque<Ball>();
    }

    public Ball removeBall() {
        return this.ballQueue.removeLast();
    }

    public Ball getBall() {
        return this.ballQueue.getLast();
    }
    
    public void addBall(Ball ball) {
        this.ballQueue.addFirst(ball);
    }

    // this may have to go or be thought about in some other way if we do threads
    public Iterator<Ball> iterator() {
        return this.ballQueue.descendingIterator();
    }

    public abstract BallPosition chooseDirection();

    public String toString() {
        StringBuilder result = new StringBuilder("[Balls:");
        Deque<Ball> temp = new ArrayDeque<Ball>();

        try {
            while(true) {
                temp.addFirst(removeBall());
                result.append(temp.getFirst().toString());
                result.append(", ");
            }
        }catch (NoSuchElementException nsee){}
        result.append("]");
        
        this.ballQueue = temp;
        
        return result.toString();
    }

    public int getNumberOfBalls() {
        return ballQueue.size();
    }
    
}
