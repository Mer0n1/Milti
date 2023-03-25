package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Button {
    private int x, y;
    private int sizex, sizey;

    private Texture button;
    private TextureRegion region;

    public Button(int x, int y) {
        button = new Texture("interface/button_fire.png");
        region = new TextureRegion(button);

        this.x = x;
        this.y = y;
        sizex = button.getWidth();
        sizey = button.getHeight();
    }

    public void setTexture(String path) {
        button = new Texture(path);
        sizex = button.getWidth();
        sizey = button.getHeight();
    }

    public boolean isPressed(int nj) { //где nj - номер нажатия
        if (nj == -1) return false;

        int cx = Gdx.input.getX(nj), cy = Gdx.graphics.getHeight() - Gdx.input.getY(nj);
        if (cx > x && cx < x + sizex && cy > y && cy < y + sizey)
            return true;

        return false;
    }

    public void render(SpriteBatch batch) {
        batch.draw(button, x, y);
    }
}
