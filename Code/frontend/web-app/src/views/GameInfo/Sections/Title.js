import React from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";

// @material-ui/icons
import Chat from "@material-ui/icons/Chat";
import VerifiedUser from "@material-ui/icons/VerifiedUser";
import Fingerprint from "@material-ui/icons/Fingerprint";

// core components
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";

// React Select
import Select from 'react-select';

import styles from "assets/jss/material-kit-react/views/landingPageSections/productStyle.js";

const useStyles = makeStyles(styles);

export default function Title() {
  const classes = useStyles();
  return (
    <div className={classes.section} style={{ marginBottom: "0px" }}>
      <GridContainer justify="center">
        <GridItem xs={12} sm={12} md={12}>
          <div style={{ textAlign: "left" }}>
            <GridContainer>
              <GridItem xs={12} sm={12} md={8}>
                <span>
                  <h2 style={{ color: "#999", fontWeight: "bolder" }}>Games <span style={{ color: "#999", fontSize: "15px", fontWeight: "normal" }}>(xxxxx products)</span>
                  </h2>
                </span>
              </GridItem>
              <GridItem xs={12} sm={12} md={4}>
                <Select
                  className="basic-single"
                  classNamePrefix="select"
                  name="color"
                  defaultValue={{ "value": "DATE", "label": "Newer First" }}
                  options={[{ "value": "DATE", "label": "Newer First" }, { "value": "REVERSE_DATE", "label": "Older First" }, { "value": "ALPHABETICAL", "label": "Alphabetical" }, { "value": "PRICE", "label": "Lowest Price to Highest" }, { "value": "PRICE_REVERSE", "label": "Highest Price to Lowest" }]}
                />
              </GridItem>
            </GridContainer>

            <hr style={{ color: "#999", opacity: "0.4" }}></hr>
          </div>
        </GridItem>
      </GridContainer>
    </div>
  );
}
