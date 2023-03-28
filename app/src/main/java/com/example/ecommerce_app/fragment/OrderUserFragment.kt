package com.example.ecommerce_app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecommerce_app.R
import com.example.ecommerce_app.databinding.FragmentOrderUserBinding

class OrderUserFragment : Fragment {

    private lateinit var binding: FragmentOrderUserBinding

    companion object {
        private const val TAG = "ORDER_USER_TAG"

        //receive data from activity to load product
        public fun newInstance(
            idstateProduct: String,
            stateProduct: String,
            idAc: String
        ): OrderUserFragment {
            val fragment = OrderUserFragment()
            val args = Bundle()
            args.putString("idstateProduct", idstateProduct)
            args.putString("stateProduct", stateProduct)
            args.putString("idAc", idAc)
            fragment.arguments = args
            return fragment
        }
    }

    private var idstateProduct = ""
    private var stateProduct = ""
    private var idAc = ""

    //arraylist to hold product


    //constructor
    constructor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if(args != null) {
            idstateProduct = args.getString("idstateProduct")!!
            stateProduct = args.getString("stateProduct")!!
            idAc = args.getString("idAc")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderUserBinding.inflate(LayoutInflater.from(context), container, false)

        if(stateProduct == "Cho Xac Nhan") {
            loadProductConfirm()
        } else if (stateProduct == "Cho Lay Hang") {
            loadProductToGetGood()
        } else if (stateProduct == "Da Lay Hang") {
            loadProductToGotGood()
        } else if (stateProduct == "Da Thanh Toan") {
            loadProductToMadePay()
        }
        return binding.root
    }

    private fun loadProductToMadePay() {
        binding.cmTv.text = "${stateProduct}"
    }

    private fun loadProductToGotGood() {
        binding.cmTv.text = "${stateProduct}"
    }

    private fun loadProductToGetGood() {
        binding.cmTv.text = "${stateProduct}"
    }

    private fun loadProductConfirm() {
        binding.cmTv.text = "${stateProduct}"
    }
}