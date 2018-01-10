package com.ninjahoahong.spacegame;

public class SizeComponent extends Component {
    
    private float length;
    private float height;
    private float width;

    public SizeComponent(float length, float height, float width) {
        name = "physics";
        this.length = length;
        this.height = height;
        this.width = width;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
