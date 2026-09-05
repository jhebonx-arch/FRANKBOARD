package com.icecrusher.frankboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items as gridItems
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icecrusher.frankboard.ui.theme.FRANKBOARDTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val LocalAppStrings = staticCompositionLocalOf {
    AppStrings.forLanguage(AppLanguage.RUSSIAN)
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            FRANKBOARDTheme {
                FrankBoardApp()
            }
        }
    }
}

@Composable
fun FrankBoardApp() {

    val context = LocalContext.current

    var language by remember {
        mutableStateOf(
            LanguageManager.getLanguage(context)
        )
    }

    val strings = AppStrings.forLanguage(language)

    var selectedProject by remember {
        mutableStateOf<Project?>(null)
    }

    var showProjectFolder by remember {
        mutableStateOf(false)
    }

    var showNotebook by remember {
        mutableStateOf(false)
    }

    var showDashboardMenu by remember {
        mutableStateOf(false)
    }

    var showSettings by remember {
        mutableStateOf(false)
    }

    CompositionLocalProvider(
        LocalAppStrings provides strings
    ) {

        when {

            selectedProject != null -> {

                ProjectDetailScreen(
                    project = selectedProject!!,
                    onBack = {
                        selectedProject = null
                    }
                )
            }

            showProjectFolder -> {

                ProjectFolderScreen(
                    onBack = {
                        showProjectFolder = false
                    },
                    onProjectClick = { project ->
                        selectedProject = project
                    }
                )
            }

            showNotebook -> {

                NotebookScreen(
                    onBack = {
                        showNotebook = false
                    }
                )
            }

            showSettings -> {

                SettingsScreen(
                    language = language,
                    onLanguageChange = { newLanguage ->

                        LanguageManager.saveLanguage(
                            context = context,
                            language = newLanguage
                        )

                        language = newLanguage
                    },
                    onBack = {
                        showSettings = false
                    }
                )
            }

            showDashboardMenu -> {

                DashboardMenuScreen(
                    onBack = {
                        showDashboardMenu = false
                    },
                    onSettingsClick = {
                        showDashboardMenu = false
                        showSettings = true
                    }
                )
            }

            else -> {

                DashboardScreen(
                    onMenuClick = {
                        showDashboardMenu = true
                    },
                    onProjectClick = {
                        showProjectFolder = true
                    },
                    onNotebookClick = {
                        showNotebook = true
                    }
                )
            }
        }
    }
}

@Composable
fun DashboardScreen(
    onMenuClick: () -> Unit,
    onProjectClick: () -> Unit,
    onNotebookClick: () -> Unit
) {

    val strings = LocalAppStrings.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Background {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                }

                item {
                    DashboardMenuButton(
                        onClick = onMenuClick
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )
                }

                item {
                    Header()
                }

                item {
                    SummaryCard()
                }

                item {
                    SectionTitle(
                        title = strings.project
                    )
                }

                item {

                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onProjectClick()
                            }
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "📁",
                                fontSize = 32.sp
                            )

                            Spacer(
                                modifier = Modifier.width(14.dp)
                            )

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = strings.project,
                                    color = Color.White,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(
                                    text = strings.allProjects,
                                    color = Color.White.copy(alpha = 0.65f),
                                    fontSize = 13.sp
                                )
                            }

                            Text(
                                text = "›",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 30.sp
                            )
                        }
                    }
                }

                item {

                    SectionTitle(
                        title = strings.notebook
                    )
                }

                item {

                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onNotebookClick()
                            }
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "📓",
                                fontSize = 32.sp
                            )

                            Spacer(
                                modifier = Modifier.width(14.dp)
                            )

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = strings.notebook,
                                    color = Color.White,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(
                                    text = strings.personalNotes,
                                    color = Color.White.copy(alpha = 0.65f),
                                    fontSize = 13.sp
                                )
                            }

                            Text(
                                text = "›",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 30.sp
                            )
                        }
                    }
                }

                item {

                    Spacer(
                        modifier = Modifier.height(18.dp)
                    )
                }

                item {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 2.dp,
                                bottom = 10.dp
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "FRANKBOARD · v0.1.0",
                            color = Color.White.copy(alpha = 0.30f),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Normal
                        )

                        Spacer(
                            modifier = Modifier.height(2.dp)
                        )

                        Text(
                            text = "Создано: Frank~Hamza",
                            color = Color.White.copy(alpha = 0.25f),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }

                item {
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                }
            }
        }
    }
}

/* ============================================================
   DASHBOARD MENU
   ============================================================ */

