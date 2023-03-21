package com.mygdx.game.tank;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.HealthBar;
import com.mygdx.game.View;

public class Enemy extends Actor {

    private View view;
    private HealthBar healthBar;
    private AI ai;

    public Enemy() {
        super();
        view = new View(this);
        view.setTexture("tanks/enemy_tank_lvl2.png");

        hp = 100;

        healthBar = new HealthBar((int)hp, (int)hp);
        healthBar.setScale(0.4,0.4);
        view.PushViewObject(healthBar);

        ai = new AI(this, null, view);
    }
    @Override
    public void update() {
        healthBar.setPos(x - Width / 2, y + Height); //x - Width / 2, y + Height
        healthBar.setCurrent_hp(hp);
        healthBar.update();

        ai.update();
    }
    @Override
    public void render(SpriteBatch batch) { view.render(batch);}
    @Override
    public void damage(double damage) {
        hp -= damage + 1;
    }
}
