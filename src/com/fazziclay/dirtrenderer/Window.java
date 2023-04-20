package com.fazziclay.dirtrenderer;

import org.lwjgl.glfw.GLFWFramebufferSizeCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final long id;
    private final Renderer renderer;
    private double fps;

    public Window(String title, int width, int height) {
        if ( !glfwInit() ) throw new IllegalStateException("Unable to initialize GLFW");
        id = glfwCreateWindow(width, height, title, NULL, NULL);
        if ( id == NULL ) throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(id, (window, key, scancode, action, mods) -> {
        });
        glfwSetFramebufferSizeCallback(id, new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long l, int width, int height) {
                glViewport(0, 0, width, height);
            }
        });

        glfwMakeContextCurrent(id);
        glfwShowWindow(id);
        renderer = new Renderer();
    }

    public void run() {
        long frame = 0;
        while (!glfwWindowShouldClose(id)) {
            long fps_start = System.currentTimeMillis();

            renderer.frame(frame);

            glfwSwapBuffers(id); // swap the color buffers
            glfwPollEvents();

            double fps_pass = System.currentTimeMillis() - fps_start;
            if (fps_pass == 0) fps_pass = 0.0001;
            fps = 1000 / fps_pass;
            frame++;
        }
    }

    public double getFps() {
        return fps;
    }
}