@Composable
fun DashboardMenuButton(
    onClick: () -> Unit
) {

    Surface(
        modifier = Modifier
            .size(42.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(12.dp),
        color = Color.Black.copy(alpha = 0.45f),
        border = BorderStroke(
            width = 1.dp,
            color = Color.White.copy(alpha = 0.22f)
        ),
        shadowElevation = 5.dp
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(2.dp)
                    .clip(
                        RoundedCornerShape(2.dp)
                    )
                    .background(
                        Color.White.copy(alpha = 0.9f)
                    )
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Box(
                modifier = Modifier
                    .width(11.dp)
                    .height(2.dp)
                    .clip(
                        RoundedCornerShape(2.dp)
                    )
                    .background(
                        Color.White.copy(alpha = 0.9f)
                    )
            )
        }
    }
}

@Composable
fun DashboardMenuScreen(
    onBack: () -> Unit,
    onSettingsClick: () -> Unit
) {

    val strings = LocalAppStrings.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Background {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                }

                item {

                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black.copy(alpha = 0.45f),
                            contentColor = Color.White
                        )
                    ) {
                        Text(strings.back)
                    }
                }

                item {

                    Text(
                        text = strings.dashboardMenu,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                item {

                    Text(
                        text = strings.mainFunctions,
                        color = Color.White.copy(alpha = 0.65f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )
                }

                item {

                    DashboardMenuItem(
                        icon = "⚙",
                        title = strings.settings,
                        description = strings.settingsDescription,
                        onClick = onSettingsClick
                    )
                }

                item {

                    DashboardMenuItem(
                        icon = "✉",
                        title = strings.emailGmail,
                        description = strings.emailGmailDescription,
                        onClick = {
                            // Функциональность добавим следующим этапом.
                        }
                    )
                }

                item {

                    DashboardMenuItem(
                        icon = "☁",
                        title = strings.sync,
                        description = strings.syncDescription,
                        onClick = {
                            // Функциональность добавим следующим этапом.
                        }
                    )
                }

                item {

                    DashboardMenuItem(
                        icon = "ℹ",
                        title = strings.aboutFrankBoard,
                        description = strings.aboutFrankBoardDescription,
                        onClick = {
                            // Функциональность добавим следующим этапом.
                        }
                    )
                }

                item {

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )
                }
            }
        }
    }
}

/* ============================================================
   SETTINGS
   ============================================================ */

