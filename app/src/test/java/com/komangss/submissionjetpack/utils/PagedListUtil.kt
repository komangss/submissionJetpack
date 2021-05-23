package com.komangss.submissionjetpack.utils

import androidx.paging.PagedList
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import android.database.Cursor
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.room.RoomDatabase
import androidx.room.RoomSQLiteQuery
import androidx.room.paging.LimitOffsetDataSource
import com.komangss.submissionjetpack.utils.LiveDataTestUtil.getOrAwaitValue
import io.mockk.every
import io.mockk.mockk
object PagedListUtil {

    fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = mock(PagedList::class.java) as PagedList<T>
        `when`(pagedList[anyInt()]).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        `when`(pagedList.size).thenReturn(list.size)

        return pagedList
    }

    fun <T> List<T>.asPagedList(config: PagedList.Config? = null): PagedList<T>? {
        val defaultConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(size)
            .setMaxSize(size + 2)
            .setPrefetchDistance(1)
            .build()
        return LivePagedListBuilder<Int, T>(
            createMockDataSourceFactory(this),
            config ?: defaultConfig
        ).build().getOrAwaitValue()
    }

    fun <T> createMockDataSourceFactory(itemList: List<T>): DataSource.Factory<Int, T> =
        object : DataSource.Factory<Int, T>() {
            override fun create(): DataSource<Int, T> = MockLimitDataSource(itemList)
        }
    private val mockQuery = mockk<RoomSQLiteQuery> {
        every { sql } returns ""
    }
    private val mockDb = mockk<RoomDatabase> {
        every { invalidationTracker } returns mockk(relaxUnitFun = true)
    }
    class MockLimitDataSource<T>(private val itemList: List<T>) : LimitOffsetDataSource<T>(mockDb, mockQuery, false, null) {
        override fun convertRows(cursor: Cursor?): MutableList<T> = itemList.toMutableList()
        override fun countItems(): Int = itemList.count()
        override fun isInvalid(): Boolean = false
        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) { /* Not implemented */ }
        override fun loadRange(startPosition: Int, loadCount: Int) =
            itemList.subList(startPosition, startPosition + loadCount).toMutableList()
        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
            callback.onResult(itemList, 0)
        }
    }
}