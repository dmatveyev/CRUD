/*
package com.client.bot;

import com.client.model.User;
import com.client.service.UsersService;

import com.client.service.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;
import java.util.logging.Logger;

@Service
public class Bot extends TelegramLongPollingBot {

    private Logger log = Logger.getLogger("Bot");
    @Autowired
    private UsersService usersService;
    */
/**
            * Метод для приема сообщений.
            * @param update Содержит сообщение от пользователя.
     *//*

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage(); // Это нам понадобится
        String txt = msg.getText();
        if (txt.equals("/start")) {
            sendMsg(msg, "Hello, world! This is simple bot!");
        }
        if (txt.equals("/getallusers")){
            List<User> users = usersService.getAll();
            for (User u: users) {
                sendMsg(msg, u.toString());
            }
        }
    }


    private void sendMsg(Message msg, String text) {
        SendMessage s = new SendMessage();
        s.setChatId(msg.getChatId()); // Боту может писать не один человек, и поэтому чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять
        s.setText(text);
        try { //Чтобы не крашнулась программа при вылете Exception
            sendMessage(s);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    */
/**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     *//*

    @Override
    public String getBotUsername() {
        return "CRUDbot";
    }

    */
/**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     *//*

    @Override
    public String getBotToken() {
        return "575870339:AAEIQZ9QvK0bDt1TgGhW0bayWPgIDn6t4f8";
    }
}
*/
