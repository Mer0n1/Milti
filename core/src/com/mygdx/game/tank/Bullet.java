package com.mygdx.game.tank;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ViewObject;

public class Bullet implements ViewObject {
    private final Texture texture;
    private final TextureRegion textureRegion;
    private final int Width, Height;

    private double length;
    private double cos;
    private double sin;
    private float angle;

    private Vector2 begin, end, CurrentPosition;
    private double speed = 10;

    public Bullet(Vector2 begin, Vector2 end, double speed)
    {
        texture = new Texture("bullet.png");
        textureRegion = new TextureRegion(texture);
        Width = texture.getWidth();
        Height = texture.getHeight();

        this.begin = new Vector2(begin);
        this.end = new Vector2(end);
        this.CurrentPosition = new Vector2(begin);
        this.speed = speed;
        angle = 0;

        start();
    }

    private void start()
    {
        length = Math.sqrt((end.x - begin.x)*(end.x - begin.x) + ((end.y - begin.y)*(end.y - begin.y)));
        cos = (end.x - begin.x) / length;
        sin = (end.y - begin.y) / length;
        angle = (float) Math.acos(cos) * 180 / 3.14f;
        if (cos > 0 && sin < 0 || sin < 0 && cos < 0)
            angle = 180 - angle + 180;
    }

    public Vector2 getCurrentPosition() { return CurrentPosition; }

    public Vector2 getEndPosition() { return end; }

    public Vector2 getBeginPosition() { return begin; }

    public float getAngle() {
        return angle;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) { this.speed = speed;}

    @Override
    public void render(SpriteBatch batch) {
        CurrentPosition.add((float)(cos*speed), (float)(sin*speed));

        batch.draw(textureRegion,
                CurrentPosition.x, CurrentPosition.y,
                0,0,
                Width, Height,
                1, 1,
                angle);
    }
}
