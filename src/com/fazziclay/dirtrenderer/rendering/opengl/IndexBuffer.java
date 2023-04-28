package com.fazziclay.dirtrenderer.rendering.opengl;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class IndexBuffer {
    private final int id;
    private final int count;

    public IndexBuffer(int[] data) {
        this.id = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW); // TODO: 4/20/23 move external GL_STATIC_DRAW
        this.count = data.length;
    }

    public void delete() {
        glDeleteBuffers(id);
    }

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    }

    public static void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int getCount() {
        return count;
    }
}
