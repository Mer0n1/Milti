package com.mygdx.game.tank;

import static com.mygdx.game.tank.Actor.world;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Controller;
import com.mygdx.game.Milti;
import com.mygdx.game.View;

import java.util.ArrayList;
import java.util.List;

//боевая система танка
public class CombatSystem {
    protected Actor actor;
    protected Controller controller;
    protected View view;

    protected List<Actor> actors;

    //Выстрелы (пули)
    protected List<Bullet> bullets;
    protected float Delay = 0.5f; //задержка между выстрелами
    protected float saveTime = 0; //сохраненное время для синхронизации с задержкой
    protected final float distanceOfDeleting = 1000; //дистанция от actor при которой удаляются пули

    //Время
    protected final long NULLTIME = System.currentTimeMillis(); //вероятно таймер или время будут передаваться также по все обьектам
    protected float Time = 0;

    public CombatSystem(Actor actor, Controller controller, View view) {
        bullets = new ArrayList<>();
        this.controller = controller;
        this.actor = actor;
        this.view = view;

        actors = world.getListActors();
    }

    public void update() {
        //обновление времени
        Time = (System.currentTimeMillis() - NULLTIME) / 1000f;
        //Обработка выстрелов
        if (Time > saveTime) {
            saveTime = Time + Delay;

            if (controller.getPressedFireButton()) {
                float x = actor.x + actor.Width / 2,
                      y = actor.y + actor.Height / 2;
                fire();
                /*bullets.add(new Bullet(new Vector2(x, y),
                            new Vector2((float)Math.cos(actor.angle) + x, (float)Math.sin(actor.angle) + y)));*/
                //play music
            }
        }

        updateFire();
    }

    public void updateFire() { //проверка пуль на столкновения и удаление пуль
        //столкновение пуль с противником
        for (int j = 0; j < actors.size(); j++)
            if (actors.get(j) != actor)
                for (int i = 0; i < bullets.size(); i++) {
                    if (actors.get(j).distanceTo(bullets.get(i).getCurrentPosition().x, bullets.get(i).getCurrentPosition().y) < 80) {
                        view.ClearViewObject(bullets.get(i));
                        bullets.remove(bullets.get(i));
                        actors.get(j).damage(actor.damage);
                    }
                    //System.out.println(" - " + actors.get(j).distanceTo(bullets.get(i).getCurrentPosition().x, bullets.get(i).getCurrentPosition().y));
                }
        //пули достигают макс дистанции и пропадают
        for (int i = 0; i < bullets.size(); i++)
            if (actor.distanceTo(bullets.get(i).getCurrentPosition().x, bullets.get(i).getCurrentPosition().y) > distanceOfDeleting) {
                view.ClearViewObject(bullets.get(i));
                bullets.remove(bullets.get(i));
            }
    }

    public void fire() {
        bullets.add(new Bullet(new Vector2(actor.x, actor.y),
                    new Vector2((float)Math.cos(actor.angle) + actor.x, (float)Math.sin(actor.angle) + actor.y)));
        view.PushViewObject(bullets.get(bullets.size()-1)); //вывод пуль через рендер
    }

}
