package com.fazziclay.dirtrenderer.rendering.opengl;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class VAO {
    private final int id;
    private int elementsCount;

    public VAO() {
        id = glGenVertexArrays();
    }

    public void delete() {
        glDeleteVertexArrays(id);
    }

    public void addVertexBuffer(VBO vbo) {
        bind();
        vbo.bind();

        // Add layouts
        glEnableVertexAttribArray(elementsCount);
        glVertexAttribPointer(elementsCount, 3, GL_FLOAT, false, 0, NULL);
        elementsCount++;
    }

    public void bind() {
        glBindVertexArray(id);
    }

    public static void unbind() {
        glBindVertexArray(0);
    }
}
