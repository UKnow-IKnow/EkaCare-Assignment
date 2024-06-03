package com.example.ekacareassignment

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.ekacareassignment.database.InformationDb
import com.example.ekacareassignment.repository.InformationRepository
import com.example.ekacareassignment.ui.theme.EkaCareAssignmentTheme
import com.example.ekacareassignment.ui.theme.screen.InformationScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = InformationDb.getDatabase(this)
        val repository = InformationRepository(database.informationDao())
        val viewModel = InformationViewModel(repository)

        setContent {
            EkaCareAssignmentTheme {
                InformationScreen(viewModel = viewModel)
            }
        }
    }
}

