package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.tank.Actor;

import java.util.ArrayList;
import java.util.List;

/**
Класс представление для игрока, его текстур, анимаций и любого рендера
 */
public class View {
    private Texture texture;
    private TextureRegion model;
    private Actor entity;

    private List<ViewObject> listview;

    public View(Actor entity) {

        this.entity = entity;

        texture = new Texture("tanks/playertankbegin.png");
        model = new TextureRegion(texture);
        listview = new ArrayList<>();

        entity.setWidth(texture.getWidth());
        entity.setHeight(texture.getHeight());
    }
    public void setTexture(String path) {
        texture = new Texture(path);
        model.setTexture(texture);
    }
    public void setTexture(Texture texture) {
        this.texture = texture;
        model.setTexture(texture);
    }

    public void render(SpriteBatch batch) {
        batch.draw(model, entity.getX() - entity.getWidth() / 2, entity.getY() - entity.getHeight() / 2,
                entity.getWidth()/2, entity.getHeight()/2,
                entity.getWidth(), entity.getHeight(),
                1,1,
                entity.getAngle() * 180 / 3.14f);

        for (int j = 0; j < listview.size(); j++)
            listview.get(j).render(batch);
    }

    public void PushViewObject(ViewObject obj) {
        listview.add(obj);
    }
    public void ClearViewObject(ViewObject obj) {listview.remove(obj);}
}
