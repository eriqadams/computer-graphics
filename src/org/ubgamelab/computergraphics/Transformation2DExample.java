package org.ubgamelab.computergraphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.input.Keyboard;
import org.ubgamelab.computergraphics.util.CGApplication;

public class Transformation2DExample extends CGApplication {

	private boolean wireframe = false;
	private boolean backfaceculling = false;
	private boolean wPressed = false;
	private boolean bPressed = false;
	private float angle = 0f;

	@Override
	public void init() {

	}

	@Override
	public void update(int delta) {
		toggleWireframe();
		toggleBackfaceCulling();
		angle -= delta * 0.1f;
	}

	/**
	 * Toggle Wireframe Mode.
	 */
	private void toggleWireframe() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			if (!wPressed) {
				wireframe = !wireframe;
				wPressed = true;
			}
		} else {
			wPressed = false;
		}

		// Render in wireframe or solid mode
		if (wireframe) {
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		} else {
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		}

	}

	private void toggleBackfaceCulling() {
		if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
			if (!bPressed) {
				backfaceculling = !backfaceculling;
				bPressed = true;
			}
		} else {
			bPressed = false;
		}

		// Render in wireframe or solid mode
		if (backfaceculling) {
			glEnable(GL_CULL_FACE);
		} else {
			glDisable(GL_CULL_FACE);
		}
	}

	@Override
	public void render() {
		// Setting up default viewport
		glViewport(0, 0, width, height);
		// Set perspective projection
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(45.0f, (float) width / height, 0.1f, 100.0f);
		gluLookAt(0, 0, 15f, 0, 0, 0, 0, 1, 0);

		// Load Identity Matrix
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		// Clear color buffer to black
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Set background color to black
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// render rectangle
		glTranslatef(4, 4, 0);
		glRotatef(angle, 0, 0, 1);
		glTranslatef(-4, -4, 0);
		drawRectangle();

		// Show Information
		renderText();

	}

	private void drawRectangle() {
		glBegin(GL_QUADS);
		// 4-side of pyramid
		glColor3f(0.0f, 0.0f, 1.0f);
		glVertex3f(3, 5, 0);
		glVertex3f(5, 5, 0);
		glVertex3f(5, 3, 0);
		glVertex3f(3, 3, 0);
		glEnd();
	}

	private void renderText() {
		drawString(0, height - getDefaultFont().getLineHeight(),
				"2D Transformation Example.");
		drawString(0, height - 2 * getDefaultFont().getLineHeight(),
				"Press 'W' to toggle WIREFRAME MODE");
		drawString(0, height - 3 * getDefaultFont().getLineHeight(),
				"Press 'B' to toggle BACKFACE CULLING");
	}

	@Override
	public void deinit() {

	}

	public static void main(String[] args) {
		CGApplication app = new Transformation2DExample();
		app.start(800, 600, false, false, true,
				"Computer Graphics Course : 2D Transformation Example.");
	}

}
