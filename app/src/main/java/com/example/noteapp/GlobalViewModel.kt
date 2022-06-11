package com.example.noteapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GlobalViewModel: ViewModel() {

    private var _notes: MutableLiveData<Map<Int, Note>> = MutableLiveData(mutableMapOf<Int, Note>())
    val notes: LiveData<Map<Int, Note>> = _notes

    fun setTitle(key: Int, title: String) {
        _notes.value?.get(key)?.title = title;
    }

    fun setContent(key: Int, content: String) {
        _notes.value?.get(key)?.content = content;
    }

}