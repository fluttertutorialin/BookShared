package com.multiplatform.book.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multiplatform.book.createBookRepository
import com.multiplatform.book.model.Book
import com.multiplatform.book.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BooksViewModel(
    private val repository: BookRepository = createBookRepository()
): PlatformViewModel() {

    val book: StateFlow<List<Book>?>
        field = MutableStateFlow(emptyList())

    init {
        loadBook()
    }

    fun loadBook() {
      platformViewModelScope.launch {
          book.value = repository.getBooks()
      }
    }

    //Sometime platform specific developer not understand code so write platform code
    fun observeBooks(callback: (List<Book>) -> Unit) {
        platformViewModelScope.launch {
            book.collect { list ->
                callback(list)
            }
        }
    }
}
