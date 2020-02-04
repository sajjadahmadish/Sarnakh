package project.data.local.db.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import project.data.model.Marker

@Dao
interface MarkerDao : BaseDao<Marker> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMarker(post: List<Marker>)

    @Query("select * from Marker")
    fun loadAll(): Flowable<List<Marker>>

    @Query("delete from Marker where id not in (:idList)")
    fun deleteList(idList: List<String>)

    @Query("DELETE FROM Marker")
    fun truncate()

}
