#version 460

layout(location = 0) in vec3 vertex_position;
layout(location = 1) in vec3 vertex_color;

out vec3 color;

void main() {
	color = vertex_color;
	float m = 0;//3.1415926f/2.01;
	mat4 prog = mat4(
	cos(m), 0, sin(m), 0,
	0, 1, 0, 0,
	-sin(m), 0, cos(m), 0,
	0, 0, 0, 1);
	gl_Position = vec4(vertex_position, 1.0); // должно быть нормированным (-1 ; 1)
}