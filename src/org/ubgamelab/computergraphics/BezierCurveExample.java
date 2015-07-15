package org.ubgamelab.computergraphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.ubgamelab.computergraphics.util.CGApplication;

public class BezierCurveExample extends CGApplication {

	FloatBuffer ctrlPoints = (FloatBuffer) BufferUtils
			.createFloatBuffer(12)
			.put(new float[] { 90.0f, 90.0f, 0.0f, 300.0f, 540.0f, 0.0f, 460.0f,
					90.0f, 0.0f, 670.0f, 540.0f, 0.0f }).flip();

	@Override
	public void init() {

	}

	@Override
	public void update(int delta) {

	}

	@Override
	public void render() {
		// Setting up default viewport
		glViewport(0, 0, width, height);

		// Set orthographic projection
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(0, width, 0, height);

		// Reset ModelView Matrix
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		// Clear color buffer to black
		glClear(GL_COLOR_BUFFER_BIT);

		// Set background color to black
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glMap1f(GL_MAP1_VERTEX_3, 0.0f, 1.0f, 3, 4, ctrlPoints);
		glEnable(GL_MAP1_VERTEX_3);
		
		int i;

		
		glColor3f(1.0f, 1.0f, 0.0f);
		glLineStipple(1, (short)0x3F07);
		glEnable(GL_LINE_STIPPLE);
		glBegin(GL_LINE_STRIP);
		for (i = 0; i <= 30; i++)
			glEvalCoord1f(i / 30.0f);
		glEnd();

		/* The following code displays the control points as dots. */
	
		 glPointSize(5.0f); 
		 glColor3f(1.0f, 0.0f, 0.0f); 
		 glBegin(GL_POINTS);
		 for (i = 0; i < 4; i++) {
			glVertex3f(ctrlPoints.get(i*3), ctrlPoints.get((i*3)+1), ctrlPoints.get((i*3)+2));
		} 
		glEnd();
		 
		drawString(0, height - getFontSize(),
				"Example of Bezier Curve with 4 Control Points.");

	}

	@Override
	public void deinit() {

	}

	public static void main(String[] args) {
		BezierCurveExample app = new BezierCurveExample();
		app.start(800, 600, false, false, true,
				"Computer Graphics Course - Bezier Curve Example");

	}

}
