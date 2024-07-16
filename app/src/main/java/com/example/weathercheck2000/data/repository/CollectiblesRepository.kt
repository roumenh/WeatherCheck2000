package com.example.weathercheck2000.data.repository

import com.example.weathercheck2000.database.collectibles.Collectible
import com.example.weathercheck2000.database.collectibles.CollectiblesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Repository for the collectibles
 */
interface CollectiblesRepository {

    val allCollectibles: Flow<MutableList<Collectible>>
    //fun collectibleExists(code: String): Boolean
    fun insert(collectible: Collectible)
    fun delete(collectible: Collectible)
}

class CollectiblesRepositoryImpl(private val collectiblesDao: CollectiblesDao) : CollectiblesRepository {

    override val allCollectibles: Flow<MutableList<Collectible>> = collectiblesDao.getAll()

   /* override fun collectibleExists(code: String): Boolean {
        return collectiblesDao.collectibleExists(code = code)
    }
*/
    override fun insert(collectible: Collectible) {
        CoroutineScope(Dispatchers.IO).launch {
            if (!collectiblesDao.collectibleExists(collectible.code)){
                collectiblesDao.insertCollectible(collectible = collectible)
            }

        }
    }

    override fun delete(collectible: Collectible) {
        CoroutineScope(Dispatchers.IO).launch {
            collectiblesDao.deleteCollectible(collectible = collectible)
        }
    }

}