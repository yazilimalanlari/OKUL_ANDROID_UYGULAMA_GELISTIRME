package english.words.database

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE username=:username LIMIT 1")
    fun getUser(username: String): User?

    @Insert
    fun add(vararg user: User)

    @Query("SELECT * FROM User")
    fun getUsers(): List<User>
}

@Entity
data class User (
    @PrimaryKey(autoGenerate=true) var id: Int? = null,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "password") val password: String?
)