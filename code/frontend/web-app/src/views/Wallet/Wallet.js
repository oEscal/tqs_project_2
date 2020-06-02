
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

class Wallet extends Component {
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

        redirectLogin: false,
        error: false
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

    async buyWithNewCard() {
        var cardNumber = document.getElementById("cardNumber").value
        var cardName = document.getElementById("cardName").value
        var cardCVC = document.getElementById("cardCVC").value
        var expiration = document.getElementById("cardExpiration").value

        var error = false

        if (cardNumber == '' || cardCVC == '' || expiration == '' || cardName == '') {
            toast.error('Oops, you have to specify all card fields!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorCardAll"
            });
            error = true
        }

        if (!error && expiration != null) {
            var tempExpiration = expiration.split("/")
            if (tempExpiration.length != 3) {
                toast.error('Please use a valid expiration date...', {
                    position: "top-center",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    toastId: "errorCardExpiration"
                });
                error = true
            } else {
                expiration = tempExpiration[1] + "/" + tempExpiration[0] + "/" + tempExpiration[2]
            }
        }


        if (!error && (cardCVC != "" && cardCVC != null && (!(/^\d+$/.test(cardCVC)) || cardCVC.length != 3))) {
            toast.error('Oops, the CVC must contain only numbers and have 3 digits!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorCardCVC"
            });
            error = true
        }

        if (!error) {
            this.addFunds()
        }
    }

    async buyWithCard() {
        var error = false

        if (global.user != null) {
            var cardNumber = global.user.creditCardNumber
            var cardName = global.user.creditCardOwner
            var cardCVC = global.user.creditCardCSC
            var expiration = global.user.creditCardExpirationDate


            if (cardNumber == '' || cardCVC == '' || expiration == '' || cardName == '' || cardNumber == null || cardCVC == null || expiration == null || cardName == null) {
                toast.error('Oops, seems like you haven\'t registered a credit card to your account!', {
                    position: "top-center",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    toastId: "errorCardNon"
                });
                error = true
            }

        } else {
            toast.error('Oops, seems like you\'re not logged in...How did you manage that?', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorCardAccount"
            });
            error = true
        }

        if (!error) {
            this.addFunds()
        }

    }

    async addFunds() {
        var amount = document.getElementById("fundsAmount").value
        var error = false

        this.setState({
            doneLoading: false
        })

        if (amount == '' || amount == null) {
            toast.error('Oops, you have to specify how much money you want to add to your wallet!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorFundsOne"
            });
            error = true
        }

        if (isNaN(amount) || amount <= 0 || (amount.split(".").length == 2 && amount.split(".")[1].length > 2)) {
            toast.error('Oops, you have to specify a valid number bigger than 0 and with, at most, 2 decimal houses!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorFundsTwo"
            });
            error = true
        }

        if (!error) {
            var login_info = null
            if (global.user != null) {
                login_info = global.user.token

                await fetch(baseURL + "grid/funds?newfunds=" + amount, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: login_info
                    },
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
                            localStorage.setItem('loggedUser', JSON.stringify(data));
                            global.user = JSON.parse(localStorage.getItem('loggedUser'))

                            window.location.reload(false);
                        }
                    })
                    .catch(error => {
                        toast.error('Sorry, an unexpected error has occurred!', {
                            position: "top-center",
                            hideProgressBar: false,
                            closeOnClick: true,
                            pauseOnHover: true,
                            draggable: true,
                            toastId: "errorThreeToast"
                        });
                    });

            } else {
                localStorage.setItem('loggedUser', null);
                global.user = JSON.parse(localStorage.getItem('loggedUser'))

                this.setState({
                    redirectLogin: true
                })
            }
        }

        this.setState({
            doneLoading: true
        })
    }

    async componentDidMount() {
        window.scrollTo(0, 0)
        await this.getPrivateUserInfo()
        this.setState({ doneLoading: true })
    }

    renderRedirectLogin = () => {
        if (this.state.redirectLogin) {
            return <Redirect to='/login-page' />
        }
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
            var today = Datetime.moment()
            var valid = function (current) {
                return current.isAfter(today);
            };

            return (
                <div>
                    <LoggedHeader user={global.user} cart={global.cart} heightChange={false} height={600} />
                    {this.renderRedirectLogin()}

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
                                                    }}>Wallet</h2>
                                                </GridItem>
                                            </GridContainer>
                                            <hr style={{ color: "#999", opacity: "0.4" }}></hr>
                                        </div>
                                    </GridItem>
                                </GridContainer>

                            </div>

                            <div >
                                <GridContainer>
                                    <GridItem xs={12} sm={12} md={8}>
                                        <h2 style={{
                                            color: "#999",
                                            fontWeight: "",
                                            marginTop: "0px",
                                            padding: "0 0"
                                        }}>You have: <span style={{
                                            background: "rgb(253,27,163)",
                                            background: "linear-gradient(0deg, rgba(253,27,163,1) 0%, rgba(251,72,138,1) 24%, rgba(252,137,114,1) 55%, rgba(253,161,104,1) 82%, rgba(254,220,87,1) 100%)",
                                            WebkitBackgroundClip: "text",
                                            WebkitTextFillColor: "transparent",
                                            fontWeight: "bold",
                                            fontSize: "50px"
                                        }}>{this.state.info.funds == null ? 0 : this.state.info.funds}€</span></h2>
                                        <h4 style={{
                                            color: "#999",
                                            fontWeight: "",
                                            marginTop: "0px",
                                            padding: "0 0"
                                        }}>in your wallet! What are you waiting for to spend them?!</h4>
                                    </GridItem>
                                    <GridItem xs={12} sm={12} md={4}>
                                        <Card>

                                            <CardContent>
                                                <h2 style={{
                                                    color: "#999",
                                                    fontWeight: "",
                                                    marginTop: "0px",
                                                    padding: "0 0"
                                                }}>Add Funds</h2>

                                                <CustomInput
                                                    labelText="Amount to add"
                                                    id="fundsAmount"
                                                    formControlProps={{
                                                        fullWidth: true
                                                    }}
                                                    inputProps={{
                                                        type: "text",
                                                        endAdornment: (
                                                            <InputAdornment position="end">
                                                                <i class="fas fa-wallet" />
                                                            </InputAdornment>
                                                        )
                                                    }}
                                                />

                                                <hr style={{ opacity: 0.2, color: "#fc3196", marginTop: "0px" }} />


                                                <div style={{ "textAlign": "center" }}>
                                                    <Button color="primary"
                                                        style={{ marginTop: "30px", width: "100%", backgroundColor: "#ed6f62" }}
                                                        onClick={() => this.buyWithCard()} id="buyWithCard">Add Funds with registered Card</Button>
                                                </div>

                                                <hr style={{ opacity: 0.2, color: "#fc3196", marginTop: "30px" }} />

                                                <CustomInput
                                                    labelText="Card Number"
                                                    id="cardNumber"
                                                    formControlProps={{
                                                        fullWidth: true
                                                    }}
                                                    inputProps={{
                                                        type: "text",
                                                        endAdornment: (
                                                            <InputAdornment position="end">
                                                                <i class="fas fa-credit-card" />
                                                            </InputAdornment>
                                                        )
                                                    }}
                                                />

                                                <div>
                                                    <FormControl fullWidth>
                                                        <Datetime
                                                            timeFormat={false}
                                                            inputProps={{ placeholder: "Expiration Date", id: "cardExpiration" }}
                                                            isValidDate={valid}
                                                        />
                                                    </FormControl>
                                                </div>

                                                <CustomInput
                                                    labelText="Card Owner's Full Name"
                                                    id="cardName"
                                                    formControlProps={{
                                                        fullWidth: true
                                                    }}
                                                    inputProps={{
                                                        type: "text",
                                                        endAdornment: (
                                                            <InputAdornment position="end">
                                                                <i class="fas fa-signature"></i>
                                                            </InputAdornment>
                                                        )
                                                    }}
                                                />

                                                <CustomInput
                                                    labelText="CVC"
                                                    id="cardCVC"
                                                    formControlProps={{
                                                        fullWidth: true
                                                    }}
                                                    inputProps={{
                                                        type: "text",

                                                    }}
                                                />

                                                <div style={{ "textAlign": "center" }}>
                                                    <Button color="primary"
                                                        style={{ marginTop: "30px", width: "100%", backgroundColor: "#ed6f62" }}

                                                        onClick={() => this.buyWithNewCard(true)} id="buyWithNewCard">Add funds with new Card</Button>
                                                </div>
                                            </CardContent>
                                        </Card>
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

export default withStyles(styles)(Wallet);