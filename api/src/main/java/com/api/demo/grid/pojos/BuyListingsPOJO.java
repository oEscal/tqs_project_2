package com.api.demo.grid.pojos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BuyListingsPOJO {
    private long userId;
    private long[] listingsId;
    private boolean withFunds;
    public BuyListingsPOJO(long userId, long[] listingsId, boolean withFunds){
        this.userId = userId;
        this.listingsId = listingsId.clone();
        this.withFunds = withFunds;
    }
    public long[] getListingsId(){
        return this.listingsId.clone();
    }
    public void setListingsId(long[] listingsId){
        this.listingsId = listingsId.clone();
    }
}
