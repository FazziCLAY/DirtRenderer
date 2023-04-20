package com.fazziclay.dirtrenderer.rendering.opengl;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDeleteShader;

public class ShaderProgram {

    private final int program;
    private String compileLog = "";
    private boolean isCompile = true;

    public ShaderProgram(String vertex, String fragment) {
        int vs = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vs, vertex);
        glCompileShader(vs);
        int[] status = new int[1];
        glGetShaderiv(vs, GL_COMPILE_STATUS, status);
        if (status[0] == GL_FALSE) {
            compileLog += "\n== VERTEX SHADER LOG ==\n"+glGetShaderInfoLog(vs);
            isCompile = false;
        }

        int fs = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fs, fragment);
        glCompileShader(fs);
        status = new int[1];
        glGetShaderiv(fs, GL_COMPILE_STATUS, status);
        if (status[0] == GL_FALSE) {
            compileLog += "\n== FRAGMENT SHADER LOG ==\n"+glGetShaderInfoLog(fs);
            isCompile = false;
        }

        program = glCreateProgram();
        glAttachShader(program, vs);
        glAttachShader(program, fs);
        glLinkProgram(program);

        glDetachShader(program, vs);
        glDetachShader(program, fs);
        glDeleteShader(vs);
        glDeleteShader(fs);
    }

    public boolean isCompile() {
        return isCompile;
    }

    public String getCompileLog() {
        return compileLog;
    }

    public void bind() {
        glUseProgram(program);
    }

    public void delete() {
        glDeleteProgram(program);
    }

    public static void unbind() {
        glUseProgram(0);
    }
}
