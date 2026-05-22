package com.musicify.app.data.internal.conf

internal object ServiceRegistry {
    private val endpoints = mapOf(
        "stream" to "aHR0cHM6Ly9waXBlZGFwaS5rYXZpbi5yb2Nrcy8=",
        "lyrics" to "aHR0cHM6Ly9scmNsaWIubmV0Lw==",
        "fallback_stream" to "aHR0cHM6Ly9waXBlZGFwaS5pbnZpZGlvdXMuaW8v",
        "fallback_lyrics" to "aHR0cHM6Ly9scmNsaWIubmV0Lw=="
    )

    private val meta = mapOf(
        "app_id" to "com.musicify.app",
        "version" to "0.1.0",
        "channel" to "beta"
    )

    fun resolve(key: String): String {
        val encoded = endpoints[key] ?: return ""
        return String(android.util.Base64.decode(encoded, android.util.Base64.DEFAULT))
    }

    fun getMeta(key: String): String = meta[key] ?: ""

    fun getInstances(): List<String> {
        return listOf(
            resolve("stream"),
            resolve("fallback_stream")
        )
    }
}
