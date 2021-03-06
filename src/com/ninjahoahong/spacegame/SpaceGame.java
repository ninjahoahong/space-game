package com.ninjahoahong.spacegame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;


public class SpaceGame extends SimpleApplication {

    public static void main(String[] args) {
        SpaceGame app = new SpaceGame();
        app.start();
    }
    
    protected Spatial spaceship;
    protected Spatial fly;
    private boolean isFlying = true;

    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        spaceship = assetManager.loadModel("Models/SpaceShip/ApolloLunar.j3o");
        spaceship.setLocalTranslation(new Vector3f(0f, 0f, 7f));
        rootNode.attachChild(spaceship);
          
        fly = assetManager.loadModel("Models/SpaceShip/ApolloLunar.j3o");
        fly.scale(2f, 2f, 2f);
        fly.setLocalTranslation(new Vector3f(0f, 0f, 2f));
        rootNode.attachChild(fly);
        
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1f, -1f, -7f));
        rootNode.addLight(sun);
        
        initKeys();
    }
    
    /**
     * Custom Keybinding: Map named actions to inputs.
     */
    private void initKeys() {
        // You can map one or several inputs to one named action
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_H));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_U));
        // Add the names to the action listener.
        inputManager.addListener(actionListener, "Pause");
        inputManager.addListener(analogListener, "Left", "Right", "Up", "Down");
    }
    
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Pause") && !keyPressed) {
                isFlying = !isFlying;
            }
        }
    };

    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (isFlying) {
                if (name.equals("Up")) {
                    Vector3f v = spaceship.getLocalTranslation();
                    spaceship.setLocalTranslation(v.x, v.y - value * speed, v.z);
                }
                if (name.equals("Down")) {
                    Vector3f v = spaceship.getLocalTranslation();
                    spaceship.setLocalTranslation(v.x, v.y + value * speed, v.z);
                }
                if (name.equals("Right")) {
                    Vector3f v = spaceship.getLocalTranslation();
                    spaceship.setLocalTranslation(v.x + value * speed, v.y, v.z);
                }
                if (name.equals("Left")) {
                    Vector3f v = spaceship.getLocalTranslation();
                    spaceship.setLocalTranslation(v.x - value * speed, v.y, v.z);
                }
            } else {
                System.out.println("Press P to unpause.");
            }
        }
    };

}
