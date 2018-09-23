package es.unizar.webeng.hello;

import java.util.Date;

class Message {

    private var message: String = ""
    private var time = Date()

    constructor(msg: String) {
        this.message = msg
    }

    constructor() {
        this.message = ""
    }

    fun getMessage() : String = message
    fun getTime() : Date = time

    fun setMessage(msg: String) {message = msg}
    fun setTime(date: Date) {time = date}


}