package com.natthawut.tamboon.remote

import com.google.gson.annotations.SerializedName

class Organization {

    var id = 0

    var name: String? = null

    @SerializedName("logo_url")
    var logoUrl: String? = null

}
