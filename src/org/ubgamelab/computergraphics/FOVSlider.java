package org.ubgamelab.computergraphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.input.Keyboard;
import org.ubgamelab.computergraphics.util.CGApplication;

public class FOVSlider extends CGApplication {

	private boolean wireframe = false;
	private boolean backfaceculling = false;
	private boolean wPressed = false;
	private boolean bPressed = false;

	private float fovy = 45f;
	private float sliderSpeed = 0.1f;
	private float rot = 0f;
	private float rotSpeed = 0.05f;

	@Override
	public void init() {

	}

	@Override
	public void update(int delta) {
		toggleWireframe();
		toggleBackfaceCulling();
		updateFOVSlider();
		rot += delta * rotSpeed;
	}

	/**
	 * Toggle Wireframe Mode.
	 */
	private void updateFOVSlider() {
		if (Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
			if (fovy > 0) {
				fovy -= sliderSpeed;
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_MINUS)) {
			if (fovy < 180) {
				fovy += sliderSpeed;
			}
		}

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
		gluPerspective(fovy, (float) width / height, 0.1f, 100.0f);

		// Load Identity Matrix
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		gluLookAt(0, 0, 10f, 0, 0, 0, 0, 1, 0);

		// Clear color buffer to black
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Set background color to black
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// rotate
		glRotatef(rot, 0, 1, 0);
		
		// render pyramid
		drawPyramid();

		// Show Information
		renderText();

	}

	private void drawPyramid() {
		glBegin(GL_TRIANGLES);
		// 4-side of pyramid
		glColor3f(0.0f, 1.0f, 0.0f);
		glVertex3f(-2, -2, 2);
		glVertex3f(2, -2, 2);
		glVertex3f(0, 4, 0);

		glColor3f(0.0f, 1.0f, 1.0f);
		glVertex3f(2, -2, 2);
		glVertex3f(2, -2, -2);
		glVertex3f(0, 4, 0);

		glColor3f(1.0f, 1.0f, 0.0f);
		glVertex3f(2, -2, -2);
		glVertex3f(-2, -2, -2);
		glVertex3f(0, 4, 0);

		glColor3f(0.0f, 0.0f, 1.0f);
		glVertex3f(-2, -2, 2);
		glVertex3f(-2, -2, -2);
		glVertex3f(0, 4, 0);

		// Bottom of pyramid
		glColor3f(1.0f, 0.0f, 0.0f);
		glVertex3f(-2, -2, 2);
		glVertex3f(2, -2, 2);
		glVertex3f(-2, -2, -2);

		glColor3f(1.0f, 0.0f, 0.5f);
		glVertex3f(2, -2, 2);
		glVertex3f(2, -2, -2);
		glVertex3f(-2, -2, -2);

		glEnd();
	}

	private void renderText() {
		drawString(0, height - getDefaultFont().getLineHeight(),
				"Field Of View Slider.");
		drawString(0, height - 2 * getDefaultFont().getLineHeight(),
				"Press 'W' to toggle WIREFRAME MODE");
		drawString(0, height - 3 * getDefaultFont().getLineHeight(),
				"Press 'B' to toggle BACKFACE CULLING");
		drawString(0, height - 4 * getDefaultFont().getLineHeight(),
				"Press '+' or '-' to update Field Of View");
	}

	@Override
	public void deinit() {

	}

	public static void main(String[] args) {
		CGApplication app = new FOVSlider();
		app.start(800, 600, false, false, true,
				"Computer Graphics Course : FoV Slider.");
	}

}
