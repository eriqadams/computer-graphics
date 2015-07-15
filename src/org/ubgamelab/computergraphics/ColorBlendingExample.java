package org.ubgamelab.computergraphics;

import org.lwjgl.input.Keyboard;
import org.ubgamelab.computergraphics.util.CGApplication;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class ColorBlendingExample extends CGApplication {

	protected boolean blended = false;
	protected boolean pressed = false;

	@Override
	public void init() {

	}

	@Override
	public void update(int delta) {
		toggleBlend();
	}

	public void toggleBlend() {
		if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
			if (!pressed) {
				blended = !blended;
				pressed = true;
			}
		} else {
			pressed = false;
		}
	}

	private void drawThreeQuads() {
		// Activate model view matrix
		glMatrixMode(GL_MODELVIEW);
		// Reset model view matrix
		glLoadIdentity();

		// Draw red quads
		glTranslatef(0.0f, -2.0f, -10.0f);
		glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
		drawQuads();

		// Draw blue quads
		glTranslatef(1.0f, 2.0f, 0f);
		glColor4f(0.0f, 0.0f, 1.0f, 0.5f);
		drawQuads();

		// Draw green quads
		glTranslatef(2.0f, 1.0f, 0.0f);
		glColor4f(0.0f, 1.0f, 0.0f, 0.25f);
		drawQuads();

	}

	private void drawQuads() {
		glBegin(GL_QUADS);
		glVertex3f(-2.0f, -2.0f, 0.0f);
		glVertex3f(2.0f, -2.0f, 0.0f);
		glVertex3f(2.0f, 2.0f, 0.0f);
		glVertex3f(-2.0f, 2.0f, 0.0f);
		glEnd();
	}

	@Override
	public void render() {
		// Setting up default viewport
		glViewport(0, 0, width, height);

		// Set perspective projection
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(45.0f, (width) / (height), 1.0f, 100.0f);

		// Clear color buffer to black
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Set background color to white
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		// Draw with blending or not
		if (blended) {
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			drawThreeQuads();
			glDisable(GL_BLEND);
		} else {
			drawThreeQuads();
		}

		renderText();
	}

	private void renderText() {
		setFontColor(0.0f, 0.0f, 0.0f);
		drawString(0, height - getDefaultFont().getLineHeight(),
				"Example of Blending.");
		drawString(0, height - 2 * getDefaultFont().getLineHeight(),
				"Press 'B' to toggle BLEND MODE");
	}

	@Override
	public void deinit() {

	}

	public static void main(String[] args) {
		ColorBlendingExample app = new ColorBlendingExample();
		app.start(800, 600, false, false, true,
				"Computer Graphic Course - Blending");
	}

}
