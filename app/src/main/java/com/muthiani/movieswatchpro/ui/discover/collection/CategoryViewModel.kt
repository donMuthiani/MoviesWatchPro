package com.muthiani.movieswatchpro.ui.discover.collection

import androidx.lifecycle.ViewModel
import com.muthiani.movieswatchpro.data.FakeWatchListRepository
import com.muthiani.movieswatchpro.data.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel
    @Inject
    constructor(fakeWatchListRepository: FakeWatchListRepository) : ViewModel() {
        sealed class CategoryState {
            data class Error(val message: String) : CategoryState()

            data object Initial : CategoryState()

            data object Loading : CategoryState()

            data class Data(val movieModel: List<Movie>) : CategoryState()
        }
    }
