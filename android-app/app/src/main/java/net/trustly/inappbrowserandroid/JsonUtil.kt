package net.trustly.inappbrowserandroid

import android.util.Base64
import com.google.gson.JsonObject
import java.nio.charset.StandardCharsets

object JsonUtil {

    private const val SEPARATOR: String = "\\."

    fun getJsonFromParameters(parameters: Map<String, String>) =
        buildJsonObjectSecond(parameters).toString()

    private fun buildJsonObjectSecond(data: Map<String, String>): JsonObject {
        val json = JsonObject()
        for ((key1, value) in data) {
            val keys = key1.split(SEPARATOR.toRegex()).toTypedArray()
            var current = json
            for (i in keys.indices) {
                val key = keys[i]
                if (i == keys.size - 1) {
                    current.addProperty(key, value)
                } else {
                    if (!current.has(key)) {
                        current.add(key, JsonObject())
                    }
                    current = current.getAsJsonObject(key)
                }
            }
        }
        return json
    }

    fun encodeStringToBase64(value: String): String =
        Base64.encodeToString(value.toByteArray(StandardCharsets.UTF_8), Base64.DEFAULT)

}