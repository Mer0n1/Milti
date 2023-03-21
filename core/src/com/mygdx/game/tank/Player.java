package com.mygdx.game.tank;

import static java.lang.Float.NaN;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Controller;
import com.mygdx.game.HealthBar;
import com.mygdx.game.InterfaceViewController;
import com.mygdx.game.View;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.ControllerEventListener;
/** tank */
public class Player extends Actor {

    private View view;
    private InterfaceViewController interfaceViewController;
    private Controller controller;
    private HealthBar healthBar;
    private CombatSystem combatSystem;
    private TypeTank typeTank;

    private double ControllerSpeed;
    private enum TypeTank{ Standart, DoubleDamTank, BigTank, DopT }; //типы танков

    //Sound sound = Gdx.audio.newSound(Gdx.files.internal("music/magnetic_laser.mp3")); //test //singleton system?

    public Player(Controller controller) {
        super();
        view = new View(this);
        healthBar = new HealthBar(hp, hp);
        interfaceViewController = new InterfaceViewController(controller.getFire_button(), healthBar);
        this.controller = controller;
        combatSystem = new CombatSystem(this, controller, view);
        typeTank = TypeTank.Standart;

        Width = 100;
        Height = 100;
        ControllerSpeed = 0;
        speed = 8;
        hp = 100;
        healthBar.setMax_hp(hp);
    }

    @Override
    public void update() {

        //controller
        float angle_ = controller.getAngle(Controller.TypeJoystick.Left);
        if (angle_ != 0 & !Float.isNaN(angle_))
            angle = angle_;

        ControllerSpeed = Math.sin(controller.getAngle(Controller.TypeJoystick.Right)); //скорость
        if (!collision.interationWithMap(x, y, Width, Height))
            move();
        else {
            x -= 1;
            y -= 1;
        }
        //-----------------------
        combatSystem.update();

        healthBar.setCurrent_hp(hp);
        healthBar.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        //render model
        view.render(batch);
        //render интерфейса. Интерфейс это не контроллер
        interfaceViewController.render(batch);
    }

    private void move() {
        if (ControllerSpeed != 0 & !Double.isNaN(ControllerSpeed)) {
            x += Math.cos(angle) * ControllerSpeed * speed;
            y += Math.sin(angle) * ControllerSpeed * speed;
        }
    }

    public void setTypeTank(TypeTank tank)
    {
        if (typeTank != tank) {
            if (tank == TypeTank.Standart) {

            }

        }
    }

    @Override
    public void damage(double damage) {
        hp -= damage;
    }
}
