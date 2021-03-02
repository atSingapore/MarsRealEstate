/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsProperty

class PhotoGridAdapter(val onClickListener: OnClickListener) : ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallBack) {

    // Need a ViewHolder for our photo grid adapter
    // Want to have binding variable available for binding our mars property to the layout
    // Since the base ViewHolder class requires a view in it's constructor, we'll pass in the binding.root view
    class MarsPropertyViewHolder(private var binding: GridViewItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(marsProperty: MarsProperty)
        {
            // Takes a mars property, and sets the property in the binding class
            binding.property = marsProperty
            // Causes the property update to execute immediately
            // Prevents the Recycler view from having to perform extra calculations when it figure out how to display the list
            binding.executePendingBindings()
        }
    }

    // Create a DiffCallBack object for us
    // Will create a companion object, because we want it in the namespace of our class
    // MarsProperty is the type of object we want to compare
    companion object DiffCallBack : DiffUtil.ItemCallback<MarsProperty>()
    {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            // This is true if the object references are the same
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridAdapter.MarsPropertyViewHolder {

        // Needs to return a new MarsPropertyViewHolder, created by inflating our GridViewItemBinding, using the LayoutInflater from our parent view group context
        return MarsPropertyViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoGridAdapter.MarsPropertyViewHolder, position: Int) {
        // Get the mars property associated with the recycler view position
        val marsProperty = getItem(position)
        // Use it to call the holder.bilnd method we created
        holder.bind(marsProperty)

        // Set up onClickListener to pass marsProperty on button click
        holder.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }
    }

    // This is an internal OnClickListener class with a lambda in its constructor that initializes a matching onClick function
    class OnClickListener(val clickListener: (marsPRoperty: MarsProperty) -> Unit) {
        fun onClick(marsProperty: MarsProperty) = clickListener(marsProperty)
    }


}
