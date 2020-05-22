package com.api.demo.grid.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyListingsPOJO {
    private long userId;
    private long[] listingsId;
    private boolean withFunds;
}
