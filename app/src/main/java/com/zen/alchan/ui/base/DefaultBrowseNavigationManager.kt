package com.zen.alchan.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.zen.alchan.R
import com.zen.alchan.helper.enums.MediaType
import com.zen.alchan.helper.utils.TimeUtil
import com.zen.alchan.ui.character.CharacterFragment
import com.zen.alchan.ui.follow.FollowFragment
import com.zen.alchan.ui.media.MediaFragment
import com.zen.alchan.ui.medialist.MediaListFragment
import com.zen.alchan.ui.profile.ProfileFragment
import com.zen.alchan.ui.staff.StaffFragment

class DefaultBrowseNavigationManager(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val layout: FragmentContainerView
) : BrowseNavigationManager {

    private fun navigateToMedia(mediaId: Int) {
        swapPage(MediaFragment.newInstance(mediaId))
    }

    private fun navigateToCharacter(characterId: Int) {
        swapPage(CharacterFragment.newInstance(characterId))
    }

    private fun navigateToStaff(staffId: Int) {
        swapPage(StaffFragment.newInstance(staffId))
    }

    private fun navigateToUser(userId: Int) {
        swapPage(ProfileFragment.newInstance(userId))
    }

    private fun navigateToAnimeMediaList(userId: Int) {
        swapPage(MediaListFragment.newInstance(MediaType.ANIME, userId))
    }

    private fun navigateToMangaMediaList(userId: Int) {
        swapPage(MediaListFragment.newInstance(MediaType.MANGA, userId))
    }

    private fun navigateToFollowing(userId: Int) {
        swapPage(FollowFragment.newInstance(userId, true))
    }

    private fun navigateToFollowers(userId: Int) {
        swapPage(FollowFragment.newInstance(userId, false))
    }

    override fun backStackCount(): Int {
        return fragmentManager.backStackEntryCount
    }

    override fun pushBrowseScreenPage(page: BrowseNavigationManager.Page, id: Int?) {
        when (page) {
            BrowseNavigationManager.Page.MEDIA -> id?.let { navigateToMedia(id) }
            BrowseNavigationManager.Page.CHARACTER -> id?.let { navigateToCharacter(id) }
            BrowseNavigationManager.Page.USER -> id?.let { navigateToUser(id) }
            BrowseNavigationManager.Page.STAFF -> id?.let { navigateToStaff(id) }
            BrowseNavigationManager.Page.ANIME_MEDIA_LIST -> id?.let { navigateToAnimeMediaList(id) }
            BrowseNavigationManager.Page.MANGA_MEDIA_LIST -> id?.let { navigateToMangaMediaList(id) }
            BrowseNavigationManager.Page.FOLLOWING -> id?.let { navigateToFollowing(id) }
            BrowseNavigationManager.Page.FOLLOWERS -> id?.let { navigateToFollowers(id) }
        }
    }

    override fun popBrowseScreenPage() {
        fragmentManager.popBackStack()
    }

    private fun swapPage(fragment: Fragment, skipBackStack: Boolean = false, disableAnimation: Boolean = false) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (!disableAnimation) {
//            fragmentTransaction.setCustomAnimations(
//                R.anim.slide_in,
//                R.anim.fade_out,
//                R.anim.fade_in,
//                R.anim.slide_out
//            )
        }
        fragmentTransaction.replace(layout.id, fragment)
        if (!skipBackStack) {
            fragmentTransaction.addToBackStack(fragment.toString())
        }
        fragmentTransaction.commit()
    }
}