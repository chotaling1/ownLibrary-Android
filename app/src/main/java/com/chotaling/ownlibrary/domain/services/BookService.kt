package com.chotaling.ownlibrary.domain.services

import com.chotaling.ownlibrary.domain.RealmConfig
import com.chotaling.ownlibrary.domain.models.Book
import com.chotaling.ownlibrary.domain.models.Location
import io.realm.kotlin.where

class BookService
{

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

    fun getAllBooks() : Set<Book>?
    {
        val books = realmConfig.getInstance().where<Book>().findAll()
        return books.toSet()
    }

    fun updateBook(book : Book)
    {
        realmConfig.getInstance().executeTransactionAsync { realm ->
            val innerBook = realm.where<Book>().equalTo("isbn", book.isbn).findFirstAsync()
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

    fun lookupBook(isbn : String, author : String, title : String) : Book? {
        return null
    }
}
