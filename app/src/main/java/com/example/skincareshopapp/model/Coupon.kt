package com.example.skincareshopapp.model

import java.util.*

data class Coupon(val id_coupon:Int?=null,
                  val coupon_name:String?=null,
                  val coupon_time:Int?=null,
                  val coupon_number:Int?=null,
                  val coupon_condition:Int?=null,
                  val coupon_code:String?=null,
                  val date_start:Date ?= null,
                  val date_end:Date?=null,


                  )
