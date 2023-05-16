package com.example.admin.data.room.user

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.admin.data.room.account.AccountEntity
import com.example.admin.data.room.branch.BranchEntity

@Dao
interface UserDao {

    @Insert
    fun insertUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)

    @Query("UPDATE user Set fullName = :fullName, gender = :gender, address = :address, phoneNumber = :phoneNumber, email = :email  WHERE idUser = :idUser")
    fun updateUser(idUser: String, fullName: String, gender: String, address: String, phoneNumber: String, email: String)

    @Query("Select * from user where idUser = :idUser")
    fun queryUserByIdUser(idUser: String): UserEntity

    @Query("Select * from user")
    fun queryAllUser(): List<UserEntity>

    @Query("Select * from user where email = :email")
    fun queryUserByEmail(email: String): UserEntity

    @Query("Delete from user")
    fun deleteAllUser()

    @Query("Select * from user where role = 1")
    fun getAdminByRole(): UserEntity

    @Update
    fun updateAdmin(userEntity: UserEntity)
}