package com.arctouch.codechallenge.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arctouch.codechallenge.R
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
            setupHome()
            observe()
        }
    }

    private fun observe(){
        viewModel.getUpcomingMovies().observe(this, Observer { movies ->
           homePagedAdapter.submitList(movies)
           progressBar.visibility = View.GONE
        })
    }

    private fun setupHome(){
        progressBar.visibility = View.VISIBLE
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = homePagedAdapter
    }

    private val homePagedAdapter = HomePagedAdapter{ movie ->
        val bundle = Bundle()
        bundle.putParcelable("movie",movie)
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_movieDetailFragment,bundle)
    }

}
