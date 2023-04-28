package com.fazziclay.dirtrenderer;

import com.fazziclay.dirtrenderer.experemental.Entity;

import java.util.ArrayList;
import java.util.List;

public class DirtRenderer {
    private final List<Entity> entities = new ArrayList<>();

    public DirtRenderer() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 1; k++) {
                    Entity e = new Entity(i, 0, j);
                    entities.add(e);
                }
            }
        }
    }

    public void tick() {
        for (Entity entity : entities) {
            entity.tick();
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
