package com.mygdx.game.tank;

public class DopTank extends PawnTank {
    public DopTank() {
        speed = 5;
        hp = 100;
        combatSystem.setSpeedBullet(15);
        combatSystem.setDelay(0.2f);
        healthBar.setMax_hp(hp);
        healthBar.setCurrent_hp(hp);
        view.setTexture("tanks/lvl2.png");
    }
}
