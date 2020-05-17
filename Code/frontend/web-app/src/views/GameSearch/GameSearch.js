
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

import InputAdornment from "@material-ui/core/InputAdornment";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Radio from "@material-ui/core/Radio";
import Switch from "@material-ui/core/Switch";

import Typography from '@material-ui/core/Typography';

import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails'

// Core Components
import Header from "components/Header/Header.js";
import Footer from "components/Footer/Footer.js";
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import Button from "components/CustomButtons/Button.js";
import HeaderLinks from "components/Header/HeaderLinks.js";
import Parallax from "components/Parallax/Parallax.js";
import CustomInput from "components/CustomInput/CustomInput.js";


import LoggedHeader from "components/MyHeaders/LoggedHeader.js"

// React Select
import Select from 'react-select';

// MaterialUI Icons
import Check from "@material-ui/icons/Check";
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import image1 from "assets/img/bg.jpg";
import image4 from "assets/img/NFS-Heat.jpg";
import CardHeader from 'components/Card/CardHeader.js';

// Loading Animation
import FadeIn from "react-fade-in";
import Lottie from "react-lottie";
import * as loadingAnim from "assets/animations/loading_anim.json";

import {
    Link
} from "react-router-dom";

class GameSearch extends Component {
    constructor() {
        super();
    }

    state = {
        doneLoading: false,
        animationOptions: {
            loop: true, autoplay: true, animationData: loadingAnim.default, rendererSettings: {
                preserveAspectRatio: "xMidYMid slice"
            }
        },
    }

    componentDidMount() {
        this.setState({ doneLoading: true })
    }

