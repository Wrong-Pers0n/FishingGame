package main;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import fish.KoiFish;
import fish.Swarms;

public class JOGLRenderer implements GLEventListener {

    Main main;

    public JOGLRenderer(Main main) {
        this.main = main;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0f, 0f, 0f, 1f); // background color

        main.background.initBackground(drawable);

        main.player.initPlayer(drawable);

        main.setup(gl);

        for(Swarms swarm : main.swarms) {
            swarm.initFish(drawable);
        }
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);  // clear screen
        gl.glLoadIdentity();

        // Example: draw a red square

        main.background.drawBackground();

        main.player.drawPlayer();

        for(Swarms swarm : main.swarms) {
            swarm.draw();
        }

        //for(KoiFish fish : main.koiFish) {
        //    fish.drawFish();
        //}

        gl.glColor3f(1.0f, 0f, 0f);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(50, 50);
        gl.glVertex2f(150, 50);
        gl.glVertex2f(150, 150);
        gl.glVertex2f(50, 150);
        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, width, height, 0, -1, 1);  // 2D projection with top-left origin
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
}
