package com.udacity.asteroidradar

import androidx.recyclerview.widget.DiffUtil
import com.udacity.asteroidradar.models.AsteroidFeedResponseModelItem

class AsteroidsDiffUtil(
    private val oldList: List<AsteroidFeedResponseModelItem>,
    private val newList: List<AsteroidFeedResponseModelItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}