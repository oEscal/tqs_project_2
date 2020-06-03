import React, { Component } from 'react';
import classNames from "classnames";
import styles from "assets/jss/material-kit-react/views/landingPage.js";
import 'assets/css/hide.css'
import { withStyles } from '@material-ui/styles';
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import LoggedHeader from "components/MyHeaders/LoggedHeader.js"
import ExistingGame from "./Sections/ExistingGame";
import "assets/css/existGame.css"
import Switch from "react-switch";
import AsyncSelect from 'react-select/async';
import InputAdornment from "@material-ui/core/InputAdornment";
import EuroSymbolIcon from '@material-ui/icons/EuroSymbol';
import CalendarTodayIcon from '@material-ui/icons/CalendarToday';
import VpnKeyIcon from '@material-ui/icons/VpnKey';
import VideogameAssetIcon from '@material-ui/icons/VideogameAsset';
import Button from "components/CustomButtons/Button.js";
import CardFooter from "components/Card/CardFooter.js";
import CustomInput from "components/CustomInput/CustomInput.js"


// Global Variables
import baseURL from '../../variables/baseURL'
import global from "../../variables/global";


// @material-ui/core components
import Icon from "@material-ui/core/Icon";
import InputLabel from "@material-ui/core/InputLabel";
import FormControl from "@material-ui/core/FormControl";


// react plugin for creating date-time-picker
import Datetime from "react-datetime";

// @material-ui/icons
import Email from "@material-ui/icons/Email";
import People from "@material-ui/icons/People";

// core components
import Header from "components/Header/Header.js";
import HeaderLinks from "components/Header/HeaderLinks.js";
import Footer from "components/Footer/Footer.js";
import Card from "components/Card/Card.js";
import CardBody from "components/Card/CardBody.js";
import CardHeader from "components/Card/CardHeader.js";


// Toastify
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast, Flip } from 'react-toastify';

// React Select
import Select from 'react-select';
import makeAnimated from 'react-select/animated';

import {
    Link,
    Redirect
} from "react-router-dom";

// Loading Animation
import FadeIn from "react-fade-in";
import Lottie from "react-lottie";
import * as loadingAnim from "assets/animations/loading_anim.json";


class SellGame extends Component {

    constructor(props) {

        super(props);
    }

    state = {
        checked: false,
        newKey: true,

        userGames: [],

        games: [],
        game: null,
        selectedKey: null,

        platform: null,

        doneLoading: false,
        animationOptions: {
            loop: true, autoplay: true, animationData: loadingAnim.default, rendererSettings: {
                preserveAspectRatio: "xMidYMid slice"
            }
        },

        redirectProfile: false,
        redirectLogin: false

    }

    async searchGame(inputVal) {
        var login_info = null
        if (global.user != null) {
            login_info = global.user.token
        }

        // Get All Games

        var arr = []

        await fetch(baseURL + "grid/name?name=" + inputVal, {
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
                    data.forEach(game => {
                        arr.push({ "value": game.id, "label": game.name })
                    })

                }
            })
            .catch(error => {
                console.log(error)
            });

