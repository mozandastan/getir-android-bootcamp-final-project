package com.getir.patika.shoppingapp.ui.shoppingcart

import DividerItemDecoration
import android.app.AlertDialog
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
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
    private var formattedPrice: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingCartBinding.inflate(inflater, container, false)
        setupToolbar()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupAdapters()
        setupItemDecoration()
        observeViewModels()
    }

    private fun setupToolbar() {
        binding.incToolbar.apply {
            txtToolbar.text = getString(R.string.sepetim)
            btnCart.visibility = View.GONE
            btnClose.visibility = View.VISIBLE
            btnDeletecart.visibility = View.VISIBLE
        }
    }

    private fun setupListeners() {
        // Remove all the cart, firstly show a dialog
        binding.incToolbar.btnDeletecart.setOnClickListener {
            showRemoveAllCartDialog()
        }
        // Navigate back
        binding.incToolbar.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }
        // Buy products in the cart, show dialog
        binding.btnCheckout.setOnClickListener {
            showCheckoutDialog()
        }
    }

    private fun setupAdapters() {
        cartSuggestedAdapter = CartSuggestedAdapter(emptyList(), cartViewModel)
        binding.recSuggested.adapter = cartSuggestedAdapter

        cartAdapter = CartAdapter(emptyList(), cartViewModel)
        binding.recCart.adapter = cartAdapter
    }

    private fun setupItemDecoration() {
        // Item decorator between items
        val itemDecorator = DividerItemDecoration(requireContext())
        binding.recCart.addItemDecoration(itemDecorator)
    }

    private fun observeViewModels() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartList ->
            //Update cart adapter, refresh horizontal adapter according to CartItem List
            cartAdapter.updateData(cartList)
            cartSuggestedAdapter.refreshData()

            //if (cartList.isEmpty()) binding.incToolbar.btnDeletecart.visibility = View.GONE
            //else binding.incToolbar.btnDeletecart.visibility = View.VISIBLE
        }
        //Listen to the totalPrice to set text
        cartViewModel.totalPrice.observe(viewLifecycleOwner, Observer { totalPrice ->
            val formattedDouble = if (totalPrice < 0) {
                0.00
            } else {
                totalPrice
            }
            //Change the totalPrice text according to totalPrice
            formattedPrice = getString(R.string.price_format, formattedDouble)
            binding.totalLastprice.text = formattedPrice
            binding.totalPrice.text = formattedPrice
            binding.totalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        })
        //Listen to the horizontalProductList to update recyclerview Items
        productViewModel.horizontalProductList.observe(viewLifecycleOwner) { horizontalList ->
            cartSuggestedAdapter.updateData(horizontalList)
        }
    }

    private fun showCheckoutDialog() {
        //Set parameters of dialog after pressing the Checkout button
        showCustomDialog(
            title = "Siparişiniz başarıyla alındı",
            message = "Toplam sepet tutarı: $formattedPrice",
            positiveButtonText = "TAMAM",
            positiveButtonAction = {
                cartViewModel.clearCart()
                findNavController().navigate(R.id.action_shoppingCartFragment_to_productListingFragment)
            },
            negativeButtonText = null,
            negativeButtonAction = null
        )
    }

    private fun showRemoveAllCartDialog() {
        //Set parameters of dialog after pressing the Remove Cart button
        showCustomDialog(
            title = "Sepeti sil",
            message = "Sepetteki tüm ürünleri silmek istiyor musunuz?",
            positiveButtonText = "SİL",
            positiveButtonAction = {
                cartViewModel.clearCart()
                findNavController().navigate(R.id.action_shoppingCartFragment_to_productListingFragment)
            },
            negativeButtonText = "İPTAL",
            negativeButtonAction = null
        )
    }
    //Setting dialog
    private fun showCustomDialog(
        title: String,
        message: String,
        positiveButtonText: String,
        negativeButtonText: String?,
        positiveButtonAction: (() -> Unit)?,
        negativeButtonAction: (() -> Unit)?
    ) {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        //Create dialog
        val dialog = builder.create()
        dialog.show()
        //Dialog views
        val titleTextView = dialogView.findViewById<TextView>(R.id.txt_dialogtitle)
        val messageTextView = dialogView.findViewById<TextView>(R.id.txt_dialogbody)
        val positiveButton = dialogView.findViewById<Button>(R.id.btn_pos)
        val negativeButton = dialogView.findViewById<Button>(R.id.btn_neg)

        //Setting dialog parameters to view
        titleTextView.text = title
        messageTextView.text = message

        //Setting button parameters
        positiveButton.text = positiveButtonText
        positiveButton.setOnClickListener {
            positiveButtonAction?.invoke()
            dialog.dismiss()
        }
        if (negativeButtonText == null) {
            negativeButton.visibility = View.GONE
        } else {
            negativeButton.text = negativeButtonText
            negativeButton.setOnClickListener {
                negativeButtonAction?.invoke()
                dialog.dismiss()
            }
        }

    }

}