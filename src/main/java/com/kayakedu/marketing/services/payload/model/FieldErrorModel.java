package com.kayakedu.marketing.services.payload.model;

@lombok.Getter
@lombok.AllArgsConstructor
public class FieldErrorModel {

    private String path;
    private String error;
}
