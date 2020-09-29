package com.pibusa.tinderapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.pibusa.tinderapp.R
import com.pibusa.tinderapp.data.network.Api
import com.pibusa.tinderapp.data.network.NetworkConnectionInterceptor
import com.pibusa.tinderapp.data.network.response.TinderProfileResponse
import com.pibusa.tinderapp.data.network.state.DataState
import com.pibusa.tinderapp.data.repository.MainRepository
import com.pibusa.tinderapp.ui.adapter.UserItemAdapter
import kotlinx.android.synthetic.main.activity_main.*
import swipeable.com.layoutmanager.OnItemSwiped
import swipeable.com.layoutmanager.SwipeableLayoutManager
import swipeable.com.layoutmanager.SwipeableTouchHelperCallback
import swipeable.com.layoutmanager.touchelper.ItemTouchHelper

class MainActivity : AppCompatActivity() {
    private var mViewModel: MainViewModel? = null;

    // private val factory: MainViewModelFactory by instance()
    private var adapter: UserItemAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        setSwappableItem()

    }

    private fun initViewModel() {
        mViewModel =
            ViewModelProvider(
                this, MainViewModelFactory(
                    MainRepository(
                        Api.invoke(
                            NetworkConnectionInterceptor(this)
                        )
                    )
                )
            ).get(
                MainViewModel::class.java
            )
        mViewModel?.getTinderProfile("")
        mViewModel?.profileLiveData?.observe(this, Observer {
            when (it) {
                is DataState.Success -> {
                    displayProgressBar(false)
                    setUserItem(it.data as MutableList<TinderProfileResponse>)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(it.exception.message)
                }
            }
        })
    }

    private fun setSwappableItem() {
        val swappableTouchHelperCallback: SwipeableTouchHelperCallback =
            object : SwipeableTouchHelperCallback(object : OnItemSwiped {
                override fun onItemSwiped() {
                    adapter?.removeTopItem()
                    // mViewModel.setStateEvent(GetUsersEvent)
                }

                override fun onItemSwipedLeft() {
                    Log.e("SWIPE", "LEFT")
                }

                override fun onItemSwipedRight() {
                    //   mViewModel.addFavorite(GetUsersEvent, adapter?.getFavoriteItem())
                    Log.e("SWIPE", "RIGHT")
                }

                override fun onItemSwipedUp() {
                    Log.e("SWIPE", "UP")
                }

                override fun onItemSwipedDown() {
                    Log.e("SWIPE", "DOWN")
                }
            }) {
                override fun getAllowedSwipeDirectionsMovementFlags(viewHolder: RecyclerView.ViewHolder): Int {
                    return ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
                }
            }

        val itemTouchHelper = ItemTouchHelper(swappableTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recycleView)
        recycleView.layoutManager = SwipeableLayoutManager().setAngle(10)
            .setAnimationDuratuion(450)
            .setMaxShowCount(3)
            .setScaleGap(0.1f)
            .setTransYGap(0)
        adapter = UserItemAdapter(this)
        recycleView.adapter = adapter
    }

    private fun displayError(message: String?) {
        if (message != null) text.text = message else text.text = "Unknown error."
    }

    private fun setUserItem(users: MutableList<TinderProfileResponse>) {
        adapter?.setData(users)
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }
}