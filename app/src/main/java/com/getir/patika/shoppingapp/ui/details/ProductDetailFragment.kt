package com.getir.patika.shoppingapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        setupToolbar()
        // Hide cart operation layout by default
        binding.cardCartOps.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonClickListeners()
        observeSelectedProduct()
        observeCartItems()
        observeTotalPrice()
    }
    private fun setupToolbar() {
        binding.incToolbar.apply {
            txtToolbar.text = getString(R.string.urun_detay)
            btnCart.visibility = View.GONE
            btnClose.visibility = View.VISIBLE
            btnDeletecart.visibility = View.GONE
        }
    }
    private fun setupButtonClickListeners() {
        binding.incToolbar.apply {
            //Navigate to the Cart screen
            btnCart.setOnClickListener {
                findNavController().navigate(R.id.action_productDetailFragment_to_shoppingCartFragment2)
            }
            //Navigate back
            btnClose.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        //Add item to the cart
        binding.btnAddcart.setOnClickListener {
            viewModel.selectedProduct.value?.let { product ->
                cartViewModel.addToCart(product)
            }
        }
        //Add item to the cart (increase)
        binding.btnPlus.setOnClickListener {
            viewModel.selectedProduct.value?.let { product ->
                cartViewModel.addToCart(product)
            }
        }
        //Remove item from the cart
        binding.btnDelete.setOnClickListener {
            viewModel.selectedProduct.value?.let { product ->
                cartViewModel.removeFromCart(product)
                // This control is not necessary at this time
                /*val itemCount = cartViewModel.getProductCount(product)
                if (itemCount > 1) {
                    cartViewModel.removeFromCart(product)*/
            }
        }
    }
    private fun observeSelectedProduct() {
        viewModel.selectedProduct.observe(viewLifecycleOwner) { selectedProduct ->
            //Change the UI according to selectedProduct
            displayProductDetails(selectedProduct)
        }
    }
    private fun observeCartItems() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            viewModel.selectedProduct.value?.let { product ->
                //Change the UI according to Cart info
                updateCartView(product)
            }
        }
    }
    private fun observeTotalPrice() {
        cartViewModel.totalPrice.observe(viewLifecycleOwner) { totalPrice ->
            //Change the totalPrice text according to totalPrice
            if (totalPrice > 0) {
                binding.incToolbar.btnCart.visibility = View.VISIBLE
                binding.incToolbar.btnText.text = getString(R.string.price_format, totalPrice)
            } else {
                binding.incToolbar.btnCart.visibility = View.GONE
            }
        }
    }
    private fun displayProductDetails(product: Product) {
        //Load image
        val imgUrl = product.imageURL ?: product.squareThumbnailURL
        Glide.with(requireContext())
            .load(imgUrl)
            .placeholder(R.drawable.img_defproduct)
            .into(binding.imgProduct)
        //Set texts
        binding.txtName.text = product.name
        binding.txtPrice.text = product.priceText
        binding.txtAtt.text = product.attribute ?: product.shortDescription
    }
    private fun updateCartView(product: Product) {
        // Check if the product is in the cart
        val itemCount = cartViewModel.getProductCount(product)
        if (itemCount > 0) {
            //Show Cartops and hide AddCart
            binding.txtCount.text = itemCount.toString()
            binding.cardCartOps.visibility = View.VISIBLE
            binding.btnAddcart.visibility = View.GONE
            //Change the button image according to count of that item
            if (itemCount == 1) {
                binding.btnDelete.setImageResource(R.drawable.img_trash)
            } else {
                binding.btnDelete.setImageResource(R.drawable.img_minus)
            }
            // If the product is not in the cart
        } else {
            binding.cardCartOps.visibility = View.GONE
            binding.btnAddcart.visibility = View.VISIBLE
        }
    }
}