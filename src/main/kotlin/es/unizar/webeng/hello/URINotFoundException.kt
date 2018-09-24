package es.unizar.webeng.hello;

class URINotFoundException(private var msg: Message) : Exception(msg.message) {

    fun getMsg() : Message = msg

}