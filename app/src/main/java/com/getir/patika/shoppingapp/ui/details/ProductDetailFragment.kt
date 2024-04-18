package com.getir.patika.shoppingapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.getir.patika.shoppingapp.R
import com.getir.patika.shoppingapp.databinding.FragmentProductDetailBinding
import com.getir.patika.shoppingapp.databinding.FragmentProductListingBinding

class ProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        binding.incToolbar.txtToolbar.text = "Ürün Detayı"
        binding.incToolbar.btnCart.visibility = View.GONE
        binding.incToolbar.btnClose.visibility = View.VISIBLE
        binding.incToolbar.btnDeletecart.visibility = View.GONE
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
    }

}