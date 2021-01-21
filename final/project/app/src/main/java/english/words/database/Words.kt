package english.words.database

import androidx.room.*

@Dao
interface WordDao {
    @Insert
    fun add(vararg word: Word)

    @Query("SELECT * FROM Word")
    fun getWords(): MutableList<Word>?

    @Query("UPDATE Word SET status=:status WHERE id=:id")
    fun changeStatus(id: Int, status: Int)

    @Query("DELETE FROM Word WHERE id=:id")
    fun delete(id: Int)
}

@Entity
data class Word (
    @PrimaryKey(autoGenerate=true) var id: Int? = null,
    @ColumnInfo(name = "tr") val tr: String?,
    @ColumnInfo(name = "en") val en: String?,
    @ColumnInfo(name = "status") var status: Int = 0
)