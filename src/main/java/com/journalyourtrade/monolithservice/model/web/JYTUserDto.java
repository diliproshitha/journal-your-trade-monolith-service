package com.journalyourtrade.monolithservice.model.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JYTUserDto {

    @Email
    private String email;

    @NotBlank
    @JsonIgnore
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private List<String> roles;
}
