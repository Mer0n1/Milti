package com.mygdx.game.system;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.View;
import com.mygdx.game.tank.Actor;
import com.mygdx.game.tank.Bullet;
import com.mygdx.game.tank.PawnTank;
import com.mygdx.game.ui.CallbackList;

import java.util.ArrayList;
import java.util.List;

//боевая система танка
public class CombatSystem {
    private PawnTank actor;
    private boolean isFire;
    private View view;

    private List<Actor> actors;

    //Выстрелы (пули)
    private CallbackList<Bullet> bullets;
    protected float Delay = 0.5f; //задержка между выстрелами
    protected float saveTime = 0; //сохраненное время для синхронизации с задержкой
    public final float distanceOfDeleting = 1000; //дистанция от actor при которой удаляются пули
    private float speedBullet; //скорость пули

    //Время
    protected final long NULLTIME = System.currentTimeMillis(); //вероятно таймер или время будут передаваться также по все обьектам
    protected float Time = 0;

    public CombatSystem(PawnTank actor) {
        bullets = new CallbackList<>();
        //this.controller = controller;
        this.actor = actor;
        this.view = actor.getView();
        speedBullet = 10;
        isFire = false;

        actors = Actor.getWorld().getListActors();
    }

    public void update() {
        //обновление времени
        Time = (System.currentTimeMillis() - NULLTIME) / 1000f;
        //Обработка выстрелов
        if (Time > saveTime) {
            saveTime = Time + Delay;

            if (isFire)
                fire(); //play music
        }
        UpdateInteractFires();
    }

    public void UpdateInteractFires() { //проверка пуль на столкновения и удаление пуль
        //столкновение пуль с противником
        for (int j = 0; j < actors.size(); j++)
            if (actors.get(j) != actor & ((PawnTank)actors.get(j)) != null)
                for (int i = 0; i < bullets.size(); i++) {
                    if (actors.get(j).distanceTo(bullets.get(i).getCurrentPosition().x, bullets.get(i).getCurrentPosition().y) < 80) {
                        view.ClearViewObject(bullets.get(i));
                        bullets.remove(bullets.get(i));
                        actors.get(j).damage(actor.getDamage());
                    }
                }
        //пули достигают макс дистанции и пропадают
        for (int i = 0; i < bullets.size(); i++)
            if (actor.distanceTo(bullets.get(i).getCurrentPosition().x, bullets.get(i).getCurrentPosition().y) > distanceOfDeleting) {
                view.ClearViewObject(bullets.get(i));
                bullets.remove(bullets.get(i));
            }
    }

    public void fire() {

        actor.fire(new Vector2(actor.getX(), actor.getY()),
                   new Vector2((float)Math.cos(actor.getAngle()) + actor.getX(), (float)Math.sin(actor.getAngle()) + actor.getY()),
                   bullets, speedBullet);

        /*bullets.add(new Bullet(new Vector2(actor.x, actor.y),
                    new Vector2((float)Math.cos(actor.angle) + actor.x, (float)Math.sin(actor.angle) + actor.y)));
        view.PushViewObject(bullets.get(bullets.size()-1)); //вывод пуль через рендер*/
    }

    public List<Actor> getActors() {return actors;}
    public float getDelay() {return Delay;}
    public CallbackList<Bullet> getBullets() {
        return bullets;
    }

    public void setDelay(float delay) { this.Delay = delay; }
    public void setSpeedBullet(float speedBullet) { this.speedBullet = speedBullet; }
    public void setFire(boolean isFire) {this.isFire = isFire;}

}
