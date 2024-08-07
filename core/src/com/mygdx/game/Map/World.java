package com.mygdx.game.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.ui.ConsoleDeveloper;
import com.mygdx.game.Controller;
import com.mygdx.game.tank.Actor;
import com.mygdx.game.tank.Enemy;
import com.mygdx.game.Player;

import java.util.ArrayList;
import java.util.List;

public class World {
    private Player player;
    private List<Actor> list;

    private TiledMaps map;
    private Controller controller;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private ConsoleDeveloper consoleDeveloper = new ConsoleDeveloper();

    public World() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(new Vector3(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2,0));

        map = new TiledMaps(camera);
        Actor.init(this, map);
        batch = new SpriteBatch();

        list = new ArrayList<>();
        controller = new Controller();
        player = new Player(controller);
        player.getTank().setCoord(400, 400);
        list.add(player.getTank());
        //camera.zoom = 1.5f; //test other mobile //в зависимости от размеров экрана телефона //st: 1920 на 1200
        //camera.zoom = (1920f / Gdx.graphics.getWidth() + 1200f / Gdx.graphics.getHeight()) / 2f;
        //camera.translate(1920f - Gdx.graphics.getWidth(), 1200f - Gdx.graphics.getHeight());

        //test
        addEntity(new Enemy());
        list.get(list.size() - 1).setCoord(800, 800);
    }

    public void update() {

        ScreenUtils.clear(1, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        controller.check();
        player.update();

        batch.begin();
        map.render();

        for (Actor ac : list) {
            ac.update();
            ac.render(batch);
        }
        player.render(batch);

        consoleDeveloper.update(batch); //отрисовки меню разработчика
        batch.end();
    }


    public void addEntity(Actor entity) {
        list.add(entity);
    }
    public List<Actor> getListActors() { return list; }

    public Player getPlayer() {
        return player;
    }

    public List<Actor> getList() {
        return list;
    }
}
