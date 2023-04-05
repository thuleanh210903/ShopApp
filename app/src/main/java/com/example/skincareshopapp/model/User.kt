package com.example.skincareshopapp.model

data class User(
    val userId:Int ?= null,
    val name:String ?= null,
    val username:String? = null,
    val password:String?= null,
    val address:String?=null,
    val numberphone:String? =null,
    val otp:Int?=null,
    val status:Int?=null,

)
