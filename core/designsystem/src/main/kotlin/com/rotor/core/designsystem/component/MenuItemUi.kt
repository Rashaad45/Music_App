package com.rotor.core.designsystem.component

import androidx.compose.runtime.Immutable

/** Display data for one [MenuRow]; strings are already resolved. */
@Immutable
data class MenuItemUi(
    val id: String,
    val label: String,
    val secondary: String? = null,
    val hasChildren: Boolean = false,
)
