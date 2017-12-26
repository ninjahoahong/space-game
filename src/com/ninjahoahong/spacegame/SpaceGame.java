package com.ninjahoahong.spacegame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;


public class SpaceGame extends SimpleApplication {
    
    private BulletAppState bulletAppState;

    public static void main(String[] args) {
        SpaceGame app = new SpaceGame();
        app.start();
    }
    
   

    /** Prepare Materials */
    Material wall_mat;
    Material stone_mat;
    Material floor_mat;

    /** Prepare geometries and physical nodes for bricks and cannon balls. */
    private static final Box floor;

    /** dimensions used for bricks and wall */
    private static final float brickLength = 0.48f;
    private static final float brickWidth  = 0.24f;
    private static final float brickHeight = 0.12f;

    private RigidBodyControl    floor_phy;
    
    static {
        /** Initialize the floor geometry */
        floor = new Box(10f, 0.1f, 5f);
        floor.scaleTextureCoordinates(new Vector2f(3, 6));
    }
    
    protected Spatial spaceship;
    protected Spatial fly;
    private boolean isFlying = true;

    @Override
    public void simpleInitApp() {
        
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        /** Configure cam to look at scene */
        cam.setLocation(new Vector3f(0, 4f, 6f));
        cam.lookAt(new Vector3f(2, 2, 0), Vector3f.UNIT_Y);
        
        initMaterials();
        initFloor();
        
        spaceship = assetManager.loadModel("Models/SpaceShip/ApolloLunar.j3o");
        spaceship.setLocalTranslation(new Vector3f(0f, 0f, 7f));
        CollisionShape shape =
    CollisionShapeFactory.createDynamicMeshShape(spaceship);
        RigidBodyControl spaceshipPhys =
    new RigidBodyControl( shape , 0.0f );
        spaceship.addControl(spaceshipPhys);
        bulletAppState.getPhysicsSpace().add(spaceshipPhys);
        
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

      /** Initialize the materials used in this scene. */
  public void initMaterials() {
    wall_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    TextureKey key = new TextureKey("Textures/Terrain/BrickWall/BrickWall.jpg");
    key.setGenerateMips(true);
    Texture tex = assetManager.loadTexture(key);
    wall_mat.setTexture("ColorMap", tex);

    stone_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
    key2.setGenerateMips(true);
    Texture tex2 = assetManager.loadTexture(key2);
    stone_mat.setTexture("ColorMap", tex2);

    floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.jpg");
    key3.setGenerateMips(true);
    Texture tex3 = assetManager.loadTexture(key3);
    tex3.setWrap(WrapMode.Repeat);
    floor_mat.setTexture("ColorMap", tex3);
  }

  /** Make a solid floor and add it to the scene. */
  public void initFloor() {
    Geometry floor_geo = new Geometry("Floor", floor);
    floor_geo.setMaterial(floor_mat);
    floor_geo.setLocalTranslation(0, -0.1f, 0);
    this.rootNode.attachChild(floor_geo);
    /* Make the floor physical with mass 0.0f! */
    floor_phy = new RigidBodyControl(0.0f);
    floor_geo.addControl(floor_phy);
    bulletAppState.getPhysicsSpace().add(floor_phy);
  }
}
