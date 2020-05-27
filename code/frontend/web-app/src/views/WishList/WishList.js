import React, { Component } from 'react';
import { withStyles } from "@material-ui/styles";
import styles from "assets/jss/material-kit-react/views/landingPage.js";
import classNames from "classnames";
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import LoggedHeader from "components/MyHeaders/LoggedHeader.js"
import CustomInput from "components/CustomInput/CustomInput";
import InputAdornment from "@material-ui/core/InputAdornment";
import SearchIcon from '@material-ui/icons/Search';
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import Avatar from "@material-ui/core/Avatar";
import IconButton from "@material-ui/core/IconButton";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardMedia from "@material-ui/core/CardMedia";
import CardContent from "@material-ui/core/CardContent";
import image1 from "assets/img/bg.jpg";
import image4 from "assets/img/NFS-Heat.jpg";
import FavoriteBorderIcon from '@material-ui/icons/FavoriteBorder';
import FavoriteIcon from '@material-ui/icons/Favorite';
import Button from "components/CustomButtons/Button.js";
import logoImage from "assets/img/favicon.png";
import Popover from "@material-ui/core/Popover";

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


import javascriptStyles from "assets/jss/material-kit-react/views/componentsSections/javascriptStyles.js";

// Global Variables
import baseURL from '../../variables/baseURL'
import global from "../../variables/global";
import CardBody from 'components/Card/CardBody';

class WishList extends Component {
    constructor(props) {

        super(props);
    }

    state = {
        data: [],
        cacheData: [],
        size: 6,
        anchorElLeft: null,
        currentGame: null,
        doneLoading: false,
        redirectLogin: false,
        animationOptions: {
            loop: true, autoplay: true, animationData: loadingAnim.default, rendererSettings: {
                preserveAspectRatio: "xMidYMid slice"
            }
        },
        gamesLoaded: false
    };

    async getWishlist() {
        var login_info = null
        if (global.user != null) {
            login_info = global.user.token
        }

        await this.setState({ gamesLoaded: false })

        // Get All Games
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
                    this.setState({ data: data.wishList })
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
            });

        await this.setState({ gamesLoaded: true })
    }

    renderFavoriteIcon = (status, index) => {
        let html = (
            <div style={{ color: "#9c27b0" }}>
                <FavoriteIcon />
            </div>
        );

        if (!status) {
            html = (
                <div>
                    <FavoriteBorderIcon />
                </div>
            )
            const { data, size } = this.state;

            data.splice(index, 1);
            this.setState({
                data: data,
                size: size - 1
            });
        }

        return html;
    };


    confirmRemoval = (e) => {

        if (this.state.currentGame === null)
            return;

        const id = this.state.currentGame.id;

        let { data } = this.state;
        let status = data[id].favorite;
        data[id].favorite = !status;

        this.setState({
            data: data,
            currentGame: null
        });

        this.setAnchorElLeft(null);

    }


    async componentDidMount() {
        await this.getWishlist();
        this.setState({ doneLoading: true, })
    }


    setAnchorElLeft = (v) => {

        this.setState({
            anchorElLeft: v,
            currentGame: v === null ? v : { id: parseInt(v.id.replace('icon', '')), text: v.name }
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
        }


        var loadedItems = <div></div>

        var empty = false

        if (this.state.gamesLoaded) {
            var items = []
            var i = -1

            this.state.data.forEach(game => {

                i++
                var style = { marginTop: "25px" }

                if (i == 0 || i == 1 || i == 2) {
                    style = {}
                }

                items.push(
                    <GridItem xs={12} sm={12} md={4} style={style}>
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
                                            <h6 style={{ color: "#999", fontSize: "11px", paddingTop: "0 0", marginTop: "0px" }}>
                                                Launch Date: <span style={{ fontWeight: "bold" }}>{game.releaseDate}</span>
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
                    </GridItem >
                )
            })

            if (items.length == 0) {
                empty = true
                items.push(
                    <GridItem xs={12} sm={12} md={12}>
                        <div style={{ textAlign: "left" }}>
                            <h3 style={{ color: "#999" }}>
                                Hmmmm, it seems as though you still haven't added any games to your wishlist...
                            </h3>
                        </div>
                    </GridItem>
                )
            }
            loadedItems = items
        } else {
            loadedItems = <GridItem xs={12} sm={12} md={12} style={{ marginBottom: "100px" }}>
                <div >
                    <FadeIn>
                        <Lottie options={this.state.animationOptions} height={"20%"} width={"20%"} />
                    </FadeIn>
                </div>
            </GridItem>
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
                                <GridItem xs={12} sm={12} md={12}>
                                    <div style={{ textAlign: "left" }}>
                                        <GridContainer>
                                            <GridItem xs={12} sm={12} md={9}>
                                                <h2 style={{
                                                    color: "#999",
                                                    fontWeight: "bolder",
                                                    marginTop: "0px",
                                                    padding: "0 0"
                                                }}>My Wishlist</h2>
                                            </GridItem>
                                        </GridContainer>
                                        <hr style={{ color: "#999", opacity: "0.4" }}></hr>

                                        <GridContainer>
                                            {loadedItems}
                                        </GridContainer>

                                    </div>
                                </GridItem>
                            </GridContainer>
                        </div>
                    </div>
                    <Popover
                        classes={{
                            paper: classes.popover
                        }}
                        open={Boolean(this.state.anchorElLeft)}
                        anchorEl={this.state.anchorElLeft}
                        onClose={() => this.setAnchorElLeft(null)}
                        anchorOrigin={{
                            vertical: "center",
                            horizontal: "left"
                        }}
                        transformOrigin={{
                            vertical: "center",
                            horizontal: "right"
                        }}
                    >
                        <h3 className={classes.popoverHeader}>Are you sure you want
                            to remove <b>{this.state.currentGame === null ? '' : this.state.currentGame.text}</b> from
                            wishList?
                        </h3>

                        <div className={classes.popoverBody}
                            style={{ "text-align": "center" }}>
                            <Button color="success" round size="sm"
                                onClick={this.confirmRemoval}>Yes</Button>
                            <Button color="danger" round size="sm"
                                onClick={() => this.setAnchorElLeft(null)}>No</Button>
                        </div>

                    </Popover>

                </div>


            </div>
        )
    }
}


export default withStyles(Object.assign({}, styles, javascriptStyles))(WishList);