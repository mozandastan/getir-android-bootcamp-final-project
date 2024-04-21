package com.getir.patika.shoppingapp.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.getir.patika.shoppingapp.R
import com.getir.patika.shoppingapp.data.models.Product
import com.getir.patika.shoppingapp.databinding.FragmentProductDetailBinding
import com.getir.patika.shoppingapp.viewmodels.CartViewModel
import com.getir.patika.shoppingapp.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding
    private val viewModel: ProductViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()
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

        binding.incToolbar.btnCart.setOnClickListener {
            //BURAYA EĞER SEPETİNDE ÜRÜN VARSA ŞARTI GELECEK
            findNavController().navigate(R.id.action_productDetailFragment_to_shoppingCartFragment2)
        }
        binding.incToolbar.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.selectedProduct.observe(viewLifecycleOwner) { selectedProduct ->
            var imgUrl: String? = ""
            selectedProduct.imageURL?.let {
                imgUrl = selectedProduct.imageURL
            } ?: run {
                imgUrl = selectedProduct.squareThumbnailURL
            }
            Glide.with(requireContext())
                .load(imgUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgProduct)

            binding.txtName.text = selectedProduct.name
            binding.txtPrice.text = selectedProduct.priceText

            var attText: String? = ""
            selectedProduct.attribute?.let {
                attText = selectedProduct.attribute
            } ?: run {
                attText = selectedProduct.shortDescription
            }
            binding.txtAtt.text = attText
        }

        binding.btnAddcart.setOnClickListener {
            // Ürünü sepete ekle
            val selectedProduct = viewModel.selectedProduct.value
            selectedProduct?.let { product ->
                cartViewModel.addToCart(product)
            }
        }
        binding.btnPlus.setOnClickListener {
            // Ürünü sepete ekle
            val selectedProduct = viewModel.selectedProduct.value
            selectedProduct?.let { product ->
                cartViewModel.addToCart(product)
            }
        }
        binding.btnDelete.setOnClickListener {
            val selectedProduct = viewModel.selectedProduct.value
            selectedProduct?.let { product ->
                // Ürünün sepete eklenmiş olup olmadığını kontrol et
                val itemCount = cartViewModel.getProductCount(product)
                if (itemCount > 1) {
                    // Sepete birden fazla ürün eklenmişse, ürün sayısını bir azalt
                    cartViewModel.removeFromCart(product)
                } else {
                    // Sepete sadece bir ürün eklenmişse, ürünü sepetten sil
                    cartViewModel.removeFromCart(product)
                }
            }
        }

        // Sepetteki ürün sayısını ve sepete eklenmiş olup olmadığını kontrol et
        cartViewModel.cartItems.observe(viewLifecycleOwner, Observer { cartItems ->
            val selectedProduct = viewModel.selectedProduct.value
            selectedProduct?.let { product ->
                val itemCount = cartItems.count { it.id == product.id }
                if (itemCount > 0) {
                    // Sepete eklenen ürün sayısını göster
                    binding.txtCount.text = itemCount.toString()
                    binding.cardCartOps.visibility = View.VISIBLE
                    binding.btnAddcart.visibility = View.GONE
                    binding.btnDelete.setImageResource(R.drawable.img_minus)
                    //binding.incToolbar.btnCart.visibility = View.VISIBLE
                } else {
                    // Ürün sepete eklenmemişse görünümü gizle
                    binding.cardCartOps.visibility = View.GONE
                    binding.btnAddcart.visibility = View.VISIBLE
                    binding.btnDelete.setImageResource(R.drawable.img_trash)
                    //binding.incToolbar.btnCart.visibility = View.GONE
                }
            }
        })
        // Sepetteki toplam ürün fiyatını güncelleyin
        cartViewModel.totalPrice.observe(viewLifecycleOwner, Observer { totalPrice ->
            if(totalPrice > 0){
                binding.incToolbar.btnCart.visibility = View.VISIBLE
                binding.incToolbar.btnText.text = getString(R.string.price_format, totalPrice)
            }else{
                binding.incToolbar.btnCart.visibility = View.GONE
            }
        })
    }
}