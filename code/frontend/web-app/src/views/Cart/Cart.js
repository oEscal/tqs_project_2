import React, { Component } from 'react';
import classNames from "classnames";
import styles from "assets/jss/material-kit-react/views/landingPage.js";
import 'assets/css/hide.css'
import { withStyles } from '@material-ui/styles';
import CardMedia from '@material-ui/core/CardMedia';
import CardContent from '@material-ui/core/CardContent';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import LoggedHeader from "components/MyHeaders/LoggedHeader.js"
import image1 from "assets/img/bg.jpg";
import image4 from "assets/img/NFS-Heat.jpg";
import CardHeader from "@material-ui/core/CardHeader";
import Avatar from "@material-ui/core/Avatar";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from '@material-ui/icons/Close';
import InputAdornment from "@material-ui/core/InputAdornment";
import CustomInput from "components/CustomInput/CustomInput";
import VideogameAssetIcon from '@material-ui/icons/VideogameAsset';
import { StickyContainer, Sticky } from "components/Sticky/index.js";
import Button from "components/CustomButtons/Button.js";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import Dialog from "@material-ui/core/Dialog";
import Slide from "@material-ui/core/Slide";

import FormControl from "@material-ui/core/FormControl";
// react plugin for creating date-time-picker
import Datetime from "react-datetime";

import Close from "@material-ui/icons/Close";
import stylesjs from "assets/jss/material-kit-react/views/componentsSections/javascriptStyles.js";
import "./cart.css";

// Global Variables
import baseURL from '../../variables/baseURL'
import global from "../../variables/global";

// Loading Animation
import FadeIn from "react-fade-in";
import Lottie from "react-lottie";
import * as loadingAnim from "assets/animations/loading_anim.json";

import {
    Link,
    Redirect
} from "react-router-dom";
// Toastify
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast, Flip } from 'react-toastify';

class Cart extends Component {
    constructor(props) {
        super(props);

        this.state = {
            price: 0,
            quantity: 0,
            items: [],
            animationOptions: {
                loop: true, autoplay: true, animationData: loadingAnim.default, rendererSettings: {
                    preserveAspectRatio: "xMidYMid slice"
                }
            },
            redirectLogin: false,
            redirectGames: false,
            doneLoading: false,

            boughtKeys: null
        };
    }


    async componentDidMount() {
        await this.setState({ doneLoading: false })
        if (global.cart == null || global.cart.games == []) {

        } else {
            this.getItems()
        }

        await this.setState({ doneLoading: true })
    }

    renderRedirectLogin = () => {
        if (this.state.redirectLogin) {
            return <Redirect to='/login-page' />
        }
    }

    renderRedirectReceipt = () => {
        if (this.state.redirectGames) {
            return <Redirect
                to={{
                    pathname: '/cart/receipt',
                    state: { games: this.state.boughtKeys }
                }} />
        }
    }

