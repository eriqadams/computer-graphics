package org.ubgamelab.computergraphics;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.ubgamelab.computergraphics.util.CGApplication;
import org.ubgamelab.computergraphics.util.Camera;
import org.ubgamelab.computergraphics.util.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.util.glu.GLU.*;

public class VirtualCityExample extends CGApplication {

	private Camera camera;
	private final int[] m_texID = new int[9];
	private Texture m_texture;
	public final static int NO_WRAP = -1;

	private void drawSkyBox(float width, float height) {
		width = width / 2;

		// front side
		glBindTexture(GL_TEXTURE_2D, m_texID[0]);
		glBegin(GL_QUADS);
		glTexCoord2d(0, 0);
		glVertex3f(-width, 0, -width);
		glTexCoord2d(1, 0);
		glVertex3f(width, 0, -width);
		glTexCoord2d(1, 1);
		glVertex3f(width, height, -width);
		glTexCoord2d(0, 1);
		glVertex3f(-width, height, -width);
		glEnd();

		// right side
		glBindTexture(GL_TEXTURE_2D, m_texID[1]);
		glBegin(GL_QUADS);
		glTexCoord2d(0, 0);
		glVertex3f(width, 0, -width);
		glTexCoord2d(1, 0);
		glVertex3f(width, 0, width);
		glTexCoord2d(1, 1);
		glVertex3f(width, height, width);
		glTexCoord2d(0, 1);
		glVertex3f(width, height, -width);
		glEnd();

		// back side
		glBindTexture(GL_TEXTURE_2D, m_texID[2]);
		glBegin(GL_QUADS);
		glTexCoord2d(0, 0);
		glVertex3f(width, 0, width);
		glTexCoord2d(1, 0);
		glVertex3f(-width, 0, width);
		glTexCoord2d(1, 1);
		glVertex3f(-width, height, width);
		glTexCoord2d(0, 1);
		glVertex3f(width, height, width);
		glEnd();

		// left side
		glBindTexture(GL_TEXTURE_2D, m_texID[3]);
		glBegin(GL_QUADS);
		glTexCoord2d(0, 0);
		glVertex3f(-width, 0, width);
		glTexCoord2d(1, 0);
		glVertex3f(-width, 0, -width);
		glTexCoord2d(1, 1);
		glVertex3f(-width, height, -width);
		glTexCoord2d(0, 1);
		glVertex3f(-width, height, width);
		glEnd();

		// top side
		glBindTexture(GL_TEXTURE_2D, m_texID[4]);
		glBegin(GL_QUADS);
		glTexCoord2d(1, 1);
		glVertex3f(width, height, -width);
		glTexCoord2d(0, 1);
		glVertex3f(width, height, width);
		glTexCoord2d(0, 0);
		glVertex3f(-width, height, width);
		glTexCoord2d(1, 0);
		glVertex3f(-width, height, -width);
		glEnd();

		// bottom side
		glBindTexture(GL_TEXTURE_2D, m_texID[5]);
		glBegin(GL_QUADS);
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

	private void drawBuilding(float width, float height, int texID) {
		width = width / 2;

		glBindTexture(GL_TEXTURE_2D, m_texID[texID]);
		glBegin(GL_QUADS);
		// front side
		glTexCoord2d(0, 0);
		glVertex3f(-width, 0, width);
		glTexCoord2d(1, 0);
		glVertex3f(width, 0, width);
		glTexCoord2d(1, 1);
		glVertex3f(width, height, width);
		glTexCoord2d(0, 1);
		glVertex3f(-width, height, width);

		// right side
		glTexCoord2d(0, 0);
		glVertex3f(width, 0, width);
		glTexCoord2d(1, 0);
		glVertex3f(width, 0, -width);
		glTexCoord2d(1, 1);
		glVertex3f(width, height, -width);
		glTexCoord2d(0, 1);
		glVertex3f(width, height, width);

		// back side
		glTexCoord2d(0, 0);
		glVertex3f(width, 0, -width);
		glTexCoord2d(1, 0);
		glVertex3f(-width, 0, -width);
		glTexCoord2d(1, 1);
		glVertex3f(-width, height, -width);
		glTexCoord2d(0, 1);
		glVertex3f(width, height, -width);

		// left side
		glTexCoord2d(0, 0);
		glVertex3f(-width, 0, -width);
		glTexCoord2d(1, 0);
		glVertex3f(-width, 0, width);
		glTexCoord2d(1, 1);
		glVertex3f(-width, height, width);
		glTexCoord2d(0, 1);
		glVertex3f(-width, height, -width);

		// top side
		glVertex3f(-width, height, width);
		glVertex3f(width, height, width);
		glVertex3f(width, height, -width);
		glVertex3f(-width, height, -width);

		glEnd();

	}

	private void drawTree(float width, float height) {
		width = width / 2;

		glBindTexture(GL_TEXTURE_2D, m_texID[8]);
		glBegin(GL_QUADS);
		// front quads
		glTexCoord2d(0, 0);
		glVertex3f(-width, 0, 0);
		glTexCoord2d(1, 0);
		glVertex3f(width, 0, 0);
		glTexCoord2d(1, 1);
		glVertex3f(width, height, 0);
		glTexCoord2d(0, 1);
		glVertex3f(-width, height, 0);

		// cross quads
		glTexCoord2d(0, 0);
		glVertex3f(0, 0, width);
		glTexCoord2d(1, 0);
		glVertex3f(0, 0, -width);
		glTexCoord2d(1, 1);
		glVertex3f(0, height, -width);
		glTexCoord2d(0, 1);
		glVertex3f(0, height, width);
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

		if (wrap == -1) {
			return;
		}

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap);

	}

