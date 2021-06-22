package com.kel3.yfaexpress.controller.keycloak;

import com.kel3.yfaexpress.model.dto.keycloak.TokenResponseDto;
import com.kel3.yfaexpress.model.dto.request.LoginRequestDto;
import com.kel3.yfaexpress.webclient.KeyCloakWebClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class KeycloakController {
    private final KeyCloakWebClient keyCloakWebClient;

    public KeycloakController(KeyCloakWebClient keyCloakWebClient) {
        this.keyCloakWebClient = keyCloakWebClient;
    }

    @PostMapping(path = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<TokenResponseDto> getToken(LoginRequestDto loginRequestDto){
        TokenResponseDto tokenResponseDto = keyCloakWebClient.getToken(loginRequestDto);
        return ResponseEntity.ok(tokenResponseDto);
    }

}
