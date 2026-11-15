package com.rotor.feature.menu.model

import androidx.annotation.StringRes

/**
 * A node in the OS-style menu tree. A node with [children] is a submenu (shown with ›); a leaf is
 * an action or a content screen that later milestones attach behaviour to.
 */
data class MenuNode(
    val id: String,
    @param:StringRes val titleRes: Int,
    val children: List<MenuNode> = emptyList(),
) {
    val hasChildren: Boolean get() = children.isNotEmpty()
}
