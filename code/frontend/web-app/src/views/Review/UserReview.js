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

import FormControl from "@material-ui/core/FormControl";
// react plugin for creating date-time-picker
import Datetime from "react-datetime";

import LoggedHeader from "components/MyHeaders/LoggedHeader.js"

import TextField from '@material-ui/core/TextField';

// Stars
import StarRatings from 'react-star-ratings';

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

class UserReview extends Component {
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
        info: null,
        user: null,

        redirectLogin: false,
        redirectProfile: false,
        error: false,

        rating: 0
    }

    async getPrivateUserInfo() {
        var login_info = null
        if (global.user != null) {
            login_info = global.user.token

            await fetch(baseURL + "grid/private/user-info?username=" + global.user.username, {
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
                        this.setState({ info: data })
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
        } else {
            localStorage.setItem('loggedUser', null);
            global.user = JSON.parse(localStorage.getItem('loggedUser'))

            this.setState({
                redirectLogin: true
            })
        }
    }

    async confirmReview() {
        var review = document.getElementById("review").value
        var error = false
        if (review.length > 250) {
            toast.error('Sorry, your review can have a maximum of 250 characters!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorMaximum"
            });
            error = true
        }

        if (this.state.rating <= 0 || this.state.rating > 5 || this.state.rating == null || this.state.rating == "") {
            toast.error('Please select a valid score!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorStars"
            });
            error = true
        }

        if (!error) {
            var login_info = null
            if (global.user != null) {
                login_info = global.user.token

                var body = {
                    "author": this.state.info.id,
                    "comment": review,
                    "target": this.state.user.sellerId,
                    "score": this.state.rating
                }

                await this.setState({ doneLoading: false })

                await fetch(baseURL + "grid/add-user-review", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: login_info
                    },
                    body: JSON.stringify(body)

                })
                    .then(response => {
                        if (response.status === 401 || response.status === 400) {
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
                        }
                        else if (data.status === 400) {
                            toast.error('You can\'t review yourself, or the same user more than once, silly!', {
                                position: "top-center",
                                autoClose: 5000,
                                hideProgressBar: false,
                                closeOnClick: true,
                                pauseOnHover: true,
                                draggable: true,
                                toastId: "errorBlowjob"
                            });
                        } else {
                            this.setState({ redirectProfile: true })
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

                await this.setState({ doneLoading: true })

            } else {
                localStorage.setItem('loggedUser', null);
                global.user = JSON.parse(localStorage.getItem('loggedUser'))

                this.setState({
                    redirectLogin: true
                })
            }
        }

    }

    async componentDidMount() {
        window.scrollTo(0, 0)

        if (this.props == null || this.props.location == null || this.props.location.state == null || this.props.location.state.user == null) {
            await this.setState({ redirectGames: true })
        } else {
            await this.setState({ user: this.props.location.state.user })
        }
        await this.getPrivateUserInfo()
        this.setState({ doneLoading: true })
    }

    renderRedirectLogin = () => {
        if (this.state.redirectLogin) {
            return <Redirect to='/login-page' />
        }
    }

    renderRedirectGames = () => {
        if (this.state.redirectGames) {
            return <Redirect to='/games' />
        }
    }

    renderRedirectProfile = () => {
        if (this.state.redirectProfile) {
            return <Redirect to={'/user/' + this.state.info.username} />
        }
    }

    changeRating = (newRating) => {
        this.setState({
            rating: newRating
        });
    }


    render() {
        const { classes } = this.props;

        if (!this.state.doneLoading) {
            return (
                <div>
                    <LoggedHeader user={global.user} cart={global.cart} heightChange={false} height={600} />

                    <div className="animated fadeOut animated" id="firstLoad" style={{ width: "100%", marginTop: "15%" }}>
                        <FadeIn>
                            <Lottie options={this.state.animationOptions} height={"20%"} width={"20%"} />
                        </FadeIn>
                    </div>
                </div>
            )
        } else {
            return (
                <div>
                    <LoggedHeader user={global.user} cart={global.cart} heightChange={false} height={600} />
                    {this.renderRedirectLogin()}
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
                                                    <h2 style={{
                                                        color: "#999",
                                                        fontWeight: "bolder",
                                                        marginTop: "0px",
                                                        padding: "0 0"
                                                    }}>Review <span style={{ color: "#f44336" }}><b>{this.state.user == null ? "" : this.state.user.sellerName}</b></span></h2>
                                                </GridItem>
                                            </GridContainer>
                                            <hr style={{ color: "#999", opacity: "0.4" }}></hr>
                                        </div>
                                    </GridItem>
                                </GridContainer>

                            </div>

                            <div >
                                <GridContainer>
                                    <GridItem md="12">
                                        <TextField
                                            id="outlined-multiline-static"
                                            label="Review"
                                            id="review"
                                            multiline
                                            fullWidth={true}
                                            rows={3}
                                            defaultValue=""
                                            variant="outlined"
                                        />
                                    </GridItem>

                                </GridContainer>
                            </div>

                            <div style={{ padding: "50px 0" }}>
                                <GridContainer>
                                    <GridItem md="8" >
                                        <StarRatings
                                            rating={this.state.rating}
                                            starRatedColor="#fd24ac"
                                            starHoverColor="#f44336"
                                            starDimension="40px"
                                            changeRating={this.changeRating}
                                            numberOfStars={5}
                                            name='rating'
                                        />
                                    </GridItem>

                                    <GridItem md="4" >
                                        <Button
                                            size="md"
                                            style={{ backgroundColor: "#fd24ac", width: "100%" }}
                                            target="_blank"
                                            rel="noopener noreferrer"
                                            onClick={() => this.confirmReview()}
                                            id="confirmReviewButton"
                                        >
                                            <i class="far fa-star"></i> Confirm Review
                                        </Button>
                                    </GridItem>
                                </GridContainer>
                            </div>

                        </div>

                    </div>
                </div >
            )
        }


    }
}

export default withStyles(styles)(UserReview);