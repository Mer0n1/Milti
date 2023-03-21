package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.tank.Actor;

public class InterfaceViewController {
    private Button fire_button;
    private HealthBar healthBar;

    private final int xButtonFire = Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 5;
    private final int yButtonFire = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2;

    public InterfaceViewController(Button fire_button, HealthBar healthBar) {
        this.fire_button = fire_button;
        this.healthBar = healthBar;

        //healthBar.setScale(0.4, 0.4);
        this.healthBar.setPos(100, 100);
    }


    public void render(SpriteBatch batch) {
        fire_button.render(batch);
        healthBar.render(batch);
    }
}
