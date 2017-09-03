package com.natthawut.tamboon.repository.remote

import com.google.gson.annotations.SerializedName

class Charity {

    var id = 0

    var name: String? = null

    @SerializedName("logo_url")
    var logoUrl: String? = null

}
