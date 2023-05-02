package com.example.skincareshopapp.model

data class Shipping(val id_shipping:Int ?= null,
                    val shipping_address:String ?= null ,
                    val shipping_name:String ?=null,
                    val shipping_phone:String ?= null,
                    val shipping_note:String ?= null
                    )
