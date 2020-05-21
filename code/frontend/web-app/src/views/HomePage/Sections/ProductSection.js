import React from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";

// @material-ui/icons
import Chat from "@material-ui/icons/Chat";
import VerifiedUser from "@material-ui/icons/VerifiedUser";
import Fingerprint from "@material-ui/icons/Fingerprint";
// core components
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import InfoArea from "components/InfoArea/InfoArea.js";

import styles from "assets/jss/material-kit-react/views/landingPageSections/productStyle.js";

const useStyles = makeStyles(styles);

export default function ProductSection() {
  const classes = useStyles();
  return (
    <div className={classes.section}>
      <GridContainer justify="center">
        <GridItem xs={12} sm={12} md={8}>
          <h2 style={{
            background: "rgb(78,16,78)",
            background: "linear-gradient(0deg, rgba(78,16,78,1) 0%, rgba(68,18,73,1) 24%, rgba(83,19,83,1) 55%, rgba(131,39,139,1) 100%)",
            WebkitBackgroundClip: "text",
            WebkitTextFillColor: "transparent",
            fontWeight: "bold"
          }}><b>Join the Grid</b></h2>
          <h5 className={classes.description}>
            Engage in an extensive marketplace and join your fellow gamers in a quest for the search for 
            the best prices. Buy, Sell, Bid, Trade and have fun! Play nice, and remember <b>GL HF</b>
          </h5>
        </GridItem>
      </GridContainer>
      <div style={{marginTop:"45px"}}>
        <GridContainer>
          <GridItem xs={12} sm={12} md={3}>
            <InfoArea
              title="Buy"
              description="Search for sales on over 30000 games, from AAA to Indie, Action to Platformer, New to Old, just find the game you want and check if anyone's selling it!"
              icon = "fas fa-shopping-basket"
              color="#ff1cb7"
              vertical
            />
          </GridItem>
          <GridItem xs={12} sm={12} md={3}>
            <InfoArea
              title="Sell"
              description="Got gifted a key for a game you don't care about? Got a questionably obtained extra key? You've come to the right place to dispose of it and turn a profit!"
              color="#f03d7b"
              icon = "fas fa-money-bill-wave"
              vertical
            />
          </GridItem>
          <GridItem xs={12} sm={12} md={3}>
            <InfoArea
              title="Bid"
              description="Don't know what price to sell the game for? Start an auction! Wanna try to snag that awesome game for an exceptionally low price? Bid on an auction!"
              color="#f8a169"
              icon = "fas fa-gavel"
              vertical
            />
          </GridItem>
          <GridItem xs={12} sm={12} md={3}>
            <InfoArea
              title="Game"
              description="Divide details about your product or agency work into parts. Write a few lines about each one. A paragraph describing a feature will be enough."
              color="#fef049"
              icon = "fas fa-gamepad"
              vertical
            />
          </GridItem>
        </GridContainer>
      </div>
    </div>
  );
}
