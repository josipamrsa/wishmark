package com.example.wishmark.feature_bookmark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.wishmark.feature_bookmark.presentation.add_bookmark.addBookmarkScreen
import com.example.wishmark.feature_bookmark.presentation.bookmarks.bookmarksScreen
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
                        bookmarksScreen(
                            navigator = navController,
                            onCreateNewBookmark = { navController.navigate(Screen.AddBookmarkScreen.route) }
                        )

                        addBookmarkScreen(
                            navigator = navController,
                            onNavigateToBookmarkScreen = { navController.navigateUp() }
                        )
                    }

                }
            }
        }
    }
}

