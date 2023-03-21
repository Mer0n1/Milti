package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**тестовая консоль разработчика */
public class ConsoleDeveloper {
    /*private static int FPS = 0;
    private static int i = 0;
    private static long NULLTIME = System.currentTimeMillis();
    private FPSLogger fpsLogger;

    private final int DelayFPS = 1; //FPS в DelayFPS секунд
    private float Time = 0;
    private float SaveTime = 0;*/

    private BitmapFont fontFps; //представление текста фпс
    private int x, y;

    public ConsoleDeveloper() {
        fontFps = new BitmapFont();
        fontFps.setColor(Color.RED);
        fontFps.getData().setScale(2);
        x = Gdx.graphics.getWidth() - 200;
        y = Gdx.graphics.getHeight() - 10;
    }

    public void update(Batch batch) {

        fontFps.draw(batch, "FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), x , y);
    }
}
