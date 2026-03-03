package com.multiplatform.book.repository

import com.multiplatform.book.model.Book
import kotlinx.coroutines.delay

class BookRepositoryImpl: BookRepository {
    private val books = listOf(
        Book(1,
            "Kotlin Multiplatform",
            "Kotlin Multiplatform is a technology for reusing up to 100% of your code across Android, iOS, web, and desktop, with Compose Multiplatform for shared UIs.",
            ""
            )
    )

    override suspend fun getBooks(): List<Book> {
        delay(400)
        return books
    }

    override suspend fun getBookDetail(bookId: Int): Book {
        delay(300)
        return  books.firstOrNull {it.id == bookId} ?: throw IllegalStateException("Book id $bookId does not exist")
    }
}