package com.getir.patika.shoppingapp.ui.shoppingcart

import DividerItemDecoration
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.getir.patika.shoppingapp.R
import com.getir.patika.shoppingapp.databinding.FragmentShoppingCartBinding
import com.getir.patika.shoppingapp.viewmodels.CartViewModel
import com.getir.patika.shoppingapp.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentShoppingCartBinding
    private val cartViewModel: CartViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartSuggestedAdapter: CartSuggestedAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoppingCartBinding.inflate(inflater, container, false)
        binding.incToolbar.txtToolbar.text = "Sepetim"
        binding.incToolbar.btnCart.visibility = View.GONE
        binding.incToolbar.btnClose.visibility = View.VISIBLE
        binding.incToolbar.btnDeletecart.visibility = View.VISIBLE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.incToolbar.btnDeletecart.setOnClickListener {
            cartViewModel.clearCart()
        }
        binding.incToolbar.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }

        cartSuggestedAdapter = CartSuggestedAdapter(emptyList(), cartViewModel)
        binding.recSuggested.adapter = cartSuggestedAdapter

        cartAdapter = CartAdapter(emptyList(), cartViewModel)
        binding.recCart.adapter = cartAdapter

        val itemDecorator = DividerItemDecoration(requireContext())
        binding.recCart.addItemDecoration(itemDecorator)

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartList ->
            cartAdapter.updateData(cartList)
            cartSuggestedAdapter.refreshData()

            if (cartList.isEmpty()) {
                binding.incToolbar.btnDeletecart.visibility = View.GONE
            } else {
                binding.incToolbar.btnDeletecart.visibility = View.VISIBLE
            }
        }
        cartViewModel.totalPrice.observe(viewLifecycleOwner, Observer { totalPrice ->
            binding.totalLastprice.text = getString(R.string.price_format, totalPrice)
            binding.totalPrice.text = getString(R.string.price_format, totalPrice)
        })

        productViewModel.horizontalProductList.observe(viewLifecycleOwner) { horizontalList ->
            cartSuggestedAdapter.updateData(horizontalList)
        }

        binding.btnCheckout.setOnClickListener {
            showDialog()
            cartViewModel.clearCart()
        }
    }
    private fun showDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Siparişiniz başarıyla alındı.")
        builder.setMessage("Toplam sepet tutarı: ${cartViewModel.totalPrice.value}")
        builder.setPositiveButton("Anasayfa") { dialog, which ->
            findNavController().navigate(R.id.action_shoppingCartFragment_to_productListingFragment)
        }
        val dialog = builder.create()
        dialog.show()
    }
}