package com.fazziclay.dirtrenderer.app;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {
    private static final boolean[] keys = new boolean[GLFW_KEY_LAST + 1];

    public static boolean isPress(int key) {
        return keys[key];
    }

    public static void press(int key) {
        keys[key] = true;
    }

    public static void release(int key) {
        keys[key] = false;
    }
}
