
import React, { Component } from 'react';
import classNames from "classnames";

// Styles
import styles from "assets/jss/material-kit-react/views/landingPage.js";
import 'assets/css/hide.css'

// Core MaterialUI 
import { withStyles } from '@material-ui/styles';
import CardMedia from '@material-ui/core/CardMedia';
import CardContent from '@material-ui/core/CardContent';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardActionArea from '@material-ui/core/CardActionArea';
import Pagination from '@material-ui/lab/Pagination';

// Core Components
import Header from "components/Header/Header.js";
import Footer from "components/Footer/Footer.js";
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import Button from "components/CustomButtons/Button.js";
import HeaderLinks from "components/Header/HeaderLinks.js";
import Parallax from "components/Parallax/Parallax.js";

import LoggedHeader from "components/MyHeaders/LoggedHeader.js"

// React Select
import Select from 'react-select';

// Sections for this page
import Title from "./Sections/Title.js";
import TeamSection from "./Sections/TeamSection.js";
import GameSection from "./Sections/GameSection.js";


import image4 from "assets/img/NFS-Heat.jpg";
import CardHeader from 'components/Card/CardHeader.js';


class GameSearch extends Component {
    constructor() {
        super();
    }

    state = {
    }

    render() {
        const { classes } = this.props;

        var items = []
        for (var i = 0; i < 18; i++) {
            var style = { marginTop: "25px" }
            if (i == 0 || i == 1 || i == 2) {
                style = {}
            }
            items.push(
                <GridItem xs={12} sm={12} md={4} style={style}>
                    <Card style={{ height: "100%", width: "100%" }}>
                        <CardActionArea>
                            <CardMedia
                                component="img"
                                height="185px"
                                image={image4}
                            />
                            <CardContent>
                                <div style={{ textAlign: "left" }}>
                                    <h6 style={{ fontWeight: "bold", color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px" }}>
                                        World of Warcraft: Complete Collection (Epic Edition) - Battle.net - Key NORTH AMERICA
                            </h6>
                                </div>
                                <div style={{ textAlign: "left" }}>
                                    <h6 style={{ color: "#999", fontSize: "11px", paddingTop: "0 0", marginTop: "0px" }}>
                                        Launch Date: <span style={{ fontWeight: "bold" }}>05-03-2020</span>
                                    </h6>
                                </div>
                                <div style={{ textAlign: "left" }}>
                                    <h6 style={{ color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px" }}>
                                        As low as <span style={{ fontWeight: "bolder", color: "#f44336", fontSize: "17px" }}> 5,99€</span>
                                    </h6>
                                </div>
                            </CardContent>
                        </CardActionArea>
                    </Card>
                </GridItem>
            )
        }
        return (
            <div>
                <LoggedHeader name="Jonas Pistolas" cart={true} wallet={0.00} heightChange={false} />

                <div className={classNames(classes.main)} style={{ marginTop: "60px" }}>

                    <div className={"search"} style={{ position: "fixed", top: "32%", right: "5%" }}>
                        <GridContainer xs={12} sm={12} md={12}>
                            <GridItem xs={12} sm={12} md={12}>
                                <Card style={{ height: "100%", width: "60%", float: "right" }}>
                                    <CardHeader>
                                        <div style={{ textAlign: "left" }}>
                                            <GridContainer>
                                                <GridItem xs={12} sm={12} md={12}>
                                                    <span>
                                                        <h3 style={{ color: "#999", fontWeight: "bolder", padding: "20px 0", marginBottom: "0px" }}>Filter Games
                                                        </h3>
                                                    </span>
                                                </GridItem>
                                                <GridItem xs={12} sm={12} md={3}>

                                                </GridItem>
                                            </GridContainer>

                                            <hr style={{ color: "#999", opacity: "0.4", padding: "0 0", marginTop: "0px" }}></hr>
                                        </div>
                                    </CardHeader>
                                    <CardContent>
                                        <div style={{ textAlign: "left" }}>
                                            <h6 style={{ fontWeight: "bold", color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px" }}>
                                                World of Warcraft: Complete Collection (Epic Edition) - Battle.net - Key NORTH AMERICA
                                            </h6>
                                        </div>
                                        
                                    </CardContent>
                                </Card>
                            </GridItem>
                        </GridContainer>
                    </div>

                    <div className={classes.container}>
                        <div style={{ padding: "70px 0" }}>
                            <GridContainer>
                                <GridItem xs={12} sm={12} md={12}>
                                    <div style={{ textAlign: "left" }}>
                                        <GridContainer>
                                            <GridItem xs={12} sm={12} md={9}>
                                                <span>
                                                    <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "0px", padding: "0 0" }}>Games <span style={{ color: "#999", fontSize: "15px", fontWeight: "normal" }}>(xxxxx products)</span>
                                                    </h2>
                                                </span>
                                            </GridItem>
                                            <GridItem xs={12} sm={12} md={3}>
                                                <div style={{ color: "#000", padding: "12px 0" }}>
                                                    <Select
                                                        className="basic-single"
                                                        classNamePrefix="select"
                                                        name="color"
                                                        defaultValue={{ "value": "DATE", "label": "Newer First" }}
                                                        options={[{ "value": "DATE", "label": "Newer First" }, { "value": "REVERSE_DATE", "label": "Older First" }, { "value": "ALPHABETICAL", "label": "Alphabetical" }, { "value": "PRICE", "label": "Lowest Price to Highest" }, { "value": "PRICE_REVERSE", "label": "Highest Price to Lowest" }]}
                                                    />
                                                </div>
                                            </GridItem>
                                        </GridContainer>

                                        <hr style={{ color: "#999", opacity: "0.4" }}></hr>
                                    </div>
                                </GridItem>
                            </GridContainer>
                        </div>

                        <div style={{ padding: "5px 0" }}>
                            <GridContainer xs={12} sm={12} md={10}>
                                {items}
                            </GridContainer>
                        </div>



                        <div style={{ padding: "25px 40px" }}>
                            <GridContainer xs={12} sm={12} md={10}>
                                <div style={{ margin: "auto", width: "50%" }}>
                                    <Pagination count={10} variant="outlined" shape="rounded" />
                                </div>
                            </GridContainer>
                        </div>
                    </div>
                </div>
            </div >
        )
    }
}

export default withStyles(styles)(GameSearch);