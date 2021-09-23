package chat.app.client.json.create

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRequest(
    @SerialName("user_name")
    val userName: String,
    val text: String,
)
