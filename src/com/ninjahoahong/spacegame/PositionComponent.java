package com.ninjahoahong.spacegame;

import com.jme3.math.Vector3f;


public class PositionComponent extends Component {
    
    private float x;
    private float y;
    private float z;

    public PositionComponent(float x, float y, float z) {
        name = "position";
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3f getPosition() {
        return new Vector3f(x, y, z);
    }
    
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
  
}
