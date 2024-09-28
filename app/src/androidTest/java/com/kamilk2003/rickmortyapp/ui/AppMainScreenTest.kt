package com.kamilk2003.rickmortyapp.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kamilk2003.rickmortyapp.helpers.waitUntilTimeout
import com.kamilk2003.rickmortyapp.modules.main.AppMainScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/*
    In ideal scenario, viewModel with all its services should be mocked using proper library,
    but for purpose of this example app, I think that it's enough to present my implementation.
    Please also note, that this tests will pass only on the fresh app install.
*/
@RunWith(AndroidJUnit4::class)
class AppMainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            AppMainScreen()
        }
    }

    @Test
    fun test_switchTabs_allCharactersListDisplayed() {
        composeTestRule.onNodeWithTag("ALL").performClick()

        composeTestRule.onNodeWithTag("all_characters_list").assertIsDisplayed()
    }

    @Test
    fun test_switchTabs_emptyFavouritesList() {
        composeTestRule.onNodeWithTag("FAVOURITE").performClick()

        composeTestRule.onNodeWithTag("empty_characters_list").assertIsDisplayed()
    }

    @Test
    fun test_switchTabs_addedCharacterToFavourites() {
        composeTestRule.waitUntilTimeout(2000)

        composeTestRule.onNodeWithTag("change_favourite_state_1").performClick()

        composeTestRule.onNodeWithTag("FAVOURITE").performClick()

        composeTestRule.onNodeWithTag("character_1").assertIsDisplayed()

        deleteFromFavourites()
    }

    @Test
    fun test_switchTabs_removedCharacterFromFavourites() {
        composeTestRule.waitUntilTimeout(2000)

        composeTestRule.onNodeWithTag("change_favourite_state_1").performClick()

        composeTestRule.onNodeWithTag("FAVOURITE").performClick()

        composeTestRule.onNodeWithTag("character_1").assertIsDisplayed()

        composeTestRule.onNodeWithTag("change_favourite_state_1").performClick()

        composeTestRule.onNodeWithTag("empty_characters_list").assertIsDisplayed()
    }

    @Test
    fun test_snackbarDisplayedAfterAddingCharacterToFavourites() {
        composeTestRule.waitUntilTimeout(2000)

        composeTestRule.onNodeWithTag("change_favourite_state_1").performClick()

        composeTestRule.onNodeWithTag("info_snackbar").assertIsDisplayed()

        deleteFromFavourites()
    }

    @Test
    fun test_snackbarDisplayedAfterRemovingCharacterFromFavourites() {
        composeTestRule.waitUntilTimeout(2000)

        composeTestRule.onNodeWithTag("change_favourite_state_1").performClick()

        composeTestRule.onNodeWithTag("FAVOURITE").performClick()

        composeTestRule.onNodeWithTag("change_favourite_state_1").performClick()

        composeTestRule.onNodeWithTag("info_snackbar").assertIsDisplayed()
    }

    private fun deleteFromFavourites() {
        composeTestRule.onNodeWithTag("FAVOURITE").performClick()
        composeTestRule.onNodeWithTag("change_favourite_state_1").performClick()
    }
}