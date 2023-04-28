package com.fazziclay.dirtrenderer.app;


import org.joml.Matrix4f;

public class Camera {
    private float x;
    private float y;
    private float z;

    private float yaw;
    private float pitch;

    public float x() {
        return x;
    }

    public void x(float x) {
        this.x = x;
    }

    public float y() {
        return y;
    }

    public void y(float y) {
        this.y = y;
    }

    public float z() {
        return z;
    }

    public void z(float z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public Matrix4f getViewMatrix() {
        Matrix4f ret = new Matrix4f();
        ret.rotateY((float) (yaw * (Math.PI / 180f)));
        ret.rotateX((float) (-pitch * (Math.PI / 180f)));

        ret.translate(-x(), -y(), -z());
        // TODO: 4/22/23 ROTATE FIX
        return ret;
    }
}
