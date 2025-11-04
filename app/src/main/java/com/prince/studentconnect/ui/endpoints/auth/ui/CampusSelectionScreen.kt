package com.prince.studentconnect.ui.endpoints.auth.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.onboarding.OnboardingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampusSelectionScreen(
    onboardingViewModel: OnboardingViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
}