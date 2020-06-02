
import React, { Component } from 'react';
import classNames from "classnames";

// Global Variables
import baseURL from '../../variables/baseURL'
import global from "../../variables/global";

// Styles
import styles from "assets/jss/material-kit-react/views/landingPage.js";
import "./edit.css";
import 'react-toastify/dist/ReactToastify.css';


// react plugin for creating date-time-picker
import Datetime from "react-datetime";

// Core MaterialUI 
import { withStyles } from '@material-ui/styles';
import CardMedia from '@material-ui/core/CardMedia';
import CardContent from '@material-ui/core/CardContent';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardActionArea from '@material-ui/core/CardActionArea';
import Pagination from '@material-ui/lab/Pagination';
import InputLabel from "@material-ui/core/InputLabel";
import FormControl from "@material-ui/core/FormControl";

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


// Toastify
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast, Flip } from 'react-toastify';


// MaterialUI Icons
import Check from "@material-ui/icons/Check";
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import Dashboard from "@material-ui/icons/Dashboard";
import Schedule from "@material-ui/icons/Schedule";

// Images
import image1 from "assets/img/bg.jpg";
import image4 from "assets/img/NFS-Heat.jpg";
import image from "assets/img/favicon.png";

// Loading Animation
import FadeIn from "react-fade-in";
import Lottie from "react-lottie";
import * as loadingAnim from "assets/animations/loading_anim.json";

