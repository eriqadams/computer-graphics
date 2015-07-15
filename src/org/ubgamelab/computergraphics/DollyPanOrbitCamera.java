package org.ubgamelab.computergraphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.input.Keyboard;
import org.ubgamelab.computergraphics.util.CGApplication;

public class DollyPanOrbitCamera extends CGApplication {

	private boolean wireframe = false;
	private boolean backfaceculling = false;
	private boolean wPressed = false;
	private boolean bPressed = false;

	// camera position
	private float eyeX = 0;
	private float eyeY = 0;
	private float eyeZ = 8;

	// camera target/direction
	private float centerX = 0;
	private float centerY = 0;
	private float centerZ = 0;

	// UP camera
	private float upX = 0;
	private float upY = 1;
	private float upZ = 0;

	// Camera Move Speed
	private float camMoveSpeed = 0.005f;
	private float hAngle = (float) -Math.PI /2;
	private float vAngle = 0;
	private float camOrbitSpeed = 0.005f;

	@Override
	public void init() {

	}

	@Override
	public void update(int delta) {
		toggleWireframe();
		toggleBackfaceCulling();
		dollyCamera();
		panCamera();
		orbitCamera(delta);
	}

	private void orbitCamera(float delta) {
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			hAngle -= delta * camOrbitSpeed;
			updateOrbitHorizontalCamera(hAngle);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			hAngle += delta * camOrbitSpeed;
			updateOrbitHorizontalCamera(hAngle);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			vAngle -= delta * camOrbitSpeed * 0.001f;
			updateOrbitVerticalCamera(vAngle);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			vAngle += delta * camOrbitSpeed * 0.001f;
			updateOrbitVerticalCamera(vAngle);
		}

	}
	
	private void updateOrbitHorizontalCamera(float angle){
		float x = centerX - eyeX;
		float y = centerY - eyeY;
		float z = centerZ - eyeZ;
		float radius = (float)Math.sqrt(x*x + y*y + z*z);
		eyeX = centerX + (float)(radius * Math.cos(angle));
		eyeY = centerY;
		eyeZ = centerZ + (float)(radius * Math.sin(angle)); 
	}
	
	private void updateOrbitVerticalCamera(float angle){
		float x = centerX - eyeX;
		float y = centerY - eyeY;
		float z = centerZ - eyeZ;
		float radius = (float)Math.sqrt(x*x + y*y + z*z);
		eyeZ = centerZ + (float)(radius * Math.cos(angle)); 
		eyeY = centerY + (float)(radius * Math.sin(angle));
		//eyeX = centerX;
	}

	private void panCamera() {
		// right positive camera move speed and left negative camera move speed.
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			updatePanCamera(-camMoveSpeed);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			updatePanCamera(camMoveSpeed);
		}

	}

	private void updatePanCamera(float speed) {
		float x = centerX - eyeX;
		float z = centerZ - eyeZ;
		eyeX = eyeX + (-z * speed);
		eyeZ = eyeZ + (x * speed);
		centerX = centerX + (-z * speed);
		centerZ = centerZ + (x * speed);

	}

	private void dollyCamera() {
		// forward positive camera move speed and forward negative camera move speed.
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			updateDollyCamera(camMoveSpeed);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			updateDollyCamera(-camMoveSpeed);
		}

	}

	private void updateDollyCamera(float speed) {
		float x = centerX - eyeX;
		float z = centerZ - eyeZ;
		eyeX = eyeX + (x * speed);
		eyeZ = eyeZ + (z * speed);
		centerX = centerX + (x * speed);
		centerZ = centerZ + (z * speed);
	}

	/**
	 * Toggle Wireframe Mode.
	 */
	private void toggleWireframe() {
		if (Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
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
		gluPerspective(45, (float) width / height, 1f, 700.0f);

		// Load Identity Matrix
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		gluLookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);

		// Clear color buffer to black
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Set background color to black
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

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
				"Dolly, Pan, Orbit of Camera.");
		drawString(0, height - 2 * getDefaultFont().getLineHeight(),
				"Press 'TAB' to toggle WIREFRAME MODE");
		drawString(0, height - 3 * getDefaultFont().getLineHeight(),
				"Press 'B' to toggle BACKFACE CULLING");
		drawString(0, height - 4 * getDefaultFont().getLineHeight(),
				"Press 'W, S' to Dolly, 'A, D' to Pan, 'Left, Right, Up, Down Arrow' to Orbit.");
	}

	@Override
	public void deinit() {

	}

	public static void main(String[] args) {
		CGApplication app = new DollyPanOrbitCamera();
		app.start(800, 600, false, false, true,
				"Computer Graphics Course : Dolly, Pan, Orbit of Camera.");
	}

}
