    
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

// Core Components
import Header from "components/Header/Header.js";
import Footer from "components/Footer/Footer.js";

import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import Button from "components/CustomButtons/Button.js";
import HeaderLinks from "components/Header/HeaderLinks.js";
import Parallax from "components/Parallax/Parallax.js";
import CustomInput from "components/CustomInput/CustomInput.js";

// Toastify
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast, Flip } from 'react-toastify';

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
    Link,
    Redirect
} from "react-router-dom";

class GameSearch extends Component {
    constructor() {
        super();
    }

    state = {
        doneLoading: false,
        redirectLogin: false,
        animationOptions: {
            loop: true, autoplay: true, animationData: loadingAnim.default, rendererSettings: {
                preserveAspectRatio: "xMidYMid slice"
            }
        },
        games: [],
        gamesLoaded: false,
        curPage: 1,
        noPages: 1,
        noGames: 0,
        cacheRequest: null
    }

    async allGames() {
        var login_info = null
        if (global.user != null) {
            login_info = global.user.token
        }

        await this.setState({ gamesLoaded: false })

        // Get All Games
        await fetch(baseURL + "grid/all?page=" + (this.state.curPage - 1), {
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
                    if (data.first) {
                        this.setState({
                            noPages: data.totalPages,
                            noGames: data.totalElements
                        })

                    }
                    this.setState({ games: data.content })
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

    async search(changePage) {
        await this.setState({ gamesLoaded: false })

        if (changePage) {
            var body = this.state.cacheRequest
            body["page"] = this.state.curPage

            await this.setState({
                cacheRequest: body
            })


            // Proceed to login
            await fetch(baseURL + "grid/search", {
                method: "POST",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(this.state.cacheRequest)
            })
                .then(response => {
                    if (response.status === 400 || response.status === 200) {
                        return response.json()
                    }
                    else throw new Error(response.status);
                })
                .then(data => {
                    if (data.status === 400) { // Wrong Credentials
                        toast.error(data.message, {
                            position: "top-center",
                            hideProgressBar: false,
                            closeOnClick: true,
                            pauseOnHover: true,
                            draggable: true,
                            toastId: "errorTwoToast"
                        });


                    } else { // Successful Login
                        this.setState({ games: data.content })
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
            var name = document.getElementById("name").value
            var priceFrom = document.getElementById("priceFrom").value
            var priceTo = document.getElementById("priceTo").value

            var genres = []
            var platforms = []
            window.scrollTo(0, 0)

            console.log(priceFrom)
            console.log(priceTo)

            for (var i = 0; i < 19; i++) {
                if (document.getElementById('genre_' + i).checked) {
                    genres.push(document.getElementById("genre_" + i).name)
                }
            }

            for (var i = 0; i < 6; i++) {
                if (document.getElementById('platform_' + i).checked) {
                    platforms.push(document.getElementById("platform_" + i).name)
                }
            }

            var error = false


            if (isNaN(priceFrom) || isNaN(priceTo)) {
                toast.error('Oops, the specified price ranges must be numbers!', {
                    position: "top-center",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    toastId: "errorRangeNumber"
                });
                error = true
            }

            if (!error) {
                await this.setState({
                    curPage: 1
                })

                var body = {
                    "page": this.state.curPage - 1

                }

                if (name != null && name != "") {
                    body["name"] = name
                }

                if (priceFrom != null && priceFrom != "") {
                    body["startPrice"] = parseFloat(priceFrom)
                }

                if (priceTo != null && priceTo != "") {
                    body["endPrice"] = parseFloat(priceTo)
                }

                if (genres != null && genres != []) {
                    body["genres"] = genres
                }

                if (platforms != null && platforms != []) {
                    body["platforms"] = platforms
                }

                console.log(body)

                // Proceed to login
                await fetch(baseURL + "grid/search", {
                    method: "POST",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(body)
                })
                    .then(response => {
                        if (response.status === 400 || response.status === 200) {
                            return response.json()
                        }
                        else throw new Error(response.status);
                    })
                    .then(data => {
                        if (data.status === 400) { // Wrong Credentials
                            toast.error(data.message, {
                                position: "top-center",
                                hideProgressBar: false,
                                closeOnClick: true,
                                pauseOnHover: true,
                                draggable: true,
                                toastId: "errorTwoToast"
                            });


                        } else { // Successful Login
                            if (data.first) {
                                this.setState({
                                    noPages: data.totalPages,
                                    noGames: data.totalElements
                                })

                            }
                            this.setState({ games: data.content, cacheRequest: body })

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
            }
        }

        await this.setState({ gamesLoaded: true })

    }

    async componentDidMount() {
        await this.allGames()
        this.setState({ doneLoading: true, })
    }

    renderRedirectLogin = () => {
        if (this.state.redirectLogin) {
            return <Redirect to='/login-page' />
        }
    }

    changePage = async (event, value) => {
        await this.setState({
            curPage: value
        })

        window.scrollTo(0, 0)

        if (this.state.cacheRequest != null) {
            await this.search(true)

        } else {
            await this.allGames()
        }

    };

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


            var loadedItems = <div></div>

            var empty = false

            if (this.state.gamesLoaded) {
                var items = []
                var i = -1

                this.state.games.forEach(game => {

                    i++
                    var style = { marginTop: "25px" }

                    if (i == 0 || i == 1 || i == 2) {
                        style = {}
                    }

                    var bestPrice = <h6 style={{ color: "#999", fontSize: "15px", paddingTop: "0 0", marginTop: "0px" }}>
                        No one is selling this game...
                    </h6>
                    if (game.bestSell != null) {
                        bestPrice = <h6 style={{ color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px" }}>
                            As low as <span style={{ fontWeight: "bolder", color: "#f44336", fontSize: "17px" }}> {game.bestSell.price}€</span>
                        </h6>
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
                                                {bestPrice}
                                            </div>
                                        </CardContent>
                                    </CardActionArea>
                                </Card>
                            </Link >
                        </GridItem>
                    )
                })

                if (items.length == 0) {
                    empty = true
                    items.push(
                        <GridItem xs={12} sm={12} md={12}>
                            <div style={{ textAlign: "left" }}>
                                <h3 style={{ color: "#999" }}>
                                    Sorry, there don't seem to be any games like that in the Grid...
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

            var pagination = null
            if (!empty && this.state.gamesLoaded) {
                pagination = <div style={{ padding: "25px 40px" }}>
                    <GridContainer xs={12} sm={12} md={12}>
                        <div className={"search"} style={{ margin: "auto", width: "42%" }}>
                            <Pagination count={this.state.noPages} page={this.state.curPage} onChange={this.changePage} variant="outlined" shape="rounded" />
                        </div>
                    </GridContainer>
                    <GridContainer xs={12} sm={12} md={12}>
                        <div className={"searchMobile"} style={{ margin: "auto", width: "90%" }}>
                            <Pagination count={this.state.noPages} page={this.state.curPage} onChange={this.changePage} variant="outlined" shape="rounded" />
                        </div>
                    </GridContainer>
                </div>
            }

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

                        <div className={"search"} style={{ position: "absolute", top: "200px", right: "25px", zIndex: "100" }}>
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
                                                    id="priceFrom"
                                                    formControlProps={{
                                                        fullWidth: false
                                                    }}
                                                />
                                                <span style={{ marginRight: "5px", marginLeft: "5px" }}></span>
                                                <CustomInput
                                                    labelText="To"
                                                    id="priceTo"
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
                                                                id="genre_0"
                                                                name="Action"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Action"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                name="Indie"
                                                                id="genre_1"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Indie"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_2"
                                                                name="Adventure"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Adventure"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_3"
                                                                name="RPG"
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
                                                                id="genre_4"
                                                                name="Strategy"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Strategy"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_5"
                                                                name="Shooter"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Shooter"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_6"
                                                                name="Casual"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Casual"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_7"
                                                                name="Simulation"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Simulation"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_8"
                                                                name="Puzzle"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Puzzle"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_9"
                                                                name="Arcade"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Arcade"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_10"
                                                                name="Platformer"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Platformer"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_11"
                                                                name="Racing"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Racing"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_12"
                                                                name="Sports"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Sports"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_13"
                                                                name="Massively Multiplayer"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Massively Multiplayer"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_14"
                                                                name="Family"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Family"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_15"
                                                                name="Fighting"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Fighting"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_16"
                                                                name="Board Games"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Board Games"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_17"
                                                                name="Card"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Card"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_18"
                                                                name="Educational"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Educational"
                                                    />
                                                </div>

                                            </div>

                                            <div style={{ textAlign: "left", width: "100%", marginTop: "30px" }}>
                                                <h6 style={{ fontWeight: "bold", color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px", marginBottom: "0px" }}>
                                                    Platforms
                                                </h6>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="platform_0"
                                                                name="Steam"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Steam"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="platform_1"
                                                                name="Origin"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Origin"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="platform_2"
                                                                name="Epic Games Store"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Epic Games Store"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="platform_3"
                                                                name="PS4"
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
                                                                id="platform_4"
                                                                name="Xbox One"
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
                                                                id="platform_5"
                                                                name="Nintendo eShop"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Nintendo eShop"
                                                    />
                                                </div>
                                            </div>
                                            <div style={{ marginTop: "20px" }}>
                                                <Button
                                                    color="rose"
                                                    size="md"
                                                    onClick={() => this.search(false)}
                                                    target="_blank"
                                                    rel="noopener noreferrer"
                                                    style={{ width: "100%", backgroundColor: "#fc3196" }}
                                                    id="confirm"
                                                >
                                                    Search
                                            </Button>
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
                                                        <h2 id="numberOfProducts" style={{ color: "#999", fontWeight: "bolder", marginTop: "0px", padding: "0 0" }}>Games <span style={{ color: "#999", fontSize: "15px", fontWeight: "normal" }}>({this.state.noGames} products)</span>
                                                        </h2>
                                                    </span>
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
                                                    id="priceFrom"
                                                    formControlProps={{
                                                        fullWidth: false
                                                    }}
                                                />
                                                <span style={{ marginRight: "5px", marginLeft: "5px" }}></span>
                                                <CustomInput
                                                    labelText="To"
                                                    id="priceTo"
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
                                                                id="genre_0"
                                                                name="Action"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Action"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                name="Indie"
                                                                id="genre_1"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Indie"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_2"
                                                                name="Adventure"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Adventure"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_3"
                                                                name="RPG"
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
                                                                id="genre_4"
                                                                name="Strategy"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Strategy"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_5"
                                                                name="Shooter"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Shooter"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_6"
                                                                name="Casual"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Casual"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_7"
                                                                name="Simulation"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Simulation"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_8"
                                                                name="Puzzle"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Puzzle"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_9"
                                                                name="Arcade"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Arcade"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_10"
                                                                name="Platformer"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Platformer"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_11"
                                                                name="Racing"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Racing"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_12"
                                                                name="Sports"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Sports"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_13"
                                                                name="Massively Multiplayer"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Massively Multiplayer"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_14"
                                                                name="Family"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Family"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_15"
                                                                name="Fighting"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Fighting"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_16"
                                                                name="Board Games"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Board Games"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_17"
                                                                name="Card"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Card"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="genre_18"
                                                                name="Educational"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Educational"
                                                    />
                                                </div>

                                            </div>
                                        </ExpansionPanelDetails>

                                        <ExpansionPanelDetails>
                                            <div style={{ textAlign: "left", width: "100%", marginTop: "30px" }}>
                                                <h6 style={{ fontWeight: "bold", color: "#3b3e48", fontSize: "15px", paddingTop: "0 0", marginTop: "0px", marginBottom: "0px" }}>
                                                    Platforms
                                                </h6>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="platform_0"
                                                                name="Steam"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Steam"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="platform_1"
                                                                name="Origin"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Origin"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="platform_2"
                                                                name="Epic Games Store"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Epic Games Store"
                                                    />
                                                </div>

                                                <div class="row">
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox
                                                                tabIndex={-1}
                                                                id="platform_3"
                                                                name="PS4"
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
                                                                id="platform_4"
                                                                name="Xbox One"
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
                                                                id="platform_5"
                                                                name="Nintendo eShop"
                                                                checkedIcon={<Check className={classes.checkedIcon} />}
                                                                icon={<Check className={classes.uncheckedIcon} />}
                                                                classes={{
                                                                    checked: classes.checked,
                                                                    root: classes.checkRoot
                                                                }}
                                                            />
                                                        }
                                                        classes={{ label: classes.label, root: classes.labelRoot }}
                                                        label="Nintendo eShop"
                                                    />
                                                </div>
                                            </div>
                                        </ExpansionPanelDetails>

                                        <div style={{ marginTop: "20px" }}>
                                            <Button
                                                color="rose"
                                                size="md"
                                                onClick={() => this.search(false)}
                                                target="_blank"
                                                rel="noopener noreferrer"
                                                style={{ width: "100%", backgroundColor: "#fc3196" }}
                                                id="confirm"
                                            >
                                                Search
                                            </Button>
                                        </div>
                                    </ExpansionPanel>
                                </GridContainer>
                            </div>


                            <div style={{ padding: "5px 0" }}>
                                <GridContainer xs={12} sm={12} md={11}>
                                    {loadedItems}
                                </GridContainer>
                            </div>


                            {pagination}
                        </div>


                        <Footer rawg={true} />
                    </div>
                </div >
            )
        }

    }
}

export default withStyles(styles)(GameSearch);


