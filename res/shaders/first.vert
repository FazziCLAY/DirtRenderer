#version 460

layout(location = 0) in vec3 vertex_position;
layout(location = 1) in vec3 vertex_color;
uniform mat4 model_matrix;
uniform mat4 view_matrix;
uniform mat4 projection_matrix;
uniform float color_shift;

out vec3 color;

void main() {
	color = (vertex_color + 1) / 2;
	color.r = color.r * color_shift;
	color.g = color.g * (color_shift / 2);
	color.b = color.b / color_shift;
	gl_Position = projection_matrix * view_matrix * model_matrix * vec4(vertex_position, 1.0); // должно быть нормированным (-1 ; 1)
}