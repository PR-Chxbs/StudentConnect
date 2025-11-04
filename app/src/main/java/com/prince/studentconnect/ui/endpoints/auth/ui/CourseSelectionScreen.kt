package com.prince.studentconnect.ui.endpoints.auth.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.prince.studentconnect.data.remote.dto.course.GetCoursesResponse
import com.prince.studentconnect.data.remote.dto.course.Campus
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.onboarding.OnboardingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseSelectionScreen(
    onboardingViewModel: OnboardingViewModel,
    onDone: () -> Unit,
    onBack: () -> Unit
) {
    val selectedCampus by onboardingViewModel.selectedCampus.collectAsState()
    val courses by onboardingViewModel.courses.collectAsState()
    val selectedCourse by onboardingViewModel.selectedCourse.collectAsState()
    val isLoadingCourses by onboardingViewModel.isLoadingCourses.collectAsState()
    val isSubmitting by onboardingViewModel.isSubmitting.collectAsState()
    val submissionSuccess by onboardingViewModel.submissionSuccess.collectAsState()

    // Fetch courses once when screen is entered
    LaunchedEffect(selectedCampus) {
        selectedCampus?.let {
            onboardingViewModel.fetchCourses(it.campus_id)
        }
    }

    // Observe submission result
    LaunchedEffect(submissionSuccess) {
        if (submissionSuccess == true) {
            onboardingViewModel.resetSubmissionState()
            onDone()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Step 3 of 3: Select Course",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                        LinearProgressIndicator(
                            progress = 3f / 3f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = { onboardingViewModel.submitUser() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = selectedCourse != null && !isSubmitting
            ) {
                if (isSubmitting) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Submitting...")
                } else {
                    Text("Done")
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoadingCourses -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                courses.isEmpty() -> {
                    Text(
                        text = "No courses found for this campus.",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(courses) { course ->
                            CourseCard(
                                course = course,
                                isSelected = selectedCourse?.course_id == course.course_id,
                                onSelect = { onboardingViewModel.selectCourse(course) }
                            )
                        }
                    }
                }
            }

            if (submissionSuccess == false) {
                Snackbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Text("Failed to submit user. Please try again.")
                }
            }
        }
    }
}

@Composable
fun CourseCard(
    course: GetCoursesResponse,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val borderColor =
        if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.3f)
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(2.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = course.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Text(
                text = course.description,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )

            Text(
                text = "Duration: ${course.duration_years} years",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCourseCard() {
    val sample = GetCoursesResponse(
        course_id = 1,
        name = "Bachelor of Science in Computer Science",
        description = "A 3-year program focusing on software development and algorithms.",
        duration_years = 3,
        campus = Campus(campus_id = 1, name = "Main Campus")
    )
    CourseCard(course = sample, isSelected = false, onSelect = {})
}