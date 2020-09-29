package com.pibusa.tinderapp.data.repository

import com.pibusa.tinderapp.data.network.Api
import com.pibusa.tinderapp.data.network.SafeApiRequest
import com.pibusa.tinderapp.data.network.response.TinderProfileResponse

class MainRepository(private var api: Api) {

    suspend fun getTinderProfile(id: String): TinderProfileResponse {
        return api.getProfileDetails(id)
    }
}