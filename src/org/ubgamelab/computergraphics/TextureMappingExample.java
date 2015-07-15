package org.ubgamelab.computergraphics;


import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.ubgamelab.computergraphics.util.CGApplication;
import org.ubgamelab.computergraphics.util.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.util.glu.GLU.*;
import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.*;

public class TextureMappingExample extends CGApplication {

	// image loader
	private Texture m_texture;
	// texture IDs
	private int m_texID;
	public final static int NO_WRAP = -1;

	@Override
	public void init() {
		// Generate 1 texture
		// Generate ID m_texID
		IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(1);
		glGenTextures(textureIDBuffer);
		textureIDBuffer.get(m_texID);
		// Load 1 texture
		loadTexture(m_texID, "Data/marble.png", false, GL_LINEAR, GL_REPEAT);
		// anisotropic
		// float anisotropic = 1.0f;
		//glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT, anisoparam);
		//glTexParameterf(GL_TEXTURE2D, GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT, anisoparam);
	}

	@Override
	public void update(int delta) {
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

		//gluBuild2DMipmaps(m_texID, GL_RGB, m_texture.getWidth(), m_texture.getHeight(), GL_RGB, GL_UNSIGNED_BYTE, m_texture.getImageData());
		
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

	@Override
	public void render() {
		// Setting up default viewport
		glViewport(0, 0, width, height);

		// Set perspective projection
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(90.0f, (width) / (height), 0.1f, 100.0f);

		// Transformation
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		// Clear color buffer to black
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Set background color to black
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// Zoom out
		glTranslatef(0.0f, -3.0f, -10.0f);

		// Enable Depth Testing for correct z-ordering
		glEnable(GL_DEPTH_TEST);

		// Enable texture 2D
		glEnable(GL_TEXTURE_2D);

		// Draw floor
		drawFloor(m_texID, 50);

		glDisable(GL_DEPTH_TEST);
		glDisable(GL_TEXTURE_2D);
		
		renderText();
	}

	private void renderText() {
		drawString(0, height - getDefaultFont().getLineHeight(),
				"Texture Mapping Example.");
	}

	@Override
	public void deinit() {
		glDeleteTextures(m_texID);
	}

	public static void main(String[] args) {
		TextureMappingExample app = new TextureMappingExample();
		app.start(800, 600, false, false, true,
				"Computer Graphic Course - Texture Mapping");
	}

}
