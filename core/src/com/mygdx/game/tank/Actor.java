package com.mygdx.game.tank;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Map.Collision;
import com.mygdx.game.Map.World;

public abstract class Actor {
    protected float x, y;
    protected double hp;
    protected double damage;
    protected double speed;
    protected float angle; //в радианах
    protected float Width, Height;

    protected static World world;
    protected static Collision collision;


    Actor() {
        x = 0;
        y = 0;
        hp = 1;
        damage = 1;
        speed = 1;
        angle = 0;
    }
    public static void init(World world, Collision collision) {
        Actor.world = world;
        Actor.collision = collision;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public double getHp() {
        return hp;
    }

    public double getDamage() {
        return damage;
    }

    public double getSpeed() {
        return speed;
    }

    public float getAngle() {
        return angle;
    }

    public float getWidth() {
        return Width;
    }

    public float getHeight() {
        return Height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setWidth(float width) {
        Width = width;
    }

    public void setHeight(float height) {
        Height = height;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setCoord(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void addHp(double hp) {this.hp += hp;}
    public void addSpeed(double speed) {this.speed += speed;}
    public void addAngle(double angle) {this.angle += angle;}
    public void addX(double x) {this.x += x;}
    public void addY(double y) {this.y += y;}

    public abstract void update();
    public abstract void render(SpriteBatch batch);
    public abstract void damage(double damage);

    public static World getWorld() {return world;}

    public double distanceTo(float x, float y) {
        return Math.abs(Math.sqrt((x - this.x)*(x - this.x) + (y - this.y )*(y - this.y)));
    }
}
