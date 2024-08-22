package com.app.controller;

import com.app.response.Response;
import com.app.domain.Transaction;
import com.app.service.AuthorizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorize")
@AllArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping
    public ResponseEntity<?> authorizeTransaction(@RequestBody Transaction transaction) {

        String resultCode = authorizationService.authorize(transaction);

        return new ResponseEntity<>(new Response(resultCode), HttpStatus.OK);
    }
}
