package com.musicify.app.data.internal.conf

internal object ServiceRegistry {
    private val endpoints = mapOf(
        "stream" to "aHR0cHM6Ly9waXBlZGFwaS5rYXZpbi5yb2Nrcy8=",
        "lyrics" to "aHR0cHM6Ly9scmNsaWIubmV0Lw==",
        "innertube" to "aHR0cHM6Ly9tdXNpYy55b3V0dWJlLmNvbS95b3V0dWJlaS92MS8="
    )

    private val credentials = mapOf(
        "gc_web" to "MjU5ODMyMzQ1NTQxLTI5azJxbDR2djMwdmRxaW45aG5zczlwM2YyanUyZGVjLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29t"
    )

    fun resolve(key: String): String {
        val encoded = endpoints[key] ?: return ""
        return String(android.util.Base64.decode(encoded, android.util.Base64.DEFAULT)).trim()
    }

    fun credential(key: String): String {
        val encoded = credentials[key] ?: return ""
        return String(android.util.Base64.decode(encoded, android.util.Base64.DEFAULT)).trim()
    }
}
