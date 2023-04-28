package com.fazziclay.dirtrenderer.app;

import com.fazziclay.dirtrenderer.DirtRenderer;
import com.fazziclay.dirtrenderer.experemental.Entity;
import com.fazziclay.dirtrenderer.math.Matrix4f;
import com.fazziclay.dirtrenderer.rendering.opengl.ShaderProgram;
import com.fazziclay.javaneoutil.FileUtil;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    private final ShaderProgram shaderProgram;
    private final Camera camera;
    private final DirtRenderer dirtRenderer;
    org.joml.Matrix4f projection;

    public Renderer(DirtRenderer dirtRenderer) {
        this.dirtRenderer = dirtRenderer;
        shaderProgram = new ShaderProgram(FileUtil.getText("res/shaders/first.vert"), FileUtil.getText("res/shaders/first.frag"));
        if (!shaderProgram.isCompile()) {
            System.err.println("Shader compile log:");
            System.err.println(shaderProgram.getCompileLog());
            throw new RuntimeException("ShaderProgram not compile!");
        }

        camera = new Camera();
        camera.x(0);
        camera.y(0);
        camera.z(2);
        glEnable(GL_DEPTH_TEST);
        Entity.init();

        float near = 1;
        float far = 3000;
        float right = 2;
        float left = -2;
        float bottom = -2;
        float top = 2;

        projection = new org.joml.Matrix4f();
        projection.setPerspectiveRect(4, 4, near, far);

        //Matrix4f.projectionMatrix(left, right, bottom, top, near, far);
    }

    public void frame(long frame) {
        float col = 0.05f;
        glClearColor(col, col, col, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer  | |

        shaderProgram.bind();

        org.joml.Matrix4f viewMatrix = camera.getViewMatrix();
        shaderProgram.setMat4f("view_matrix", viewMatrix);
        shaderProgram.setMat4f("projection_matrix", projection);


        int i = 1;
        for (Entity entity : dirtRenderer.getEntities()) {
            Matrix4f m = Matrix4f.translate(entity.getX(), entity.getY(), entity.getZ());
            m.multiply(Matrix4f.scale(0.5f, 0.5f, 0.5f));
            m.multiply(Matrix4f.rotateX(entity.getRotate()));
            m.multiply(Matrix4f.rotateY(entity.getRotate()));
            Entity.RANDOM.setSeed((long) (entity.getX() * entity.getZ()) * i);
            shaderProgram.setFloat("color_shift", Entity.RANDOM.nextFloat() * Entity.RANDOM.nextFloat());
            shaderProgram.setMat4f("model_matrix", m);
            entity.draw();
            i++;
        }

    }

    public Camera getCamera() {
        return camera;
    }
}
