

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.getir.patika.shoppingapp.R
import com.getir.patika.shoppingapp.data.models.Product
import com.getir.patika.shoppingapp.databinding.ItemProductBinding
import com.getir.patika.shoppingapp.viewmodels.CartViewModel
import com.getir.patika.shoppingapp.viewmodels.ProductViewModel
import com.getir.patika.shoppingapp.utils.dpToPx


class VerticalAdapter(private var dataList: List<Product>, private val viewModel: ProductViewModel,
                      private val cartViewModel: CartViewModel) : RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder>() {

    fun updateData(newDataList: List<Product>) {
        dataList = newDataList
        notifyDataSetChanged()
    }
    fun refreshData(){
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return VerticalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)

        //inner classtaki işlemler burada mı olmalı?
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class VerticalViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnPlus.setOnClickListener {
                val product = dataList[bindingAdapterPosition]
                cartViewModel.addToCart(product)
            }
            binding.btnDelete.setOnClickListener {
                val product = dataList[bindingAdapterPosition]
                val cartItems = cartViewModel.cartItems.value.orEmpty()
                val count = cartItems.count { it.id == product.id }

                if (count > 1) {
                    cartViewModel.removeFromCart(product)
                } else {
                    cartViewModel.removeFromCart(product)
                }
            }
            itemView.setOnClickListener {
                val product = dataList[bindingAdapterPosition]
                viewModel.setSelectedProduct(product)
                itemView.findNavController()
                    .navigate(R.id.action_productListingFragment_to_productDetailFragment2)
            }
        }
        fun bind(product: Product) {
            Glide.with(itemView.context)
                .load(product.imageURL)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgProduct)

            binding.txtName.text = product.name
            binding.txtPrice.text = product.priceText

            var attText : String? = ""
            product.attribute?.let {
                attText = product.attribute } ?: run {
                attText = product.shortDescription
            }
            binding.txtAtt.text = attText

            // Check if the product is in the cart
            val cartItems = cartViewModel.cartItems.value.orEmpty()
            val count = cartItems.count { it.id == product.id }
            if (count > 0) {
                binding.txtCount.text = count.toString()
                binding.txtCount.visibility = View.VISIBLE
                binding.btnDelete.visibility = View.VISIBLE

                val layoutParam = binding.itemstatecardview.layoutParams
                layoutParam.height = 96.dpToPx(itemView.context)
                binding.itemstatecardview.layoutParams = layoutParam

                if(count == 1){
                    binding.btnDelete.setImageResource(R.drawable.img_trash)
                }else{
                    binding.btnDelete.setImageResource(R.drawable.img_minus)
                }
            } else {
                binding.txtCount.visibility = View.GONE
                binding.btnDelete.visibility = View.GONE
                val layoutParam = binding.itemstatecardview.layoutParams
                layoutParam.height = 32.dpToPx(itemView.context)
                binding.itemstatecardview.layoutParams = layoutParam
            }
        }
    }
}