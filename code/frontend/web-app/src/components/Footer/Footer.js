/*eslint-disable*/
import React from "react";
// nodejs library to set properties for components
import PropTypes from "prop-types";
// nodejs library that concatenates classes
import classNames from "classnames";
// material-ui core components
import { List, ListItem } from "@material-ui/core";
import { makeStyles } from "@material-ui/core/styles";

// @material-ui/icons
import Favorite from "@material-ui/icons/Favorite";

import styles from "assets/jss/material-kit-react/components/footerStyle.js";

const useStyles = makeStyles(styles);

export default function Footer(props) {
  const classes = useStyles();
  const { whiteFont, rawg } = props;
  const footerClasses = classNames({
    [classes.footer]: true,
    [classes.footerWhiteFont]: whiteFont
  });
  const aClasses = classNames({
    [classes.a]: true,
    [classes.footerWhiteFont]: whiteFont
  });

  var rawgDiv = null
  if (rawg) {
    rawgDiv = <div className={classes.middle} style={{fontSize:"12px"}}>
      Game informations and images taken from <a href="https://rawg.io/">Rawg API</a>
    </div>
  }
  return (
    <footer className={footerClasses}>
      <div className={classes.container}>
        <div className={classes.middle}>
          &copy; {1900 + new Date().getYear()} , made with{" "}
          <i class="far fa-sad-tear" style={{marginLeft:"1px", marginRight:"1px"}}></i> by a bunch of nerds for TQS
        </div>
        {rawgDiv}
      </div>
    </footer>
  );
}

Footer.propTypes = {
  whiteFont: PropTypes.bool,
  rawg: PropTypes.bool
};
