package com.example.admin.data.room.Account

import android.accounts.Account
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.admin.data.room.User.UserEntity

@Dao
interface AccountDao {

    @Insert
    fun insertAccount(accountEntity: AccountEntity)

    @Delete
    fun deleteAccount(accountEntity: AccountEntity)

    @Query("UPDATE account Set userName = :userName, password = :password, method = :method, idUser = :idUser, token = :token  WHERE idAccount = :idAccount")
    fun updateAccount(userName: String, password: String, method: String, idUser: String, token: String, idAccount: String)

    @Query("Select * from account where idAccount = :idAccount")
    fun queryUserByIdUser(idAccount: String): AccountEntity

    @Query("Select * from account")
    fun queryAllAccount(): List<AccountEntity>

    @Query("Select * from account where userName = :userName and password = :password")
    fun queryAccountByUserNameAndPW(userName: String, password: String): AccountEntity

    @Query("Select * from account where token = :token")
    fun queryAccountByToken(token: String): AccountEntity

    @Query("Update account set token = :token WHERE idAccount = :idAccount")
    fun updateTokenAccount(token: String, idAccount: String): Int

    @Query("Select * from account WHERE idUser = :idUser")
    fun queryAccountByidUser(idUser: String): AccountEntity

    @Query("Update account Set password = :password WHERE idAccount = :idAccount")
    fun updatePassWord(password: String, idAccount: String): Int

}