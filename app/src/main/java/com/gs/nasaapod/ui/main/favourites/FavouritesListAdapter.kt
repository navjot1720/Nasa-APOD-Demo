package com.gs.nasaapod.ui.main.favourites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gs.nasaapod.BR
import com.gs.nasaapod.data.database.entities.FavouritePicturesEntity
import com.gs.nasaapod.databinding.ListItemFavouritesBinding
import com.gs.nasaapod.interfaces.FavouriteItemClickListener


class FavouritesListAdapter(
    private val favouriteList: ArrayList<FavouritePicturesEntity>,
    val clickCallBack  : (String) -> Unit,
    val removeCallBack  : (FavouritePicturesEntity?) -> Unit
) : RecyclerView.Adapter<FavouritesListAdapter.ItemViewHolder>(), FavouriteItemClickListener {

    private var context: Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ListItemFavouritesBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(favouriteList[position])
    }

    override fun getItemCount() = favouriteList.size


    inner class ItemViewHolder(private val itemBinding: ListItemFavouritesBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.clickListener = this@FavouritesListAdapter
        }

        fun bind(favouritePicturesEntity: FavouritePicturesEntity) {
            itemBinding.setVariable(BR.model, favouritePicturesEntity)
            itemBinding.executePendingBindings()
        }
    }


    override fun onRemoveFavouriteClicked(model: FavouritePicturesEntity?) {
        favouriteList.remove(model)
        notifyItemRemoved(favouriteList.indexOf(model))
        removeCallBack.invoke(model)
    }

    override fun onFavouriteItemClicked(date: String?) {
        clickCallBack.invoke(date!!)
    }

}