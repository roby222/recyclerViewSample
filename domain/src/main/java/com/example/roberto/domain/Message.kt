package com.example.roberto.domain

import java.util.ArrayList

class Message(val id: Long, val userId: Long, val content: String, val attachment: ArrayList<Attachment>)
