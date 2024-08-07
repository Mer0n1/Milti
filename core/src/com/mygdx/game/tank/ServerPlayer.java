package com.mygdx.game.tank;

import com.mygdx.game.ui.HealthBar;

public class ServerPlayer extends PawnTank {

    public ServerPlayer() {
        super();
        healthBar.setPos(-100, -100); //TODO ошибка текстуры
    }

    @Override
    public void update() {
        super.update();
        //healthBar.setPos(x - Width / 2, y + Height);
    }

}
