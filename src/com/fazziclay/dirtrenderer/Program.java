package com.fazziclay.dirtrenderer;

import com.fazziclay.javaneoutil.FileUtil;

import java.io.File;

import static org.lwjgl.opengl.GL20.*;

public class Program {
    int mProgram;
    int mVertexShader;
    int mFragmentShader;
    String name = "first";

    public Program() {
        mProgram = glCreateProgram();
        mVertexShader = loadShader("res/shaders/" + name + ".vert", GL_VERTEX_SHADER);
        mFragmentShader = loadShader("res/shaders/" + name + ".frag", GL_FRAGMENT_SHADER);
    }

    public void link() {
        glAttachShader(mProgram, mVertexShader);
        glAttachShader(mProgram, mFragmentShader);

        // линкуем
        glLinkProgram(mProgram);
    }

    void bindAttribute(int index, String name)
    {
        glBindAttribLocation(mProgram, index, name);
    }

    void use() {
        glUseProgram(mProgram);

        glGetShaderiv(mVertexShader, GL_COMPILE_STATUS, new int[1]);
        String s1 = glGetShaderInfoLog(mVertexShader);
        if (!s1.isEmpty()) {
            System.err.println("Vertex Shader error: " + s1);
        }


        glGetShaderiv(mFragmentShader, GL_COMPILE_STATUS, new int[1]);
        String s2 = glGetShaderInfoLog(mFragmentShader);
        if (!s2.isEmpty()) {
            System.err.println("Fragment Shader error: " + s2);
        }

    }

    void setFloat(String name, float value)
    {
        glUniform1f(getLocation(name), value);
    }


    /**
     * \brief Получить ключ атрибута для glUniform
     * \param name Название атрибута
     * \return Ключ
     */
    int getLocation(String name)
    {
        return glGetUniformLocation(mProgram, name);
    }

    private int loadShader(String s, int shaderType) {
        int shader = glCreateShader(shaderType);


        // да, тут именно указатель на указатель, char**
        glShaderSource(shader, FileUtil.getText(new File(s)));



        return shader;
    }
}
