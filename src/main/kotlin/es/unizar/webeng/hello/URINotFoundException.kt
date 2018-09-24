package es.unizar.webeng.hello;

class URINotFoundException(private var msg: Message) : Exception(msg.message) {

    var mg : Message = msg

}