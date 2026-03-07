package com.multiplatform.book.viewmodel

import com.multiplatform.book.createBookRepository
import com.multiplatform.book.model.Book
import com.multiplatform.book.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val repository: BookRepository = createBookRepository()
): PlatformViewModel() {

    val book: StateFlow<Book?>
        field = MutableStateFlow(null)

    suspend fun loadBook(bookId: Int){
      platformViewModelScope.launch {
          book.value = repository.getBookDetail(bookId = bookId)
      }
    }
}
