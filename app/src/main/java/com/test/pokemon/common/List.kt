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
    val pagingSource = object : PagingSource<Int, T>() {
        override fun getRefreshKey(state: PagingState<Int, T>): Int? = null
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> =
            LoadResult.Page(data = this@toLazyPagingItems, prevKey = null, nextKey = null)
    }

    val pager = Pager(PagingConfig(pageSize = 10)) { pagingSource }
    val fakeFlow = pager.flow.collectAsLazyPagingItems()
    return fakeFlow
}
