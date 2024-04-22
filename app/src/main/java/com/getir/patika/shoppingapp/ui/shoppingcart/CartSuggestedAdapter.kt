package com.getir.patika.shoppingapp.ui.shoppingcart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.getir.patika.shoppingapp.R
import com.getir.patika.shoppingapp.data.models.Product
import com.getir.patika.shoppingapp.databinding.ItemAddedproductBinding
import com.getir.patika.shoppingapp.databinding.ItemSuggestedproductBinding
import com.getir.patika.shoppingapp.utils.dpToPx
import com.getir.patika.shoppingapp.viewmodels.CartViewModel

class CartSuggestedAdapter(private var dataList: List<Product>, private val cartViewModel: CartViewModel) :
    RecyclerView.Adapter<CartSuggestedAdapter.CartViewHolder>() {

    fun updateData(newDataList: List<Product>) {
        val uniqueDataList = newDataList.distinctBy { it.id }
        dataList = uniqueDataList
        notifyDataSetChanged()
    }
    fun refreshData(){
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSuggestedproductBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class CartViewHolder(private val binding: ItemSuggestedproductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {

            binding.btnPlus.setOnClickListener {
                cartViewModel.addToCart(product)
            }

            binding.btnDelete.setOnClickListener {
                cartViewModel.removeFromCart(product)
            }

            var imgUrl: String? = ""
            product.imageURL?.let {
                imgUrl = product.imageURL
            } ?: run {
                imgUrl = product.squareThumbnailURL
            }
            Glide.with(itemView.context)
                .load(imgUrl)
                .placeholder(R.drawable.img_defproduct)
                .into(binding.imgSuggestedproduct)

            binding.txtName.text = product.name
            binding.txtPrice.text = product.priceText

            var attText: String? = ""
            product.attribute?.let {
                attText = product.attribute
            } ?: run {
                attText = product.shortDescription
            }
            binding.txtAtt.text = attText

            binding.txtCount.text = cartViewModel.getProductCount(product).toString()

            val cartItems = cartViewModel.cartItems.value.orEmpty()
            val count = cartItems.count { it.id == product.id }
            if (count > 0) {
                binding.txtCount.text = count.toString()
                binding.txtCount.visibility = View.VISIBLE
                binding.btnDelete.visibility = View.VISIBLE

                val color = ContextCompat.getColor(itemView.context, R.color.purple)
                binding.cardSuggestedproduct.strokeColor = color

                val layoutParam = binding.suggesteditemstatecardview.layoutParams
                layoutParam.height = 96.dpToPx(itemView.context)
                binding.suggesteditemstatecardview.layoutParams = layoutParam

                if (cartViewModel.getProductCount(product) > 1) {
                    binding.btnDelete.setImageResource(R.drawable.img_minus)
                } else {
                    binding.btnDelete.setImageResource(R.drawable.img_trash)
                }
            } else {
                binding.txtCount.visibility = View.GONE
                binding.btnDelete.visibility = View.GONE

                val color = ContextCompat.getColor(itemView.context, R.color.stroke_grey)
                binding.cardSuggestedproduct.strokeColor = color

                val layoutParam = binding.suggesteditemstatecardview.layoutParams
                layoutParam.height = 32.dpToPx(itemView.context)
                binding.suggesteditemstatecardview.layoutParams = layoutParam
            }
        }
    }
}