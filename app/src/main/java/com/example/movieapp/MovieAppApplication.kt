package com.example.movieapp

import android.app.Application
import com.example.movieapp.data.AppContainer
import com.example.movieapp.data.MovieAppContainer

class MovieAppApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = MovieAppContainer()
    }
}