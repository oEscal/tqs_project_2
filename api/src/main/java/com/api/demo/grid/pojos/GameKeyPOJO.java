package com.api.demo.grid.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GameKeyPOJO {
    private String key;
    private long gameId;
    private String retailer;
    private String platform;
}
