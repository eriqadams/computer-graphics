package org.ubgamelab.computergraphics;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.ubgamelab.computergraphics.util.CGApplication;
import org.ubgamelab.computergraphics.util.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class TextureBlendingExample extends CGApplication {

	private Texture m_texture;
	private int m_texID;
	private float angle;

	@Override
	public void init() {

		// Load image data
		m_texture = new Texture();
		if (!m_texture.load("Data/tree.png")) {
			System.out.println("Failed to load texture\n");
			System.exit(1);
			return;
		}

		// Generate ID m_texID
		IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(1);
		glGenTextures(textureIDBuffer);
		m_texID = textureIDBuffer.get(0);
		// Activate texture m_texID
		glBindTexture(GL_TEXTURE_2D, m_texID);
		// Copy image data into texture memory
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, m_texture.getWidth(),
				m_texture.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE,
				m_texture.getImageData());

		// Set magnification and minification filter
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glDisable(GL_TEXTURE_2D);

	}

	@Override
	public void update(int delta) {
		angle += 0.05f;
	}

	private void drawTexturedQuads() {
		glBegin(GL_QUADS);
		glTexCoord2d(0, 0);
		glVertex3f(-2.0f, -2.0f, 0.0f);
		glTexCoord2d(1, 0);
		glVertex3f(2.0f, -2.0f, 0.0f);
		glTexCoord2d(1, 1);
		glVertex3f(2.0f, 2.0f, 0.0f);
		glTexCoord2d(0, 1);
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

		// Clear color buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Set background color to black
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		// Draw 2 TexturedQuads with transparency

		// Transform Quads
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		// Zoom-out
		glTranslatef(0.0f, 0.0f, -7.0f);
		// Always rotate-y
		glRotatef(angle, 0, 1, 0);

		// Enable Depth Testing for correct z-ordering
		glEnable(GL_DEPTH_TEST);
		// Enable alpha test for correct transparency ordering
		glEnable(GL_ALPHA_TEST);
		// Display image pixel which transparency greater than 0.1
		glAlphaFunc(GL_GREATER, 0.1f);

		// Enable transparency of image with blending
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// Enable 2D texturing
		glEnable(GL_TEXTURE_2D);
		// Activate texture m_texID
		glBindTexture(GL_TEXTURE_2D, m_texID);
		// Draw quads
		drawTexturedQuads();
		// Draw cross quads
		glRotatef(90, 0, 1, 0);
		drawTexturedQuads();

		glDisable(GL_DEPTH_TEST);
		glDisable(GL_ALPHA_TEST);
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);

		renderText();
	}

	private void renderText() {
		drawString(0, height - getDefaultFont().getLineHeight(),
				"Draw textured quads with transparency.");
	}

	@Override
	public void deinit() {
		glDeleteTextures(m_texID);
	}

	public static void main(String[] args) {
		TextureBlendingExample app = new TextureBlendingExample();
		app.start(800, 600, false, false, true,
				"Computer Graphic Course - Texture Mapping");
	}

}
