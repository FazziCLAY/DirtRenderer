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


        for (BufferLayout.BufferElement current_element : vbo.getBufferLayout().elements)
        {
            glEnableVertexAttribArray(elementsCount);
            glVertexAttribPointer(
                    elementsCount,
                    current_element.componentCount,
                    current_element.openglType,
                    false,
                    vbo.getBufferLayout().m_stride,
                    current_element.offset
            );
            elementsCount++;
        }
    }

    public void bind() {
        glBindVertexArray(id);
    }

    public static void unbind() {
        glBindVertexArray(0);
    }
}
