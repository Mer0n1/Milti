package com.mygdx.game.tank;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ui.HealthBar;
import com.mygdx.game.View;
import com.mygdx.game.system.CombatSystem;

import java.util.List;

public class PawnTank extends Actor {
    protected CombatSystem combatSystem; //protected?
    protected HealthBar healthBar;
    protected View view;
    private double ControllerSpeed;

    PawnTank() {

        view = new View(this);
        healthBar = new HealthBar(hp, hp);
        combatSystem = new CombatSystem(this);
        view.PushViewObject(healthBar);

        ControllerSpeed = 1;
        speed = 5;
        hp = 100;
        healthBar.setMax_hp(hp);
    }

    @Override
    public void update() {

        combatSystem.update();
        healthBar.setCurrent_hp(hp);
        healthBar.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        view.render(batch);
    }

    @Override
    public void damage(double damage) {
        hp -= damage;
    }

    public void move() {
        if (ControllerSpeed != 0 & !Double.isNaN(ControllerSpeed)) {
            x += Math.cos(angle) * ControllerSpeed * speed;
            y += Math.sin(angle) * ControllerSpeed * speed;
        }
    }
    public void fire(Vector2 beginPoint, Vector2 endPoint, List<Bullet> bullets, double speedBullet) {
        bullets.add(new Bullet(beginPoint, endPoint, speedBullet));
        view.PushViewObject(bullets.get(bullets.size()-1)); //вывод пуль через рендер
    }

    public void setControllerSpeed(double ControllerSpeed) { this.ControllerSpeed = ControllerSpeed;}
    public void setHealthBar(HealthBar healthBar) { if (healthBar != null) { this.healthBar = healthBar;}}

    public HealthBar getHealthBar() { return healthBar; }
    public View getView() {return view;}
    public CombatSystem getCombatSystem() {return combatSystem;}
}
