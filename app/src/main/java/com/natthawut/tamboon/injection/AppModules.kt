package com.natthawut.tamboon.injection

import com.natthawut.tamboon.remote.ApiRemote
import com.natthawut.tamboon.repository.TamboonRepository

class AppModules {

    companion object {

        private val baseUrl = "http://192.168.1.3:8080/"

        @JvmStatic fun provideApiRemote(): ApiRemote {
            return ApiRemote(baseUrl)
        }

        @JvmStatic fun provideRepository(): TamboonRepository {
            return TamboonRepository(provideApiRemote())
        }
    }
}
