package com.multiplatform.book.viewmodel

import com.multiplatform.book.createBookRepository
import com.multiplatform.book.model.Book
import com.multiplatform.book.repository.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
                list?.let { callback(it) }
            }
        }
    }
}

