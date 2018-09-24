package es.unizar.webeng.hello;

class URINotFoundException(private var msg: Message) : Exception(msg.getMessage()) {

    fun getMsg() : Message = msg

}