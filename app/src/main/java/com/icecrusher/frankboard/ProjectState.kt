package com.icecrusher.frankboard

data class ProjectState(
    val status: String = "",
    val progress: Int = 0,
    val currentTask: String = "",
    val nextTask: String = "",
    val facts: List<String> = emptyList(),
    val interpretation: List<String> = emptyList(),
    val humanDecisions: List<String> = emptyList(),
    val lastUpdated: String = "",
    val stateVersion: Int = 1
)