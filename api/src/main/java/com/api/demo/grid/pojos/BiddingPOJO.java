package com.api.demo.grid.pojos;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonDeserialize
@JsonSerialize
public class BiddingPOJO {

    private String user;
    private String gameKey;
    private double price;
}
