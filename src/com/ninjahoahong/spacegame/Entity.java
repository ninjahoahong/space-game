package com.ninjahoahong.spacegame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Entity implements Serializable {
    
    protected String id = UUID.randomUUID().toString();
    
    List<Component> components = new ArrayList<Component>();
    
    public void removeComponent(String componentName) {
        for (Component component : components) {
            if (component.getName().equals(componentName)) {
                components.remove(component);
                return;
            }
        }
    }
    
    public void addComponent(Component component) {
        components.add(component);
    }
    
    public void removeAllComponents() {
        if (!components.isEmpty()) {
            components.clear();
        }
    }
    
    @Override
    public String toString() {
        String name = String.format("entity: %s", id);
        for (Component component : components) {
            name = name.concat("\n");
            name = name.concat(component.getName());
        }
        return name;
    }
}
