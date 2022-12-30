package com.cnureddy.newapifeedstaticdata.model

data class Movie(
	val movie: List<MovieItem?>? = null
)

data class MovieItem(
	val imageUrl: String? = null,
	val name: String? = null,
	val category: String? = null,
	val desc: String? = null
)