    render() {
        const { classes } = this.props;

        if (!this.state.doneLoading) {
            return (
                <div>
                    <LoggedHeader name="Jonas Pistolas" cart={true} wallet={0.00} heightChange={false} username={"Jonas_PP"} />

                    <div className="animated fadeOut animated" style={{ width: "100%", marginTop: "15%" }}>
                        <FadeIn>
                            <Lottie options={this.state.animationOptions} height={"20%"} width={"20%"} />
                        </FadeIn>
                    </div>
                </div>
            )
        } else {
            var items = []
            for (var i = 0; i < 18; i++) {
                var style = { marginTop: "25px" }
                var text = ""
                var image = null

                if (i == 0 || i == 1 || i == 2) {
                    style = {}
                }
                if (i % 2 == 0) {
                    text = "NHS: Heat"
                    image = image4
                } else {
                    text = "No Man's Sky: Beyond"
                    image = image1
                }
                items.push(
                    <GridItem xs={12} sm={12} md={4} style={style}>
                        <Link to={"/games/info/" + text.replace(" ", "")}>

                            <Card style={{ height: "375px", width: "100%" }}>
                                <CardActionArea>
                                    <CardMedia
                                        component="img"
                                        height="185px"
                                        image={image}
                                    />
                                    <CardContent >
                                        <div style={{ textAlign: "left", height: "105px" }}>
                                            <h6 style={{ fontWeight: "bold", color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px" }}>
                                                {text}
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
                        </Link >

                    </GridItem>
                )
            }

            var loadedItems = <div></div>
            if (true) {
                loadedItems = items
            } else {
                loadedItems = <div
                    className="animated fadeOut animated"
                    id="loadingGames"
                    style={{
                        top: "50%",
                        left: "50%",
                        display: ""
                    }}>
                    <FadeIn>
                        <Lottie options={this.state.animationOptions} height={"20%"} width={"20%"} />
                    </FadeIn>
                </div>
            }

            return (
                <div>
                    <LoggedHeader name="Jonas Pistolas" cart={true} wallet={0.00} heightChange={false} username={"Jonas_PP"} />

                    <div className={classNames(classes.main)} style={{ marginTop: "60px" }}>

                        <div className={"search"} style={{ position: "absolute", top: "8%", right: "5%", zIndex:"10" }}>
                            <GridContainer xs={12} sm={12} md={12}>
                                <GridItem xs={12} sm={12} md={12}>
                                    <Card style={{ height: "100%", width: "400px", float: "right" }}>
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
                                        <CardContent style={{ width: "100%" }}>
                                            <div style={{ textAlign: "left", width: "100%" }}>
                                                <h6 style={{ fontWeight: "bold", color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px", marginBottom: "0px" }}>
                                                    Search by name
                                                </h6>
                                                <CustomInput
                                                    labelText="Name"
                                                    id="name"
                                                    formControlProps={{
                                                        fullWidth: true
                                                    }}
                                                />
                                            </div>

                                            <div style={{ textAlign: "left", width: "100%", marginTop: "30px" }}>
                                                <h6 style={{ fontWeight: "bold", color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px", marginBottom: "0px" }}>
                                                    Price Range
                                                </h6>
                                                <CustomInput
                                                    labelText="From"
                                                    id="from"
                                                    formControlProps={{
                                                        fullWidth: false
                                                    }}
                                                />
                                                <span style={{ marginRight: "5px", marginLeft: "5px" }}></span>
                                                <CustomInput
                                                    labelText="To"
                                                    id="to"
                                                    formControlProps={{
                                                        fullWidth: false
                                                    }}
                                                />
                                            </div>

                                            <div style={{ textAlign: "left", width: "100%", marginTop: "30px" }}>
                                                <h6 style={{ fontWeight: "bold", color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px", marginBottom: "0px" }}>
                                                    Genres
                                                </h6>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="MMO"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Point n Click"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="RPG"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="RTS"
                                                    />
                                                </div>
                                            </div>

                                            <div style={{ textAlign: "left", width: "100%", marginTop: "30px" }}>
                                                <h6 style={{ fontWeight: "bold", color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px", marginBottom: "0px" }}>
                                                    Genres
                                                </h6>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="PC"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="PS4"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Xbox One"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Nintendo Switch"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Other"
                                                    />
                                                </div>
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
                                                            isSearchable={false}
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


                            <div className={"searchMobile"} style={{ padding: "5px 0", margin: "auto", width: "90%" }}>
                                <GridContainer xs={12} sm={12} md={12}>
                                    <ExpansionPanel style={{ width: "100%" }}>
                                        <ExpansionPanelSummary
                                            expandIcon={<ExpandMoreIcon />}
                                            aria-controls="panel1a-content"
                                            id="panel1a-header"
                                        >
                                            <Typography className={classes.heading} style={{ width: "100%" }}>Filter Games</Typography>
                                        </ExpansionPanelSummary>
                                        <ExpansionPanelDetails>
                                            <div style={{ textAlign: "left", width: "100%" }}>
                                                <h6 style={{ fontWeight: "bold", color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px", marginBottom: "0px" }}>
                                                    Search by name
                                                </h6>
                                                <CustomInput
                                                    labelText="Name"
                                                    id="name"
                                                    formControlProps={{
                                                        fullWidth: true
                                                    }}
                                                />
                                            </div>
                                        </ExpansionPanelDetails>
                                        <ExpansionPanelDetails>
                                            <div style={{ textAlign: "left", width: "100%" }}>
                                                <h6 style={{ fontWeight: "bold", color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px", marginBottom: "0px" }}>
                                                    Price Range
                                                </h6>
                                                <CustomInput
                                                    labelText="From"
                                                    id="from"
                                                    formControlProps={{
                                                        fullWidth: false
                                                    }}
                                                />
                                                <span style={{ marginRight: "5px", marginLeft: "5px" }}></span>
                                                <CustomInput
                                                    labelText="To"
                                                    id="to"
                                                    formControlProps={{
                                                        fullWidth: false
                                                    }}
                                                />
                                            </div>
                                        </ExpansionPanelDetails>
                                        <ExpansionPanelDetails>
                                            <div style={{ textAlign: "left", width: "100%" }}>
                                                <h6 style={{ fontWeight: "bold", color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px", marginBottom: "0px" }}>
                                                    Price Range
                                                </h6>
                                                <CustomInput
                                                    labelText="From"
                                                    id="from"
                                                    formControlProps={{
                                                        fullWidth: false
                                                    }}
                                                />
                                                <span style={{ marginRight: "5px", marginLeft: "5px" }}></span>
                                                <CustomInput
                                                    labelText="To"
                                                    id="to"
                                                    formControlProps={{
                                                        fullWidth: false
                                                    }}
                                                />
                                            </div>
                                        </ExpansionPanelDetails>
                                        <ExpansionPanelDetails>
                                            <div style={{ textAlign: "left", width: "100%", marginTop: "30px" }}>
                                                <h6 style={{ fontWeight: "bold", color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px", marginBottom: "0px" }}>
                                                    Genres
                                                </h6>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="MMO"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Point n Click"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="RPG"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="RTS"
                                                    />
                                                </div>
                                            </div>
                                        </ExpansionPanelDetails>

                                        <ExpansionPanelDetails>
                                            <div style={{ textAlign: "left", width: "100%", marginTop: "30px" }}>
                                                <h6 style={{ fontWeight: "bold", color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px", marginBottom: "0px" }}>
                                                    Genres
                                                </h6>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="PC"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="PS4"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Xbox One"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Nintendo Switch"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Other"
                                                    />
                                                </div>
                                            </div>
                                        </ExpansionPanelDetails>
                                    </ExpansionPanel>
                                </GridContainer>
                            </div>


                            <div style={{ padding: "5px 0" }}>
                                <GridContainer xs={12} sm={12} md={10}>
                                    {loadedItems}
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
}

export default withStyles(styles)(GameSearch);


