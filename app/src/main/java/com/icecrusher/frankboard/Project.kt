package com.icecrusher.frankboard

data class Project(
    val name: String,
    val status: String,
    val progress: Int,
    val version: String,
    val currentTask: String,
    val nextTask: String,
    val memory: ProjectMemory,
    val architecture: ProjectArchitecture,
    val state: ProjectState = ProjectState(),
    val source: ProjectSource = ProjectSource.LOCAL
)