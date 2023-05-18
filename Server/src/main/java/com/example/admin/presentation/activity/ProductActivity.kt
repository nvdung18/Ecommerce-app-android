package com.example.admin.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.R
import com.example.admin.data.model.Product
import com.example.admin.data.room.AppDatabase
import com.example.admin.data.room.branch.BranchEntity
import com.example.admin.data.room.branch.BranchViewModel
import com.example.admin.data.room.product.ProductEntity
import com.example.admin.data.room.product.ProductViewModel
import com.example.admin.databinding.ActivityBranchBinding
import com.example.admin.databinding.ActivityProductBinding
import com.example.admin.presentation.adapter.BranchAdapter
import com.example.admin.presentation.adapter.ProductAdapter
import com.example.admin.presentation.adapter.ProductItemClickAdapter
import com.google.gson.Gson

class ProductActivity: AppCompatActivity(),ProductItemClickAdapter {
    private lateinit var binding: ActivityProductBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var adapter: ProductAdapter
    private var latestIdProduct=""
    private var listAutoProduct= mutableListOf<String>()
    private var nameProductMap= mutableMapOf<String, String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        binding= ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= ViewModelProviders.of(this).get(ProductViewModel::class.java)

        viewModel.allProduct.observe(this,{List->
            List?.let {
                for (item in it){
                    listAutoProduct.add(item.idProduct)
                    listAutoProduct.add(item.nameProduct)
                    nameProductMap[item.idProduct]=item.nameProduct
                }
                adapter.updateList(it)
            }
        })

        initComponents()
    }

    private fun initComponents() {
        //adapter of AutoCompleteTextView
        val adapterAutoId=ArrayAdapter(this,android.R.layout.simple_list_item_1,listAutoProduct)
        binding.autoProduct.setAdapter(adapterAutoId)

        binding.autoProduct.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            var autoText=binding.autoProduct.text.toString()
            if (nameProductMap.getKeyByValue(autoText)!=null){
                autoText= nameProductMap.getKeyByValue(autoText)!!
            }
//            Log.e("auto",autoText)
            var product=viewModel.getProductById(autoText)
            var convertToListProduct= mutableListOf<ProductEntity>() //use for adapter.updateList(List<ProductEntity>)
            convertToListProduct.add(product)
            adapter.updateList(convertToListProduct)
        })

        //adapter of product
        setLoadAdapterProduct()

        binding.btnAddProduct.setOnClickListener {
            addProductActivity()
        }

        binding.imgBtnProductBack.setOnClickListener {
            back()
        }