    getItems() {
        const { classes } = this.props;

        var style = { margin: "12px 0", float: 'left' };
        var items = []
        var sum_price = 0
        var sum_items = 0

        for (var i = 0; i < global.cart.games.length; i++) {
            var game = global.cart.games[i]
            sum_items++
            sum_price += game.price
            items.push(
                <GridItem xs={12} sm={12} md={12} style={style}>

                    <Card style={{ width: "100%" }}>

                        <CardHeader
                            title={
                                <h6 style={{ color: "#999", fontSize: "11px", paddingTop: "0 0", marginTop: "0px" }}>
                                    From seller <span style={{ color: 'black', fontWeight: "bold" }}>{game.gameKey.retailer}</span>
                                </h6>
                            }
                            avatar={
                                <Avatar aria-label="recipe" className={classes.avatar}>
                                    {game.gameKey.retailer[0]}
                                </Avatar>
                            }
                            action={
                                <IconButton aria-label="settings" onClick={() => this.removeFromCart(game)}>
                                    <CloseIcon />
                                </IconButton>
                            }
                        >
                        </CardHeader>
                        <CardActionArea>
                            <Link to={"/games/info/" + game.gameKey.gameId}>

                                <CardMedia
                                    component="img"
                                    height="185px"
                                    image={game.gameKey.gamePhoto}
                                />

                                <CardContent>
                                    <div style={{ textAlign: "left", height: "30px" }}>
                                        <h6 style={{
                                            fontWeight: "bold",
                                            color: "#3b3e48",
                                            fontSize: "15px",
                                            paddingTop: "0 0",
                                            marginTop: "0px"
                                        }}>
                                            {game.gameKey.gameName}
                                        </h6>
                                    </div>

                                    <div style={{ textAlign: "left" }}>
                                        <h6 style={{ color: "#999", fontSize: "11px", paddingTop: "0 0", marginTop: "0px" }}>
                                            Platform: <span style={{ fontWeight: "bold" }}>{game.gameKey.platform}</span>
                                        </h6>
                                    </div>
                                    <div style={{ textAlign: "left" }}>
                                        <h6 style={{
                                            color: "#3b3e48",
                                            fontSize: "15px",
                                            paddingTop: "0 0",
                                            marginTop: "0px"
                                        }}>
                                            Price <span
                                                style={{
                                                    fontWeight: "bolder",
                                                    color: "#f44336",
                                                    fontSize: "17px"
                                                }}> {game.price} €</span>
                                        </h6>
                                    </div>
                                </CardContent>
                            </Link>
                        </CardActionArea>
                    </Card>
                </GridItem>
            )
        }

        this.setState({
            price: sum_price,
            quantity: sum_items,
            items: items
        })
    }

    async removeFromCart(game) {
        await this.setState({ doneLoading: false })

        var cart = []
        if (global.cart != null) {
            for (var i = 0; i < global.cart.games.length; i++) {
                var foundGame = global.cart.games[i]
                if (game.id != foundGame.id) {
                    cart.push(foundGame)
                }
            }
        }

        await localStorage.setItem('cart', JSON.stringify({ "games": cart }));
        global.cart = await JSON.parse(localStorage.getItem('cart'))

        this.getItems()

        this.setState({ doneLoading: true })
    }

    async buyWithWallet() {
        var error = false
        if (global.user == null || parseFloat(global.user.funds) < parseFloat(this.state.price)) {
            error = true
            toast.error('Oops, you don\'t have enough funds in your wallet! Either add more funds or pick another payment method!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorCardAll"
            });
        }

        if (!error) {
            this.confirmBuy(true)
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
        } else {
            cardNumber = null
            cardCVC = null
            expiration = null
            cardName = null
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

        if (!error && cardCVC != "" && cardCVC != null && (!(/^\d+$/.test(cardCVC)) || cardCVC.length != 3)) {
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
            this.confirmBuy(false)
        }
    }

