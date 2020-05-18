import React from "react";
// nodejs library that concatenates classes
import classNames from "classnames";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";

// @material-ui/icons

// core components
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import Button from "components/CustomButtons/Button.js";
import Card from "components/Card/Card.js";
import CardBody from "components/Card/CardBody.js";
import CardFooter from "components/Card/CardFooter.js";

import styles from "assets/jss/material-kit-react/views/landingPageSections/teamStyle.js";

import team1 from "assets/img/faces/team-1.png";
import team2 from "assets/img/faces/team-2.png";
import team3 from "assets/img/faces/team-3.png";
import team4 from "assets/img/faces/team-4.png";


const useStyles = makeStyles(styles);

export default function TeamSection() {
  const classes = useStyles();
  const imageClasses = classNames(
    classes.imgRaised,
    classes.imgRounded,
    classes.imgFluid
  );
  return (
    <div className={classes.section}>
      <h2 style={{
        background: "rgb(78,16,78)",
        background: "linear-gradient(0deg, rgba(78,16,78,1) 0%, rgba(68,18,73,1) 24%, rgba(83,19,83,1) 55%, rgba(131,39,139,1) 100%)",
        WebkitBackgroundClip: "text",
        WebkitTextFillColor: "transparent",
        fontWeight: "bold"
      }}><b>The Grid Team</b></h2>
      <div>
        <GridContainer>
          <GridItem xs={12} sm={12} md={3}>
            <Card plain>
              <GridItem xs={12} sm={12} md={12} className={classes.itemGrid}>
                <img src={team4} alt="..." className={imageClasses} />
              </GridItem>
              <h4 className={classes.cardTitle}>
                Pedro Oliveira
                <br />
                <small className={classes.smallTitle}>#89156</small>
              </h4>
              <CardFooter className={classes.justifyCenter}>
                <Button
                  justIcon
                  color="transparent"
                  className={classes.margin5}
                  href="https://github.com/DrPunpun"
                >
                  <i className={classes.socials + " fab fa-github"} style={{fontSize:"30px"}} />
                </Button>
                <Button
                  justIcon
                  color="transparent"
                  className={classes.margin5}
                  href="https://www.linkedin.com/in/pedromroliveirapt/"
                >
                  <i className={classes.socials + " fab fa-linkedin"} style={{fontSize:"30px"}} />
                </Button>
              </CardFooter>
            </Card>
          </GridItem>
          <GridItem xs={12} sm={12} md={3}>
            <Card plain>
              <GridItem xs={12} sm={12} md={12} className={classes.itemGrid}>
                <img src={team3} alt="..." className={imageClasses} />
              </GridItem>
              <h4 className={classes.cardTitle}>
                Diogo Silva
                <br />
                <small className={classes.smallTitle}>#89348</small>
              </h4>
              <CardFooter className={classes.justifyCenter}>
                <Button
                  justIcon
                  color="transparent"
                  className={classes.margin5}
                  href="https://github.com/HerouFenix"
                >
                  <i className={classes.socials + " fab fa-github"} style={{fontSize:"30px"}} />
                </Button>
                <Button
                  justIcon
                  color="transparent"
                  className={classes.margin5}
                  href="https://www.linkedin.com/in/diogosilvads/"
                >
                  <i className={classes.socials + " fab fa-linkedin"} style={{fontSize:"30px"}} />
                </Button>
              </CardFooter>
            </Card>
          </GridItem>
          <GridItem xs={12} sm={12} md={3}>
            <Card plain>
              <GridItem xs={12} sm={12} md={12} className={classes.itemGrid}>
                <img src={team2} alt="..." className={imageClasses} />
              </GridItem>
              <h4 className={classes.cardTitle}>
                Rafael Simões
                <br />
                <small className={classes.smallTitle}>#88984</small>
              </h4>
              <CardFooter className={classes.justifyCenter}>
                <Button
                  justIcon
                  color="transparent"
                  className={classes.margin5}
                  href="https://github.com/Rafaelyot"
                >
                  <i className={classes.socials + " fab fa-github"} style={{fontSize:"30px"}} />
                </Button>
                <Button
                  justIcon
                  color="transparent"
                  className={classes.margin5}
                  href="https://www.linkedin.com/in/rafael-sim%C3%B5es-60958b173/"
                >
                  <i className={classes.socials + " fab fa-linkedin"} style={{fontSize:"30px"}} />
                </Button>
              </CardFooter>
            </Card>
          </GridItem>
          <GridItem xs={12} sm={12} md={3}>
            <Card plain>
              <GridItem xs={12} sm={12} md={12} className={classes.itemGrid}>
                <img src={team1} alt="..." className={imageClasses} />
              </GridItem>
              <h4 className={classes.cardTitle}>
                Pedro Escaleira
                <br />
                <small className={classes.smallTitle}>#88821</small>
              </h4>
              <CardFooter className={classes.justifyCenter}>
                <Button
                  justIcon
                  color="transparent"
                  className={classes.margin5}
                  href="https://github.com/oEscal"
                >
                  <i className={classes.socials + " fab fa-github"} style={{fontSize:"30px"}} />
                </Button>
                <Button
                  justIcon
                  color="transparent"
                  className={classes.margin5}
                  href="https://www.linkedin.com/in/pedro-escaleira-b9b39115b/"
                >
                  <i className={classes.socials + " fab fa-linkedin"} style={{fontSize:"30px"}} />
                </Button>
              </CardFooter>
            </Card>
          </GridItem>
        </GridContainer>
      </div>
    </div>
  );
}
