package com.alekthehero.ece470.project1.datamodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ResponsePacket {
    private ResponseType responseType;
    private String message;
}
