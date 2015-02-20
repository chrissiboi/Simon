package com.headfirst.pushTheButton;

import com.mysql.fabric.Server;
import com.sun.corba.se.pept.encoding.InputObject;
import com.sun.media.jfxmedia.events.PlayerEvent;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by User on 19.02.2015.
 */
public class ButtonServer {

    ObjectOutputStream oos;
    PrintWriter writer;

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

                    System.out.println("Got Message!");
                    tellOtherPlayer(player);

                }

            }catch(Exception ex) {

                System.out.println("User disconnected");

            }

        }

    }

    private void tellOtherPlayer(PlayerObject player) {

        try {

            oos.reset();
            oos.writeObject(player);
            System.out.println("Told other players!");


        } catch(Exception e) {

            e.printStackTrace();

        }

    }

    public static void main(String[] args){

        ButtonServer server = new ButtonServer();
        server.start();

    }

    public void start(){

        try{

            ServerSocket socket = new ServerSocket(3333);
            System.out.println("Server started");

            while(true){

                Socket clientSocket = socket.accept();
                ClientHandler handler = new ClientHandler(clientSocket);
                OutputStream os = clientSocket.getOutputStream();
                oos = new ObjectOutputStream(os);

                Thread t = new Thread(handler);
                t.start();
                System.out.println("Got one");

            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
