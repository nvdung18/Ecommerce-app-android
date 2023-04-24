package com.example.admin

import android.R
import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import com.example.admin.data.model.BrandAndModel
import com.example.admin.data.model.CartDetailsAndProduct
import com.example.admin.data.model.OrderAndOrderdetails
import com.example.admin.data.model.CartDetailsAndProductAndBranch
import com.example.admin.data.model.StatusOrder
import com.example.admin.data.room.AppDatabase
import com.example.admin.data.room.account.AccountEntity
import com.example.admin.data.room.branch.BranchEntity
import com.example.admin.data.room.cart.CartEntity
import com.example.admin.data.room.cartDetails.CartDetailsEntity
import com.example.admin.data.room.checkout.CheckoutEntity
import com.example.admin.data.room.order.OrderDao
import com.example.admin.data.room.product.ProductDao
import com.example.admin.data.room.detailsOrder.OrderDetailsEntity
import com.example.admin.data.room.order.OrderEntity
import com.example.admin.data.room.promocode.PromocodeEntity
import com.example.admin.data.room.user.UserEntity
import com.google.gson.Gson
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*


class Provider: ContentProvider() {

    private var IDCheckout: String = ""
    private var IDOrder: String = ""

    companion object {
        const val AUTHORITY = "com.example.admin"
        const val TABLE_USER = "user"
        const val TABLE_ACCOUNT = "account"
        const val TABLE_BRANCH = "Branch"
        const val TABLE_CART = "Cart"
        const val TABLE_CARTDETAILS = "CartDetails"
        const val TABLE_CHECKOUT = "Checkout"
        const val TABLE_ORDERDETAILS = "OrderDetails"
        const val TABLE_ORDER = "OrderTable"
        const val TABLE_PAYMENT = "Payment"
        const val TABLE_PRODUCT = "Product"
        const val TABLE_PROMODECODE = "PromoCode"
        const val TABLE_RECEIPT = "Receipt"

        val URI_TABLE_USER = "content://${AUTHORITY}/${TABLE_USER}"
        val URI_TABLE_ACCOUNT = "content://${AUTHORITY}/${TABLE_ACCOUNT}"
        val URI_TABLE_BRANCH = "content://${AUTHORITY}/${TABLE_BRANCH}"
        val URI_TABLE_CART = "content://${AUTHORITY}/${TABLE_CART}"
        val URI_TABLE_CARTDETAILS = "content://${AUTHORITY}/${TABLE_CARTDETAILS}"
        val URI_TABLE_CHECKOUT = "content://${AUTHORITY}/${TABLE_CHECKOUT}"
        val URI_TABLE_ORDERDETAILS = "content://${AUTHORITY}/${TABLE_ORDERDETAILS}"
        val URI_TABLE_ORDER = "content://${AUTHORITY}/${TABLE_ORDER}"
        val URI_TABLE_PAYMENT = "content://${AUTHORITY}/${TABLE_PAYMENT}"
        val URI_TABLE_PRODUCT = "content://${AUTHORITY}/${TABLE_PRODUCT}"
        val URI_TABLE_PROMODECODE = "content://${AUTHORITY}/${TABLE_PROMODECODE}"
        val URI_TABLE_RECEIPT = "content://${AUTHORITY}/${TABLE_RECEIPT}"

        private lateinit var uriMatcher: UriMatcher
        private lateinit var instace:AppDatabase

        private lateinit var orderDao:OrderDao
        private lateinit var productDao:ProductDao

    }

