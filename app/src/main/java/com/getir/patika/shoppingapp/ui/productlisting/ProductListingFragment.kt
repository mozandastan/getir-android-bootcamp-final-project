package com.getir.patika.shoppingapp.ui.productlisting

import VerticalAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
    private val viewModel: ProductViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()
    private lateinit var horizontalAdapter: HorizontalAdapter
    private lateinit var verticalAdapter: VerticalAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListingBinding.inflate(inflater,container,false)
        binding.incToolbar.txtToolbar.text = "Ürünler"
        binding.incToolbar.btnCart.visibility = View.VISIBLE
        binding.incToolbar.btnClose.visibility = View.GONE
        binding.incToolbar.btnDeletecart.visibility = View.GONE
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.incToolbar.btnCart.setOnClickListener{
            if(cartViewModel.totalPrice.value!! > 0){
                findNavController().navigate(R.id.action_productListingFragment_to_shoppingCartFragment2)
            }
            else{
                showToast()
            }
        }

        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recVertical.layoutManager = layoutManager
        horizontalAdapter = HorizontalAdapter(emptyList(),viewModel,cartViewModel)
        binding.recHorizontal.adapter = horizontalAdapter

        verticalAdapter = VerticalAdapter(emptyList(),viewModel,cartViewModel)
        binding.recVertical.adapter = verticalAdapter


        viewModel.horizontalProductList.observe(viewLifecycleOwner) { horizontalList ->
            horizontalAdapter.updateData(horizontalList)
        }
        viewModel.verticalProductList.observe(viewLifecycleOwner) { verticalList ->
            verticalAdapter.updateData(verticalList)
        }

        cartViewModel.totalPrice.observe(viewLifecycleOwner, Observer { totalPrice ->
                binding.incToolbar.btnText.text = getString(R.string.price_format, totalPrice)
        })

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            verticalAdapter.refreshData()
            horizontalAdapter.refreshData()
        }
    }
    private fun showToast() {
        Toast.makeText(context, "Sepetiniz boş. Sepetim sayfasına gidebilmek ürün ekleyin.", Toast.LENGTH_SHORT).show()
    }
}