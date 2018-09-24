package es.unizar.webeng.hello;

class InvalidWelcomeMessageException(private var msg: Message) : Exception(msg.message) {

    var mg : Message = msg

}