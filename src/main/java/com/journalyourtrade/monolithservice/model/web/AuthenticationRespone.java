package com.journalyourtrade.monolithservice.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthenticationRespone {

    private final String jwt;
}
