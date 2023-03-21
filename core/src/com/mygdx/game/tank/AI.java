package com.mygdx.game.tank;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Controller;
import com.mygdx.game.View;

import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;

import sun.rmi.transport.Endpoint;

public class AI extends CombatSystem {
    /** Искуственный интеллект бота*/
    //что то подобие combat system только переопределяет его

    private Actor myActor;
    private Actor target;
    private Mode mode;

    private int distanceVisible; //дистанция при которой будет виден противник
    private boolean VisibleEnemy; //видел ли противник сейчас


    AI(Actor myActor, Controller controller, View view) {
        super(myActor, controller,view);
        this.myActor = myActor;

        VisibleEnemy = false;
        distanceVisible = 400; //Window / 4
        Delay = 1f;
        actor.speed = 3;

        mode = Mode.patrol;
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
                if (ac.distanceTo(myActor.x, myActor.y) < distanceVisible) {
                    target = ac;
                    VisibleEnemy = true;
                    mode = Mode.inaction;
                }
        updateFire();
        mode.update(this);

        if (Time > saveTime)  //некоторая задержка
            saveTime = Time + Delay;
        if (!VisibleEnemy) {
            target = null;
            mode = Mode.patrol;
        }
    }

    public void move() {
        actor.x += Math.cos(actor.angle) * actor.speed;
        actor.y += Math.sin(actor.angle) * actor.speed;
    }

    public enum Mode { //режимы
        patrol { //патрулирование

            public void update(AI ai) {
                ai.actor.angle = (float) Math.atan((NextPoint.y - ai.actor.y) / (NextPoint.x - ai.actor.x));
                if (ai.actor.angle < 0) ai.actor.angle = 1.57f - ai.actor.angle * -1 + 1.57f;
                if (NextPoint.y - ai.actor.y < 0) ai.actor.angle += 3.14;

                ai.move();

                if (Math.sqrt((ai.actor.x - NextPoint.x)*(ai.actor.x - NextPoint.x) + (ai.actor.y - NextPoint.y)*(ai.actor.y - NextPoint.y)) < 32) {
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

                    ai.myActor.angle = (float) Math.atan((ai.target.y - ai.myActor.y) / (ai.target.x - ai.myActor.x));
                    if (ai.myActor.angle < 0)
                        ai.myActor.angle = 1.57f - ai.myActor.angle * -1 + 1.57f;
                    if (ai.target.y - ai.myActor.y < 0)
                        ai.myActor.angle += 3.14;

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
