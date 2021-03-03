/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// defines constants to match the query values our web service expects:
enum class MarsApiFilter(val value: String) { SHOW_RENT("rent"), SHOW_BUY("buy"), SHOW_ALL("all") }

private const val BASE_URL = "https://mars.udacity.com/"

// Use the Moshi Builder to create a Mosh object with the KotlinJsonAdapterFactory
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

// Create Retrofit object
private val retrofit = Retrofit.Builder()
        // Add an instance of ScalarsConverterFactory
        //.addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        // Add Base URL
        .baseUrl(BASE_URL)
        // Built to create Retrofit object
        .build()

// Create MarsApiService interface
interface  MarsApiService {
    // Annotate with @GET and specify endpoint for JSON real estate response
    @GET("realestate")
    // Method to request the JSON Response string
    // Added a query filter parameter so that we can filter properties based on the MarsApiFilter enum values
    suspend fun getProperties(@Query("filter") type: String): //Response<String>
            // Create the retrofit call object that will start the HTTP request
            //Call<List<MarsProperty>>
            List<MarsProperty>

}

// Create Public object called MarsApi to expose Retrofit service to the rest of the app
object  MarsApi {
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}



