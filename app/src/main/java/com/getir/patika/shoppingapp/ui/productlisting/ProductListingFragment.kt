package com.getir.patika.shoppingapp.ui.productlisting

import VerticalAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.getir.patika.shoppingapp.R
import com.getir.patika.shoppingapp.databinding.FragmentProductListingBinding
import com.getir.patika.shoppingapp.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class ProductListingFragment : Fragment() {

    private lateinit var binding: FragmentProductListingBinding
    private val viewModel: ProductViewModel by viewModels()
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
            //BURAYA EĞER SEPETİNDE ÜRÜN VARSA ŞARTI GELECEK
            findNavController().navigate(R.id.action_productListingFragment_to_shoppingCartFragment2)
        }

        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recVertical.layoutManager = layoutManager
        horizontalAdapter = HorizontalAdapter(emptyList())
        binding.recHorizontal.adapter = horizontalAdapter

        verticalAdapter = VerticalAdapter(emptyList())
        binding.recVertical.adapter = verticalAdapter


        viewModel.horizontalProductList.observe(viewLifecycleOwner) { horizontalList ->
            horizontalAdapter.updateData(horizontalList)
        }
        viewModel.verticalProductList.observe(viewLifecycleOwner) { verticalList ->
            verticalAdapter.updateData(verticalList)
        }
    }
}