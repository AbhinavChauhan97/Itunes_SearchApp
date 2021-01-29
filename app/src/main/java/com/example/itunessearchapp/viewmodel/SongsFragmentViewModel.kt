package com.example.itunessearchapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.*
import com.example.itunessearchapp.model.Song
import com.example.itunessearchapp.repository.RetrofitClient
import com.example.itunessearchapp.repository.RoomClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * for such a simple app not creating separate repository class otherwise app may look over engineered
 */
class SongsFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val songs = MutableLiveData<List<Song>>()
    private var onFailedToFetchSongs = {}
    private var onSearchedArtistDoesNotExist = {}

    fun getSongsFor(artistName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            //first attempt to find songs in room
            val songsLiveData = RoomClient.getRoom(getApplication<Application>().applicationContext).songsDao().getSongsFor(artistName)
            if (songsLiveData.isNotEmpty())  { // if we find songs in room
                //Log.d("log","in the room")
                songs.postValue(songsLiveData)
            } else {
                // Log.d("log","not in the room")
                if (isOnline()) { // if we are connected to internet
                    //hit the api
                    val songsResponse = RetrofitClient.songsApi.getSongsFor(artistName, "song")
                    val responseBody = songsResponse.body() ?: return@launch // if we didn't find any results , return
                    val songsList = responseBody.results
                    if (!songsList.isNullOrEmpty()) { // if we have something to show
                        songs.postValue(songsList) // show those results
                        RoomClient.getRoom(getApplication<Application>().applicationContext)
                            .songsDao().saveSong(songsList) // also save those results to room to use offline
                    } else {
                        onSearchedArtistDoesNotExist.invoke() // if artist doesn't exist invoke callback
                    }
                } else {
                    onFailedToFetchSongs.invoke() // if we fail to fetch songs for some reason invoke callback
                }
            }
        }
    }


    /**
     * checks whether of not user is connected to internet
     * <p> returns true if user is connected false otherwise</p>
     * @return boolean indicating whether or not user is connected
     */
    private fun isOnline(): Boolean {
        val connectivityManager =
            getApplication<Application>().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetworkInfo
        return network != null && network.isConnected
    }

    /**
     * register a callback to be invoked if we failed to load songs for some reason
     * for example user is not connected to internet
     */
    fun doWhenFailToGetSongs(doWhenFailToGetSongs: () -> Unit) {
        this.onFailedToFetchSongs = doWhenFailToGetSongs
    }

    /**
     * register a callback to be invoked to be invoked to if no
     * songs for the given artist were returned for the given artist or the artist itself is not recognised by api
     */
    fun doWhenSongsDoesNotExistForSearchedArtist(songDoesNotExist: () -> Unit) {
        this.onSearchedArtistDoesNotExist = songDoesNotExist
    }
}
