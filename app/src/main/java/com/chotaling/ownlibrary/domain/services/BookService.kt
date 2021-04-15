package com.chotaling.ownlibrary.domain.services

import android.content.Context
import com.chotaling.ownlibrary.domain.RealmConfig
import com.chotaling.ownlibrary.domain.models.Book
import com.chotaling.ownlibrary.domain.models.Location
import com.chotaling.ownlibrary.infrastructure.dto.Google.GoogleBookDto
import com.chotaling.ownlibrary.infrastructure.repositories.GoogleBooksRepository
import io.realm.RealmResults
import io.realm.kotlin.where

class BookService()
{

    private val googleLookupService : GoogleBooksRepository = GoogleBooksRepository()
    private var realmConfig : RealmConfig = RealmConfig()

    fun addBook(book : Book)
    {
        realmConfig.getInstance().executeTransactionAsync() { realm ->
            realm.insert(book)
        }
    }

    fun removeBook(book : Book)
    {
        realmConfig.getInstance().executeTransactionAsync { realm ->
            book.deleteFromRealm()
        }
    }

    fun getBookByTitle(title : String) : Set<Book>?
    {
        val books = realmConfig.getInstance().where<Book>().equalTo("author", title).findAll()
        return books.toSet()
    }

    fun getBooksByAuther(author : String) : Set<Book>?
    {
        val books = realmConfig.getInstance().where<Book>().equalTo("author", author).findAll()
        return books.toSet()
    }

    fun getBookByIsbn(isbn : String) : Book?
    {
        val book = realmConfig.getInstance().where<Book>().equalTo("isbn", isbn).findFirst()
        return book
    }

    fun getAllBooks() : RealmResults<Book>?
    {
        return realmConfig.getInstance().where<Book>().findAllAsync()
    }

    fun updateBook(book : Book)
    {
        realmConfig.getInstance().executeTransactionAsync { realm ->
            val innerBook = realm.where<Book>().equalTo("id", book.id).findFirstAsync()
            innerBook?.author = book.author
            innerBook?.imageUrl = book.imageUrl
            innerBook?.title = book.title
        }
    }

    fun getBooksByLocation(location : Location) : Set<Book>?
    {
        val books = realmConfig.getInstance().where<Book>().equalTo("locationId", location.id).findAll()
        return books.toSet()
    }

    suspend fun lookupBook(isbn : String, author : String, title : String) : Array<GoogleBookDto>? {

        return googleLookupService.lookupBookByIsbn(isbn)
    }
}
