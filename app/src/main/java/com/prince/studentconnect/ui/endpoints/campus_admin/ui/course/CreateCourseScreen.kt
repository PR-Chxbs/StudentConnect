package com.prince.studentconnect.ui.endpoints.campus_admin.ui.course

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.course.CreateCourseViewModel
import com.prince.studentconnect.R

@Composable
fun CreateCourseScreen(
    viewModel: CreateCourseViewModel,
    onNextClick: () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        bottomBar = bottomBar
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.create_course),
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = viewModel.courseName.collectAsState().value,
                onValueChange = { viewModel.courseName.value = it },
                label = { Text("Course Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.courseDescription.collectAsState().value,
                onValueChange = { viewModel.courseDescription.value = it },
                label = { Text(stringResource(R.string.course_description)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.durationYears.collectAsState().value,
                onValueChange = { viewModel.durationYears.value = it },
                label = { Text(stringResource(R.string.duration)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    focusManager.clearFocus()
                    onNextClick()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.next))
            }
        }
    }
}
