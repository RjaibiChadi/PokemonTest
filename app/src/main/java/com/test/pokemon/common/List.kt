package com.test.pokemon.common

import androidx.compose.runtime.Composable
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun <T : Any> List<T>.toLazyPagingItems(): LazyPagingItems<T> {
    // Create a custom paging source
    val pagingSource = object : PagingSource<Int, T>() {
        override fun getRefreshKey(state: PagingState<Int, T>): Int? = null
        // Load method to fetch the data
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> =
            LoadResult.Page(data = this@toLazyPagingItems, prevKey = null, nextKey = null)
    }
    // Create the Pager with the pagination configuration (page size is set to 10 items)
    val pager = Pager(PagingConfig(pageSize = 10)) { pagingSource }
    // Collect the flow returned by the pager as LazyPagingItems
    val fakeFlow = pager.flow.collectAsLazyPagingItems()
    // Return the LazyPagingItems object to be used in the composable.
    return fakeFlow
}
