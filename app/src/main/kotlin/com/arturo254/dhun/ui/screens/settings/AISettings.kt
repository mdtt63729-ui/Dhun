/*
 * Dhun Project Original (2026)
 * Dhun
 * Licensed Under GPL-3.0 | see git history for contributors
 */

package com.arturo254.dhun.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.arturo254.dhun.LocalPlayerAwareWindowInsets
import com.arturo254.dhun.R
import com.arturo254.dhun.constants.AIProvider
import com.arturo254.dhun.constants.AIProviderKey
import com.arturo254.dhun.constants.AIApiKeyKey
import com.arturo254.dhun.constants.UseAITranslationKey
import com.arturo254.dhun.constants.CustomModelIdKey
import com.arturo254.dhun.constants.CustomOpenAIBaseUrlKey
import com.arturo254.dhun.constants.CustomOpenAIHeadersKey
import com.arturo254.dhun.constants.AITranslationLanguageKey
import com.arturo254.dhun.constants.DEFAULT_CUSTOM_OPENAI_BASE_URL
import com.arturo254.dhun.ui.component.IconButton
import com.arturo254.dhun.ui.component.TextFieldDialog
import com.arturo254.dhun.ui.utils.backToMain
import com.arturo254.dhun.utils.dataStore
import com.arturo254.dhun.utils.rememberPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AISettings(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val context = LocalContext.current
    val scope = androidx.compose.runtime.rememberCoroutineScope()

    // AI settings from DataStore
    val (aiProvider, onAIProviderChange) = rememberPreference(AIProviderKey, AIProvider.OPENAI)
    val (aiApiKey, onAIApiKeyChange) = rememberPreference(AIApiKeyKey, "")
    val (useAITranslation, onUseAITranslationChange) = rememberPreference(UseAITranslationKey, false)
    val (customModelId, onCustomModelIdChange) = rememberPreference(CustomModelIdKey, "")
    val (customBaseUrl, onCustomBaseUrlChange) = rememberPreference(CustomOpenAIBaseUrlKey, "")
    val (customHeaders, onCustomHeadersChange) = rememberPreference(CustomOpenAIHeadersKey, "")
    val (translationLanguage, onTranslationLanguageChange) = rememberPreference(AITranslationLanguageKey, "en")

    // Dialog states
    var showProviderDialog by remember { mutableStateOf(false) }
    var showApiKeyDialog by remember { mutableStateOf(false) }
    var showModelIdDialog by remember { mutableStateOf(false) }
    var showBaseUrlDialog by remember { mutableStateOf(false) }
    var showHeadersDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    val isHasApiKey = aiApiKey.isNotEmpty()
    // If API key is removed, disable AI translation
    LaunchedEffect(isHasApiKey) {
        if (!isHasApiKey && useAITranslation) {
            onUseAITranslationChange(false)
        }
    }

    val providers = listOf(
        AIProvider.OPENAI to "OpenAI",
        AIProvider.GEMINI to "Gemini",
        AIProvider.CUSTOM_OPENAI to "OpenAI-compatible (Custom)",
    )
    val providerName = providers.firstOrNull { it.first == aiProvider }?.second ?: aiProvider

    Column(
        modifier = Modifier
            .windowInsetsPadding(LocalPlayerAwareWindowInsets.current)
            .verticalScroll(rememberScrollState()),
    ) {
        // ── Use AI Translation toggle ──────────────────────────────────────
        SwitchItem(
            title = "Use AI Translation",
            description = "Translate lyrics using AI as a fallback after other sources",
            checked = useAITranslation,
            enabled = isHasApiKey,
            onCheckedChange = onUseAITranslationChange,
        )

        // ── AI Provider ────────────────────────────────────────────────────
        SettingsEntry(
            title = "AI Provider",
            subtitle = providerName,
            onClick = { showProviderDialog = true },
        )

        // ── API Key ───────────────────────────────────────────────────────
        SettingsEntry(
            title = "API Key",
            subtitle = if (isHasApiKey) "XXXXXXXXXX" else "Not set",
            onClick = { showApiKeyDialog = true },
        )

        // ── Custom Model ID ────────────────────────────────────────────────
        SettingsEntry(
            title = "Custom Model ID",
            subtitle = customModelId.ifEmpty { "Default" },
            onClick = { showModelIdDialog = true },
        )

        // ── Custom OpenAI settings (only show when Custom OpenAI selected) ─
        if (aiProvider == AIProvider.CUSTOM_OPENAI) {
            SettingsEntry(
                title = "Custom Base URL",
                subtitle = customBaseUrl.ifEmpty { DEFAULT_CUSTOM_OPENAI_BASE_URL },
                onClick = { showBaseUrlDialog = true },
            )
            SettingsEntry(
                title = "Custom Headers (JSON)",
                subtitle = customHeaders.ifEmpty { "None" },
                onClick = { showHeadersDialog = true },
            )
        }

        // ── Translation Language ──────────────────────────────────────────
        SettingsEntry(
            title = "Translation Language",
            subtitle = translationLanguage,
            onClick = { showLanguageDialog = true },
        )
    }

    // ── Dialogs ────────────────────────────────────────────────────────────

    if (showProviderDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showProviderDialog = false },
            title = { Text("AI Provider") },
            confirmButton = {
                androidx.compose.material3.TextButton(onClick = { showProviderDialog = false }) {
                    Text("Close")
                }
            },
            text = {
                Column {
                    providers.forEach { (id, name) ->
                        SettingsEntry(
                            title = name,
                            subtitle = if (id == aiProvider) "Selected" else null,
                            onClick = {
                                onAIProviderChange(id)
                                showProviderDialog = false
                            },
                        )
                    }
                }
            },
        )
    }

    if (showApiKeyDialog) {
        TextFieldDialog(
            title = { Text("API Key") },
            initialTextFieldValue = TextFieldValue(aiApiKey),
            placeholder = { Text("Enter your API key") },
            onDone = {
                onAIApiKeyChange(it)
                showApiKeyDialog = false
            },
            onDismiss = { showApiKeyDialog = false },
        )
    }

    if (showModelIdDialog) {
        TextFieldDialog(
            title = { Text("Custom Model ID") },
            initialTextFieldValue = TextFieldValue(customModelId),
            placeholder = { Text("e.g. gpt-4o, gemini-1.5-flash") },
            onDone = {
                onCustomModelIdChange(it)
                showModelIdDialog = false
            },
            onDismiss = { showModelIdDialog = false },
        )
    }

    if (showBaseUrlDialog) {
        TextFieldDialog(
            title = { Text("Custom Base URL") },
            initialTextFieldValue = TextFieldValue(customBaseUrl),
            placeholder = { Text(DEFAULT_CUSTOM_OPENAI_BASE_URL) },
            onDone = {
                onCustomBaseUrlChange(it)
                showBaseUrlDialog = false
            },
            onDismiss = { showBaseUrlDialog = false },
        )
    }

    if (showHeadersDialog) {
        TextFieldDialog(
            title = { Text("Custom Headers (JSON)") },
            initialTextFieldValue = TextFieldValue(customHeaders),
            placeholder = { Text("""{"Authorization":"Bearer xxx"}""") },
            onDone = {
                onCustomHeadersChange(it)
                showHeadersDialog = false
            },
            onDismiss = { showHeadersDialog = false },
        )
    }

    if (showLanguageDialog) {
        TextFieldDialog(
            title = { Text("Translation Language") },
            initialTextFieldValue = TextFieldValue(translationLanguage),
            placeholder = { Text("e.g. bn, hi, es, en") },
            onDone = {
                onTranslationLanguageChange(it.lowercase().trim())
                showLanguageDialog = false
            },
            onDismiss = { showLanguageDialog = false },
        )
    }
}

/**
 * Simple switch item for settings.
 */
@Composable
private fun SwitchItem(
    title: String,
    description: String,
    checked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
) {
    androidx.compose.material3.ListItem(
        headlineContent = { Text(title) },
        supportingContent = { Text(description, style = MaterialTheme.typography.bodySmall) },
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled,
            )
        },
    )
}

@Composable
private fun SettingsEntry(
    title: String,
    subtitle: String?,
    onClick: () -> Unit,
) {
    androidx.compose.material3.ListItem(
        headlineContent = { Text(title) },
        supportingContent = subtitle?.let { s -> { Text(s, style = MaterialTheme.typography.bodySmall) } },
        modifier = Modifier.androidxClickable(onClick),
    )
}

// Helper to make ListItem clickable
@Composable
private fun Modifier.androidxClickable(onClick: () -> Unit): Modifier =
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple(),
        onClick = onClick,
    )
