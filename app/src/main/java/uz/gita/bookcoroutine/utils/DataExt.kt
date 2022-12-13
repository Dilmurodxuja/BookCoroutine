package uz.gita.bookcoroutine.utils

import uz.gita.bookcoroutine.data.local.room.BookEntity
import uz.gita.bookcoroutine.data.remote.request.BookRequest
import uz.gita.bookcoroutine.data.remote.response.BookData
import uz.gita.bookcoroutine.data.remote.response.BookFullData

fun BookData.toAdd(): BookRequest.Add = BookRequest.Add(title, author, description, pageCount)
fun BookData.toEntity(n: ChangeCol) = BookEntity(id, title, author, description, pageCount, fav, synCol = n.value)
fun BookData.toId(): BookRequest.ById = BookRequest.ById(bookId = id)
fun BookData.toUpdate(): BookRequest.Update = BookRequest.Update(id, title, author, description, pageCount )

fun BookEntity.toAddRequest() = BookRequest.Add(title, author, description, pageCount)
fun BookEntity.toBookEntity(n: ChangeCol)= BookEntity(id, title, author, description, pageCount, fav, synCol = n.value)
fun BookEntity.toById() = BookRequest.ById(bookId = id)
fun BookEntity.toUpdate() = BookRequest.Update(id, title, author, description, pageCount)

fun BookFullData.toId(): BookRequest.ById = BookRequest.ById(bookId = id)

fun BookRequest.Add.toEntity(n: ChangeCol) = BookEntity(0, title, author, description, pageCount, false, synCol = n.value)

fun BookRequest.Update.toBookData() = BookData(id, title, author, description, pageCount, fav = false )
fun BookRequest.Update.toBookEntity(n: ChangeCol) = BookEntity(id, title, author, description, pageCount, synCol = n.value)