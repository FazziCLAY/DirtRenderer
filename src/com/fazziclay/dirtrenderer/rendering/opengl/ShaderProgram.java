package com.fazziclay.dirtrenderer.rendering.opengl;

import com.fazziclay.dirtrenderer.math.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    private final int id;
    private String compileLog = null;
    private boolean isCompile = true;
    private final HashMap<String, Integer> uniformsLocations = new HashMap<>();
    private final float[] mat4x4array = new float[4*4];

    public ShaderProgram(String vertex, String fragment) {
        int vs = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vs, vertex);
        glCompileShader(vs);
        int[] status = new int[1];
        glGetShaderiv(vs, GL_COMPILE_STATUS, status);
        if (status[0] == GL_FALSE) {
            log("Vertex shader", glGetShaderInfoLog(vs));
            isCompile = false;
        }

        int fs = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fs, fragment);
        glCompileShader(fs);
        status = new int[1];
        glGetShaderiv(fs, GL_COMPILE_STATUS, status);
        if (status[0] == GL_FALSE) {
            log("Fragment shader", glGetShaderInfoLog(fs));
            isCompile = false;
        }

        id = glCreateProgram();
        glAttachShader(id, vs);
        glAttachShader(id, fs);
        glLinkProgram(id);

        glDetachShader(id, vs);
        glDetachShader(id, fs);
        glDeleteShader(vs);
        glDeleteShader(fs);
    }

    private void log(String s, String m) {
        if (compileLog == null) compileLog = "";
        compileLog += "== ["+s+"] ==\n" + m + "\n";
    }

    private int getCachedUniformLocation(String k) {
        int l;
        if (uniformsLocations.containsKey(k)) {
            l = uniformsLocations.get(k);
        } else {
            uniformsLocations.put(k, l = glGetUniformLocation(id, k));
        }
        return l;
    }

    public void setMat4f(String k, Matrix4f mat) {
        glUniformMatrix4fv(getCachedUniformLocation(k), false, mat.arrayColumn());
    }

    public void setMat4f(String k, org.joml.Matrix4f mat) {
        glUniformMatrix4fv(getCachedUniformLocation(k), false, mat.get(mat4x4array));
    }

    public void setFloat(String k, float f) {
        glUniform1f(getCachedUniformLocation(k), f);
    }

    public void setVec3f(String k, Vector3f vector3f) {
        glUniform3f(getCachedUniformLocation(k), vector3f.x, vector3f.y, vector3f.z);
    }

    public void setVec4f(String k, Vector4f vector3f) {
        glUniform4f(getCachedUniformLocation(k), vector3f.x, vector3f.y, vector3f.z, vector3f.w);
    }

    public void setInt(String k, int b) {
        glUniform1i(getCachedUniformLocation(k), b);
    }

    public boolean isCompile() {
        return isCompile;
    }

    public String getCompileLog() {
        return compileLog;
    }

    public void bind() {
        glUseProgram(id);
    }

    public void delete() {
        glDeleteProgram(id);
    }

    public static void unbind() {
        glUseProgram(0);
    }
}
