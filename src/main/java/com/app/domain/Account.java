package com.app.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {
    private String id;
    private String accountId;
    private Double cashBalance;
    private Double foodBalance;
    private Double mealBalance;

}