    async confirmBuy(withFunds) {
        if (global.cart != null && global.cart.games.length > 0) {
            await this.setState({
                doneLoading: false,
            })

            var gameIds = []
            for (var i = 0; i < global.cart.games.length; i++) {
                gameIds.push(global.cart.games[i].id)
            }

            var user = -1
            if (global.user != null) {
                user = global.user.id
            }

            var body = {
                "listingsId": gameIds,
                "userId": user,
                "withFunds": withFunds
            }

            var success = true

            var login_info = null
            if (global.user != null) {
                login_info = global.user.token
            }


            await fetch(baseURL + "grid/buy-listing", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: login_info
                },
                body: JSON.stringify(body)
            })
                .then(response => {
                    if (response.status === 401 || response.status === 404 || response.status === 400) {
                        return response
                    } else if (response.status === 200) {
                        return response.json()
                    }
                    else throw new Error(response.status);
                })
                .then(data => {
                    console.log(data)
                    if (data.status === 401) { // Wrong token
                        localStorage.setItem('loggedUser', null);
                        global.user = JSON.parse(localStorage.getItem('loggedUser'))

                        this.setState({
                            redirectLogin: true
                        })

                    } else if (data.status === 404) { // Game not available
                        toast.error('Oh no, one of the games you were trying to buy is no longer available...next time be faster!', {
                            position: "top-center",
                            hideProgressBar: false,
                            closeOnClick: true,
                            pauseOnHover: true,
                            draggable: true,
                        });

                        //update cart

                        success = false

                    } else if (data.status === 400) { // No funds
                        toast.error('Oops, you don\'t have enough funds in your wallet! Either add more funds or pick another payment method!', {
                            position: "top-center",
                            hideProgressBar: false,
                            closeOnClick: true,
                            pauseOnHover: true,
                            draggable: true,
                        });

                        //update cart
                        localStorage.setItem('cart', JSON.stringify({ "games": [] }));
                        global.cart = JSON.parse(localStorage.getItem('cart'))

                        success = false

                    } else { // Successful 
                        localStorage.setItem('cart', JSON.stringify({ "games": [] }));
                        global.cart = JSON.parse(localStorage.getItem('cart'))

                        this.setState({
                            boughtKeys: data
                        })
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
                    });
                });

            console.log(success)
            await this.setState({
                doneLoading: true,
                redirectGames: success
            })

            if (!success) {
                this.getItems()
            }

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
        }

        var today = Datetime.moment()
        var valid = function (current) {
            return current.isAfter(today);
        };

        var extraPaymentOptions = null

        if (global.user != null) {
            extraPaymentOptions = [
                <hr style={{ opacity: 0.2, color: "#fc3196" }} />,
                <div style={{ "textAlign": "center" }}>
                    <Button color="primary"
                        style={{ marginTop: "30px", width: "100%", backgroundColor: "#ed6f62" }}
                        onClick={() => this.buyWithWallet(true)}>Buy with Wallet</Button>
                </div>,

                <hr style={{ opacity: 0.2, color: "#fc3196", marginTop: "30px" }} />,
                <div style={{ "textAlign": "center" }}>
                    <Button color="primary"
                        style={{ marginTop: "30px", width: "100%", backgroundColor: "#ed6f62" }}
                        onClick={() => this.confirmBuy(false)}>Buy with Card</Button>
                </div>

            ]
        }

        return (
            <div>
                <LoggedHeader user={global.user} cart={global.cart} heightChange={false} height={600} />
                {this.renderRedirectLogin()}
                {this.renderRedirectReceipt()}

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
                                                    <h2 style={{
                                                        color: "#999",
                                                        fontWeight: "bolder",
                                                        marginTop: "0px",
                                                        padding: "0 0"
                                                    }}>Cart
                                                    </h2>
                                                </span>
                                            </GridItem>

                                        </GridContainer>

                                        <hr style={{ color: "#999", opacity: "0.4" }}></hr>
                                    </div>
                                </GridItem>
                            </GridContainer>
                        </div>

                        <StickyContainer
                            style={{ padding: "5px 0", "vertical-align": "top", display: "flex", height: "100%" }}>
                            <GridContainer xs={12} sm={12} md={8} style={{ flex: "1" }}>
                                {this.state.items}

                            </GridContainer>
                            <GridContainer xs={12} sm={12} md={4}>
                                <GridItem xs={2} sm={2} md={2}></GridItem>
                                <GridItem xs={10} sm={10} md={10}>
                                    <Card>
                                        <CardHeader
                                            title={
                                                <span>
                                                    Total Price: <span
                                                        style={{
                                                            fontWeight: "bolder",
                                                            color: "#f44336",
                                                        }}> {this.state.price} €</span>
                                                </span>
                                            }
                                            subheader={"Items: " + this.state.quantity}
                                        >

                                        </CardHeader>
                                        <CardContent>
                                            {extraPaymentOptions}

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
                                                    type: "text"
                                                }}
                                            />

                                            <div style={{ marginTop: "20px" }}>
                                                <FormControl fullWidth>
                                                    <Datetime
                                                        timeFormat={false}
                                                        inputProps={{ placeholder: "Expiration Date", id: "cardExpiration" }}
                                                        isValidDate={valid}
                                                    />
                                                </FormControl>
                                            </div>

                                            <div style={{ "textAlign": "center" }}>
                                                <Button color="primary"
                                                    style={{ marginTop: "30px", width: "100%", backgroundColor: "#ed6f62" }}

                                                    onClick={() => this.buyWithNewCard(true)}>Buy with new Card</Button>
                                            </div>
                                        </CardContent>
                                    </Card>
                                </GridItem>

                            </GridContainer>
                        </StickyContainer>
                    </div>
                </div>
            </div >
        )
    }
}

export default withStyles(styles)(Cart);