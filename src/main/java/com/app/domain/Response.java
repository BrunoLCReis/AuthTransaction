package com.app.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response {
    private String code;

    public Response(String code) {
        this.code = code;
    }

}