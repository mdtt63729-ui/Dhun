/*
 * Dhun Project Original (2026)
 * Dhun
 * Licensed Under GPL-3.0 | see git history for contributors
 */



package com.arturo254.dhun.ui.screens.settings

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arturo254.dhun.LocalPlayerAwareWindowInsets
import com.arturo254.dhun.R
import com.arturo254.dhun.constants.CanvasSource
import com.arturo254.dhun.constants.CanvasSourceKey
import com.arturo254.dhun.constants.ChipSortTypeKey
import com.arturo254.dhun.constants.DarkModeKey
import com.arturo254.dhun.constants.DefaultOpenTabKey
import com.arturo254.dhun.constants.DynamicThemeKey
import com.arturo254.dhun.constants.GridItemSize
import com.arturo254.dhun.constants.GridItemsSizeKey
import com.arturo254.dhun.constants.LibraryFilter
import com.arturo254.dhun.constants.LyricsClickKey
import com.arturo254.dhun.constants.LyricsScrollKey
import com.arturo254.dhun.constants.LyricsTextPositionKey
import com.arturo254.dhun.constants.PlayerDesignStyle
import com.arturo254.dhun.constants.PlayerDesignStyleKey
import com.arturo254.dhun.constants.UseNewMiniPlayerDesignKey
import com.arturo254.dhun.constants.PlayerBackgroundStyle
import com.arturo254.dhun.constants.PlayerBackgroundStyleKey
import com.arturo254.dhun.constants.PureBlackKey
import com.arturo254.dhun.constants.RandomThemeOnStartupKey
import com.arturo254.dhun.constants.UseSystemFontKey
import com.arturo254.dhun.constants.PlayerButtonsStyle
import com.arturo254.dhun.constants.PlayerButtonsStyleKey
import com.arturo254.dhun.constants.LyricsAnimationStyleKey
import com.arturo254.dhun.constants.LyricsAnimationStyle
import com.arturo254.dhun.constants.LyricsTextSizeKey
import com.arturo254.dhun.constants.LyricsLineSpacingKey
import com.arturo254.dhun.constants.SliderStyle
import com.arturo254.dhun.constants.SliderStyleKey
import com.arturo254.dhun.constants.SlimNavBarKey
import com.arturo254.dhun.constants.TranslucentBottomBarKey
import com.arturo254.dhun.constants.ShowLikedPlaylistKey
import com.arturo254.dhun.constants.ShowDownloadedPlaylistKey
import com.arturo254.dhun.constants.ShowHomeCategoryChipsKey
import com.arturo254.dhun.constants.ShowTopPlaylistKey
import com.arturo254.dhun.constants.ShowCachedPlaylistKey
import com.arturo254.dhun.constants.ShowLocalPlaylistKey
import com.arturo254.dhun.constants.ShowTagsInLibraryKey
import com.arturo254.dhun.constants.SwipeThumbnailKey
import com.arturo254.dhun.constants.SwipeSensitivityKey
import com.arturo254.dhun.constants.SwipeToSongKey
import com.arturo254.dhun.constants.HidePlayerThumbnailKey
import com.arturo254.dhun.constants.DhunCanvasKey
import com.arturo254.dhun.constants.ThumbnailCornerRadiusKey
import com.arturo254.dhun.constants.CropThumbnailToSquareKey
import com.arturo254.dhun.constants.DisableBlurKey
import com.arturo254.dhun.constants.EnableHapticFeedbackKey
import com.arturo254.dhun.constants.LiquidGlassNavBarKey
import com.arturo254.dhun.constants.EnableLiquidGlassKey
import com.arturo254.dhun.constants.PlayerFullscreenKey
import com.arturo254.dhun.constants.UseLyricsV2Key
import com.arturo254.dhun.ui.component.DefaultDialog
import com.arturo254.dhun.ui.component.EnumListPreference
import com.arturo254.dhun.ui.component.IconButton
import com.arturo254.dhun.ui.component.ListPreference
import com.arturo254.dhun.ui.component.PreferenceEntry
import com.arturo254.dhun.ui.component.PreferenceGroupTitle
import com.arturo254.dhun.ui.component.SwitchPreference
import com.arturo254.dhun.ui.component.ThumbnailCornerRadiusSelectorButton
import com.arturo254.dhun.ui.player.StyledPlaybackSlider
import com.arturo254.dhun.ui.utils.backToMain
import com.arturo254.dhun.constants.BackGradKey
import com.arturo254.dhun.constants.BottomGradKey
import com.arturo254.dhun.constants.CanvasColorKey
import com.arturo254.dhun.constants.CardColorKey
import com.arturo254.dhun.constants.CardGradKey
import com.arturo254.dhun.constants.SavedThemesKey
import com.arturo254.dhun.constants.UseDenseMiniKey
import com.arturo254.dhun.constants.EnableGestureKey
import com.arturo254.dhun.constants.VolumeGestureEnabledKey
import com.arturo254.dhun.constants.ColorHueKey
import com.arturo254.dhun.constants.MusicLanguageKey
import com.arturo254.dhun.constants.ChartLocationKey
import com.arturo254.dhun.constants.PlayerGradientTypeKey
import com.arturo254.dhun.constants.LyricsSyncOffsetMsKey
import com.arturo254.dhun.constants.SleepTimerDurationKey
import com.arturo254.dhun.constants.PlaybackSpeedKey
import com.arturo254.dhun.constants.FadeOutDurationKey
import com.arturo254.dhun.constants.AbRepeatKey
import com.arturo254.dhun.constants.UseLessDataImageKey
import com.arturo254.dhun.utils.rememberEnumPreference
import com.arturo254.dhun.utils.rememberPreference
import kotlin.math.roundToInt
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceSettings(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val (dynamicTheme, onDynamicThemeChange) = rememberPreference(
        DynamicThemeKey,
        defaultValue = true
    )
    val (randomThemeOnStartup, onRandomThemeOnStartupChange) = rememberPreference(
        RandomThemeOnStartupKey,
        defaultValue = false
    )
    val (darkMode, onDarkModeChange) = rememberEnumPreference(
        DarkModeKey,
        defaultValue = DarkMode.AUTO
    )
    val (enableLiquidGlass, onEnableLiquidGlassChange) = rememberPreference(
        EnableLiquidGlassKey,
        defaultValue = false
    )
    val (playerDesignStyle, onPlayerDesignStyleChange) = rememberEnumPreference(
        PlayerDesignStyleKey,
        defaultValue = PlayerDesignStyle.PIXEL
    )
    val (useNewMiniPlayerDesign, onUseNewMiniPlayerDesignChange) = rememberPreference(
        UseNewMiniPlayerDesignKey,
        defaultValue = true
    )
    val (useNewLibraryDesign, onUseNewLibraryDesignChange) = rememberPreference(
        key = com.arturo254.dhun.constants.UseNewLibraryDesignKey,
        defaultValue = false
    )
    val (hidePlayerThumbnail, onHidePlayerThumbnailChange) = rememberPreference(
        HidePlayerThumbnailKey,
        defaultValue = false
    )
    val (canvasSource, setCanvasSource) = rememberEnumPreference(
        key = CanvasSourceKey,
        defaultValue = CanvasSource.AUTO,
    )
    val (thumbnailCornerRadius, onThumbnailCornerRadiusChange) = rememberPreference(
        key = ThumbnailCornerRadiusKey,
        defaultValue = 16f // default dp
    )
    val (cropThumbnailToSquare, onCropThumbnailToSquareChange) = rememberPreference(
        CropThumbnailToSquareKey,
        defaultValue = false
    )
    val (playerBackground, onPlayerBackgroundChange) =
        rememberEnumPreference(
            PlayerBackgroundStyleKey,
            defaultValue = PlayerBackgroundStyle.DEFAULT,
        )
    val (pureBlack, onPureBlackChange) = rememberPreference(PureBlackKey, defaultValue = false)
    val (disableBlur, onDisableBlurChange) = rememberPreference(DisableBlurKey, defaultValue = true)
    val (useSystemFont, onUseSystemFontChange) = rememberPreference(UseSystemFontKey, defaultValue = false)
    val (defaultOpenTab, onDefaultOpenTabChange) = rememberEnumPreference(
        DefaultOpenTabKey,
        defaultValue = NavigationTab.HOME
    )
    val (playerButtonsStyle, onPlayerButtonsStyleChange) = rememberEnumPreference(
        PlayerButtonsStyleKey,
        defaultValue = PlayerButtonsStyle.DEFAULT
    )
    val (lyricsPosition, onLyricsPositionChange) = rememberEnumPreference(
        LyricsTextPositionKey,
        defaultValue = LyricsPosition.LEFT
    )
    val (lyricsAnimation, onLyricsAnimationChange) = rememberEnumPreference<LyricsAnimationStyle>(
    key = LyricsAnimationStyleKey,
    defaultValue = LyricsAnimationStyle.APPLE
    )
    val (lyricsClick, onLyricsClickChange) = rememberPreference(LyricsClickKey, defaultValue = true)
    val (lyricsScroll, onLyricsScrollChange) = rememberPreference(LyricsScrollKey, defaultValue = true)
    val (lyricsTextSize, onLyricsTextSizeChange) = rememberPreference(LyricsTextSizeKey, defaultValue = 26f)
    val (lyricsLineSpacing, onLyricsLineSpacingChange) = rememberPreference(LyricsLineSpacingKey, defaultValue = 1.3f)
    val (useLyricsV2, onUseLyricsV2Change) = rememberPreference(UseLyricsV2Key, defaultValue = false)

    val (sliderStyle, onSliderStyleChange) = rememberEnumPreference(
        SliderStyleKey,
        defaultValue = SliderStyle.Standard
    )
    val (swipeThumbnail, onSwipeThumbnailChange) = rememberPreference(
        SwipeThumbnailKey,
        defaultValue = true
    )
    val (swipeSensitivity, onSwipeSensitivityChange) = rememberPreference(
        SwipeSensitivityKey,
        defaultValue = 0.73f
    )
    val (gridItemSize, onGridItemSizeChange) = rememberEnumPreference(
        GridItemsSizeKey,
        defaultValue = GridItemSize.SMALL
    )

    val (slimNav, onSlimNavChange) = rememberPreference(
        SlimNavBarKey,
        defaultValue = false
    )

    val (translucentBottomBar, onTranslucentBottomBarChange) = rememberPreference(
        TranslucentBottomBarKey,
        defaultValue = false
    )

    val (swipeToSong, onSwipeToSongChange) = rememberPreference(
        SwipeToSongKey,
        defaultValue = false
    )

    val (showLikedPlaylist, onShowLikedPlaylistChange) = rememberPreference(
        ShowLikedPlaylistKey,
        defaultValue = true
    )
    val (showDownloadedPlaylist, onShowDownloadedPlaylistChange) = rememberPreference(
        ShowDownloadedPlaylistKey,
        defaultValue = true
    )
    val (showTopPlaylist, onShowTopPlaylistChange) = rememberPreference(
        ShowTopPlaylistKey,
        defaultValue = true
    )
    val (showCachedPlaylist, onShowCachedPlaylistChange) = rememberPreference(
        ShowCachedPlaylistKey,
        defaultValue = true
    )
    val (showLocalPlaylist, onShowLocalPlaylistChange) = rememberPreference(
        ShowLocalPlaylistKey,
        defaultValue = true
    )
    val (showTagsInLibrary, onShowTagsInLibraryChange) = rememberPreference(
        ShowTagsInLibraryKey,
        defaultValue = true
    )
    val (showHomeCategoryChips, onShowHomeCategoryChipsChange) = rememberPreference(
        ShowHomeCategoryChipsKey,
        defaultValue = true
    )

    val availableBackgroundStyles = PlayerBackgroundStyle.entries.filter {
        it != PlayerBackgroundStyle.BLUR || Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    }

    val (playerFullscreen, onPlayerFullscreenChange) = rememberPreference(
        PlayerFullscreenKey,
        defaultValue = false
    )

    val (hapticEnabled, onHapticEnabledChange) = rememberPreference(
        EnableHapticFeedbackKey,
        defaultValue = true
    )

    // BlackHole-ported theme settings
    val (backGrad, onBackGradChange) = rememberPreference(BackGradKey, defaultValue = 2)
    val (cardGrad, onCardGradChange) = rememberPreference(CardGradKey, defaultValue = 4)
    val (bottomGrad, onBottomGradChange) = rememberPreference(BottomGradKey, defaultValue = 3)
    val (canvasColor, onCanvasColorChange) = rememberPreference(CanvasColorKey, defaultValue = "Grey")
    val (cardColor, onCardColorChange) = rememberPreference(CardColorKey, defaultValue = "Grey900")
    val (useDenseMini, onUseDenseMiniChange) = rememberPreference(UseDenseMiniKey, defaultValue = false)
    val (enableGesture, onEnableGestureChange) = rememberPreference(EnableGestureKey, defaultValue = true)
    val (volumeGestureEnabled, onVolumeGestureEnabledChange) = rememberPreference(VolumeGestureEnabledKey, defaultValue = false)
    val (useLessDataImage, onUseLessDataImageChange) = rememberPreference(UseLessDataImageKey, defaultValue = false)

    // Additional BlackHole + new feature settings
    val (colorHue, onColorHueChange) = rememberPreference(ColorHueKey, defaultValue = 400)
    val (musicLanguage, onMusicLanguageChange) = rememberPreference(MusicLanguageKey, defaultValue = "Hindi")
    val (chartLocation, onChartLocationChange) = rememberPreference(ChartLocationKey, defaultValue = "India")
    val (playerGradientType, onPlayerGradientTypeChange) = rememberPreference(PlayerGradientTypeKey, defaultValue = "halfDark")
    val (lyricsSyncOffsetMs, onLyricsSyncOffsetMsChange) = rememberPreference(LyricsSyncOffsetMsKey, defaultValue = 0)
    val (sleepTimerDuration, onSleepTimerDurationChange) = rememberPreference(SleepTimerDurationKey, defaultValue = 0)
    val (playbackSpeed, onPlaybackSpeedChange) = rememberPreference(PlaybackSpeedKey, defaultValue = 1.0f)
    val (fadeOutDuration, onFadeOutDurationChange) = rememberPreference(FadeOutDurationKey, defaultValue = 0f)
    val (abRepeat, onAbRepeatChange) = rememberPreference(AbRepeatKey, defaultValue = "")
    val (savedThemes, onSavedThemesChange) = rememberPreference(SavedThemesKey, defaultValue = "")


    val isSystemInDarkTheme = isSystemInDarkTheme()
    val useDarkTheme =
        remember(darkMode, isSystemInDarkTheme) {
            if (darkMode == DarkMode.AUTO) isSystemInDarkTheme else darkMode == DarkMode.ON
        }

    val (defaultChip, onDefaultChipChange) = rememberEnumPreference(
        key = ChipSortTypeKey,
        defaultValue = LibraryFilter.LIBRARY
    )

    var showSliderOptionDialog by rememberSaveable {
        mutableStateOf(false)
    }

    if (showSliderOptionDialog) {
        val sliderStyles = remember {
            listOf(
                SliderStyle.Standard,
                SliderStyle.Wavy,
                SliderStyle.Thick,
                SliderStyle.Circular,
                SliderStyle.Simple
            )
        }
        DefaultDialog(
            buttons = {
                TextButton(
                    onClick = { showSliderOptionDialog = false }
                ) {
                    Text(text = stringResource(android.R.string.cancel))
                }
            },
            onDismiss = {
                showSliderOptionDialog = false
            }
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                sliderStyles.chunked(3).forEach { styleRow ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        styleRow.forEach { style ->
                            SliderStyleOptionCard(
                                sliderStyle = style,
                                selected = sliderStyle == style,
                                onClick = {
                                    onSliderStyleChange(style)
                                    showSliderOptionDialog = false
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        repeat(3 - styleRow.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }

    Column(
        Modifier
            .windowInsetsPadding(LocalPlayerAwareWindowInsets.current)
            .verticalScroll(rememberScrollState()),
    ) {
        PreferenceGroupTitle(
            title = stringResource(R.string.theme),
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.enable_dynamic_theme)) },
            icon = { Icon(painterResource(R.drawable.palette), null) },
            checked = dynamicTheme,
            onCheckedChange = onDynamicThemeChange,
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.player_fullscreen)) },
            icon = { Icon(painterResource(R.drawable.fullscreen), null) },
            checked = playerFullscreen,
            onCheckedChange = onPlayerFullscreenChange,
        )

        SwitchPreference(
            title = { Text("Haptic feedback") },
            icon = { Icon(painterResource(R.drawable.haptic), null) },
            checked = hapticEnabled,
            onCheckedChange = onHapticEnabledChange,
        )

        AnimatedVisibility(visible = !dynamicTheme || Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            SwitchPreference(
                title = { Text(stringResource(R.string.random_theme_on_startup)) },
                description = stringResource(R.string.random_theme_on_startup_desc),
                icon = { Icon(painterResource(R.drawable.shuffle), null) },
                checked = randomThemeOnStartup,
                onCheckedChange = onRandomThemeOnStartupChange,
            )
        }

        AnimatedVisibility(visible = !dynamicTheme || Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            PreferenceEntry(
                title = { Text(stringResource(R.string.color_palette)) },
                description = stringResource(R.string.customize_theme_colors),
                icon = { Icon(painterResource(R.drawable.format_paint), null) },
                onClick = { navController.navigate("settings/appearance/palette_picker") }
            )
        }

        SwitchPreference(
            title = { Text(stringResource(R.string.enable_liquid_glass)) },
            description = stringResource(R.string.enable_liquid_glass_desc),
            icon = { Icon(painterResource(R.drawable.palette), null) },
            checked = enableLiquidGlass,
            onCheckedChange = { newValue ->
                onEnableLiquidGlassChange(newValue)
                if (newValue) {
                    onDarkModeChange(DarkMode.ON)
                }
            },
        )

        EnumListPreference(
            title = { Text(stringResource(R.string.dark_theme)) },
            icon = { Icon(painterResource(R.drawable.dark_mode), null) },
            selectedValue = if (enableLiquidGlass) DarkMode.ON else darkMode,
            onValueSelected = onDarkModeChange,
            valueText = {
                if (enableLiquidGlass) {
                    stringResource(R.string.dark_theme_on)
                } else {
                    when (it) {
                        DarkMode.ON -> stringResource(R.string.dark_theme_on)
                        DarkMode.OFF -> stringResource(R.string.dark_theme_off)
                        DarkMode.AUTO -> stringResource(R.string.dark_theme_follow_system)
                    }
                }
            },
            isEnabled = !enableLiquidGlass
        )

        AnimatedVisibility(useDarkTheme) {
            SwitchPreference(
                title = { Text(stringResource(R.string.pure_black)) },
                icon = { Icon(painterResource(R.drawable.contrast), null) },
                checked = pureBlack && useDarkTheme && !enableLiquidGlass,
                onCheckedChange = { newValue ->
                    if (useDarkTheme && !enableLiquidGlass) {
                        onPureBlackChange(newValue)
                    }
                },
                isEnabled = useDarkTheme && !enableLiquidGlass
            )
        }

        // Background Gradient picker (dark mode only) - BlackHole ported
        AnimatedVisibility(useDarkTheme && !enableLiquidGlass) {
            PreferenceEntry(
                title = { Text("Background Gradient") },
                description = "Darkness level: ${if (backGrad == 4) "AMOLED (Pure Black)" else "Level $backGrad"}",
                icon = { Icon(painterResource(R.drawable.gradient), null) },
                onClick = {
                    val next = (backGrad + 1) % 5
                    onBackGradChange(next)
                }
            )
        }

        // Card Gradient picker (dark mode only) - BlackHole ported
        AnimatedVisibility(useDarkTheme && !enableLiquidGlass) {
            PreferenceEntry(
                title = { Text("Card Gradient") },
                description = "Darkness level: ${if (cardGrad == 6) "AMOLED (Pure Black)" else "Level $cardGrad"}",
                icon = { Icon(painterResource(R.drawable.gradient), null) },
                onClick = {
                    val next = (cardGrad + 1) % 7
                    onCardGradChange(next)
                }
            )
        }

        // Bottom Gradient picker (dark mode only) - BlackHole ported
        AnimatedVisibility(useDarkTheme && !enableLiquidGlass) {
            PreferenceEntry(
                title = { Text("Bottom Gradient") },
                description = "Darkness level: ${if (bottomGrad == 4) "AMOLED (Pure Black)" else "Level $bottomGrad"}",
                icon = { Icon(painterResource(R.drawable.gradient), null) },
                onClick = {
                    val next = (bottomGrad + 1) % 5
                    onBottomGradChange(next)
                }
            )
        }

        // Use AMOLED preset - BlackHole ported
        AnimatedVisibility(useDarkTheme && !enableLiquidGlass) {
            PreferenceEntry(
                title = { Text("Use AMOLED") },
                description = "Apply full AMOLED preset (pure black everywhere)",
                icon = { Icon(painterResource(R.drawable.dark_mode), null) },
                onClick = {
                    onBackGradChange(4)
                    onCardGradChange(6)
                    onBottomGradChange(4)
                    onCanvasColorChange("Black")
                    onCardColorChange("Grey900")
                    onPureBlackChange(true)
                }
            )
        }

        // Canvas Color - BlackHole ported
        ListPreference(
            title = { Text("Canvas Color") },
            icon = { Icon(painterResource(R.drawable.palette), null) },
            selectedValue = canvasColor,
            values = listOf("Grey", "Black"),
            valueText = { it },
            onValueSelected = onCanvasColorChange,
        )

        // Card Color - BlackHole ported
        ListPreference(
            title = { Text("Card Color") },
            icon = { Icon(painterResource(R.drawable.palette), null) },
            selectedValue = cardColor,
            values = listOf("Grey800", "Grey850", "Grey900", "Black"),
            valueText = { it },
            onValueSelected = onCardColorChange,
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.disable_blur)) },
            description = stringResource(R.string.disable_blur_desc),
            icon = { Icon(painterResource(R.drawable.blur_off), null) },
            checked = disableBlur,
            onCheckedChange = onDisableBlurChange,
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.use_system_font)) },
            description = stringResource(R.string.use_system_font_desc),
            icon = { Icon(painterResource(R.drawable.text_fields), null) },
            checked = useSystemFont,
            onCheckedChange = onUseSystemFontChange,
        )

        PreferenceGroupTitle(
            title = stringResource(R.string.player),
        )

        EnumListPreference(
            title = { Text(stringResource(R.string.player_design_style)) },
            icon = { Icon(painterResource(R.drawable.palette), null) },
            selectedValue = playerDesignStyle,
            onValueSelected = onPlayerDesignStyleChange,
            valueText = {
                when (it) {
                    PlayerDesignStyle.PIXEL -> stringResource(R.string.player_design_pixel)
                    PlayerDesignStyle.V1 -> stringResource(R.string.player_design_v1)
                    PlayerDesignStyle.V2 -> stringResource(R.string.player_design_v2)
                    PlayerDesignStyle.V3 -> stringResource(R.string.player_design_v3)
                    PlayerDesignStyle.V4 -> stringResource(R.string.player_design_v4)
                    PlayerDesignStyle.V5 -> stringResource(R.string.player_design_v5)
                    PlayerDesignStyle.V6 -> stringResource(R.string.player_design_v6)
                    PlayerDesignStyle.V7 -> stringResource(R.string.player_design_v7)
                    PlayerDesignStyle.V8 -> stringResource(R.string.Apple_Music)
                    PlayerDesignStyle.SPOTIFY -> stringResource(R.string.player_design_spotify)
                }
            },
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.new_mini_player_design)) },
            icon = { Icon(painterResource(R.drawable.nav_bar), null) },
            checked = useNewMiniPlayerDesign,
            onCheckedChange = onUseNewMiniPlayerDesignChange,
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.new_library_design)) },
            description = stringResource(R.string.new_library_design_description),
            icon = { Icon(painterResource(R.drawable.grid_view), null) },
            checked = useNewLibraryDesign,
            onCheckedChange = onUseNewLibraryDesignChange,
        )

        EnumListPreference(
            title = { Text(stringResource(R.string.player_background_style)) },
            icon = { Icon(painterResource(R.drawable.gradient), null) },
            selectedValue = playerBackground,
            onValueSelected = onPlayerBackgroundChange,
            valueText = {
                when (it) {
                    PlayerBackgroundStyle.DEFAULT -> stringResource(R.string.follow_theme)
                    PlayerBackgroundStyle.GRADIENT -> stringResource(R.string.gradient)
                        PlayerBackgroundStyle.CUSTOM -> stringResource(R.string.custom)
                    PlayerBackgroundStyle.BLUR -> stringResource(R.string.player_background_blur)
                    PlayerBackgroundStyle.COLORING -> stringResource(R.string.coloring)
                    PlayerBackgroundStyle.BLUR_GRADIENT -> stringResource(R.string.blur_gradient)
                    PlayerBackgroundStyle.GLOW -> stringResource(R.string.glow)
                    PlayerBackgroundStyle.GLOW_ANIMATED -> "Glow Animated"
                    PlayerBackgroundStyle.SPOTIFY -> stringResource(R.string.player_background_spotify)
                }
            },
        )

        // When custom background is selected, show a direct link to customize it
        if (playerBackground == PlayerBackgroundStyle.CUSTOM) {
            PreferenceEntry(
                title = { Text(stringResource(R.string.customized_background)) },
                icon = { Icon(painterResource(R.drawable.image), null) },
                onClick = { navController.navigate("customize_background") }
            )
        }

        SwitchPreference(
            title = { Text(stringResource(R.string.hide_player_thumbnail)) },
            description = stringResource(R.string.hide_player_thumbnail_desc),
            icon = { Icon(painterResource(R.drawable.hide_image), null) },
            checked = hidePlayerThumbnail,
            onCheckedChange = onHidePlayerThumbnailChange
        )

        ListPreference(
            title = { Text("Canvas source") },
            icon = { Icon(painterResource(R.drawable.motion_photos_on), null) },
            selectedValue = canvasSource,
            values = CanvasSource.entries,
            valueText = { source ->
                when (source) {
                    CanvasSource.AUTO -> "Auto"
                    CanvasSource.APPLE_MUSIC -> "Apple Music"
                    CanvasSource.CUSTOM -> "Custom by Dhun"
                    CanvasSource.TIDAL -> "Tidal"
                }
            },
            onValueSelected = setCanvasSource,
        )
      

        ThumbnailCornerRadiusSelectorButton(
            modifier = Modifier.padding(16.dp),
            onRadiusSelected = { selectedRadius ->
                Timber.tag("Thumbnail").d("Radius Selector: $selectedRadius")
            }
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.crop_thumbnail_to_square)) },
            description = stringResource(R.string.crop_thumbnail_to_square_desc),
            icon = { Icon(painterResource(R.drawable.image), null) },
            checked = cropThumbnailToSquare,
            onCheckedChange = onCropThumbnailToSquareChange
        )


        EnumListPreference(
            title = { Text(stringResource(R.string.player_buttons_style)) },
            icon = { Icon(painterResource(R.drawable.palette), null) },
            selectedValue = playerButtonsStyle,
            onValueSelected = onPlayerButtonsStyleChange,
            valueText = {
                when (it) {
                    PlayerButtonsStyle.DEFAULT -> stringResource(R.string.default_style)
                    PlayerButtonsStyle.SECONDARY -> stringResource(R.string.secondary_color_style)
                }
            },
        )

        PreferenceEntry(
            title = { Text(stringResource(R.string.player_slider_style)) },
            description = sliderStyleLabel(sliderStyle),
            icon = { Icon(painterResource(R.drawable.sliders), null) },
            onClick = {
                showSliderOptionDialog = true
            },
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.enable_swipe_thumbnail)) },
            icon = { Icon(painterResource(R.drawable.swipe), null) },
            checked = swipeThumbnail,
            onCheckedChange = onSwipeThumbnailChange,
        )

        AnimatedVisibility(swipeThumbnail) {
            var showSensitivityDialog by rememberSaveable { mutableStateOf(false) }
            
            if (showSensitivityDialog) {
                var tempSensitivity by remember { mutableFloatStateOf(swipeSensitivity) }
                
                DefaultDialog(
                    onDismiss = { 
                        tempSensitivity = swipeSensitivity
                        showSensitivityDialog = false 
                    },
                    buttons = {
                        TextButton(
                            onClick = { 
                                tempSensitivity = 0.73f
                            }
                        ) {
                            Text(stringResource(R.string.reset))
                        }
                        
                        Spacer(modifier = Modifier.weight(1f))
                        
                        TextButton(
                            onClick = { 
                                tempSensitivity = swipeSensitivity
                                showSensitivityDialog = false 
                            }
                        ) {
                            Text(stringResource(android.R.string.cancel))
                        }
                        TextButton(
                            onClick = { 
                                onSwipeSensitivityChange(tempSensitivity)
                                showSensitivityDialog = false 
                            }
                        ) {
                            Text(stringResource(android.R.string.ok))
                        }
                    }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.swipe_sensitivity),
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
    
                        Text(
                            text = stringResource(R.string.sensitivity_percentage, (tempSensitivity * 100).roundToInt()),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
    
                        Slider(
                            value = tempSensitivity,
                            onValueChange = { tempSensitivity = it },
                            valueRange = 0f..1f,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            
            PreferenceEntry(
                title = { Text(stringResource(R.string.swipe_sensitivity)) },
                description = stringResource(R.string.sensitivity_percentage, (swipeSensitivity * 100).roundToInt()),
                icon = { Icon(painterResource(R.drawable.tune), null) },
                onClick = { showSensitivityDialog = true }
            )
        }

        PreferenceGroupTitle(
            title = stringResource(R.string.lyrics),
        )

        SwitchPreference(
            title = { Text("Lyrics V2 (Experimental)") },
            description = "Use the new fluid word-synced lyrics engine",
            icon = { Icon(painterResource(R.drawable.lyrics), null) },
            checked = useLyricsV2,
            onCheckedChange = onUseLyricsV2Change,
        )

        EnumListPreference(
            title = { Text(stringResource(R.string.lyrics_text_position)) },
            icon = { Icon(painterResource(R.drawable.lyrics), null) },
            selectedValue = lyricsPosition,
            onValueSelected = onLyricsPositionChange,
            valueText = {
                when (it) {
                    LyricsPosition.LEFT -> stringResource(R.string.left)
                    LyricsPosition.CENTER -> stringResource(R.string.center)
                    LyricsPosition.RIGHT -> stringResource(R.string.right)
                }
            },
        )

        EnumListPreference(
          title = { Text(stringResource(R.string.lyrics_animation_style)) },
          icon = { Icon(painterResource(R.drawable.animation), null) },
          selectedValue = lyricsAnimation,
          onValueSelected = onLyricsAnimationChange,
          valueText = {
              when (it) {
                  LyricsAnimationStyle.NONE -> stringResource(R.string.none)
                  LyricsAnimationStyle.FADE -> stringResource(R.string.fade)
                  LyricsAnimationStyle.GLOW -> stringResource(R.string.glow)
                  LyricsAnimationStyle.SLIDE -> stringResource(R.string.slide)
                  LyricsAnimationStyle.KARAOKE -> stringResource(R.string.karaoke)
                  LyricsAnimationStyle.APPLE -> stringResource(R.string.apple_music_style)
              }
          }
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.lyrics_click_change)) },
            icon = { Icon(painterResource(R.drawable.lyrics), null) },
            checked = lyricsClick,
            onCheckedChange = onLyricsClickChange,
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.lyrics_auto_scroll)) },
            icon = { Icon(painterResource(R.drawable.lyrics), null) },
            checked = lyricsScroll,
            onCheckedChange = onLyricsScrollChange,
        )

        var showLyricsTextSizeDialog by rememberSaveable { mutableStateOf(false) }
        
        if (showLyricsTextSizeDialog) {
            var tempTextSize by remember { mutableFloatStateOf(lyricsTextSize) }
            
            DefaultDialog(
                onDismiss = { 
                    tempTextSize = lyricsTextSize
                    showLyricsTextSizeDialog = false 
                },
                buttons = {
                    TextButton(
                        onClick = { 
                            tempTextSize = 24f
                        }
                    ) {
                        Text(stringResource(R.string.reset))
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    TextButton(
                        onClick = { 
                            tempTextSize = lyricsTextSize
                            showLyricsTextSizeDialog = false 
                        }
                    ) {
                        Text(stringResource(android.R.string.cancel))
                    }
                    TextButton(
                        onClick = { 
                            onLyricsTextSizeChange(tempTextSize)
                            showLyricsTextSizeDialog = false 
                        }
                    ) {
                        Text(stringResource(android.R.string.ok))
                    }
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.lyrics_text_size),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "${tempTextSize.roundToInt()} sp",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Slider(
                        value = tempTextSize,
                        onValueChange = { tempTextSize = it },
                        valueRange = 16f..36f,
                        steps = 19,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        
        PreferenceEntry(
            title = { Text(stringResource(R.string.lyrics_text_size)) },
            description = "${lyricsTextSize.roundToInt()} sp",
            icon = { Icon(painterResource(R.drawable.text_fields), null) },
            onClick = { showLyricsTextSizeDialog = true }
        )
        
        var showLyricsLineSpacingDialog by rememberSaveable { mutableStateOf(false) }
        
        if (showLyricsLineSpacingDialog) {
            var tempLineSpacing by remember { mutableFloatStateOf(lyricsLineSpacing) }
            
            DefaultDialog(
                onDismiss = { 
                    tempLineSpacing = lyricsLineSpacing
                    showLyricsLineSpacingDialog = false 
                },
                buttons = {
                    TextButton(
                        onClick = { 
                            tempLineSpacing = 1.3f
                        }
                    ) {
                        Text(stringResource(R.string.reset))
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    TextButton(
                        onClick = { 
                            tempLineSpacing = lyricsLineSpacing
                            showLyricsLineSpacingDialog = false 
                        }
                    ) {
                        Text(stringResource(android.R.string.cancel))
                    }
                    TextButton(
                        onClick = { 
                            onLyricsLineSpacingChange(tempLineSpacing)
                            showLyricsLineSpacingDialog = false 
                        }
                    ) {
                        Text(stringResource(android.R.string.ok))
                    }
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.lyrics_line_spacing),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "${String.format("%.1f", tempLineSpacing)}x",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Slider(
                        value = tempLineSpacing,
                        onValueChange = { tempLineSpacing = it },
                        valueRange = 1.0f..2.0f,
                        steps = 19,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        
        PreferenceEntry(
            title = { Text(stringResource(R.string.lyrics_line_spacing)) },
            description = "${String.format("%.1f", lyricsLineSpacing)}x",
            icon = { Icon(painterResource(R.drawable.text_fields), null) },
            onClick = { showLyricsLineSpacingDialog = true }
        )

        PreferenceGroupTitle(
            title = stringResource(R.string.misc),
        )

        EnumListPreference(
            title = { Text(stringResource(R.string.default_open_tab)) },
            icon = { Icon(painterResource(R.drawable.nav_bar), null) },
            selectedValue = defaultOpenTab,
            onValueSelected = onDefaultOpenTabChange,
            valueText = {
                when (it) {
                    NavigationTab.HOME -> stringResource(R.string.home)
                    NavigationTab.SEARCH -> stringResource(R.string.search)
                    NavigationTab.LIBRARY -> stringResource(R.string.filter_library)
                }
            },
        )

        ListPreference(
            title = { Text(stringResource(R.string.default_lib_chips)) },
            icon = { Icon(painterResource(R.drawable.tab), null) },
            selectedValue = defaultChip,
            values = listOf(
                LibraryFilter.LIBRARY, LibraryFilter.PLAYLISTS, LibraryFilter.SONGS,
                LibraryFilter.ALBUMS, LibraryFilter.ARTISTS
            ),
            valueText = {
                when (it) {
                    LibraryFilter.SONGS -> stringResource(R.string.songs)
                    LibraryFilter.ARTISTS -> stringResource(R.string.artists)
                    LibraryFilter.ALBUMS -> stringResource(R.string.albums)
                    LibraryFilter.PLAYLISTS -> stringResource(R.string.playlists)
                    LibraryFilter.LIBRARY -> stringResource(R.string.filter_library)
                    LibraryFilter.SPOTIFY -> stringResource(R.string.spotify)
                    LibraryFilter.ON_DEVICE -> stringResource(R.string.filter_on_device)
                }
            },
            onValueSelected = onDefaultChipChange,
        )


        PreferenceEntry(
            title = { Text("Always On Display") },
            description = stringResource(R.string.always_on_display_description),
            icon = { Icon(painterResource(R.drawable.dark_mode), null) },
            onClick = { navController.navigate("settings/appearance/always_on_display") }
        )

        PreferenceEntry(
            title = { Text("Widget Settings") },
            description = "Personaliza la apariencia del widget",
            icon = { Icon(painterResource(R.drawable.buttons), null) },
            onClick = { navController.navigate("settings/widget") }
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.show_home_category_chips)) },
            description = stringResource(R.string.show_home_category_chips_desc),
            icon = { Icon(painterResource(R.drawable.home_outlined), null) },
            checked = showHomeCategoryChips,
            onCheckedChange = onShowHomeCategoryChipsChange,
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.show_tags_in_library)) },
            description = stringResource(R.string.show_tags_in_library_desc),
            icon = { Icon(painterResource(R.drawable.filter_alt), null) },
            checked = showTagsInLibrary,
            onCheckedChange = onShowTagsInLibraryChange,
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.swipe_song_to_add)) },
            icon = { Icon(painterResource(R.drawable.swipe), null) },
            checked = swipeToSong,
            onCheckedChange = onSwipeToSongChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.slim_navbar)) },
            icon = { Icon(painterResource(R.drawable.nav_bar), null) },
            checked = slimNav,
            onCheckedChange = onSlimNavChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.translucent_bottom_bar)) },
            description = stringResource(R.string.translucent_bottom_bar_description),
            icon = { Icon(painterResource(R.drawable.opacity), null) },
            checked = translucentBottomBar,
            onCheckedChange = onTranslucentBottomBarChange
        )

        // Use Dense Mini - BlackHole ported
        SwitchPreference(
            title = { Text("Use Dense Mini") },
            description = "Compact mini player (Restart Required)",
            icon = { Icon(painterResource(R.drawable.nav_bar), null) },
            checked = useDenseMini,
            onCheckedChange = onUseDenseMiniChange
        )

        // Enable Gesture - BlackHole ported
        SwitchPreference(
            title = { Text("Enable Gesture") },
            description = "Swipe gestures on player screen",
            icon = { Icon(painterResource(R.drawable.swipe), null) },
            checked = enableGesture,
            onCheckedChange = onEnableGestureChange
        )

        // Volume Gesture Enabled - BlackHole ported
        SwitchPreference(
            title = { Text("Volume Gesture Enabled") },
            description = "Vertical swipe to adjust volume",
            icon = { Icon(painterResource(R.drawable.volume_up), null) },
            checked = volumeGestureEnabled,
            onCheckedChange = onVolumeGestureEnabledChange
        )

        // Use Less Data Image - BlackHole ported
        SwitchPreference(
            title = { Text("Use Less Data Image") },
            description = "Optimize images to save data",
            icon = { Icon(painterResource(R.drawable.image), null) },
            checked = useLessDataImage,
            onCheckedChange = onUseLessDataImageChange
        )

        EnumListPreference(
            title = { Text(stringResource(R.string.grid_cell_size)) },
            icon = { Icon(painterResource(R.drawable.grid_view), null) },
            selectedValue = gridItemSize,
            onValueSelected = onGridItemSizeChange,
            valueText = {
                when (it) {
                    GridItemSize.BIG -> stringResource(R.string.big)
                    GridItemSize.SMALL -> stringResource(R.string.small)
                }
            },
        )


        // === BlackHole-ported: Save Theme ===
        PreferenceGroupTitle(title = "Theme Management")

        PreferenceEntry(
            title = { Text("Save Current Theme") },
            description = "Save your current theme configuration",
            icon = { Icon(painterResource(R.drawable.palette), null) },
            onClick = {
                val currentTheme = mapOf(
                    "darkMode" to darkMode.name,
                    "pureBlack" to pureBlack,
                    "backGrad" to backGrad,
                    "cardGrad" to cardGrad,
                    "bottomGrad" to bottomGrad,
                    "canvasColor" to canvasColor,
                    "cardColor" to cardColor,
                    "colorHue" to colorHue
                ).toString()
                val existing = if (savedThemes.isEmpty()) "{}" else savedThemes
                val themeName = "Theme ${System.currentTimeMillis() % 10000}"
                onSavedThemesChange(existing + "|$themeName=$currentTheme")
            }
        )

        // === Color Hue Selector ===
        ListPreference(
            title = { Text("Color Hue") },
            icon = { Icon(painterResource(R.drawable.palette), null) },
            selectedValue = colorHue,
            values = listOf(100, 200, 400, 700),
            valueText = { it.toString() },
            onValueSelected = onColorHueChange,
        )

        // === Player Gradient Type ===
        ListPreference(
            title = { Text("Player Screen Background") },
            icon = { Icon(painterResource(R.drawable.gradient), null) },
            selectedValue = playerGradientType,
            values = listOf("simple", "halfLight", "halfDark", "fullLight", "fullDark", "fullMix"),
            valueText = { type ->
                when (type) {
                    "simple" -> "Simple"
                    "halfLight" -> "Half Light"
                    "halfDark" -> "Half Dark"
                    "fullLight" -> "Full Light"
                    "fullDark" -> "Full Dark"
                    "fullMix" -> "Full Mix"
                    else -> type
                }
            },
            onValueSelected = onPlayerGradientTypeChange,
        )

        // === Mini Buttons (reorderable concept) ===
        PreferenceEntry(
            title = { Text("Mini Buttons") },
            description = "Customize mini player buttons order",
            icon = { Icon(painterResource(R.drawable.nav_bar), null) },
            onClick = { }
        )

        // === Compact Notification Buttons ===
        PreferenceEntry(
            title = { Text("Compact Notification Buttons") },
            description = "Select up to 3 buttons for notification",
            icon = { Icon(painterResource(R.drawable.notifications), null) },
            onClick = { }
        )

        // === Blacklisted Home Sections ===
        PreferenceEntry(
            title = { Text("Blacklisted Home Sections") },
            description = "Hide specific sections from home screen",
            icon = { Icon(painterResource(R.drawable.filter_alt), null) },
            onClick = { }
        )

        // === Navigation Tabs ===
        PreferenceEntry(
            title = { Text("Navigation Tabs") },
            description = "Reorder and toggle navigation tabs",
            icon = { Icon(painterResource(R.drawable.nav_bar), null) },
            onClick = { }
        )

        // === Lyrics Sync Offset ===
        PreferenceEntry(
            title = { Text("Lyrics Sync Offset") },
            description = "Adjust lyrics timing by ${lyricsSyncOffsetMs}ms",
            icon = { Icon(painterResource(R.drawable.lyrics), null) },
            onClick = {
                onLyricsSyncOffsetMsChange(if (lyricsSyncOffsetMs >= 500) -500 else lyricsSyncOffsetMs + 100)
            }
        )

        // === Sleep Timer ===
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

        // === Playback Speed ===
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

        // === Fade Out on Pause/Stop ===
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

        // === A-B Repeat ===
        PreferenceEntry(
            title = { Text("A-B Repeat") },
            description = if (abRepeat.isEmpty()) "Not set" else "Active: $abRepeat",
            icon = { Icon(painterResource(R.drawable.repeat), null) },
            onClick = {
                onAbRepeatChange(if (abRepeat.isEmpty()) "active" else "")
            }
        )


        PreferenceGroupTitle(
            title = stringResource(R.string.auto_playlists)
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.show_liked_playlist)) },
            icon = { Icon(painterResource(R.drawable.favorite), null) },
            checked = showLikedPlaylist,
            onCheckedChange = onShowLikedPlaylistChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.show_downloaded_playlist)) },
            icon = { Icon(painterResource(R.drawable.offline), null) },
            checked = showDownloadedPlaylist,
            onCheckedChange = onShowDownloadedPlaylistChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.show_top_playlist)) },
            icon = { Icon(painterResource(R.drawable.trending_up), null) },
            checked = showTopPlaylist,
            onCheckedChange = onShowTopPlaylistChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.show_cached_playlist)) },
            icon = { Icon(painterResource(R.drawable.cached), null) },
            checked = showCachedPlaylist,
            onCheckedChange = onShowCachedPlaylistChange
        )

        SwitchPreference(
            title = { Text(stringResource(R.string.filter_on_device)) },
            icon = { Icon(painterResource(R.drawable.folder), null) },
            checked = showLocalPlaylist,
            onCheckedChange = onShowLocalPlaylistChange
        )
    }

    TopAppBar(
        title = { Text(stringResource(R.string.appearance)) },
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

@Composable
private fun SliderStyleOptionCard(
    sliderStyle: SliderStyle,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var sliderValue by remember {
        mutableFloatStateOf(0.5f)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .border(
                1.dp,
                if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        StyledPlaybackSlider(
            sliderStyle = sliderStyle,
            value = sliderValue,
            valueRange = 0f..1f,
            onValueChange = { sliderValue = it },
            onValueChangeFinished = {},
            activeColor = MaterialTheme.colorScheme.primary,
            isPlaying = true,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )

        Text(
            text = sliderStyleLabel(sliderStyle),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun sliderStyleLabel(sliderStyle: SliderStyle): String {
    return when (sliderStyle) {
        SliderStyle.Standard -> stringResource(R.string.slider_style_standard)
        SliderStyle.Wavy -> stringResource(R.string.slider_style_wavy)
        SliderStyle.Thick -> stringResource(R.string.slider_style_thick)
        SliderStyle.Circular -> stringResource(R.string.slider_style_circular)
        SliderStyle.Simple -> stringResource(R.string.slider_style_simple)
    }
}

enum class DarkMode {
    ON,
    OFF,
    AUTO,
}

enum class NavigationTab {
    HOME,
    SEARCH,
    LIBRARY,
}

enum class LyricsPosition {
    LEFT,
    CENTER,
    RIGHT,
}

enum class PlayerTextAlignment {
    SIDED,
    CENTER,
}
