package org.ubgamelab.computergraphics;

import org.ubgamelab.computergraphics.util.CGApplication;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class JuliaFractalExample extends CGApplication {

	// each iteration, it calculates: new = old*old + c, where c is a constant
	// and old starts at current pixel
	private double cRe, cIm; // real and imaginary part of the constant c,
								// determinate shape of the Julia Set
	private double newRe, newIm, oldRe, oldIm; // real and imaginary parts of
												// new and old
	private double zoom = 1, moveX = 0, moveY = 0; // you can change these to
													// zoom and change position
	private int maxIterations = 300; // after how much iterations the function
										// should stop

	@Override
	public void init() {
		// pick some values for the constant c, this determines the shape of the
		// Julia Set
		cRe = -0.7;
		cIm = 0.27015;

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

		glBegin(GL_POINTS);
		// loop through every pixel
		for (int x = 0; x < width; x++) 
			for (int y = 0; y < height; y++) {
				// calculate the initial real and imaginary part of z, based on
				// the pixel location and zoom and position values
				newRe = 1.5 * (x - width / 2) / (0.5 * zoom * width) + moveX;
				newIm = (y - height / 2) / (0.5 * zoom * height) + moveY;
				// i will represent the number of iterations
				int i;
				// start the iteration process
				for (i = 0; i < maxIterations; i++) {
					// remember value of previous iteration
					oldRe = newRe;
					oldIm = newIm;
					// the actual iteration, the real and imaginary part are
					// calculated
					newRe = oldRe * oldRe - oldIm * oldIm + cRe;
					newIm = 2 * oldRe * oldIm + cIm;
					// if the point is outside the circle with radius 2: stop
					if ((newRe * newRe + newIm * newIm) > 4)
						break;
				}
				// use color model conversion to get rainbow palette, make
				// brightness black if maxIterations reached
				glColor3f(i%256f/255f, i%256/255f, i%256/255f);
				// draw the pixel
				glVertex2i(x, y);
			}

			glEnd();
		
	}

	
	@Override
	public void deinit() {

	}

	public static void main(String[] args) {
		JuliaFractalExample app = new JuliaFractalExample();
		app.start(800, 600, false, false, true,
				"Computer Graphic Course - Fractal Julia");

	}

}
