package english.words.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [User::class, Word::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun wordDao(): WordDao
}