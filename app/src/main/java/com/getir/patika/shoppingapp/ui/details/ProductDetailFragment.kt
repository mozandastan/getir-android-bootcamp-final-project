package com.getir.patika.shoppingapp.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.getir.patika.shoppingapp.R
import com.getir.patika.shoppingapp.data.models.Product
import com.getir.patika.shoppingapp.databinding.FragmentProductDetailBinding
import com.getir.patika.shoppingapp.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding
    private val viewModel: ProductViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        binding.incToolbar.txtToolbar.text = "Ürün Detayı"
        binding.incToolbar.btnCart.visibility = View.GONE
        binding.incToolbar.btnClose.visibility = View.VISIBLE
        binding.incToolbar.btnDeletecart.visibility = View.GONE
        binding.btnAddcart.visibility = View.VISIBLE
        binding.cardCartOps.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.incToolbar.btnCart.setOnClickListener{
            //BURAYA EĞER SEPETİNDE ÜRÜN VARSA ŞARTI GELECEK
            findNavController().navigate(R.id.action_productListingFragment_to_shoppingCartFragment2)
        }
        binding.incToolbar.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.selectedProduct.observe(viewLifecycleOwner) { selectedProduct ->
        var imgUrl: String? = ""
        selectedProduct.imageURL?.let {
            imgUrl = selectedProduct.imageURL } ?: run {
            imgUrl = selectedProduct.squareThumbnailURL
        }
        Glide.with(requireContext())
            .load(imgUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.imgProduct)

        binding.txtName.text = selectedProduct.name
        binding.txtPrice.text = selectedProduct.priceText

        var attText : String? = ""
        selectedProduct.attribute?.let {
            attText = selectedProduct.attribute } ?: run {
            attText = selectedProduct.shortDescription
        }
        binding.txtAtt.text = attText
        }

    }


}