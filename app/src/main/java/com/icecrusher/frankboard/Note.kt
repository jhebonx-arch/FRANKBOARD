package com.icecrusher.frankboard

data class Note(
    val id: Long,
    val title: String,
    val text: String,
    val createdAt: Long,
    val updatedAt: Long,
    val color: String = "yellow"
)