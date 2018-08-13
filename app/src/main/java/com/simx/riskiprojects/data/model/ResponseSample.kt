package com.simx.riskiprojects.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ResponseSample(
		@field:SerializedName("id")
		var id: String? = null,
		@field:SerializedName("Alamat")
		var alamat: String? = null,

		@field:SerializedName("Nama")
		var nama: String? = null,

		@field:SerializedName("Kelas")
		var kelas: String? = null,

		@field:SerializedName("Rawat_Inap")
		var rawatInap: String? = null,

		@field:SerializedName("Pimpinan")
		var pimpinan: String? = null,

		@field:SerializedName("Latitude")
		var latitude: String? = null,

		@field:SerializedName("Telpon")
		var telpon: String? = null,

		@field:SerializedName("Longitude")
		var longitude: String? = null,

		@field:SerializedName("Tipe")
		var tipe: String? = null,

		@field:SerializedName("Jenis")
		var jenis: String? = null,
		@field:SerializedName("Distance")
		var disctance: Double? = null
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(id)
		writeString(alamat)
		writeString(nama)
		writeString(kelas)
		writeString(rawatInap)
		writeString(pimpinan)
		writeString(latitude)
		writeString(telpon)
		writeString(longitude)
		writeString(tipe)
		writeString(jenis)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<ResponseSample> = object : Parcelable.Creator<ResponseSample> {
			override fun createFromParcel(source: Parcel): ResponseSample = ResponseSample(source)
			override fun newArray(size: Int): Array<ResponseSample?> = arrayOfNulls(size)
		}
	}

}