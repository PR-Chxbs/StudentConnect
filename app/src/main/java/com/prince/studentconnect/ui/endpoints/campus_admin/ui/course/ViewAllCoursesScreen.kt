package com.prince.studentconnect.ui.endpoints.campus_admin.ui.course

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.prince.studentconnect.data.remote.dto.course.GetCoursesResponse
import com.prince.studentconnect.ui.components.course.CourseCard
import com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.course.CourseUiState
import com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.course.ViewAllCoursesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewAllCoursesScreen(
    viewModel: ViewAllCoursesViewModel,
    onAddCourseClick: () -> Unit,
    onEditCourseClick: (Int) -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isDeleting by viewModel.isDeleting.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadCourses()
    }

    Scaffold(
        bottomBar = bottomBar,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddCourseClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("+")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
        ) {
            when (uiState) {
                is CourseUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is CourseUiState.Error -> {
                    val message = (uiState as CourseUiState.Error).message
                    Text(
                        text = message,
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is CourseUiState.Success -> {
                    val courses = (uiState as CourseUiState.Success).courses
                    if (courses.isEmpty()) {
                        Text(
                            text = "No courses available.",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        CoursesList(
                            courses = courses,
                            isDeleting = isDeleting,
                            onEditClick = { id -> onEditCourseClick(id) },
                            onDeleteClick = { id ->
                                viewModel.deleteCourse(
                                    courseId = id,
                                    onSuccess = {
                                        Toast.makeText(
                                            context,
                                            "Course deleted successfully.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    },
                                    onError = { msg ->
                                        Toast.makeText(
                                            context,
                                            msg,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CoursesList(
    courses: List<GetCoursesResponse>,
    isDeleting: Boolean,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(courses) { course ->
                CourseCard(
                    course = course,
                    isSelected = false,
                    isAdminMode = true,
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick,
                    onSelect = {}
                )
            }
        }

        if (isDeleting) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
