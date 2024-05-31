
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.namemanager.Name
import com.namemanager.NameDao
import com.namemanager.NameRoomDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NameDaoTest {

    private lateinit var nameDao: NameDao
    private lateinit var db: NameRoomDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, NameRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        nameDao = db.nameDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetName() = runBlocking {
        val name = Name("name")
        nameDao.insert(name)
        val allNames = nameDao.getAlphabetizedNames().first()
        assertEquals(allNames[0].name, name.name)
    }

    @Test
    @Throws(Exception::class)
    fun getAllNames() = runBlocking {
        val name = Name("Marcus")
        nameDao.insert(name)
        val name2 = Name("Bob")
        nameDao.insert(name2)
        val allNames = nameDao.getAlphabetizedNames().first()
        assertEquals(allNames[0].name, name.name)
        assertEquals(allNames[1].name, name2.name)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runBlocking {
        val name = Name("name")
        nameDao.insert(name)
        val name2 = Name("name2")
        nameDao.insert(name2)
        nameDao.deleteAll()
        val allNames = nameDao.getAlphabetizedNames().first()
        assertTrue(allNames.isEmpty())
    }
}
