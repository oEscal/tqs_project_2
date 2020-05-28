package com.api.demo.grid.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

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
    @JsonIgnore
    private String realKey;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Game game;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Sell sell;

    @OneToOne(orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
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

    private boolean sameAsFormer(Game newGame) {
        return Objects.equals(game, newGame);
    }

    public void setSell(Sell sell){
        if (sameAsFormerSell(sell)) return ;
        this.sell = sell;
        if (sell!=null) sell.setGameKey(this);
    }

    private boolean sameAsFormerSell(Sell newSell) {
        return Objects.equals(sell, newSell);
    }

    @EqualsAndHashCode.Include
    public long getGameId(){
        if (game == null) return -1L;
        return this.game.getId();
    }

    @EqualsAndHashCode.Include
    public String getGameName(){
        if (game == null) return "";
        return this.game.getName();
    }

    @EqualsAndHashCode.Include
    public String getGamePhoto(){
        if (game == null) return "";
        return this.game.getCoverUrl();
    }
}
