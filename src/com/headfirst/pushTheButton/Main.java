package com.headfirst.pushTheButton;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by User on 28.01.2015.
 */
public class Main {

    JFrame frame                = new JFrame();
    JPanel background           = new JPanel();
    JPanel topPlayerJPanel      = new JPanel();
    JPanel middlePlayerJPanel   = new JPanel();
    JPanel bottomPlayerJPanel   = new JPanel();
    JPanel topOpponentJPanel    = new JPanel();
    JPanel middleOpponentJPanel = new JPanel();
    JPanel bottomOpponentJPanel = new JPanel();
    JLabel player1NameLabel;
    JLabel opponentNameLabel;
    JTextField playerNameInput;
    JTextArea    statusArea      = new JTextArea(20, 30);
    int          levelCounter    = 3;
    String       sequence        = "";
    PushButton[] pushButtons     = new PushButton[4];
    PushButton[] opponentButtons = new PushButton[4];
    boolean      ongoingRound    = false;
    boolean      inputAllowed    = false;
    Sequencer          sequencer;
    Sequence           sequenc;
    Track              track;
    BufferedReader     reader;
    ObjectInputStream  ois;
    PrintWriter        writer;
    Socket             socket;
    ObjectOutputStream objectOutput;
    PlayerObject       player1;

    public static void main(String[] args) {

        Main main = new Main();
        main.start();

    }

