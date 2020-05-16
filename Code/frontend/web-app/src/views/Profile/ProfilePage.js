
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
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

// Core Components
import Header from "components/Header/Header.js";
import Footer from "components/Footer/Footer.js";
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import Button from "components/CustomButtons/Button.js";
import HeaderLinks from "components/Header/HeaderLinks.js";
import Parallax from "components/Parallax/Parallax.js";
import CustomInput from "components/CustomInput/CustomInput.js";
import CardHeader from 'components/Card/CardHeader.js';
import NavPills from "components/NavPills/NavPills.js";


import LoggedHeader from "components/MyHeaders/LoggedHeader.js"

// React Select
import Select from 'react-select';

// MaterialUI Icons
import Check from "@material-ui/icons/Check";
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import Dashboard from "@material-ui/icons/Dashboard";
import Schedule from "@material-ui/icons/Schedule";

// Images
import image1 from "assets/img/bg.jpg";
import image4 from "assets/img/NFS-Heat.jpg";
import image from "assets/img/favicon.png";


// Loading Animation
import FadeIn from "react-fade-in";
import Lottie from "react-lottie";
import * as loadingAnim from "assets/animations/loading_anim.json";



class ProfilePage extends Component {
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
                    <LoggedHeader name="Jonas Pistolas" cart={true} wallet={0.00} heightChange={false} />

