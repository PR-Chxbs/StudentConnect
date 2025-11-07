package com.prince.studentconnect.ui.lang

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prince.studentconnect.R
import com.prince.studentconnect.data.preferences.UserPreferencesRepository
import com.prince.studentconnect.util.LocaleManager
import com.prince.studentconnect.util.Prefs
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun LanguageSelector(
    onLanguageChanged: (String) -> Unit,
    repo: UserPreferencesRepository = UserPreferencesRepository(LocalContext.current)
    ) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val currentLocaleLang = remember { LocaleManager.getLanguageBlocking(context) }

    val savedLang by repo.languageFlow.collectAsState(initial = currentLocaleLang)

    // Local state for currently selected radio button
    var tempSelectedLang by remember { mutableStateOf(savedLang) }


    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(text = stringResource(id = R.string.select_language), style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(12.dp))

        val languages = listOf(
            "en" to stringResource(R.string.lang_english),
            "af" to stringResource(R.string.lang_afrikaans),
            "zu" to stringResource(R.string.lang_zulu)
        )

        languages.forEach { (code, label) ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                RadioButton(selected = tempSelectedLang == code, onClick = { tempSelectedLang = code })
                Spacer(Modifier.width(8.dp))
                Text(text = label)
            }
        }

        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                repo.saveLanguage(tempSelectedLang)
                onLanguageChanged(tempSelectedLang)
            }
        }) {
            Text(stringResource(R.string.save))
        }
    }
}
