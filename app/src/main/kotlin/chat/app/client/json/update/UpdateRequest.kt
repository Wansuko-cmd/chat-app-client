package chat.app.client.json.update

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateRequest(
    val id: String,
    @SerialName("user_name")
    val userName: String,
    val text: String
)
