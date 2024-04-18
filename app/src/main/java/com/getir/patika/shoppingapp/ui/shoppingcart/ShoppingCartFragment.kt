package com.getir.patika.shoppingapp.ui.shoppingcart

import DividerItemDecoration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.getir.patika.shoppingapp.R
import com.getir.patika.shoppingapp.databinding.FragmentProductDetailBinding
import com.getir.patika.shoppingapp.databinding.FragmentShoppingCartBinding
import com.getir.patika.shoppingapp.ui.productlisting.HorizontalAdapter
import com.getir.patika.shoppingapp.viewmodels.ProductViewModel
import com.getir.patika.shoppingapp.viewmodels.ShoppingCartViewModel

class ShoppingCartFragment : Fragment() {

    private lateinit var binding: FragmentShoppingCartBinding
    private val viewModel: ShoppingCartViewModel by viewModels()
    private lateinit var cartAdapter: ShoppingCartAdapter
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

        binding.incToolbar.btnDeletecart.setOnClickListener{
            //BURADA KART SİLİNECEK
            findNavController().navigate(R.id.action_shoppingCartFragment_to_productListingFragment)
        }
        binding.incToolbar.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }

        cartAdapter = ShoppingCartAdapter(emptyList())
        binding.recCart.adapter = cartAdapter

        val itemDecorator = DividerItemDecoration(requireContext())
        binding.recCart.addItemDecoration(itemDecorator)

        viewModel.cartProductList.observe(viewLifecycleOwner) { cartList ->
            cartAdapter.updateData(cartList)
        }
    }
}