        return arr
    }

    async allGames() {
        var login_info = null
        if (global.user != null) {
            login_info = global.user.token
        }

        var arr = []

        // Get All Games
        await fetch(baseURL + "grid/all?page=0", {
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
                    data.content.forEach(game => {
                        arr.push({ "value": game.id, "label": game.name })
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
                    toastId: "errorToast"
                });
            });
        return arr
    }

    async getUserKeys() {
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
                    data["token"] = login_info
                    localStorage.setItem('loggedUser', JSON.stringify(data));
                    global.user = JSON.parse(localStorage.getItem('loggedUser'))

                    var userKeys = []
                    for (var i = 0; i < data.buys.length; i++) {
                        var game = data.buys[i]
                        userKeys.push({ "value": game.gamerKey, "label": game.gameName + " (" + game.gamerKey + ")" })
                    }
                    this.setState({ userGames: userKeys })
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

    async componentDidMount() {
        await this.getUserKeys()
        this.setState({ doneLoading: true, })
    }

    setChecked = async () => {
        await this.setState({
            checked: !this.state.checked
        })
    }

    setNewKey = async () => {
        await this.setState({
            newKey: !this.state.newKey
        })
    }

    renderRedirectProfile = () => {
        if (this.state.redirectProfile) {
            return <Redirect to={"/user/" + global.user.username} />
        }
    }

    renderRedirectLogin = () => {
        if (this.state.redirectLogin) {
            return <Redirect to='/login-page' />
        }
    }

    async newKey() {
        var game = this.state.game
        var key = document.getElementById("key").value
        var price = document.getElementById("price").value
        var platform = document.getElementById("platform").textContent

        var endDate = null
        if (this.state.checked) {
            endDate = document.getElementById("endDate").value
        }

        var error = false
        // Check if fields are filled
        if (game == null || platform == null || game.value == null || key == null || price == null || game.value == "" || key == "" || price == "" || platform == "" || platform == "Platform") {
            toast.error('Oops, you\'ve got to specify, all fields!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorMinimum"
            });
            error = true
        }

        // Check if price is numeric
        if (isNaN(price)) {
            toast.error('You must specify a valid selling price!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorPrice"
            });
            error = true
        }

        if (this.state.checked && (endDate == "" || endDate == null)) {
            toast.error('For auctions you must specify the auction\'s closure date!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorDate1"
            });
            error = true
        }

        if (this.state.checked) {
            var tempExpiration = endDate.split("/")
            if (tempExpiration.length != 3) {
                toast.error('Please use a valid closure date...', {
                    position: "top-center",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    toastId: "errorDate2"
                });
                error = true
            } else {
                endDate = tempExpiration[1] + "/" + tempExpiration[0] + "/" + tempExpiration[2]
            }
        }

        if (!error) {
            price = parseFloat(price)

            var success = false
            var redirectLogin = false
            var successAddingKey = false

            var login_info = null
            if (global.user != null) {
                login_info = global.user.token
            }

            await this.setState({ doneLoading: false })

            var body = {
                "gameId": game.value,
                "key": key,
                "platform": platform,
                "retailer": global.user.username
            }

            // Insert new Key
            await fetch(baseURL + "grid/gamekey", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: login_info
                },
                body: JSON.stringify(body)
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
                    console.log(data)
                    if (data.status === 401) { // Wrong token
                        localStorage.setItem('loggedUser', null);
                        global.user = JSON.parse(localStorage.getItem('loggedUser'))

                        redirectLogin = true
                    } else {
                        successAddingKey = true

                    }
                })
                .catch(error => {
                    console.log(error)
                    toast.error('Sorry, an unexpected error has occurred when creating the key!', {
                        position: "top-center",
                        hideProgressBar: false,
                        closeOnClick: true,
                        pauseOnHover: true,
                        draggable: true,
                        toastId: "errorToast"
                    });
                });

            if (successAddingKey) {
                if (!this.state.checked) {
                    var d = new Date();

                    var body2 = {
                        "gameKey": key,
                        "price": price,
                        "userId": global.user.id,
                        "date": d.toISOString()
                    }
                    // Create selling
                    await fetch(baseURL + "grid/add-sell-listing", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                            Authorization: login_info
                        },
                        body: JSON.stringify(body2)
                    })
                        .then(response => {
                            if (response.status === 401 || response.status === 409) {
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

                                redirectLogin = true

                            } else if (data.status === 409) { // Wrong token
                                toast.error('Sorry, that key has already been registered!', {
                                    position: "top-center",
                                    hideProgressBar: false,
                                    closeOnClick: true,
                                    pauseOnHover: true,
                                    draggable: true,
                                    toastId: "errorToast"
                                });

                            } else {
                                success = true

                            }

                        })
                        .catch(error => {
                            console.log(error)
                            toast.error('Sorry, an unexpected error has occurred when creating the listing!', {
                                position: "top-center",
                                hideProgressBar: false,
                                closeOnClick: true,
                                pauseOnHover: true,
                                draggable: true,
                                toastId: "errorToast"
                            });
                        });
                } else {
                    var body2 = {
                        "gameKey": key,
                        "price": price,
                        "auctioneer": global.user.id,
                        "endDate": endDate
                    }

                    console.log(body2);
                    console.log(login_info)

                    // Create selling
                    fetch(baseURL + "grid/create-auction", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                            Authorization: login_info
                        },
                        body: JSON.stringify(body2)
                    })
                        .then(response => {
                            console.log(response)
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

                                redirectLogin = true


                            } else {
                                success = true
                            }

                        })
                        .catch(error => {
                            console.log(error)
                            toast.error('Sorry, an unexpected error has occurred when creating the auction!', {
                                position: "top-center",
                                hideProgressBar: false,
                                closeOnClick: true,
                                pauseOnHover: true,
                                draggable: true,
                                toastId: "errorToast"
                            });
                        });
                }

            }

            await this.setState({ redirectLogin: redirectLogin, redirectProfile: success, doneLoading: true, })
        }
    }

    async existingKey() {
        var key = this.state.selectedKey
        var price = document.getElementById("price").value
        var platform = document.getElementById("gameKey").textContent

        var endDate = null
        if (this.state.checked) {
            endDate = document.getElementById("endDate").value
        }

        var error = false
        // Check if fields are filled
        if (key == null || key == "" || key.value == null || key.value == "" || price == "" || platform == "" || platform == null || price == null) {
            toast.error('Oops, you\'ve got to fill all fields!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorMinimum"
            });
            error = true
        }

        // Check if price is numeric
        if (isNaN(price)) {
            toast.error('You must specify a valid selling price!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorPrice"
            });
            error = true
        }

        if (this.state.checked && (endDate == "" || endDate == null)) {
            toast.error('For auctions you must specify the auction\'s closure date!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorDate1"
            });
            error = true
        }

        if (this.state.checked) {
            var tempExpiration = endDate.split("/")
            if (tempExpiration.length != 3) {
                toast.error('Please use a valid closure date...', {
                    position: "top-center",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    toastId: "errorDate2"
                });
                error = true
            } else {
                endDate = tempExpiration[1] + "/" + tempExpiration[0] + "/" + tempExpiration[2]
            }
        }

        if (!error) {
            price = parseFloat(price)
            var d = new Date();

            var success = false
            var redirectLogin = false

            var login_info = null
            if (global.user != null) {
                login_info = global.user.token
            }

            await this.setState({ doneLoading: false })

            if (!this.state.checked) {
                var body2 = {
                    "gameKey": key.value,
                    "price": price,
                    "userId": global.user.id,
                    "date": d.toISOString()
                }

                // Create selling
                fetch(baseURL + "grid/add-sell-listing", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: login_info
                    },
                    body: JSON.stringify(body2)
                })
                    .then(response => {
                        console.log(response)
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

                            redirectLogin = true


                        } else {
                            success = true
                        }

                    })
                    .catch(error => {
                        console.log(error)
                        toast.error('Sorry, an unexpected error has occurred when creating the auction!', {
                            position: "top-center",
                            hideProgressBar: false,
                            closeOnClick: true,
                            pauseOnHover: true,
                            draggable: true,
                            toastId: "errorToast"
                        });
                    });
            } else {
                var body2 = {
                    "gameKey": key.value,
                    "price": price,
                    "auctioneer": global.user.id,
                    "endDate": endDate
                }

                // Create selling
                fetch(baseURL + "grid/create-auction", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: login_info
                    },
                    body: JSON.stringify(body2)
                })
                    .then(response => {
                        console.log(response)
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

                            redirectLogin = true


                        } else {
                            success = true
                        }

                    })
                    .catch(error => {
                        console.log(error)
                        toast.error('Sorry, an unexpected error has occurred when creating the auction!', {
                            position: "top-center",
                            hideProgressBar: false,
                            closeOnClick: true,
                            pauseOnHover: true,
                            draggable: true,
                            toastId: "errorToast"
                        });
                    });
            }

            await this.setState({ doneLoading: true, redirectLogin: redirectLogin, redirectProfile: success })
        }

    }

    async confirm() {
        if (this.state.newKey) {
            await this.newKey()
        } else {
            await this.existingKey()
        }
    }

    changeSelectedGame = (selectedOption) => {
        if (selectedOption != null) {
            this.setState({ game: selectedOption });
        } else {
            this.setState({ game: null });
        }
    }

    changeSelectedKey = (selectedOption) => {
        if (selectedOption != null) {
            this.setState({ selectedKey: selectedOption });
        } else {
            this.setState({ selectedKey: null });
        }
    }

    loadGames = inputValue =>
        new Promise(resolve => {
            if (inputValue == "" || inputValue == null) {
                var requestValues = this.allGames()
                resolve(requestValues)
            } else {
                var requestValues = this.searchGame(inputValue)
                resolve(requestValues)

            }
        });


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

        var today = Datetime.moment()
        var valid = function (current) {
            return current.isAfter(today);
        };

        var key = null
        if (this.state.newKey) {
            key = <div style={{ marginTop: "25px", color: "black" }}>
                <AsyncSelect
                    placeholder="Game"
                    id="game"
                    components={makeAnimated()}
                    defaultOptions
                    value={this.state.game || ''}
                    loadOptions={this.loadGames}
                    onChange={this.changeSelectedGame}

                    cacheOptions
                />
                <CustomInput
                    labelText="Game Key"
                    id="key"
                    formControlProps={{
                        fullWidth: true
                    }}
                    inputProps={{
                        type: "text",
                        endAdornment: (
                            <InputAdornment position="end">
                                <i class="fas fa-key"></i>
                            </InputAdornment>
                        )
                    }}
                />
                <div style={{ marginTop: "25px", color: "black" }}>
                    <Select

                        className="basic-single"
                        classNamePrefix="select"
                        defaultValue={null}
                        id="platform"
                        placeholder="Platform"
                        options={[{ "value": "Steam", "label": "Steam" }, { "value": "Origin", "label": "Origin" }, { "value": "Epic Games Store", "label": "Epic Games Store" }, { "value": "PS4", "label": "PS4" }, { "value": "XBox One", "label": "XBox One" }, { "value": "Nintendo eShop", "label": "Nintendo eShop" }]}
                    />

                </div>
            </div>
        } else {
            key = <div style={{ marginTop: "25px", color: "black" }}>
                <Select
                    defaultValue={[]}
                    id="gameKey"
                    options={this.state.userGames}
                    className="basic-single"
                    classNamePrefix="select"
                    placeholder="Owned GameKey"
                    value={this.state.selectedKey || ''}
                    onChange={this.changeSelectedKey}
                    components={makeAnimated()}
                />

            </div>
        }

        var endDate = null
        if (this.state.checked) {
            endDate = <div style={{ marginTop: "20px" }}>
                <FormControl fullWidth style={{ color: "black" }}>
                    <Datetime
                        timeFormat={false}
                        inputProps={{ placeholder: "End Date", id: "endDate" }}
                        isValidDate={valid}
                    />
                </FormControl>
            </div>
        }

        return (
            <div>
                <LoggedHeader user={global.user} cart={global.cart} heightChange={false} height={600} />
                {this.renderRedirectLogin()}
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

                <div className={classNames(classes.main)} style={{ marginTop: "60px", "background-size": "cover" }}>

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
                                                }}>Sell a game</h2>
                                            </GridItem>
                                        </GridContainer>
                                        <hr style={{ color: "#999", opacity: "0.4" }}></hr>

                                        <GridContainer>
                                            <GridItem xs={12} sm={12} md={12}>
                                                <form className={classes.form}>

                                                    <label htmlFor="normal-switch" className="switchClass" style={{ "padding-top": "20px" }}>
                                                        <span>New Key</span>&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <Switch
                                                            checked={this.state.newKey}
                                                            onChange={() => this.setNewKey()}
                                                            id="normal-switch"
                                                            className="react-switch"
                                                            onColor="#9c27b0"
                                                        />
                                                    </label>

                                                    {key}

                                                    <hr style={{ opacity: 0.2, color: "#fc3196", marginTop: "22px" }} />

                                                    <label htmlFor="normal-switch" className="switchClass" style={{ "padding-top": "20px" }}>
                                                        <span>Auction</span>&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <Switch
                                                            checked={this.state.checked}
                                                            onChange={() => this.setChecked()}
                                                            id="normal-switch"
                                                            className="react-switch"
                                                            onColor="#9c27b0"
                                                        />
                                                    </label>

                                                    <CustomInput

                                                        labelText="Starting Price"
                                                        id="price"
                                                        formControlProps={{
                                                            fullWidth: true,
                                                        }}
                                                        inputProps={{
                                                            endAdornment: (
                                                                <InputAdornment position="end">
                                                                    <EuroSymbolIcon className={classes.inputIconsColor} />
                                                                </InputAdornment>
                                                            ),
                                                            autoComplete: "off"
                                                        }}

                                                    />

                                                    {endDate}


                                                    <hr style={{ opacity: 0, color: "#fc3196", marginTop: "30px" }} />


                                                    <GridContainer xs={12} sm={12} md={12}>
                                                        <GridItem xs={12} sm={12} md={12}>
                                                            <Button
                                                                color="rose"
                                                                size="md"
                                                                onClick={() => this.confirm()}
                                                                target="_blank"
                                                                rel="noopener noreferrer"
                                                                style={{ backgroundColor: "#fc3196" }}
                                                                id="confirm"
                                                            >
                                                                Confirm
                                                                </Button>
                                                        </GridItem>


                                                    </GridContainer>
                                                </form>
                                            </GridItem>
                                        </GridContainer>

                                    </div>
                                </GridItem>
                            </GridContainer>
                        </div>
                    </div>


                </div>


            </div>
        )
    }
}


export default withStyles(styles)(SellGame);