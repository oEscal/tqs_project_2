
import React, { Component } from 'react';
import classNames from "classnames";

// Global Variables
import baseURL from '../../variables/baseURL'
import global from "../../variables/global";

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


// Toastify
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast, Flip } from 'react-toastify';

import {
    Link,
    Redirect
} from "react-router-dom";

class Receipt extends Component {
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

        redirectProfile: false,
        redirectGames: false,
        games: []
    }

    async componentDidMount() {
        window.scrollTo(0, 0)
        if (this.props == null || this.props.location == null || this.props.location.state == null || this.props.location.state.games == null) {
            this.setState({ redirectGames: true })
        } else {
            await this.setState({ games: this.props.location.state.games })
        }

        console.log(this.state.games)

        this.setState({ doneLoading: true })
    }

    renderRedirectGames = () => {
        if (this.state.redirectGames) {
            return <Redirect to='/games' />
        }
    }

    renderRedirectProfile = () => {
        if (this.state.redirectProfile && global.user != null) {
            return <Redirect to={'/user/' + global.user.username} />
        }
    }



    render() {
        const { classes } = this.props;


        if (!this.state.doneLoading) {
            return (
                <div>
                    <LoggedHeader user={global.user} cart={global.cart} heightChange={false} height={600} />

                    <div className="animated fadeOut animated" style={{ width: "100%", marginTop: "15%" }}>
                        <FadeIn>
                            <Lottie options={this.state.animationOptions} height={"20%"} width={"20%"} />
                        </FadeIn>
                    </div>
                </div>
            )
        } else {
            var boughtItems = []

            for (var i = 0; i < this.state.games.length; i++) {
                var game = this.state.games[i]
                boughtItems.push(<GridItem xs={12} sm={12} md={12}>
                    <Link to={"/games/info/" + game.id}>

                        <Card style={{ height: "375px", width: "100%" }}>
                            <CardActionArea>
                                <CardMedia
                                    component="img"
                                    height="185px"
                                    image={game.coverUrl}
                                />
                                <CardContent >
                                    <div style={{ textAlign: "left", height: "105px" }}>
                                        <h6 style={{ fontWeight: "bold", color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px" }}>
                                            {game.name}
                                        </h6>
                                    </div>
                                    <div style={{ textAlign: "left" }}>
                                        <h1 style={{ color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px" }}>
                                            <span style={{
                                                background: "rgb(253,27,163)",
                                                background: "linear-gradient(0deg, rgba(253,27,163,1) 0%, rgba(251,72,138,1) 24%, rgba(252,137,114,1) 55%, rgba(253,161,104,1) 82%, rgba(254,220,87,1) 100%)",
                                                WebkitBackgroundClip: "text",
                                                WebkitTextFillColor: "transparent",
                                            }}><i class="fas fa-key"></i> </span><span style={{ fontWeight: "bolder", color: "#f44336", fontSize: "17px" }}> {game.key}</span>
                                        </h1>
                                    </div>
                    
                                </CardContent>
                            </CardActionArea>
                        </Card>
                    </Link >
                </GridItem>)
            }

            return (
                <div>
                    <LoggedHeader user={global.user} cart={global.cart} heightChange={false} height={600} />
                    {this.renderRedirectGames()}
                    {this.renderRedirectProfile()}

                    <ToastContainer
                        position="top-center"
                        autoClose={2500}
                        hideProgressBar={false}
                        transition={Flip}
                        newestOnTop={false}
                        closeOnClick
                        rtl={false}
                        pauseOnVisibilityChange
                        draggable
                        pauseOnHover
                    />


                    <div className={classNames(classes.main)} style={{ marginTop: "60px" }}>
                        <div className={classes.container}>
                            <div style={{ padding: "70px 0" }}>
                                <GridContainer>
                                    <GridItem xs={12} sm={12} md={12}>
                                        <div style={{ textAlign: "left" }}>
                                            <GridContainer>
                                                <GridItem xs={12} sm={12} md={9}>
                                                    <span>
                                                        <h2 id="numberOfProducts" style={{
                                                            background: "rgb(253,27,163)",
                                                            background: "linear-gradient(0deg, rgba(253,27,163,1) 0%, rgba(251,72,138,1) 24%, rgba(252,137,114,1) 55%, rgba(253,161,104,1) 82%, rgba(254,220,87,1) 100%)",
                                                            WebkitBackgroundClip: "text",
                                                            WebkitTextFillColor: "transparent",
                                                            fontWeight: "bolder", fontWeight: "bolder", marginTop: "0px", padding: "0 0"
                                                        }}><b>Game on!</b>
                                                        </h2>
                                                    </span>
                                                </GridItem>
                                                <GridItem xs={12} sm={12} md={9}>
                                                    <span>
                                                        <h4 style={{ color: "#999", fontWeight: "bolder", marginTop: "0px", padding: "0 0" }}><b></b>Here's your receipt
                                                        </h4>
                                                    </span>
                                                </GridItem>

                                            </GridContainer>
                                            <hr style={{ color: "#999", opacity: "0.4" }}></hr>
                                        </div>
                                    </GridItem>
                                </GridContainer>
                            </div>

                            <div style={{}}>
                                <GridContainer xs={12} sm={12} md={12}>
                                    {boughtItems}
                                </GridContainer>
                            </div>
                        </div>

                        <Footer rawg={true} />

                    </div>
                </div>
            )
        }


    }
}

export default withStyles(styles)(Receipt);