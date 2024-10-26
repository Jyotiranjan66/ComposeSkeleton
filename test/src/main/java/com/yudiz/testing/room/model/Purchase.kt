package com.yudiz.testing.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Purchase(
    @PrimaryKey val uid: Int,
    val item: String?,
    val cost: Int
)