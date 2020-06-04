import React from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import CardMedia from '@material-ui/core/CardMedia';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';

// react component for creating beautiful carousel
import Carousel from "react-slick";

// @material-ui/icons
import Chat from "@material-ui/icons/Chat";
import VerifiedUser from "@material-ui/icons/VerifiedUser";
import Fingerprint from "@material-ui/icons/Fingerprint";
import LocationOn from "@material-ui/icons/LocationOn";

// core components
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import InfoArea from "components/InfoArea/InfoArea.js";

import Card from "components/Card/Card.js";
import CardBody from "components/Card/CardBody.js";
import CardFooter from "components/Card/CardFooter.js";
import CardHeader from "components/Card/CardHeader.js";

import Button from "components/CustomButtons/Button.js";

import image1 from "assets/img/bg.jpg";
import image2 from "assets/img/bg2.jpg";
import image3 from "assets/img/bg3.jpg";
import image4 from "assets/img/NFS-Heat.jpg";

import styles from "assets/jss/material-kit-react/views/landingPageSections/productStyle.js";

import {
  Link
} from "react-router-dom";

const useStyles = makeStyles(styles);

export default function GameSection(props) {
  const classes = useStyles();
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: false,
  };

  var games = props.games
  console.log(props.games)
  var carousel = []
  var highlight = null
  var maxcarousel = null

  if (games != null && games != [] && games.length != 0) {
    for (var i = 1; i < games.length; i++) {
      var game = games[i]
      console.log(game)
      var price = ""
      if (game.bestSell != null) {
        price = "as low as " + game.bestSell.price + "€"
      }

      carousel.push(
        <Link to={"/games/info/" + game.id} style={{ height: "100%" }}>
          <div>
            <img src={game.coverUrl} style={{ width: "100%", bottom: "0px" }} />
            <div className="slick-caption">
              <h3 style={{ fontWeight: "bold", textShadow: "2px 2px #fc3b98" }}>
                {game.name}
                <br />
                <span style={{ fontSize: "20px" }}>{price}</span>
              </h3>
            </div>
          </div>
        </Link>
      )
    }

    maxcarousel = <GridItem xs={12} sm={12} md={8}>
      <h5 style={{ color: "#ff1cb7", float: "left" }}><b>Featured</b></h5>
      <Card carousel style={{ height: "75%" }}>
        <Carousel {...settings}>
          {carousel}
        </Carousel>
      </Card>
    </GridItem>
    
    var bestPrice = <h6 style={{ color: "#999", fontSize: "15px", paddingTop: "0 0", marginTop: "0px" }}>
      No one is selling this game...
                    </h6>
    if (games[0].bestSell != null) {
      bestPrice = <h6 style={{ color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px" }}>
        As low as <span style={{ fontWeight: "bolder", color: "#f44336", fontSize: "17px" }}> {games[0].bestSell.price}€</span>
      </h6>
    }

    highlight = <GridItem xs={12} sm={12} md={4}>
      <h5 style={{ color: "#ff1cb7", float: "left" }}><b>Highlight</b></h5>

      <Link to={"/games/info/" + game.id}>

        <Card style={{ height: "75%", width: "100%" }}>
          <CardActionArea>
            <CardMedia
              component="img"
              height="185px"
              image={games[0].coverUrl}
            />
            <CardContent >
              <div style={{ textAlign: "left", height: "105px" }}>
                <h6 style={{ fontWeight: "bold", color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px" }}>
                  {games[0].name}
                </h6>
              </div>
              <div style={{ textAlign: "left" }}>
                <h6 style={{ color: "#999", fontSize: "11px", paddingTop: "0 0", marginTop: "0px" }}>
                  Launch Date: <span style={{ fontWeight: "bold" }}>{games[0].releaseDate}</span>
                </h6>
              </div>
              <div style={{ textAlign: "left" }}>
                {bestPrice}
              </div>
            </CardContent>
          </CardActionArea>
        </Card>
      </Link >
    </GridItem>
  }





  return (
    <div className={classes.section}>
      <GridContainer>
        {maxcarousel}


        {highlight}
      </GridContainer>

      <GridContainer>
        <GridItem xs={12} sm={12} md={12}>
          <Link to="/games">
            <Button
              color="danger"
              size="lg"
              target="_blank"
              rel="noopener noreferrer"
            >
              <i className="fas fa-search" />
              Search the market
            </Button>
          </Link>
        </GridItem>
      </GridContainer>
    </div >
  );
}
//
