package com.example.itunessearchapp.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.View.GONE
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itunessearchapp.R
import com.example.itunessearchapp.adapters.SongsRecyclerAdapter
import com.example.itunessearchapp.viewmodel.SongsFragmentViewModel
import com.google.android.material.snackbar.Snackbar

class SongsFragment : Fragment(R.layout.layout_songs_fragment){



    lateinit var viewModel:SongsFragmentViewModel
    lateinit var songsRecyclerAdapter:SongsRecyclerAdapter
    lateinit var searchSongsImageView:ImageView
    lateinit var searchSongstextView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchSongsImageView = view.findViewById(R.id.search_songs)
        searchSongstextView = view.findViewById(R.id.text)
        val songsRecyclerView = view.findViewById<RecyclerView>(R.id.songs_recyclerview)
        songsRecyclerView.apply {
            layoutManager = GridLayoutManager(requireActivity(),2,GridLayoutManager.VERTICAL,false)
            addItemDecoration(DividerItemDecoration(requireActivity(),LinearLayoutManager.VERTICAL))
            addItemDecoration(DividerItemDecoration(requireActivity(),LinearLayoutManager.HORIZONTAL))
            songsRecyclerAdapter = SongsRecyclerAdapter()
            adapter = songsRecyclerAdapter
        }
       setupViewModel()
    }

    private fun setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(SongsFragmentViewModel::class.java)
        viewModel.songs.observe(this,{
            searchSongstextView.visibility = GONE
            searchSongsImageView.visibility = GONE
            songsRecyclerAdapter.submitList(it)
        })

        viewModel.doWhenFailToGetSongs {
            Snackbar.make(view!!,"Make Sure you're Connected to Internet",Snackbar.LENGTH_LONG).show()
        }

        viewModel.doWhenSongsDoesNotExistForSearchedArtist {
            Snackbar.make(view!!,"No songs Exist For Searched Artist",Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setupSearchView(searchBar: androidx.appcompat.widget.SearchView){
        searchBar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    viewModel.getSongsFor("%${query}%")
                }
                return true
            }
            override fun onQueryTextChange(newText: String?) = true
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu,menu)
        val searchBar = menu.findItem(R.id.search_bar).actionView as androidx.appcompat.widget.SearchView
        setupSearchView(searchBar)
    }
}