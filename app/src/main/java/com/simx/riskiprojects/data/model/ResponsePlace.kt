package com.simx.riskiprojects.data.model


import com.google.gson.annotations.SerializedName

data class ResponsePlace(

	@field:SerializedName("html_attributions")
	val htmlAttributions: List<Any?>? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null,
	@field:SerializedName("error_message")
	val error_message: String? = null
)