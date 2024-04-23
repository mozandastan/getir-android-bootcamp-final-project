package com.getir.patika.shoppingapp.ui.productlisting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.getir.patika.shoppingapp.R
import com.getir.patika.shoppingapp.data.models.Product
import com.getir.patika.shoppingapp.databinding.ItemSuggestedproductBinding
import com.getir.patika.shoppingapp.utils.Constants
import com.getir.patika.shoppingapp.utils.dpToPx
import com.getir.patika.shoppingapp.viewmodels.CartViewModel
import com.getir.patika.shoppingapp.viewmodels.ProductViewModel

class HorizontalAdapter(private var dataList: List<Product>,
                        private val viewModel: ProductViewModel,
                        private val cartViewModel: CartViewModel) :
    RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder>() {

    fun updateData(newDataList: List<Product>) {
        dataList = newDataList
        notifyDataSetChanged()
    }
    fun refreshData(){
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSuggestedproductBinding.inflate(inflater, parent, false)
        return HorizontalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        val item = dataList[position]
        //Dynamic UI updates
        holder.bind(item)
    }
    override fun getItemCount(): Int = dataList.size

    inner class HorizontalViewHolder(private val binding: ItemSuggestedproductBinding) :
        RecyclerView.ViewHolder(binding.root) {

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
                //Set product detail button
                itemView.setOnClickListener {
                    val product = dataList[bindingAdapterPosition]
                    viewModel.setSelectedProduct(product)
                    itemView.findNavController().navigate(R.id.action_productListingFragment_to_productDetailFragment2)
                }
            }
        fun bind(product: Product) {

            //Load image
            val imgUrl = product.squareThumbnailURL ?: product.imageURL
            Glide.with(itemView.context)
                .load(imgUrl)
                .placeholder(R.drawable.img_defproduct)
                .into(binding.imgSuggestedproduct)

            //Set texts
            binding.txtName.text = product.name
            binding.txtPrice.text = product.priceText
            val attText = product.attribute ?: product.shortDescription
            binding.txtAtt.text = attText

            // Check if the product is in the cart
            //val cartItems = cartViewModel.cartItems.value.orEmpty()
            //val count = cartItems.count { it.id == product.id }
            val count = cartViewModel.getProductCount(product)
            if (count > 0) {
                //Show item count and remove button
                binding.txtCount.text = count.toString()
                binding.txtCount.visibility = View.VISIBLE
                binding.btnDelete.visibility = View.VISIBLE
                //Change the image stroke color
                val color = ContextCompat.getColor(itemView.context, R.color.purple)
                binding.cardSuggestedproduct.strokeColor = color
                // Change the button cart's maximum height
                val layoutParam = binding.suggesteditemstatecardview.layoutParams
                layoutParam.height = Constants.PRODUCT_ITEM_CARD_MAX_HEIGHT.dpToPx(itemView.context)
                binding.suggesteditemstatecardview.layoutParams = layoutParam
                //Change the button image according to count of that item
                if(count == 1){
                    binding.btnDelete.setImageResource(R.drawable.img_trash)
                }else{
                    binding.btnDelete.setImageResource(R.drawable.img_minus)
                }
                // If the product is not in the cart
            } else {
                //Hide item count and remove button
                binding.txtCount.visibility = View.GONE
                binding.btnDelete.visibility = View.GONE
                //Change the image stroke color to the default
                val color = ContextCompat.getColor(itemView.context, R.color.stroke_grey)
                binding.cardSuggestedproduct.strokeColor = color
                // Change the button cart's maximum height
                val layoutParam = binding.suggesteditemstatecardview.layoutParams
                layoutParam.height = Constants.PRODUCT_ITEM_CARD_MIN_HEIGHT.dpToPx(itemView.context)
                binding.suggesteditemstatecardview.layoutParams = layoutParam
            }

        }
    }

}
