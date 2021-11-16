package com.torres.demo.data

import com.torres.demo.application.App
import com.torres.demo.data.model.MovieDao
import com.torres.demo.data.model.MovieEntity
import com.torres.demo.domain.ApiRestServices
import com.torres.demo.utilities.Constants
import com.torres.demo.utilities.Utils
import com.torres.demo.vo.ApiClient
import com.torres.demo.vo.AppDatabase
import com.torres.demo.vo.Resource
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

class DataSourceImp(private val appDatabase: AppDatabase):DataSource {

    override suspend fun requestSearchMovie(nameMovie: String): Resource<MovieEntity> {
        val sendService = ApiClient.createService(ApiRestServices::class.java)
        return when (val response = processCall { sendService.searchMovie(Constants.API_KEY,nameMovie,10) }) {
            is MovieEntity -> {
                Resource.Success(data = response as MovieEntity)
            }
            else -> {
                Resource.DataError(errorMessage = response as String)
            }
        }
    }

    override suspend fun insertMovie(movieDao: MovieDao) {
        appDatabase.accessDao().insertMovie(movieDao)
    }

    override suspend fun getMovie(): Resource<List<MovieDao>> {
        return Resource.Success(data = appDatabase.accessDao().getMovies())
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!Utils.isConnect(App.appInstance!!.applicationContext)) {
            return "Sin acceso a internet"
        } else {
            return try {
                val response = responseCall.invoke()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    val message = try {
                        val jsonObject = JSONObject(response!!.errorBody()!!.string())
                        jsonObject.getString("message")
                    } catch (e: JSONException) {
                       e.message
                    } catch (e: IOException) {
                        e.message
                    }
                    message
                }
            } catch (e: IOException) {
                e.message
            }
        }
    }

}