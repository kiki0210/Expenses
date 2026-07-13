package com.prova.demo.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

/**
 * genera e valida i token
 * */
@Service
class JwtService(
    @Value("\${app.jwt.secret}") private val secret: String,
    @Value("\${app.jwt.expiration-ms}") private val expirationMs: Long
) {

    private val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
    fun generaToken(userId: Long, email: String): String {
        val now = Date()
        val scadenza = Date(now.time + expirationMs)

        return Jwts.builder()
            .subject(email)
            .claim("userId", userId)
            .issuedAt(now)
            .expiration(scadenza)
            .signWith(key)
            .compact()
    }

    fun estraiEmail(token: String): String =
        parseClaims(token).subject

    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    fun estraiUserId(token: String): Long =
        parseClaims(token)["userId", Integer::class.java].toLong()

    fun isTokenValido(token: String): Boolean {
        return try {
            parseClaims(token)
            true
        } catch (e: Exception) {
            println("DEBUG - Validazione token fallita: ${e.javaClass.simpleName} - ${e.message}")
            e.printStackTrace()
            false
        }
    }

    private fun parseClaims(token: String) =
        Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload
}