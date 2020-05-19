import React, { Component } from 'react';
import classNames from "classnames";
import styles from "assets/jss/material-kit-react/views/landingPage.js";
import 'assets/css/hide.css'
import { withStyles } from '@material-ui/styles';
import CardMedia from '@material-ui/core/CardMedia';
import CardContent from '@material-ui/core/CardContent';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import LoggedHeader from "components/MyHeaders/LoggedHeader.js"
import image1 from "assets/img/bg.jpg";
import image4 from "assets/img/NFS-Heat.jpg";
import CardHeader from "@material-ui/core/CardHeader";
import Avatar from "@material-ui/core/Avatar";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from '@material-ui/icons/Close';
import InputAdornment from "@material-ui/core/InputAdornment";
import CustomInput from "components/CustomInput/CustomInput";
import VideogameAssetIcon from '@material-ui/icons/VideogameAsset';
import { StickyContainer, Sticky } from "components/Sticky/index.js";
import Button from "components/CustomButtons/Button.js";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import Dialog from "@material-ui/core/Dialog";
import Slide from "@material-ui/core/Slide";

import Close from "@material-ui/icons/Close";
import stylesjs from "assets/jss/material-kit-react/views/componentsSections/javascriptStyles.js";
import "./cart.css";

// Global Variables
import baseURL from '../../variables/baseURL'
import global from "../../variables/global";

class Cart extends Component {
    constructor(props) {
        super(props);

        this.state = {
            price: 5.99,
            quantity: 1,
            classicModal: false
        };
    }


    updateQuantity = (e) => {
        this.setState({
            quantity: e.target.value
        });
    };


    getPrice = () => {
        let value = (this.state.price * this.state.quantity).toFixed(2);
        return value > 0 ? value : 0;
    };

    setClassicModal = (v) => {
        this.setState({
            classicModal: v
        });
    };

    transition = React.forwardRef(function Transition(props, ref) {
        return <Slide direction="down" ref={ref} {...props} />;
    });


