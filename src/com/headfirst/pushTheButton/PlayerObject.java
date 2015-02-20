package com.headfirst.pushTheButton;

import java.io.Serializable;

/**
 * Created by User on 19.02.2015.
 */
public class PlayerObject implements Serializable {

    int pushedButton;
    int userId;
    int rightAnswerCounter = 0;
    String username;

    PlayerObject(int userId){

        this.userId = userId;

    }

    public int getPushedButton() {

        return pushedButton;
    }

    public void setPushedButton(int pushedButton) {

        this.pushedButton = pushedButton;
    }

    public void setRightAnswerCounter(int rightAnswerCounter) {

        this.rightAnswerCounter = rightAnswerCounter;
    }

    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {

        this.userId = userId;
    }

    public int getRightAnswerCounter() {

        return rightAnswerCounter;
    }

    public void increaseRightAnswerCounter() {

        this.rightAnswerCounter++;

    }
}
