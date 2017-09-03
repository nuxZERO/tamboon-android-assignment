package com.natthawut.tamboon.injection

import co.omise.android.Client
import com.natthawut.tamboon.repository.remote.ApiRemote
import com.natthawut.tamboon.repository.TamboonRepository

class AppModules {

    companion object {

        private val BASE_URL = "http://192.168.1.3:8080/"
        private val OMISE_PKEY = "pkey_test_594vvefmwizkte5ooue"

        @JvmStatic
        fun provideApiRemote(): ApiRemote {
            return ApiRemote(BASE_URL)
        }

        @JvmStatic
        fun provideRepository(): TamboonRepository {
            return TamboonRepository(provideApiRemote(), provideOmiseClient())
        }

        @JvmStatic
        fun provideOmiseClient(): Client {
            return Client(OMISE_PKEY)
        }

    }
}
