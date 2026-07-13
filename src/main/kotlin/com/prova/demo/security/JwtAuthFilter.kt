package com.prova.demo.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtService:JwtService,
    private val userDetailsService: CustomUserDetailService
) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI
        return request.method == "OPTIONS" ||
            path.startsWith("/api/auth/") ||
            path.startsWith("/h2-console/")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
       val authHeader = request.getHeader("Authorization")
        println("DEBUG - Header ricevuto: [$authHeader]")
        if (authHeader== null || !authHeader.startsWith("Bearer ")) {
            println("DEBUG - Condizione fallita, salto autenticazione")
            filterChain.doFilter(request, response)
            return
        }
        val token = authHeader.substring(7)
        println("DEBUG - Token estratto: [$token]")

        if (jwtService.isTokenValido(token)) {
            val email = jwtService.estraiEmail(token)
            val userDetails = userDetailsService.loadUserByUsername(email)

            val authentication = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities
            )
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}
