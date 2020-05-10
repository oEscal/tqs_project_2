
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
import ProductSection from "../LandingPage/Sections/ProductSection.js";
import TeamSection from "../LandingPage/Sections/TeamSection.js";
import WorkSection from "../LandingPage/Sections/WorkSection.js";


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
                <LoggedHeader name="Jonas Pistolas" cart={true}/>

                <Parallax filter image={require("assets/img/landing-bg.jpg")}>
                    <div className={classes.container}>
                        <GridContainer>
                            <GridItem xs={12} sm={12} md={6}>
                                <h1 className={classes.title}>Your Story Starts With Us.</h1>
                                <h4>
                                    Every landing page needs a small description after the big bold
                                    title, that{"'"}s why we added this text here. Add here all the
                                    information that can make you or your product create the first
                                    impression.
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
                        <ProductSection />
                        <TeamSection />
                        <WorkSection />
                    </div>
                </div>
            </div>
        )
    }
}

export default withStyles(styles)(HomePage);