    package com.example.jotit

    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.viewModels
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.ViewModelProvider
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import androidx.room.Room
    import com.example.jotit.data.EntryDatabase
    import com.example.jotit.presentation.EntryScreen
    import com.example.jotit.presentation.EntryViewModel
    import com.example.jotit.presentation.VoidWriting
    import com.example.jotit.ui.theme.JotitTheme

    class MainActivity : ComponentActivity() {

        private val db by lazy {
            Room.databaseBuilder(
                applicationContext,
                EntryDatabase::class.java,
                "entries.db"
            ).build()
        }

        private val viewModel by viewModels<EntryViewModel>(
            factoryProducer = {
                object : ViewModelProvider.Factory{
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return EntryViewModel(db.dao) as T
                    }
                }
            }
        )

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                JotitTheme {
                    val state by viewModel.state.collectAsState()
                    val navController = rememberNavController()

                    // Define navigation destinations inside setContent
                    NavHost(navController, startDestination = "entry_screen") {
                        composable("entry_screen") {
                            EntryScreen(state = state, onEvent = viewModel::onEvent, navController = navController)
                        }
                        composable("void_screen") {
                            VoidWriting()
                        }
                    }
                }
            }
        }
    }

