package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Player;
import com.mygdx.game.tank.DopTank;
import com.mygdx.game.tank.PawnTank;
import com.mygdx.game.tank.StandartTank;
import com.mygdx.game.ui.Button;

import java.util.ArrayList;
import java.util.List;

/**Меню для выбора/смены орудия на танке. */
public class WeaponSelectionMenu {
    public class Window {
        public Texture window = new Texture("interface/MenuChange/WindowSelect.jpg");
        public TextureRegion windowregion = new TextureRegion(window);
        public Vector2 coord = new Vector2(0,0);

        //icon
        public Texture icon; //иконка танка
        public TextureRegion iconRegion;
        public Vector2 coordIcon = new Vector2();

        //описание
        public BitmapFont description;
        public String vv;
        public Vector2 coordStr;

        //button
        private Button button;

        //tank
        public PawnTank tank;

        Window(int x, int y, String PathTextureIcon, PawnTank tank) {
            coord.x = x;
            coord.y = (Gdx.graphics.getHeight() - window.getHeight()) / 2;

            icon = new Texture(PathTextureIcon);
            iconRegion = new TextureRegion(icon);
            coordIcon.x = coord.x + window.getWidth() / 4;
            coordIcon.y = coord.y + window.getHeight() / 3;

            description = new BitmapFont();
            vv = new String("Tank");
            coordStr = new Vector2(coordIcon.x, coordIcon.y + 100);
            Color color = new Color(Color.argb8888(131, 122, 105, 105));
            description.setColor(color);
            description.getData().setScale(2);

            button = new Button((int)(coord.x + window.getWidth() / 2 - 100), (int)(coord.y + 100));
            button.setTexture("interface/MenuChange/Button.png");

            this.tank = tank;
        }
    }
    private List<Window> list;
    private Player player;

    //Controller //test
    /*private boolean isClick;
    private Vector2 fixClick;
    private Vector2 CurClick;*/
    private boolean isOpened;


    public WeaponSelectionMenu(Player player) {
        list = new ArrayList<>();
        list.add(new Window(100, 0, "tanks/playertankbegin.png", new StandartTank()));
        list.add(new Window(650, 0, "tanks/lvl2.png", new DopTank())); //x: 700

        /*isClick = false;
        fixClick = null;
        CurClick = new Vector2(0,0);*/
        isOpened = false;
        this.player = player;
    }

    public void update(SpriteBatch batch)
    {
        if (!isOpened) return;

        updateController();
        render(batch);
    }

    public void updateController() { //обработка нажатия
        if (Gdx.input.isTouched(0)) {

            /*CurClick.x = Gdx.input.getX();
            CurClick.y = Gdx.input.getY();*/

            /*if (fixClick == null) {
                fixClick = new Vector2(CurClick.x, CurClick.y);
            } else {
                for (Window window : list) {
                    window.coord.x = CurClick.x - 100;
                }
            }*/

            for (int j = 0; j < list.size(); j++)
                if (list.get(j).button.isPressed(0)) {
                    isOpened = false;
                    player.setTypeTank(list.get(j).tank);
                }
        } else {
            //fixClick = null;
        }
    }

    public void render(Batch batch) {
        for (Window window : list) {
            batch.draw(window.windowregion, window.coord.x, window.coord.y);

            batch.draw(window.iconRegion, window.coordIcon.x, window.coordIcon.y,
                    window.icon.getWidth(), window.icon.getHeight(),
                    window.icon.getWidth(), window.icon.getHeight(),
                    1, 1, 270f);

            window.button.render((SpriteBatch) batch);
            window.description.draw(batch, window.vv, window.coordStr.x, window.coordStr.y);
        }
    }

    public void setOpened(boolean isOpen) { this.isOpened = isOpen; }
}
