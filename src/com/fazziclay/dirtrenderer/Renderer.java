package com.fazziclay.dirtrenderer;

import com.fazziclay.dirtrenderer.rendering.opengl.BufferLayout;
import com.fazziclay.dirtrenderer.rendering.opengl.ShaderProgram;
import com.fazziclay.dirtrenderer.rendering.opengl.VAO;
import com.fazziclay.dirtrenderer.rendering.opengl.VBO;
import com.fazziclay.javaneoutil.FileUtil;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11C;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.glDrawArrays;

public class Renderer {

    private final float[] vetexes = new float[]{
            0.0f, 0.5f, 0.0f, // первая вершина XYZ
            0.5f, -0.5f, 0.0f, // вторая
            -0.5f, -0.5f, 0.0f // третья
    };

    private final float[] colors = new float[]{
          // R     G     B
            1.0f, 0.0f, 0.0f, // цвета для каждой вершины, тоесть смотри щас поменяю вот так
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f //пон?
    }; // т.е. R G G

    private final float[] vc = new float[]{
            0.0f, 0.5f, 0.0f,    0.0f, 1.0f, 0.0f,
            0.5f, -0.5f, 0.0f,   0.0f, 0.0f, 1.0f,
            -0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 0.0f
    };

    private final ShaderProgram shaderProgram;
    private final VAO vao;
    private final VAO vc_vao;

    public Renderer() {
        GL.createCapabilities(); // LWJGL Needed
        shaderProgram = new ShaderProgram(FileUtil.getText("res/shaders/first.vert"), FileUtil.getText("res/shaders/first.frag"));
        if (!shaderProgram.isCompile()) {
            System.err.println("Shader compile log:");
            System.err.println(shaderProgram.getCompileLog());
            throw new RuntimeException("ShaderProgram not compile!");
        }

        // смотри, при инициализации (тоесть 1 раз в начале) мы передаём вершины и цвета 1 раз в VAO
        VBO points_vbo = new VBO(vetexes, new BufferLayout(new BufferLayout.BufferElement[]{new BufferLayout.BufferElement(BufferLayout.ShaderDataType.FLOAT3)}));
        VBO colors_vbo = new VBO(colors, new BufferLayout(new BufferLayout.BufferElement[]{new BufferLayout.BufferElement(BufferLayout.ShaderDataType.FLOAT3)}));


        VBO vc_vbo = new VBO(vc, BufferLayout.create(BufferLayout.ShaderDataType.FLOAT3, BufferLayout.ShaderDataType.FLOAT3));
        vc_vao = new VAO();
        vc_vao.addVertexBuffer(vc_vbo);


        vao = new VAO();
        vao.addVertexBuffer(points_vbo);
        vao.addVertexBuffer(colors_vbo);
    }

    public void frame(long frame) {
        // а тут каждый кадр мы его просто биндим, и рисуем, и всё, каждый кадр рендерим то, что УЖЕ ЕСТЬ В ВИДЮХЕ
        // ПРОСТО ТАК рЕСУРСЫ НЕ ТРАТИМ!!11!111
        float col = (float) ((Math.sin(frame / 25f) + 1) / 2) * 0.5f;
        GL11C.glClearColor(col, col, col, 0.0f);
        GL11C.glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer  | | GL_DEPTH_BUFFER_BIT

        shaderProgram.bind();
        vc_vao.bind();
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}
