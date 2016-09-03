package io.github.hotstu.graphicfun.fireworks;// Daniel Shiffman


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import io.github.hotstu.graphicfun.PVector;

import static io.github.hotstu.graphicfun.PContext.random;

class Firework {
    private final static PVector gravity = new PVector(0f, 0.2f);
    ArrayList<Particle> particles;    // An arraylist for all the particles
    Particle firework;
    float hu;

    Firework(float x, float y) {
        hu = random(255);
        firework = new Particle(x, y, hu);
        particles = new ArrayList<>();   // Initialize the arraylist
    }

    boolean done() {
        return (firework == null && particles.isEmpty());
    }

    boolean firstExplode = false;
    void run(Canvas canvas, Paint paint) {
        if (firework != null) {
            firework.applyForce(gravity);
            firework.update();
            firework.display(canvas, paint);

            if (firework.explode()) {
                for (int i = 0; i < 80; i++) {
                    particles.add(new Particle(firework.location, hu));    // Add "num" amount of particles to the arraylist
                }
                firework = null;
                firstExplode = true;
            }
        }
        if (firstExplode) {
            firstExplode = false;
            canvas.drawColor(Color.HSVToColor(new float[]{hu, 0.6f, 0.6f}));
        }

        for (int i = particles.size() - 1; i >= 0; i--) {
            Particle p = particles.get(i);
            p.applyForce(gravity);
            p.update();
            p.display(canvas, paint);
            if (p.isDead()) {
                particles.remove(i);
            }
        }
    }

}