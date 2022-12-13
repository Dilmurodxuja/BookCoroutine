package uz.gita.bookcoroutine.data.remote.apis

import retrofit2.Response
import retrofit2.http.*
import uz.gita.bookcoroutine.data.remote.request.BookRequest
import uz.gita.bookcoroutine.data.remote.response.BookData
import uz.gita.bookcoroutine.data.remote.response.MessageResponse

interface BookApi {
    @GET("/books")
    suspend fun getAllBooks(@Header("Authorization") token: String): Response<List<BookData>>

    @POST("/book")
    suspend fun addBook(@Header("Authorization") token: String, @Body data: BookRequest.Add): Response<BookData>

    @HTTP(method = "DELETE", path = "/book", hasBody = true)
    suspend fun deleteBook(@Header("Authorization") token: String, @Body bookId: BookRequest.ById): Response<MessageResponse>

    @PUT("/book")
    suspend fun updateBook(@Header("Authorization") token: String, @Body data: BookRequest.Update): Response<Unit>

    @POST("/book/change-fav")
    suspend fun updateBookFav(@Header("Authorization") token: String, @Body bookId: BookRequest.ById): Response<MessageResponse>
}
