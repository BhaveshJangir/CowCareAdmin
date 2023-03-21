package com.example.cowcareadmin

class ChatModel {
    var message:String?=null
    var sender:String?=null
    constructor(){}
    constructor(message:String?,sender:String?){
        this.message=message
        this.sender=sender
    }
}