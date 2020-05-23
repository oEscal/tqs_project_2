import React, { Component } from 'react';

// Global Variables
import baseURL from '../../variables/baseURL'
import global from "../../variables/global";


// @material-ui/core components
import { withStyles } from '@material-ui/styles';
import InputAdornment from "@material-ui/core/InputAdornment";
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
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import Button from "components/CustomButtons/Button.js";
import Card from "components/Card/Card.js";
import CardBody from "components/Card/CardBody.js";
import CardHeader from "components/Card/CardHeader.js";
import CardFooter from "components/Card/CardFooter.js";
import CustomInput from "components/CustomInput/CustomInput.js";


// Toastify
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast, Flip } from 'react-toastify';


import LoggedHeader from "components/MyHeaders/LoggedHeader.js"

import styles from "assets/jss/material-kit-react/views/loginPage.js";

import image from "assets/img/bg.png";

// React Select
import Select from 'react-select';

import {
  Link,
  Redirect
} from "react-router-dom";

// Loading Animation
import FadeIn from "react-fade-in";
import Lottie from "react-lottie";
import * as loadingAnim from "assets/animations/loading_anim.json";

class Signup extends Component {
  constructor() {
    super();
  }

  state = {
    processing: false,
    animationOptions: {
      loop: true, autoplay: true, animationData: loadingAnim.default, rendererSettings: {
        preserveAspectRatio: "xMidYMid slice"
      }
    },

    redirect: false
  }

  componentDidMount() {

  }

