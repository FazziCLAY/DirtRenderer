package com.fazziclay.dirtrenderer.app;

import com.fazziclay.dirtrenderer.DirtRenderer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final long id;
    private final Renderer renderer;
    private final Camera camera;
    private double fps;

    public Window(String title, int width, int height) {
        if ( !glfwInit() ) throw new IllegalStateException("Unable to initialize GLFW");
        id = glfwCreateWindow(width, height, title, NULL, NULL);
        if ( id == NULL ) throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(id, (window, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                Keyboard.press(key);
            }
            if (action == GLFW_RELEASE) {
                Keyboard.release(key);
            }
        });
        glfwSetFramebufferSizeCallback(id, new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long l, int width, int height) {
                glViewport(0, 0, width, height);
            }
        });

        glfwMakeContextCurrent(id);
        glfwSwapInterval(0);
        glfwShowWindow(id);
        GL.createCapabilities(); // LWJGL Needed

        DirtRenderer dirtRenderer = new DirtRenderer();
        renderer = new Renderer(dirtRenderer);
        camera = renderer.getCamera();

        Thread thread = new Thread(() -> {
            while (true) {
                dirtRenderer.tick();
                try {
                    Thread.sleep(1000 / 20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.setDaemon(true);
        thread.setName("Server");
        thread.start();
    }

    public void run() {
        long frame = 0;
        while (!glfwWindowShouldClose(id)) {
            long fps_start = System.currentTimeMillis();

            keyboard();

            renderer.frame(frame);

            glfwSwapBuffers(id); // swap the color buffers
            glfwPollEvents();

            double fps_pass = System.currentTimeMillis() - fps_start;
            if (fps_pass == 0) fps_pass = 0.1f;
            fps = 1000f / fps_pass;
            frame++;

            System.out.println("FPS: " + getFps());
        }
        glfwTerminate();
    }

    private void keyboard() {

        //glFrustum(-1, 1, -1, 1, 2, 200);
        int c = Keyboard.isPress(GLFW_KEY_LEFT_CONTROL) ? 4 : 1;
        if (Keyboard.isPress(org.lwjgl.glfw.GLFW.GLFW_KEY_W)) {
            camera.z(camera.z() - 0.2f * c);
        }
        if (Keyboard.isPress(GLFW.GLFW_KEY_S)) {
            camera.z(camera.z() + 0.2f * c);
        }
        if (Keyboard.isPress(GLFW.GLFW_KEY_A)) {
            camera.x(camera.x() - 0.2f * c);
        }
        if (Keyboard.isPress(GLFW.GLFW_KEY_D)) {
            camera.x(camera.x() + 0.2f * c);
        }
        if (Keyboard.isPress(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            camera.y(camera.y() - 0.2f * c);
        }
        if (Keyboard.isPress(GLFW.GLFW_KEY_SPACE)) {
            camera.y(camera.y() + 0.2f * c);
        }

        if (Keyboard.isPress(GLFW.GLFW_KEY_UP)) {
            camera.setPitch(camera.getPitch() + 0.9f);
        }
        if (Keyboard.isPress(GLFW.GLFW_KEY_DOWN)) {
            camera.setPitch(camera.getPitch() - 0.9f);
        }
        if (Keyboard.isPress(GLFW.GLFW_KEY_LEFT)) {
            camera.setYaw(camera.getYaw() - 0.9f);
        }
        if (Keyboard.isPress(GLFW.GLFW_KEY_RIGHT)) {
            camera.setYaw(camera.getYaw() + 0.9f);
        }
    }

    public double getFps() {
        return fps;
    }

    public void delete() {
        // TODO: 4/20/23 owo
    }
}
