package com.icecrusher.frankboard

data class AppStrings(
    val back: String,
    val dashboardMenu: String,
    val mainFunctions: String,

    val settings: String,
    val settingsDescription: String,

    val emailGmail: String,
    val emailGmailDescription: String,

    val sync: String,
    val syncDescription: String,

    val aboutFrankBoard: String,
    val aboutFrankBoardDescription: String,

    val generalSettings: String,
    val language: String,
    val languageDescription: String,
    val russian: String,
    val english: String,
    val currentLanguage: String,

    val project: String,
    val projects: String,
    val allProjects: String,
    val projectSystem: String,
    val activeProjects: String,
    val centralizedProjectState: String,

    val projectOverview: String,
    val projectStatus: String,
    val currentTask: String,
    val nextTask: String,
    val status: String,
    val progress: String,
    val version: String,

    val projectMemory: String,
    val checkpoint: String,
    val completed: String,
    val remaining: String,
    val decisions: String,
    val errorsConstraints: String,

    val whatAlreadyDone: String,
    val whatRemains: String,
    val keyProjectDecisions: String,
    val problemsConstraints: String,

    val projectState: String,
    val facts: String,
    val objectiveProjectFacts: String,
    val interpretation: String,
    val currentStateInterpretation: String,
    val humanDecisions: String,
    val developerDecisions: String,

    val architecture: String,
    val description: String,
    val source: String,

    val local: String,
    val github: String,
    val ai: String,
    val mixed: String,

    val notebook: String,
    val personalNotes: String,
    val newNote: String,
    val noNotesYet: String,
    val createFirstNote: String,

    val newEntry: String,
    val editing: String,
    val delete: String,
    val myNote: String,
    val title: String,
    val text: String,
    val save: String,

    val noTitle: String,
    val emptyEntry: String,

    val black: String,
    val red: String,
    val blue: String,
    val green: String,
    val yellow: String
) {

    companion object {

        fun forLanguage(language: AppLanguage): AppStrings {

            return when (language) {

                AppLanguage.RUSSIAN -> AppStrings(
                    back = "← НАЗАД",
                    dashboardMenu = "МЕНЮ DASHBOARD",
                    mainFunctions = "ОСНОВНЫЕ ФУНКЦИИ",

                    settings = "НАСТРОЙКИ",
                    settingsDescription = "Настройки FRANKBOARD",

                    emailGmail = "EMAIL / GMAIL",
                    emailGmailDescription = "Подключение электронной почты",

                    sync = "SYNC",
                    syncDescription = "Синхронизация данных",

                    aboutFrankBoard = "ABOUT FRANKBOARD",
                    aboutFrankBoardDescription = "Информация о системе",

                    generalSettings = "ОБЩИЕ НАСТРОЙКИ",
                    language = "🌐 ЯЗЫК",
                    languageDescription = "Выберите язык интерфейса FRANKBOARD.",
                    russian = "🇷🇺 Русский",
                    english = "🇬🇧 English",
                    currentLanguage = "Текущий язык: Русский",

                    project = "PROJECT",
                    projects = "PROJECTS",
                    allProjects = "Все проекты",
                    projectSystem = "PROJECT SYSTEM",
                    activeProjects = "активных проектов",
                    centralizedProjectState =
                        "Централизованное состояние, память и архитектура проектов.",

                    projectOverview = "PROJECT OVERVIEW",
                    projectStatus = "Статус проекта",
                    currentTask = "ТЕКУЩАЯ ЗАДАЧА",
                    nextTask = "СЛЕДУЮЩАЯ ЗАДАЧА",
                    status = "STATUS",
                    progress = "PROGRESS",
                    version = "VERSION",

                    projectMemory = "PROJECT MEMORY",
                    checkpoint = "CHECKPOINT",
                    completed = "COMPLETED",
                    remaining = "REMAINING",
                    decisions = "DECISIONS",
                    errorsConstraints = "ERRORS / CONSTRAINTS",

                    whatAlreadyDone = "Что уже сделано",
                    whatRemains = "Что ещё предстоит сделать",
                    keyProjectDecisions = "Ключевые решения по проекту",
                    problemsConstraints =
                        "Проблемы, ограничения и важные условия",

                    projectState = "PROJECT STATE",
                    facts = "FACTS",
                    objectiveProjectFacts = "Объективные факты проекта",
                    interpretation = "INTERPRETATION",
                    currentStateInterpretation =
                        "Интерпретация текущего состояния",
                    humanDecisions = "HUMAN DECISIONS",
                    developerDecisions = "Решения разработчика",

                    architecture = "ARCHITECTURE",
                    description = "DESCRIPTION",
                    source = "SOURCE",

                    local = "LOCAL",
                    github = "GITHUB",
                    ai = "AI",
                    mixed = "MIXED",

                    notebook = "NOTEBOOK",
                    personalNotes = "PERSONAL NOTES",
                    newNote = "+ НОВАЯ ЗАПИСЬ",
                    noNotesYet = "ЗАПИСЕЙ ПОКА НЕТ",
                    createFirstNote = "Создай первую заметку.",

                    newEntry = "НОВАЯ ЗАПИСЬ",
                    editing = "РЕДАКТИРОВАНИЕ",
                    delete = "УДАЛИТЬ",
                    myNote = "📓 МОЯ ЗАПИСЬ",
                    title = "Название",
                    text = "Текст",
                    save = "СОХРАНИТЬ",

                    noTitle = "Без названия",
                    emptyEntry = "Пустая запись",

                    black = "⬛ Чёрный",
                    red = "🟥 Красный",
                    blue = "🟦 Синий",
                    green = "🟩 Зелёный",
                    yellow = "🟨 Жёлтый"
                )

                AppLanguage.ENGLISH -> AppStrings(
                    back = "← BACK",
                    dashboardMenu = "DASHBOARD MENU",
                    mainFunctions = "MAIN FUNCTIONS",

                    settings = "SETTINGS",
                    settingsDescription = "FRANKBOARD settings",

                    emailGmail = "EMAIL / GMAIL",
                    emailGmailDescription = "Email connection",

                    sync = "SYNC",
                    syncDescription = "Data synchronization",

                    aboutFrankBoard = "ABOUT FRANKBOARD",
                    aboutFrankBoardDescription = "System information",

                    generalSettings = "GENERAL SETTINGS",
                    language = "🌐 LANGUAGE",
                    languageDescription =
                        "Choose the FRANKBOARD interface language.",
                    russian = "🇷🇺 Russian",
                    english = "🇬🇧 English",
                    currentLanguage = "Current language: English",

                    project = "PROJECT",
                    projects = "PROJECTS",
                    allProjects = "All projects",
                    projectSystem = "PROJECT SYSTEM",
                    activeProjects = "active projects",
                    centralizedProjectState =
                        "Centralized project state, memory and architecture.",

                    projectOverview = "PROJECT OVERVIEW",
                    projectStatus = "Project status",
                    currentTask = "CURRENT TASK",
                    nextTask = "NEXT TASK",
                    status = "STATUS",
                    progress = "PROGRESS",
                    version = "VERSION",

                    projectMemory = "PROJECT MEMORY",
                    checkpoint = "CHECKPOINT",
                    completed = "COMPLETED",
                    remaining = "REMAINING",
                    decisions = "DECISIONS",
                    errorsConstraints = "ERRORS / CONSTRAINTS",

                    whatAlreadyDone = "What has already been done",
                    whatRemains = "What remains to be done",
                    keyProjectDecisions = "Key project decisions",
                    problemsConstraints =
                        "Problems, constraints and important conditions",

                    projectState = "PROJECT STATE",
                    facts = "FACTS",
                    objectiveProjectFacts = "Objective project facts",
                    interpretation = "INTERPRETATION",
                    currentStateInterpretation =
                        "Interpretation of the current state",
                    humanDecisions = "HUMAN DECISIONS",
                    developerDecisions = "Developer decisions",

                    architecture = "ARCHITECTURE",
                    description = "DESCRIPTION",
                    source = "SOURCE",

                    local = "LOCAL",
                    github = "GITHUB",
                    ai = "AI",
                    mixed = "MIXED",

                    notebook = "NOTEBOOK",
                    personalNotes = "PERSONAL NOTES",
                    newNote = "+ NEW NOTE",
                    noNotesYet = "NO NOTES YET",
                    createFirstNote = "Create your first note.",

                    newEntry = "NEW ENTRY",
                    editing = "EDITING",
                    delete = "DELETE",
                    myNote = "📓 MY NOTE",
                    title = "Title",
                    text = "Text",
                    save = "SAVE",

                    noTitle = "Untitled",
                    emptyEntry = "Empty entry",

                    black = "⬛ Black",
                    red = "🟥 Red",
                    blue = "🟦 Blue",
                    green = "🟩 Green",
                    yellow = "🟨 Yellow"
                )
            }
        }
    }
}