    renderModal() {
        const useStyles = withStyles(stylesjs);
        const classes = useStyles(Cart);

        var items = [];


        var text = "";
        var image = image4;


        return (
            <Dialog
                classes={{
                    root: classes.center,
                    paper: classes.modal
                }}
                open={this.state.classicModal}
                TransitionComponent={this.transition}
                keepMounted
                onClose={() => this.setClassicModal(false)}
                aria-labelledby="classic-modal-slide-title"
                aria-describedby="classic-modal-slide-description"
            >
                <DialogTitle
                    id="classic-modal-slide-title"
                    disableTypography
                    className={classes.modalHeader}
                >
                    <IconButton
                        className={classes.modalCloseButton}
                        key="close"
                        aria-label="Close"
                        color="inherit"
                        onClick={() => this.setClassicModal(false)}
                        style={{ "float": "right" }}
                    >
                        <Close className={classes.modalClose} />
                    </IconButton>
                    <h3 style={{ color: 'black' }} className={classes.modalTitle}>Price: xxxx</h3>
                </DialogTitle>

                <DialogContent
                    id="classic-modal-slide-description"
                    className={classes.modalBody}
                >
                    <hr style={{ color: "#999", opacity: "0.4" }}></hr>

                    <GridContainer xs={12} sm={12} md={12}>
                        <GridItem xs={4} sm={4} md={4}>
                            <a
                                style={
                                    {
                                        "background-color": "#fff",
                                        "border": "1px solid #d4d4d4",
                                        "border-radius": "3px",
                                        cursor: "pointer",
                                        display: "inline-block",
                                        height: "145px",
                                        margin: "0 11px 20px",
                                        opacity: "1",
                                        padding: "20px 8px",
                                        "text-align": "center",
                                        transition: "opacity .2s linear",
                                        "vertical-align": "top",
                                        width: "145px",
                                    }
                                }

                            >

                                <div class=".box" style={{
                                    height: "50px",
                                    margin: "12px auto 10px",
                                    position: "relative",
                                    width: "95px",
                                }}>
                                    <img
                                        style={{
                                            bottom: "0",
                                            height: "auto",
                                            left: "0",
                                            margin: "auto",
                                            "max-height": "100%",
                                            "max-width": "100%",
                                            right: "0",
                                            top: "0",
                                            width: "auto"
                                        }}

                                        src="https://checkout.pay.g2a.com/03274.png" alt="Paypal" />
                                </div>
                                <h4 style={{
                                    color: "#444",
                                    display: "block",
                                    "font-weight": "500",
                                    "line-height": "1em",
                                }} className="title">PayPal</h4>

                            </a>

                        </GridItem>
                        <GridItem xs={4} sm={4} md={4}>
                            <a
                                style={
                                    {
                                        "background-color": "#fff",
                                        "border": "1px solid #d4d4d4",
                                        "border-radius": "3px",
                                        cursor: "pointer",
                                        display: "inline-block",
                                        height: "145px",
                                        margin: "0 11px 20px",
                                        opacity: "1",
                                        padding: "20px 8px",
                                        "text-align": "center",
                                        transition: "opacity .2s linear",
                                        "vertical-align": "top",
                                        width: "145px",
                                    }
                                }

                            >

                                <div class=".box" style={{
                                    height: "50px",
                                    margin: "12px auto 10px",
                                    position: "relative",
                                    width: "95px",
                                }}>
                                    <img
                                        style={{
                                            bottom: "0",
                                            height: "auto",
                                            left: "0",
                                            margin: "auto",
                                            "max-height": "100%",
                                            "max-width": "100%",
                                            right: "0",
                                            top: "0",
                                            width: "auto"
                                        }}

                                        src="https://checkout.pay.g2a.com/03274.png" alt="Paypal" />
                                </div>
                                <h4 style={{
                                    color: "#444",
                                    display: "block",
                                    "font-weight": "500",
                                    "line-height": "1em",
                                }} className="title">PayPal</h4>

                            </a>

                        </GridItem>
                        <GridItem xs={4} sm={4} md={4}>
                            <a
                                style={
                                    {
                                        "background-color": "#fff",
                                        "border": "1px solid #d4d4d4",
                                        "border-radius": "3px",
                                        cursor: "pointer",
                                        display: "inline-block",
                                        height: "145px",
                                        margin: "0 11px 20px",
                                        opacity: "1",
                                        padding: "20px 8px",
                                        "text-align": "center",
                                        transition: "opacity .2s linear",
                                        "vertical-align": "top",
                                        width: "145px",
                                    }
                                }

                            >

                                <div class=".box" style={{
                                    height: "50px",
                                    margin: "12px auto 10px",
                                    position: "relative",
                                    width: "95px",
                                }}>
                                    <img
                                        style={{
                                            bottom: "0",
                                            height: "auto",
                                            left: "0",
                                            margin: "auto",
                                            "max-height": "100%",
                                            "max-width": "100%",
                                            right: "0",
                                            top: "0",
                                            width: "auto"
                                        }}

                                        src="https://checkout.pay.g2a.com/03274.png" alt="Paypal" />
                                </div>
                                <h4 style={{
                                    color: "#444",
                                    display: "block",
                                    "font-weight": "500",
                                    "line-height": "1em",
                                }} className="title">PayPal</h4>

                            </a>

                        </GridItem>
                        <GridItem xs={4} sm={4} md={4}>
                            <a
                                style={
                                    {
                                        "background-color": "#fff",
                                        "border": "1px solid #d4d4d4",
                                        "border-radius": "3px",
                                        cursor: "pointer",
                                        display: "inline-block",
                                        height: "145px",
                                        margin: "0 11px 20px",
                                        opacity: "1",
                                        padding: "20px 8px",
                                        "text-align": "center",
                                        transition: "opacity .2s linear",
                                        "vertical-align": "top",
                                        width: "145px",
                                    }
                                }

                            >

                                <div class=".box" style={{
                                    height: "50px",
                                    margin: "12px auto 10px",
                                    position: "relative",
                                    width: "95px",
                                }}>
                                    <img
                                        style={{
                                            bottom: "0",
                                            height: "auto",
                                            left: "0",
                                            margin: "auto",
                                            "max-height": "100%",
                                            "max-width": "100%",
                                            right: "0",
                                            top: "0",
                                            width: "auto"
                                        }}

                                        src="https://checkout.pay.g2a.com/03274.png" alt="Paypal" />
                                </div>
                                <h4 style={{
                                    color: "#444",
                                    display: "block",
                                    "font-weight": "500",
                                    "line-height": "1em",
                                }} className="title">PayPal</h4>

                            </a>

                        </GridItem>



                    </GridContainer>


                </DialogContent>
                <DialogActions className={classes.modalFooter}>
                    <Button color="transparent" simple>
                        Pay
                    </Button>
                </DialogActions>
            </Dialog>

        )
    }

