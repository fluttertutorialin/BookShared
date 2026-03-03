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
    private val _book = MutableStateFlow<List<Book>?>(null)
    val book: StateFlow<List<Book>?> = _book.asStateFlow()

    init {
        loadBook()
    }

    fun loadBook() {
      platformViewModelScope.launch {
          _book.value = repository.getBooks()
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

/*inline fun <T> NetworkResult<T>.handle(
    onSuccess: (T) -> Unit,
    onError: (String?) -> Unit,
    onLoading: () -> Unit = {}
) {
    when (this) {
        is NetworkResult.Success -> onSuccess(data)
        is NetworkResult.Error -> onError(message)
        is NetworkResult.Loading -> onLoading()
    }
}

sealed class NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error<T>(val message: String?) : NetworkResult<T>()
    object Loading : NetworkResult<Nothing>() // Fixed: Use object for memory efficiency
}*/

