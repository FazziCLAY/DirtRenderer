package com.fazziclay.dirtrenderer.rendering.opengl;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_INT;

public class BufferLayout {
    final int m_stride;
    final BufferElement[] elements;

    public BufferLayout(BufferElement[] elements) {
        this.elements = elements;
        int offset = 0;
        int m_stride_tmp = 0;
        for (BufferElement element : elements) {
            element.offset = offset;
            offset += element.size;
            m_stride_tmp += element.size;
        }
        m_stride = m_stride_tmp;
    }

    public static BufferLayout create(final ShaderDataType... elements) {
        final BufferElement[] result = new BufferElement[elements.length];
        int i = 0;
        for (ShaderDataType element : elements) {
            result[i] = new BufferElement(element);
            i++;
        }
        return new BufferLayout(result);
    }

    public static class BufferElement {
        public final ShaderDataType type;
        public final int openglType;
        public final int componentCount;
        public final int size;
        public int offset;

        public BufferElement(final ShaderDataType type) {
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

        private final int openglType;
        private final int count;
        private final int bytes;

        ShaderDataType(int openglType, int count, int bytes) {
            this.openglType = openglType;
            this.count = count;
            this.bytes = bytes;
        }
    }
}
