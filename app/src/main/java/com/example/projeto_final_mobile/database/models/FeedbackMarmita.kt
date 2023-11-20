import androidx.room.Entity

@Entity
data class FeedbackMarmita(
    val idFeedBackMarmita: Long,
    var textFeedBackMarmita: String,
    var photo_id: String
)