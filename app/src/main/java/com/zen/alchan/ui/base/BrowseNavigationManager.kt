package com.zen.alchan.ui.base

interface BrowseNavigationManager {

    fun backStackCount(): Int
    fun pushBrowseScreenPage(page: Page, id: Int?)
    fun popBrowseScreenPage()

    enum class Page {
        MEDIA,
        CHARACTER,
        STAFF,
        USER,
        ANIME_MEDIA_LIST,
        MANGA_MEDIA_LIST,
        FOLLOWING,
        FOLLOWERS,
    }
}