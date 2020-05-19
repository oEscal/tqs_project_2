import React, {Component} from 'react';
import {withStyles} from "@material-ui/styles";
import styles from "assets/jss/material-kit-react/views/landingPage.js";
import classNames from "classnames";
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import LoggedHeader from "components/MyHeaders/LoggedHeader.js"
import CustomInput from "components/CustomInput/CustomInput";
import InputAdornment from "@material-ui/core/InputAdornment";
import SearchIcon from '@material-ui/icons/Search';
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import Avatar from "@material-ui/core/Avatar";
import IconButton from "@material-ui/core/IconButton";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardMedia from "@material-ui/core/CardMedia";
import CardContent from "@material-ui/core/CardContent";
import image1 from "assets/img/bg.jpg";
import image4 from "assets/img/NFS-Heat.jpg";
import FavoriteBorderIcon from '@material-ui/icons/FavoriteBorder';
import FavoriteIcon from '@material-ui/icons/Favorite';
import Button from "components/CustomButtons/Button.js";
import logoImage from "assets/img/favicon.png";
import Popover from "@material-ui/core/Popover";

import javascriptStyles from "assets/jss/material-kit-react/views/componentsSections/javascriptStyles.js";


class WishList extends Component {
    constructor(props) {

        super(props);

        this.state = {
            data: [],
            cacheData: [],
            size: 6,
            anchorElLeft: null,
            currentGame: null,
        };


    }


    renderFavoriteIcon = (status, index) => {
        let html = (
            <div style={{color: "#9c27b0"}}>
                <FavoriteIcon/>
            </div>
        );

        if (!status) {
            html = (
                <div>
                    <FavoriteBorderIcon/>
                </div>
            )
            const {data, size} = this.state;

            data.splice(index, 1);
            this.setState({
                data: data,
                size: size - 1
            });
        }

        return html;
    };


    confirmRemoval = (e) => {

        if (this.state.currentGame === null)
            return;

        const id = this.state.currentGame.id;

        let {data} = this.state;
        let status = data[id].favorite;
        data[id].favorite = !status;

        this.setState({
            data: data,
            currentGame: null
        });

        this.setAnchorElLeft(null);

    }


    componentDidMount() {
        this.loadItems();
    }

    loadItems = () => {
        let cacheData = JSON.parse(localStorage.getItem("wishlist"));
        let data = cacheData === null ? [] : cacheData;
        let names = [
            "GTA",
            "FIFA",
            "PES",
            "CSGO",
            "VALORANT",
            "PUBG",
            "FORTNITE"
        ];
        if (data.length === 0) {
            for (let i = 0; i < this.state.size; i++) {
                let txt = '';
                let img = null;

                if (i % 2 == 0) {
                    txt = "NHS: Heat"
                    img = image4
                } else {
                    txt = "No Man's Sky: Beyond"
                    img = image1
                }


                data.push({
                    text: names[i],
                    image: img,
                    favorite: true,
                    price: i
                })
            }

            localStorage.setItem("wishlist", JSON.stringify(data));
        }

        this.setState({
            data: data,
            cacheData: data
        });

    }

    setAnchorElLeft = (v) => {

        this.setState({
            anchorElLeft: v,
            currentGame: v === null ? v : {id: parseInt(v.id.replace('icon', '')), text: v.name}
        });


    }


    filterData = (e) => {
        const userInput = e.target.value.toLowerCase();


        const data = this.state.cacheData;

        let newData = [];
        for (let i = 0; i < data.length; i++) {
            if (data[i].text.toLowerCase().startsWith(userInput))
                newData.push(data[i])
        }

        this.setState({
            data: newData
        });
    };

    renderNoResults = () => {
        return (
            <GridItem xs={12} sm={12} md={12} style={{margin: "12px 0"}} id="cardEmpty">
                <div style={{"text-align": "center"}}>
                    <FavoriteBorderIcon fontSize="large"/>
                    <div
                        style={{textAlign: "center", height: "30px"}}>
                        <h6 style={{
                            fontWeight: "bold",
                            color: "#3b3e48",
                            fontSize: "15px",
                            paddingTop: "0 0",
                            marginTop: "0px"
                        }}>
                            0 results
                        </h6>
                    </div>
                    <div style={{textAlign: "center"}}>
                        <Button color="primary" size="sm" round onClick={() => {
                            window.location.href = "/"
                        }}>
                            Explore &nbsp;<img src={logoImage}/>
                        </Button>

                    </div>
                </div>


            </GridItem>

        )
    }

