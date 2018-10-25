package db.movies.movies.utils

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

public abstract class InfiniteScrollListener
    : RecyclerView.OnScrollListener() {

    var mPreviousTotal = 0
    /**
     * True if we are still waiting for the last set of data to load.
     */
    var mLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = recyclerView.childCount
        val totalItemCount = recyclerView.layoutManager!!.itemCount
        val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false
                mPreviousTotal = totalItemCount
            }
        }
        val visibleThreshold = 1
        if (!mLoading && ((totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold))) {
            onLoadMore()

            mLoading = true
        }
    }

    abstract fun onLoadMore()

}