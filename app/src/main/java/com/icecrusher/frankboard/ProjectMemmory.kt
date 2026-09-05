package com.icecrusher.frankboard

data class ProjectMemory(
    val checkpoint: String,
    val completed: List<String>,
    val remaining: List<String>,
    val decisions: List<String>,
    val errors: List<String>
)