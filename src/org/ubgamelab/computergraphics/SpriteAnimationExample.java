package org.ubgamelab.computergraphics;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.ubgamelab.computergraphics.util.CGApplication;
import org.ubgamelab.computergraphics.util.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class SpriteAnimationExample extends CGApplication {

	private float zoom = 1.0f;
	private Texture m_texture;
	private int m_texID;
	private int frame_idx, NUM_FRAME;
	private float frame_dur, frame_width;

	@Override
	public void init() {
		// Load image data
		m_texture = new Texture();
		if (!m_texture.load("Data/homeranim.png")) {
			System.out.println("Failed to load texture\n");
			return;
		}

		// Animation initialisation
		frame_idx = 0;
		frame_dur = 0;
		NUM_FRAME = 8;
		frame_width = (float) (1.0f / NUM_FRAME);

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
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

		glDisable(GL_TEXTURE_2D);
	}

	@Override
	public void update(int delta) {
		// Update animation
		frame_dur += delta;

		if (frame_dur >200) {
			frame_dur = 0;
			if (frame_idx == NUM_FRAME - 1) {
				frame_idx = 0;
			} else {
				frame_idx++;
			}

		}
		cameraZooming();
	}

	private void cameraZooming() {
        if (Mouse.isButtonDown(0) && !(zoom * 45 <= 0)) {
            zoom -= 0.001;
        }

        if (Mouse.isButtonDown(1) && !(zoom * 45 >= 180)) {
            zoom += 0.001;
        }
    }

	void drawAnimatedTexturedQuads() {
		glBegin(GL_QUADS);
		glTexCoord2d(frame_width * frame_idx, 0);
		glVertex3f(-2.0f, -3.0f, 0.0f);
		glTexCoord2d(frame_width * (frame_idx + 1), 0);
		glVertex3f(2.0f, -3.0f, 0.0f);
		glTexCoord2d(frame_width * (frame_idx + 1), 1);
		glVertex3f(2.0f, 3.0f, 0.0f);
		glTexCoord2d(frame_width * frame_idx, 1);
		glVertex3f(-2.0f, 3.0f, 0.0f);
		glEnd();
	}

	@Override
	public void render() {
		// Setting up default viewport
		glViewport(0, 0, width, height);

		// Set perspective projection
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(45.0f * zoom, (float) width / (float) height, 0.1f,
                100f);

		// Clear color buffer to black
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Set background color to black
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// Draw animated textured quads with transparency

		// Transform quads
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		// Zoom-out
		glTranslatef(0.0f, 0.0f, -20.0f);

		// Enable transparency of image with blending
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// Enable 2D texturing
		glEnable(GL_TEXTURE_2D);
		// Activate texture m_texID
		glBindTexture(GL_TEXTURE_2D, m_texID);
		// Draw animation
		drawAnimatedTexturedQuads();

		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);

		renderText();
	}

	private void renderText() {
		drawString(0, height - getDefaultFont().getLineHeight(),
				"Drawing 2D Sprite Animation : Homerbart Simpson Animation");
		drawString(0, height - 2 * getDefaultFont().getLineHeight(),
				"Press LMB or RMB to ZOOM IN and ZOOM OUT");
	}

	@Override
	public void deinit() {
		glDeleteTextures(m_texID);
	}

	public static void main(String[] args) {
		SpriteAnimationExample app = new SpriteAnimationExample();
		app.start(800, 600, false, false, true,
				"Computer Graphic Course - Sprite Animation");
	}

}