  //METHODS////////////////////////////////////
  validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  }

  async confirm() {
    var name = document.getElementById("name").value
    var username = document.getElementById("username").value
    var country = document.getElementById("country").textContent
    var birthday = document.getElementById("birthday").value

    var email = document.getElementById("email").value


    var pass = document.getElementById("pass").value
    var passConfirm = document.getElementById("passConfirm").value

    var cardNumber = document.getElementById("cardNumber").value
    var cardName = document.getElementById("cardName").value
    var cardCVC = document.getElementById("cardCVC").value
    var expiration = document.getElementById("cardExpiration").value

    var error = false

    if (name == "" || username == "" || birthday == "" || pass == "" || email == "") {
      toast.error('Oops, you\'ve got to specify, at least, a name, username, birthday, email and password!', {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        toastId:"errorMinimum"
      });
      error = true
    }

    var tempBirth = birthday.split("/")
    if (tempBirth.length != 3) {
      toast.error('Please use a valid birthday...', {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        toastId:"errorBirthday"
      });
      error = true
    } else {
      birthday = tempBirth[1] + "/" + tempBirth[0] + "/" + tempBirth[2]
    }

    if (!this.validateEmail(email)) {
      toast.error('Please use a valid email...', {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        toastId:"errorEmail"
      });
      error = true
    }

    email = email.toLowerCase()

    if (pass != passConfirm) {
      toast.error('Oops, your passwords don\'t match!', {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        toastId:"errorPass"
      });
      error = true
    }


    if (cardNumber != '' || cardCVC != '' || expiration != '' || cardName != '') {
      if (cardNumber == '' || cardCVC == '' || expiration == '' || cardName == '') {
        toast.error('Oops, if you want to add your payment info, you\'ve got to specify all four card information fields!', {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
        });
        error = true
      }
    } else {
      cardNumber = null
      cardCVC = null
      expiration = null
      cardName = null
    }

    if (expiration != null) {
      var tempExpiration = expiration.split("/")
      if (tempExpiration.length != 3) {
        toast.error('Please use a valid expiration date...', {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
        });
        error = true
      } else {
        expiration = tempExpiration[1] + "/" + tempExpiration[0] + "/" + tempExpiration[2]
      }
    }

    if (cardCVC != "" && cardCVC != null && (!(/^\d+$/.test(cardCVC)) || cardCVC.length != 3)) {
      toast.error('Oops, the CVC must contain only numbers and have 3 digits!', {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
      });
      error = true
    }

    if (country == "Country*") {
      country = null
    }

    if (!error) {
      await this.setState({
        processing: true
      })

      var success = false

      var body = {
        username: username,
        birthDate: birthday,
        email: email,
        name: name,
        country: country,

        password: pass,
      }

      if (cardNumber != null) {
        body["creditCardNumber"] = cardNumber
        body["creditCardCSC"] = cardCVC
        body["creditCardOwner"] = cardName
        body["creditCardExpirationDate"] = expiration
      }

      // Proceed to login
      await fetch(baseURL + "grid/sign-up", {
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
            const token = "Basic " + btoa(username + ":" + pass);

            data["token"] = token
            localStorage.setItem('loggedUser', JSON.stringify(data));
            global.user = JSON.parse(localStorage.getItem('loggedUser'))
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
        processing: false,
        redirect: success
      })

    }
  }

  renderRedirect = () => {
    if (this.state.redirect) {
      return <Redirect to='/' />
    }
  }

  ////////////////////////////////////////////

  render() {
    const { classes } = this.props;

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

    var processing = null
    //Overlay for when processing a login request
    if (this.state.processing) {
      processing = [
        <div style={{ position: "fixed", top: "0", left: "0", height: "100%", width: "100%", backgroundColor: "black", opacity: 0.6, zIndex: 11 }} id="processing">
        </div>,

        <div style={{ zIndex: 11, position: "absolute", top: "0", left: "0", height: "100%", width: "100%" }}>
          <div style={{ zIndex: 11, position: "fixed", top: "50%", left: "50%", transform: "translate(-50%, -50%)" }}>
            <FadeIn>
              <Lottie options={this.state.animationOptions} width={"200px"} />
            </FadeIn>
          </div>
        </div>
      ]
    }


    var today = Datetime.moment()
    var valid = function (current) {
      return current.isAfter(today);
    };

    return (
      <div>
        <LoggedHeader user={global.user} cart={global.cart} heightChange={true} height={200} />
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

        {processing}
        {this.renderRedirect()}

        <div
          className={classes.pageHeader}
          style={{
            backgroundImage: "url(" + image + ")",
            backgroundSize: "cover",
            backgroundPosition: "top center",
          }}
        >
          <div className={classes.container} style={{ minHeight: "900px", maxHeight: "100%" }}>
            <GridContainer justify="center" style={{ marginTop: "60px" }}>
              <GridItem xs={12} sm={12} md={6} >
                <Card>
                  <form className={classes.form}>
                    <CardHeader className={classes.cardHeader} style={{
                      color: "#FFFFFF",
                      WebkitBackgroundClip: "initial",
                      WebkitTextFillColor: "#FFFFFF",
                      background: "rgb(253,27,163)",
                      background: "linear-gradient(0deg, rgba(253,27,163,1) 0%, rgba(251,72,138,1) 24%, rgba(252,137,114,1) 55%, rgba(253,161,104,1) 82%, rgba(254,220,87,1) 100%)",
                    }}>
                      <h3 style={{ fontWeight: "bolder" }}><b>Sign Up</b></h3>
                    </CardHeader>
                    <CardBody>
                      <CustomInput
                        labelText="Full Name*"
                        id="name"
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
                        labelText="Email*"
                        id="email"
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          type: "text",
                          endAdornment: (
                            <InputAdornment position="end">
                              <i class="fas fa-envelope"></i>
                            </InputAdornment>
                          )
                        }}
                      />

                      <CustomInput
                        labelText="Username*"
                        id="username"
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          type: "text",
                          endAdornment: (
                            <InputAdornment position="end">
                              <i class="far fa-user"></i>
                            </InputAdornment>
                          )
                        }}
                      />

                      <div style={{ marginTop: "20px" }}>
                        <FormControl fullWidth
                        >
                          <Datetime
                            timeFormat={false}
                            inputProps={{ placeholder: "Birthday*", id: "birthday" }}
                          />
                        </FormControl>
                      </div>

                      <div style={{ marginTop: "40px" }}>
                        <Select
                          className="basic-single"
                          classNamePrefix="select"
                          defaultValue={null}
                          id="country"
                          placeholder="Country*"
                          options={countryList}
                        />
                      </div>

                      <hr style={{ opacity: 0.2, color: "#fc3196", marginTop: "22px" }} />

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
                              <i class="fas fa-lock"></i>
                            </InputAdornment>
                          ),
                          autoComplete: "off"
                        }}
                      />
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
                              <i class="fas fa-lock"></i>
                            </InputAdornment>
                          ),
                          autoComplete: "off"
                        }}
                      />

                      <hr style={{ opacity: 0.2, color: "#fc3196", marginTop: "22px" }} />

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

                    </CardBody>
                    <CardFooter className={classes.cardFooter}>
                      <GridContainer xs={12} sm={12} md={12}>
                        <GridItem xs={12} sm={12} md={12}>
                          <Button
                            color="rose"
                            size="md"
                            onClick={() => this.confirm()}
                            target="_blank"
                            rel="noopener noreferrer"
                            style={{ width: "100%", backgroundColor: "#fc3196" }}
                            id="confirm"
                          >
                            Login
                          </Button>
                        </GridItem>

                        <GridItem xs={12} sm={12} md={12} style={{ marginTop: "20px" }}>
                          <span >
                            Already have an account? <Link to="/login-page" style={{ color: "#fc3196" }}><b>Login the Grid!</b></Link>
                          </span>
                        </GridItem>
                      </GridContainer>
                    </CardFooter>
                  </form>
                </Card>
              </GridItem>
            </GridContainer>
          </div>
          <Footer whiteFont />
        </div>
      </div>
    )
  }
}

export default withStyles(styles)(Signup);