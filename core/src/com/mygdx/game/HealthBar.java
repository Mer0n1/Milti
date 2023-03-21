package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HealthBar implements ViewObject {
    private final int MaxWidth;
    private final int MaxHeight;
    private double max_hp;
    private double current_hp;
    private float x, y;
    private int Width, Height;
    private int LongWidthHp;

    private Texture ramka_hp;
    private Texture sothboard;
    private TextureRegion hp;


    public HealthBar(double max_hp, double current_hp) {
        this.max_hp = max_hp;
        this.current_hp = current_hp;
        x = 0;
        y = 0;

        ramka_hp = new Texture("interface/hp.png");
        sothboard = new Texture("interface/hp_sothboard.png");
        hp  = new TextureRegion(sothboard);

        MaxWidth = sothboard.getWidth();
        MaxHeight = sothboard.getHeight();
        Width = MaxWidth;
        Height = MaxHeight;
        LongWidthHp = Width;
    }

    public void setCurrent_hp(double newHp) {
        if (newHp > max_hp) newHp = max_hp;
        if (newHp < 0) newHp = 0;
        current_hp = newHp;
    }
    public void setMax_hp(double newMaxHp) {
        max_hp = newMaxHp;
    }

    public void setScale(double x, double y) {
        Width  = (int) (MaxWidth * x);
        Height = (int) (MaxHeight * y);
        LongWidthHp = Width;
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        //hp.setRegionWidth((int) (sothboard.getWidth() * current_hp / max_hp));
        LongWidthHp = (int) (Width * current_hp / max_hp);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(hp, x, y, LongWidthHp, Height);
        batch.draw(ramka_hp, x, y, Width, Height);
    }

}
