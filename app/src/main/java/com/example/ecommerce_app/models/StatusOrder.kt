package com.example.ecommerce_app.models

import androidx.room.TypeConverters
import com.google.type.DateTime
import java.sql.Time
import java.util.Date
data class StatusOrder(val date: Date,val statusOrder:String) {}