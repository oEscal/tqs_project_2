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

// Router
import {
  Link
} from "react-router-dom";

const useStyles = makeStyles(styles);

export default function SectionNavbars(props) {
  const classes = useStyles();

  const { name, cart, wallet, heightChange } = props;

  var cartIcon
  if (cart) {
    cartIcon = <i class="fas fa-cart-arrow-down" style={{ color: "#fd24ac" }}></i>
  } else {
    cartIcon = <i class="fas fa-shopping-cart"></i>
  }

  var change = {}
  var color = "transparent"
  if (heightChange) {
    change = {
      height: 400,
      color: "dark",
    }
  } else {
    color = "dark"
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
              <Link to="/games">
                <Button
                  className={classes.navLink}
                  color="transparent"
                  style={{ color: "white" }}
                >
                  <i class="fas fa-gamepad"></i>
                  Games
                </Button>
              </Link>

            </ListItem>
            <ListItem className={classes.listItem}>
              <Link to="/sell-game">
                <Button
                  className={classes.navLink}
                  color="transparent"
                  style={{ color: "white" }}
                >
                  <i class="fas fa-money-bill-wave"></i>
                Sell
              </Button>
              </Link>
            </ListItem>
            <ListItem className={classes.listItem}>
              <Link to="/wishlist">
                <Button
                  className={classes.navLink}
                  color="transparent"
                  style={{ color: "white" }}
                >
                  <i class="fas fa-star"></i>
                Wishlist
              </Button>
              </Link>
            </ListItem>
            <ListItem className={classes.listItem}>
              <Link to="/cart">
                <Button
                  className={classes.navLink}
                  color="transparent"
                  style={{ color: "white" }}
                >
                  {cartIcon}
                </Button>
              </Link>
            </ListItem>

            <ListItem className={classes.listItem}>
              <Link to="/wallet">
                <Button
                  className={classes.navLink}
                  color="transparent"
                  style={{ color: "white" }}
                >
                  <i class="fas fa-wallet"></i>
                  {wallet} €
              </Button>
              </Link>
            </ListItem>

            <ListItem className={classes.listItem}>
              <Link to="/profile">
                <Button
                  className={classes.navLink}
                  color="transparent"
                  style={{ color: "white" }}
                >
                  <i class="fas fa-user-circle"></i>
                  {name}
                </Button>
              </Link>
            </ListItem>
          </List>
        }
      />
    </div>
  );
}
