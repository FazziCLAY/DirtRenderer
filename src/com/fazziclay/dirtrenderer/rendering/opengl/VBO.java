package com.fazziclay.dirtrenderer.rendering.opengl;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class VBO {
    private final int id;
    private final BufferLayout bufferLayout;

    public VBO(float[] data, BufferLayout bufferLayout) {
        this.bufferLayout = bufferLayout;
        id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW); // TODO: 4/20/23 move external GL_STATIC_DRAW
    }

    public void delete() {
        glDeleteBuffers(id);
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    public static void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public BufferLayout getBufferLayout() {
        return bufferLayout;
    }
}
