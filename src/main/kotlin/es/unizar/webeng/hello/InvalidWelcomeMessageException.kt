package es.unizar.webeng.hello;

class InvalidWelcomeMessageException(private var msg: Message) : Exception(msg.message) {

    fun getMsg() : Message = msg

}