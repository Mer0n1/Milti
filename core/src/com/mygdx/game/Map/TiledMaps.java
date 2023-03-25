package com.mygdx.game.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledMaps implements Collision {
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    private int LayersCount;
    TiledMapTileLayer collisionLayer;

    TiledMaps(OrthographicCamera camera) {
        map = new TmxMapLoader().load("Maps/test4.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        mapRenderer.setView(camera);
        this.camera = camera;

        LayersCount = map.getLayers().getCount();
        collisionLayer = (TiledMapTileLayer)map.getLayers().get(0);
        MapProperties pr = collisionLayer.getCell(0, 0).getTile().getProperties();


        
    }

    @Override
    public boolean interationWithMap(float x, float y) {

        /*for (int c = 0; c < 20; c++)
        for (int j = 0; j < 20; j++) {
            //collisionX = collisionLayer.getCell((int) x / 64, (int) y / 64).getTile().getProperties().containsKey("Blocked");
            collisionX = collisionLayer.getCell(j, c).getTile().getProperties().containsKey("Blocked");
        }*/
        if (x < 0 && x > Gdx.graphics.getWidth() && y < 0 && y > Gdx.graphics.getHeight())
            return true;

        return false;
    }


    public void render() {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }
}
