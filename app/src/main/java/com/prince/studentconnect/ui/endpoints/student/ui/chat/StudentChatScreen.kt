package com.prince.studentconnect.ui.endpoints.student.ui.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prince.studentconnect.ui.components.shared.SearchBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.FontWeight
import com.prince.studentconnect.ui.components.chat.ConversationItem
import com.prince.studentconnect.ui.endpoints.student.viewmodel.ConversationType
import com.prince.studentconnect.ui.endpoints.student.viewmodel.ConversationUiModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.ConversationViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentChatScreen(
    navController: NavController,
    conversationViewModel: ConversationViewModel,
    bottomBar: @Composable () -> Unit
) {
    val tabs = listOf("Students", "Lecturers", "Groups")
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    val conversations by conversationViewModel.conversations.collectAsState()
    val loading by conversationViewModel.loading.collectAsState()
    val error by conversationViewModel.error.collectAsState()

    // Load conversations once
    LaunchedEffect(Unit) {
        conversationViewModel.loadConversations()
        conversationViewModel.simulateMessageEmits()
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    // you used 32.dp vertically before; can reduce to 16.dp if it feels too spaced.
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 32.dp,     // keep top padding as is
                        bottom = 0.dp )
            ) {
                // Determine placeholder based on current page
                val placeholder = when (pagerState.currentPage) {
                    0 -> "Search students..."
                    1 -> "Search lecturers..."
                    2 -> "Search groups..."
                    else -> "Search..."
                }

                SearchBar(
                    placeholder = placeholder
                )
            }
        },
        bottomBar = bottomBar
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Tab row (simple, flat, no indicator)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = pagerState.currentPage == index
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        ),
                        color = if (isSelected)
                            MaterialTheme.colorScheme.onSurface
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        modifier = Modifier
                            .clickable {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                            .padding(vertical = 8.dp)
                    )
                }
            }

            // Pager: swipeable content for each tab
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> ConversationListScreen(
                        conversations = conversations.filter { it.type == ConversationType.PRIVATE_STUDENT },
                        loading = loading,
                        error = error
                    )
                    1 -> ConversationListScreen(
                        conversations = conversations.filter { it.type == ConversationType.PRIVATE_LECTURER },
                        loading = loading,
                        error = error
                    )
                    2 -> ConversationListScreen(
                        conversations = conversations.filter {
                            it.type == ConversationType.GROUP || it.type == ConversationType.MODULE_DEFAULT
                        },
                        loading = loading,
                        error = error
                    )
                }
            }
        }
    }
}

@Composable
fun ConversationListScreen(
    conversations: List<ConversationUiModel>,
    loading: Boolean,
    error: String?
) {
    if (loading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (!error.isNullOrEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(error!!)
        }
        return
    }

    LazyColumn (
        modifier = Modifier.fillMaxSize()
    ) {
        items(conversations) { conversation ->
            ConversationItem(conversation) {
                // TODO: Navigate to chat detail screen
            }
        }
    }
}

