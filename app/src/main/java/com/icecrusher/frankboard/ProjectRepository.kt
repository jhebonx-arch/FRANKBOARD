package com.icecrusher.frankboard

object ProjectRepository {

    val projects: List<Project> = listOf(

        Project(
            name = "MUSICBOXFRANK",
            status = "ACTIVE",
            progress = 75,
            version = "v2.2",
            currentTask = "Развитие музыкальной платформы",
            nextTask = "Telegram Sync / Dashboard",

            memory = ProjectMemory(
                checkpoint = "MUSICBOXFRANK v2.2 checkpoint",

                completed = listOf(
                    "Основная музыкальная платформа",
                    "9 музыкальных зон",
                    "Audio Player",
                    "Регистрация пользователей",
                    "SQLite профиль",
                    "Базовая визуальная система"
                ),

                remaining = listOf(
                    "Telegram Sync",
                    "Dashboard",
                    "Расширение профиля",
                    "Автоматизация загрузки треков"
                ),

                decisions = listOf(
                    "Telegram используется как источник экосистемы",
                    "Пользовательские данные отделены от музыкального контента",
                    "Изменения выполняются контролируемыми этапами"
                ),

                errors = listOf(
                    "Предыдущие JS-конфликты исправлены",
                    "Telegram требует стабильного сетевого соединения"
                )
            ),

            architecture = ProjectArchitecture(
                description = "Frontend → PHP API → SQLite / Telegram → Music Library"
            ),

            state = ProjectState(
                status = "ACTIVE",
                progress = 75,
                currentTask = "Развитие музыкальной платформы",
                nextTask = "Telegram Sync / Dashboard",

                facts = listOf(
                    "GitHub-проект AMBRELA существует",
                    "Основная платформа работает на PHP",
                    "Профиль пользователя хранится в SQLite",
                    "Telegram является частью экосистемы",
                    "Текущая версия v2.2"
                ),

                interpretation = listOf(
                    "Проект находится в активной разработке",
                    "Следующий крупный этап — Telegram Sync и Dashboard",
                    "Текущую стабильную версию необходимо сохранять"
                ),

                humanDecisions = listOf(
                    "Сначала стабилизировать текущую систему",
                    "Развивать проект контролируемыми этапами",
                    "Разделять пользовательские данные и музыкальный контент"
                )
            ),

            source = ProjectSource.LOCAL
        ),

        Project(
            name = "BUSIDO",
            status = "PAUSED",
            progress = 35,
            version = "0.1.0",
            currentTask = "Quest Board / HUD",
            nextTask = "Продолжение Android-разработки",

            memory = ProjectMemory(
                checkpoint = "BUSIDO QUEST 06.12 — HUD CHECKPOINT",

                completed = listOf(
                    "Базовый Android-проект",
                    "Jetpack Compose",
                    "Персонаж",
                    "XP / Level",
                    "HUD",
                    "Основа Quest Board"
                ),

                remaining = listOf(
                    "Исправление UI",
                    "Навигация",
                    "Quest System",
                    "Анимации",
                    "Финальная структура экранов"
                ),

                decisions = listOf(
                    "Сначала статический UI",
                    "Анимации после стабилизации UI",
                    "Не продолжать при наличии красных ошибок"
                ),

                errors = listOf(
                    "Ранее возникало большое количество Compose-ошибок",
                    "HUD и Quest Board требовали переработки"
                )
            ),

            architecture = ProjectArchitecture(
                description = "Android → Kotlin → Jetpack Compose → Local Game State"
            ),

            state = ProjectState(
                status = "PAUSED",
                progress = 35,
                currentTask = "Quest Board / HUD",
                nextTask = "Продолжение Android-разработки",

                facts = listOf(
                    "Android + Kotlin",
                    "Jetpack Compose",
                    "Персонаж / XP / Level / HUD",
                    "Основа Quest Board",
                    "Версия 0.1.0"
                ),

                interpretation = listOf(
                    "Проект временно приостановлен",
                    "Основной риск — стабильность Compose UI",
                    "Следует сначала стабилизировать интерфейс"
                ),

                humanDecisions = listOf(
                    "Сначала статический интерфейс",
                    "Анимацию добавлять позже",
                    "Не двигаться дальше при красных ошибках"
                )
            ),

            source = ProjectSource.LOCAL
        ),

        Project(
            name = "РЕЙС-КОНТРОЛЬ",
            status = "IN DEVELOPMENT",
            progress = 45,
            version = "0.1",
            currentTask = "Telegram + PostgreSQL",
            nextTask = "Работа с рейсами и сессиями",

            memory = ProjectMemory(
                checkpoint = "PostgreSQL foundation checkpoint",

                completed = listOf(
                    "Telegram Bot",
                    "Python environment",
                    "PostgreSQL 16",
                    "Основные таблицы",
                    "psycopg",
                    "База сотрудников"
                ),

                remaining = listOf(
                    "Сменные сессии",
                    "Рейсы",
                    "Голосовые команды",
                    "Anti-duplicate",
                    "Отчёты"
                ),

                decisions = listOf(
                    "PostgreSQL является основной БД",
                    "Android Offline будет использовать SQLite",
                    "Синхронизация будет отдельным слоем"
                ),

                errors = listOf(
                    "Ранее возникали проблемы с правами PostgreSQL",
                    "Голосовой workflow Telegram требует тестирования"
                )
            ),

            architecture = ProjectArchitecture(
                description = "Telegram → Python → PostgreSQL → Future Android App"
            ),

            state = ProjectState(
                status = "IN DEVELOPMENT",
                progress = 45,
                currentTask = "Telegram + PostgreSQL",
                nextTask = "Работа с рейсами и сессиями",

                facts = listOf(
                    "Telegram-бот создан",
                    "Python environment подготовлен",
                    "PostgreSQL 16.15",
                    "База reis_kontrol",
                    "Основные таблицы созданы",
                    "psycopg установлен",
                    "Структура сотрудников существует"
                ),

                interpretation = listOf(
                    "Серверная основа сформирована",
                    "Следующий критический этап — учёт смен и рейсов",
                    "Голосовой workflow необходимо тестировать отдельно"
                ),

                humanDecisions = listOf(
                    "PostgreSQL — основная БД",
                    "Android Offline — SQLite",
                    "Синхронизация — отдельный слой",
                    "Сначала стабилизировать сервер"
                )
            ),

            source = ProjectSource.LOCAL
        )
    )

    fun getProjectByName(name: String): Project? {
        return projects.find { it.name == name }
    }
}