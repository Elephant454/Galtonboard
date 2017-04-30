package edu.csuci.myci.sullivan971.matthew.galtonboard;

import java.awt.Color;

public class Ball {
    private int id;
    private Color color;

    public Ball(int id, Color color) {
        setId(id);
        setColor(color);
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public void setId(int id) {
        this.id = id;
    }
}
