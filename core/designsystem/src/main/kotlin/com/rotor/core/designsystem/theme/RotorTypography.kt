package com.rotor.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rotor.core.designsystem.R

/** Inter (OFL) for UI text. */
val InterFamily =
    FontFamily(
        Font(R.font.inter_regular, FontWeight.Normal),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_semibold, FontWeight.SemiBold),
        Font(R.font.inter_bold, FontWeight.Bold),
    )

/** JetBrains Mono (OFL) for numerals: times, counts, track numbers. */
val MonoFamily =
    FontFamily(
        Font(R.font.jetbrainsmono_regular, FontWeight.Normal),
        Font(R.font.jetbrainsmono_medium, FontWeight.Medium),
    )

@Immutable
data class RotorTypography(
    val header: TextStyle,
    val rowLabel: TextStyle,
    val rowSecondary: TextStyle,
    val title: TextStyle,
    val body: TextStyle,
    val numeral: TextStyle,
)

val DefaultRotorTypography =
    RotorTypography(
        header = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.SemiBold, fontSize = 15.sp),
        rowLabel = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Medium, fontSize = 18.sp),
        rowSecondary = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp),
        title = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Bold, fontSize = 22.sp),
        body = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp),
        numeral = TextStyle(fontFamily = MonoFamily, fontWeight = FontWeight.Medium, fontSize = 13.sp),
    )
