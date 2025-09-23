package com.prince.studentconnect.ui.endpoints.student.ui.chat

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.prince.studentconnect.di.ServiceLocator
import com.prince.studentconnect.ui.components.chat.ChatTopBar
import com.prince.studentconnect.ui.components.chat.MessageInputBar
import com.prince.studentconnect.ui.components.chat.MessagesList
import com.prince.studentconnect.ui.endpoints.student.model.chat.MemberUiModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.chat.MessageViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(
    navController: NavController,
    conversationId: Int,
    userId: String,
    members: List<MemberUiModel>,
    conversationName: String
) {
    Log.d("ChatScreen", "Received conversationId: $conversationId")

    val viewModel: MessageViewModel = viewModel(
        factory = ServiceLocator.provideMessageViewModelFactory(
            userId = userId,
            conversationId = conversationId,
            members = members,
            conversationName = conversationName
        )
    )

    val imePadding = WindowInsets.ime.asPaddingValues()
    val density = LocalDensity.current

    val messages by viewModel.messages.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    val listState = rememberLazyListState()
    var messageText by remember { mutableStateOf("") }

    // Auto-scroll to bottom when messages change
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    // Auto-scroll again when keyboard visibility changes
    LaunchedEffect(messages.size, imePadding.calculateBottomPadding()) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            ChatTopBar(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            ) },

        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
                    .navigationBarsPadding()
                    .imePadding()
            ) {
                MessageInputBar(
                    text = messageText,
                    onTextChange = { messageText = it },
                    onSend = {
                        if (messageText.isNotBlank()) {
                            viewModel.sendMessage(messageText)
                            messageText = ""
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            when {
                loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                !error.isNullOrEmpty() -> Text(error!!, modifier = Modifier.align(Alignment.Center))
                else -> MessagesList(messages = messages, listState = listState, currentUserId = viewModel.userId, viewModel = viewModel)
            }
        }
    }
}
