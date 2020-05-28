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

import Close from "@material-ui/icons/Close";
import stylesjs from "assets/jss/material-kit-react/views/componentsSections/javascriptStyles.js";
import "./cart.css";

// Global Variables
import baseURL from '../../variables/baseURL'
import global from "../../variables/global";

class Cart extends Component {
    constructor(props) {
        super(props);

        this.state = {
            price: 0,
            quantity: 0,
            items: []
        };
    }


    componentDidMount(){
        if (global.cart == null || global.cart.games == []) {

        } else {
            this.getItems()
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

        await this.getGameListings()

        this.getItems()

        this.setState({ doneLoading: true })
    }

    render() {
        const { classes } = this.props;


        return (
            <div>
                <LoggedHeader user={global.user} cart={global.cart} heightChange={false} height={600} />

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
                                                    }}>Cart <span
                                                        style={{ color: "#999", fontSize: "15px", fontWeight: "normal" }}>(xxxxx products)</span>
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
                            <GridContainer xs={12} sm={12} md={4}
                                style={{ flex: "1", float: "right", "padding-left": "30px" }}>
                                <GridItem xs={12} sm={12} md={12}>
                                    <Sticky>
                                        {({ style }) => (
                                            <Card>
                                                <CardHeader
                                                    title={"Total Price: " + this.state.price + "€"}
                                                    subheader={"Items: " + this.state.quantity}
                                                >

                                                </CardHeader>
                                                <CardContent>
                                                    <div style={{ "textAlign": "center" }}>
                                                        <Button color="primary" round
                                                            onClick={() => this.setClassicModal(true)}>Continue to
                                                            payment</Button>
                                                    </div>
                                                </CardContent>
                                            </Card>)}
                                    </Sticky>
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