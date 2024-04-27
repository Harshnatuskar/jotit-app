package com.example.jotit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jotit.data.EntryDao
import com.example.jotit.data.EntryEvent
import com.example.jotit.data.EntryState
import com.example.jotit.data.SortType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class EntryViewModel(
    private val dao: EntryDao
):ViewModel() {
    private val _sortType = MutableStateFlow(SortType.DATE)
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
            EntryEvent.SaveEvent -> TODO()
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