                    <div className="animated fadeOut animated" style={{ width: "100%", marginTop: "15%" }}>
                        <FadeIn>
                            <Lottie options={this.state.animationOptions} height={"20%"} width={"20%"} />
                        </FadeIn>
                    </div>
                </div>
            )
        } else {
            const rows = [
                {
                    "user": "Jonas_PP",
                    "gridScore": <span style={{ color: "#4ec884", fontSize: "15px", fontWeight: "bolder" }}>
                        4 <i class="far fa-star"></i>
                    </span>,
                    "review": <span style={{ color: "#3b3e48", fontSize: "15px", fontWeight: "bold" }}>
                        "The dude fucking ripped me off. Absolutely dreadful, would recomend to stay as far away from this guy as possible!
                        
                    </span>,
                }
            ];

            var sellListings = <div></div>
            if (true) {
                sellListings = <div style={{ marginTop: "10px", width:"99%" }}>
                    <TableContainer component={Paper}>
                        <Table style={{ width: "100%" }} aria-label="simple table">
                            <TableBody>
                                {rows.map((row) => (
                                    <TableRow hover key={row.name}>
                                        <TableCell align="left">{row.user}</TableCell>
                                        <TableCell align="left">{row.gridScore}</TableCell>
                                        <TableCell align="left">{row.review}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </div>
            } else {
                sellListings = <div
                    className="animated fadeOut animated"
                    id="loadingAuctions"
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
                    <LoggedHeader name="Jonas Pistolas" cart={true} wallet={0.00} heightChange={false} />

                    <div className={classNames(classes.main)} style={{ marginTop: "60px" }}>
                        <div className={classes.container}>
                            <div style={{ padding: "70px 0" }}>
                                <GridContainer>
                                    <GridItem xs={12} sm={12} md={4}>
                                        <img
                                            src={image1}
                                            alt="..."
                                            style={{ width: "90%", marginTop: "28px" }}
                                            className={
                                                classes.imgRaised +
                                                " " +
                                                classes.imgRounded
                                            }
                                        />
                                    </GridItem>

                                    <GridItem xs={12} sm={12} md={6}>
                                        <div style={{ textAlign: "left" }}>
                                            <h3 style={{ color: "#3b3e48", fontWeight: "bolder" }}><b style={{ color: "#3b3e48" }}>Jonas Pistolas</b></h3>
                                            <hr style={{ color: "#999" }}></hr>
                                        </div>
                                        <div style={{ textAlign: "left", marginTop: "30px", minHeight: "110px" }}>
                                            <span style={{ color: "#999", fontSize: "15px" }}>
                                                <b>Description:</b> <span style={{ color: "#3b3e48" }}> Form a team with friends or fly alone and discover everything the universe has to offer. Gather, craft, explore – rediscover the title and enjoy new features introduced by the major expansion packs, including the famous Next update.</span>
                                            </span>
                                        </div>
                                        <div style={{ textAlign: "left", marginTop: "30px" }}>
                                            <span style={{ color: "#999", fontSize: "25px" }}>
                                                <img src={image} style={{ marginBottom: "10px" }}></img><b> Grid Score:</b> <span style={{ color: "#4ec884" }}><b>4 <i class="far fa-star"></i></b></span>
                                            </span>
                                        </div>
                                    </GridItem>

                                    <GridItem xs={12} sm={12} md={2}>
                                        <div style={{ textAlign: "left", marginTop: "30px" }}>
                                            <span style={{ color: "#999", fontSize: "12px" }}>
                                                <i class="fas fa-globe-europe"></i> Country
                                            </span>
                                        </div>
                                        <div style={{ textAlign: "left" }}>
                                            <span style={{ color: "#3b3e48", fontSize: "15px", fontWeight: "bolder" }}>
                                                Angola
                                            </span>
                                        </div>

                                        <div style={{ textAlign: "left", marginTop: "15px" }}>
                                            <span style={{ color: "#999", fontSize: "12px" }}>
                                                <i class="fas fa-birthday-cake"></i> Birthday
                                            </span>
                                        </div>
                                        <div style={{ textAlign: "left" }}>
                                            <span style={{ color: "#3b3e48", fontSize: "15px", fontWeight: "bolder" }}>
                                                Angola
                                            </span>
                                        </div>

                                        <div style={{ textAlign: "left", marginTop: "15px" }}>
                                            <span style={{ color: "#999", fontSize: "12px" }}>
                                                Has been in the grid since...
                                            </span>
                                        </div>
                                        <div style={{ textAlign: "left" }}>
                                            <span style={{ color: "#3b3e48", fontSize: "15px", fontWeight: "bolder" }}>
                                                Angola
                                            </span>
                                        </div>

                                        <div style={{ marginTop: "20px" }}>
                                            <Button
                                                color="danger"
                                                size="md"
                                                style={{ backgroundColor: "#ff3ea0" }}
                                                href="https://www.youtube.com/watch?v=dQw4w9WgXcQ&ref=creativetim"
                                                target="_blank"
                                                rel="noopener noreferrer"
                                            >
                                                <i class="fas fa-pencil-alt"></i> Edit Profile
                                            </Button>
                                        </div>

                                    </GridItem>
                                </GridContainer>
                            </div>

                            <div style={{ padding: "20px 0px", paddingBottom:"60px" }}>
                                <GridContainer>
                                    <GridItem xs={12} sm={12} md={12}>
                                        <NavPills
                                            color="rose"
                                            tabs={[
                                                {
                                                    tabButton: "Reviews",
                                                    tabIcon: "far fa-comment-alt",
                                                    tabContent: (
                                                        <div>
                                                            <div>
                                                                <span>
                                                                    <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "10px", padding: "0 0" }}>User Reviews
                                                                    </h2>
                                                                </span>
                                                            </div>
                                                            <div>
                                                                <div style={{ color: "#000", padding: "12px 0", width: "20%" }}>
                                                                    <Select
                                                                        className="basic-single"
                                                                        classNamePrefix="select"
                                                                        isSearchable={false}
                                                                        name="color"
                                                                        defaultValue={{ "value": "DATE", "label": "Sort by Date" }}
                                                                        options={[{ "value": "DATE", "label": "Sort by Date" }, { "value": "SCORE", "label": "Sort by Score" }]}
                                                                    />
                                                                </div>
                                                            </div>
                                                            {sellListings}
                                                            <div style={{ marginTop: "20px" }}>
                                                                <div style={{ margin: "auto", width: "40%" }}>
                                                                    <Pagination count={1} variant="outlined" shape="rounded" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                    )
                                                },
                                                {
                                                    tabButton: "Library",
                                                    tabIcon: "fas fa-gamepad",
                                                    tabContent: (
                                                        <div>
                                                            <div>
                                                                <span>
                                                                    <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "10px", padding: "0 0" }}>User Games
                                                                    </h2>
                                                                </span>
                                                            </div>
                                                            <div>
                                                                <div style={{ color: "#000", padding: "12px 0", width: "20%" }}>
                                                                    <Select
                                                                        className="basic-single"
                                                                        classNamePrefix="select"
                                                                        isSearchable={false}
                                                                        name="color"
                                                                        defaultValue={{ "value": "DATE", "label": "Sort by Date" }}
                                                                        options={[{ "value": "DATE", "label": "Sort by Date" }, { "value": "SCORE", "label": "Sort by Score" }]}
                                                                    />
                                                                </div>
                                                            </div>
                                                            {sellListings}
                                                            <div style={{ marginTop: "20px" }}>
                                                                <div style={{ margin: "auto", width: "40%" }}>
                                                                    <Pagination count={1} variant="outlined" shape="rounded" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                    )
                                                },
                                                {
                                                    tabButton: "Sales",
                                                    tabIcon: "fas fa-money-bill-wave",
                                                    tabContent: (
                                                        <div>
                                                            <div>
                                                                <span>
                                                                    <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "10px", padding: "0 0" }}>Sales Listings
                                                                    </h2>
                                                                </span>
                                                            </div>
                                                            <div>
                                                                <div style={{ color: "#000", padding: "12px 0", width: "20%" }}>
                                                                    <Select
                                                                        className="basic-single"
                                                                        classNamePrefix="select"
                                                                        isSearchable={false}
                                                                        name="color"
                                                                        defaultValue={{ "value": "NAME", "label": "Sort by Name" }}
                                                                        options={[{ "value": "NAME", "label": "Sort by Name" }, { "value": "DATE", "label": "Sort by Date" }]}
                                                                    />
                                                                </div>
                                                            </div>
                                                            {sellListings}
                                                            <div style={{ marginTop: "20px" }}>
                                                                <div style={{ margin: "auto", width: "40%" }}>
                                                                    <Pagination count={1} variant="outlined" shape="rounded" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                    )
                                                },
                                                {
                                                    tabButton: "Biddings",
                                                    tabIcon: "fas fa-gavel",
                                                    tabContent: (
                                                        <div>
                                                            <div>
                                                                <span>
                                                                    <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "10px", padding: "0 0" }}>My Biddings
                                                                    </h2>
                                                                </span>
                                                            </div>
                                                          
                                                            {sellListings}
                                                            <div style={{ marginTop: "20px" }}>
                                                                <div style={{ margin: "auto", width: "40%" }}>
                                                                    <Pagination count={1} variant="outlined" shape="rounded" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                    )
                                                },
                                                {
                                                    tabButton: "Transactions",
                                                    tabIcon: "fas fa-exchange-alt",
                                                    tabContent: (
                                                        <div>
                                                            <div>
                                                                <span>
                                                                    <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "10px", padding: "0 0" }}>My Transactions
                                                                    </h2>
                                                                </span>
                                                            </div>
                                                            {sellListings}
                                                            <div style={{ marginTop: "20px" }}>
                                                                <div style={{ margin: "auto", width: "40%" }}>
                                                                    <Pagination count={1} variant="outlined" shape="rounded" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                    )
                                                },
                                                {
                                                    tabButton: "Keys",
                                                    tabIcon: "fas fa-key",
                                                    tabContent: (
                                                        <div>
                                                            <div>
                                                                <span>
                                                                    <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "10px", padding: "0 0" }}>My Keys
                                                                    </h2>
                                                                </span>
                                                            </div>
                                                            {sellListings}
                                                            <div style={{ marginTop: "20px" }}>
                                                                <div style={{ margin: "auto", width: "40%" }}>
                                                                    <Pagination count={1} variant="outlined" shape="rounded" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                    )
                                                },
                                            ]}
                                        />
                                    </GridItem>
                                </GridContainer>
                            </div>
                        </div>
                    </div>
                </div>
            )
        }


    }
}

export default withStyles(styles)(ProfilePage);