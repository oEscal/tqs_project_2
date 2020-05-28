
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
import NavPills from "components/NavPills/NavPills.js";

import EditProfile from "./EditProfile.js";
import LoggedHeader from "components/MyHeaders/LoggedHeader.js"

// React Select
import Select from 'react-select';

// MaterialUI Icons
import Check from "@material-ui/icons/Check";
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import Dashboard from "@material-ui/icons/Dashboard";
import Schedule from "@material-ui/icons/Schedule";

// Images
import image1 from "assets/img/default_user.png";
import image from "assets/img/favicon.png";

// Toastify
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast, Flip } from 'react-toastify';

// Loading Animation
import FadeIn from "react-fade-in";
import Lottie from "react-lottie";
import * as loadingAnim from "assets/animations/loading_anim.json";

import {
    Link,
    Redirect
} from "react-router-dom";

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
        public: false,
        info: null
    }

    async getPrivateUserInfo() {
        var login_info = null
        if (global.user != null) {
            login_info = global.user.token
        }

        // Get All Games
        await fetch(baseURL + "grid/private/user-info?username=" + this.props.match.params.user, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: login_info
            }
        })
            .then(response => {
                if (response.status === 401) {
                    return response
                } else if (response.status === 200) {
                    return response.json()
                }
                else throw new Error(response.status);
            })
            .then(data => {
                if (data.status === 401) { // Wrong token
                    localStorage.setItem('loggedUser', null);
                    global.user = JSON.parse(localStorage.getItem('loggedUser'))

                    this.setState({
                        redirectLogin: true
                    })

                } else {
                    this.setState({ info: data, public: false })
                }
            })
            .catch(error => {
                console.log(error)
                toast.error('Sorry, an unexpected error has occurred!', {
                    position: "top-center",
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    toastId: "errorToast"
                });
                this.setState({ error: true })
            });
    }

    async getPublicUserInfo() {
        var login_info = null
        if (global.user != null) {
            login_info = global.user.token
        }

        // Get All Games
        await fetch(baseURL + "grid/public/user-info?username=" + this.props.match.params.user, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: login_info
            }
        })
            .then(response => {
                if (response.status === 401) {
                    return response
                } else if (response.status === 200) {
                    return response.json()
                }
                else throw new Error(response.status);
            })
            .then(data => {
                if (data.status === 401) { // Wrong token
                    localStorage.setItem('loggedUser', null);
                    global.user = JSON.parse(localStorage.getItem('loggedUser'))

                    this.setState({
                        redirectLogin: true
                    })

                } else {
                    this.setState({ info: data, public: true })
                }
            })
            .catch(error => {
                console.log(error)
                toast.error('Sorry, an unexpected error has occurred!', {
                    position: "top-center",
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    toastId: "errorToast"
                });
                this.setState({ error: true })
            });
    }

    async componentDidMount() {
        if (global.user.username == this.props.match.params.user) {
            await this.getPrivateUserInfo()
        } else {
            await this.getPublicUserInfo()
        }

        console.log(this.state.info)
        console.log(this.state.public)
        console.log(global.user.username)
        console.log(this.props.match.params.user)
        this.setState({ doneLoading: true })
    }


    //METHODS////////////////////////////////////


    ////////////////////////////////////////////


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
        } else if (this.state.error) {
            return (
                <div>
                    <LoggedHeader user={global.user} cart={global.cart} heightChange={false} height={600} />
                    <div className={classNames(classes.main)} style={{ marginTop: "60px" }}>
                        <div style={{ padding: "5px 0", paddingTop: "150px" }}>
                            <GridContainer xs={12} sm={12} md={11}>
                                <GridItem xs={12} sm={12} md={12}>
                                    <div style={{ textAlign: "center" }}>
                                        <h3 style={{ color: "#999" }}>
                                            Sorry, there was an error retrieving this user's information...
                                        </h3>
                                    </div>
                                </GridItem>
                            </GridContainer>
                        </div>
                    </div>
                </div>
            )
        } else {

            var info = <div></div>
            if (this.state.info != null) {
                if (!this.state.public) {
                    info = <GridItem xs={12} sm={12} md={12}>
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

                                            <div style={{ marginTop: "10px", width: "99%" }}>
                                                {this.state.info.reviewUsers.length == 0 ?
                                                    <div style={{ textAlign: "left" }}>
                                                        <h3 style={{ color: "#999" }}>
                                                            Oops, seems like no one's reviewed this user yet...
                                                </h3>
                                                    </div> :
                                                    <TableContainer component={Paper}>
                                                        <Table style={{ width: "100%" }} aria-label="simple table">
                                                            <TableBody>
                                                                {this.state.info.reviewUsers.map((row) => (
                                                                    <TableRow hover key={row.name}>
                                                                        <TableCell align="left">{row.author.username}</TableCell>
                                                                        <TableCell align="left">{row.score}</TableCell>
                                                                        <TableCell align="left">{row.comment}</TableCell>
                                                                        <TableCell align="left">{row.date}</TableCell>
                                                                    </TableRow>
                                                                ))}
                                                            </TableBody>
                                                        </Table>
                                                    </TableContainer>
                                                }

                                            </div>
                                        </div>
                                    )
                                },
                                {
                                    tabButton: "Game Reviews",
                                    tabIcon: "fas fa-gamepad",
                                    tabContent: (
                                        <div>
                                            <div>
                                                <span>
                                                    <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "10px", padding: "0 0" }}>My Game Reviews
                                                </h2>
                                                </span>
                                            </div>

                                            <div style={{ marginTop: "10px", width: "99%" }}>
                                                {this.state.info.reviewUsers.length == 0 ?
                                                    <div style={{ textAlign: "left" }}>
                                                        <h3 style={{ color: "#999" }}>
                                                            Oops, seems like you haven't reviewed any games yet...
                                                </h3>
                                                    </div> :
                                                    <TableContainer component={Paper}>
                                                        <Table style={{ width: "100%" }} aria-label="simple table">
                                                            <TableBody>
                                                                {this.state.info.reviewUsers.map((row) => (
                                                                    <TableRow hover key={row.name}>
                                                                        <TableCell align="left">{row.game.name}</TableCell>
                                                                        <TableCell align="left">{row.score}</TableCell>
                                                                        <TableCell align="left">{row.comment}</TableCell>
                                                                        <TableCell align="left">{row.date}</TableCell>
                                                                    </TableRow>
                                                                ))}
                                                            </TableBody>
                                                        </Table>
                                                    </TableContainer>
                                                }

                                            </div>
                                        </div>
                                    )
                                },
                                {
                                    tabButton: "User Reviews",
                                    tabIcon: "fas fa-users",
                                    tabContent: (
                                        <div>
                                            <div>
                                                <span>
                                                    <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "10px", padding: "0 0" }}>My User Reviews
                                                </h2>
                                                </span>
                                            </div>

                                            <div style={{ marginTop: "10px", width: "99%" }}>
                                                {this.state.info.reviewUsers.length == 0 ?
                                                    <div style={{ textAlign: "left" }}>
                                                        <h3 style={{ color: "#999" }}>
                                                            Oops, seems like you haven't reviewed any users yet...
                                                </h3>
                                                    </div> :
                                                    <TableContainer component={Paper}>
                                                        <Table style={{ width: "100%" }} aria-label="simple table">
                                                            <TableBody>
                                                                {this.state.info.reviewUsers.map((row) => (
                                                                    <TableRow hover key={row.name}>
                                                                        <TableCell align="left">{row.game.name}</TableCell>
                                                                        <TableCell align="left">{row.score}</TableCell>
                                                                        <TableCell align="left">{row.comment}</TableCell>
                                                                        <TableCell align="left">{row.date}</TableCell>
                                                                    </TableRow>
                                                                ))}
                                                            </TableBody>
                                                        </Table>
                                                    </TableContainer>
                                                }
                                            </div>
                                        </div>
                                    )
                                },
                                {
                                    tabButton: "Library",
                                    tabIcon: "fas fa-key",
                                    tabContent: (
                                        <div>
                                            <div>
                                                <span>
                                                    <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "10px", padding: "0 0" }}>My Games
                                                </h2>
                                                </span>
                                            </div>

                                            <div style={{ marginTop: "10px", width: "99%" }}>
                                                {this.state.info.buys.length == 0 ?
                                                    <div style={{ textAlign: "left" }}>
                                                        <h3 style={{ color: "#999" }}>
                                                            Oops, seems like you haven't bought any games...
                                                </h3>
                                                    </div> :
                                                    <TableContainer component={Paper}>
                                                        <Table style={{ width: "100%" }} aria-label="simple table">
                                                            <TableBody>
                                                                {this.state.info.buys.map((row) => (
                                                                    <TableRow hover key={row.name}>

                                                                    </TableRow>
                                                                ))}
                                                            </TableBody>
                                                        </Table>
                                                    </TableContainer>
                                                }
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
                                                    <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "10px", padding: "0 0" }}>My Sales
                                                </h2>
                                                </span>
                                            </div>

                                            <div style={{ marginTop: "10px", width: "99%" }}>
                                                {this.state.info.sells.length == 0 ?
                                                    <div style={{ textAlign: "left" }}>
                                                        <h3 style={{ color: "#999" }}>
                                                            Oops, seems like you haven't sold any games...
                                                </h3>
                                                    </div> :
                                                    <TableContainer component={Paper}>
                                                        <Table style={{ width: "100%" }} aria-label="simple table">
                                                            <TableBody>
                                                                {this.state.info.sells.map((row) => (
                                                                    <TableRow hover key={row.name}>
                                                                        <TableCell align="left" style={{ fontWeight: "bold" }}>{row.gameKey.gameName}</TableCell>
                                                                        <TableCell align="left">{row.gameKey.platform}</TableCell>
                                                                        <TableCell align="left">{row.gameKey.rkey}</TableCell>
                                                                        <TableCell align="left">{row.price}€</TableCell>
                                                                        <TableCell align="left">{row.date}</TableCell>
                                                                        {row.purchased ?
                                                                            <TableCell align="left" style={{ color: "#4ec884", fontWeight: "bold" }}>SOLD</TableCell> :
                                                                            <TableCell align="left" style={{ color: "red", fontWeight: "bold" }}>NOT SOLD</TableCell>
                                                                        }
                                                                        {row.purchased ?
                                                                            null :
                                                                            <TableCell align="left" style={{ color: "red", fontWeight: "bold" }}>
                                                                                <Button
                                                                                    size="md"
                                                                                    style={{ backgroundColor: "#ff3ea0" }}
                                                                                    href="https://www.youtube.com/watch?v=dQw4w9WgXcQ&ref=creativetim"
                                                                                    target="_blank"
                                                                                    rel="noopener noreferrer"
                                                                                >
                                                                                    <i class="fas fa-times"></i> Cancel Sale
                                                                        </Button>
                                                                            </TableCell>
                                                                        }
                                                                    </TableRow>
                                                                ))}
                                                            </TableBody>
                                                        </Table>
                                                    </TableContainer>
                                                }
                                            </div>
                                        </div>
                                    )
                                },
                                {
                                    tabButton: "Auctions",
                                    tabIcon: "fas fa-gavel",
                                    tabContent: (
                                        <div>
                                            <div>
                                                <span>
                                                    <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "10px", padding: "0 0" }}>My Auctions
                                                </h2>
                                                </span>
                                            </div>

                                            <div style={{ marginTop: "10px", width: "99%" }}>
                                                {this.state.info.auctions.length == 0 ?
                                                    <div style={{ textAlign: "left" }}>
                                                        <h3 style={{ color: "#999" }}>
                                                            Oops, seems like you haven't put any games for auction...
                                                </h3>
                                                    </div> :
                                                    <TableContainer component={Paper}>
                                                        <Table style={{ width: "100%" }} aria-label="simple table">
                                                            <TableBody>
                                                                {this.state.info.auctions.map((row) => (
                                                                    <TableRow hover key={row.name}>
                                                                        <TableCell align="left" style={{ fontWeight: "bold" }}>{row.gameKey.platform}</TableCell>
                                                                        <TableCell align="left">{row.gameKey.platform}</TableCell>
                                                                        <TableCell align="left">{row.gameKey.rkey}</TableCell>
                                                                        <TableCell align="left">{row.price}€</TableCell>
                                                                        <TableCell align="left">Started {row.date}</TableCell>
                                                                        <TableCell align="left">Ends {row.date}</TableCell>
                                                                        {row.purchased ?
                                                                            <TableCell align="left" style={{ color: "#4ec884", fontWeight: "bold" }}>SOLD</TableCell> :
                                                                            <TableCell align="left" style={{ color: "red", fontWeight: "bold" }}>NOT SOLD</TableCell>
                                                                        }
                                                                        {row.purchased ?
                                                                            null :
                                                                            <TableCell align="left" style={{ color: "red", fontWeight: "bold" }}>
                                                                                <Button
                                                                                    size="md"
                                                                                    style={{ backgroundColor: "#ff3ea0" }}
                                                                                    href="https://www.youtube.com/watch?v=dQw4w9WgXcQ&ref=creativetim"
                                                                                    target="_blank"
                                                                                    rel="noopener noreferrer"
                                                                                >
                                                                                    <i class="fas fa-times"></i> Cancel Auction
                                                                        </Button>
                                                                            </TableCell>
                                                                        }
                                                                    </TableRow>
                                                                ))}
                                                            </TableBody>
                                                        </Table>
                                                    </TableContainer>
                                                }
                                            </div>
                                        </div>
                                    )
                                },
                                {
                                    tabButton: "Payment Info",
                                    tabIcon: "fas fa-credit-card",
                                    tabContent: (
                                        <div>
                                            <div>
                                                <span>
                                                    <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "10px", padding: "0 0" }}>My Credit Card
                                                </h2>
                                                </span>
                                            </div>
                                            {this.state.info.creditCardNumber == null ?
                                                <div style={{ textAlign: "left" }}>
                                                    <h3 style={{ color: "#999" }}>
                                                        Oops, seems like you haven't registered a credit card yet...
                                                </h3>
                                                </div> :
                                                <TableContainer component={Paper}>
                                                    <Table style={{ width: "100%" }} aria-label="simple table">
                                                        <TableBody>
                                                            <TableRow hover key="cardInfo">
                                                                <TableCell align="left" style={{ fontWeight: "bold" }}>{this.state.info.creditCardNumber}</TableCell>
                                                                <TableCell align="left">{this.state.info.creditCardCSC}</TableCell>
                                                                <TableCell align="left">{this.state.info.creditCardOwner}</TableCell>
                                                                <TableCell align="left">{this.state.info.creditCardExpirationDate}</TableCell>
                                                            </TableRow>
                                                        </TableBody>
                                                    </Table>
                                                </TableContainer>
                                            }
                                        </div>
                                    )
                                },
                            ]}
                        />
                    </GridItem>
                } else {
                    info = <GridItem xs={12} sm={12} md={12}>
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

                                            <div style={{ marginTop: "10px", width: "99%" }}>
                                                {this.state.info.reviewUsers.length == 0 ?
                                                    <div style={{ textAlign: "left" }}>
                                                        <h3 style={{ color: "#999" }}>
                                                            Oops, seems like no one's reviewed this user yet...
                                                </h3>
                                                    </div> :
                                                    <TableContainer component={Paper}>
                                                        <Table style={{ width: "100%" }} aria-label="simple table">
                                                            <TableBody>
                                                                {this.state.info.reviewUsers.map((row) => (
                                                                    <TableRow hover key={row.name}>
                                                                        <TableCell align="left">{row.author.username}</TableCell>
                                                                        <TableCell align="left">{row.score}</TableCell>
                                                                        <TableCell align="left">{row.comment}</TableCell>
                                                                        <TableCell align="left">{row.date}</TableCell>
                                                                    </TableRow>
                                                                ))}
                                                            </TableBody>
                                                        </Table>
                                                    </TableContainer>
                                                }

                                            </div>
                                        </div>
                                    )
                                }
                            ]}
                        />
                    </GridItem>
                }

                var score = <span style={{ color: "#999" }}>No one's reviewed this user yet!</span>

                if (this.state.info.score > 0 && this.state.info.score <= 1) {
                    score = <span style={{ color: "red" }}><b>{this.state.info.score} <i class="far fa-star"></i></b></span>
                } else if (this.state.info.score < 4) {
                    score = <span style={{ color: "#fc926e" }}><b>{this.state.info.score} <i class="far fa-star"></i></b></span>
                } else if (this.state.info.score <= 5) {
                    score = <span style={{ color: "#4ec884" }}><b>{this.state.info.score} <i class="far fa-star"></i></b></span>
                }
            } else {
                info = <div style={{ padding: "5px 0", paddingTop: "150px" }}>
                    <GridContainer xs={12} sm={12} md={11}>
                        <GridItem xs={12} sm={12} md={12}>
                            <div style={{ textAlign: "center" }}>
                                <h3 style={{ color: "#999" }}>
                                    Loading...
                            </h3>
                            </div>
                        </GridItem>
                    </GridContainer>
                </div>
            }

            return (
                <div>
                    <LoggedHeader user={global.user} cart={global.cart} heightChange={false} height={600} />

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
                                    <GridItem xs={12} sm={12} md={3}>
                                        <img
                                            src={image1}
                                            alt="..."
                                            style={{ width: "85%", marginTop: "22px" }}
                                            className={
                                                classes.imgRaised +
                                                " " +
                                                classes.imgRounded
                                            }
                                        />
                                    </GridItem>

                                    <GridItem xs={12} sm={12} md={7}>
                                        <div style={{ textAlign: "left" }}>
                                            <h3 style={{ color: "#3b3e48", fontWeight: "bolder" }}><b style={{ color: "#3b3e48" }}>{this.state.info.username}</b> <span style={{ color: "#999" }}>({this.state.info.name})</span></h3>
                                            <hr style={{ color: "#999" }}></hr>
                                        </div>
                                        <div style={{ textAlign: "left", marginTop: "30px", minHeight: "110px" }}>
                                            <span style={{ color: "#999", fontSize: "15px" }}>
                                                <b>Description:</b> <span style={{ color: "#3b3e48" }}> {this.state.info.description != null ? this.state.info.description : "This user hasn't written a description yet!"}</span>
                                            </span>
                                        </div>
                                        <div style={{ textAlign: "left", marginTop: "30px" }}>
                                            <span style={{ color: "#999", fontSize: "25px" }}>
                                                <img src={image} style={{ marginBottom: "10px" }}></img><b> Grid Score:</b> {score}
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
                                                {this.state.info.country}
                                            </span>
                                        </div>

                                        <div style={{ textAlign: "left", marginTop: "15px" }}>
                                            <span style={{ color: "#999", fontSize: "12px" }}>
                                                <i class="fas fa-birthday-cake"></i> Birthday
                                            </span>
                                        </div>
                                        <div style={{ textAlign: "left" }}>
                                            <span style={{ color: "#3b3e48", fontSize: "15px", fontWeight: "bolder" }}>
                                                {this.state.info.birthDateStr}
                                            </span>
                                        </div>

                                        <div style={{ textAlign: "left", marginTop: "15px" }}>
                                            <span style={{ color: "#999", fontSize: "12px" }}>
                                                Has been in the Grid since...
                                            </span>
                                        </div>
                                        <div style={{ textAlign: "left" }}>
                                            <span style={{ color: "#3b3e48", fontSize: "15px", fontWeight: "bolder" }}>
                                                {this.state.info.startDateStr}
                                            </span>
                                        </div>

                                        {!this.state.public ? <div style={{ marginTop: "20px" }}>
                                            <Link to={"/user/" + "Jonas_PP" + "/edit"} style={{ color: "inherit" }}>
                                                <Button
                                                    color="danger"
                                                    size="md"
                                                    style={{ backgroundColor: "#ff3ea0" }}
                                                    target="_blank"
                                                    rel="noopener noreferrer"
                                                >
                                                    <i class="fas fa-pencil-alt"></i> Edit Profile
                                                </Button>
                                            </Link>
                                        </div> : null
                                        }


                                    </GridItem>
                                </GridContainer>
                            </div>

                            <div style={{ padding: "20px 0px", paddingBottom: "60px" }}>
                                <GridContainer>
                                    {info}
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