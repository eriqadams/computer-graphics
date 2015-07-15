package org.ubgamelab.computergraphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.input.Keyboard;
import org.ubgamelab.computergraphics.util.CGApplication;

public class DrawRectangleExample extends CGApplication {

	private boolean wireframe = false;
	private boolean backfaceculling = false;
	private boolean wPressed = false;
	private boolean bPressed = false;

	@Override
	public void init() {

	}

	@Override
	public void update(int delta) {
		toggleWireframe();
		toggleBackfaceCulling();
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
		gluPerspective(45.0f, (float)width/height, 0.1f, 100.0f);
		gluLookAt(0, 0, 10f, 0, 0, 0, 0, 1, 0);

		// Clear color buffer to black
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Set background color to black
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// Draw pyramid using triangle primitive mode
		glBegin(GL_TRIANGLES);

		// Set Foreground Color to Red
		glColor3f(0.7f, 0f, 0.2f);

		// Triangle 1
		glVertex3f(0, 0, 0f); // 0
		glVertex3f(4, 0, 0f); // 1
		glVertex3f(4, 4, 0f); // 2

		// Triangle 2
		//Wrong-Order
//		glVertex3f(4, 4, 0f); // 2
//		glVertex3f(0, 0, 0f); // 0
//		glVertex3f(0, 4, 0f); // 3
		
		// Correct-Order
		glVertex3f(4, 4, 0f); // 2
		glVertex3f(0, 4, 0f); // 3
		glVertex3f(0, 0, 0f); // 0

		glEnd();
		
		// Show Information
		renderText();

	}

	private void renderText() {
		drawString(0, height - getDefaultFont().getLineHeight(), "Draw a Rectangle.");
		drawString(0, height - 2 * getDefaultFont().getLineHeight(),
				"Press 'W' to toggle WIREFRAME MODE");
		drawString(0, height - 3 * getDefaultFont().getLineHeight(),
				"Press 'B' to toggle BACKFACE CULLING");
	}

	@Override
	public void deinit() {

	}

	public static void main(String[] args) {
		CGApplication app = new DrawRectangleExample();
		app.start(800, 600, false, false, true,
				"Computer Graphics Course : Draw Rectangle Example.");
	}

}
