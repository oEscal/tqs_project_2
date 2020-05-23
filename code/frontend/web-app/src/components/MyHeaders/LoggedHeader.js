import React from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import Icon from "@material-ui/core/Icon";
// @material-ui/icons
import Search from "@material-ui/icons/Search";
import Email from "@material-ui/icons/Email";
import Face from "@material-ui/icons/Face";
import AccountCircle from "@material-ui/icons/AccountCircle";
import Explore from "@material-ui/icons/Explore";
// core components
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import Header from "components/Header/Header.js";
import CustomInput from "components/CustomInput/CustomInput.js";
import CustomDropdown from "components/CustomDropdown/CustomDropdown.js";
import Button from "components/CustomButtons/Button.js";

import image from "assets/img/favicon.png";
import profileImage from "assets/img/faces/avatar.jpg";


import styles from "assets/jss/material-kit-react/views/componentsSections/navbarsStyle.js";

// Global Variables
import baseURL from '../../variables/baseURL'
import global from "../../variables/global";

// Router
import {
  Link
} from "react-router-dom";

const useStyles = makeStyles(styles);

export default function SectionNavbars(props) {
  const classes = useStyles();

  const { user, cart, heightChange, height } = props;

  var cartIcon
  if (cart == []) {
    cartIcon = <i class="fas fa-shopping-cart"></i>
  } else {
    cartIcon = <i class="fas fa-cart-arrow-down" style={{ color: "#fd24ac" }}></i>
  }


  var change = {}
  var color = "transparent"
  if (heightChange) {
    change = {
      height: height,
      color: "dark",
    }
  } else {
    color = "dark"
  }

  const logout = () => {
    fetch(baseURL + "grid/logout", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: global.user.token
      }
    })
      .then(response => {
        if (response.status === 200) {
          return response
        }
        else throw new Error(response.status);
      })
      .then(data => {

      })
      .catch(error => {
        console.log(error)

      });
    localStorage.setItem('loggedUser', null);
    global.user = JSON.parse(localStorage.getItem('loggedUser'))
  }

  var content = null
  if (user != null) {
    content = [
      <ListItem className={classes.listItem}>
        <Link to="/sell-game" style={{ color: "inherit" }}>
          <Button
            className={classes.navLink}
            color="transparent"
          >
            <i class="fas fa-money-bill-wave"></i>
                Sell
              </Button>
        </Link>
      </ListItem>,

      <ListItem className={classes.listItem}>
        <Link to="/wishlist" style={{ color: "inherit" }}>
          <Button
            className={classes.navLink}
            color="transparent"
          >
            <i class="fas fa-star"></i>
                Wishlist
              </Button>
        </Link>
      </ListItem>,

      <ListItem className={classes.listItem}>
        <Link to="/cart" style={{ color: "inherit" }}>
          <Button
            className={classes.navLink}
            color="transparent"
          >
            {cartIcon}
          </Button>
        </Link>
      </ListItem>,

      <ListItem className={classes.listItem}>
        <Link to="/wallet" style={{ color: "inherit" }}>
          <Button
            className={classes.navLink}
            color="transparent"
          >
            <i class="fas fa-wallet"></i>
            {user.wallet} €
              </Button>
        </Link>
      </ListItem>,

      <ListItem className={classes.listItem}>
        <CustomDropdown
          noLiPadding
          buttonText={user.username}
          buttonProps={{
            className: classes.navLink,
            color: "transparent"
          }}
          id="logged"
          dropdownList={[
            <Link to={"/user/" + user.username} style={{ color: "inherit" }} className={classes.dropdownLink}>
              <i class="far fa-address-card"></i> My Profile
                  </Link>,
            <Link to="/login-page" className={classes.dropdownLink} onClick={logout}>
              <i class="fas fa-sign-out-alt"></i> Logout
            </Link>,
          ]}
        />
      </ListItem>
    ]
  } else {
    content = [
      <ListItem className={classes.listItem}>
        <Link to="/cart" style={{ color: "inherit" }}>
          <Button
            className={classes.navLink}
            color="transparent"
          >
            {cartIcon}
          </Button>
        </Link>
      </ListItem>,

      <ListItem className={classes.listItem}>
        <Link to="/login-page" style={{ color: "inherit" }}>
          <Button
            className={classes.navLink}
            color="transparent"
          >
            <i class="fas fa-sign-in-alt"></i>
            Login
          </Button>
        </Link>
      </ListItem>,
      <ListItem className={classes.listItem}>

        <Link to="/signup-page" style={{ color: "inherit" }}>
          <Button
            className={classes.navLink}
            color="transparent"
          >
            Sign up
        </Button>
        </Link>
      </ListItem>,
    ]
  }

  return (
    <div id="navbar" className={classes.navbar}>
      <Header
        brand={<Link to="/"><img src={image}></img></Link>}
        fixed

        changeColorOnScroll={change}
        color={color}
        rightLinks={
          <List className={classes.list}>
            <ListItem className={classes.listItem}>
              <Link to="/games" style={{ color: "inherit" }}>
                <Button
                  className={classes.navLink}
                  color="transparent"
                >
                  <i class="fas fa-gamepad"></i>
                  Games
                </Button>
              </Link>

            </ListItem>

            {content}

          </List>
        }
      />
    </div>
  );
}