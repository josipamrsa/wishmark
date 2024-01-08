package com.example.wishmark.feature_bookmark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wishmark.feature_bookmark.presentation.add_bookmark.AddEditBookmarkScreen
import com.example.wishmark.feature_bookmark.presentation.bookmarks.BookmarksScreen
import com.example.wishmark.feature_bookmark.presentation.util.Screen
import com.example.wishmark.ui.theme.WishmarkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WishmarkTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.BookmarksScreen.route
                    ) {
                        composable(
                            route = Screen.BookmarksScreen.route
                        ) {
                            BookmarksScreen(navController = navController)
                        }

                        composable(
                            route = "${Screen.AddBookmarkScreen.route}" +
                                    "?bookmarkId={bookmarkId}",
                            arguments = listOf(
                                navArgument(name = "bookmarkId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {

                            AddEditBookmarkScreen(navController = navController)
                        }
                    }

                }
            }
        }
    }
}

