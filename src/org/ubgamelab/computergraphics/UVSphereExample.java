package org.ubgamelab.computergraphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.input.Keyboard;
import org.ubgamelab.computergraphics.util.CGApplication;

public class UVSphereExample extends CGApplication {

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
		
		glRotatef(90, 1, 0, 0);
		// Set foreground color 
		glColor3f(0, 0.7f, 1.0f);
		
		// Draw UV Sphere
		drawUVSphere(10, 10, 2);

		// Show Information
		renderText();

	}

	
	public void drawUVSphere(int stacks, int slices, float radius){
		for (int j = 0; j < stacks; j++) {
	         double latitude1 = (Math.PI/stacks) * j - Math.PI/2;
	         double latitude2 = (Math.PI/stacks) * (j+1) - Math.PI/2;
	         double sinLat1 = Math.sin(latitude1);
	         double cosLat1 = Math.cos(latitude1);
	         double sinLat2 = Math.sin(latitude2);
	         double cosLat2 = Math.cos(latitude2);
	         glBegin(GL_QUAD_STRIP);
	         for (int i = 0; i <= slices; i++) {
	            double longitude = (2*Math.PI/slices) * i;
	            double sinLong = Math.sin(longitude);
	            double cosLong = Math.cos(longitude);
	            double x1 = cosLong * cosLat1;
	            double y1 = sinLong * cosLat1;
	            double z1 = sinLat1;
	            double x2 = cosLong * cosLat2;
	            double y2 = sinLong * cosLat2;
	            double z2 = sinLat2;
	            glNormal3d(x2,y2,z2);
	            glVertex3d(radius*x2,radius*y2,radius*z2);
	            glNormal3d(x1,y1,z1);
	            glVertex3d(radius*x1,radius*y1,radius*z1);
	         }
	         glEnd();
	      }
	}
	
	private void renderText() {
		drawString(0, height - getDefaultFont().getLineHeight(), "UV Sphere Example.");
		drawString(0, height - 2 * getDefaultFont().getLineHeight(),
				"Press 'W' to toggle WIREFRAME MODE");
		drawString(0, height - 3 * getDefaultFont().getLineHeight(),
				"Press 'B' to toggle BACKFACE CULLING");
	}

	@Override
	public void deinit() {

	}

	public static void main(String[] args) {
		CGApplication app = new UVSphereExample();
		app.start(800, 600, false, false, true,
				"Computer Graphics Course : UV Sphere Example.");
	}

}
