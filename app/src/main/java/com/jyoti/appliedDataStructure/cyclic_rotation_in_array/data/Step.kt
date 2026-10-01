package com.jyoti.appliedDataStructure.cyclic_rotation_in_array.data

data class Step(
    val arr: List<Int>,
    val i: Int,           // current index
    val j: Int?,          // index where the number belongs
    val message: String,
    val duplicate: Int? = null
)
