package com.rotor.core.designsystem.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import kotlin.math.max
import kotlin.math.min

private const val LUMINANCE_OFFSET = 0.05f

/** WCAG 2.x contrast ratio between two opaque colours, from 1.0 to 21.0. */
fun contrastRatio(
    a: Color,
    b: Color,
): Float {
    val la = a.luminance()
    val lb = b.luminance()
    return (max(la, lb) + LUMINANCE_OFFSET) / (min(la, lb) + LUMINANCE_OFFSET)
}
