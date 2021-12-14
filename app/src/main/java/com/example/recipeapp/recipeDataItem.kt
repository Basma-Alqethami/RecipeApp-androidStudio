package com.example.recipeapp

import java.io.Serializable

data class recipeDataItem(
    val pk: Int,
    val author: String,
    val ingredients: String,
    val instructions: String,
    val title: String
): Serializable