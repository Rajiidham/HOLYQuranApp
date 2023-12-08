package com.fahmi.quranapp.core.data.network.quran

import kotlinx.parcelize.Parcelize
import com.squareup.moshi.JsonClass
import android.os.Parcelable
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
@Parcelize
data class AyahResponse(

    @Json(name = "code")
    val code: Int? = null,

    @Json(name = "data")
    val quranEdition: List<QuranEditionItem>,

    @Json(name = "status")
    val status: String? = null
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class AyahsItem(

    @Json(name = "number")
    val number: Int? = null,

    @Json(name = "audio")
    val audio: String? = null,

    @Json(name = "text")
    val text: String? = null,

    @Json(name = "numberInSurah")
    val numberInSurah: Int? = null,

    ) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class QuranEditionItem(

    @Json(name = "number")
    val number: Int? = null,

    @Json(name = "englishName")
    val englishName: String? = null,

    @Json(name = "numberOfAyahs")
    val numberOfAyahs: Int? = null,

    @Json(name = "revelationType")
    val revelationType: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "ayahs")
    val listAyahs: List<AyahsItem>,

    @Json(name = "englishNameTranslation")
    val englishNameTranslation: String? = null
) : Parcelable
