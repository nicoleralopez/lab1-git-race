package es.unizar.webeng.hello;

class URINotFoundException(val msg: Message) : Exception(msg.message)