package com.example.wishmark.feature_bookmark.presentation.bookmarks

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wishmark.di.AppModule
import com.example.wishmark.feature_bookmark.MainActivity
import com.example.wishmark.feature_bookmark.presentation.util.Screen
import com.example.wishmark.ui.theme.WishmarkTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class BookmarksScreenKtTest {

    // Order applies rules by priority - 0 runs first, 1 runs second, etc

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            BookmarksScreen(state = BookmarksContract.State(), onEvent = {})
            /*val navController = rememberNavController()
            WishmarkTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.BookmarksScreen.route
                ) {
                    composable(
                        route = Screen.BookmarksScreen.route,

                        ) {
                        BookmarksScreen(state = BookmarksContract.State()) {

                        }
                    }
                }
            }*/
        }
    }

    // Can't use backticks :(
    @Test
    fun bookmarksScreen() {
        //composeRule.onNodeWithTag("BOOKMARK_DISPLAY")
            //.assertExists()

        //composeRule.onNodeWithTag("BOOKMARK_ITEM_TITLE")
            //.assertExists()
            //.assertIsDisplayed()

        composeRule.onNodeWithTag("INFO_HANDLER_CONTAINER")
            .assertExists()
            .assertIsDisplayed()
    }
}