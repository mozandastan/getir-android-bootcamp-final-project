package com.getir.patika.shoppingapp.ui.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.getir.patika.shoppingapp.R
import com.getir.patika.shoppingapp.data.models.Product
import com.getir.patika.shoppingapp.databinding.ItemAddedproductBinding
import com.getir.patika.shoppingapp.viewmodels.CartViewModel

class CartAdapter(private var dataList: List<Product>,private val cartViewModel: CartViewModel) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    fun updateData(newDataList: List<Product>) {
        //To avoid showing duplicate products
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

    override fun getItemCount(): Int = dataList.size

    inner class CartViewHolder(private val binding: ItemAddedproductBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            //Set add to cart button
            binding.btnPlus.setOnClickListener {
                val product = dataList[bindingAdapterPosition]
                cartViewModel.addToCart(product)
            }
            //Set remove from cart button
            binding.btnDelete.setOnClickListener {
                val product = dataList[bindingAdapterPosition]
                if (cartViewModel.isProductInCart(product)) {
                    cartViewModel.removeFromCart(product)
                }
            }
        }
        fun bind(product: Product) {
            //Load image
            val imgUrl = product.squareThumbnailURL ?: product.thumbnailURL ?: product.imageURL
            Glide.with(itemView.context)
                .load(imgUrl)
                .placeholder(R.drawable.img_defproduct)
                .into(binding.imgProduct)

            //Set texts
            binding.txtName.text = product.name
            binding.txtPrice.text = product.priceText
            val attText = product.attribute ?: product.shortDescription
            binding.txtAtt.text = attText

            // Get the product count
            val count = cartViewModel.getProductCount(product)
            binding.txtCount.text = count.toString()

            //Change the button image according to count of that item
            if(count > 1){
                binding.btnDelete.setImageResource(R.drawable.img_minus)
            }else{
                binding.btnDelete.setImageResource(R.drawable.img_trash)
            }
        }
        }
    }
