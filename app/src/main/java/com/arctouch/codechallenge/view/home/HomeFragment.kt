package com.arctouch.codechallenge.view.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.InjectorUtils
import com.arctouch.codechallenge.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.context?.let { context ->
            val factory = InjectorUtils().provideViewModelFactory(context)
            viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
            observe()
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun observe(){
        viewModel.movies.observe(this, Observer { movies ->
            updateUI(movies)
        })
    }

    private fun updateUI(moviesWithGenres: List<Movie>){
        recyclerView.adapter = HomeAdapter(moviesWithGenres)
        progressBar.visibility = View.GONE
    }

}
