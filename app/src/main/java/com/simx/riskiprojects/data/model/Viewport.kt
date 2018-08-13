package com.simx.riskiprojects.data.model

import com.google.gson.annotations.SerializedName

data class Viewport(

	@field:SerializedName("southwest")
	val southwest: Southwest? = null,

	@field:SerializedName("northeast")
	val northeast: Northeast? = null
)