    render() {
        const { classes } = this.props;

        var items = [];
        for (var i = 0; i < 6; i++) {
            var style = { margin: "12px 0", float: 'left' };
            var text = "";
            var image = null;

            if (i == 0) {
                style = {}
            }
            if (i % 2 == 0) {
                text = "NHS: Heat"
                image = image4
            } else {
                text = "No Man's Sky: Beyond"
                image = image1
            }

            items.push(
                <GridItem xs={12} sm={12} md={12} style={style}>
                    <Card style={{ width: "100%" }}>
                        <CardHeader
                            title={
                                <h6 style={{ color: "#999", fontSize: "11px", paddingTop: "0 0", marginTop: "0px" }}>
                                    From seller <span style={{ color: 'black', fontWeight: "bold" }}>Jonas Pistolas</span>
                                </h6>
                            }
                            avatar={
                                <Avatar aria-label="recipe" className={classes.avatar}>
                                    R
                                </Avatar>
                            }
                            action={
                                <IconButton aria-label="settings">
                                    <CloseIcon />
                                </IconButton>
                            }
                        >
                        </CardHeader>
                        <CardActionArea>
                            <CardMedia
                                component="img"
                                height="185px"
                                image={image}
                            />
                            <CardContent>
                                <div style={{ textAlign: "left", height: "30px" }}>
                                    <h6 style={{
                                        fontWeight: "bold",
                                        color: "#3b3e48",
                                        fontSize: "15px",
                                        paddingTop: "0 0",
                                        marginTop: "0px"
                                    }}>
                                        {text}
                                    </h6>
                                </div>
                                <div style={{ textAlign: "left" }}>

                                    <CustomInput

                                        labelText="Quantity"
                                        id="quantity"
                                        formControlProps={{}}
                                        inputProps={{
                                            type: "number",
                                            value: this.state.quantity,
                                            precision: 2,
                                            min: 0,
                                            endAdornment: (
                                                <InputAdornment position="end">
                                                    <VideogameAssetIcon className={classes.inputIconsColor} />
                                                </InputAdornment>
                                            ),
                                            onChange: this.updateQuantity,
                                        }}
                                    />


                                </div>
                                <div style={{ textAlign: "left" }}>
                                    <h6 style={{ color: "#999", fontSize: "11px", paddingTop: "0 0", marginTop: "0px" }}>
                                        Delivery: <span style={{ fontWeight: "bold" }}>Instant access</span>
                                    </h6>
                                </div>
                                <div style={{ textAlign: "left" }}>
                                    <h6 style={{
                                        color: "#3b3e48",
                                        fontSize: "15px",
                                        paddingTop: "0 0",
                                        marginTop: "0px"
                                    }}>
                                        Price <span
                                            style={{
                                                fontWeight: "bolder",
                                                color: "#f44336",
                                                fontSize: "17px"
                                            }}> {this.getPrice()} €</span>
                                    </h6>
                                </div>
                            </CardContent>
                        </CardActionArea>
                    </Card>
                </GridItem>
            )
        }
        return (
            <div>
                <LoggedHeader user={global.user} cart={global.cart} heightChange={false} height={600} />

                <div className={classNames(classes.main)} style={{ marginTop: "60px" }}>

                    <div className={classes.container}>
                        <div style={{ padding: "70px 0" }}>
                            <GridContainer>
                                <GridItem xs={12} sm={12} md={12}>
                                    <div style={{ textAlign: "left" }}>
                                        <GridContainer>
                                            <GridItem xs={12} sm={12} md={9}>
                                                <span>
                                                    <h2 style={{
                                                        color: "#999",
                                                        fontWeight: "bolder",
                                                        marginTop: "0px",
                                                        padding: "0 0"
                                                    }}>Cart <span
                                                        style={{ color: "#999", fontSize: "15px", fontWeight: "normal" }}>(xxxxx products)</span>
                                                    </h2>
                                                </span>
                                            </GridItem>

                                        </GridContainer>

                                        <hr style={{ color: "#999", opacity: "0.4" }}></hr>
                                    </div>
                                </GridItem>
                            </GridContainer>
                        </div>

                        <StickyContainer
                            style={{ padding: "5px 0", "vertical-align": "top", display: "flex", height: "100%" }}>
                            <GridContainer xs={12} sm={12} md={8} style={{ flex: "1" }}>
                                {items}

                            </GridContainer>
                            <GridContainer xs={12} sm={12} md={4}
                                style={{ flex: "1", float: "right", "padding-left": "30px" }}>
                                <GridItem xs={12} sm={12} md={12}>
                                    <Sticky>
                                        {({ style }) => (
                                            <Card>
                                                <CardHeader
                                                    title="Total Price: xxxx"
                                                    subheader="Items: xxxx"
                                                >

                                                </CardHeader>
                                                <CardContent>
                                                    <div style={{ "textAlign": "center" }}>
                                                        <Button color="primary" round
                                                            onClick={() => this.setClassicModal(true)}>Continue to
                                                            payment</Button>
                                                    </div>
                                                </CardContent>
                                            </Card>)}
                                    </Sticky>
                                </GridItem>

                            </GridContainer>
                        </StickyContainer>
                    </div>
                </div>
                {this.renderModal()}
            </div>
        )
    }
}

export default withStyles(styles)(Cart);