package com.example.jotit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jotit.data.Entry
import com.example.jotit.data.EntryDao
import com.example.jotit.data.EntryEvent
import com.example.jotit.data.EntryState
import com.example.jotit.data.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class EntryViewModel(
    private val dao: EntryDao
):ViewModel() {
    private val _sortType = MutableStateFlow(SortType.DATE)
    private val _entries = _sortType
        .flatMapLatest { sortType->
            when (sortType) {
                SortType.DATE -> dao.getEntriesOrderedByDate()
                else -> dao.getEntriesOrderedByDate() // Default to sorting by date
            }
        }
        .stateIn(viewModelScope,SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow((
            EntryState(
                date = Date(), // Provide a valid date here
                gratitude = "",
                todayGreat = "",
                amazingThings = "",
                betterThings = "",
                isAddingEntry = false,
                sortType = SortType.DATE // Set default sort type
            )
            ))
    val state = combine(_state, _sortType, _entries){
        state, sortType, entries ->
            state.copy(
                entries =  entries,
                sortType = sortType
            )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EntryState())
    fun onEvent(event: EntryEvent){
        when(event){
            is EntryEvent.DeleteEntry -> {
                viewModelScope.launch {
                    dao.deleteEntry(event.entry)
                }
            }

            EntryEvent.HideDialog -> {
                _state.update{
                    it.copy(
                        isAddingEntry = false
                    )
                }
            }

            EntryEvent.SaveEvent -> {
                val date = state.value.date
                val gratitude = state.value.gratitude
                val todayGreat  = state.value.todayGreat
                val amazingThings = state.value.amazingThings
                val betterThings = state.value.betterThings

                if( gratitude.isBlank() || todayGreat.isBlank() || amazingThings.isBlank() || betterThings.isBlank()){
                    return
                }

                val entry = Entry(
                    gratitude = gratitude,
                    todayGreat = todayGreat,
                    amazingThings = amazingThings,
                    betterThings =  betterThings
                )

                viewModelScope.launch {
                    dao.upsertEntry(entry)
                }

                _state.update { it.copy(
                    isAddingEntry = false,
                    gratitude = "",
                    todayGreat = "",
                    amazingThings = "",
                    betterThings = ""
                ) }
            }

            is EntryEvent.SetAmazingThings -> {
                _state.update { it.copy(
                    amazingThings = event.amazingThings
                ) }
            }

            is EntryEvent.SetBetterThings ->  {
                _state.update { it.copy(
                    betterThings = event.setBetterThings
                ) }
            }

            is EntryEvent.SetDate -> {
                _state.update { it.copy(
                    date = event.date
                ) }
            }

            is EntryEvent.SetGratitude -> {
                _state.update { it.copy(
                    gratitude = event.gratitude
                ) }
            }

            is EntryEvent.SetTodayGreat -> {
                _state.update { it.copy(
                    todayGreat = event.todayGreat
                )}
            }

            EntryEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingEntry = true
                )   }
            }

            is EntryEvent.SortEntry -> {
                _sortType.value = event.sortType

            }
        }
    }
}