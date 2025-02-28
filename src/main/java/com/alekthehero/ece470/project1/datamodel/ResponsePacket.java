package com.alekthehero.ece470.project1.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter @NoArgsConstructor
public class ResponsePacket {
    private ResponseType responseType;
    private String message;

    public ResponsePacket(ResponseType responseType, String message) {
        this.responseType = responseType;
        this.message = message;
    }
}
