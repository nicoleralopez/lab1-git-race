package es.unizar.webeng.hello;

class InvalidWelcomeMessageException(private var msg: Message) : Exception(msg.getMessage()) {

    fun getMsg() : Message = msg

}