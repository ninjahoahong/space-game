package com.ninjahoahong.spacegame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;


public class SpaceGame extends SimpleApplication {

    public static void main(String[] args) {
        SpaceGame app = new SpaceGame();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Spatial spaceship = assetManager.loadModel("Models/SpaceShip/ApolloLunar.j3o");
        rootNode.attachChild(spaceship);
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(sun);        
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
