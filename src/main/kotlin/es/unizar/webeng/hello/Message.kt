package es.unizar.webeng.hello;

import java.util.Date;

class Message {
    /* 
     * Properties declared in kotlin way. We don't use getters or setters as
     * recomended in kotlin reference: https://kotlinlang.org/docs/reference/properties.html
     */
    var message: String = ""
    var time = Date()

    constructor(msg: String) {
        this.message = msg
    }

    constructor() {
        this.message = ""
    }
}