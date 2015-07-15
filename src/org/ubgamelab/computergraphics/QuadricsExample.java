package org.ubgamelab.computergraphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FLAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.GLU_FILL;
import static org.lwjgl.util.glu.GLU.GLU_FLAT;
import static org.lwjgl.util.glu.GLU.GLU_LINE;
import static org.lwjgl.util.glu.GLU.GLU_POINT;
import static org.lwjgl.util.glu.GLU.gluLookAt;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Sphere;
import org.ubgamelab.computergraphics.util.CGApplication;
import org.ubgamelab.computergraphics.util.Texture;


public class QuadricsExample extends CGApplication {

	// image loader
	private Texture m_texture;
	// texture IDs
	private final int[] m_texID = new int[2];
	// Quadrics objects
	private Sphere g_wireframeSphere;
	private Cylinder g_texturedCylinder, g_flatshadedCylinder;
        public final static int NO_WRAP = -1;

	private void drawFloor(int texID, float width) {
		// floor
		glBindTexture(GL_TEXTURE_2D, texID);
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

	@Override
	public void init() {
		// Generate 2 textures
		IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(2);
		glGenTextures(textureIDBuffer);
		m_texID[0] = textureIDBuffer.get(0);
		m_texID[1] = textureIDBuffer.get(1);
		// Load 2 textures
		loadTexture(m_texID[0], "Data/marble.png", false, GL_LINEAR, GL_REPEAT);
		loadTexture(m_texID[1], "Data/crate.png", false, GL_LINEAR, NO_WRAP);

		// Initialise quadric objects

		// create an object to use with the wireframe draw stile
		g_wireframeSphere = new Sphere();
		g_wireframeSphere.setDrawStyle(GLU_LINE);
		
		// created an object that generates texture coordinates
		g_texturedCylinder = new Cylinder();
		g_texturedCylinder.setTextureFlag(true);

		// Enable flat shading
		//glShadeModel(GL_FLAT);

		// create an object that uses flat shading
		g_flatshadedCylinder = new Cylinder();
		g_flatshadedCylinder.setNormals(GLU_FLAT);
	}

	@Override
	public void update(int delta) {

	}

	@Override
	public void render() {
		// Setting up default viewport
		glViewport(0, 0, width, height);

		// Set perspective projection
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(45.0f, (float)width/height, 0.1f, 100.0f);
		gluLookAt(0, 0, 0.12f, 0, 0, 0, 0, 1, 0);

		// Transformation
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		// Clear color buffer to black
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Set background color to black
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		// Zoom out
		glTranslatef(0.0f, -3.0f, -10.0f);

		// Enable Depth Testing for correct z-ordering
		glEnable(GL_DEPTH_TEST);

		// Enable texture 2D
		glEnable(GL_TEXTURE_2D);

		// Draw floor
		drawFloor(m_texID[0], 10);

		// draw sphere
		glPushMatrix();
		glColor3f(1.0f, 1.0f, 1.0f);
		glTranslatef(-3.0f, 1.0f, 0.0f);
		g_wireframeSphere.draw(1.0f, 20, 20);
		glPopMatrix();

		// Draw cylinder
		glPushMatrix();
		glColor3f(1.0f, 1.0f, 1.0f);
		glRotatef(-90, 1, 0, 0);
		glBindTexture(GL_TEXTURE_2D, m_texID[1]);
		g_texturedCylinder.draw(1.0f, 1.0f, 2.0f, 20, 4);
		glPopMatrix();

		// Draw Cone
		glPushMatrix();
		glTranslatef(3.0f, 0.0f, 0.0f);
		glRotatef(-90, 1, 0, 0);
		glColor3f(1.0f, 0.0f, 0.0f);
		g_flatshadedCylinder.draw(1.0f, 0.0f, 2.0f, 32, 2);
		glPopMatrix();

		glDisable(GL_DEPTH_TEST);
		glDisable(GL_TEXTURE_2D);

		renderText();
	}

	private void renderText() {
		drawString(0, height - getDefaultFont().getLineHeight(),
				"Draw Sphere, Clylinder, Cone using OpenGL Quadrics.");
	}

	@Override
	public void deinit() {
		glDeleteTextures(m_texID[0]);
		glDeleteTextures(m_texID[1]);
	}

	public static void main(String[] args) {
		QuadricsExample app = new QuadricsExample();
		app.start(800, 600, false, false, true,
				"Computer Graphic Course - Quadrics");
	}

}
