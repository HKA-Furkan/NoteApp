package com.example.noteapp

sealed class Route(val routeTemplate: String) {
    object OverviewRoute: Route("overview")
    object DetailsRoute: Route("details/note={note}") {
        fun createRoute(note: String) = "details/note=$note"
    }
}