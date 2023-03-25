package com.mygdx.game.tank;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ui.HealthBar;
import com.mygdx.game.View;
import com.mygdx.game.system.AI;

public class Enemy extends PawnTank {

    private View view;
    private HealthBar healthBar;
    private AI ai;

    public Enemy() {
        view = getView();
        healthBar = getHealthBar();

        view.setTexture("tanks/enemy_tank_lvl2.png");
        healthBar.setScale(0.4,0.4);

        ai = new AI(this, view);
        hp = 100;
    }
    @Override
    public void update() {
        healthBar.setPos(x - Width / 2, y + Height);
        healthBar.setCurrent_hp(hp);
        healthBar.update();

        ai.update();
    }
    /*@Override
    public void render(SpriteBatch batch) {
        view.render(batch);
    }*/
    @Override
    public void damage(double damage) {
        hp -= damage + 1;
    }
}
