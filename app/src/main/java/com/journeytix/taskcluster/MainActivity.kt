package com.journeytix.taskcluster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.journeytix.taskcluster.ui.navigation.NavGraph
import com.journeytix.taskcluster.ui.theme.TaskClusterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskClusterTheme {
                NavGraph(navController = rememberNavController())
            }
        }
    }
}
