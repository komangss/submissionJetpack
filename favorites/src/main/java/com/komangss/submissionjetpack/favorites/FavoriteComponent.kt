package com.komangss.submissionjetpack.favorites

import android.content.Context
import com.komangss.submissionjetpack.di.FavoritesModuleDependencies
import com.komangss.submissionjetpack.favorites.movie.MovieFavoriteFragment
import com.komangss.submissionjetpack.favorites.tvshow.TvShowFavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoritesModuleDependencies::class])
interface FavoriteComponent {
    fun inject(favoriteMovieFragment: MovieFavoriteFragment)
    fun inject(favoriteTvShowFragment: TvShowFavoriteFragment)
    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoritesModuleDependencies: FavoritesModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}