    private void start() {

        PushButton pushButton1 = new PushButton(Color.red, "button1", 42);
        PushButton pushButton2 = new PushButton(Color.green, "button2", 52);
        PushButton pushButton3 = new PushButton(Color.yellow, "button3", 62);
        PushButton pushButton4 = new PushButton(Color.blue, "button4", 72);

        PushButton opponentButton1 = new PushButton(Color.red, "button1", 42);
        PushButton opponentButton2 = new PushButton(Color.green, "button2", 52);
        PushButton opponentButton3 = new PushButton(Color.yellow, "button3", 62);
        PushButton opponentButton4 = new PushButton(Color.blue, "button4", 72);

        pushButton1.addActionListener(new PushButtonListener());
        pushButton2.addActionListener(new PushButtonListener());
        pushButton3.addActionListener(new PushButtonListener());
        pushButton4.addActionListener(new PushButtonListener());

        opponentButtons[0] = opponentButton1;
        opponentButtons[1] = opponentButton2;
        opponentButtons[2] = opponentButton3;
        opponentButtons[3] = opponentButton4;

        pushButtons[0] = pushButton1;
        pushButtons[1] = pushButton2;
        pushButtons[2] = pushButton3;
        pushButtons[3] = pushButton4;


        Button startButton = new Button("Start");
        startButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if(!ongoingRound) {

                            sequence = "";
                            startRound();

                        }

                    }

                }

        );

        //player playfield
        topPlayerJPanel.add(pushButton1);
        middlePlayerJPanel.add(pushButton2);
        middlePlayerJPanel.add(startButton);
        middlePlayerJPanel.add(pushButton3);
        bottomPlayerJPanel.add(pushButton4);
        Box playerBox = new Box(BoxLayout.Y_AXIS);
        playerBox.add(topPlayerJPanel);
        playerBox.add(middlePlayerJPanel);
        playerBox.add(bottomPlayerJPanel);

        //opponent playfield
        topOpponentJPanel.add(opponentButton1);
        middleOpponentJPanel.add(opponentButton2);
        middleOpponentJPanel.add(opponentButton3);
        bottomOpponentJPanel.add(opponentButton4);
        Box opponentBox = new Box(BoxLayout.Y_AXIS);
        opponentBox.add(topOpponentJPanel);
        opponentBox.add(middleOpponentJPanel);
        opponentBox.add(bottomOpponentJPanel);

        //player Stats like points and name

        Box playerStatsBox = new Box(BoxLayout.X_AXIS);
        playerNameInput = new JTextField("User");
        playerNameInput.addFocusListener(
                new FocusListener() {

                    @Override
                    public void focusGained(FocusEvent e) {

                    }

                    @Override
                    public void focusLost(FocusEvent e) {

                        player1NameLabel.setText(playerNameInput.getText());

                    }
                }
        );
        opponentNameLabel = new JLabel("No Player connected.");
        player1NameLabel = new JLabel(playerNameInput.getText());
        playerStatsBox.add(new JLabel("Name: "));
        playerStatsBox.add(playerNameInput);
        playerStatsBox.add(player1NameLabel);
        playerStatsBox.add(opponentNameLabel);

        //Box with all components
        Box topBox = new Box(BoxLayout.X_AXIS);
        topBox.add(playerBox);
        topBox.add(opponentBox);

        Box completeBox = new Box(BoxLayout.Y_AXIS);
        completeBox.add(topBox);
        completeBox.add(playerStatsBox);
        completeBox.add(statusArea);

        background.add(completeBox);

        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(background);
        /*
        frame.getContentPane().add(BorderLayout.SOUTH, pushButton2);
        frame.getContentPane().add(BorderLayout.NORTH, pushButton3);
        frame.getContentPane().add(BorderLayout.WEST, pushButton4);
        frame.getContentPane().add(BorderLayout.CENTER, button);
        */
        frame.setVisible(true);

        player1 = new PlayerObject();
        player1.username = playerNameInput.getText();


        setUpNetworking();
        setUpMidi();

        Thread t = new Thread(new IncomingReader());
        t.start();

    }

    private void setUpNetworking() {

        statusArea.append("Trying to create connection!");
        try {
            socket = new Socket("127.0.0.1", 3333);

            OutputStream os = socket.getOutputStream();
            objectOutput = new ObjectOutputStream(os);

            InputStream is = socket.getInputStream();
            ois = new ObjectInputStream(is);

            statusArea.append("\nConnection established!");
            player1.serverMessage = 2;
            objectOutput.reset();
            objectOutput.writeObject(player1);

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    private void startRound() {

        generateSequence();
        ongoingRound = true;
        inputAllowed = true;

    }

    private void generateSequence() {

        for(int i = 0; i < levelCounter; i++) {

            Integer rnd = (int) (Math.random() * 4);
            sequence = sequence + rnd.toString();
            track = sequenc.createTrack();
            pushButtons[rnd].buttonPressed(sequencer, track, sequenc);
            sequenc.deleteTrack(track);

        }

        levelCounter++;
        System.out.println(sequence);
    }

    class PushButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(inputAllowed) {
                PushButton pressedButton = (PushButton) e.getSource();
                int rightAnswer = Character.getNumericValue(sequence.charAt(0));

                try {

                    player1.setPushedButton(pressedButton.getButtonID());
                    player1.serverMessage = 1;
                    objectOutput.reset();
                    objectOutput.writeObject(player1);

                } catch(IOException e1) {

                    e1.printStackTrace();

                }

                if(pushButtons[rightAnswer] == pressedButton) {

                    player1.increaseRightAnswerCounter();
                    track = sequenc.createTrack();
                    pressedButton.buttonPressed(sequencer, track, sequenc);
                    sequenc.deleteTrack(track);
                    sequence = sequence.substring(1);

                    if(sequence.length() == 0) {

                        inputAllowed = false;
                        startRound();

                    }

                } else {

                    endgame();

                }

            }
        }

    }

    public void setUpMidi() {

        try {

            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequenc = new Sequence(Sequence.PPQ, 4);
            track = sequenc.createTrack();
            sequencer.setTempoInBPM(120);

        } catch(Exception ex) {

            ex.printStackTrace();

        }

    }

    private void endgame() {

        levelCounter = 3;
        ongoingRound = false;
        inputAllowed = false;
        System.out.println("Wrong answer. Lost the Game");

    }

    class IncomingReader implements Runnable {

        public void run() {

            PlayerObject playerObject;

            try {

                while((playerObject = (PlayerObject) ois.readObject()) != null) {

                    if(player1.userId != playerObject.userId && playerObject.serverMessage == 1) {

                        statusArea.append("\n" + playerObject.username + " pressed the " + playerObject.getPushedButton() + " Button");
                        opponentButtons[playerObject.getPushedButton() - 1].buttonPressed(sequencer, track, sequenc);

                    }
                    else if(player1.userId != playerObject.userId && playerObject.serverMessage == 0){

                        opponentNameLabel.setText(playerObject.username);

                    }
                    else if(player1.getUserId() == 0 && playerObject.serverMessage == 2) {
                        System.out.println("set user id");
                        player1.setUserId(playerObject.getUserId());
                    }

                }

            } catch(Exception ex) {

                ex.printStackTrace();

            }

        }

    }

}
