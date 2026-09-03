/*
 * Dhun Project Original (2026)
 * Dhun
 * Licensed Under GPL-3.0 | see git history for contributors
 */

package com.arturo254.dhun.ui.screens.settings

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.arturo254.dhun.innertube.YouTube
import com.arturo254.dhun.LocalPlayerAwareWindowInsets
import com.arturo254.dhun.R
import com.arturo254.dhun.constants.*
import com.arturo254.dhun.ui.component.*
import com.arturo254.dhun.ui.utils.backToMain
import com.arturo254.dhun.utils.rememberEnumPreference
import com.arturo254.dhun.utils.rememberPreference
import com.arturo254.dhun.utils.setAppLocale
import java.net.Proxy
import java.util.Locale
import androidx.core.net.toUri

private fun getLanguageDisplayName(languageCode: String): String {
    return when (languageCode) {
        SYSTEM_DEFAULT -> "System Default"
        else -> LanguageCodeToName[languageCode] ?: languageCode
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentSettings(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val context = LocalContext.current

    val (appLanguage, onAppLanguageChange) = rememberPreference(
        key = AppLanguageKey,
        defaultValue = SYSTEM_DEFAULT
    )

    val (contentLanguage, onContentLanguageChange) = rememberPreference(
        key = ContentLanguageKey,
        defaultValue = "system"
    )
    val (contentCountry, onContentCountryChange) = rememberPreference(
        key = ContentCountryKey,
        defaultValue = "system"
    )
    val (hideExplicit, onHideExplicitChange) = rememberPreference(
        key = HideExplicitKey,
        defaultValue = false
    )
    val (hideVideo, onHideVideoChange) = rememberPreference(
        key = HideVideoKey,
        defaultValue = false
    )
    val (proxyEnabled, onProxyEnabledChange) = rememberPreference(
        key = ProxyEnabledKey,
        defaultValue = false
    )
    val (proxyType, onProxyTypeChange) = rememberEnumPreference(
        key = ProxyTypeKey,
        defaultValue = Proxy.Type.HTTP
    )
    val (proxyUrl, onProxyUrlChange) = rememberPreference(
        key = ProxyUrlKey,
        defaultValue = "host:port"
    )
    val (streamBypassProxy, onStreamBypassProxyChange) = rememberPreference(
        key = StreamBypassProxyKey,
        defaultValue = false
    )
    val (enableKugou, onEnableKugouChange) = rememberPreference(
        key = EnableKugouKey,
        defaultValue = true
    )
    val (enableLrclib, onEnableLrclibChange) = rememberPreference(
        key = EnableLrcLibKey,
        defaultValue = true
    )
    val (enableBetterLyrics, onEnableBetterLyricsChange) = rememberPreference(
        key = EnableBetterLyricsKey,
        defaultValue = true
    )
    val (enableSimpMusicLyrics, onEnableSimpMusicLyricsChange) =
        rememberPreference(
            key = EnableSimpMusicLyricsKey,
            defaultValue = true
        )
    val (preferredProvider, onPreferredProviderChange) =
        rememberEnumPreference(
            key = PreferredLyricsProviderKey,
            defaultValue = PreferredLyricsProvider.LRCLIB,
        )
    val (lyricsRomanizeJapanese, onLyricsRomanizeJapaneseChange) = rememberPreference(
        LyricsRomanizeJapaneseKey,
        defaultValue = true
    )
    val (lyricsRomanizeKorean, onLyricsRomanizeKoreanChange) = rememberPreference(
        LyricsRomanizeKoreanKey,
        defaultValue = true
    )
    val (preloadQueueLyricsEnabled, onPreloadQueueLyricsEnabledChange) = rememberPreference(
        PreloadQueueLyricsEnabledKey,
        defaultValue = true
    )
    val (queueLyricsPreloadCount, onQueueLyricsPreloadCountChange) = rememberPreference(
        QueueLyricsPreloadCountKey,
        defaultValue = 1
    )
    val (lengthTop, onLengthTopChange) = rememberPreference(
        key = TopSize,
        defaultValue = "50"
    )
    val (quickPicks, onQuickPicksChange) = rememberEnumPreference(
        key = QuickPicksKey,
        defaultValue = QuickPicks.QUICK_PICKS
    )
    val (jossRedEnabled, onJossRedEnabledChange) = rememberPreference(
        key = JossRedMultimediaKey,
        defaultValue = true
    )

    // SimpMusic-ported lyrics/content settings
    val (enableSpotifyLyrics, onEnableSpotifyLyricsChange) = rememberPreference(
        key = EnableSpotifyLyricsKey,
        defaultValue = false
    )
    val (helpBuildLyricsDatabase, onHelpBuildLyricsDatabaseChange) = rememberPreference(
        key = HelpBuildLyricsDatabaseKey,
        defaultValue = false
    )
    val (contributorName, onContributorNameChange) = rememberPreference(
        key = ContributorNameKey,
        defaultValue = ""
    )
    val (contributorEmail, onContributorEmailChange) = rememberPreference(
        key = ContributorEmailKey,
        defaultValue = ""
    )
    val (youtubeSubtitleLanguage, onYouTubeSubtitleLanguageChange) = rememberPreference(
        key = YouTubeSubtitleLanguageKey,
        defaultValue = ""
    )
    val (enableSponsorBlock, onEnableSponsorBlockChange) = rememberPreference(
        key = EnableSponsorBlockKey,
        defaultValue = false
    )
    val (localTrackingEnabled, onLocalTrackingEnabledChange) = rememberPreference(
        key = LocalTrackingEnabledKey,
        defaultValue = true
    )
    val (lyricsRomanization, onLyricsRomanizationChange) = rememberPreference(
        key = LyricsRomanizationKey,
        defaultValue = false
    )

    var showLanguageSelector by remember { mutableStateOf(false) }
    var showContributorNameDialog by remember { mutableStateOf(false) }
    var showContributorEmailDialog by remember { mutableStateOf(false) }
    var showYouTubeSubtitleLanguageDialog by remember { mutableStateOf(false) }

    val languageOptions = remember {
        LanguageCodeToName.map { (code, name) ->
            LanguageOption(code = code, displayName = name)
        }
    }

    var showProviderOrderDialog by remember { mutableStateOf(false) }

    val (providerOrder, onProviderOrderChange) = rememberPreference(
        key = ProviderOrderKey,
        defaultValue = DefaultProviderOrder.joinToString(",") { it.name },
    )

    // BlackHole-ported download settings
    val (downloadQuality, onDownloadQualityChange) = rememberPreference(DownloadQualityKey, defaultValue = "320 kbps")
    val (ytDownloadQuality, onYtDownloadQualityChange) = rememberPreference(VideoDownloadQualityKey, defaultValue = "High")
    val (downloadFilename, onDownloadFilenameChange) = rememberPreference(DownloadFilenameKey, defaultValue = 0)
    val (createAlbumFolder, onCreateAlbumFolderChange) = rememberPreference(CreateAlbumFolderKey, defaultValue = false)
    val (createYoutubeFolder, onCreateYoutubeFolderChange) = rememberPreference(CreateYoutubeFolderKey, defaultValue = false)
    val (downloadLyrics, onDownloadLyricsChange) = rememberPreference(DownloadLyricsKey, defaultValue = false)

    // BlackHole-ported other settings
    val (liveSearch, onLiveSearchChange) = rememberPreference(LiveSearchKey, defaultValue = true)
    val (useDown, onUseDownChange) = rememberPreference(UseDownKey, defaultValue = true)
    val (getLyricsOnline, onGetLyricsOnlineChange) = rememberPreference(GetLyricsOnlineKey, defaultValue = true)
    val (supportEq, onSupportEqChange) = rememberPreference(SupportEqKey, defaultValue = false)

    // Additional BlackHole + new settings
    val (musicLanguage, onMusicLanguageChange) = rememberPreference(MusicLanguageKey, defaultValue = "Hindi")
    val (chartLocation, onChartLocationChange) = rememberPreference(ChartLocationKey, defaultValue = "India")
    val (minAudioLength, onMinAudioLengthChange) = rememberPreference(MinAudioLengthKey, defaultValue = 10)
    val (includeOrExclude, onIncludeOrExcludeChange) = rememberPreference(IncludeOrExcludeKey, defaultValue = false)
    val (includedExcludedPaths, onIncludedExcludedPathsChange) = rememberPreference(IncludedExcludedPathsKey, defaultValue = "")
    val (minDuration, onMinDurationChange) = rememberPreference(MinDurationKey, defaultValue = 10)

    Column(
        Modifier
            .windowInsetsPadding(LocalPlayerAwareWindowInsets.current)
            .verticalScroll(rememberScrollState()),
    ) {
        PreferenceGroupTitle(title = stringResource(R.string.general))

        ListPreference(
            title = { Text(stringResource(R.string.content_language)) },
            icon = { Icon(painterResource(R.drawable.language), null) },
            selectedValue = contentLanguage,
            values = listOf(SYSTEM_DEFAULT) + LanguageCodeToName.keys.toList(),
            valueText = {
                LanguageCodeToName.getOrElse(it) { stringResource(R.string.system_default) }
            },
            onValueSelected = { newValue ->
                val locale = Locale.getDefault()
                val languageTag = locale.toLanguageTag().replace("-Hant", "")

                YouTube.locale = YouTube.locale.copy(
                    hl = newValue.takeIf { it != SYSTEM_DEFAULT }
                        ?: locale.language.takeIf { it in LanguageCodeToName }
                        ?: languageTag.takeIf { it in LanguageCodeToName }
                        ?: "en"
                )

                onContentLanguageChange(newValue)
            }
        )

        ListPreference(
            title = { Text(stringResource(R.string.content_country)) },
            icon = { Icon(painterResource(R.drawable.location_on), null) },
            selectedValue = contentCountry,
            values = listOf(SYSTEM_DEFAULT) + CountryCodeToName.keys.toList(),
            valueText = {
                CountryCodeToName.getOrElse(it) { stringResource(R.string.system_default) }
            },
            onValueSelected = { newValue ->
                val locale = Locale.getDefault()

                YouTube.locale = YouTube.locale.copy(
                    gl = newValue.takeIf { it != SYSTEM_DEFAULT }
                        ?: locale.country.takeIf { it in CountryCodeToName }
                        ?: "US"
                )

                onContentCountryChange(newValue)
            }
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.hide_explicit)) },
            icon = { Icon(painterResource(R.drawable.explicit), null) },
            checked = hideExplicit,
            onCheckedChange = onHideExplicitChange,
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.hide_video)) },
            icon = { Icon(painterResource(R.drawable.slow_motion_video), null) },
            checked = hideVideo,
            onCheckedChange = onHideVideoChange,
        )

        PreferenceGroupTitle(title = stringResource(R.string.app_language))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PreferenceEntry(
                title = { Text(stringResource(R.string.app_language)) },
                subtitle = {
                    Text(
                        text = getLanguageDisplayName(appLanguage)
                    )
                },
                icon = { Icon(painterResource(R.drawable.translate), null) },
                onClick = {
                    context.startActivity(
                        Intent(
                            Settings.ACTION_APP_LOCALE_SETTINGS,
                            "package:${context.packageName}".toUri()
                        )
                    )
                }
            )
        } else {
            PreferenceEntry(
                title = { Text(stringResource(R.string.app_language)) },
                subtitle = {
                    Text(
                        text = getLanguageDisplayName(appLanguage)
                    )
                },
                icon = { Icon(painterResource(R.drawable.language), null) },
                onClick = { showLanguageSelector = true }
            )
        }

        PreferenceGroupTitle(title = stringResource(R.string.proxy))

        SwitchPreference(
            title = { Text(stringResource(R.string.enable_proxy)) },
            icon = { Icon(painterResource(R.drawable.wifi_proxy), null) },
            checked = proxyEnabled,
            onCheckedChange = onProxyEnabledChange,
        )

        if (proxyEnabled) {
            Column {
                ListPreference(
                    title = { Text(stringResource(R.string.proxy_type)) },
                    selectedValue = proxyType,
                    values = listOf(Proxy.Type.HTTP, Proxy.Type.SOCKS),
                    valueText = { it.name },
                    onValueSelected = onProxyTypeChange,
                )
                EditTextPreference(
                    title = { Text(stringResource(R.string.proxy_url)) },
                    value = proxyUrl,
                    onValueChange = onProxyUrlChange,
                )
                SwitchPreference(
                    title = { Text(stringResource(R.string.stream_bypass_proxy)) },
                    description = stringResource(R.string.stream_bypass_proxy_desc),
                    icon = { Icon(painterResource(R.drawable.wifi_proxy), null) },
                    checked = streamBypassProxy,
                    onCheckedChange = {
                        onStreamBypassProxyChange(it)
                        YouTube.streamBypassProxy = it
                    },
                )
            }
        }

        PreferenceGroupTitle(title = stringResource(R.string.lyrics))

        SwitchPreference(
            title = { Text(stringResource(R.string.enable_lrclib)) },
            icon = { Icon(painterResource(R.drawable.lyrics), null) },
            checked = enableLrclib,
            onCheckedChange = onEnableLrclibChange,
        )
        SwitchPreference(
            title = { Text(stringResource(R.string.enable_kugou)) },
            icon = { Icon(painterResource(R.drawable.lyrics), null) },
            checked = enableKugou,
            onCheckedChange = onEnableKugouChange,
        )
        SwitchPreference(
            title = { Text(stringResource(R.string.enable_betterlyrics)) },
            icon = { Icon(painterResource(R.drawable.lyrics), null) },
            checked = enableBetterLyrics,
            onCheckedChange = onEnableBetterLyricsChange,
        )
        SwitchPreference(
            title = { Text(stringResource(R.string.enable_simpmusic_lyrics)) },
            icon = { Icon(painterResource(R.drawable.lyrics), null) },
            checked = enableSimpMusicLyrics,
            onCheckedChange = onEnableSimpMusicLyricsChange,
        )

        val savedOrder = remember(providerOrder) {
            val parsed = providerOrder.split(",")
                .mapNotNull { name -> PreferredLyricsProvider.entries.find { it.name == name } }

            val missing = DefaultProviderOrder.filterNot { it in parsed }
            (parsed + missing).ifEmpty { DefaultProviderOrder }
        }

        PreferenceEntry(
            title = {
                Text(stringResource(R.string.lyrics_provider_order))
            },
            subtitle = {
                Text(
                    stringResource(
                        R.string.lyrics_provider_priority,
                        savedOrder.joinToString(" → ") { it.displayName() }
                    )
                )
            },
            icon = { Icon(painterResource(R.drawable.lyrics), null) },
            onClick = { showProviderOrderDialog = true },
        )

        if (showProviderOrderDialog) {
            DragDropLyricsProviderDialog(
                providers = savedOrder,
                selectedProvider = preferredProvider,
                onDismiss = { showProviderOrderDialog = false },
                onOrderConfirmed = { newOrder ->
                    onProviderOrderChange(newOrder.joinToString(",") { it.name })

                    val newPreferred = newOrder.firstOrNull() ?: PreferredLyricsProvider.LRCLIB
                    if (newPreferred != preferredProvider) {
                        onPreferredProviderChange(newPreferred)
                    }
                },
                valueText = { it.displayName() },
            )
        }

        SwitchPreference(
            title = { Text(stringResource(R.string.lyrics_romanize_japanese)) },
            icon = { Icon(painterResource(R.drawable.lyrics), null) },
            checked = lyricsRomanizeJapanese,
            onCheckedChange = onLyricsRomanizeJapaneseChange,
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.lyrics_romanize_korean)) },
            icon = { Icon(painterResource(R.drawable.lyrics), null) },
            checked = lyricsRomanizeKorean,
            onCheckedChange = onLyricsRomanizeKoreanChange,
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.preload_queue_lyrics)) },
            icon = { Icon(painterResource(R.drawable.lyrics), null) },
            checked = preloadQueueLyricsEnabled,
            onCheckedChange = onPreloadQueueLyricsEnabledChange,
        )

        if (preloadQueueLyricsEnabled) {
            NumberPickerPreference(
                title = { Text(stringResource(R.string.queue_lyrics_preload_count)) },
                icon = { Icon(painterResource(R.drawable.lyrics), null) },
                value = queueLyricsPreloadCount,
                onValueChange = onQueueLyricsPreloadCountChange,
                minValue = 0,
                maxValue = 10,
                valueText = { if (it == 0) "Off" else it.toString() },
            )
        }

        PreferenceGroupTitle(title = stringResource(R.string.playback))

        SwitchPreference(
            title = { Text(stringResource(R.string.jossred_fallback_label)) },
            description = stringResource(R.string.jossred_fallback_description),
            icon = { Icon(painterResource(R.drawable.cloud_off), null) },
            checked = jossRedEnabled,
            onCheckedChange = onJossRedEnabledChange,
        )

        PreferenceGroupTitle(title = stringResource(R.string.lyrics))

        SwitchPreference(
            title = { Text(stringResource(R.string.enable_spotify_lyrics)) },
            description = stringResource(R.string.spotify_lyrics_info),
            icon = { Icon(painterResource(R.drawable.lyrics), null) },
            checked = enableSpotifyLyrics,
            onCheckedChange = onEnableSpotifyLyricsChange,
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.lyrics_romanization)) },
            icon = { Icon(painterResource(R.drawable.translate), null) },
            checked = lyricsRomanization,
            onCheckedChange = onLyricsRomanizationChange,
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.help_build_lyrics_database)) },
            description = stringResource(R.string.help_build_lyrics_database_description),
            icon = { Icon(painterResource(R.drawable.contributors), null) },
            checked = helpBuildLyricsDatabase,
            onCheckedChange = onHelpBuildLyricsDatabaseChange,
        )

        PreferenceEntry(
            title = { Text(stringResource(R.string.contributor_name)) },
            description = contributorName.ifEmpty { "Not set" },
            icon = { Icon(painterResource(R.drawable.person), null) },
            onClick = { showContributorNameDialog = true },
            isEnabled = helpBuildLyricsDatabase,
        )

        PreferenceEntry(
            title = { Text(stringResource(R.string.contributor_email)) },
            description = contributorEmail.ifEmpty { "Not set" },
            icon = { Icon(painterResource(R.drawable.email), null) },
            onClick = { showContributorEmailDialog = true },
            isEnabled = helpBuildLyricsDatabase,
        )

        PreferenceEntry(
            title = { Text(stringResource(R.string.youtube_subtitle_language)) },
            description = youtubeSubtitleLanguage.ifEmpty { "Auto" },
            icon = { Icon(painterResource(R.drawable.language), null) },
            onClick = { showYouTubeSubtitleLanguageDialog = true },
        )

        PreferenceGroupTitle(title = stringResource(R.string.misc))

        SwitchPreference(
            title = { Text(stringResource(R.string.enable_sponsor_block)) },
            description = stringResource(R.string.sponsor_block_description),
            icon = { Icon(painterResource(R.drawable.block), null) },
            checked = enableSponsorBlock,
            onCheckedChange = onEnableSponsorBlockChange,
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.local_tracking)) },
            description = stringResource(R.string.local_tracking_description),
            icon = { Icon(painterResource(R.drawable.history), null) },
            checked = localTrackingEnabled,
            onCheckedChange = onLocalTrackingEnabledChange,
        )

        PreferenceGroupTitle(title = stringResource(R.string.misc))

        EditTextPreference(
            title = { Text(stringResource(R.string.top_length)) },
            icon = { Icon(painterResource(R.drawable.trending_up), null) },
            value = lengthTop,
            isInputValid = { it.toIntOrNull()?.let { num -> num > 0 } == true },
            onValueChange = onLengthTopChange,
        )

        ListPreference(
            title = { Text(stringResource(R.string.set_quick_picks)) },
            icon = { Icon(painterResource(R.drawable.home_outlined), null) },
            selectedValue = quickPicks,
            values = listOf(QuickPicks.QUICK_PICKS, QuickPicks.LAST_LISTEN),
            valueText = {
                when (it) {
                    QuickPicks.QUICK_PICKS -> stringResource(R.string.quick_picks)
                    QuickPicks.LAST_LISTEN -> stringResource(R.string.last_song_listened)
                }
            },
            onValueSelected = onQuickPicksChange,
        )

        // === BlackHole-ported Download settings ===
        PreferenceGroupTitle(title = "Download")

        ListPreference(
            title = { Text("Download Quality") },
            icon = { Icon(painterResource(R.drawable.download), null) },
            selectedValue = downloadQuality,
            values = listOf("96 kbps", "160 kbps", "320 kbps"),
            valueText = { it },
            onValueSelected = onDownloadQualityChange,
        )

        ListPreference(
            title = { Text("YouTube Download Quality") },
            icon = { Icon(painterResource(R.drawable.play), null) },
            selectedValue = ytDownloadQuality,
            values = listOf("Low", "High"),
            valueText = { it },
            onValueSelected = onYtDownloadQualityChange,
        )

        ListPreference(
            title = { Text("Download Filename") },
            icon = { Icon(painterResource(R.drawable.edit), null) },
            selectedValue = downloadFilename,
            values = listOf(0, 1, 2),
            valueText = { idx ->
                when (idx) {
                    0 -> "Title - Artist"
                    1 -> "Artist - Title"
                    2 -> "Title"
                    else -> "Title - Artist"
                }
            },
            onValueSelected = onDownloadFilenameChange,
        )

        SwitchPreference(
            title = { Text("Create Album Folder") },
            description = "Create separate folder for each album",
            icon = { Icon(painterResource(R.drawable.folder), null) },
            checked = createAlbumFolder,
            onCheckedChange = onCreateAlbumFolderChange
        )

        SwitchPreference(
            title = { Text("Create YouTube Folder") },
            description = "Create separate folder for YouTube downloads",
            icon = { Icon(painterResource(R.drawable.folder), null) },
            checked = createYoutubeFolder,
            onCheckedChange = onCreateYoutubeFolderChange
        )

        SwitchPreference(
            title = { Text("Download Lyrics") },
            description = "Download lyrics along with songs",
            icon = { Icon(painterResource(R.drawable.lyrics), null) },
            checked = downloadLyrics,
            onCheckedChange = onDownloadLyricsChange
        )

        // === BlackHole-ported Other settings ===
        PreferenceGroupTitle(title = "Others")

        SwitchPreference(
            title = { Text("Live Search") },
            description = "Search as you type",
            icon = { Icon(painterResource(R.drawable.search), null) },
            checked = liveSearch,
            onCheckedChange = onLiveSearchChange
        )

        SwitchPreference(
            title = { Text("Use Down") },
            description = "Use downloaded version if available",
            icon = { Icon(painterResource(R.drawable.download), null) },
            checked = useDown,
            onCheckedChange = onUseDownChange
        )

        SwitchPreference(
            title = { Text("Get Lyrics Online") },
            description = "Fetch lyrics from online sources",
            icon = { Icon(painterResource(R.drawable.lyrics), null) },
            checked = getLyricsOnline,
            onCheckedChange = onGetLyricsOnlineChange
        )

        SwitchPreference(
            title = { Text("Support Equalizer") },
            description = "Enable built-in equalizer support",
            icon = { Icon(painterResource(R.drawable.equalizer), null) },
            checked = supportEq,
            onCheckedChange = onSupportEqChange
        )

        // === Music Language (16 Indian languages) ===
        PreferenceGroupTitle(title = "Music Language")

        ListPreference(
            title = { Text("Music Language") },
            icon = { Icon(painterResource(R.drawable.language), null) },
            selectedValue = musicLanguage,
            values = listOf("Hindi", "English", "Punjabi", "Tamil", "Telugu", "Marathi", "Gujarati", "Bengali", "Kannada", "Bhojpuri", "Malayalam", "Urdu", "Haryanvi", "Rajasthani", "Odia", "Assamese"),
            valueText = { it },
            onValueSelected = onMusicLanguageChange,
        )

        // === Chart Location (68 countries) ===
        ListPreference(
            title = { Text("Chart Location") },
            icon = { Icon(painterResource(R.drawable.public_icon), null) },
            selectedValue = chartLocation,
            values = listOf("Global", "India", "United States", "United Kingdom", "Australia", "Brazil", "Canada", "France", "Germany", "Japan", "South Korea", "Spain", "Italy", "Mexico", "Netherlands", "Russia", "Sweden", "Switzerland", "Taiwan", "Thailand", "Turkey", "UAE", "Argentina", "Chile", "Colombia", "Egypt", "Hong Kong", "Indonesia", "Ireland", "Israel", "Malaysia", "New Zealand", "Nigeria", "Norway", "Pakistan", "Peru", "Philippines", "Poland", "Portugal", "Romania", "Saudi Arabia", "Singapore", "South Africa", "Vietnam", "Belgium", "Bulgaria", "Costa Rica", "Denmark", "Dominican Republic", "Ecuador", "El Salvador", "Estonia", "Finland", "Greece", "Guatemala", "Honduras", "Hungary", "Iceland", "Kazakhstan", "Latvia", "Lithuania", "Luxembourg", "Nicaragua", "Panama", "Paraguay", "Slovakia", "Uruguay", "Venezuela", "Belarus", "Bolivia"),
            valueText = { it },
            onValueSelected = onChartLocationChange,
        )

        // === Include/Exclude Folder ===
        PreferenceGroupTitle(title = "Local Folders")

        SwitchPreference(
            title = { Text("Include Mode") },
            description = if (includeOrExclude) "Only scan listed folders" else "Exclude listed folders",
            icon = { Icon(painterResource(R.drawable.folder), null) },
            checked = includeOrExclude,
            onCheckedChange = onIncludeOrExcludeChange
        )

        // === Minimum Audio Length ===
        PreferenceEntry(
            title = { Text("Minimum Audio Length") },
            description = "Filter audio shorter than ${minDuration}s",
            icon = { Icon(painterResource(R.drawable.timer), null) },
            onClick = {
                val next = when (minDuration) {
                    0 -> 10; 10 -> 30; 30 -> 60; 60 -> 120; 120 -> 0; else -> 10
                }
                onMinDurationChange(next)
            }
        )

        PreferenceEntry(
            title = { Text("Minimum Audio Length (Local)") },
            description = "Filter local audio shorter than ${minAudioLength}s",
            icon = { Icon(painterResource(R.drawable.timer), null) },
            onClick = {
                val next = when (minAudioLength) {
                    0 -> 10; 10 -> 30; 30 -> 60; 60 -> 120; 120 -> 0; else -> 10
                }
                onMinAudioLengthChange(next)
            }
        )

        // === Share Logs ===
        PreferenceEntry(
            title = { Text("Share Logs") },
            description = "Share app logs for debugging",
            icon = { Icon(painterResource(R.drawable.share), null) },
            onClick = { }
        )

    }

    LanguageSelectorBottomSheet(
        show = showLanguageSelector,
        title = "Select App Language",
        languages = languageOptions,
        selectedCode = appLanguage,
        systemDefaultCode = SYSTEM_DEFAULT,
        systemDefaultLabel = "System Default",
        searchPlaceholder = "Search language...",
        onDismiss = { showLanguageSelector = false },
        onLanguageSelected = { selectedCode ->
            onAppLanguageChange(selectedCode)

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                val newLocale = if (selectedCode == SYSTEM_DEFAULT) {
                    Locale.getDefault()
                } else {
                    Locale.forLanguageTag(selectedCode)
                }
                setAppLocale(context, newLocale)
            }

            showLanguageSelector = false
        }
    )

    if (showContributorNameDialog) {
        TextFieldDialog(
            initialTextFieldValue = androidx.compose.ui.text.input.TextFieldValue(contributorName),
            onDone = { name ->
                onContributorNameChange(name)
                showContributorNameDialog = false
            },
            onDismiss = { showContributorNameDialog = false },
            singleLine = true,
            maxLines = 1,
        )
    }

    if (showContributorEmailDialog) {
        TextFieldDialog(
            initialTextFieldValue = androidx.compose.ui.text.input.TextFieldValue(contributorEmail),
            onDone = { email ->
                onContributorEmailChange(email)
                showContributorEmailDialog = false
            },
            onDismiss = { showContributorEmailDialog = false },
            singleLine = true,
            maxLines = 1,
        )
    }

    if (showYouTubeSubtitleLanguageDialog) {
        TextFieldDialog(
            initialTextFieldValue = androidx.compose.ui.text.input.TextFieldValue(youtubeSubtitleLanguage),
            onDone = { lang ->
                onYouTubeSubtitleLanguageChange(lang)
                showYouTubeSubtitleLanguageDialog = false
            },
            onDismiss = { showYouTubeSubtitleLanguageDialog = false },
            singleLine = true,
            maxLines = 1,
        )
    }

    TopAppBar(
        title = { Text(stringResource(R.string.content)) },
        navigationIcon = {
            IconButton(
                onClick = navController::navigateUp,
                onLongClick = navController::backToMain,
            ) {
                Icon(
                    painterResource(R.drawable.arrow_back),
                    contentDescription = null,
                )
            }
        }
    )
}