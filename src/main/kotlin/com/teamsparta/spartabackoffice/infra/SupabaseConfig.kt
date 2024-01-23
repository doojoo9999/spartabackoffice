package com.teamsparta.spartabackoffice.infra

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.serializer.KotlinXSerializer
import io.github.jan.supabase.storage.Storage
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
open class SupabaseConfig {

    @Value("\${supabase.url}")
    lateinit var supabaseUrl: String

    @Value("\${supabase.key}")
    lateinit var supabaseKey: String

    @Bean
    fun supabaseClient() : SupabaseClient {
        return createSupabaseClient(supabaseUrl, supabaseKey) {
            install (Storage)
        }
    }
//
//    val supabase = createSupabaseClient(supabaseUrl, supabaseKey) {
//        install(Storage)
//        defaultSerializer = KotlinXSerializer()
//    }

//    @Bean
//    open fun supabase () : SupabaseClient {
//        return createSupabaseClient(supabaseUrl, supabaseKey) {
//            install(Storage)}
//
//    }

}