// Router
import {
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
        confirmed: false,
    }

    async componentDidMount() {
        window.scrollTo(0, 0)
        this.setState({ doneLoading: true })
    }

    //METHODS////////////////////////////////////
    validateEmail(email) {
        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }

    async confirm() {
        var name = document.getElementById("name").value
        var country = document.getElementById("country").textContent
        var birthday = document.getElementById("birthday").value

        var description = document.getElementById("description").value

        var email = document.getElementById("email").value


        var pass = document.getElementById("pass").value
        var passConfirm = document.getElementById("passConfirm").value

        var cardNumber = document.getElementById("cardNumber").value
        var cardName = document.getElementById("cardName").value
        var cardCVC = document.getElementById("cardCVC").value
        var expiration = document.getElementById("cardExpiration").value

        var error = false

        var tempBirth = birthday.split("/")
        if (!error && (tempBirth != "" && tempBirth.length != 3)) {
            toast.error('Please use a valid birthday...', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorBirthday"
            });
            error = true
        } else {
            if (tempBirth != "") {
                birthday = tempBirth[1] + "/" + tempBirth[0] + "/" + tempBirth[2]
            }
        }

        if (!error && (email != "" && !this.validateEmail(email))) {
            toast.error('Please use a valid email...', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorEmail"
            });
            error = true
        } else {
            if (email != "") {
                email = email.toLowerCase()
            }
        }


        if (!error && (pass != "" && pass != passConfirm)) {
            toast.error('Oops, your passwords don\'t match!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorPass"
            });
            error = true
        }


        if (!error && (cardNumber != '' || cardCVC != '' || expiration != '' || cardName != '')) {
            if (cardNumber == '' || cardCVC == '' || expiration == '' || cardName == '') {
                toast.error('Oops, if you want to add your payment info, you\'ve got to specify all four card information fields!', {
                    position: "top-center",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    toastId: "errorCardAll"
                });
                error = true
                console.log(document.getElementById("errorMinimum"))
            }
        }

        if (!error && expiration != "" && expiration != null) {
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

        if (!error && (cardNumber != "" && cardNumber != null && (!(/^\d+$/.test(cardNumber)) || cardNumber.length < 9 || cardName.length > 19))) {
            toast.error('Oops, the credit card number must contain only numbers and have at least 9 digits and less than 19!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                toastId: "errorCardNumber"
            });
            error = true
        }


        if (!error && (cardCVC != "" && cardCVC != null && (!(/^\d+$/.test(cardCVC)) || cardCVC.length > 4 || cardCVC.length < 3))) {
            toast.error('Oops, the CVC must contain only numbers and have 3 or 4 digits!', {
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

        if (country == "Country*") {
            country = null
        }

        if (!error) {
            await this.setState({
                doneLoading: false
            })

            var success = false


            var body = {
                "birthDate": birthday == "" ? null : birthday,
                "country": country == "" ? null : country,
                "creditCardCSC": cardCVC == "" ? null : cardCVC,
                "creditCardExpirationDate": expiration == "" ? null : expiration,
                "creditCardNumber": cardNumber == "" ? null : cardNumber,
                "creditCardOwner": cardName == "" ? null : cardName,
                "description": description == "" ? null : description,
                "email": email == "" ? null : email,
                "name": name == "" ? null : name,
                "password": pass == "" ? null : pass
            }

            var onSuccessLogin = (pass != "" && pass != null)
            console.log(onSuccessLogin)

            if (global.user == null) {
                localStorage.setItem('loggedUser', null);
                global.user = JSON.parse(localStorage.getItem('loggedUser'))

                this.setState({
                    redirectLogin: true
                })

            }
            await fetch(baseURL + "grid/user", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: global.user.token
                },
                body: JSON.stringify(body)
            })
                .then(response => {
                    if (response.status == 401) {
                        return response
                    }
                    else if (response.status === 200) {
                        return response.json()
                    }
                    else throw new Error(response.status);
                })
                .then(data => {
                    if (data.status === 401) {
                        localStorage.setItem('loggedUser', null);
                        global.user = JSON.parse(localStorage.getItem('loggedUser'))

                        this.setState({
                            redirectLogin: true
                        })


                    } else { // Successful Login
                        if (onSuccessLogin) {
                            const token = "Basic " + btoa(data.username + ":" + pass);
                            data["token"] = token
                            localStorage.setItem('loggedUser', JSON.stringify(data));
                            global.user = JSON.parse(localStorage.getItem('loggedUser'))

                        } else {
                            data["token"] = global.user.token
                            localStorage.setItem('loggedUser', JSON.stringify(data));
                            global.user = JSON.parse(localStorage.getItem('loggedUser'))
                        }

                        success = true

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

            await this.setState({
                doneLoading: true,
                confirmed: success
            })
        }
    }

    ////////////////////////////////////////////

    render() {
        const { classes } = this.props;

        if (this.state.redirectLogin) {
            return <Redirect to='/login-page' />
        }
        if (this.state.confirmed) {
            var redirect
            if (global.user != null) {
                redirect = "/user/" + global.user.username
            } else {
                redirect = "/login-page"
            }
            return <Redirect to={redirect} />
        }

        if (!this.state.doneLoading) {
            return (
                <div>
                    <LoggedHeader user={global.user} cart={global.cart} heightChange={false} height={600} />

                    <div className="animated fadeOut animated" style={{ width: "100%", marginTop: "15%" }} id="firstLoad">
                        <FadeIn>
                            <Lottie options={this.state.animationOptions} height={"20%"} width={"20%"} />
                        </FadeIn>
                    </div>
                </div>
            )
        } else {
            const countryListTemp = [
                "Afghanistan",
                "Albania",
                "Algeria",
                "American Samoa",
                "Andorra",
                "Angola",
                "Anguilla",
                "Antarctica",
                "Antigua and Barbuda",
                "Argentina",
                "Armenia",
                "Aruba",
                "Australia",
                "Austria",
                "Azerbaijan",
                "Bahamas (the)",
                "Bahrain",
                "Bangladesh",
                "Barbados",
                "Belarus",
                "Belgium",
                "Belize",
                "Benin",
                "Bermuda",
                "Bhutan",
                "Bolivia (Plurinational State of)",
                "Bonaire, Sint Eustatius and Saba",
                "Bosnia and Herzegovina",
                "Botswana",
                "Bouvet Island",
                "Brazil",
                "British Indian Ocean Territory (the)",
                "Brunei Darussalam",
                "Bulgaria",
                "Burkina Faso",
                "Burundi",
                "Cabo Verde",
                "Cambodia",
                "Cameroon",
                "Canada",
                "Cayman Islands (the)",
                "Central African Republic (the)",
                "Chad",
                "Chile",
                "China",
                "Christmas Island",
                "Cocos (Keeling) Islands (the)",
                "Colombia",
                "Comoros (the)",
                "Congo (the Democratic Republic of the)",
                "Congo (the)",
                "Cook Islands (the)",
                "Costa Rica",
                "Croatia",
                "Cuba",
                "Curaçao",
                "Cyprus",
                "Czechia",
                "Côte d'Ivoire",
                "Denmark",
                "Djibouti",
                "Dominica",
                "Dominican Republic (the)",
                "Ecuador",
                "Egypt",
                "El Salvador",
                "Equatorial Guinea",
                "Eritrea",
                "Estonia",
                "Eswatini",
                "Ethiopia",
                "Falkland Islands (the) [Malvinas]",
                "Faroe Islands (the)",
                "Fiji",
                "Finland",
                "France",
                "French Guiana",
                "French Polynesia",
                "French Southern Territories (the)",
                "Gabon",
                "Gambia (the)",
                "Georgia",
                "Germany",
                "Ghana",
                "Gibraltar",
                "Greece",
                "Greenland",
                "Grenada",
                "Guadeloupe",
                "Guam",
                "Guatemala",
                "Guernsey",
                "Guinea",
                "Guinea-Bissau",
                "Guyana",
                "Haiti",
                "Heard Island and McDonald Islands",
                "Holy See (the)",
                "Honduras",
                "Hong Kong",
                "Hungary",
                "Iceland",
                "India",
                "Indonesia",
                "Iran (Islamic Republic of)",
                "Iraq",
                "Ireland",
                "Isle of Man",
                "Israel",
                "Italy",
                "Jamaica",
                "Japan",
                "Jersey",
                "Jordan",
                "Kazakhstan",
                "Kenya",
                "Kiribati",
                "Korea (the Democratic People's Republic of)",
                "Korea (the Republic of)",
                "Kuwait",
                "Kyrgyzstan",
                "Lao People's Democratic Republic (the)",
                "Latvia",
                "Lebanon",
                "Lesotho",
                "Liberia",
                "Libya",
                "Liechtenstein",
                "Lithuania",
                "Luxembourg",
                "Macao",
                "Madagascar",
                "Malawi",
                "Malaysia",
                "Maldives",
                "Mali",
                "Malta",
                "Marshall Islands (the)",
                "Martinique",
                "Mauritania",
                "Mauritius",
                "Mayotte",
                "Mexico",
                "Micronesia (Federated States of)",
                "Moldova (the Republic of)",
                "Monaco",
                "Mongolia",
                "Montenegro",
                "Montserrat",
                "Morocco",
                "Mozambique",
                "Myanmar",
                "Namibia",
                "Nauru",
                "Nepal",
                "Netherlands (the)",
                "New Caledonia",
                "New Zealand",
                "Nicaragua",
                "Niger (the)",
                "Nigeria",
                "Niue",
                "Norfolk Island",
                "Northern Mariana Islands (the)",
                "Norway",
                "Oman",
                "Pakistan",
                "Palau",
                "Palestine, State of",
                "Panama",
                "Papua New Guinea",
                "Paraguay",
                "Peru",
                "Philippines (the)",
                "Pitcairn",
                "Poland",
                "Portugal",
                "Puerto Rico",
                "Qatar",
                "Republic of North Macedonia",
                "Romania",
                "Russian Federation (the)",
                "Rwanda",
                "Réunion",
                "Saint Barthélemy",
                "Saint Helena, Ascension and Tristan da Cunha",
                "Saint Kitts and Nevis",
                "Saint Lucia",
                "Saint Martin (French part)",
                "Saint Pierre and Miquelon",
                "Saint Vincent and the Grenadines",
                "Samoa",
                "San Marino",
                "Sao Tome and Principe",
                "Saudi Arabia",
                "Senegal",
                "Serbia",
                "Seychelles",
                "Sierra Leone",
                "Singapore",
                "Sint Maarten (Dutch part)",
                "Slovakia",
                "Slovenia",
                "Solomon Islands",
                "Somalia",
                "South Africa",
                "South Georgia and the South Sandwich Islands",
                "South Sudan",
                "Spain",
                "Sri Lanka",
                "Sudan (the)",
                "Suriname",
                "Svalbard and Jan Mayen",
                "Sweden",
                "Switzerland",
                "Syrian Arab Republic",
                "Taiwan (Province of China)",
                "Tajikistan",
                "Tanzania, United Republic of",
                "Thailand",
                "Timor-Leste",
                "Togo",
                "Tokelau",
                "Tonga",
                "Trinidad and Tobago",
                "Tunisia",
                "Turkey",
                "Turkmenistan",
                "Turks and Caicos Islands (the)",
                "Tuvalu",
                "Uganda",
                "Ukraine",
                "United Arab Emirates (the)",
                "United Kingdom of Great Britain and Northern Ireland (the)",
                "United States Minor Outlying Islands (the)",
                "United States of America (the)",
                "Uruguay",
                "Uzbekistan",
                "Vanuatu",
                "Venezuela (Bolivarian Republic of)",
                "Viet Nam",
                "Virgin Islands (British)",
                "Virgin Islands (U.S.)",
                "Wallis and Futuna",
                "Western Sahara",
                "Yemen",
                "Zambia",
                "Zimbabwe",
                "Åland Islands"
            ];
            var countryList = []

            countryListTemp.forEach(country => {
                countryList.push({ "value": country, "label": country })
            })

            return (
                <div style={{ backgroundColor: "#fff" }}>
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

                    <LoggedHeader user={global.user} cart={global.cart} heightChange={false} height={600} />

                    <div className={classNames(classes.main)} style={{ marginTop: "60px" }}>
                        <div className={classes.container}>
                            <div style={{ padding: "70px 0" }}>
                                <GridContainer>
                                    <GridItem xs={12} sm={12} md={12}>
                                        <div style={{ textAlign: "left" }}>
                                            <GridContainer>
                                                <GridItem xs={12} sm={12} md={12}>
                                                    <span>
                                                        <h2 style={{ color: "#999", fontWeight: "bolder", marginTop: "0px", padding: "0 0" }}>Edit Profile
                                                        </h2>
                                                    </span>
                                                </GridItem>
                                            </GridContainer>
                                            <hr style={{ color: "#999", opacity: "0.4" }}></hr>
                                        </div>
                                    </GridItem>
                                </GridContainer>
                            </div>

                            <div style={{ height: "100%" }}>
                                <GridContainer>
                                    <GridItem xs={12} sm={12} md={12}>
                                        <div>
                                            <GridContainer>
                                                <GridItem xs={12} sm={12} md={12}>
                                                    <CustomInput
                                                        labelText="Name*"
                                                        id="name"
                                                        formControlProps={{
                                                            fullWidth: true
                                                        }}
                                                        inputProps={{
                                                            endAdornment: (
                                                                <InputAdornment position="end">
                                                                    <i className="fas fa-signature" />
                                                                </InputAdornment>
                                                            )
                                                        }}
                                                    />
                                                </GridItem>
                                            </GridContainer>
                                        </div>
                                    </GridItem>

                                    <GridItem xs={12} sm={12} md={12}>
                                        <div>
                                            <GridContainer>
                                                <GridItem xs={12} sm={12} md={12}>
                                                    <CustomInput
                                                        labelText="Email*"
                                                        id="email"
                                                        formControlProps={{
                                                            fullWidth: true
                                                        }}
                                                        inputProps={{
                                                            endAdornment: (
                                                                <InputAdornment position="end">
                                                                    <i class="far fa-envelope" />
                                                                </InputAdornment>
                                                            )
                                                        }}
                                                    />
                                                </GridItem>
                                            </GridContainer>
                                        </div>
                                    </GridItem>

                                    <GridItem xs={12} sm={12} md={12}>
                                        <div>
                                            <GridContainer>
                                                <GridItem xs={12} sm={12} md={12}>
                                                    <CustomInput
                                                        labelText="Description"
                                                        id="description"
                                                        formControlProps={{
                                                            fullWidth: true
                                                        }}
                                                        inputProps={{
                                                            multiline: true,
                                                            endAdornment: (
                                                                <InputAdornment position="end">
                                                                    <i class="fas fa-align-justify" />
                                                                </InputAdornment>
                                                            )
                                                        }}
                                                    />
                                                </GridItem>
                                            </GridContainer>
                                        </div>
                                    </GridItem>

                                    <GridItem xs={12} sm={12} md={12}>
                                        <div>
                                            <GridContainer>
                                                <GridItem xs={12} sm={12} md={6}>
                                                    <CustomInput
                                                        labelText="Password*"
                                                        id="pass"
                                                        formControlProps={{
                                                            fullWidth: true
                                                        }}
                                                        inputProps={{
                                                            type: "password",
                                                            endAdornment: (
                                                                <InputAdornment position="end">
                                                                    <i class="fas fa-lock" />
                                                                </InputAdornment>
                                                            )
                                                        }}
                                                    />
                                                </GridItem>
                                                <GridItem xs={12} sm={12} md={6}>
                                                    <CustomInput
                                                        labelText="Confirm Password*"
                                                        id="passConfirm"
                                                        formControlProps={{
                                                            fullWidth: true
                                                        }}
                                                        inputProps={{
                                                            type: "password",
                                                            endAdornment: (
                                                                <InputAdornment position="end">
                                                                    <i class="fas fa-lock" />
                                                                </InputAdornment>
                                                            )
                                                        }}
                                                    />
                                                </GridItem>
                                            </GridContainer>
                                        </div>
                                    </GridItem>


                                    <GridItem xs={12} sm={12} md={12}>
                                        <div style={{ color: "black", marginTop: "25px" }}>
                                            <GridContainer>
                                                <GridItem xs={12} sm={12} md={12}>
                                                    <InputLabel className={classes.label}>
                                                        Country*
                                                    </InputLabel>
                                                    <br />
                                                    <Select
                                                        className="basic-single"
                                                        classNamePrefix="select"
                                                        name="color"
                                                        id="country"
                                                        placeholder="Country*"
                                                        defaultValue={null}
                                                        options={countryList}
                                                    />
                                                </GridItem>
                                            </GridContainer>
                                        </div>
                                    </GridItem>

                                    <GridItem xs={12} sm={12} md={12}>
                                        <div style={{ color: "black", marginTop: "45px" }}>
                                            <GridContainer>
                                                <GridItem xs={12} sm={12} md={12}>
                                                    <InputLabel className={classes.label}>
                                                        Birthday*
                                                    </InputLabel>
                                                    <br />
                                                    <FormControl fullWidth>
                                                        <Datetime
                                                            timeFormat={false}
                                                            inputProps={{ placeholder: "Birthday*", id: "birthday" }}
                                                        />
                                                    </FormControl>
                                                </GridItem>
                                            </GridContainer>
                                        </div>
                                    </GridItem>

                                    <GridItem xs={12} sm={12} md={12}>
                                        <div style={{ marginTop: "45px" }}>
                                            <GridContainer>
                                                <GridItem xs={12} sm={12} md={4}>
                                                    <CustomInput
                                                        labelText="Credit Card Number"
                                                        id="cardNumber"
                                                        formControlProps={{
                                                            fullWidth: true
                                                        }}
                                                        inputProps={{
                                                            endAdornment: (
                                                                <InputAdornment position="end">
                                                                    <i class="fas fa-credit-card" />
                                                                </InputAdornment>
                                                            )
                                                        }}
                                                    />
                                                </GridItem>
                                                <GridItem xs={12} sm={12} md={4}>
                                                    <CustomInput
                                                        labelText="Credit Card Name"
                                                        id="cardName"
                                                        formControlProps={{
                                                            fullWidth: true
                                                        }}
                                                        inputProps={{
                                                            endAdornment: (
                                                                <InputAdornment position="end">
                                                                    <i class="fas fa-user" />
                                                                </InputAdornment>
                                                            )
                                                        }}
                                                    />
                                                </GridItem>
                                                <GridItem xs={12} sm={12} md={2}>
                                                    <CustomInput
                                                        labelText="CVC"
                                                        id="cardCVC"
                                                        formControlProps={{
                                                            fullWidth: true
                                                        }}
                                                        inputProps={{
                                                        }}
                                                    />
                                                </GridItem>
                                                <GridItem xs={12} sm={12} md={2}>
                                                    <div style={{ color: "black" }}>
                                                        <GridContainer>
                                                            <GridItem xs={12} sm={12} md={12}>
                                                                <InputLabel className={classes.label}>
                                                                    Expiration Date
                                                                </InputLabel>
                                                                <br />
                                                                <FormControl fullWidth>
                                                                    <Datetime
                                                                        timeFormat={false}
                                                                        inputProps={{ placeholder: "Expiration Date", id: "cardExpiration" }}
                                                                    />
                                                                </FormControl>
                                                            </GridItem>
                                                        </GridContainer>
                                                    </div>
                                                </GridItem>
                                            </GridContainer>
                                        </div>
                                    </GridItem>

                                    <GridItem xs={12} sm={12} md={12}>
                                        <div style={{ marginTop: "45px", float: "right" }}>
                                            <Button
                                                color="success"
                                                size="md"
                                                style={{ backgroundColor: "#ff3ea0" }}
                                                target="_blank"
                                                rel="noopener noreferrer"
                                                onClick={() => this.confirm()}
                                            >
                                                <i class="fas fa-pencil-alt"></i> Confirm Edit
                                            </Button>
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
}

export default withStyles(styles)(ProfilePage);