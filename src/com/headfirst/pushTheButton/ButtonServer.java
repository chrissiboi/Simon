package com.headfirst.pushTheButton;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by User on 19.02.2015.
 */
public class ButtonServer {

    ObjectOutputStream oos;
    ArrayList<PlayerObject> playerObjects;
    ArrayList outputStreams;
    int idCounter = 0;

    public class ClientHandler implements Runnable{

        ObjectInputStream ois;
        Socket socket;
        PlayerObject player = null;

        public ClientHandler(Socket clientSocket){

            try{

                socket = clientSocket;
                InputStream is = socket.getInputStream();
                ois = new ObjectInputStream(is);


            }catch(Exception ex ) {

                ex.printStackTrace();

            }

        }

        public void run(){

            try{

                while((player = (PlayerObject) ois.readObject()) != null){

                    System.out.println("Got Object!");

                    if(player.serverMessage == 2) {

                        PlayerObject po = new PlayerObject();
                        po.setUserId(++idCounter);
                        po.username = player.username;
                        playerObjects.add(po);
                        po.serverMessage = player.serverMessage;

                        tellOtherPlayer(po);

                    }
                    else
                        tellOtherPlayer(player);

                }

            }catch(Exception ex) {

                outputStreams.remove(oos);
                System.out.println("User disconnected");

            }

        }

    }

    private void tellOtherPlayer(PlayerObject player) {

        Iterator it = outputStreams.iterator();

        while(it.hasNext()) {

            try {

                ObjectOutputStream oos = (ObjectOutputStream) it.next();
                oos.reset();
                oos.writeObject(player);
                System.out.println("Told other players!");


            } catch(Exception e) {

                e.printStackTrace();

            }

        }

    }

    public static void main(String[] args){

        ButtonServer server = new ButtonServer();
        server.start();

    }

    public void start(){

        outputStreams = new ArrayList();
        playerObjects = new ArrayList<>();

        try{

            ServerSocket socket = new ServerSocket(3333);
            System.out.println("Server started");

            while(true){

                Socket clientSocket = socket.accept();
                ClientHandler handler = new ClientHandler(clientSocket);
                OutputStream os = clientSocket.getOutputStream();
                oos = new ObjectOutputStream(os);
                outputStreams.add(oos);

                Thread t = new Thread(handler);
                t.start();
                System.out.println("Got one");

            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
