/*
 * Dhun Project Original (2026)
 * Dhun
 * Licensed Under GPL-3.0 | see git history for contributors
 */



package com.arturo254.dhun.models

import com.arturo254.dhun.innertube.models.YTItem
import com.arturo254.dhun.db.entities.LocalItem

data class SimilarRecommendation(
    val title: LocalItem,
    val items: List<YTItem>,
)
