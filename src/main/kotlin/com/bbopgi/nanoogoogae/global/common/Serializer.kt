package com.bbopgi.nanoogoogae.global.common

class Serializer() {
    fun buildMap(pair: Pair<String, *>,): HashMap<String, *> {
        val map = HashMap<String, Any>()

        when(pair.second) {
            is String -> map[pair.first] = pair.second as String
            is Int -> map[pair.first] = pair.second as Int
            else -> map[pair.first] = pair.second as Any
        }
        return map
    }
}