import React, { Component } from 'react';

// Global Variables
import baseURL from '../../variables/baseURL'
import global from "../../variables/global";

// @material-ui/core components
import { withStyles } from '@material-ui/styles';
import InputAdornment from "@material-ui/core/InputAdornment";
import Icon from "@material-ui/core/Icon";

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

import LoggedHeader from "components/MyHeaders/LoggedHeader.js"

import styles from "assets/jss/material-kit-react/views/loginPage.js";

import image from "assets/img/bg.png";

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

class LoginPage extends Component {
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

  componentDidMount(){
    //Reset prior info
    localStorage.setItem('loggedUser', null);
    global.user = null;
  }

  // Methods ///////////////////////////////
  async login() {
    // Clear prior errors
    document.getElementById("errorTwo").style.display = "none"
    document.getElementById("errorThree").style.display = "none"

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const login_info = "Basic " + btoa(username + ":" + password);

    console.log(login_info);

    var error = false

    // Check if fields were filled
    if (username == "" || password == "") {
      toast.error('Please fill both fields!', {
        position: "top-center",
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        toastId: "errorOneToast"
      });

      document.getElementById("errorOne").style.display = ""

      error = true
    } else {
      document.getElementById("errorOne").style.display = "none"
    }


    if (!error) {
      await this.setState({
        processing: true
      })

      var success = false

      // Proceed to login
      await fetch(baseURL + "grid/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: login_info
        }
      })
        .then(response => {
          if (response.status === 401 || response.status === 200) {
            return response.json()
          }
          else throw new Error(response.status);
        })
        .then(data => {
          if (data.status === 401) { // Wrong Credentials
            toast.error('Sorry, those credentials seem to be incorrect!', {
              position: "top-center",
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              toastId: "errorTwoToast"
            });

            document.getElementById("errorTwo").style.display = ""

          } else { // Successful Login
            localStorage.setItem('loggedUser', JSON.stringify(data));
            global.user = JSON.parse(localStorage.getItem('loggedUser'))
            success = true
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
            toastId: "errorThreeToast"
          });

          document.getElementById("errorThree").style.display = ""
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
  //////////////////////////////////////////


  render() {
    const { classes } = this.props;

    var processing = null
    //Overlay for when processing a login request
    if (this.state.processing) {
      processing = [
        <div style={{ position: "absolute", top: "0", left: "0", height: "100%", width: "100%", backgroundColor: "black", opacity: 0.6, zIndex: 11 }} id="processing">
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

    return (
      <div>
        <LoggedHeader user={global.user} cart={global.cart} heightChange={true} height={600} />

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
              <GridItem xs={12} sm={12} md={4} >
                <Card>
                  <form className={classes.form}>
                    <CardHeader className={classes.cardHeader} style={{
                      color: "#FFFFFF",
                      WebkitBackgroundClip: "initial",
                      WebkitTextFillColor: "#FFFFFF",
                      background: "rgb(253,27,163)",
                      background: "linear-gradient(0deg, rgba(253,27,163,1) 0%, rgba(251,72,138,1) 24%, rgba(252,137,114,1) 55%, rgba(253,161,104,1) 82%, rgba(254,220,87,1) 100%)",
                    }}>
                      <h3 style={{ fontWeight: "bolder" }}><b>Login</b></h3>
                    </CardHeader>
                    <CardBody>
                      <CustomInput
                        labelText="Username..."
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
                      <CustomInput
                        labelText="Password"
                        id="password"
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

                      <GridItem xs={12} sm={12} md={12} style={{ marginTop: "20px", display: "none" }} id="errorOne">
                        <span style={{ color: "#f50b0a", fontWeight: "bolder" }}>
                          Please fill both fields before submitting!
                          </span>
                      </GridItem>
                      <GridItem xs={12} sm={12} md={12} style={{ marginTop: "20px", display: "none" }} id="errorTwo">
                        <span style={{ color: "#f50b0a", fontWeight: "bolder" }}>
                          Oops, wrong credentials!
                          </span>
                      </GridItem>
                      <GridItem xs={12} sm={12} md={12} style={{ marginTop: "20px", display: "none" }} id="errorThree">
                        <span style={{ color: "#f50b0a", fontWeight: "bolder" }}>
                          Sorry, an unexpected error has occurred, please try again!
                          </span>
                      </GridItem>

                    </CardBody>
                    <CardFooter className={classes.cardFooter}>
                      <GridContainer xs={12} sm={12} md={12}>
                        <GridItem xs={12} sm={12} md={12}>
                          <Button
                            color="rose"
                            size="md"
                            onClick={() => this.login()}
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
                            Don't have an account? <Link to="/signup-page" style={{ color: "#fc3196" }}><b>Join the Grid!</b></Link>
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

export default withStyles(styles)(LoginPage);