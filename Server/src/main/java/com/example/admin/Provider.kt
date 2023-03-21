package com.example.admin

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.example.admin.data.room.Account.AccountEntity
import com.example.admin.data.room.AppDatabase
import com.example.admin.data.room.User.UserEntity
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class Provider: ContentProvider() {

    companion object {
        const val AUTHORITY = "com.example.admin"
        const val TABLE_USER = "user"
        const val TABLE_ACCOUNT = "account"
        const val TABLE_BRANCH = "Branch"
        const val TABLE_CART = "Cart"
        const val TABLE_CARTDETAILS = "CartDetails"
        const val TABLE_CHECKOUT = "Checkout"
        const val TABLE_ORDERDETAILS = "OrderDetails"
        const val TABLE_ORDER = "Order"
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
    }

    override fun onCreate(): Boolean {
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
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
               val userDao = AppDatabase.getInstance(context!!).userDao()
                val listUser = userDao.queryAllUser()
                return getUserList(listUser)
            }
            2 -> {
                //selection == "userName = ? and password = ?"
                if (selectionArgs?.get(0)!!.isNotEmpty() &&
                    (selection == "token = ?" && selection != null)) {
                    Log.d("PROVIDER",selectionArgs?.getOrNull(0).toString())
                    val token = selectionArgs?.getOrNull(0).toString()
                    val accountDao = AppDatabase.getInstance(context!!).accountDao()
                    val account = accountDao.queryAccountByToken(token)
                    return getAccount(account)
                } else if (selectionArgs?.get(0)!!.isNotEmpty() && selectionArgs?.get(1)!!.isNotEmpty() &&
                    (selection == "userName = ? and password = ?" && selection != null)) {
                    Log.d("Check", "${selection ?: "null"}")
                    Log.d("check",selectionArgs?.getOrNull(0).toString())
                    val userName = selectionArgs?.getOrNull(0).toString()
                    val password = selectionArgs?.getOrNull(1).toString()
                    val accountDao = AppDatabase.getInstance(context!!).accountDao()
                    val account = accountDao.queryAccountByUserNameAndPW(userName!!, hashPassword(password!!))
                    return getAccount(account)
                }
            }
        }
        return null
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
                val idAccount = account.idAccount.split("_")
                return ContentUris.withAppendedId(uri, idAccount[idAccount.size-1].toLong())
            }
        }
        return null

    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
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
                if(selectionArgs?.getOrNull(0)!!.isNotEmpty() && values?.getAsString("token")!!.isNotEmpty()) {
                    val accountDao = AppDatabase.getInstance(context!!).accountDao()
                    return accountDao.updateTokenAccount(values?.getAsString("token").toString() ?: "",selectionArgs?.getOrNull(0).toString())
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
}


