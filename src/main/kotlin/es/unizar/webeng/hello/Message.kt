package es.unizar.webeng.hello;

import java.util.Date;

class Message(msg: String) {

    private var message: String = msg
    private var time = Date();

    fun getMessage() : String = message;
    fun getDate() : Date = time;


}