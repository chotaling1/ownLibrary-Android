package com.chotaling.ownlibrary.domain.services

import android.content.Context
import com.chotaling.ownlibrary.domain.RealmConfig
import com.chotaling.ownlibrary.domain.models.Book
import com.chotaling.ownlibrary.domain.models.Location
import com.chotaling.ownlibrary.infrastructure.dto.Google.GoogleBookDto
import com.chotaling.ownlibrary.infrastructure.repositories.GoogleBooksRepository
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import org.bson.types.ObjectId

class BookService()
{

    private val googleLookupService : GoogleBooksRepository = GoogleBooksRepository()
    private var realmInstance : Realm = RealmConfig().getInstance()
    
    fun addBook(book : Book)
    {
        realmInstance.executeTransactionAsync() { realm ->
            realm.insert(book)
        }
    }

    fun removeBook(bookId : ObjectId)
    {
        realmInstance.executeTransactionAsync() { realm ->
            val managedBook = realm.where<Book>().equalTo("id", bookId).findFirst()
            managedBook?.deleteFromRealm()
        }
    }

    fun getBookByTitle(title : String) : Set<Book>?
    {
        val books = realmInstance.where<Book>().equalTo("author", title).findAll()
        return books.toSet()
    }

    fun getBooksByAuther(author : String) : Set<Book>?
    {
        val books = realmInstance.where<Book>().equalTo("author", author).findAll()
        return books.toSet()
    }

    fun getBookByIsbn(isbn : String) : Book?
    {
        val book = realmInstance.where<Book>().equalTo("isbn", isbn).findFirst()
        return book
    }

    fun getAllBooks() : RealmResults<Book>?
    {
        return realmInstance.where<Book>().findAllAsync()
    }

    fun updateBook(book : Book)
    {
        realmInstance.executeTransactionAsync { realm ->
            val innerBook = realm.where<Book>().equalTo("id", book.id).findFirstAsync()
            innerBook?.author = book.author
            innerBook?.imageUrl = book.imageUrl
            innerBook?.title = book.title
        }
    }

    fun getBooksByLocation(location : Location) : Set<Book>?
    {

        val books = realmInstance.where<Book>().findAll()
        val returnedBooks = mutableSetOf<Book>()
        books.forEach {
            b -> if (location.shelves.contains(b.shelf))
            {
                    returnedBooks.add(b);
            }
        }
        return returnedBooks
    }

    suspend fun lookupBook(isbn : String?, author : String?, title : String?, index : Int?, take : Int?) : Array<GoogleBookDto>? {

        return googleLookupService.lookupBook(isbn, author, title, index, take)
    }
}
