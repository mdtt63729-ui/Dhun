/*
 * Dhun Project Original (2026)
 * Dhun
 * Licensed Under GPL-3.0 | see git history for contributors
 */



package com.arturo254.dhun.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arturo254.dhun.LocalPlayerAwareWindowInsets
import com.arturo254.dhun.R
import com.arturo254.dhun.constants.ArtistSeparatorsKey
import com.arturo254.dhun.constants.ExternalDownloaderEnabledKey
import com.arturo254.dhun.constants.ExternalDownloaderPackageKey
import com.arturo254.dhun.constants.AudioNormalizationKey
import com.arturo254.dhun.constants.AudioOffload
import com.arturo254.dhun.constants.AudioQuality
import com.arturo254.dhun.constants.AudioQualityKey
import com.arturo254.dhun.constants.NetworkMeteredKey
import com.arturo254.dhun.constants.AutoDownloadOnLikeKey
import com.arturo254.dhun.constants.AutoStartOnBluetoothKey
import com.arturo254.dhun.constants.AutoSkipNextOnErrorKey
import com.arturo254.dhun.constants.PauseOnDeviceMuteKey
import com.arturo254.dhun.constants.PermanentShuffleKey
import com.arturo254.dhun.constants.PersistentQueueKey

import com.arturo254.dhun.constants.SkipSilenceKey
import com.arturo254.dhun.constants.StopMusicOnTaskClearKey
import com.arturo254.dhun.constants.WakelockKey
import com.arturo254.dhun.constants.HistoryDuration
import com.arturo254.dhun.constants.AudioCrossfadeDurationKey
import com.arturo254.dhun.constants.PlayerStreamClient
import com.arturo254.dhun.constants.PlayerStreamClientKey
import com.arturo254.dhun.constants.SeekExtraSeconds
import com.arturo254.dhun.constants.CrossfadeDjModeKey
import com.arturo254.dhun.constants.CrossfadeSkipAlbumKey
import com.arturo254.dhun.constants.SaveLastPlayedKey
import com.arturo254.dhun.constants.KillServiceOnExitKey
import com.arturo254.dhun.constants.KeepServiceAliveKey
import com.arturo254.dhun.constants.PlayExplicitContentKey
import com.arturo254.dhun.constants.CombineLikedSongsKey
import com.arturo254.dhun.constants.RadioAudioOnlyKey
import com.arturo254.dhun.constants.PlayVideoInsteadOfAudioKey
import com.arturo254.dhun.constants.SendListeningDataToGoogleKey
import com.arturo254.dhun.constants.SyncFollowToYouTubeKey
import com.arturo254.dhun.constants.KeepYouTubePlaylistOfflineKey
import com.arturo254.dhun.constants.AutoDownloadLikedSongsKey
import com.arturo254.dhun.constants.SavePlaybackStateKey
import com.arturo254.dhun.constants.LoadLastKey
import com.arturo254.dhun.constants.ResetOnSkipKey
import com.arturo254.dhun.constants.EnforceRepeatKey
import com.arturo254.dhun.constants.AutoplayKey
import com.arturo254.dhun.constants.CacheSongKey
import com.arturo254.dhun.constants.StreamingQualityMobileKey
import com.arturo254.dhun.constants.StreamingQualityWifiKey
import com.arturo254.dhun.constants.YtStreamQualityKey
import com.arturo254.dhun.constants.SleepTimerDurationKey
import com.arturo254.dhun.constants.PlaybackSpeedKey
import com.arturo254.dhun.constants.FadeOutDurationKey
import com.arturo254.dhun.constants.AbRepeatKey
import com.arturo254.dhun.constants.EqualizerCustomProfilesJsonKey
import com.arturo254.dhun.ui.component.ArtistSeparatorsDialog
import com.arturo254.dhun.ui.component.TagsManagementDialog
import com.arturo254.dhun.ui.component.TextFieldDialog
import com.arturo254.dhun.ui.component.EnumListPreference
import com.arturo254.dhun.ui.component.ListPreference
import com.arturo254.dhun.ui.component.IconButton
import com.arturo254.dhun.ui.component.ListDialog
import com.arturo254.dhun.ui.component.PreferenceEntry
import com.arturo254.dhun.ui.component.PreferenceGroupTitle
import com.arturo254.dhun.ui.component.SliderPreference
import com.arturo254.dhun.ui.component.CrossfadeSliderPreference
import com.arturo254.dhun.ui.component.SwitchPreference
import com.arturo254.dhun.ui.utils.backToMain
import com.arturo254.dhun.utils.rememberEnumPreference
import com.arturo254.dhun.utils.rememberPreference
import com.arturo254.dhun.LocalDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerSettings(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val (audioQuality, onAudioQualityChange) = rememberEnumPreference(
        AudioQualityKey,
        defaultValue = AudioQuality.AUTO
    )
    val (playerStreamClient, onPlayerStreamClientChange) = rememberEnumPreference(
        PlayerStreamClientKey,
        defaultValue = PlayerStreamClient.ANDROID_VR
    )
    val (networkMetered, onNetworkMeteredChange) = rememberPreference(
        NetworkMeteredKey,
        defaultValue = true
    )
    val (persistentQueue, onPersistentQueueChange) = rememberPreference(
        PersistentQueueKey,
        defaultValue = true
    )
    val (permanentShuffle, onPermanentShuffleChange) = rememberPreference(
        PermanentShuffleKey,
        defaultValue = false
    )
    val (skipSilence, onSkipSilenceChange) = rememberPreference(
        SkipSilenceKey,
        defaultValue = false
    )
    val (audioNormalization, onAudioNormalizationChange) = rememberPreference(
        AudioNormalizationKey,
        defaultValue = true
    )
    val (audioOffload, onAudioOffloadChange) = rememberPreference(
        AudioOffload,
        defaultValue = false
    )

    val (seekExtraSeconds, onSeekExtraSeconds) = rememberPreference(
        SeekExtraSeconds,
        defaultValue = false
    )

    val (autoDownloadOnLike, onAutoDownloadOnLikeChange) = rememberPreference(
        AutoDownloadOnLikeKey,
        defaultValue = false
    )
    val (autoSkipNextOnError, onAutoSkipNextOnErrorChange) = rememberPreference(
        AutoSkipNextOnErrorKey,
        defaultValue = false
    )
    val (pauseOnDeviceMute, onPauseOnDeviceMuteChange) = rememberPreference(
        PauseOnDeviceMuteKey,
        defaultValue = false
    )
    val (autoStartOnBluetooth, onAutoStartOnBluetoothChange) = rememberPreference(
        AutoStartOnBluetoothKey,
        defaultValue = false
    )
    val (stopMusicOnTaskClear, onStopMusicOnTaskClearChange) = rememberPreference(
        StopMusicOnTaskClearKey,
        defaultValue = false
    )
    val (historyDuration, onHistoryDurationChange) = rememberPreference(
        HistoryDuration,
        defaultValue = 30f
    )

    val (audioCrossfadeSeconds, onAudioCrossfadeSecondsChange) = rememberPreference(
        AudioCrossfadeDurationKey,
        defaultValue = 0
    )

    val (artistSeparators, onArtistSeparatorsChange) = rememberPreference(
        ArtistSeparatorsKey,
        defaultValue = ",;/&"
    )
    val (externalDownloaderEnabled, onExternalDownloaderEnabledChange) = rememberPreference(
        ExternalDownloaderEnabledKey,
        defaultValue = false
    )
    val (externalDownloaderPackage, onExternalDownloaderPackageChange) = rememberPreference(
        ExternalDownloaderPackageKey,
        defaultValue = ""
    )

    val (wakelockEnabled, onWakelockChange) = rememberPreference(
        WakelockKey,
        defaultValue = false
    )

    // BlackHole-ported playback settings
    val (loadLast, onLoadLastChange) = rememberPreference(LoadLastKey, defaultValue = true)
    val (resetOnSkip, onResetOnSkipChange) = rememberPreference(ResetOnSkipKey, defaultValue = false)
    val (enforceRepeat, onEnforceRepeatChange) = rememberPreference(EnforceRepeatKey, defaultValue = false)
    val (autoplay, onAutoplayChange) = rememberPreference(AutoplayKey, defaultValue = true)
    val (cacheSong, onCacheSongChange) = rememberPreference(CacheSongKey, defaultValue = true)
    val (streamingQualityMobile, onStreamingQualityMobileChange) = rememberPreference(StreamingQualityMobileKey, defaultValue = "96 kbps")
    val (streamingQualityWifi, onStreamingQualityWifiChange) = rememberPreference(StreamingQualityWifiKey, defaultValue = "320 kbps")
    val (ytStreamQuality, onYtStreamQualityChange) = rememberPreference(YtStreamQualityKey, defaultValue = "Low")

    // New feature settings
    val (sleepTimerDuration, onSleepTimerDurationChange) = rememberPreference(SleepTimerDurationKey, defaultValue = 0)
    val (playbackSpeed, onPlaybackSpeedChange) = rememberPreference(PlaybackSpeedKey, defaultValue = 1.0f)
    val (fadeOutDuration, onFadeOutDurationChange) = rememberPreference(FadeOutDurationKey, defaultValue = 0f)
    val (abRepeat, onAbRepeatChange) = rememberPreference(AbRepeatKey, defaultValue = "")
    val (equalizerProfiles, onEqualizerProfilesChange) = rememberPreference(EqualizerCustomProfilesJsonKey, defaultValue = "")

    // SimpMusic-ported settings
    val (crossfadeDjMode, onCrossfadeDjModeChange) = rememberPreference(
        CrossfadeDjModeKey,
        defaultValue = true
    )
    val (crossfadeSkipAlbum, onCrossfadeSkipAlbumChange) = rememberPreference(
        CrossfadeSkipAlbumKey,
        defaultValue = false
    )
    val (saveLastPlayed, onSaveLastPlayedChange) = rememberPreference(
        SaveLastPlayedKey,
        defaultValue = true
    )
    val (killServiceOnExit, onKillServiceOnExitChange) = rememberPreference(
        KillServiceOnExitKey,
        defaultValue = false
    )
    val (keepServiceAlive, onKeepServiceAliveChange) = rememberPreference(
        KeepServiceAliveKey,
        defaultValue = false
    )
    val (playExplicitContent, onPlayExplicitContentChange) = rememberPreference(
        PlayExplicitContentKey,
        defaultValue = false
    )
    val (combineLikedSongs, onCombineLikedSongsChange) = rememberPreference(
        CombineLikedSongsKey,
        defaultValue = false
    )
    val (radioAudioOnly, onRadioAudioOnlyChange) = rememberPreference(
        RadioAudioOnlyKey,
        defaultValue = true
    )
    val (playVideoInsteadOfAudio, onPlayVideoInsteadOfAudioChange) = rememberPreference(
        PlayVideoInsteadOfAudioKey,
        defaultValue = false
    )
    val (sendListeningDataToGoogle, onSendListeningDataToGoogleChange) = rememberPreference(
        SendListeningDataToGoogleKey,
        defaultValue = false
    )
    val (syncFollowToYouTube, onSyncFollowToYouTubeChange) = rememberPreference(
        SyncFollowToYouTubeKey,
        defaultValue = false
    )
    val (keepYouTubePlaylistOffline, onKeepYouTubePlaylistOfflineChange) = rememberPreference(
        KeepYouTubePlaylistOfflineKey,
        defaultValue = false
    )
    val (autoDownloadLikedSongs, onAutoDownloadLikedSongsChange) = rememberPreference(
        AutoDownloadLikedSongsKey,
        defaultValue = false
    )
    val (savePlaybackState, onSavePlaybackStateChange) = rememberPreference(
        SavePlaybackStateKey,
        defaultValue = true
    )

    var showArtistSeparatorsDialog by remember { mutableStateOf(false) }
    var showTagsManagementDialog by remember { mutableStateOf(false) }
    var showPlayerStreamClientDialog by remember { mutableStateOf(false) }
    var showExternalDownloaderPackageDialog by remember { mutableStateOf(false) }
    val database = LocalDatabase.current

    if (showArtistSeparatorsDialog) {
        ArtistSeparatorsDialog(
            currentSeparators = artistSeparators,
            onDismiss = { showArtistSeparatorsDialog = false },
            onSave = { newSeparators ->
                onArtistSeparatorsChange(newSeparators)
                showArtistSeparatorsDialog = false
            }
        )
    }

    if (showTagsManagementDialog) {
        TagsManagementDialog(
            database = database,
            onDismiss = { showTagsManagementDialog = false }
        )
    }

    if (showExternalDownloaderPackageDialog) {
        TextFieldDialog(
            initialTextFieldValue = androidx.compose.ui.text.input.TextFieldValue(externalDownloaderPackage),
            onDone = { pkg ->
                onExternalDownloaderPackageChange(pkg)
                showExternalDownloaderPackageDialog = false
            },
            onDismiss = { showExternalDownloaderPackageDialog = false },
            singleLine = true,
            maxLines = 1,
        )
    }

    if (showPlayerStreamClientDialog) {
        ListDialog(
            onDismiss = { showPlayerStreamClientDialog = false },
            modifier = Modifier.padding(horizontal = 8.dp),
        ) {
            items(listOf(PlayerStreamClient.ANDROID_VR, PlayerStreamClient.WEB_REMIX)) { value ->
                Row(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            onPlayerStreamClientChange(value)
                            showPlayerStreamClientDialog = false
                        }.padding(horizontal = 16.dp, vertical = 12.dp),
                ) {
                    RadioButton(
                        selected = value == playerStreamClient,
                        onClick = null,
                    )

                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text(
                            text =
                            when (value) {
                                PlayerStreamClient.ANDROID_VR -> stringResource(R.string.player_stream_client_android_vr)
                                else -> stringResource(R.string.player_stream_client_web_remix)
                            },
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text =
                            when (value) {
                                PlayerStreamClient.ANDROID_VR -> stringResource(R.string.player_stream_client_android_vr_desc)
                                else -> stringResource(R.string.player_stream_client_web_remix_desc)
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    }
                }
            }
        }
    }

    Column(
        Modifier
            .windowInsetsPadding(LocalPlayerAwareWindowInsets.current.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom))
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(
            Modifier.windowInsetsPadding(
                LocalPlayerAwareWindowInsets.current.only(
                    WindowInsetsSides.Top
                )
            )
        )

        PreferenceGroupTitle(
            title = stringResource(R.string.player)
        )

        EnumListPreference(
            title = { Text(stringResource(R.string.audio_quality)) },
            icon = { Icon(painterResource(R.drawable.graphic_eq), null) },
            selectedValue = audioQuality,
            onValueSelected = onAudioQualityChange,
            valueText = {
                when (it) {
                    AudioQuality.HIGHEST -> stringResource(R.string.audio_quality_max)
                    AudioQuality.HIGH -> stringResource(R.string.audio_quality_high)
                    AudioQuality.AUTO -> stringResource(R.string.audio_quality_auto)
                    AudioQuality.LOW -> stringResource(R.string.audio_quality_low)
                    AudioQuality.ULTRA_HIGH -> stringResource(R.string.audio_quality_ultra_high)
                    AudioQuality.ULTRA_HIGH_PRO -> stringResource(R.string.audio_quality_ultra_high_pro)
                    AudioQuality.EXTREME -> stringResource(R.string.audio_quality_extreme)
                }
            }
        )

        PreferenceEntry(
            title = { Text(stringResource(R.string.player_stream_client)) },
            description =
            when (playerStreamClient) {
                PlayerStreamClient.ANDROID_VR -> stringResource(R.string.player_stream_client_android_vr)
                else -> stringResource(R.string.player_stream_client_web_remix)
            },
            icon = { Icon(painterResource(R.drawable.integration), null) },
            onClick = { showPlayerStreamClientDialog = true }
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.network_metered_title)) },
            description = stringResource(R.string.network_metered_description),
            icon = { Icon(painterResource(R.drawable.android_cell), null) },
            checked = networkMetered,
            onCheckedChange = onNetworkMeteredChange
        )

        SliderPreference(
            title = { Text(stringResource(R.string.history_duration)) },
            icon = { Icon(painterResource(R.drawable.history), null) },
            value = historyDuration,
            onValueChange = onHistoryDurationChange,
        )

        CrossfadeSliderPreference(
            value = audioCrossfadeSeconds,
            onValueChange = onAudioCrossfadeSecondsChange,
            isEnabled = !audioOffload,
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.skip_silence)) },
            icon = { Icon(painterResource(R.drawable.fast_forward), null) },
            checked = skipSilence,
            onCheckedChange = onSkipSilenceChange,
            isEnabled = !audioOffload,
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.audio_normalization)) },
            icon = { Icon(painterResource(R.drawable.volume_up), null) },
            checked = audioNormalization,
            onCheckedChange = onAudioNormalizationChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.audio_offload)) },
            description = stringResource(R.string.audio_offload_desc),
            icon = { Icon(painterResource(R.drawable.speed), null) },
            checked = audioOffload,
            onCheckedChange = { enabled ->
                onAudioOffloadChange(enabled)
                if (enabled) {
                    onSkipSilenceChange(false)
                }
            }
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.seek_seconds_addup)) },
            description = stringResource(R.string.seek_seconds_addup_description),
            icon = { Icon(painterResource(R.drawable.arrow_forward), null) },
            checked = seekExtraSeconds,
            onCheckedChange = onSeekExtraSeconds
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.pause_on_device_mute)) },
            description = stringResource(R.string.pause_on_device_mute_desc),
            icon = { Icon(painterResource(R.drawable.volume_off), null) },
            checked = pauseOnDeviceMute,
            onCheckedChange = onPauseOnDeviceMuteChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.auto_start_on_bluetooth)) },
            description = stringResource(R.string.auto_start_on_bluetooth_desc),
            icon = { Icon(painterResource(R.drawable.bluetooth), null) },
            checked = autoStartOnBluetooth,
            onCheckedChange = onAutoStartOnBluetoothChange
        )

        // Streaming Quality (Mobile) - BlackHole ported
        ListPreference(
            title = { Text("Streaming Quality (Mobile)") },
            icon = { Icon(painterResource(R.drawable.signal_cellular), null) },
            selectedValue = streamingQualityMobile,
            values = listOf("96 kbps", "160 kbps", "320 kbps"),
            valueText = { it },
            onValueSelected = onStreamingQualityMobileChange,
        )

        // Streaming Quality (WiFi) - BlackHole ported
        ListPreference(
            title = { Text("Streaming Quality (WiFi)") },
            icon = { Icon(painterResource(R.drawable.wifi), null) },
            selectedValue = streamingQualityWifi,
            values = listOf("96 kbps", "160 kbps", "320 kbps"),
            valueText = { it },
            onValueSelected = onStreamingQualityWifiChange,
        )

        // YouTube Stream Quality - BlackHole ported
        ListPreference(
            title = { Text("YouTube Stream Quality") },
            icon = { Icon(painterResource(R.drawable.play), null) },
            selectedValue = ytStreamQuality,
            values = listOf("Low", "High"),
            valueText = { it },
            onValueSelected = onYtStreamQualityChange,
        )

        // Load Last - BlackHole ported
        SwitchPreference(
            title = { Text("Load Last") },
            description = "Load last played song on startup",
            icon = { Icon(painterResource(R.drawable.history), null) },
            checked = loadLast,
            onCheckedChange = onLoadLastChange
        )

        // Reset on Skip - BlackHole ported
        SwitchPreference(
            title = { Text("Reset on Skip") },
            description = "Reset playback position when skipping",
            icon = { Icon(painterResource(R.drawable.skip_next), null) },
            checked = resetOnSkip,
            onCheckedChange = onResetOnSkipChange
        )

        // Enforce Repeat - BlackHole ported
        SwitchPreference(
            title = { Text("Enforce Repeat") },
            description = "Keep repeat mode across sessions",
            icon = { Icon(painterResource(R.drawable.repeat), null) },
            checked = enforceRepeat,
            onCheckedChange = onEnforceRepeatChange
        )

        // Autoplay - BlackHole ported
        SwitchPreference(
            title = { Text("Autoplay") },
            description = "Play similar songs after queue ends",
            icon = { Icon(painterResource(R.drawable.play), null) },
            checked = autoplay,
            onCheckedChange = onAutoplayChange
        )

        // Cache Song - BlackHole ported
        SwitchPreference(
            title = { Text("Cache Song") },
            description = "Cache songs during playback",
            icon = { Icon(painterResource(R.drawable.cached), null) },
            checked = cacheSong,
            onCheckedChange = onCacheSongChange
        )


        // === New Features: Sleep Timer, Speed, Fade Out, A-B Repeat ===

        PreferenceEntry(
            title = { Text("Sleep Timer") },
            description = if (sleepTimerDuration > 0) "Set: ${sleepTimerDuration} min" else "Off",
            icon = { Icon(painterResource(R.drawable.bedtime), null) },
            onClick = {
                val next = when (sleepTimerDuration) {
                    0 -> 15; 15 -> 30; 30 -> 45; 45 -> 60; 60 -> 90; 90 -> 0; else -> 0
                }
                onSleepTimerDurationChange(next)
            }
        )

        PreferenceEntry(
            title = { Text("Playback Speed") },
            description = String.format("%.1fx", playbackSpeed),
            icon = { Icon(painterResource(R.drawable.speed), null) },
            onClick = {
                val speeds = listOf(0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 1.75f, 2.0f)
                val idx = speeds.indexOfFirst { kotlin.math.abs(it - playbackSpeed) < 0.01f }
                val nextIdx = (idx + 1) % speeds.size
                onPlaybackSpeedChange(speeds[nextIdx])
            }
        )

        PreferenceEntry(
            title = { Text("Fade Out Duration") },
            description = if (fadeOutDuration > 0) "${fadeOutDuration.toInt()}s fade on pause" else "Disabled",
            icon = { Icon(painterResource(R.drawable.gradient), null) },
            onClick = {
                val next = when {
                    fadeOutDuration <= 0f -> 0.5f
                    fadeOutDuration < 1f -> 1f
                    fadeOutDuration < 2f -> 2f
                    fadeOutDuration < 3f -> 3f
                    else -> 0f
                }
                onFadeOutDurationChange(next)
            }
        )

        PreferenceEntry(
            title = { Text("A-B Repeat") },
            description = if (abRepeat.isEmpty()) "Not set" else "Active: $abRepeat",
            icon = { Icon(painterResource(R.drawable.repeat), null) },
            onClick = {
                onAbRepeatChange(if (abRepeat.isEmpty()) "active" else "")
            }
        )

        PreferenceEntry(
            title = { Text("Custom Equalizer Presets") },
            description = "Save and load custom EQ presets",
            icon = { Icon(painterResource(R.drawable.equalizer), null) },
            onClick = { }
        )


        PreferenceGroupTitle(
            title = stringResource(R.string.queue)
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.persistent_queue)) },
            description = stringResource(R.string.persistent_queue_desc),
            icon = { Icon(painterResource(R.drawable.queue_music), null) },
            checked = persistentQueue,
            onCheckedChange = onPersistentQueueChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.permanent_shuffle)) },
            description = stringResource(R.string.permanent_shuffle_desc),
            icon = { Icon(painterResource(R.drawable.shuffle), null) },
            checked = permanentShuffle,
            onCheckedChange = onPermanentShuffleChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.auto_download_on_like)) },
            description = stringResource(R.string.auto_download_on_like_desc),
            icon = { Icon(painterResource(R.drawable.download), null) },
            checked = autoDownloadOnLike,
            onCheckedChange = onAutoDownloadOnLikeChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.auto_skip_next_on_error)) },
            description = stringResource(R.string.auto_skip_next_on_error_desc),
            icon = { Icon(painterResource(R.drawable.skip_next), null) },
            checked = autoSkipNextOnError,
            onCheckedChange = onAutoSkipNextOnErrorChange
        )

        PreferenceGroupTitle(
            title = stringResource(R.string.misc)
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.stop_music_on_task_clear)) },
            icon = { Icon(painterResource(R.drawable.clear_all), null) },
            checked = stopMusicOnTaskClear,
            onCheckedChange = onStopMusicOnTaskClearChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.wakelock)) },
            description = stringResource(R.string.wakelock_desc),
            icon = { Icon(painterResource(R.drawable.bolt), null) },
            checked = wakelockEnabled,
            onCheckedChange = onWakelockChange
        )

        PreferenceEntry(
            title = { Text(stringResource(R.string.artist_separators)) },
            description = artistSeparators.map { "\"$it\"" }.joinToString("  "),
            icon = { Icon(painterResource(R.drawable.artist), null) },
            onClick = { showArtistSeparatorsDialog = true }
        )

        PreferenceEntry(
            title = { Text(stringResource(R.string.manage_playlist_tags)) },
            description = stringResource(R.string.manage_playlist_tags_desc),
            icon = { Icon(painterResource(R.drawable.style), null) },
            onClick = { showTagsManagementDialog = true }
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.external_downloader)) },
            description = stringResource(R.string.external_downloader_desc),
            icon = { Icon(painterResource(R.drawable.download), null) },
            checked = externalDownloaderEnabled,
            onCheckedChange = onExternalDownloaderEnabledChange
        )

        PreferenceEntry(
            title = { Text(stringResource(R.string.external_downloader_package)) },
            description = externalDownloaderPackage.ifEmpty { stringResource(R.string.external_downloader_package_desc) },
            icon = { Icon(painterResource(R.drawable.integration), null) },
            onClick = { showExternalDownloaderPackageDialog = true },
            isEnabled = externalDownloaderEnabled
        )

        PreferenceGroupTitle(
            title = stringResource(R.string.settings_section_player_content)
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.play_explicit_content)) },
            description = stringResource(R.string.play_explicit_content_description),
            icon = { Icon(painterResource(R.drawable.explicit), null) },
            checked = playExplicitContent,
            onCheckedChange = onPlayExplicitContentChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.combine_liked_songs)) },
            description = stringResource(R.string.combine_liked_songs_description),
            icon = { Icon(painterResource(R.drawable.favorite), null) },
            checked = combineLikedSongs,
            onCheckedChange = onCombineLikedSongsChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.radio_audio_only)) },
            description = stringResource(R.string.radio_audio_only_description),
            icon = { Icon(painterResource(R.drawable.radio), null) },
            checked = radioAudioOnly,
            onCheckedChange = onRadioAudioOnlyChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.play_video_instead_of_audio)) },
            description = stringResource(R.string.play_video_instead_of_audio_description),
            icon = { Icon(painterResource(R.drawable.video), null) },
            checked = playVideoInsteadOfAudio,
            onCheckedChange = onPlayVideoInsteadOfAudioChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.auto_download_liked_songs)) },
            description = stringResource(R.string.auto_download_liked_songs_description),
            icon = { Icon(painterResource(R.drawable.download), null) },
            checked = autoDownloadLikedSongs,
            onCheckedChange = onAutoDownloadLikedSongsChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.keep_youtube_playlist_offline)) },
            description = stringResource(R.string.keep_youtube_playlist_offline_description),
            icon = { Icon(painterResource(R.drawable.offline), null) },
            checked = keepYouTubePlaylistOffline,
            onCheckedChange = onKeepYouTubePlaylistOfflineChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.sync_follow_to_youtube)) },
            description = stringResource(R.string.sync_follow_to_youtube_description),
            icon = { Icon(painterResource(R.drawable.sync), null) },
            checked = syncFollowToYouTube,
            onCheckedChange = onSyncFollowToYouTubeChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.send_listening_data_to_google)) },
            icon = { Icon(painterResource(R.drawable.google), null) },
            checked = sendListeningDataToGoogle,
            onCheckedChange = onSendListeningDataToGoogleChange
        )

        PreferenceGroupTitle(
            title = stringResource(R.string.misc)
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.crossfade_dj_mode)) },
            icon = { Icon(painterResource(R.drawable.dj), null) },
            checked = crossfadeDjMode,
            onCheckedChange = onCrossfadeDjModeChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.crossfade_skip_album)) },
            icon = { Icon(painterResource(R.drawable.skip_next), null) },
            checked = crossfadeSkipAlbum,
            onCheckedChange = onCrossfadeSkipAlbumChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.save_playback_state)) },
            description = stringResource(R.string.save_playback_state_description),
            icon = { Icon(painterResource(R.drawable.shuffle), null) },
            checked = savePlaybackState,
            onCheckedChange = onSavePlaybackStateChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.save_last_played)) },
            description = stringResource(R.string.save_last_played_subtitle),
            icon = { Icon(painterResource(R.drawable.history), null) },
            checked = saveLastPlayed,
            onCheckedChange = onSaveLastPlayedChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.kill_service_on_exit)) },
            description = stringResource(R.string.kill_service_on_exit_description),
            icon = { Icon(painterResource(R.drawable.close), null) },
            checked = killServiceOnExit,
            onCheckedChange = onKillServiceOnExitChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.keep_service_alive)) },
            description = stringResource(R.string.keep_service_alive_description),
            icon = { Icon(painterResource(R.drawable.notification), null) },
            checked = keepServiceAlive,
            onCheckedChange = onKeepServiceAliveChange
        )
    }

    TopAppBar(
        title = { Text(stringResource(R.string.player_and_audio)) },
        navigationIcon = {
            IconButton(
                onClick = navController::navigateUp,
                onLongClick = navController::backToMain
            ) {
                Icon(
                    painterResource(R.drawable.arrow_back),
                    contentDescription = null
                )
            }
        }
    )
}
