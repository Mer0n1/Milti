package com.mygdx.game;

import static java.lang.Float.NaN;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final int MaxFingers = 3;
    private final int WidthWindow = Gdx.graphics.getWidth(), HeightWindow = Gdx.graphics.getHeight();
    public enum TypeJoystick { Left, Right };

    private class Joystick {
        public float beginX = 0, beginY = 0; //начальные координаты нажатия
        public float CurrentX = 0, CurrentY = 0;
        public float angle; //угол поворота
        public TypeJoystick type = TypeJoystick.Left;
        public int number = -1; //номер нажатия
    }
    private class Fire {
        private boolean Fire = false;
        private int number = -1;
        private Button fire_button;
    }
    private Joystick left = null, right = null; //левый и правые джойстики
    private List<Joystick> list;
    private Fire fire;

    private final int ZoneXEndLeft = WidthWindow / 3;
    private final int ZoneYEndLeft = HeightWindow - HeightWindow / 2;


    public Controller() {
        list = new ArrayList<>();
        fire = new Fire();
        fire.fire_button = new Button(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 5,
                                 Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2);
    }

    public void check()
    {
      for (int j = 0; j < MaxFingers; j++) {
          if (Gdx.input.isTouched(j)) {
              int gdxX = Gdx.input.getX(j);
              int gdxY = Gdx.input.getY(j);

              if (left == null) { //нажатие и активация левого джойстика
                  if (gdxX > 0 && gdxX < ZoneXEndLeft && gdxY > ZoneYEndLeft && gdxY < HeightWindow) {//проверим координаты клика на попадание в зоны джойстиков
                      left = new Joystick();
                      left.beginX = gdxX;
                      left.beginY = gdxY;
                      left.CurrentX = gdxX;
                      left.CurrentY = gdxY;
                      left.type = TypeJoystick.Left;
                      left.number = j;
                      list.add(left);
                      continue;
                  }
              } else {
                  if (j == left.number) {
                      left.CurrentX = gdxX;
                      left.CurrentY = gdxY; continue;
                  }
              }

              if (right == null) {
                  if (gdxX > WidthWindow - WidthWindow / 3 && gdxX < WidthWindow && gdxY > ZoneYEndLeft && gdxY < HeightWindow) {
                      right = new Joystick();
                      right.beginX = gdxX;
                      right.beginY = gdxY;
                      right.CurrentX = gdxX;
                      right.CurrentY = gdxY;
                      right.type = TypeJoystick.Right;
                      right.number = j;
                      list.add(right); continue;
                  }
              } else {
                  if (j == right.number) {
                      right.CurrentX = gdxX;
                      right.CurrentY = gdxY;continue;
                  }
              }

          } else {
              //удаление неактивного джойстика

              if (left != null)
              if (left.number == j) {
                  list.remove(left);
                  left = null;
              }
              if (right != null)
              if (right.number == j) {
                  list.remove(right);
                  right = null;
              }
          }

          //Проверка кнопки выстрела
          if (Gdx.input.isTouched(j)) {
              if (fire.number == -1 && fire.fire_button.isPressed(j)) {
                  fire.Fire = true;
                  fire.number = j;
              } else if (fire.fire_button.isPressed(fire.number)) {
                  //true
              } else {
                  fire.Fire = false;
                  fire.number = -1;
              }
          } else if (fire.number == j) {
              fire.Fire = false;
              fire.number = -1;
          }
      }

      //далее обновляем данные активных джойстиков
        for (int j = 0; j < list.size(); j++)
            update(list.get(j));
    }

    private void update(Joystick joystick)
    {
        float gpx = joystick.CurrentX - joystick.beginX;
        float gpy = joystick.CurrentY - joystick.beginY;
        //double rad = Math.acos(gpx / Math.sqrt(gpx*gpx + gpy*gpy));
        //joystick.angle = (float) (rad * 180 / 3.14f);
        joystick.angle = (float)Math.acos(gpx / Math.sqrt(gpx*gpx + gpy*gpy));

        if (gpx < 0 && gpy > 0 || gpx > 0 && gpy > 0)
            joystick.angle = 3.14f - joystick.angle + 3.14f;
    }

    public float getAngle(TypeJoystick type)
    {
        for (int j = 0; j < list.size(); j++)
            if (list.get(j).type == type) {
                return list.get(j).angle;
            }
        return 0;
    }

    public Button getFire_button() {
        return fire.fire_button;
    }
    public boolean getPressedFireButton() { return fire.Fire; }
}
