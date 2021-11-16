package com.torres.demo.viewModel

import androidx.lifecycle.*
import com.torres.demo.data.model.MovieDao
import com.torres.demo.domain.DataRepository
import com.torres.demo.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel constructor(private val repository: DataRepository) : ViewModel() {

    private val _searchMovie= MutableLiveData<String>()
    private val _getMovie = MutableLiveData<String>()

    fun setMovie(nameMovie:String){
        _searchMovie.value = nameMovie
    }

    fun setGetMovie(item:String){
        _getMovie.value = item
    }

    val searchMoview = _searchMovie.distinctUntilChanged().switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            emit(repository.requestSearchMovie(it))
        }
    }

   fun saveMovie(movieDao: MovieDao){
       viewModelScope.launch {
           repository.insertMovie(movieDao)
       }
   }

    val getMovie = _getMovie.distinctUntilChanged().switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            emit(repository.getMovie())
        }
    }
}