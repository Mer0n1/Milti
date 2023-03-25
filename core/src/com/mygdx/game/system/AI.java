package com.mygdx.game.system;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Controller;
import com.mygdx.game.View;
import com.mygdx.game.tank.Actor;
import com.mygdx.game.tank.PawnTank;

import java.util.List;

public class AI extends CombatSystem {
    /** Искуственный интеллект бота*/

    private Actor myActor;
    private Actor target;
    private Mode mode;
    private List<Actor> actors;

    private int distanceVisible; //дистанция при которой будет виден противник
    private boolean VisibleEnemy; //видел ли противник сейчас


    public AI(PawnTank myActor, View view) {
        super(myActor);
        this.myActor = myActor;

        VisibleEnemy = false;
        distanceVisible = 400; //Window / 4
        setDelay(1f);
        myActor.setSpeed(3);

        mode = Mode.patrol;
        actors = getActors();
    }


    @Override
    public void update() {
        Time = (System.currentTimeMillis() - NULLTIME) / 1000f;

        /*for (Actor ac : actors) //прошлая система
            if (ac != myActor)
                if (ac.distanceTo(myActor.x, myActor.y) < distanceVisible) {
                    //замечен противник
                    myActor.angle = (float) Math.atan((ac.y - myActor.y) / (ac.x - myActor.x));
                    if (myActor.angle < 0)
                        myActor.angle = 1.57f - myActor.angle * -1 + 1.57f;
                    if (ac.y - myActor.y < 0)
                        myActor.angle += 3.14;

                    if (Time > saveTime)
                        fire();
                }
        updateFire();*/

        VisibleEnemy = false;
        for (Actor ac : actors)
            if (ac != myActor)
                if (ac.distanceTo(myActor.getX(), myActor.getY()) < distanceVisible) {
                    target = ac;
                    VisibleEnemy = true;
                    mode = Mode.inaction;
                }
        UpdateInteractFires();
        mode.update(this);

        if (Time > saveTime)  //некоторая задержка
            saveTime = Time + Delay;
        if (!VisibleEnemy) {
            target = null;
            mode = Mode.patrol;
        }
    }

    public void move() {
        myActor.addX(Math.cos(myActor.getAngle()) * myActor.getSpeed());
        myActor.addY(Math.sin(myActor.getAngle()) * myActor.getSpeed());
    }

    public enum Mode { //режимы
        patrol { //патрулирование

            public void update(AI ai) {
                ai.myActor.setAngle((float) Math.atan((NextPoint.y - ai.myActor.getY()) / (NextPoint.x - ai.myActor.getX())));
                if (ai.myActor.getAngle() < 0) ai.myActor.setAngle(1.57f - ai.myActor.getAngle() * -1 + 1.57f);
                if (NextPoint.y - ai.myActor.getY() < 0) ai.myActor.addAngle(3.14);

                ai.move();

                if (Math.sqrt((ai.myActor.getX() - NextPoint.x)*(ai.myActor.getX() - NextPoint.x) + (ai.myActor.getY() - NextPoint.y)*(ai.myActor.getY() - NextPoint.y)) < 32) {
                    if (NextPoint == BeginPoint)
                        NextPoint = EndPoint;
                    else
                        NextPoint = BeginPoint;
                }
            }
        },
        inaction { //стандартная боевая - огонь стоя
            public void update(AI ai) {
                //замечен противник
                if (ai.target != null) {

                    ai.myActor.setAngle((float) Math.atan((ai.target.getY() - ai.myActor.getY()) / (ai.target.getX() - ai.myActor.getX())));
                    if (ai.myActor.getAngle() < 0)
                        ai.myActor.setAngle(1.57f - ai.myActor.getAngle() * -1 + 1.57f);
                    if (ai.target.getY() - ai.myActor.getY() < 0)
                        ai.myActor.addAngle(3.14);

                    if (ai.Time > ai.saveTime)
                        ai.fire();
                }
            }
        },
        free { //свободное. Танк ездит куда хочет
            public void update(AI ai) {

            }
        },
        zone { //зона. Танк ездит рандомно в зоне.
            public void update(AI ai) {

            }
        },
        Overtake_Destroy { //боевая с преследованием
            public void update(AI ai) {

            }
        };
        public abstract void update(AI ai);

        public Vector2 BeginPoint = new Vector2(800, 800);
        public Vector2 EndPoint = new Vector2(800, 400);
        public Vector2 NextPoint = EndPoint;
    }
}
