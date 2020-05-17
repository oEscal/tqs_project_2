import React, { Component } from 'react';

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

import {
  Link
} from "react-router-dom";

class LoginPage extends Component {
  constructor() {
    super();
  }

  state = {
  }

  render() {
    const { classes } = this.props;

    return (
      <div>
        <LoggedHeader name="Jonas Pistolas" cart={true} wallet={0.00} heightChange={true} />

        <div
          className={classes.pageHeader}
          style={{
            backgroundImage: "url(" + image + ")",
            backgroundSize: "cover",
            backgroundPosition: "top center",
          }}
        >
          <div className={classes.container} style={{ minHeight: "900px" }}>
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
                        id="first"
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          type: "text",
                          endAdornment: (
                            <InputAdornment position="end">
                              <People className={classes.inputIconsColor} />
                            </InputAdornment>
                          )
                        }}
                      />
                      <CustomInput
                        labelText="Password"
                        id="pass"
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          type: "password",
                          endAdornment: (
                            <InputAdornment position="end">
                              <Icon className={classes.inputIconsColor}>
                                lock_outline
                            </Icon>
                            </InputAdornment>
                          ),
                          autoComplete: "off"
                        }}
                      />
                    </CardBody>
                    <CardFooter className={classes.cardFooter}>
                      <GridContainer xs={12} sm={12} md={12}>
                        <GridItem xs={12} sm={12} md={12}>
                          <Button
                            color="rose"
                            size="md"
                            href="https://www.youtube.com/watch?v=dQw4w9WgXcQ&ref=creativetim"
                            target="_blank"
                            rel="noopener noreferrer"
                            style={{ width: "100%", backgroundColor:"#fc3196" }}
                          >
                            Login
                          </Button>
                        </GridItem>

                        <GridItem xs={12} sm={12} md={12} style={{marginTop:"20px"}}>
                          <span >
                            Don't have an account? <Link style={{color:"#fc3196"}}><b>Join the Grid!</b></Link>
                          </span>
                        </GridItem>
                      </GridContainer>
                    </CardFooter>
                  </form>
                </Card>
              </GridItem>
            </GridContainer>
          </div>
          <div>
            <Footer whiteFont />
          </div>
        </div>
      </div>
    )
  }
}

export default withStyles(styles)(LoginPage);