    override fun onCreate(): Boolean {
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        instace= AppDatabase.getInstance(context!!)

        orderDao= instace.orderDao()
        productDao= instace.productDao()
//        1->12 URI TABLE NOT PARAMETERS
        uriMatcher.addURI(AUTHORITY, TABLE_USER, 1)
        uriMatcher.addURI(AUTHORITY, TABLE_ACCOUNT, 2)
        uriMatcher.addURI(AUTHORITY, TABLE_BRANCH, 3)
        uriMatcher.addURI(AUTHORITY, TABLE_CART, 4)
        uriMatcher.addURI(AUTHORITY, TABLE_CARTDETAILS, 5)
        uriMatcher.addURI(AUTHORITY, TABLE_CHECKOUT, 6)
        uriMatcher.addURI(AUTHORITY, TABLE_ORDERDETAILS, 7)
        uriMatcher.addURI(AUTHORITY, TABLE_ORDER, 8)
        uriMatcher.addURI(AUTHORITY, TABLE_PAYMENT, 9)
        uriMatcher.addURI(AUTHORITY, TABLE_PRODUCT, 10)
        uriMatcher.addURI(AUTHORITY, TABLE_PROMODECODE, 11)
        uriMatcher.addURI(AUTHORITY, TABLE_RECEIPT, 12)


        uriMatcher.addURI(AUTHORITY, TABLE_USER+"/*", 13)
        uriMatcher.addURI(AUTHORITY, TABLE_ACCOUNT+"/*", 14)
        uriMatcher.addURI(AUTHORITY, TABLE_BRANCH+"/*", 15)
        uriMatcher.addURI(AUTHORITY, TABLE_CART+"/*", 16)
        uriMatcher.addURI(AUTHORITY, TABLE_CARTDETAILS+"/*", 17)
        uriMatcher.addURI(AUTHORITY, TABLE_CHECKOUT+"/*", 18)
        uriMatcher.addURI(AUTHORITY, TABLE_ORDERDETAILS+"/*", 19)
        uriMatcher.addURI(AUTHORITY, TABLE_ORDER+"/*", 20)
        uriMatcher.addURI(AUTHORITY, TABLE_PAYMENT+"/*", 21)
        uriMatcher.addURI(AUTHORITY, TABLE_PRODUCT+"/*", 22)
        uriMatcher.addURI(AUTHORITY, TABLE_PROMODECODE+"/*", 23)
        uriMatcher.addURI(AUTHORITY, TABLE_RECEIPT+"/*", 24)

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        when(uriMatcher.match(uri)) {
            1 -> {
                if(selection == null) {
                    val userDao = AppDatabase.getInstance(context!!).userDao()
                    val listUser = userDao.queryAllUser()
                    return getUserList(listUser)
                } else if(selection == "token = ?") {
                    val token = selectionArgs?.getOrNull(0).toString()
                    val accountDao = AppDatabase.getInstance(context!!).accountDao()
                    val userDao = AppDatabase.getInstance(context!!).userDao()
                    val account = accountDao.queryAccountByToken(token)
                    val user = userDao.queryUserByIdUser(account.idUser)
                    return getUser(user)
                }
            }
            2 -> {
                //selection == "userName = ? and password = ?"
                if (selectionArgs?.get(0)!!.isNotEmpty() &&
                    selection == "token = ?" && selection != null) {
                    Log.d("PROVIDER",selectionArgs?.getOrNull(0).toString())
                    val token = selectionArgs?.getOrNull(0).toString()
                    val accountDao = AppDatabase.getInstance(context!!).accountDao()
                    val account = accountDao.queryAccountByToken(token)
                    if (account != null) {
                        return getAccount(account)
                    }
                } else if(selectionArgs?.get(0)!!.isNotEmpty() && selection == "email = ?" && selection != null) {
                    val userDao = AppDatabase.getInstance(context!!).userDao()
                    val user = userDao.queryUserByEmail(selectionArgs?.get(0)!!.toString().trim())
                    if(user != null) {
                        return getUser(user)
                    }
                } else if (selectionArgs?.get(0)!!.isNotEmpty() && selectionArgs?.get(1)!!.isNotEmpty() && selection == "userName = ? and password = ?" && selection != null) {
                    val userName = selectionArgs?.getOrNull(0).toString()
                    val password = selectionArgs?.getOrNull(1).toString()
                    val accountDao = AppDatabase.getInstance(context!!).accountDao()
                    val account = accountDao.queryAccountByUserNameAndPW(userName!!, hashPassword(password!!))
                    if (account != null) {
                        return getAccount(account)
                    }
                }
            }
            3-> {
                if(selection == null && selectionArgs == null) {
                    val branchDao = AppDatabase.getInstance(context!!).branchDao()
                    val listDataBranch = branchDao.getAllBranchNotLive()
                    if(listDataBranch.size != 0) {
                        return getBranchList(listDataBranch)
                    }
                }
            }

            4 -> {
                if(selection == "idAccount = ?") {
                    if(selectionArgs?.get(0)!!.isNotEmpty()) {
                        val idAccount = selectionArgs?.get(0).toString()
                        val cartDao = AppDatabase.getInstance(context!!).cartDao()
                        val cartEntity = cartDao.queryIdCartByIdAccount(idAccount)
                        return getCartEntity(cartEntity)
                    }
                }
            }

            5 -> {
                if(selection == "idCart = ?") {
                    if(selectionArgs?.get(0)!!.isNotEmpty()) {
                        val idCart = selectionArgs?.get(0).toString()
                        val cartDetailsDao = AppDatabase.getInstance(context!!).cartDetailsDao()
                        val listCartDetails_Product = cartDetailsDao.queryAllCartDetails_Product(idCart)
                        return getCartDetails_Product(listCartDetails_Product)
                    }
                }
            }

            6 -> {
                if(selection == "idCheckout = ?"){
                    if(selectionArgs?.get(0)!!.isNotEmpty()) {
                        val idCheckout = selectionArgs?.get(0).toString()
                        val checkoutDao = AppDatabase.getInstance(context!!).checkoutDao()
                        val checkOutEntity = checkoutDao.getCheckoutById(idCheckout)
                        return getCheckoutById(checkOutEntity)
                    }
                }
            }

            8 -> {
                if(selection == "idAccount = ?"&&selectionArgs!=null){
                    val idAccount=selectionArgs?.get(0).toString()
                    var cursor=getOrderByIdAccount(idAccount)
//                    var orderList=orderDao.getAllOrderByIdJoinOrDetails_App()
//                    Log.e("a",orderList.toString())
                    return cursor
                }
            }

            10 -> {
                if(selection == null && selectionArgs == null) {
                    val productDao = AppDatabase.getInstance(context!!).productDao()
                    val listAllProduct = productDao.getAllProductByBranch()
                    if(listAllProduct.size != 0) {
                        return getProductList(listAllProduct)
                    }
                }
            }

            11 -> {
                if(selection == "idPromoCode = ?") {
                    if(selectionArgs?.get(0)!!.isNotEmpty()) {
                        val idPromocode = selectionArgs?.get(0).toString()
                        val promocodeDao = AppDatabase.getInstance(context!!).promocodeDao()
                        val promoCode = promocodeDao.getPromocodeBiIdServer(idPromocode)
                        if(promoCode != null) {
                            return getPromoCode(promoCode)
                        }
                    }
                }
            }
        }
        return null
    }

