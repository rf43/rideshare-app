package org.rideshareinfo.rideshareinfo

data class User(
        val name: String,
        val status: String,
        val readyTime: Long,
        val latLongLocation: Pair<Double, Double>
)
