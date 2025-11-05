package com.prince.studentconnect.ui.lang

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prince.studentconnect.R
import com.prince.studentconnect.util.LocaleManager
import com.prince.studentconnect.util.Prefs

@Composable
fun LanguageSelector(onLanguageChanged: () -> Unit) {
    val context = LocalContext.current
    var selectedLang by remember { mutableStateOf(LocaleManager.getLanguage(context)) }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(text = stringResource(id = R.string.select_language), style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(12.dp))

        val languages = listOf(
            "en" to stringResource(R.string.lang_english),
            "af" to stringResource(R.string.lang_afrikaans),
            "zu" to stringResource(R.string.lang_zulu)
        )

        languages.forEach { (code, label) ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                RadioButton(selected = selectedLang == code, onClick = { selectedLang = code })
                Spacer(Modifier.width(8.dp))
                Text(text = label)
            }
        }

        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            Prefs.saveLanguage(context, selectedLang)
            onLanguageChanged()
        }) {
            Text(stringResource(R.string.save))
        }
    }
}
