package com.example.jotit

import java.util.Date

sealed interface EntryEvent {
    object SaveEvent: EntryEvent

    data class SetDate(val date: Date):EntryEvent
    data class SetGratitude(val gratitude: String): EntryEvent
    data class SetTodayGreat(val todayGreat: String): EntryEvent
    data class SetAmazingThings( val amazingThings:String): EntryEvent
    data class SetBetterThings(val setBetterThings: String): EntryEvent

    object ShowDialog: EntryEvent
    object HideDialog: EntryEvent

    data class SortEntry(val sortType:SortType): EntryEvent
    data class DeleteEntry(val entry:Entry): EntryEvent
}