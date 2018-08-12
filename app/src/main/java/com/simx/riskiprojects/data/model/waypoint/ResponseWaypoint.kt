package com.simx.riskiprojects.data.model.waypoint

import com.google.gson.annotations.SerializedName

data class ResponseWaypoint(

	@field:SerializedName("routes")
	val routes: List<RoutesItem?>? = null,

	@field:SerializedName("geocoded_waypoints")
	val geocodedWaypoints: List<GeocodedWaypointsItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)