    private fun getOrderByIdAccount(idAccount:String):Cursor? {
        //get order list include order and order details
        var orderList=orderDao.getAllOrderByIdJoinOrDetails_App(idAccount)
        //get idProduct from order to get information of product include product and branch
        var idProductList= mutableListOf<String>()
        for(orderItem in orderList){
            if(!idProductList.contains(orderItem.idProduct)){
                idProductList.add(orderItem.idProduct)
            }
        }

        //create map to get product and branch
        val productMap= mutableMapOf<String, BrandAndModel>()
        for (idProductItem in idProductList){
            productMap[idProductItem]=productDao.getProductJoinBranchByIdProduct(idProductItem)
        }

        return getOrderListByidAccountCursor(orderList,productMap)
    }


    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when(uriMatcher.match(uri)) {
            1 -> {
                val userDao = AppDatabase.getInstance(context!!).userDao()
                val idofUser = getIdUserAuto()
                val user = UserEntity(
                    idofUser,
                    values?.getAsString("name") ?: "",
                    values?.getAsString("gender") ?: "",
                    values?.getAsString("address") ?: "",
                    values?.getAsInteger("phonenumber") ?: 0,
                    values?.getAsString("email") ?: "",
                    values?.getAsInteger("role") ?: 0,
                )
                userDao.insertUser(user)
                val idUser = user.idUser.split("_")
                val idUserForeignKey = user.idUser
                return ContentUris.withAppendedId(uri, idUser[idUser.size-1].toLong())
            }
            2 -> {
                val accountDao = AppDatabase.getInstance(context!!).accountDao()
                val userDao = AppDatabase.getInstance(context!!).userDao()
                val listUser = userDao.queryAllUser()
                val lastUser = listUser[listUser.size-1]
                val idofAccount = getIdAccountAuto()
                val hashPassword = hashPassword(values?.getAsString("password").toString())
                val account = AccountEntity(
                    getIdAccountAuto(),
                    values?.getAsString("userName") ?: "",
                    hashPassword ?: "",
                    values?.getAsString("method") ?: "default",
                    lastUser.idUser,
                    values?.getAsString("token") ?: ""
                )
                accountDao.insertAccount(account)
                val cartDao = AppDatabase.getInstance(context!!).cartDao()
                val idCart = cartDao.insertCart(CartEntity(
                    getIdCartAuto(),
                    account.idAccount
                ))
                val idAccount = account.idAccount.split("_")
                return ContentUris.withAppendedId(uri, idAccount[idAccount.size-1].toLong())
            }
            5 -> {
                val cartDetailsDao = AppDatabase.getInstance(context!!).cartDetailsDao()
                val quantity = values?.getAsString("quantity")!!.toInt()
                val idCart = values?.getAsString("idCart").toString()
                val idProduct = values?.getAsString("idProduct").toString()
                var cartDetail:Long = 0
                val existCartDetails = checkProductCartDetailExist(quantity, idCart, idProduct)
                if(existCartDetails != null) {
                    val newquantity = quantity.toInt()+existCartDetails.quantity.toInt()
                    cartDetailsDao.updateQuantityCartDetailsByIdCart_IdProduct(newquantity.toString(), idCart, idProduct)
                } else {
                    cartDetail = cartDetailsDao.insertCardDetail(CartDetailsEntity(
                        quantity,
                        idCart,
                        idProduct
                    ))
                }
                return ContentUris.withAppendedId(uri, cartDetail)

            }
            6 -> {
                val checkoutDao = AppDatabase.getInstance(context!!).checkoutDao()
                val idCheckout = getIdCheckoutAuto()
                IDCheckout = idCheckout
                val recipientName = values?.getAsString("recipientName").toString()
                val recipientEmail = values?.getAsString("recipientEmail").toString()
                val recipientAddress = values?.getAsString("recipientAddress").toString()
                val total = values?.getAsString("total").toString()
                val idAccount = values?.getAsString("idAccount").toString()
                val checkout = CheckoutEntity(
                    idCheckout,
                    recipientName,
                    0,
                    recipientEmail,
                    recipientAddress,
                    idAccount
                )
                val id = checkoutDao.insertCheckout(checkout)
                return ContentUris.withAppendedId(uri, id)
            }

            7 -> {
                val orderDetailsDao = AppDatabase.getInstance(context!!).orderDetailsDao()
                val idProduct = values?.getAsString("idProduct").toString()
                val total = values?.getAsDouble("total")!!.toDouble()
                val quantity = values?.getAsInteger("quantity").toInt()
                val OrderDetailsEntity = OrderDetailsEntity(
                    IDOrder,
                    idProduct,
                    total,
                    quantity
                )
                val id = orderDetailsDao.insertOrderDetails(OrderDetailsEntity)
                return ContentUris.withAppendedId(uri, id)
            }

            8 -> {
                val orderDao = AppDatabase.getInstance(context!!).orderDao()
                val idOrder = getIdOrderAuto()
                IDOrder = idOrder
                Log.d("IDCheckout", "${IDCheckout}")
                var formatterDate = SimpleDateFormat( "dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                var formatterTime = SimpleDateFormat( "HH:mm:ss", Locale.getDefault());
                var now = Date();
                var date=formatterDate.format(now)

                val listStatus= ArrayList<StatusOrder>()
                listStatus.add(StatusOrder(Date(date),"Wait for confirmation"))

                Log.e("List",listStatus.toString())

                val gson = Gson()
                val json = gson.toJson(listStatus)


                val orderNotes = values?.getAsString("orderNotes").toString()
                val deliveryCharges = values?.getAsDouble("deliveryCharges")!!.toDouble()
                val total = values?.getAsDouble("total")!!.toDouble()
                val idAccount = values?.getAsString("idAccount").toString()
                val idPayment = values?.getAsString("idPayment").toString()
                val idPromocode = values?.getAsString("idPromocode").toString()
                val order = OrderEntity(
                    idOrder,
                    json,
                    orderNotes,
                    deliveryCharges,
                    total,
                    idAccount,
                    idPayment,
                    idPromocode,
                    IDCheckout
                )
                val id = orderDao.insertOrder(order)
                return ContentUris.withAppendedId(uri, id)
            }

            10 -> {

            }
        }
        return null

    }




    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        when(uriMatcher.match(uri)) {
            5 -> {
                if(selection == "idCart = ?") {
                    val cartDetailsDao = AppDatabase.getInstance(context!!).cartDetailsDao()
                    val idDeleteCartDetailEntity = cartDetailsDao.deleteCartDetails(selectionArgs?.get(0)!!.toString())
                    context?.contentResolver?.notifyChange(Uri.parse(URI_TABLE_CARTDETAILS), null)
                    return idDeleteCartDetailEntity
                }
            }
        }
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        when(uriMatcher.match(uri)) {
            1 -> {

            }
            2 -> {
                if(selectionArgs?.getOrNull(0)!!.isNotEmpty() && selection == "idAccount = ?" && selection != null) {
                    val accountDao = AppDatabase.getInstance(context!!).accountDao()
                    return accountDao.updateTokenAccount(values?.getAsString("token").toString() ,selectionArgs?.getOrNull(0).toString())
                } else if (selectionArgs?.getOrNull(0)!!.isNotEmpty() && selection == "email = ?" && selection != null) {
                    val userDao = AppDatabase.getInstance(context!!).userDao()
                    Log.d("UpdatePassword", "${selectionArgs?.getOrNull(0)!!.isNotEmpty()}")
                    val accountDao = AppDatabase.getInstance(context!!).accountDao()
                    val account = accountDao.queryAccountByidUser(values?.getAsString("idUser").toString())
                    val hashPassword = hashPassword(values?.getAsString("password").toString())
                    return accountDao.updatePassWord(hashPassword, account.idAccount.toString().trim())
                }
            }

            5 -> {
                if(selection == "idCart = ? and idProduct = ?" && selection != null) {
                    Log.d("Check", "${values?.getAsInteger("quantity")!!.toInt()}")
                    val cartDetailsDao = AppDatabase.getInstance(context!!).cartDetailsDao()
                    val idCart = selectionArgs?.get(0).toString()
                    val idProduct = selectionArgs?.get(1).toString()
                    val quantity = values?.getAsInteger("quantity")!!.toInt()
                    val idUpdateCardDetails = cartDetailsDao.updateProductInCartDetails(CartDetailsEntity(
                        quantity,
                        idCart,
                        idProduct
                    ))
                    context?.contentResolver?.notifyChange(Uri.parse(URI_TABLE_CARTDETAILS), null)
                    return idUpdateCardDetails
                }

            }
        }
        return 0
    }

    private fun getIdUserAuto(): String {
        val userDao = AppDatabase.getInstance(context!!).userDao()
        val listUser: List<UserEntity> = userDao.queryAllUser()
        var idUser: String = ""
        if(listUser.isEmpty()) {
            idUser = "idUser_1"
        } else {
            val lastUser = listUser[listUser.size-1].idUser
            val listUserNew = lastUser.split("_")
            var newId = listUserNew[listUserNew.size-1].toInt() + 1
            idUser = "idUser_${newId}"
        }
        return idUser
    }

    private fun getIdAccountAuto(): String {
        val accountDao = AppDatabase.getInstance(context!!).accountDao()
        val listAccount: List<AccountEntity> = accountDao.queryAllAccount()
        var idAccount: String = ""
        if(listAccount.isEmpty()) {
            idAccount = "idAccount_1"
        } else {
            val lastAccount = listAccount[listAccount.size-1].idAccount
            val listAccountNew = lastAccount.split("_")
            var newId = listAccountNew[listAccountNew.size-1].toInt() + 1
            idAccount = "idAccount_${newId}"
        }
        return idAccount
    }

    private fun getIdCartAuto(): String {
        val cartDao = AppDatabase.getInstance(context!!).cartDao()
        val listCart: List<CartEntity> = cartDao.queryAllCart()
        var idCart: String = ""
        if(listCart.isEmpty()) {
            idCart = "idCart_1"
        } else {
            val lastCart = listCart[listCart.size-1].idCart
            val listCartNew = lastCart.split("_")
            var newId = listCartNew[listCartNew.size-1].toInt() + 1
            idCart = "idCart_${newId}"
        }
        return idCart
    }

    private fun getIdCheckoutAuto(): String {
        val checkoutDao = AppDatabase.getInstance(context!!).checkoutDao()
        val listCheckout: List<CheckoutEntity> = checkoutDao.getCheckout()
        var idCheckout: String = ""
        if(listCheckout.isEmpty()) {
            idCheckout = "idCheckout_1"
        } else {
            val lastCheckout = listCheckout[listCheckout.size-1].idCheckout
            val listCheckoutNew = lastCheckout.split("_")
            var newId = listCheckoutNew[listCheckoutNew.size-1].toInt() + 1
            idCheckout = "idCheckout_${newId}"
        }
        return idCheckout
    }

    private fun getIdOrderAuto(): String {
        val orderDao = AppDatabase.getInstance(context!!).orderDao()
        val listOrder: List<OrderEntity> = orderDao.getAllOrderNotLive()
        var idOrder: String = ""
        if(listOrder.isEmpty()) {
            idOrder = "idOrder_1"
        } else {
            val lastOrder = listOrder[listOrder.size-1].idOrder
            val listOrderNew = lastOrder.split("_")
            var newId = listOrderNew[listOrderNew.size-1].toInt() + 1
            idOrder = "idOrder_${newId}"
        }
        return idOrder
    }

    private fun getUserList(listUser: List<UserEntity>): Cursor? {
        val cursor = MatrixCursor(
            arrayOf<String>(
                "idUser",
                "fullName",
                "gender",
                "address",
                "phoneNumber",
                "email",
                "role"
            )
        )

        for (user in listUser) {
            cursor.addRow(
                arrayOf<Any>(
                    user.idUser,
                    user.fullName,
                    user.gender,
                    user.address,
                    user.phoneNumber,
                    user.email,
                    user.role
                )
            )
        }
        return cursor
    }

    fun hashPassword(password: String): String {
        val bytes = password.toByteArray(StandardCharsets.UTF_8)
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(bytes)
        return hash.joinToString(separator = "") { "%02x".format(it) }
    }


    private fun getAccount(account: AccountEntity): Cursor? {
        val cursor = MatrixCursor(
            arrayOf<String>(
                "idAccount",
                "userName",
                "password",
                "method",
                "idUser",
                "token"
            )
        )
        cursor.addRow(
            arrayOf<Any>(
                account.idAccount,
                account.userName,
                account.password,
                account.method,
                account.idUser,
                account.token
            )
        )
        return cursor
    }

    private fun getUser(user: UserEntity): Cursor? {
        val cursor = MatrixCursor(
            arrayOf<String>(
                "idUser",
                "fullName",
                "gender",
                "address",
                "phoneNumber",
                "email",
                "role"
            )
        )

        cursor.addRow(
            arrayOf<Any>(
                user.idUser,
                user.fullName,
                user.gender,
                user.address,
                user.phoneNumber,
                user.email,
                user.role
            )
        )
        return cursor
    }

    private fun getProductList(listAllProduct: List<BrandAndModel>): Cursor? {
        val cursor = MatrixCursor(
            arrayOf<String>(
                "idProduct",
                "nameProduct",
                "image",
                "price",
                "description",
                "type",
                "sale",
                "soldQuantity",
                "idBranch",
                "nameBranch"
            )
        )

        for (product in listAllProduct) {
            cursor.addRow(
                arrayOf<Any>(
                    product.idProduct,
                    product.nameProduct,
                    product.image,
                    product.price,
                    product.description,
                    product.type,
                    product.sale,
                    product.soldQuantity,
                    product.idBranch,
                    product.nameBranch
                )
            )
        }
        return cursor
    }

    private fun getBranchList(listBranchNew: List<BranchEntity>?): Cursor? {
        val cursor = MatrixCursor(
            arrayOf<String>(
                "idBranch",
                "nameBranch",
            )
        )

        if (listBranchNew != null) {
            for (branch in listBranchNew) {
                cursor.addRow(
                    arrayOf<Any>(
                        branch.idBranch,
                        branch.nameBranch,
                    )
                )
            }
        }
        return cursor
    }

    private fun getCartEntity(cartEntity: CartEntity): Cursor? {
        val cursor = MatrixCursor(
            arrayOf<String>(
                "idCart",
                "idAccount"
            )
        )

        cursor.addRow(
            arrayOf<Any>(
                cartEntity.idCart,
                cartEntity.idAccount
            )
        )
        return cursor
    }

    private fun getCartDetails_Product(listcartdetailsProduct: List<CartDetailsAndProductAndBranch>): Cursor? {
        val cursor = MatrixCursor(
            arrayOf<String>(
                "quantity",
                "idCart",
                "idProduct",
                "nameProduct",
                "image",
                "price",
                "description",
                "type",
                "sale",
                "soldQuantity",
                "idBranch",
                "nameBranch"
            )
        )

        if(listcartdetailsProduct.size != 0) {
            for (cartdetails_product in listcartdetailsProduct) {
                cursor.addRow(
                    arrayOf<Any>(
                        cartdetails_product.quantity,
                        cartdetails_product.idCart,
                        cartdetails_product.idProduct,
                        cartdetails_product.nameProduct,
                        cartdetails_product.image,
                        cartdetails_product.price,
                        cartdetails_product.description,
                        cartdetails_product.type,
                        cartdetails_product.sale,
                        cartdetails_product.soldQuantity,
                        cartdetails_product.idBranch,
                        cartdetails_product.nameBranch
                    )
                )
            }
        }

        return cursor
    }

    private fun checkProductCartDetailExist(quantity: Int, idCart: String, idProduct: String): CartDetailsEntity? {
        val cartDetailsDao = AppDatabase.getInstance(context!!).cartDetailsDao()
        val cartDetailsEntity = cartDetailsDao.queryAllCartDetailsByIdCart_IdProduct(idCart, idProduct)
        if(cartDetailsEntity != null) {
            return cartDetailsEntity
        } else {
            return null
        }
    }

    private fun getOrderListByidAccountCursor(orderList: List<OrderAndOrderdetails>,productMap:Map<String,BrandAndModel>):Cursor?{
        val cursor = MatrixCursor(
            arrayOf<String>(
                "idOrder",
                "status",
                "orderNotes",
                "deliveryCharges",
                "productMoney",
                "idPayment",
                "idPromoCode",
                "idCheckout",
                "idProduct",
                "quantity",
                "description",
                "discountPercent",
                "idBranch",
                "nameProduct",
                "image",
                "price",
                "nameBranch",
            )
        )

        if (orderList != null && productMap!=null) {
            for (order in orderList) {
                var product=productMap.get(order.idProduct)
                cursor.addRow(
                    arrayOf<Any>(
                        order.idOrder,
                        order.status,
                        order.orderNotes,
                        order.deliveryCharges,
                        order.productMoney,
                        order.idPayment,
                        order.idPromoCode,
                        order.idCheckout,
                        order.idProduct,
                        order.quantity,
                        order.description,
                        order.discountPercent,
                        product!!.idBranch,
                        product!!.nameProduct,
                        product!!.image,
                        product!!.price,
                        product!!.nameBranch,
                    )
                )
            }
        }
        return cursor
    }

    private fun getCheckoutById(checkoutEntity: CheckoutEntity): Cursor? {
        val cursor = MatrixCursor(
            arrayOf<String>(
                "idCheckout",
                "recipientName",
                "recipientPhoneNumber",
                "recipientEmail",
                "recipientAddress",
            )
        )

        if (checkoutEntity != null) {
            cursor.addRow(
                arrayOf<Any>(
                    checkoutEntity.idCheckout,
                    checkoutEntity.recipientEmail,
                    checkoutEntity.recipientPhoneNumber,
                    checkoutEntity.recipientEmail,
                    checkoutEntity.recipientAddress
                )
            )
        }
        return cursor
    }
    private fun getPromoCode(promoCode: PromocodeEntity): Cursor? {
        val cursor = MatrixCursor(
            arrayOf<String>(
                "idPromoCode",
                "description",
                "discountPercent"
            )
        )

        cursor.addRow(
            arrayOf<Any>(
                promoCode.idPromoCode,
                promoCode.description,
                promoCode.discountPercent
            )
        )
        return cursor
    }
}