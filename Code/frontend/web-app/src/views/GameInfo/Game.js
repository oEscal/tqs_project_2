
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


import LoggedHeader from "components/MyHeaders/LoggedHeader.js"

// React Select
import Select from 'react-select';

// MaterialUI Icons
import Check from "@material-ui/icons/Check";
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

// Images
import image1 from "assets/img/bg.jpg";
import image4 from "assets/img/NFS-Heat.jpg";
import image from "assets/img/favicon.png";


// Loading Animation
import FadeIn from "react-fade-in";
import Lottie from "react-lottie";
import * as loadingAnim from "assets/animations/loading_anim.json";



class Game extends Component {
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
        window.scrollTo(0, 0)
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
                    "seller": "Jonas_PP",
                    "gridScore": <span style={{ color: "#4ec884", fontSize: "15px", fontWeight: "bolder" }}>
                        4 <i class="far fa-star"></i>
                    </span>,

                    "price": <span style={{ color: "#f44336", fontSize: "25px", fontWeight: "bolder" }}>
                        5,99€
                        </span>,
                    "type": <span style={{ color: "#3b3e48", fontSize: "15px", fontWeight: "bolder" }}>
                        Steam
                    </span>,
                    "buy": <Button
                        size="md"
                        style={{ backgroundColor: "#4ec884" }}
                        href="https://www.youtube.com/watch?v=dQw4w9WgXcQ&ref=creativetim"
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        <i class="fas fa-cart-arrow-down"></i> Add to Cart
                    </Button>
                },
                {
                    "seller": "Not_Jonas_PP",
                    "gridScore": <span style={{ color: "#fb5a87", fontSize: "15px", fontWeight: "bolder" }}>
                        2 <i class="far fa-star"></i>
                    </span>,
                    "price": <span style={{ color: "#f44336", fontSize: "23px", fontWeight: "bolder" }}>
                        8,99€
                    </span>,
                    "type": <span style={{ color: "#3b3e48", fontSize: "15px", fontWeight: "bolder" }}>
                        Epic Games Store
                    </span>,
                    "buy": <Button
                        size="md"
                        style={{ backgroundColor: "#4ec884" }}
                        href="https://www.youtube.com/watch?v=dQw4w9WgXcQ&ref=creativetim"
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        <i class="fas fa-cart-arrow-down"></i> Add to Cart
                    </Button>
                },
                {
                    "seller": "Oofington",
                    "gridScore": <span style={{ color: "#4ec884", fontSize: "15px", fontWeight: "bolder" }}>
                        3 <i class="far fa-star"></i>
                    </span>,
                    "price": <span style={{ color: "#f44336", fontSize: "25px", fontWeight: "bolder" }}>
                        12,99€
                    </span>,
                    "type": <span style={{ color: "#3b3e48", fontSize: "15px", fontWeight: "bolder" }}>
                        Steam
                    </span>,
                    "buy": <Button
                        size="md"
                        style={{ backgroundColor: "#4ec884" }}
                        href="https://www.youtube.com/watch?v=dQw4w9WgXcQ&ref=creativetim"
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        <i class="fas fa-cart-arrow-down"></i> Add to Cart
                    </Button>
                }
            ];

            const rows2 = [
                {
                    "seller": "Jonas_PP",
                    "gridScore": <span style={{ color: "#4ec884", fontSize: "15px", fontWeight: "bolder" }}>
                        4 <i class="far fa-star"></i>
                    </span>,

                    "price": <span style={{ color: "#f44336", fontSize: "25px", fontWeight: "bolder" }}>
                        0,99€
                        </span>,
                    "type": <span style={{ color: "#3b3e48", fontSize: "15px", fontWeight: "bolder" }}>
                        Steam
                    </span>,
                    "date": <span style={{ color: "#3b3e48", fontSize: "15px", fontWeight: "bolder" }}>
                        00:00:03:00
                    </span>,
                    "buy": <Button
                        size="md"
                        style={{ backgroundColor: "#4ec884" }}
                        href="https://www.youtube.com/watch?v=dQw4w9WgXcQ&ref=creativetim"
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        <i class="fas fa-gavel"></i> Make a Bidding
                    </Button>
                }
            ];

            var auctionListings = <div></div>
            if (true) {
                auctionListings = <GridItem xs={12} sm={12} md={12} style={{ marginTop: "10px" }}>
                    <TableContainer component={Paper}>
                        <Table style={{ width: "100%" }} aria-label="simple table">
                            <TableBody>
                                {rows2.map((row) => (
                                    <TableRow hover key={row.name}>
                                        <TableCell align="left">{row.seller}</TableCell>
                                        <TableCell align="left">{row.gridScore}</TableCell>
                                        <TableCell align="left">{row.type}</TableCell>
                                        <TableCell align="left">{row.date}</TableCell>
                                        <TableCell align="right">{row.price}</TableCell>
                                        <TableCell align="right">{row.buy}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </GridItem>
            } else {
                auctionListings = <div
                    className="animated fadeOut animated"
                    id="loadingSales"
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

            var sellListings = <div></div>
            if (true) {
                sellListings = <GridItem xs={12} sm={12} md={12} style={{ marginTop: "10px" }}>
                    <TableContainer component={Paper}>
                        <Table style={{ width: "100%" }} aria-label="simple table">
                            <TableBody>
                                {rows.map((row) => (
                                    <TableRow hover key={row.name}>
                                        <TableCell align="left">{row.seller}</TableCell>
                                        <TableCell align="left">{row.gridScore}</TableCell>
                                        <TableCell align="left">{row.type}</TableCell>
                                        <TableCell align="right">{row.price}</TableCell>
                                        <TableCell align="right">{row.buy}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </GridItem>
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
                                    <GridItem xs={12} sm={12} md={5}>
                                        <img
                                            src={image1}
                                            alt="..."
                                            style={{ width: "95%", height: "260px", marginTop: "28px" }}
                                            className={
                                                classes.imgRaised +
                                                " " +
                                                classes.imgRounded
                                            }
                                        />
                                    </GridItem>

                                    <GridItem xs={12} sm={12} md={5}>
                                        <div style={{ textAlign: "left" }}>
                                            <h3 style={{ color: "#3b3e48", fontWeight: "bolder" }}><b style={{ color: "#3b3e48" }}>No Man's Sky</b></h3>
                                            <hr style={{ color: "#999" }}></hr>
                                        </div>
                                        <div style={{ textAlign: "left", marginTop: "30px" }}>
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
                                                BEST OFFER
                                            </span>
                                        </div>
                                        <div style={{ textAlign: "left" }}>
                                            <span style={{ color: "#3b3e48", fontSize: "15px", fontWeight: "bolder" }}>
                                                Jonas_PP
                                            </span>
                                        </div>
                                        <div style={{ textAlign: "left" }}>
                                            <span style={{ color: "#4ec884", fontSize: "15px", fontWeight: "bolder" }}>
                                                4 <i class="far fa-star"></i>
                                            </span>
                                            <span style={{ color: "#999", fontSize: "15px", fontWeight: "bolder", marginLeft: "10px" }}>
                                                Grid Score
                                            </span>
                                        </div>
                                        <div style={{ textAlign: "left" }}>
                                            <Button
                                                color="danger"
                                                size="sm"
                                                style={{ backgroundColor: "#ff3ea0" }}
                                                href="https://www.youtube.com/watch?v=dQw4w9WgXcQ&ref=creativetim"
                                                target="_blank"
                                                rel="noopener noreferrer"
                                            >
                                                <i class="far fa-user"></i> Seller Profile
                                            </Button>
                                        </div>

                                        <div style={{ textAlign: "left", marginTop: "20px" }}>
                                            <span style={{ color: "#999", fontSize: "12px" }}>
                                                Price
                                            </span>
                                        </div>
                                        <div style={{ textAlign: "left" }}>
                                            <span style={{ color: "#f44336", fontSize: "40px", fontWeight: "bolder" }}>
                                                5,99€
                                            </span>

                                        </div>
                                        <div style={{ textAlign: "left" }}>
                                            <span style={{ color: "#3b3e48", fontSize: "20", fontWeight: "bolder" }}>
                                                ( Steam Key )
                                            </span>
                                        </div>

                                        <div style={{ textAlign: "left", marginTop: "5px" }}>
                                            <Button
                                                size="md"
                                                style={{ backgroundColor: "#4ec884", width: "100%" }}
                                                href="https://www.youtube.com/watch?v=dQw4w9WgXcQ&ref=creativetim"
                                                target="_blank"
                                                rel="noopener noreferrer"
                                            >
                                                <i class="fas fa-cart-arrow-down"></i> Add to Cart
                                            </Button>
                                            <Button
                                                size="md"
                                                style={{ backgroundColor: "#1598a7", width: "100%" }}
                                                href="https://www.youtube.com/watch?v=dQw4w9WgXcQ&ref=creativetim"
                                                target="_blank"
                                                rel="noopener noreferrer"
                                            >
                                                <i class="far fa-heart"></i> Add to Wishlist
                                            </Button>
                                        </div>

                                    </GridItem>
                                </GridContainer>
                            </div>

                            <div style={{ padding: "20px 0px" }}>
                                <GridContainer>
                                    <GridItem xs={12} sm={12} md={12}>
                                        <span>
                                            <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "0px", padding: "0 0" }}>3 Offers
                                            </h2>
                                        </span>
                                    </GridItem>
                                    <GridItem xs={12} sm={12} md={3}>
                                        <div style={{ color: "#000", padding: "12px 0", width: "100%" }}>
                                            <Select
                                                className="basic-single"
                                                classNamePrefix="select"
                                                isSearchable={false}
                                                name="color"
                                                defaultValue={{ "value": "PRICE", "label": "Sort by Price" }}
                                                options={[{ "value": "PRICE", "label": "Sort by Price" }, { "value": "DATE", "label": "Sort by Listing Date" }, { "value": "RATING", "label": "Sort by Seller Rating" }]}
                                            />
                                        </div>
                                    </GridItem>
                                    {sellListings}
                                    <GridItem xs={12} sm={12} md={12} style={{ marginTop: "20px" }}>
                                        <div style={{ margin: "auto", width: "40%" }}>
                                            <Pagination count={1} variant="outlined" shape="rounded" />
                                        </div>
                                    </GridItem>
                                </GridContainer>
                            </div>

                            <div style={{ padding: "45px 0px" }}>
                                <GridContainer>
                                    <GridItem xs={12} sm={12} md={12}>
                                        <span>
                                            <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "0px", padding: "0 0" }}>1 Auctions
                                            </h2>
                                        </span>
                                    </GridItem>
                                    <GridItem xs={12} sm={12} md={3}>
                                        <div style={{ color: "#000", padding: "12px 0", width: "100%" }}>
                                            <Select
                                                className="basic-single"
                                                classNamePrefix="select"
                                                isSearchable={false}
                                                name="color"
                                                defaultValue={{ "value": "PRICE", "label": "Sort by Price" }}
                                                options={[{ "value": "PRICE", "label": "Sort by Price" }, { "value": "DATE", "label": "Sort by Ending Date" }, { "value": "RATING", "label": "Sort by Seller Rating" }]}
                                            />
                                        </div>
                                    </GridItem>
                                    {auctionListings}
                                    <GridItem xs={12} sm={12} md={12} style={{ marginTop: "20px" }}>
                                        <div style={{ margin: "auto", width: "40%" }}>
                                            <Pagination count={1} variant="outlined" shape="rounded" />
                                        </div>
                                    </GridItem>
                                </GridContainer>
                            </div>

                            <div style={{ padding: "45px 0px" }}>
                                <GridContainer>
                                    <GridItem xs={12} sm={12} md={12}>
                                        <span>
                                            <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "0px", padding: "0 0" }}>Game Details
                                            </h2>
                                        </span>
                                    </GridItem>
                                    <GridItem xs={12} sm={12} md={12} style={{ marginTop: "10px" }}>
                                        <div style={{ color: "black" }}>
                                            ADD GAME INFO
                                        </div>
                                    </GridItem>
                                </GridContainer>
                            </div>

                        </div>
                        
                        <Footer />

                    </div>
                </div>
            )
        }


    }
}

export default withStyles(styles)(Game);