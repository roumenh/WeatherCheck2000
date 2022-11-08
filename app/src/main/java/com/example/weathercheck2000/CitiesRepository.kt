import androidx.annotation.WorkerThread
import com.example.weathercheck2000.database.cities.Cities
import com.example.weathercheck2000.database.cities.CitiesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CitiesRepository(private val citiesDao: CitiesDao) {

    val allCities:Flow<MutableList<Cities>> = citiesDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insert(cities: Cities){
        CoroutineScope(Dispatchers.IO).launch {
            citiesDao.insertCity(cities)
        }
    }
    /*
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(foodItem: FoodItem){
        foodItemDao.delete(foodItem)
    }
    */
}