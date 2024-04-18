
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.getir.patika.shoppingapp.R
import com.getir.patika.shoppingapp.data.models.Product
import com.getir.patika.shoppingapp.databinding.ItemProductBinding
import com.getir.patika.shoppingapp.databinding.ItemSuggestedproductBinding

class VerticalAdapter(private var dataList: List<Product>) :
    RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder>() {

    fun updateData(newDataList: List<Product>) {
        dataList = newDataList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return VerticalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        val item = dataList[position]
        // Item öğelerini görünüme atama işlemleri burada yapılacak
        holder.bind(item)
        holder.itemView.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.action_productListingFragment_to_productDetailFragment2)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class VerticalViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            Glide.with(itemView.context)
                .load(product.imageURL)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgProduct)

            binding.txtName.text = product.name
            binding.txtPrice.text = product.price.toString()
            binding.txtAtt.text = product.attribute
        }
    }
}