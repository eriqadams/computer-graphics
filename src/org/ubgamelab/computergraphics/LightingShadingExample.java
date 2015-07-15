package org.ubgamelab.computergraphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.ubgamelab.computergraphics.util.CGApplication;
import org.ubgamelab.computergraphics.util.Camera;
import org.ubgamelab.computergraphics.util.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class LightingShadingExample extends CGApplication {

	// image loader
	private Texture m_texture;
	// texture IDs
	private final int[] m_texID = new int[2];
	private boolean KEY_0_pressed, KEY_1_pressed, KEY_2_pressed, light0_ON,
			light1_ON, light2_ON;
	public final static int NO_WRAP = -1;
	private Camera camera;

	@Override
	public void init() {
		camera = new Camera();
		camera.init();
		camera.setCameraSpeed(0.005f);
		// init vars
		KEY_0_pressed = KEY_1_pressed = KEY_2_pressed = false;
		light0_ON = light1_ON = light2_ON = false;

		// Generate 2 textures
		// Generate ID m_texID
		IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(2);
		glGenTextures(textureIDBuffer);
		textureIDBuffer.get(m_texID);
		// Load 2 textures
		loadTexture(m_texID[0], "Data/marble.png", false, GL_LINEAR, GL_REPEAT);
		loadTexture(m_texID[1], "Data/crate.png", false, GL_LINEAR, NO_WRAP);

		// setup lighting
		// Lighting variables
		FloatBuffer ambientPointLight = (FloatBuffer) BufferUtils
				.createFloatBuffer(4)
				.put(new float[] { 0.5f, 0.5f, 0.5f, 1.0f }).flip(); // ambient
																		// light

		FloatBuffer diffusePointLight = (FloatBuffer) BufferUtils
				.createFloatBuffer(4)
				.put(new float[] { 0.5f, 0.5f, 0.5f, 1.0f }).flip(); // diffuse
																		// light

		FloatBuffer specularPointLight = (FloatBuffer) BufferUtils
				.createFloatBuffer(4)
				.put(new float[] { 1.0f, 1.0f, 1.0f, 1.0f }).flip(); // specular
		// light

		FloatBuffer ambientDirectionalLight = (FloatBuffer) BufferUtils
				.createFloatBuffer(4)
				.put(new float[] { 0.0f, 1.0f, 0.0f, 1.0f }).flip(); // ambient

		// light
		FloatBuffer diffuseDirectionalLight = (FloatBuffer) BufferUtils
				.createFloatBuffer(4)
				.put(new float[] { 0.0f, 1.0f, 0.0f, 1.0f }).flip(); // diffuse
		// light

		FloatBuffer ambientSpotLight = (FloatBuffer) BufferUtils
				.createFloatBuffer(4)
				.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f }).flip(); // ambient
																		// light

		FloatBuffer diffuseSpotLight = (FloatBuffer) BufferUtils
				.createFloatBuffer(4)
				.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f }).flip(); // diffuse
																		// light

		FloatBuffer directionallightPosition = (FloatBuffer) BufferUtils
				.createFloatBuffer(4)
				.put(new float[] { 6.0f, 0.5f, 0.0f, 0.0f }).flip(); // spotlight

		// position
		FloatBuffer pointlightPosition = (FloatBuffer) BufferUtils
				.createFloatBuffer(4)
				.put(new float[] { 6.0f, 0.5f, 0.0f, 1.0f }).flip(); // spotlight

		// position
		FloatBuffer spotlightPosition = (FloatBuffer) BufferUtils
				.createFloatBuffer(4)
				.put(new float[] { 10.0f, 3.0f, 0.0f, 1.0f }).flip(); // spotlight

		// position
		FloatBuffer spotlightDirection = (FloatBuffer) BufferUtils
				.createFloatBuffer(4)
				.put(new float[] { -1.0f, 0.0f, -1.0f, 1.0f }).flip(); // point
																		// spotlight
		// downwards

		// Material variables
		FloatBuffer matAmbient = (FloatBuffer) BufferUtils.createFloatBuffer(4)
				.put(new float[] { 1.0f, 1.0f, 1.0f, 1.0f }).flip(); // ambient
																		// material

		FloatBuffer matDiff = (FloatBuffer) BufferUtils.createFloatBuffer(4)
				.put(new float[] { 1.0f, 1.0f, 1.0f, 1.0f }).flip(); // diffuse
																		// material

		FloatBuffer matSpecular = (FloatBuffer) BufferUtils
				.createFloatBuffer(4)
				.put(new float[] { 1.0f, 1.0f, 1.0f, 1.0f }).flip(); // specular
																		// material

		// Now setup LIGHT0
		glLight(GL_LIGHT0, GL_AMBIENT, ambientPointLight); // setup the
		// ambient
		// element
		glLight(GL_LIGHT0, GL_DIFFUSE, diffusePointLight); // the diffuse
		// element
		glLight(GL_LIGHT0, GL_SPECULAR, specularPointLight); // the specular
		// element
		glLight(GL_LIGHT0, GL_POSITION, pointlightPosition); // place the
		// light in the
		// world

		// Now setup LIGHT1
		glLight(GL_LIGHT1, GL_AMBIENT, ambientDirectionalLight); // setup the
		// ambient
		// element
		glLight(GL_LIGHT1, GL_DIFFUSE, diffuseDirectionalLight); // the
		// diffuse
		// element
		glLight(GL_LIGHT1, GL_POSITION, directionallightPosition); // place
		// the
		// light
		// in
		// the
		// world

		// Now setup LIGHT2
		glLight(GL_LIGHT2, GL_AMBIENT, ambientSpotLight); // setup the ambient
		// element
		glLight(GL_LIGHT2, GL_DIFFUSE, diffuseSpotLight); // the diffuse
		// element
		glLight(GL_LIGHT2, GL_POSITION, spotlightPosition); // place the light
		// in the world

		glLightf(GL_LIGHT2, GL_SPOT_CUTOFF, 15.0f);
		glLightf(GL_LIGHT2, GL_SPOT_EXPONENT, 5.0f);
		glLight(GL_LIGHT2, GL_SPOT_DIRECTION, spotlightDirection);

		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

		glMaterial(GL_FRONT, GL_SPECULAR, matSpecular);
		glMaterial(GL_FRONT, GL_AMBIENT, matAmbient);
		glMaterial(GL_FRONT, GL_DIFFUSE, matDiff);
		glMaterialf(GL_FRONT, GL_SHININESS, 10.0f);
	}

	@Override
	public void update(int delta) {
		camera.update();
		toggleKey();
	}

	public void toggleKey() {
		// Toggle light 0,1,2
		if (Keyboard.isKeyDown(Keyboard.KEY_0)) {
			if (!KEY_0_pressed) {
				light0_ON = !light0_ON;
				KEY_0_pressed = true;
			}
		} else {
			KEY_0_pressed = false;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
			if (!KEY_1_pressed) {
				light1_ON = !light1_ON;
				KEY_1_pressed = true;
			}
		} else {
			KEY_1_pressed = false;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
			if (!KEY_2_pressed) {
				light2_ON = !light2_ON;
				KEY_2_pressed = true;
			}
		} else {
			KEY_2_pressed = false;
		}
	}

	void drawCube(int texID, float width, float height) {
		width = width / 2;
		glBindTexture(GL_TEXTURE_2D, texID);
		glBegin(GL_QUADS);
		// front side
		glNormal3f(0, 0, 1);
		glTexCoord2d(0, 0);
		glVertex3f(-width, 0, width);
		glTexCoord2d(1, 0);
		glVertex3f(width, 0, width);
		glTexCoord2d(1, 1);
		glVertex3f(width, height, width);
		glTexCoord2d(0, 1);
		glVertex3f(-width, height, width);

		// right side
		glNormal3f(1, 0, 0);
		glTexCoord2d(0, 0);
		glVertex3f(width, 0, width);
		glTexCoord2d(1, 0);
		glVertex3f(width, 0, -width);
		glTexCoord2d(1, 1);
		glVertex3f(width, height, -width);
		glTexCoord2d(0, 1);
		glVertex3f(width, height, width);

		// back side
		glNormal3f(0, 0, -1);
		glTexCoord2d(0, 0);
		glVertex3f(width, 0, -width);
		glTexCoord2d(1, 0);
		glVertex3f(-width, 0, -width);
		glTexCoord2d(1, 1);
		glVertex3f(-width, height, -width);
		glTexCoord2d(0, 1);
		glVertex3f(width, height, -width);

		// left side
		glNormal3f(-1, 0, 0);
		glTexCoord2d(0, 0);
		glVertex3f(-width, 0, -width);
		glTexCoord2d(1, 0);
		glVertex3f(-width, 0, width);
		glTexCoord2d(1, 1);
		glVertex3f(-width, height, width);
		glTexCoord2d(0, 1);
		glVertex3f(-width, height, -width);

		// top side
		glNormal3f(0, 1, 0);
		glTexCoord2d(0, 0);
		glVertex3f(-width, height, width);
		glTexCoord2d(1, 0);
		glVertex3f(width, height, width);
		glTexCoord2d(1, 1);
		glVertex3f(width, height, -width);
		glTexCoord2d(0, 1);
		glVertex3f(-width, height, -width);

		// bottom side
		glNormal3f(0, -1, 0);
		glTexCoord2d(0, 0);
		glVertex3f(-width, 0, width);
		glTexCoord2d(1, 0);
		glVertex3f(width, 0, width);
		glTexCoord2d(1, 1);
		glVertex3f(width, 0, -width);
		glTexCoord2d(0, 1);
		glVertex3f(-width, 0, -width);

		glEnd();
	}

	void drawFloor(int texID, float width) {
		// floor
		glBindTexture(GL_TEXTURE_2D, texID);
		glBegin(GL_QUADS);
		glNormal3f(0, 1, 0);
		glTexCoord2d(0, 0);
		glVertex3f(-width, 0, width);
		glTexCoord2d(width, 0);
		glVertex3f(width, 0, width);
		glTexCoord2d(width, width);
		glVertex3f(width, 0, -width);
		glTexCoord2d(0, width);
		glVertex3f(-width, 0, -width);
		glEnd();
	}

	private void loadTexture(int texID, String path, boolean transparent,
			int filter, int wrap) {
		m_texture = new Texture();
		if (!m_texture.load(path)) {
			System.out.println("Failed to load texture\n");
			return;
		}

		glBindTexture(GL_TEXTURE_2D, texID);

		if (transparent) {
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, m_texture.getWidth(),
					m_texture.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE,
					m_texture.getImageData());
		} else {
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, m_texture.getWidth(),
					m_texture.getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE,
					m_texture.getImageData());
		}

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);

		if (wrap == NO_WRAP) {
			return;
		}

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap);

	}

	private void toggleLighting(int lightID, boolean on) {
		if (on) {
			glEnable(lightID);
		} else {
			glDisable(lightID);
		}
	}

	@Override
	public void render() {
		// Setting up default viewport
		glViewport(0, 0, width, height);

		// Set perspective projection
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(45.0f, (width) / (height), 1.0f, 100.0f);

		// Transformation
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		// Clear color buffer to black
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Set background color to black
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		camera.render();
		
		// Zoom out
		glTranslatef(0.0f, -3.0f, -10.0f);

		// Enable lighting
		glEnable(GL_LIGHTING);

		// toggle lighting
		toggleLighting(GL_LIGHT0, light0_ON);
		toggleLighting(GL_LIGHT1, light1_ON);
		toggleLighting(GL_LIGHT2, light2_ON);

		// Enable Depth Testing for correct z-ordering
		glEnable(GL_DEPTH_TEST);

		// Enable texture 2D
		glEnable(GL_TEXTURE_2D);

		// Draw floor
		drawFloor(m_texID[0], 10);

		// Draw cube
		drawCube(m_texID[1], 2, 2);

		glDisable(GL_DEPTH_TEST);
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_LIGHTING);
		renderText();
	}

	private void renderText() {
		drawString(0, height - getDefaultFont().getLineHeight(),
				"Lighting & Shading Example.");
		drawString(0, height - 2 * getDefaultFont().getLineHeight(),
				"Press 0, 1, 2, to turn on/off point light, directional light, spot light.");
	}

	@Override
	public void deinit() {
		glDeleteTextures(m_texID[0]);
		glDeleteTextures(m_texID[1]);
	}

	public static void main(String[] args) {
		LightingShadingExample app = new LightingShadingExample();
		app.start(800, 600, false, false, true,
				"Computer Graphic Course - Lighting & Shading");
	}

}
