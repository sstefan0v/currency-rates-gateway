package com.sstefanov.currencies.gateway.controller.model.xml.request.current;

import lombok.Data;

@Data
public class CommandCurrent  {
    private Get get;
    private String id;
}



