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

export default function GameSection() {
  const classes = useStyles();
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: false,
  };

  return (
    <div className={classes.section}>
      <GridContainer>
        <GridItem xs={12} sm={12} md={8}>
          <h5 style={{ color: "#ff1cb7", float: "left" }}><b>Featured</b></h5>
          <Card carousel style={{ height: "80%" }}>
            <Carousel {...settings}>
              <div>
                <img src={image1} alt="First slide" className="slick-image" />
                <div className="slick-caption">
                  <h3 style={{ fontWeight: "bold", textShadow: "2px 2px #fc3b98" }}>
                    No Man's Sky
                    <br />
                    <span style={{ fontSize: "20px" }}>as low as 15,99€</span>
                  </h3>
                </div>
              </div>
              <div>
                <img
                  src={image2}
                  alt="Second slide"
                  className="slick-image"
                />
                <div className="slick-caption">
                  <h3 style={{ fontWeight: "bold", textShadow: "2px 2px #fc3b98" }}>
                    Witcher 3: Game of the Year Edition
                    <br />
                    <span style={{ fontSize: "20px" }}>as low as 11,50€</span>
                  </h3>
                </div>
              </div>
              <div>
                <img src={image3} alt="Third slide" className="slick-image" />
                <div className="slick-caption">
                  <h3 style={{ fontWeight: "bold", textShadow: "2px 2px #fc3b98" }}>
                    Doom: Eternal
                    <br />
                    <span style={{ fontSize: "20px" }}>as low as 1,50€</span>
                  </h3>
                </div>
              </div>
            </Carousel>
          </Card>
        </GridItem>


        <GridItem xs={12} sm={12} md={4}>
          <h5 style={{ color: "#ff1cb7", float: "left" }}><b>Highlight</b></h5>
          <Card style={{ height: "80%", width: "100%" }}>
            <CardMedia
              component="img"
              height="275px"
              image={image4}
            />
            <CardContent>
              <div style={{ textAlign: "left" }}>
                <h3 style={{ fontWeight: "bold", color: "#3b3e48" }}>
                  NFS: Heat
                    <br />
                  <span style={{ fontSize: "20px" }}>as low as 1,50€</span>
                </h3>
              </div>
            </CardContent>
          </Card>
        </GridItem>
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
