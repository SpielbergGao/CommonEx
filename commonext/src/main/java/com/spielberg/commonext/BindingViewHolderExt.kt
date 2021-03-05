
@file:Suppress("unused")

package com.spielberg.commonext

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


inline fun <reified VB : ViewBinding> BindingViewHolder(parent: ViewGroup) =
  BindingViewHolder(inflateBinding<VB>(parent))

class BindingViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root) {

  constructor(block: (LayoutInflater, ViewGroup, Boolean) -> VB, parent: ViewGroup) :
      this(block(LayoutInflater.from(parent.context), parent, false))
}
