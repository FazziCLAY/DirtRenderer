package com.fazziclay.dirtrenderer;

import com.fazziclay.dirtrenderer.rendering.opengl.ShaderProgram;
import com.fazziclay.dirtrenderer.rendering.opengl.VAO;
import com.fazziclay.dirtrenderer.rendering.opengl.VBO;
import com.fazziclay.javaneoutil.FileUtil;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11C;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Renderer {

    private final float[] vetexes = new float[]{
            0.0f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f
    };

    private final float[] colors = new float[]{
            1.0f, 0.0f, 0.f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 1.0f
    };

    private final ShaderProgram shaderProgram;
    private final VAO vao;

    public Renderer() {
        GL.createCapabilities(); // LWJGL Needed

        shaderProgram = new ShaderProgram(FileUtil.getText("res/shaders/first.vert"), FileUtil.getText("res/shaders/first.frag"));
        if (!shaderProgram.isCompile()) {
            System.err.println("Shader compile log:");
            System.err.println(shaderProgram.getCompileLog());
            throw new RuntimeException("ShaderProgram not compile!");
        }

        VBO points_vbo = new VBO(vetexes);
        VBO colors_vbo = new VBO(colors);
        vao = new VAO();
        vao.addVertexBuffer(points_vbo);
        vao.addVertexBuffer(colors_vbo);
    }

    public void frame(long frame) {
        GL11C.glClearColor(0.05f, 0.05f, 0.05f, 0.0f);
        GL11C.glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer  | | GL_DEPTH_BUFFER_BIT

        shaderProgram.bind();
        vao.bind();
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}
