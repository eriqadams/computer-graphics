package org.ubgamelab.computergraphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.input.Keyboard;
import org.ubgamelab.computergraphics.util.CGApplication;

public class PolygonCreator extends CGApplication {

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
		
		// Set foreground color 
		glColor3f(0, 0.7f, 1.0f);
		
		// Draw polygon
		drawPolygon(5, 2, 0, 0);

		// Show Information
		renderText();

	}

	/**
	 * Create Polygon.
	 * 
	 * @param radius
	 * @param xpos
	 * @param ypos
	 */
	public void drawPolygon(int sides, float radius, float xpos, float ypos) {
		int minTriangulation = sides - 2;
		float[] xarray = new float[sides];
		float[] yarray = new float[sides];

		// Calculate vertices.
		for (int i = 0; i < sides; i++) {
			double angle = (Math.PI / 2.0f) + ((i * 2 * Math.PI)/sides);
			xarray[i] = xpos + (float) (radius * Math.cos(angle));
			yarray[i] = ypos + (float) (radius * Math.sin(angle));
		}

		// Draw Polygon
		glBegin(GL_TRIANGLES);		
		for (int i = 0; i < minTriangulation; i++) {
			glVertex3f(xarray[0], yarray[0], 0);
			glVertex3f(xarray[i + 1], yarray[i + 1], 0);
			glVertex3f(xarray[i + 2], yarray[i + 2], 0);
		}
		glEnd();

	}

	private void renderText() {
		drawString(0, height - getDefaultFont().getLineHeight(), "Polygon Creator.");
		drawString(0, height - 2 * getDefaultFont().getLineHeight(),
				"Press 'W' to toggle WIREFRAME MODE");
		drawString(0, height - 3 * getDefaultFont().getLineHeight(),
				"Press 'B' to toggle BACKFACE CULLING");
	}

	@Override
	public void deinit() {

	}

	public static void main(String[] args) {
		CGApplication app = new PolygonCreator();
		app.start(800, 600, false, false, true,
				"Computer Graphics Course : Polygon Creator.");
	}

}
