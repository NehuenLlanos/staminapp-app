package com.staminapp.data.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.staminapp.data.network.model.NetworkError
import retrofit2.Response
import java.io.IOException

abstract class RemoteDataSource {

    /*
        Voy a llamar a la funcion. Me puede dar successful, me retorna error o excepcion de retrofit.
        Entonces esta funcion me va a devolver solamente cuando haya dado succesful
     */
    suspend fun <T : Any> handleApiResponse(
        execute: suspend () -> Response<T>
    ) : T {
        try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                return body
            }
            response.errorBody()?.let {
                val gson = Gson()
                val error = gson.fromJson<NetworkError>(it.string(), object : TypeToken<NetworkError?>() {}.type)
                throw DataSourceException(error.code, error.description, error.details)
            }
            throw DataSourceException(UNEXPECTED_ERROR_CODE, "Missing error", null)
        } catch (e: IOException) {
            throw DataSourceException(CONNECTION_ERROR_CODE, "Connection error", getDetailsFromException(e))
        } catch (e: Exception) {
            throw DataSourceException(UNEXPECTED_ERROR_CODE, "Unexpected error", getDetailsFromException(e))
        }
    }

    private fun getDetailsFromException(e: Exception) : List<String> {
        return if (e.message != null) listOf(e.message!!) else emptyList()
    }

    companion object {
        const val CONNECTION_ERROR_CODE = 98
        const val UNEXPECTED_ERROR_CODE = 98
    }
}