package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

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

        Window(int x, int y, String PathTextureIcon) {
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
        }
    }
    private List<Window> list;

    //Controller
    private boolean isClick;
    private Vector2 fixClick;
    private Vector2 CurClick;


    public WeaponSelectionMenu() {
        list = new ArrayList<>();

        list.add(new Window(100, 0, "tanks/lvl2.png"));


        isClick = false;
        fixClick = null;
        CurClick = new Vector2(0,0);
    }

    public int start(SpriteBatch batch)
    {
        int i = 0;
        i = updateController();
        render(batch);

        return i;
    }

    public int updateController() { //обработка нажатия
        if (Gdx.input.isTouched(0)) {

            CurClick.x = Gdx.input.getX();
            CurClick.y = Gdx.input.getY();

            /*if (fixClick == null) {
                fixClick = new Vector2(CurClick.x, CurClick.y);
            } else {
                for (Window window : list) {
                    window.coord.x = CurClick.x - 100;
                }
            }*/

            for (int j = 0; j < list.size(); j++)
                if (list.get(j).button.isPressed(j))
                    return j;

        } else {
            fixClick = null;
        }
        return -1;
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
}
