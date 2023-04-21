package com.fazziclay.dirtrenderer.rendering.opengl;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_INT;

public class BufferLayout {
    int m_stride;
    BufferElement[] elements;

    public BufferLayout(BufferElement[] elements) {
        this.elements = elements;
        int offset = 0;
        m_stride = 0;
        for (BufferElement element : elements)
        {
            element.offset = offset;
            offset += element.size;
            m_stride += element.size;
        }
    }

    public static BufferLayout create(ShaderDataType... elements) {
        BufferElement[] result = new BufferElement[elements.length];
        int i = 0;
        for (ShaderDataType element : elements) {
            result[i] = new BufferElement(element);
            i++;
        }
        return new BufferLayout(result);
    }

    public static class BufferElement {
        public ShaderDataType type;
        public int openglType;
        public int componentCount;
        public int size;
        public int offset;

        public BufferElement(ShaderDataType type) {
            this.type = type;
            this.openglType = type.openglType;
            this.componentCount = type.count;
            this.size = type.bytes * type.count;
            this.offset = 0;
        }
    }

    public enum ShaderDataType {
        FLOAT(GL_FLOAT, 1, Float.BYTES),
        FLOAT2(GL_FLOAT, 2, Float.BYTES),
        FLOAT3(GL_FLOAT, 3, Float.BYTES),
        FLOAT4(GL_FLOAT, 4, Float.BYTES),
        INT(GL_INT, 1, Integer.BYTES),
        INT2(GL_INT, 2, Integer.BYTES),
        INT3(GL_INT, 3, Integer.BYTES),
        INT4(GL_INT, 4, Integer.BYTES);

        final int openglType;
        private final int count;
        private final int bytes;

        ShaderDataType(int openglType, int count, int bytes) {
            this.openglType = openglType;
            this.count = count;
            this.bytes = bytes;
        }
    }
}
