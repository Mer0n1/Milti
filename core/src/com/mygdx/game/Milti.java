package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Map.World;
import com.mygdx.game.tank.Actor;
import com.mygdx.game.tank.Enemy;

public class Milti extends ApplicationAdapter {
	private World world;

	@Override
	public void create () {
		world = new World();
		//Actor.World = world;
	}

	@Override
	public void render () {
		world.update();
	}
}
