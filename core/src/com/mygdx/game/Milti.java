package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Map.World;
import com.mygdx.game.server.Server;
import com.mygdx.game.tank.Actor;
import com.mygdx.game.tank.Enemy;

import java.io.IOException;

public class Milti extends ApplicationAdapter {
	private World world;
	private Server server;

	@Override
	public void create () {
		world = new World();
		server = new Server();


		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					server.sync_with_game(world);
					server.connect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void render () {
		world.update();
	}
}
