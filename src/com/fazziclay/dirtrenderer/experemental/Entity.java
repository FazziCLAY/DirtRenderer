package com.fazziclay.dirtrenderer.experemental;

import com.fazziclay.dirtrenderer.rendering.opengl.BufferLayout;
import com.fazziclay.dirtrenderer.rendering.opengl.IndexBuffer;
import com.fazziclay.dirtrenderer.rendering.opengl.VAO;
import com.fazziclay.dirtrenderer.rendering.opengl.VBO;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Entity {
    public final static Random RANDOM = new Random();
    private static IndexBuffer indexBuffer;
    private static VAO vc_vao;

    private float x;
    private float y;
    private float z;
    private float rotate = 0;

    private static final float[] positions_colors2 = {
            -1.0f, -1.f, -1.f,    -1.f,  0.f,  0.f,     0.f, 0.f,              // 0
            -1.0f,  1.f, -1.f,    -1.f,  0.f,  0.f,     1.f, 0.f,              // 1
            -1.0f,  1.f,  1.f,    -1.f,  0.f,  0.f,     1.f, 1.f,              // 2
            -1.0f, -1.f,  1.f,    -1.f,  0.f,  0.f,     0.f, 1.f,              // 3

            // BACK
            1.0f, -1.f, -1.f,     1.f,  0.f,  0.f,     1.f, 0.f,              // 4
            1.0f,  1.f, -1.f,     1.f,  0.f,  0.f,     0.f, 0.f,              // 5
            1.0f,  1.f,  1.f,     1.f,  0.f,  0.f,     0.f, 1.f,              // 6
            1.0f, -1.f,  1.f,     1.f,  0.f,  0.f,     1.f, 1.f,              // 7

            // RIGHT
            -1.0f,  1.f, -1.f,     0.f,  1.f,  0.f,     0.f, 0.f,              // 8
            1.0f,  1.f, -1.f,     0.f,  1.f,  0.f,     1.f, 0.f,              // 9
            1.0f,  1.f,  1.f,     0.f,  1.f,  0.f,     1.f, 1.f,              // 10
            -1.0f,  1.f,  1.f,     0.f,  1.f,  0.f,     0.f, 1.f,              // 11

            // LEFT
            -1.0f, -1.f, -1.f,     0.f, -1.f,  0.f,     1.f, 0.f,              // 12
            1.0f, -1.f, -1.f,     0.f, -1.f,  0.f,     0.f, 0.f,              // 13
            1.0f, -1.f,  1.f,     0.f, -1.f,  0.f,     0.f, 1.f,              // 14
            -1.0f, -1.f,  1.f,     0.f, -1.f,  0.f,     1.f, 1.f,              // 15

            // TOP
            -1.0f, -1.f,  1.f,     0.f,  0.f,  1.f,     0.f, 0.f,              // 16
            -1.0f,  1.f,  1.f,     0.f,  0.f,  1.f,     1.f, 0.f,              // 17
            1.0f,  1.f,  1.f,     0.f,  0.f,  1.f,     1.f, 1.f,              // 18
            1.0f, -1.f,  1.f,     0.f,  0.f,  1.f,     0.f, 1.f,              // 19

            // BOTTOM
            -1.0f, -1.f, -1.f,    0.f,  0.f, -1.f,     0.f, 1.f,              // 20
            -1.0f,  1.f, -1.f,    0.f,  0.f, -1.f,     1.f, 1.f,              // 21
            1.0f,  1.f, -1.f,    0.f,  0.f, -1.f,     1.f, 0.f,              // 22
            1.0f, -1.f, -1.f,    0.f,  0.f, -1.f,     0.f, 0.f,              // 23
    };


    static int indices[] = {
            0,   1,  2,  2,  3,  0, // front
            4,   5,  6,  6,  7,  4, // back
            8,   9, 10, 10, 11,  8, // right
            12, 13, 14, 14, 15, 12, // left
            16, 17, 18, 18, 19, 16, // top
            20, 21, 22, 22, 23, 20  // bottom
    };


    public Entity() {

    }

    public static void init() {
        indexBuffer = new IndexBuffer(indices);
        VBO vc_vbo = new VBO(positions_colors2, BufferLayout.create(BufferLayout.ShaderDataType.FLOAT3, BufferLayout.ShaderDataType.FLOAT3, BufferLayout.ShaderDataType.FLOAT2));
        vc_vao = new VAO();
        vc_vao.addVertexBuffer(vc_vbo);
        vc_vao.setIndexBuffer(indexBuffer);
    }

    public Entity(float x, float y, float z) {
        this();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void draw() {
        vc_vao.bind();
        indexBuffer.bind();
        glDrawElements(GL_TRIANGLES, indexBuffer.getCount(), GL_UNSIGNED_INT, NULL);
    }


    int ticks = 0;
    int rem = 0;
    public void tick() {
        ticks++;
        if (false) return;
        if (ticks > 5 * 20) {
            x = x + 0.25f;
            return;
        }
        float f = 0.09f;
        if (ticks > 30) f = 13;
        x += RANDOM.nextFloat() / f *(RANDOM.nextBoolean() ? 1 : -1);
        y += RANDOM.nextFloat() / f  *(RANDOM.nextBoolean() ? 1 : -1);
        z += RANDOM.nextFloat() / f *(RANDOM.nextBoolean() ? 1 : -1);
        if (rem > 0) {
            if (RANDOM.nextBoolean()) {
                x += rem;
            } else {
                x -= rem;
            }
            rem--;
        }

        if (RANDOM.nextBoolean() && RANDOM.nextBoolean() && RANDOM.nextBoolean() && RANDOM.nextBoolean() && RANDOM.nextBoolean()) {
            rem = (int) (RANDOM.nextFloat() * 5f);
        }
        rotate += RANDOM.nextFloat() / f *(RANDOM.nextBoolean() ? 1 : 0.5);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getRotate() {
        return rotate;
    }

    public void setRotate(float rotate) {
        this.rotate = rotate;
    }
}
