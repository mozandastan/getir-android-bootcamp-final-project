package com.getir.patika.shoppingapp.ui.productlisting

import VerticalAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.getir.patika.shoppingapp.R
import com.getir.patika.shoppingapp.databinding.FragmentProductListingBinding
import com.getir.patika.shoppingapp.viewmodels.CartViewModel
import com.getir.patika.shoppingapp.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class ProductListingFragment : Fragment() {

    private lateinit var binding: FragmentProductListingBinding
    private val productViewModel: ProductViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()
    private lateinit var horizontalAdapter: HorizontalAdapter
    private lateinit var verticalAdapter: VerticalAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListingBinding.inflate(inflater,container,false)
        setupToolbar()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCartButton()
        setupRecyclerViews()
        observeViewModels()
    }
    private fun setupToolbar() {
        binding.incToolbar.apply {
            txtToolbar.text = getString(R.string.urunler)
            btnCart.visibility = View.VISIBLE
            btnClose.visibility = View.GONE
            btnDeletecart.visibility = View.GONE
        }
    }
    private fun setupCartButton() {
        binding.incToolbar.btnCart.setOnClickListener {
            //Check if totalPrice greater than 0 to navigate Cart Screen
            if (cartViewModel.totalPrice.value!! > 0) {
                findNavController().navigate(R.id.action_productListingFragment_to_shoppingCartFragment2)
            } else {
                showToast(getString(R.string.cart_empty))
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    private fun setupRecyclerViews() {
        //Set the Vertical Recyclerview adapter and layout
        binding.recVertical.layoutManager = GridLayoutManager(requireContext(), 3)
        verticalAdapter = VerticalAdapter(emptyList(), productViewModel, cartViewModel)
        binding.recVertical.adapter = verticalAdapter
        //Set the Horizontal Recyclerview adapter and layout
        horizontalAdapter = HorizontalAdapter(emptyList(), productViewModel, cartViewModel)
        binding.recHorizontal.adapter = horizontalAdapter
    }
    private fun observeViewModels() {
        //Listen to the horizontalProductList to update recyclerview Items
        productViewModel.horizontalProductList.observe(viewLifecycleOwner) { horizontalList ->
            horizontalAdapter.updateData(horizontalList)
        }
        //Listen to the verticalProductList to update recyclerview Items
        productViewModel.verticalProductList.observe(viewLifecycleOwner) { verticalList ->
            verticalAdapter.updateData(verticalList)
        }
        //Listen to the totalPrice to set text
        cartViewModel.totalPrice.observe(viewLifecycleOwner) { totalPrice ->
            val formattedPrice = if (totalPrice < 0) {
                0.00
            } else {
                totalPrice
            }
            binding.incToolbar.btnText.text = getString(R.string.price_format, formattedPrice)
        }
        //Listen to the cartItems to refresh recyclerview Items
        cartViewModel.cartItems.observe(viewLifecycleOwner) {
            verticalAdapter.refreshData()
            horizontalAdapter.refreshData()
        }
    }
}