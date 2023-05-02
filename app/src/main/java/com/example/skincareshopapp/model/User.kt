package com.example.skincareshopapp.model

data class User(
    val userId:Int ?= null,
    val email:String ?= null,
    val username:String? = null,
    val password:String?= null,
    val created_at:String?= null,
    val updated_at:String?=null,
    val address:String?=null,
    val numberphone:String? =null,
    val otp:Int?=null,
    val status:Int?=null,

)
