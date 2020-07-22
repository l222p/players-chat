package com.luisaraujo.app.services.impl;

import java.util.HashMap;

import com.luisaraujo.app.models.Message;
import com.luisaraujo.app.models.Player;
import com.luisaraujo.app.services.ChatService;

public class ChatServiceImpl implements ChatService {
    
    private Message message;

    private final static Integer MAX_SENT_MESSAGES = 10;
    private final static String MAX_MESSAGES_REACHED_NOTIFICATION = "You have reached the max messages allowed";

    public ChatServiceImpl() {
        
    }

    public String sendMessage(Player initiator, Player receiver, String message){
        this.message = new Message(initiator, receiver, message);
        HashMap<Player, Integer> playerSentMessages =  initiator.getSentMessages();
        Integer counter = handleCounter(playerSentMessages, receiver);
        
        if(counter == -1)
            return MAX_MESSAGES_REACHED_NOTIFICATION;

        counter++;
        initiator.setSentMessages(receiver, counter);
        receiver.setReceivedMessages(initiator, counter);
        return this.message.getMessage() + " | " + receiver.getReceivedMessage(initiator);
    }

    private Integer handleCounter(HashMap<Player, Integer> playerSentMessages, Player receiver){
        Integer counter = playerSentMessages.get(receiver) == null? 0: playerSentMessages.get(receiver);
        return counter == MAX_SENT_MESSAGES ? -1: counter;
    }

}