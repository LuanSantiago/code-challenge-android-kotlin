package com.arctouch.codechallenge.view.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.arctouch.codechallenge.viewmodel.MovieDetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MovieDetailFragment()
    }

    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
        arguments?.let {
            val movie: Movie? = it.getParcelable("movie")
            setupDetail(movie)
        }
    }

    private fun setupDetail(movie: Movie?){
        val movieImageUrlBuilder = MovieImageUrlBuilder()
        Glide.with(img_poster)
            .load(movie?.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
            .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
            .into(img_poster)

        Glide.with(img_backdrop)
            .load(movie?.backdropPath?.let { movieImageUrlBuilder.buildBackdropUrl(it) })
            .apply(RequestOptions().centerCrop())
            .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
            .into(img_backdrop)

        text_name.text = movie?.title
        text_date.text = movie?.releaseDate
        text_overview.text = movie?.overview
        text_genres.text =  movie?.genres?.joinToString(separator = ", ") { it.name }

    }

}
