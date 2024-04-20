package com.getir.patika.shoppingapp.ui.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.getir.patika.shoppingapp.R
import com.getir.patika.shoppingapp.data.models.Product
import com.getir.patika.shoppingapp.databinding.ItemAddedproductBinding

class CartAdapter(private var dataList: List<Product>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    fun updateData(newDataList: List<Product>) {
        dataList = newDataList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAddedproductBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class CartViewHolder(private val binding: ItemAddedproductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            Glide.with(itemView.context)
                .load(product.imageURL)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgProduct)

            binding.txtName.text = product.name
            binding.txtPrice.text = product.price.toString()
            //binding.txtAtt.text = product.attribute
        }
    }
}