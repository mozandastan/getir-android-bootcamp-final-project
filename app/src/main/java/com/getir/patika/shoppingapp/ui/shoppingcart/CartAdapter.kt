package com.getir.patika.shoppingapp.ui.shoppingcart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.getir.patika.shoppingapp.R
import com.getir.patika.shoppingapp.data.models.Product
import com.getir.patika.shoppingapp.databinding.ItemAddedproductBinding
import com.getir.patika.shoppingapp.utils.dpToPx
import com.getir.patika.shoppingapp.viewmodels.CartViewModel

class CartAdapter(private var dataList: List<Product>,private val cartViewModel: CartViewModel) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    fun updateData(newDataList: List<Product>) {
        val uniqueDataList = newDataList.distinctBy { it.id }
        dataList = uniqueDataList
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

            binding.btnPlus.setOnClickListener {
                cartViewModel.addToCart(product)
            }

            binding.btnDelete.setOnClickListener {
                cartViewModel.removeFromCart(product)
            }

            var imgUrl: String? = ""
            product.imageURL?.let {
                imgUrl = product.imageURL } ?: run {
                imgUrl = product.squareThumbnailURL
            }
            Glide.with(itemView.context)
                .load(imgUrl)
                .placeholder(R.drawable.img_defproduct)
                .into(binding.imgProduct)

            binding.txtName.text = product.name
            binding.txtPrice.text = product.priceText

            var attText : String? = ""
            product.attribute?.let {
                attText = product.attribute } ?: run {
                attText = product.shortDescription
            }
            binding.txtAtt.text = attText

            binding.txtCount.text = cartViewModel.getProductCount(product).toString()

            if(cartViewModel.getProductCount(product) > 1){
                binding.btnDelete.setImageResource(R.drawable.img_minus)
            }else{
                binding.btnDelete.setImageResource(R.drawable.img_trash)
            }
        }
        }
    }
