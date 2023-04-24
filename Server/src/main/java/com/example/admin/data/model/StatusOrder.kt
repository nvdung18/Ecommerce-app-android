package com.example.admin.data.model

import androidx.room.TypeConverters
import com.example.admin.data.room.ConvertersDate
import com.google.type.DateTime
import java.sql.Time
import java.util.Date
//@TypeConverters(ConvertersDate::class)
data class StatusOrder(val date: Date,val statusOrder:String) {}