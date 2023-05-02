package com.example.skincareshopapp.model

import java.time.format.DateTimeFormatter

data class Orders(val id_order:Int?=null,
                  val email: String ?= null,
                  val order_status: Int ?= null,
                  val order_total: Double? = null,
                  val order_date : DateTimeFormatter ?= null,
                  val id_shipping : Int ?= null,
                  val id_coupon : Int ?= null
                  )
