package com.bbopgi.nanoogoogae.global.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class OpenApiConfig {
    @Bean
    fun openApi(): OpenAPI {
        val bearerAuth: SecurityScheme = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("Bearer")
        val securityItem: SecurityRequirement = SecurityRequirement().addList("bearerAuth")

        return OpenAPI()
            .components(Components().addSecuritySchemes("bearerAuth", bearerAuth))
            .addSecurityItem(securityItem)
    }
}