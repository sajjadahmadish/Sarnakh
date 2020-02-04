package project.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class MarkerResponse(
    @SerializedName("markers")
    val markers: List<Marker>,
    @SerializedName("message")
    val message: String?,
    @SerializedName("response")
    val response: Int?
)

@Entity
data class Marker(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("lang")
    val lang: Double,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val adder: String
)