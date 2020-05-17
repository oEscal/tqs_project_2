
import React, { Component } from 'react';
import classNames from "classnames";

// Styles
import styles from "assets/jss/material-kit-react/views/landingPage.js";

// Core MaterialUI 
import { withStyles } from '@material-ui/styles';

// Core Components
import Header from "components/Header/Header.js";
import Footer from "components/Footer/Footer.js";
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import Button from "components/CustomButtons/Button.js";
import HeaderLinks from "components/Header/HeaderLinks.js";
import Parallax from "components/Parallax/Parallax.js";

import LoggedHeader from "components/MyHeaders/LoggedHeader.js"


// Sections for this page
import ProductSection from "./Sections/ProductSection.js";
import TeamSection from "./Sections/TeamSection.js";
import GameSection from "./Sections/GameSection.js";


class HomePage extends Component {
    constructor() {
        super();
    }

    state = {
    }

    render() {
        const { classes } = this.props;
        return (
            <div>
                <LoggedHeader name="Jonas Pistolas" cart={true} wallet={0.00} heightChange={true} username={"Jonas_PP"} />

                <Parallax filter image={require("assets/img/bg.png")}>
                    <div className={classes.container}>
                        <GridContainer>
                            <GridItem xs={12} sm={12} md={6}>
                                <h1 style={{
                                    background:"rgb(253,27,163)",
                                    background: "linear-gradient(0deg, rgba(253,27,163,1) 0%, rgba(251,72,138,1) 24%, rgba(252,137,114,1) 55%, rgba(253,161,104,1) 82%, rgba(254,220,87,1) 100%)",
                                    WebkitBackgroundClip: "text",
                                    WebkitTextFillColor: "transparent",   
                                    fontWeight:"bolder"                                 
                                    }}>Grid Marketplace</h1>
                                <h4>
                                    Buy and sell all your favourite games! Participate in auctions and bid for exceptionally cheap prices!
                                </h4>
                                <br />
                                <Button
                                    color="danger"
                                    size="lg"
                                    href="https://www.youtube.com/watch?v=dQw4w9WgXcQ&ref=creativetim"
                                    target="_blank"
                                    rel="noopener noreferrer"
                                >
                                    <i className="fas fa-play" />
                                    Watch video
                                </Button>
                            </GridItem>
                        </GridContainer>
                    </div>
                </Parallax>

                <div className={classNames(classes.main, classes.mainRaised)}>
                    <div className={classes.container}>
                        <GameSection />
                        <ProductSection />
                        <TeamSection />
                    </div>
                </div>
            </div>
        )
    }
}

export default withStyles(styles)(HomePage);