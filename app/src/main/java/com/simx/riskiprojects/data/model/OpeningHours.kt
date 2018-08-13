package com.simx.riskiprojects.data.model

import com.google.gson.annotations.SerializedName


data class OpeningHours(

	@field:SerializedName("open_now")
	val openNow: Boolean? = null
)