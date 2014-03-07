package id.ac.ub.ptiik.computergraphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class CG02_3DPrimitive extends CGApplication {

	protected boolean wireframe = true;

	@Override
	public void init() {
		// Setting up default viewport
		glViewport(0, 0, width, height);

		// Set perspective projection
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(45.0f, width / height, 1.0f, 100.0f);

	}

	@Override
	public void update(int delta) {

	}

	@Override
	public void render() {

		// Clear color buffer to black
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Set background color to black
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// Draw pyramid using triangle primitive mode
		// Render in wireframe or solid mode
		if (wireframe) {
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		} else {
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		}

		// Enable depth testing
		glEnable(GL_DEPTH_TEST);

		glBegin(GL_TRIANGLES);
		// 4-side of pyramid
		glColor3f(0.0f, 1.0f, 0.0f);
		glVertex3f(-2, -2, -10);
		glVertex3f(2, -2, -10);
		glVertex3f(0, 4, -12);

		glColor3f(0.0f, 1.0f, 1.0f);
		glVertex3f(2, -2, -10);
		glVertex3f(2, -2, -14);
		glVertex3f(0, 4, -12);

		glColor3f(1.0f, 1.0f, 0.0f);
		glVertex3f(2, -2, -14);
		glVertex3f(-2, -2, -14);
		glVertex3f(0, 4, -12);

		glColor3f(0.0f, 0.0f, 1.0f);
		glVertex3f(-2, -2, -10);
		glVertex3f(-2, -2, -14);
		glVertex3f(0, 4, -12);

		// Bottom of pyramid
		glColor3f(1.0f, 0.0f, 0.0f);
		glVertex3f(-2, -2, -10);
		glVertex3f(2, -2, -10);
		glVertex3f(-2, -2, -14);

		glColor3f(1.0f, 0.0f, 0.5f);
		glVertex3f(2, -2, -10);
		glVertex3f(2, -2, -14);
		glVertex3f(-2, -2, -14);

		glEnd();

		glDisable(GL_DEPTH_TEST);

	}

	@Override
	public void deinit() {

	}

	public static void main(String[] args) {
		CG02_3DPrimitive app = new CG02_3DPrimitive();
		app.start(800, 600, false, true, "DemoApp02 - 3D Primitives");
	}
}
