package com.simx.riskiprojects.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ResponseSample(
		@field:SerializedName("id")
		val id: String? = null,
		@field:SerializedName("Alamat")
		val alamat: String? = null,

		@field:SerializedName("Nama")
		val nama: String? = null,

		@field:SerializedName("Kelas")
		val kelas: String? = null,

		@field:SerializedName("Rawat_Inap")
		val rawatInap: String? = null,

		@field:SerializedName("Pimpinan")
		val pimpinan: String? = null,

		@field:SerializedName("Latitude")
		val latitude: String? = null,

		@field:SerializedName("Telpon")
		val telpon: String? = null,

		@field:SerializedName("Longitude")
		val longitude: String? = null,

		@field:SerializedName("Tipe")
		val tipe: String? = null,

		@field:SerializedName("Jenis")
		val jenis: String? = null
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