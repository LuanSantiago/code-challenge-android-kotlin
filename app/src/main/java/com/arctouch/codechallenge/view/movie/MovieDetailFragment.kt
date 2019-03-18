package com.arctouch.codechallenge.view.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.InjectorUtils
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.arctouch.codechallenge.viewmodel.MovieDetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import java.text.SimpleDateFormat
import java.util.*

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
        val factory = InjectorUtils().provideViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(MovieDetailViewModel::class.java)
        observe()
        arguments?.let {
            val id = it.getLong("movie_id")
            val posterPath = it.getString("movie_poster")
            val backdropPath = it.getString("movie_backdrop")
            loadImage(posterPath,backdropPath)
            viewModel.getMovie(id)
            progressBar.visibility = View.VISIBLE
        }

    }

    private fun observe(){
        viewModel.movie.observe(this, Observer { movie ->
            setupDetail(movie)
            progressBar.visibility = View.GONE
        })
    }

    private fun setupDetail(movie: Movie?){
        if(movie != null) {
            loadImage(movie.posterPath,movie.backdropPath)
            text_name.text = movie.title
            text_date.text = dateStringFormat(movie.releaseDate.toString())
            if(!movie.overview.isNullOrEmpty()){
                textNoInfo.visibility = View.GONE
                text_overview.text = movie.overview
            }else{
                textNoInfo.visibility = View.VISIBLE
            }
            text_genres.text = movie.genres?.joinToString(separator = ", ") { it.name }
        }else{
            textNoInfo.visibility = View.VISIBLE
            val message = context?.getString(R.string.error_movie_detail).toString()
            view?.let {
                val snackbar : Snackbar = Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }
    }

    private fun loadImage(posterPath: String?,backdropPath: String?){
        val movieImageUrlBuilder = MovieImageUrlBuilder()
        Glide.with(img_poster)
            .load(posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
            .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
            .into(img_poster)

        Glide.with(img_backdrop)
            .load(backdropPath?.let { movieImageUrlBuilder.buildBackdropUrl(it) })
            .apply(RequestOptions().centerCrop())
            .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
            .into(img_backdrop)
    }

    private fun dateStringFormat(dateFormat: String): String{
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
        val data = format.parse(dateFormat)
        format.applyPattern("dd/MM/yyyy")
        return format.format(data)
    }

}
