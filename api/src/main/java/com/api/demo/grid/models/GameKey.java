package com.api.demo.grid.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.AccessLevel;

import java.util.Objects;
import javax.persistence.*;


@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonSerialize
@SuppressFBWarnings
public class GameKey {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private long id;

    @Column(unique = true)
    private String rKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Game game;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Sell sell;

    @OneToOne
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Auction auction;

    private String retailer;

    private String platform;

    public void setGame(Game game) {
        //prevent endless loop
        if (sameAsFormer(game)) return ;
        //set new user
        this.game = game;

        //set myself into new owner
        if (game!=null) game.addGameKey(this);
    }

    public void setSell(Sell sell){
        if (sameAsFormerSale(sell)) return;

        this.sell = sell;

        if (sell != null) sell.setGameKey(this);
    }

    private boolean sameAsFormerSale(Sell newSale) {return Objects.equals(newSale, sell); }

    private boolean sameAsFormer(Game newGame) {
        return Objects.equals(game, newGame);
    }

    public long getGameId(){
        if (game == null) return -1L;
        return this.game.getId();
    }
}
