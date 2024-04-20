package com.getir.patika.shoppingapp.ui.productlisting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.getir.patika.shoppingapp.R
import com.getir.patika.shoppingapp.data.models.Product

import com.getir.patika.shoppingapp.databinding.ItemSuggestedproductBinding
import com.getir.patika.shoppingapp.viewmodels.ProductViewModel

class HorizontalAdapter(private var dataList: List<Product>, private val viewModel: ProductViewModel) :
    RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder>() {

    fun updateData(newDataList: List<Product>) {
        dataList = newDataList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSuggestedproductBinding.inflate(inflater, parent, false)
        return HorizontalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            viewModel.setSelectedProduct(item)
            holder.itemView.findNavController().navigate(R.id.action_productListingFragment_to_productDetailFragment2)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class HorizontalViewHolder(private val binding: ItemSuggestedproductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {

            var imgUrl: String? = ""
            product.imageURL?.let {
                imgUrl = product.imageURL } ?: run {
                imgUrl = product.squareThumbnailURL
            }
            Glide.with(itemView.context)
                .load(imgUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgSuggestedproduct)

            binding.txtName.text = product.name
            binding.txtPrice.text = product.priceText
            binding.txtAtt.text = product.attribute ?: ""
        }
    }

}
