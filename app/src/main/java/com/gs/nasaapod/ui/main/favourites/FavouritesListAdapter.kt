package com.gs.nasaapod.ui.main.favourites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gs.nasaapod.data.database.entities.FavouritePicturesEntity
import com.gs.nasaapod.databinding.ListItemFavouritesBinding
import com.gs.nasaapod.utils.AppUtils


class FavouritesListAdapter(val favouriteList: ArrayList<FavouritePicturesEntity>,
    val clickListener : (Int) -> Unit
) : RecyclerView.Adapter<FavouritesListAdapter.ItemViewHolder>() {

    private var context: Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ListItemFavouritesBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = favouriteList.size


    inner class ItemViewHolder(private val itemBinding: ListItemFavouritesBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.atvRemove.setOnClickListener {
                clickListener.invoke(adapterPosition)
            }
        }

        fun bind(position: Int) {
            favouriteList[position].let {
                itemBinding.sdvImage.setImageURI(it.url)

                itemBinding.atvTitle.text = it.title
                itemBinding.atvDate.text = AppUtils.parseDateTimeFormat(it.date)
                itemBinding.atvExplanation.text = it.explanation
            }
        }
    }

}