	@Override
	public void init() {
		// Initialise camera
		camera = new Camera();
		camera.init();
		camera.setCameraSpeed(0.005f);

		IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(9);
		glGenTextures(textureIDBuffer);
		m_texID[0] = textureIDBuffer.get(0);
		m_texID[1] = textureIDBuffer.get(1);
		m_texID[2] = textureIDBuffer.get(2);
		m_texID[3] = textureIDBuffer.get(3);
		m_texID[4] = textureIDBuffer.get(4);
		m_texID[5] = textureIDBuffer.get(5);
		m_texID[6] = textureIDBuffer.get(6);
		m_texID[7] = textureIDBuffer.get(7);
		m_texID[8] = textureIDBuffer.get(8);

		// Load 9 textures
		loadTexture(m_texID[0], "Data/0.png", false, GL_LINEAR,
				GL_CLAMP_TO_EDGE);
		loadTexture(m_texID[1], "Data/90.png", false, GL_LINEAR,
				GL_CLAMP_TO_EDGE);
		loadTexture(m_texID[2], "Data/180.png", false, GL_LINEAR,
				GL_CLAMP_TO_EDGE);
		loadTexture(m_texID[3], "Data/270.png", false, GL_LINEAR,
				GL_CLAMP_TO_EDGE);
		loadTexture(m_texID[4], "Data/up.png", false, GL_LINEAR,
				GL_CLAMP_TO_EDGE);
		loadTexture(m_texID[5], "Data/grass.png", false, GL_LINEAR, GL_REPEAT);
		loadTexture(m_texID[6], "Data/building01.png", false, GL_LINEAR,
				NO_WRAP);
		loadTexture(m_texID[7], "Data/building02.png", false, GL_LINEAR,
				NO_WRAP);
		loadTexture(m_texID[8], "Data/tree.png", true, GL_LINEAR, NO_WRAP);
	}

	@Override
	public void update(int delta) {
		// Update camera
		camera.update();
	}

	@Override
	public void render() {
		// Setting up default viewport
		glViewport(0, 0, width, height);

		// Set perspective projection
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(45.0f, (width) / (height), 1.0f, 700.0f);

		// Transformation
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		// Clear color buffer to black
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Set background color to black
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// Positioning camera
		camera.render();

		// Enable Depth Testing for correct z-ordering
		glEnable(GL_DEPTH_TEST);
		// Enable alpha test for correct transparency ordering
		glEnable(GL_ALPHA_TEST);
		// Display image pixel which transparency greater than 0.1
		glAlphaFunc(GL_GREATER, 0.1f);

		// Enable transparency of image with blending
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// Enable texturing
		glEnable(GL_TEXTURE_2D);

		// Render sky box
		drawSkyBox(500, 100);

		glTranslatef(-33, 0, -33);
		// Render trees and building
		for (int row = 0; row < 6; row++) {
			glPushMatrix();
			for (int col = 0; col < 6; col++) {
				drawBuilding(2, 3, 6);
				glTranslatef(3, 0, 0);
				drawBuilding(2, 6, 7);
				glTranslatef(3, 0, 0);
				drawBuilding(2, 5, 6);
				glTranslatef(2, 0, 0);
				drawTree(1, 2);
				glTranslatef(2, 0, 0);
				drawTree(1, 3);
				glTranslatef(2, 0, 0);
			}
			glPopMatrix();
			glTranslatef(0, 0, 12);
		}

		glDisable(GL_DEPTH_TEST);
		glDisable(GL_ALPHA_TEST);
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);

		renderText();
	}

	private void renderText() {
		drawString(0, height - getDefaultFont().getLineHeight(),
				"Virtual city : an example outdoor 3D scene using skybox.");
	}

	@Override
	public void deinit() {
		glDeleteTextures(m_texID[0]);
		glDeleteTextures(m_texID[1]);
		glDeleteTextures(m_texID[2]);
		glDeleteTextures(m_texID[3]);
		glDeleteTextures(m_texID[4]);
		glDeleteTextures(m_texID[5]);
		glDeleteTextures(m_texID[6]);
		glDeleteTextures(m_texID[7]);
		glDeleteTextures(m_texID[8]);
	}

	public static void main(String[] args) {
		VirtualCityExample app = new VirtualCityExample();
		app.start(800, 600, false, false, true,
				"Computer Graphic Course - VirtualCity");
	}

}