@Composable
fun SettingsScreen(
    language: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    onBack: () -> Unit
) {

    val strings = LocalAppStrings.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Background {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                }

                item {

                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black.copy(alpha = 0.45f),
                            contentColor = Color.White
                        )
                    ) {

                        Text(
                            text = strings.back
                        )
                    }
                }

                item {

                    Text(
                        text = strings.settings,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                item {

                    Text(
                        text = strings.generalSettings,
                        color = Color.White.copy(alpha = 0.65f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )
                }

                item {

                    GlassCard {

                        Column {

                            Text(
                                text = strings.language,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Text(
                                text = strings.languageDescription,
                                color = Color.White.copy(alpha = 0.65f),
                                fontSize = 13.sp
                            )

                            Spacer(
                                modifier = Modifier.height(14.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                Button(
                                    onClick = {
                                        onLanguageChange(
                                            AppLanguage.RUSSIAN
                                        )
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (
                                            language == AppLanguage.RUSSIAN
                                        ) {
                                            Color.White.copy(alpha = 0.22f)
                                        } else {
                                            Color.Black.copy(alpha = 0.35f)
                                        },
                                        contentColor = Color.White
                                    )
                                ) {

                                    Text(
                                        text = strings.russian
                                    )
                                }

                                Button(
                                    onClick = {
                                        onLanguageChange(
                                            AppLanguage.ENGLISH
                                        )
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (
                                            language == AppLanguage.ENGLISH
                                        ) {
                                            Color.White.copy(alpha = 0.22f)
                                        } else {
                                            Color.Black.copy(alpha = 0.35f)
                                        },
                                        contentColor = Color.White
                                    )
                                ) {

                                    Text(
                                        text = strings.english
                                    )
                                }
                            }

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )

                            Text(
                                text = strings.currentLanguage,
                                color = Color.White.copy(alpha = 0.55f),
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                item {

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardMenuItem(
    icon: String,
    title: String,
    description: String,
    onClick: () -> Unit
) {

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = icon,
                color = Color.White,
                fontSize = 28.sp
            )

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = description,
                    color = Color.White.copy(alpha = 0.65f),
                    fontSize = 13.sp
                )
            }

            Text(
                text = "›",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 30.sp
            )
        }
    }
}

/* ============================================================
   PROJECT DETAIL
   ============================================================ */

@Composable
fun ProjectDetailScreen(
    project: Project,
    onBack: () -> Unit
) {
    val strings = LocalAppStrings.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Background {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {
                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                }

                item {
                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black.copy(alpha = 0.45f),
                            contentColor = Color.White
                        )
                    ) {
                        Text(strings.back)
                    }
                }

                item {
                    ProjectHero(
                        project = project
                    )
                }

                item {
                    InfoCard(
                        title = strings.projectOverview
                    ) {
                        Text(
                            text = "${strings.projectStatus}: ${project.state.status}",
                            color = Color.White,
                            fontSize = 15.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = "${strings.currentTask}: ${project.state.currentTask}",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 14.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = "${strings.nextTask}: ${project.state.nextTask}",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 14.sp
                        )
                    }
                }

                item {
                    InfoCard(
                        title = strings.status
                    ) {
                        Text(
                            text = project.state.status,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                item {
                    InfoCard(
                        title = strings.progress
                    ) {
                        Text(
                            text = "${project.state.progress}%",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        LinearProgressIndicator(
                            progress = {
                                project.state.progress / 100f
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                        )
                    }
                }

                item {
                    InfoCard(
                        title = strings.version
                    ) {
                        Text(
                            text = project.version,
                            color = Color.White
                        )
                    }
                }

                item {
                    InfoCard(
                        title = strings.currentTask
                    ) {
                        Text(
                            text = project.state.currentTask,
                            color = Color.White
                        )
                    }
                }

                item {
                    InfoCard(
                        title = strings.nextTask
                    ) {
                        Text(
                            text = project.state.nextTask,
                            color = Color.White
                        )
                    }
                }

                item {
                    SectionTitle(
                        title = strings.projectMemory
                    )
                }

                item {
                    MemoryCard(
                        title = strings.checkpoint,
                        value = project.memory.checkpoint
                    )
                }

                item {
                    InfoCard(
                        title = strings.completed
                    ) {
                        Text(
                            text = strings.whatAlreadyDone,
                            color = Color.White.copy(alpha = 0.55f),
                            fontSize = 12.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        project.memory.completed.forEach { itemText ->
                            BulletRow(
                                text = itemText
                            )
                        }
                    }
                }

                item {
                    InfoCard(
                        title = strings.remaining
                    ) {
                        Text(
                            text = strings.whatRemains,
                            color = Color.White.copy(alpha = 0.55f),
                            fontSize = 12.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        project.memory.remaining.forEach { itemText ->
                            BulletRow(
                                text = itemText
                            )
                        }
                    }
                }

                item {
                    InfoCard(
                        title = strings.decisions
                    ) {
                        Text(
                            text = strings.keyProjectDecisions,
                            color = Color.White.copy(alpha = 0.55f),
                            fontSize = 12.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        project.memory.decisions.forEach { itemText ->
                            BulletRow(
                                text = itemText
                            )
                        }
                    }
                }

                item {
                    InfoCard(
                        title = strings.errorsConstraints
                    ) {
                        Text(
                            text = strings.problemsConstraints,
                            color = Color.White.copy(alpha = 0.55f),
                            fontSize = 12.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        project.memory.errors.forEach { itemText ->
                            BulletRow(
                                text = itemText
                            )
                        }
                    }
                }

                item {
                    SectionTitle(
                        title = strings.projectState
                    )
                }

                item {
                    InfoCard(
                        title = strings.facts
                    ) {
                        Text(
                            text = strings.objectiveProjectFacts,
                            color = Color.White.copy(alpha = 0.55f),
                            fontSize = 12.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        project.state.facts.forEach { itemText ->
                            BulletRow(
                                text = itemText
                            )
                        }
                    }
                }

                item {
                    InfoCard(
                        title = strings.interpretation
                    ) {
                        Text(
                            text = strings.currentStateInterpretation,
                            color = Color.White.copy(alpha = 0.55f),
                            fontSize = 12.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        project.state.interpretation.forEach { itemText ->
                            BulletRow(
                                text = itemText
                            )
                        }
                    }
                }

                item {
                    InfoCard(
                        title = strings.humanDecisions
                    ) {
                        Text(
                            text = strings.developerDecisions,
                            color = Color.White.copy(alpha = 0.55f),
                            fontSize = 12.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        project.state.humanDecisions.forEach { itemText ->
                            BulletRow(
                                text = itemText
                            )
                        }
                    }
                }

                item {
                    SectionTitle(
                        title = strings.architecture
                    )
                }

                item {
                    InfoCard(
                        title = strings.description
                    ) {
                        Text(
                            text = project.architecture.description,
                            color = Color.White,
                            lineHeight = 22.sp
                        )
                    }
                }

                item {
                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )
                }
            }
        }
    }
}

                item {

                    InfoCard(
                        title = strings.remaining
                    ) {

                        Text(
                            text = strings.whatRemains,
                            color = Color.White.copy(alpha = 0.55f),
                            fontSize = 12.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        project.memory.remaining.forEach { itemText ->

                            BulletRow(
                                text = itemText
                            )
                        }
                    }
                }

                item {

                    InfoCard(
                        title = strings.decisions
                    ) {

                        Text(
                            text = strings.keyProjectDecisions,
                            color = Color.White.copy(alpha = 0.55f),
                            fontSize = 12.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        project.memory.decisions.forEach { itemText ->

                            BulletRow(
                                text = itemText
                            )
                        }
                    }
                }

                item {

                    InfoCard(
                        title = strings.errorsConstraints
                    ) {

                        Text(
                            text = strings.problemsConstraints,
                            color = Color.White.copy(alpha = 0.55f),
                            fontSize = 12.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        project.memory.errors.forEach { itemText ->

                            BulletRow(
                                text = itemText
                            )
                        }
                    }
                }

                item {

                    SectionTitle(
                        title = strings.projectState
                    )
                }

                item {

                    InfoCard(
                        title = strings.facts
                    ) {

                        Text(
                            text = strings.objectiveProjectFacts,
                            color = Color.White.copy(alpha = 0.55f),
                            fontSize = 12.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        project.state.facts.forEach { itemText ->

                            BulletRow(
                                text = itemText
                            )
                        }
                    }
                }

                item {

                    InfoCard(
                        title = strings.interpretation
                    ) {

                        Text(
                            text = strings.currentStateInterpretation,
                            color = Color.White.copy(alpha = 0.55f),
                            fontSize = 12.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        project.state.interpretation.forEach { itemText ->

                            BulletRow(
                                text = itemText
                            )
                        }
                    }

                    item {

                        InfoCard(
                            title = strings.humanDecisions
                        ) {

                            Text(
                                text = strings.developerDecisions,
                                color = Color.White.copy(alpha = 0.55f),
                                fontSize = 12.sp
                            )

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            project.state.humanDecisions.forEach { itemText ->

                                BulletRow(
                                    text = itemText
                                )
                            }
                        }
                    }

                    item {

                        SectionTitle(
                            title = strings.architecture
                        )
                    }

                    item {

                        InfoCard(
                            title = strings.description
                        ) {

                            Text(
                                text = project.architecture.description,
                                color = Color.White,
                                lineHeight = 22.sp
                            )
                        }
                    }

                    item {

                        Spacer(
                            modifier = Modifier.height(24.dp)
                        )
                    }
                }
            }
        }
    }

    /* ============================================================
       BACKGROUND
       ============================================================ */

    @Composable
    fun Background(
        content: @Composable () -> Unit
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.frankboard_bg
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.Black.copy(alpha = 0.48f)
                    )
            )

            content()
        }
    }

    /* ============================================================
       HEADER
       ============================================================ */

    @Composable
    fun Header() {

        val strings = LocalAppStrings.current

        Column {

            Text(
                text = "FRANKBOARD",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = if (strings == AppStrings.forLanguage(AppLanguage.RUSSIAN)) {
                    "ЦЕНТР УПРАВЛЕНИЯ ЛИЧНЫМИ ПРОЕКТАМИ"
                } else {
                    "PERSONAL PROJECT COMMAND CENTER"
                },
                color = Color.White.copy(alpha = 0.65f),
                fontSize = 12.sp
            )
        }
    }

    /* ============================================================
       SUMMARY
       ============================================================ */

    @Composable
    fun SummaryCard() {

        val strings = LocalAppStrings.current

        GlassCard {

            Column {

                Text(
                    text = strings.projectSystem,
                    color = Color.White.copy(alpha = 0.65f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "${ProjectRepository.projects.size} ${strings.activeProjects}",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = strings.centralizedProjectState,
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = 13.sp
                )
            }
        }
    }

    /* ============================================================
       SECTION TITLE
       ============================================================ */

    @Composable
    fun SectionTitle(
        title: String
    ) {

        Text(
            text = title,
            color = Color.White.copy(alpha = 0.75f),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp,
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 2.dp
            )
        )
    }

    /* ============================================================
       PROJECT CARD
       ============================================================ */

    @Composable
    fun ProjectCard(
        project: Project,
        onClick: () -> Unit
    ) {

        val strings = LocalAppStrings.current

        val sourceLabel = when (project.source) {

            ProjectSource.LOCAL ->
                strings.local

            ProjectSource.GITHUB ->
                strings.github

            ProjectSource.AI ->
                strings.ai

            ProjectSource.MIXED ->
                strings.mixed
        }

        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
        ) {

            Column {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = project.name,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(
                                        statusColor(project.state.status)
                                    )
                            )

                            Spacer(
                                modifier = Modifier.width(7.dp)
                            )

                            Text(
                                text = project.state.status,
                                color = statusColor(project.state.status),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text(
                        text = "${project.state.progress}%",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                LinearProgressIndicator(
                    progress = {
                        project.state.progress / 100f
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(7.dp)
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = "${strings.currentTask}: ${project.state.currentTask}",
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = 13.sp
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "${strings.nextTask}: ${project.state.nextTask}",
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = 13.sp
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "${strings.source}: $sourceLabel",
                    color = Color.White.copy(alpha = 0.45f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }

    /* ============================================================
       PROJECT HERO
       ============================================================ */

    @Composable
    fun ProjectHero(
        project: Project
    ) {

        val strings = LocalAppStrings.current

        val sourceLabel = when (project.source) {

            ProjectSource.LOCAL ->
                strings.local

            ProjectSource.GITHUB ->
                strings.github

            ProjectSource.AI ->
                strings.ai

            ProjectSource.MIXED ->
                strings.mixed
        }

        GlassCard {

            Column {

                Text(
                    text = project.name,
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = project.state.status,
                    color = statusColor(project.state.status),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "${strings.source}: $sourceLabel",
                    color = Color.White.copy(alpha = 0.55f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }

    /* ============================================================
       MEMORY
       ============================================================ */

    @Composable
    fun MemoryCard(
        title: String,
        value: String
    ) {

        GlassCard {

            Column {

                Text(
                    text = title,
                    color = Color.White.copy(alpha = 0.65f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = value,
                    color = Color.White,
                    fontSize = 15.sp,
                    lineHeight = 21.sp
                )
            }
        }
    }

    /* ============================================================
       INFO CARD
       ============================================================ */

    @Composable
    fun InfoCard(
        title: String,
        content: @Composable ColumnScope.() -> Unit
    ) {

        val accentColor = when (title) {

            "FACTS" ->
                Color(0xFF7DD3FC)

            "INTERPRETATION" ->
                Color(0xFFC4B5FD)

            "HUMAN DECISIONS" ->
                Color(0xFFFFC857)

            else ->
                Color.White.copy(alpha = 0.72f)
        }

        GlassCard {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(48.dp)
                        .clip(
                            RoundedCornerShape(4.dp)
                        )
                        .background(
                            accentColor.copy(alpha = 0.8f)
                        )
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(9.dp)
                                .clip(CircleShape)
                                .background(accentColor)
                        )

                        Spacer(
                            modifier = Modifier.width(9.dp)
                        )

                        Text(
                            text = title,
                            color = accentColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    content()
                }
            }
        }
    }

    /* ============================================================
       GLASS CARD
       ============================================================ */

    @Composable
    fun GlassCard(
        modifier: Modifier = Modifier,
        content: @Composable ColumnScope.() -> Unit
    ) {

        Surface(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            color = Color.White.copy(alpha = 0.075f),
            border = BorderStroke(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.22f)
            ),
            shadowElevation = 8.dp
        ) {

            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content = content
            )
        }
    }

    /* ============================================================
       BULLET ROW
       ============================================================ */

    @Composable
    fun BulletRow(
        text: String
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp),
            verticalAlignment = Alignment.Top
        ) {

            Text(
                text = "•",
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.width(18.dp)
            )

            Text(
                text = text,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }

    /* ============================================================
       STATUS COLOR
       ============================================================ */

    fun statusColor(
        status: String
    ): Color {

        return when {

            status.contains(
                "ACTIVE",
                ignoreCase = true
            ) -> Color(0xFF55E6B0)

            status.contains(
                "PLANNING",
                ignoreCase = true
            ) -> Color(0xFFFFC857)

            status.contains(
                "PAUSED",
                ignoreCase = true
            ) -> Color(0xFFFF7F8A)

            status.contains(
                "DONE",
                ignoreCase = true
            ) -> Color(0xFF7DD3FC)

            else -> Color.White.copy(alpha = 0.85f)
        }
    }

    /* ============================================================
       PROJECT FOLDER
       ============================================================ */

    @Composable
    fun ProjectFolderScreen(
        onBack: () -> Unit,
        onProjectClick: (Project) -> Unit
    ) {

        val strings = LocalAppStrings.current

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->

            Background {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    item {

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )
                    }

                    item {

                        Button(
                            onClick = onBack,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black.copy(alpha = 0.45f),
                                contentColor = Color.White
                            )
                        ) {
                            Text(strings.back)
                        }
                    }

                    item {

                        Text(
                            text = strings.project,
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    item {

                        Text(
                            text = strings.projects,
                            color = Color.White.copy(alpha = 0.65f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )
                    }

                    items(ProjectRepository.projects) { project ->

                        ProjectCard(
                            project = project,
                            onClick = {
                                onProjectClick(project)
                            }
                        )
                    }

                    item {

                        Spacer(
                            modifier = Modifier.height(24.dp)
                        )
                    }
                }
            }
        }
    }

    /* ============================================================
       NOTEBOOK
       ============================================================ */

    @Composable
    fun NotebookScreen(
        onBack: () -> Unit
    ) {

        val strings = LocalAppStrings.current
        val context = LocalContext.current

        var notes by remember {
            mutableStateOf(
                NoteRepository
                    .getNotes(context)
                    .sortedByDescending { it.updatedAt }
            )
        }

        var editingNote by remember {
            mutableStateOf<Note?>(null)
        }

        var showEditor by remember {
            mutableStateOf(false)
        }

        var tileSizeLevel by remember {
            mutableStateOf(3)
        }

        val tileSizePercent = listOf(
            50,
            65,
            80,
            100,
            120,
            140,
            160
        )

        val currentTilePercent = tileSizePercent[tileSizeLevel]

        if (showEditor) {

            NoteEditorScreen(
                note = editingNote,
                onBack = {
                    showEditor = false
                },
                onSaved = {

                    notes = NoteRepository
                        .getNotes(context)
                        .sortedByDescending { it.updatedAt }

                    showEditor = false
                },
                onDelete = {

                    editingNote?.let { note ->

                        NoteRepository.deleteNote(
                            context = context,
                            noteId = note.id
                        )
                    }

                    notes = NoteRepository
                        .getNotes(context)
                        .sortedByDescending { it.updatedAt }

                    editingNote = null
                    showEditor = false
                }
            )

            return
        }

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->

            Background {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(
                        when (currentTilePercent) {
                            50 -> 5
                            65 -> 4
                            80 -> 3
                            else -> 2
                        }
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        when (currentTilePercent) {
                            50 -> 6.dp
                            else -> 12.dp
                        }
                    ),
                    verticalArrangement = Arrangement.spacedBy(
                        when (currentTilePercent) {
                            50 -> 6.dp
                            else -> 12.dp
                        }
                    )
                ) {

                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )
                    }

                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Button(
                                onClick = onBack,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black.copy(alpha = 0.45f),
                                    contentColor = Color.White
                                )
                            ) {
                                Text(strings.back)
                            }

                            Spacer(
                                modifier = Modifier.weight(1f)
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                SmallScaleButton(
                                    text = "−",
                                    enabled = tileSizeLevel > 0,
                                    onClick = {

                                        if (tileSizeLevel > 0) {
                                            tileSizeLevel--
                                        }
                                    }
                                )

                                Text(
                                    text = "$currentTilePercent%",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(
                                        horizontal = 7.dp
                                    )
                                )

                                SmallScaleButton(
                                    text = "+",
                                    enabled = tileSizeLevel < tileSizePercent.lastIndex,
                                    onClick = {

                                        if (tileSizeLevel < tileSizePercent.lastIndex) {
                                            tileSizeLevel++
                                        }
                                    }
                                )
                            }
                        }
                    }

                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {

                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Text(
                                text = strings.notebook,
                                color = Color.White,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(2.dp)
                            )

                            Text(
                                text = strings.personalNotes,
                                color = Color.White.copy(alpha = 0.65f),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.5.sp
                            )
                        }
                    }

                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {

                        Button(
                            onClick = {

                                editingNote = null
                                showEditor = true
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(strings.newNote)
                        }
                    }

                    if (notes.isEmpty()) {

                        item(
                            span = {
                                GridItemSpan(maxLineSpan)
                            }
                        ) {

                            GlassCard {

                                Column {

                                    Text(
                                        text = "📓",
                                        fontSize = 36.sp
                                    )

                                    Spacer(
                                        modifier = Modifier.height(8.dp)
                                    )

                                    Text(
                                        text = strings.noNotesYet,
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(
                                        modifier = Modifier.height(4.dp)
                                    )

                                    Text(
                                        text = strings.createFirstNote,
                                        color = Color.White.copy(alpha = 0.65f),
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }

                    } else {

                        gridItems(
                            items = notes,
                            key = { note -> note.id }
                        ) { note ->

                            NoteTile(
                                note = note,
                                scalePercent = currentTilePercent,
                                onColorChange = { color ->

                                    NoteRepository.updateNoteColor(
                                        context = context,
                                        noteId = note.id,
                                        color = color
                                    )

                                    notes = NoteRepository
                                        .getNotes(context)
                                        .sortedByDescending { it.updatedAt }
                                },
                                onClick = {

                                    editingNote = note
                                    showEditor = true
                                }
                            )
                        }
                    }

                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ) {

                        Spacer(
                            modifier = Modifier.height(24.dp)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun SmallScaleButton(
        text: String,
        enabled: Boolean,
        onClick: () -> Unit
    ) {

        Surface(
            modifier = Modifier
                .size(30.dp)
                .clickable(
                    enabled = enabled,
                    onClick = onClick
                ),
            shape = RoundedCornerShape(8.dp),
            color = if (enabled) {
                Color.Black.copy(alpha = 0.45f)
            } else {
                Color.Black.copy(alpha = 0.18f)
            },
            border = BorderStroke(
                width = 1.dp,
                color = Color.White.copy(
                    alpha = if (enabled) 0.25f else 0.10f
                )
            )
        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = text,
                    color = Color.White.copy(
                        alpha = if (enabled) 1f else 0.35f
                    ),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    @Composable
    fun NoteTile(
        note: Note,
        scalePercent: Int,
        onColorChange: (String) -> Unit,
        onClick: () -> Unit
    ) {

        val strings = LocalAppStrings.current

        var showColorPicker by remember(note.id) {
            mutableStateOf(false)
        }

        val scale = scalePercent / 100f
        val tileColor = noteColor(note.color)

        val tileHeight = when (scalePercent) {
            50 -> 58.dp
            65 -> 82.dp
            80 -> 100.dp
            100 -> 125.dp
            120 -> 150.dp
            140 -> 175.dp
            else -> 200.dp
        }

        val tileWidth = when (scalePercent) {
            50 -> 58.dp
            else -> null
        }

        val tilePadding = when (scalePercent) {
            50 -> 4.dp
            65 -> 7.dp
            80 -> 9.dp
            100 -> 11.dp
            120 -> 13.dp
            140 -> 15.dp
            else -> 17.dp
        }

        val titleSize = when (scalePercent) {
            50 -> 7.sp
            65 -> 12.sp
            80 -> 13.sp
            100 -> 15.sp
            120 -> 17.sp
            140 -> 19.sp
            else -> 21.sp
        }

        val textSize = when (scalePercent) {
            50 -> 6.sp
            65 -> 10.sp
            80 -> 11.sp
            100 -> 12.sp
            120 -> 14.sp
            140 -> 15.sp
            else -> 17.sp
        }

        val dateSize = when (scalePercent) {
            50 -> 5.sp
            65 -> 8.sp
            80 -> 9.sp
            100 -> 9.sp
            120 -> 10.sp
            140 -> 11.sp
            else -> 12.sp
        }

        val textLineHeight = when (scalePercent) {
            50 -> 7.sp
            65 -> 13.sp
            80 -> 14.sp
            100 -> 16.sp
            120 -> 18.sp
            140 -> 20.sp
            else -> 22.sp
        }

        val textLines = when {
            scalePercent <= 50 -> 1
            scalePercent <= 65 -> 2
            scalePercent <= 100 -> 3
            scalePercent <= 120 -> 4
            scalePercent <= 140 -> 5
            else -> 6
        }

        Box(
            modifier = Modifier
                .then(
                    if (tileWidth != null) {
                        Modifier.width(tileWidth)
                    } else {
                        Modifier.fillMaxWidth()
                    }
                )
                .height(tileHeight)
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onClick()
                    },
                shape = RoundedCornerShape(
                    when {
                        scale <= 0.65f -> 8.dp
                        scale <= 1f -> 16.dp
                        else -> 20.dp
                    }
                ),
                color = tileColor,
                shadowElevation = when {
                    scale <= 0.65f -> 2.dp
                    scale <= 1f -> 5.dp
                    else -> 7.dp
                }
            ) {

                Column(
                    modifier = Modifier.padding(tilePadding)
                ) {

                    Text(
                        text = if (note.title.isBlank()) {
                            strings.noTitle
                        } else {
                            note.title
                        },
                        color = if (note.color == "yellow") {
                            Color(0xFF3F3528)
                        } else {
                            Color.White
                        },
                        fontSize = titleSize,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(
                        modifier = Modifier.height(
                            when {
                                scalePercent <= 65 -> 1.dp
                                scalePercent <= 100 -> 5.dp
                                else -> 7.dp
                            }
                        )
                    )

                    Text(
                        text = if (note.text.isBlank()) {
                            strings.emptyEntry
                        } else {
                            note.text
                        },
                        color = if (note.color == "yellow") {
                            Color(0xFF5C4A32)
                        } else {
                            Color.White.copy(alpha = 0.88f)
                        },
                        fontSize = textSize,
                        lineHeight = textLineHeight,
                        maxLines = textLines,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(
                        modifier = Modifier.height(
                            when {
                                scalePercent <= 65 -> 1.dp
                                scalePercent <= 100 -> 5.dp
                                else -> 7.dp
                            }
                        )
                    )

                    Text(
                        text = formatNoteDate(note.updatedAt),
                        color = if (note.color == "yellow") {
                            Color(0xFF8A765B)
                        } else {
                            Color.White.copy(alpha = 0.65f)
                        },
                        fontSize = dateSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = 4.dp,
                        end = 4.dp
                    )
            ) {

                Surface(
                    modifier = Modifier
                        .size(
                            when {
                                scalePercent <= 50 -> 13.dp
                                scalePercent <= 80 -> 16.dp
                                else -> 20.dp
                            }
                        )
                        .clickable {
                            showColorPicker = true
                        },
                    shape = RoundedCornerShape(4.dp),
                    color = Color.White.copy(alpha = 0.9f),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color.Black.copy(alpha = 0.35f)
                    )
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(2.dp)
                            .clip(
                                RoundedCornerShape(2.dp)
                            )
                            .background(tileColor)
                    )
                }

                DropdownMenu(
                    expanded = showColorPicker,
                    onDismissRequest = {
                        showColorPicker = false
                    }
                ) {

                    DropdownMenuItem(
                        text = {
                            Text(strings.black)
                        },
                        onClick = {
                            onColorChange("black")
                            showColorPicker = false
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text(strings.red)
                        },
                        onClick = {
                            onColorChange("red")
                            showColorPicker = false
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text(strings.blue)
                        },
                        onClick = {
                            onColorChange("blue")
                            showColorPicker = false
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text(strings.green)
                        },
                        onClick = {
                            onColorChange("green")
                            showColorPicker = false
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text(strings.yellow)
                        },
                        onClick = {
                            onColorChange("yellow")
                            showColorPicker = false
                        }
                    )
                }
            }
        }
    }

    fun noteColor(
        color: String
    ): Color {

        return when (color) {

            "black" ->
                Color(0xFF202020)

            "red" ->
                Color(0xFFD94A4A)

            "blue" ->
                Color(0xFF4A78D9)

            "green" ->
                Color(0xFF4F9D69)

            else ->
                Color(0xFFF4D35E)
        }
    }

    /* ============================================================
       NOTE EDITOR
       ============================================================ */

    @Composable
    fun NoteEditorScreen(
        note: Note?,
        onBack: () -> Unit,
        onSaved: (Note) -> Unit,
        onDelete: () -> Unit
    ) {

        val strings = LocalAppStrings.current
        val context = LocalContext.current

        var title by remember(note?.id) {
            mutableStateOf(note?.title ?: "")
        }

        var text by remember(note?.id) {
            mutableStateOf(note?.text ?: "")
        }

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->

            Background {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    item {

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )
                    }

                    item {

                        Button(
                            onClick = onBack,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black.copy(alpha = 0.45f),
                                contentColor = Color.White
                            )
                        ) {
                            Text(strings.back)
                        }
                    }

                    item {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = if (note == null) {
                                    strings.newEntry
                                } else {
                                    strings.editing
                                },
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )

                            if (note != null) {

                                TextButton(
                                    onClick = onDelete
                                ) {

                                    Text(
                                        text = strings.delete,
                                        color = Color(0xFFFF7F8A)
                                    )
                                }
                            }
                        }
                    }

                    item {

                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFF4E7C5)
                        ) {

                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {

                                Text(
                                    text = strings.myNote,
                                    color = Color(0xFF5C4A32),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )

                                OutlinedTextField(
                                    value = title,
                                    onValueChange = {
                                        title = it
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    label = {
                                        Text(strings.title)
                                    }
                                )

                                OutlinedTextField(
                                    value = text,
                                    onValueChange = {
                                        text = it
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp),
                                    label = {
                                        Text(strings.text)
                                    }
                                )
                            }
                        }
                    }

                    item {

                        Button(
                            onClick = {

                                val now = System.currentTimeMillis()

                                val savedNote = Note(
                                    id = note?.id ?: now,
                                    title = title.trim().ifBlank {
                                        strings.noTitle
                                    },
                                    text = text,
                                    createdAt = note?.createdAt ?: now,
                                    updatedAt = now,
                                    color = note?.color ?: "yellow"
                                )

                                NoteRepository.saveNote(
                                    context = context,
                                    note = savedNote
                                )

                                onSaved(savedNote)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Text(strings.save)
                        }
                    }

                    item {

                        Spacer(
                            modifier = Modifier.height(24.dp)
                        )
                    }
                }
            }
        }
    }

    private fun formatNoteDate(
        timestamp: Long
    ): String {

        return SimpleDateFormat(
            "dd.MM.yyyy HH:mm",
            Locale.getDefault()
        ).format(
            Date(timestamp)
        )
    }