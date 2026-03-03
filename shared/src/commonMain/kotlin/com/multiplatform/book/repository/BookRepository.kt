package com.multiplatform.book.repository

import com.multiplatform.book.model.Book

interface BookRepository {
    suspend fun getBooks() : List<Book>
    suspend fun getBookDetail(bookId: Int) : Book
}