package com.multiplatform.book

import com.multiplatform.book.repository.BookRepository
import com.multiplatform.book.repository.BookRepositoryImpl

fun createBookRepository() : BookRepository {
    return BookRepositoryImpl()
}