package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.ui.Button;

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
    private Joystick left = null, right = null; //левый и правые джойстики
    private List<Joystick> list;

    private final int ZoneXEndLeft = WidthWindow / 3;
    private final int ZoneYEndLeft = HeightWindow - HeightWindow / 2;


    public Controller() {
        list = new ArrayList<>();
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

      }

      //далее обновляем данные активных джойстиков
        for (int j = 0; j < list.size(); j++)
            update(list.get(j));
    }

    private void update(Joystick joystick)
    {
        float gpx = joystick.CurrentX - joystick.beginX;
        float gpy = joystick.CurrentY - joystick.beginY;

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

    public boolean CheckButton(Button button)
    {
        for (int j = 0; j < 3; j++) //может быть сделаем метод проверки в контроллере?
            if (Gdx.input.isTouched(j))
            {
                if (button.isPressed(j))
                    return true;
            }
        return false;
    }

}
