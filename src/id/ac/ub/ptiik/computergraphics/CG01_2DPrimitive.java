/*
 * Copyright (c) 2014, Eriq Muhammad Adams J.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the University of Brawijaya nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY ERIQ MUHAMMAD ADAMS J. ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <copyright holder> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package id.ac.ub.ptiik.computergraphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class CG01_2DPrimitive extends CGApplication {

	protected boolean wireframe = false;

	@Override
	public void init() {
		// Setting up default viewport
		glViewport(0, 0, width, height);

		// Set orthographic projection
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(0, width, 0, height);

		// Reset ModelView Matrix
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

	}

	@Override
	public void update(int delta) {

	}

	@Override
	public void render() {
		// Clear color buffer to black
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Set background color to black
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// Draw red rectangle using triangle primitive mode
		// Render in wireframe or solid mode
		if (wireframe) {
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		} else {
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		}
		
		
		glBegin(GL_TRIANGLES);
		// Bottom triangle
		// Set color to red
		glColor3f(1.0f, 0.0f, 0.0f);		
		glVertex2f(width / 4.0f, height * 3.0f / 4.0f);
		// Set color to green
		glColor3f(0.0f, 1.0f, 0.0f);
		glVertex2f(width / 4.0f, height / 4.0f);
		// Set color to violet
		glColor3f(1.0f, 0.0f, 1.0f);
		glVertex2f(width * 3.0f / 4.0f, height / 4.0f);

		// Set color to blue
		glColor3f(0.0f, 0.0f, 1.0f);		
		// Top triangle
		glVertex2f(width / 4.0f, height * 3.0f / 4.0f);
		glVertex2f(width * 3.0f / 4.0f, height * 3.0f / 4.0f);
		glVertex2f(width * 3.0f / 4.0f, height / 4.0f);
		glEnd();

	}

	@Override
	public void deinit() {

	}

	public static void main(String[] args) {
		CG01_2DPrimitive app = new CG01_2DPrimitive();
		app.start(640, 480, false, true, "Demo App 01");
	}

}
