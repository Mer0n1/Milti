package com.mygdx.game.server;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Map.World;
import com.mygdx.game.tank.Actor;
import com.mygdx.game.tank.Bullet;
import com.mygdx.game.tank.PawnTank;
import com.mygdx.game.tank.ServerPlayer;
import com.mygdx.game.ui.CallbackList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private Socket client;
    private DatagramSocket udpSocket;
    private DatagramSocket receiveSocket;
    private InetAddress addressServer;
    private final String ipServer = "192.168.0.103";
    private final int delay = 50; //задержка отправки любых данных в мс
    private final int UDP_port = 5556;
    private final int TCP_port = 5555;

    /** Все сетевые актуальные игроки */
    private Map<Integer, Avatar> players;
    private Actor myActor;
    private int myId;
    private World world;

    /** Актуальные сетевые сущности которые находятся в списке всех сущностей мира
     * и обновляются здесь в listener */
    private Map<Integer, Actor> gameEntities;
    /** Обьекты пуль которые мы хотим передавать на сервер */
    private CallbackList<Bullet> bullets;

    /** Переменные типа обьектов */
    private final byte AVATAR_TYPE = 1;
    private final byte AVATAR_BULLET_TYPE = 2;
    //TODO буквенные значения начинаются с 97


    public Server() {
        try {
            addressServer = InetAddress.getByName(ipServer);
            udpSocket     = new DatagramSocket();
            receiveSocket = new DatagramSocket(UDP_port);
            players       = new HashMap<>();
            gameEntities  = new HashMap<>();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /** Создаем TCP подключение и регистрируемся на сервере.
     *  Метод запускает также udp */
    public void connect() throws IOException {
        client = new Socket(ipServer, TCP_port);

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        StringBuilder req1 = new StringBuilder();
        int character;
        while ((character = in.read()) != -1) {
            req1.append((char) character);

            if (!in.ready())
                break;
        }

        myId = Integer.valueOf(req1.toString());

        sync_my_player_with_server();
        listener();
    }

    /** Прослушивание udp датаграмм.
     *  Принимаем 2 типа обьектов: Аватар сетевого игрока и аватар пули */
    public void listener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        byte[] receiveData = new byte[1024];
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        receiveSocket.receive(receivePacket);

                        byte typeId = receivePacket.getData()[0];
                        if (typeId == 1) {
                            Avatar avatar = Avatar.fromByteArray(receivePacket.getData(), 1);
                            players.put(avatar.getId(), avatar);

                            //если в Map нет такого id то регистрируем в registered и добавляем в World
                            if (!gameEntities.containsKey(avatar.getId())) {
                                //обновим обьект в списке преобразовав в actor
                                Actor actor = new ServerPlayer();
                                actor.setCoord(avatar.getX(), avatar.getY());
                                actor.setHp(avatar.getHp());
                                actor.setAngle(avatar.getAngle());

                                world.getListActors().add(actor);
                                gameEntities.put(avatar.getId(), actor);
                            }

                            //после добавляем/обновляем в Map
                            Actor oldActor = gameEntities.get(avatar.getId());
                            oldActor.setHp(avatar.getHp());
                            oldActor.setCoord(avatar.getX(), avatar.getY());
                            oldActor.setAngle(avatar.getAngle());
                        }

                        //AvatarBullet
                        if (typeId == 2) {
                            AvatarBullet bullet = AvatarBullet.fromByteArray(receivePacket.getData(), 1);

                            if (gameEntities.containsKey(bullet.getIdOwner())) {
                                PawnTank tank = (PawnTank) gameEntities.get(bullet.getIdOwner());

                                Bullet bullet1 = new Bullet(new Vector2(bullet.getX_begin(), bullet.getY_begin()),
                                        new Vector2((float)Math.cos(tank.getAngle()) + tank.getX(),
                                                (float)Math.sin(tank.getAngle()) + tank.getY()), bullet.getSpeed());

                                tank.fire(bullet1.getBeginPosition(), bullet1.getEndPosition(),
                                        tank.getCombatSystem().getBullets(), bullet.getSpeed());
                            }
                        }

                    } catch (IOException e) {

                    }
                }
            }
        }).start();
    }

    /** Отправляем собственные данные своего аватара на сервер */
    public void sync_my_player_with_server() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Avatar avatar = new Avatar(myId, (int)myActor.getX(), (int)myActor.getY(),
                                (int)myActor.getHp(), myActor.getAngle());

                        ByteBuffer buffer = ByteBuffer.allocate(128);
                        buffer.put(AVATAR_TYPE);
                        buffer.put(avatar.toByteArray());
                        byte[] bytes = buffer.array();

                        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, addressServer, UDP_port);
                        udpSocket.send(packet);

                        Thread.sleep(delay);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /** Метод выступающий как callback-функция. Использует последний обьект
     * bullet отправляя его на сервер в момент создания обьекта */
    public void sync_bullet() {
        try {
            if (!bullets.isEmpty()) {
                Bullet bullet = bullets.get(bullets.size() - 1);

                AvatarBullet avatarBullet = new AvatarBullet((int) bullet.getBeginPosition().x,
                        (int) bullet.getBeginPosition().y, bullet.getEndPosition().x, bullet.getEndPosition().y,
                        bullet.getSpeed(), bullet.getAngle(), myId);

                ByteBuffer buffer = ByteBuffer.allocate(128);
                buffer.put(AVATAR_BULLET_TYPE);
                buffer.put(avatarBullet.toByteArray());
                byte[] bytes = buffer.array();

                DatagramPacket packet = new DatagramPacket(bytes, bytes.length, addressServer, UDP_port);
                udpSocket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Основная конфигурация сервера */
    public void sync_with_game(World world) {
        this.world = world;
        myActor = world.getPlayer().getTank();

        bullets = world.getPlayer().getTank().getCombatSystem().getBullets();

        //Регистрируем калбек функцию которая будет вызывать сразу после добавления обьекта в лист
        bullets.registerAddCallback(new CallbackList.CallbackInterface() {
            @Override
            public void callback() {
                sync_bullet();
            }
        });
    }

}