    render() {
        const {classes} = this.props;


        return (
            <div>
                <LoggedHeader name="Jonas Pistolas" cart={true} wallet={0.00} heightChange={false}/>

                <div className={classNames(classes.main)} style={{marginTop: "60px"}}>

                    <div className={classes.container}>
                        <div style={{padding: "70px 0"}}>
                            <GridContainer>
                                <GridItem xs={12} sm={12} md={12}>
                                    <div style={{textAlign: "left"}}>
                                        <GridContainer>
                                            <GridItem xs={12} sm={12} md={9}>
                                                <h2 style={{
                                                    color: "#999",
                                                    fontWeight: "bolder",
                                                    marginTop: "0px",
                                                    padding: "0 0"
                                                }}>Wishlist</h2>
                                            </GridItem>
                                        </GridContainer>
                                        <hr style={{color: "#999", opacity: "0.4"}}></hr>

                                        <GridContainer justify="center">
                                            <GridItem xs={12} sm={12} md={8}>
                                                <CustomInput
                                                    labelText="Search game..."
                                                    id="Search"
                                                    formControlProps={{
                                                        fullWidth: true
                                                    }}
                                                    inputProps={{
                                                        type: "text",
                                                        endAdornment: (
                                                            <InputAdornment position="end">
                                                                <SearchIcon className={classes.inputIconsColor}/>
                                                            </InputAdornment>
                                                        ),
                                                        onInput: (e) => {
                                                            this.filterData(e)
                                                        },
                                                        autoComplete: "off"
                                                    }}
                                                />
                                            </GridItem>
                                        </GridContainer>

                                        <GridContainer>
                                            {this.state.data.length > 0 && this.state.data.map((v, i) => {
                                                    let text = v.text;
                                                    let image = v.image;
                                                    let status = v.favorite;
                                                    let price = v.price;

                                                    return (
                                                        <>
                                                            <GridItem xs={12} sm={12} md={6} style={{margin: "12px 0"}}
                                                                      id={"card" + i}>
                                                                <Card style={{width: "100%"}}>
                                                                    <CardHeader
                                                                        title={
                                                                            <h6 style={{
                                                                                color: "#999",
                                                                                fontSize: "11px",
                                                                                paddingTop: "0 0",
                                                                                marginTop: "0px"
                                                                            }}>
                                                                                From seller <span
                                                                                style={{
                                                                                    color: 'black',
                                                                                    fontWeight: "bold"
                                                                                }}>Jonas Pistolas</span>
                                                                            </h6>
                                                                        }
                                                                        avatar={
                                                                            <Avatar aria-label="recipe"
                                                                                    className={classes.avatar}>
                                                                                R
                                                                            </Avatar>
                                                                        }
                                                                        action={

                                                                            <IconButton aria-label="settings"
                                                                                        id={"icon" + i}
                                                                                        name={text}
                                                                                        onClick={(e) => {
                                                                                            this.setAnchorElLeft(e.currentTarget)
                                                                                        }}>
                                                                                {this.renderFavoriteIcon(status, i)}


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
                                                                            <div
                                                                                style={{textAlign: "left", height: "30px"}}>
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
                                                                            <div style={{textAlign: "left"}}>
                                                                                <h6 style={{
                                                                                    color: "#999",
                                                                                    fontSize: "11px",
                                                                                    paddingTop: "0 0",
                                                                                    marginTop: "0px"
                                                                                }}>
                                                                                    Delivery: <span
                                                                                    style={{fontWeight: "bold"}}>Instant access</span>
                                                                                </h6>
                                                                            </div>
                                                                            <div style={{textAlign: "left"}}>
                                                                                <h6 style={{
                                                                                    color: "#3b3e48",
                                                                                    fontSize: "15px",
                                                                                    paddingTop: "0 0",
                                                                                    marginTop: "0px"
                                                                                }}>
                                                                                    Price: <span
                                                                                    style={{
                                                                                        fontWeight: "bolder",
                                                                                        color: "#f44336",
                                                                                        fontSize: "17px"
                                                                                    }}> {price} €</span>
                                                                                </h6>
                                                                            </div>
                                                                            <div style={{textAlign: "center"}}>
                                                                                <Button color="primary" size="sm" round>
                                                                                    View on &nbsp;<img src={logoImage}/>
                                                                                </Button>

                                                                            </div>
                                                                        </CardContent>
                                                                    </CardActionArea>
                                                                </Card>

                                                            </GridItem>

                                                        </>


                                                    )
                                                }
                                            )
                                            }
                                            {this.state.data.length == 0 && this.renderNoResults()}

                                        </GridContainer>

                                    </div>
                                </GridItem>
                            </GridContainer>
                        </div>
                    </div>
                    <Popover
                        classes={{
                            paper: classes.popover
                        }}
                        open={Boolean(this.state.anchorElLeft)}
                        anchorEl={this.state.anchorElLeft}
                        onClose={() => this.setAnchorElLeft(null)}
                        anchorOrigin={{
                            vertical: "center",
                            horizontal: "left"
                        }}
                        transformOrigin={{
                            vertical: "center",
                            horizontal: "right"
                        }}
                    >
                        <h3 className={classes.popoverHeader}>Are you sure you want
                            to remove <b>{this.state.currentGame === null ? '' : this.state.currentGame.text}</b> from
                            wishList?
                        </h3>

                        <div className={classes.popoverBody}
                             style={{"text-align": "center"}}>
                            <Button color="success" round size="sm"
                                    onClick={this.confirmRemoval}>Yes</Button>
                            <Button color="danger" round size="sm"
                                    onClick={() => this.setAnchorElLeft(null)}>No</Button>
                        </div>

                    </Popover>

                </div>


            </div>
        )
    }
}


export default withStyles(Object.assign({}, styles, javascriptStyles))(WishList);