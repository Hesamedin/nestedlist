package com.telus.screens.movieList

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.telus.screens.movieList.model.Movie
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type
import java.util.SortedMap

interface MoviesActions {
    fun displayMoviesFromAsset(inputStream: InputStream)
}

class MoviesViewModel: MoviesActions, ViewModel(), LifecycleObserver {

    val changeNotifier = MutableLiveData<SortedMap<String, List<Movie>>>()

    private lateinit var moviesObservable: Disposable

    override fun onCleared() {
        super.onCleared()

        if (!moviesObservable.isDisposed) {
            moviesObservable.dispose()
        }
    }

    override fun displayMoviesFromAsset(inputStream: InputStream) {
        var movies: ArrayList<Movie>
        val map = HashMap<String, ArrayList<Movie>>()

        moviesObservable = getListOfMovies(inputStream)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    movies = it
                    movies.forEach {
                        if (map.containsKey(it.category)) {
                            val list = map[it.category]
                            list!!.add(it)
                            map[it.category] = list
                        } else {
                            val list = ArrayList<Movie>()
                            list.add(it)
                            map[it.category] = list
                        }
                    }

                    changeNotifier.postValue( map.toSortedMap())
                }, {
                    Timber.e(it)
                })
    }

    private fun getListOfMovies(inputStream: InputStream): Single<ArrayList<Movie>> {
        val gson = Gson()
        val json: String = loadJSONFromAsset(inputStream)
        if (json.isEmpty()) {
            return Single.error(Throwable("Error is parse of assets.json file."))
        }

        val type: Type = object:TypeToken<ArrayList<Movie>>() {
        }.getType()
        return Single.just(gson.fromJson(json, type))
    }

    private fun loadJSONFromAsset(inputStream: InputStream): String {
        val json: String

        try {
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }

        return json
    }

}