package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.tank.Actor;
import com.mygdx.game.tank.PawnTank;
import com.mygdx.game.tank.StandartTank;
import com.mygdx.game.ui.HealthBar;
import com.mygdx.game.ui.Interface;

/** tank */
public class Player {

    private Interface interfacePlayer;
    private final Controller controller;
    private PawnTank tank;

    //Sound sound = Gdx.audio.newSound(Gdx.files.internal("music/magnetic_laser.mp3")); //test //singleton system?

    public Player(Controller controller) {
        super();
        tank = new StandartTank();
        interfacePlayer = new Interface(tank.getHealthBar(), controller, this);
        this.controller = controller;
    }

    public void update() {

        //Controller joysticks
        float angle_ = controller.getAngle(Controller.TypeJoystick.Left);
        if (angle_ != 0 & !Float.isNaN(angle_))
            tank.setAngle(angle_);

        tank.setControllerSpeed(Math.sin(controller.getAngle(Controller.TypeJoystick.Right))); //скорость
        tank.move();

        //standard update
        tank.getCombatSystem().setFire(interfacePlayer.getFire());
        interfacePlayer.update();
    }

    public void render(SpriteBatch batch) {
        interfacePlayer.render(batch); //render интерфейса
    }

    public PawnTank getTank() { return tank; }

    public void setTypeTank(PawnTank tank)
    {
        //add controller characteristics
        tank.setAngle(this.tank.getAngle());
        tank.setX(this.tank.getX());
        tank.setY(this.tank.getY());
        tank.getView().ClearViewObject(tank.getHealthBar());
        tank.setHealthBar(this.tank.getHealthBar());
        tank.getView().PushViewObject(tank.getHealthBar());

        Actor.getWorld().getListActors().remove((Actor)this.tank); //need delete from world list
        this.tank = tank;
        Actor.getWorld().getListActors().add((Actor)this.tank); //and add again
    }

}