//        addSampleProduct()
    }

    fun <K, V> Map<K, V>.getKeyByValue(value: V): K? {
        for ((key, entryValue) in this.entries) {
            if (entryValue == value) {
                return key
            }
        }
        return null
    }
    private fun setLoadAdapterProduct() {
        adapter=ProductAdapter(this,viewModel)
        adapter.setListener(this)
        binding.rvAllProduct.adapter=adapter
        binding.rvAllProduct.layoutManager=LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }


    private fun back() {
        var intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    private fun addProductActivity() {
        var listProduct=viewModel.allProduct.value
        if (listProduct != null && listProduct.size>0) {
            latestIdProduct= listProduct!![0].idProduct
        }else{
            latestIdProduct="SP01"
        }
        var intent=Intent(this,AddProductActivity::class.java)
        intent.putExtra("latestIdProduct",latestIdProduct)
        intent.putExtra("sizeOfListProduct",listProduct!!.size)
        startActivity(intent)
    }

    override fun onItemDeleteClick(product: ProductEntity) {
        viewModel.deleteProduct(product)
        Toast.makeText(this,"Delete Successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onItemUpdateClick(product: ProductEntity) {
        var intent=Intent(this,EditProduct::class.java)
        val gson = Gson()
        val productJson = gson.toJson(product)
        intent.putExtra("product",productJson)
        startActivity(intent)
    }

    override fun onDetailsItemClick(product: ProductEntity){
        var intent=Intent(this,DetailsProductActivity::class.java)
        val gson = Gson()
        val productJson = gson.toJson(product)
        intent.putExtra("product",productJson)
        startActivity(intent)
    }

    private fun addSampleProduct() {
        var productDao=AppDatabase.getInstance(this).productDao()
        val productList= mutableListOf<ProductEntity>()
//        Branch Yadou
        productList.add(ProductEntity(
            "SP01"
            , "Túi xách YADOU chất liệu da PU hình bán nguyệt màu trơn thời trang cao cấp"
            ,"https://firebasestorage.googleapis.com/v0/b/shop-ban-hang-c9314.appspot.com/o/image%20product%2Fyadou1.png?alt=media&token=c6652e55-5250-4f37-b4f3-21a3d64bd592"
            ,272000.0
            ,"Thiết kế đơn giản - chiếc túi tinh tế, nhẹ này rất được ưa chuộng, rất thiết thực."
            ,"female"
            ,0F
            ,0
            ,"B01"))
        productList.add(ProductEntity(
            "SP02"
            , "Túi xách tay/ đeo vai YADOU phối lông phong cách thu đông phương Tây cho nữ"
            ,"https://firebasestorage.googleapis.com/v0/b/shop-ban-hang-c9314.appspot.com/o/image%20product%2Fyadou2.png?alt=media&token=b32f90ea-1e5b-4872-bcfa-0699d67bd80a"
            ,123000.0
            ,"Túi đeo vai"
            ,"female"
            ,0F
            ,0
            ,"B01"))
        productList.add(ProductEntity(
            "SP03"
            , "Túi tote YADOU đeo vai/ xách tay bằng vải cotton sức chứa lớn họa tiết ngựa vằn trắng đen"
            ,"https://firebasestorage.googleapis.com/v0/b/shop-ban-hang-c9314.appspot.com/o/image%20product%2Fyadou3.png?alt=media&token=981c97a0-a823-40d6-9c71-7abd2962b0a6"
            ,1150000.0
            ,"túi chéo, túi dưới cánh tay"
            ,"female"
            ,0F
            ,0
            ,"B01"))
        productList.add(ProductEntity(
            "SP04"
            , "Túi xách đi học YADOU sức chứa lớn in hình gấu bắc cực có thể điều chỉnh dây phong cách Nhật Bản đơn giản cho nam và nữ"
            ,"https://firebasestorage.googleapis.com/v0/b/shop-ban-hang-c9314.appspot.com/o/image%20product%2Fyadou4.png?alt=media&token=36fee7f4-10e4-4e86-9401-6d942d3dd0c9"
            ,98000.0
            ,"Ngăn đựng điện thoại di động, ngăn đựng tài liệu"
            ,"female"
            ,0F
            ,0
            ,"B01"))

//        Branch Lesac
        productList.add(ProductEntity(
            "SP05"
            , "Túi xách nữ LESAC Sunita Bag"
            ,"https://firebasestorage.googleapis.com/v0/b/shop-ban-hang-c9314.appspot.com/o/image%20product%2FLesac1.png?alt=media&token=64320b1b-0f53-460e-a1ac-06161081be72"
            ,310000.0
            ,"Sunita Bag là túi đeo chéo da PU sần kèm dây đeo sợi nhỏ."
            ,"female"
            ,0F
            ,0
            ,"B02"))
        productList.add(ProductEntity(
            "SP06"
            , "Túi xách nữ LESAC Julia Bag"
            ,"https://firebasestorage.googleapis.com/v0/b/shop-ban-hang-c9314.appspot.com/o/image%20product%2FLesac2.png?alt=media&token=67918c18-974a-43ad-a1c1-8b8202c73a53"
            ,340000.0
            ,"Julia Bag là túi đeo chéo nắp gập da PU sần nhẹ có quai xách tay."
            ,"female"
            ,0F
            ,0
            ,"B02"))
        productList.add(ProductEntity(
            "SP07"
            , "Túi xách nữ LESAC Ivy Bag"
            ,"https://firebasestorage.googleapis.com/v0/b/shop-ban-hang-c9314.appspot.com/o/image%20product%2FLesac3.png?alt=media&token=fc93fdc1-f36f-4094-b87e-a3788358f7fb"
            ,450000.0
            ,"Ivy Bag là túi đeo vai da PU sần gồm 2 quai đeo với 1 màu đen. "
            ,"female"
            ,0F
            ,0
            ,"B02"))
        productList.add(ProductEntity(
            "SP08"
            , "Túi xách nữ LESAC Scarlet Bag"
            ,"https://firebasestorage.googleapis.com/v0/b/shop-ban-hang-c9314.appspot.com/o/image%20product%2FLesac4.png?alt=media&token=6d738ad1-f35b-4171-be36-e1d1b539e4d2"
            ,400000.0
            ,"Scarlet Bag là túi đeo chéo da PU mềm với dây đeo da phối dây xích."
            ,"female"
            ,0F
            ,0
            ,"B02"))

//        Branch IELGY
        productList.add(ProductEntity(
            "SP09"
            , "Túi Xách Da Đeo Dưới Cánh Tay Thời Trang Hàn Quốc Cao Cấp Cho Nữ"
            ,"https://firebasestorage.googleapis.com/v0/b/shop-ban-hang-c9314.appspot.com/o/image%20product%2Fielgy1.png?alt=media&token=fe208d3a-7b8a-4464-aaf8-64d8c04ff15d"
            ,298333.0
            ,""
            ,"female"
            ,0F
            ,0
            ,"B06"))
        productList.add(ProductEntity(
            "SP10"
            , "Túi xách IELGY Jinzhu đeo vai dây xích đựng son môi bề mặt kim cương cho nữ"
            ,"https://firebasestorage.googleapis.com/v0/b/shop-ban-hang-c9314.appspot.com/o/image%20product%2Fielgy2.png?alt=media&token=fc4ed39b-6f8f-45e7-b187-5fa9295a4b6f"
            ,312315.0
            ,"Kích thước không thể đặt điện thoại\n" +
                    "\n" +
                    "Phong cách: tươi mát và ngọt ngào\n" +
                    "\n" +
                    "Chất liệu: PU\n" +
                    "\n" +
                    "Các yếu tố phổ biến: hình thoi, dây chuyền"
            ,"female"
            ,0F
            ,0
            ,"B06"))
        productList.add(ProductEntity(
            "SP11"
            , "Túi xách đeo vai IELGY hình vuông nhỏ phối dây xích cá tính cho nữ"
            ,"https://firebasestorage.googleapis.com/v0/b/shop-ban-hang-c9314.appspot.com/o/image%20product%2Fielgy3.png?alt=media&token=ba425623-2d26-43ae-864c-6faeb5696841"
            ,407000.0
            ,"Phong cách: Xu hướng đường phố\n" +
                    "\n" +
                    "Chất liệu: PU\n" +
                    "\n" +
                    "Phong cách xu hướng hành lý: Túi vuông nhỏ"
            ,"female"
            ,0F
            ,0
            ,"B06"))
        productList.add(ProductEntity(
            "SP12"
            , "Túi xách IELGY vuông nhỏ kết cấu ren có quai xách ngọc trai và dây mắc xích hình thoi đeo chéo vai thời trang"
            ,"https://firebasestorage.googleapis.com/v0/b/shop-ban-hang-c9314.appspot.com/o/image%20product%2Fielgy4.png?alt=media&token=6801502d-cbdc-42f9-acb5-4a1c4cdce1b2"
            ,208000.0
            ,"Kích thước: 17 * 5 * 12.5CM\n" +
                    "\n" +
                    "Chất liệu: Bện\n" +
                    "Kiểu túi: túi vuông nhỏ"
            ,"female"
            ,0F
            ,0
            ,"B06"))

//        Insert Product
        for (product in productList){
            productDao.insertProduct(product)
        }

//        var list=productDao.getAllProduct()
//        Log.e("a",list[0].price.toString())
    }
}