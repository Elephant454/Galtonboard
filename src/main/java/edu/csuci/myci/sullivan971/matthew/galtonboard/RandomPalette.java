package edu.csuci.myci.sullivan971.matthew.galtonboard;

import java.awt.Color;

public class RandomPalette {
    private float s;
    private float b;

    public RandomPalette(float s, float b) {
        setS(s);
        setB(b);
    }

    public float getS() {
        return(s);
    }
    public void setS(float s) {
        if(s <= 1f) {
            this.s = s;
        }else {
            throw new IllegalArgumentException("s must be less than or equal to"
                                               + "1");
        }
    }

    public float getB() {
        return(b);
    }
    public void setB(float b) {
        if(b <= 1f) {
            this.b = b;
        }else {
            throw new IllegalArgumentException("b must be less than or equal to"
                                               + "1");
        }
    }

    public Color next() {
        return Color.getHSBColor(new Float(Math.random()), s, b);
    }
}
