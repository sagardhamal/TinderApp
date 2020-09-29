package com.pibusa.tinderapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.pibusa.tinderapp.R
import com.pibusa.tinderapp.data.network.response.TinderProfileResponse
import kotlinx.android.synthetic.main.item_user_profile.view.*

class UserItemAdapter(private val context: Context) :
    RecyclerView.Adapter<UserItemAdapter.UserViewHolder>() {
    private var userList: MutableList<TinderProfileResponse>? = null

    init {
        userList = arrayListOf()
    }
    fun setData(list: MutableList<TinderProfileResponse>?) {
        userList = list
        notifyDataSetChanged()
    }

    fun removeTopItem() {
        userList?.removeAt(0)
        notifyDataSetChanged()
    }

    fun getFavoriteItem(): TinderProfileResponse? {
        return userList?.get(0)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserItemAdapter.UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_user_profile, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList?.get(position))
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: TinderProfileResponse?) {
            /*    itemView.iv_profile.load(user?.picture) {
                    crossfade(true)
                    error(R.drawable.ic_launcher_background)
                    transformations(CircleCropTransformation())
                }*/
            itemView.rb_name.isSelected = true
            setSelected(itemView.rb_name.id, user)

            itemView.rg_bottom.setOnCheckedChangeListener { group, checkedId ->
                setSelected(checkedId, user)
            }
        }

        private fun setSelected(
            checkedId: Int,
            user: TinderProfileResponse?
        ) {
            when (checkedId) {
                R.id.rb_name -> {
                  /*  setUserDetails(
                        context?.getString(R.string.label_my_name_is),
                        "${user?.name?.first} ${user?.name?.last}"
                    )*/
                }
                R.id.rb_dob -> {
                    setUserDetails(
                        context?.getString(R.string.label_my_dob_is),
                        user?.dob.toString()
                    )
                }
                R.id.rb_location -> {
                    setUserDetails(
                        context?.getString(R.string.label_my_address_is),
                        "${user?.location?.zip}, ${user?.location?.street}, ${user?.location?.city}"
                    )
                }
                R.id.rb_call -> {
                    setUserDetails(
                        context?.getString(R.string.label_my_phone_is), user?.phone
                    )
                }
                R.id.rb_password -> {
                    setUserDetails(
                        context?.getString(R.string.label_my_password), user?.SSN
                    )
                }
            }
        }

        private fun setUserDetails(title: String?, description: String?) {
            itemView.tv_title.text = title
            itemView.tv_description.text = description
        }
    }


}