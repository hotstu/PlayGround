package io.github.hotstu.graphicfun.fireworks;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import io.github.hotstu.graphicfun.PVector;

import static io.github.hotstu.graphicfun.PContext.random;


class Particle {
    PVector location;
    PVector velocity;
    PVector acceleration;
    float lifespan;
    boolean seed = false;
    float hu;
    private long lastUpdate;

    Particle(float x, float y, float h) {
        hu = h;
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, random(-20, -10));
        location = new PVector(x, y);
        seed = true;
        lifespan = 255.0f;
    }

    Particle(PVector loc, float h) {
        hu = h;
        acceleration = new PVector(0, 0);
        velocity = PVector.random2D();
        velocity.mult(random(4, 8));
        location = loc.get();
        lifespan = 255.0f;
    }

    void applyForce(PVector force) {
        acceleration.add(force);
    }


    boolean explode() {
        if (seed && velocity.y > 0) {
            lifespan = 0;
            return true;
        }
        return false;
    }

    // Method to update location
    void update() {
        velocity.add(acceleration);
        location.add(velocity);
        if (!seed) {
            lifespan -= 5.0;
            velocity.mult(0.95f);
        }
        acceleration.mult(0);
    }

    // Method to display
    void display(Canvas canvas, Paint paint) {
        paint.setColor(HSBTOColor(hu, 1, 1, (int) lifespan));
        if (seed) {
            paint.setStrokeWidth(10);
        } else {
            paint.setStrokeWidth(4);
        }
        canvas.drawPoint(location.x, location.y, paint);
    }

    // Is the particle still useful?
    boolean isDead() {
        return lifespan <= 0;
    }


    private int HSBTOColor(float h, float s, float v, int alpha) {
        return Color.HSVToColor(alpha, new float[]{h, s, v});
    }
}