package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Controller;
import com.mygdx.game.Player;

public class Interface {
    private Controller controller;

    private Button fire_button;
    private Button OpenWeaponMenuButton;
    private HealthBar healthBar;

    private WeaponSelectionMenu weaponSelectionMenu;
    private boolean isFire;

    public Interface(HealthBar healthBar, Controller controller, Player player) {
        this.controller = controller;
        this.healthBar = healthBar;
        weaponSelectionMenu = new WeaponSelectionMenu(player);
        OpenWeaponMenuButton = new Button(50, Gdx.graphics.getHeight() - 150);
        OpenWeaponMenuButton.setTexture("interface/irlSelectMenu.png");

        //healthBar.setScale(0.4, 0.4);
        this.healthBar.setPos(100, 100);

        fire_button = new Button(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 5,
                Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2);

        isFire = false;
    }

    public void update() {
        if (controller.CheckButton(OpenWeaponMenuButton))
            weaponSelectionMenu.setOpened(true);
        isFire = controller.CheckButton(fire_button);
    }



    public void render(SpriteBatch batch) {
        fire_button.render(batch);
        healthBar.render(batch);
        OpenWeaponMenuButton.render(batch);
        weaponSelectionMenu.update(batch);
    }
    public boolean getFire() { return isFire; }

}
