package es.unizar.webeng.hello

class InvalidWelcomeMessageException(val msg: Message) : Exception(msg.message)