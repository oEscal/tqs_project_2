import React, {Component} from "react";
import {withStyles} from "@material-ui/styles";
import styles from "assets/jss/material-kit-react/views/landingPage.js";
import LoggedHeader from "components/MyHeaders/LoggedHeader.js"
import classNames from "classnames";
import GridContainer from "components/Grid/GridContainer.js";
import GridItem from "components/Grid/GridItem.js";
import Select from "react-select";
import Pagination from "@material-ui/lab/Pagination";
import NavPills from "components/NavPills/NavPills.js";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import CloseIcon from '@material-ui/icons/Close';
import Button from "components/CustomButtons/Button.js";
import Popover from "@material-ui/core/Popover";
import javascriptStyles from "assets/jss/material-kit-react/views/componentsSections/javascriptStyles.js";
import SettingsIcon from '@material-ui/icons/Settings';
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import IconButton from "@material-ui/core/IconButton";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import Slide from "@material-ui/core/Slide";
import Close from "@material-ui/icons/Close";
import image1 from "assets/img/bg.jpg";
import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Avatar from "@material-ui/core/Avatar";
import InputAdornment from "@material-ui/core/InputAdornment";
import SearchIcon from "@material-ui/icons/Search";
import CustomInput from "components/CustomInput/CustomInput";

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="down" ref={ref} {...props} />;
});


class Admin extends Component {
    constructor(props) {

        super(props);

        this.state = {
            anchorElLeft: null,
            gamesActions: null,
            userActions: null,
            current: null,
            classicModal: false,
            userBanModel: false,
            reviews: {},
            games: {},
            users: {}
        };

    }

    loadData = () => {
        const reviews_size = 6;

        const reviews = {};

        for (let i = 1; i <= reviews_size; i++) {
            reviews[i] = {
                "name": "Jonas_PP" + i,
                "review_text": '"The dude fucking ripped me off. Absolutely dreadful, would recommend to stay as far away from this guy as possible!"',
                "score": i
            }
        }

        const games_size = 6;
        const games = {};
        for (let i = 1; i <= games_size; i++) {
            games[i] = {
                "seller": {
                    "id": i,
                    "name": "Jonas_pistolas" + i
                },
                "name": "FIFA" + i,
                "description": "GANDA JOGO" + i,
                "price": 9,
                "image": image1
            }
        }


        const users_size = 6;
        const users = {};

        for (let i = 1; i <= users_size; i++) {
            users[i] = {
                "username": "Jonas pistolas" + i,
                "name": "Jonas" + i,
                "email": "jonas" + i + "@gmail.com",
                "photo": image1
            }
        }


        this.setState({
            reviews: reviews,
            games: games,
            users: users
        });

    };


    filterData = (e, title) => {
        const userInput = e.target.value.toLowerCase();


        let data = {};
        let type = "";
        let key = ""; // Tentar meter varias keys

        if (title === "User reviews") {
            data = this.state.reviews;
            type = "reviews";
            key = "name";
        } else if (title === "Games Request") {
            data = this.state.games;
            type = "games";
            key = "name";
        } else if (title === "Users") {
            data = this.state.users;
            type = "users";
            key = "name";
        }


        let newData = {};
        const keys = Object.keys(data);
        for (let i = 0; i < keys.length; i++) {
            if (data[keys[i]][key].toLowerCase().startsWith(userInput)) {
                newData[keys[i]] = data[keys[i]];
            }

        }


        this.setState({
            [`${type}`]: newData
        });

    };


    componentDidMount() {
        this.loadData();
    }

    setAnchorElLeft = (v) => {
        this.setState({
            anchorElLeft: v,
        });
    };

    setGamesActions = (v) => {
        this.setState({
            gamesActions: v
        })
    };

    setClassicModal = (v) => {
        this.setState({
            classicModal: v
        })
    };


    removeReview = (e) => {
        const id = e.currentTarget.id;

        if (!id.includes("review")) return;

        const id_ = parseInt(id.replace('review', ''));
        const text = e.currentTarget.name + " review's";

        this.setState({
            current: {
                id: id_,
                type: 'review',
                text: text
            }
        });

        this.setAnchorElLeft(e.currentTarget);

    };

    gamesActions = (e) => {
        const id = e.currentTarget.id;

        if (!id.includes("game_request")) return;

        const _id = parseInt(id.replace("game_request", ""));


        this.setState({
            current: {
                id: _id,
                type: "game_request",
                data: this.state.games[_id]
            }
        });

        this.setGamesActions(e.currentTarget);
    };

    acceptGame = (e) => {
        // Accepts and send to REST
        const {current} = this.state;
        const name = current.data.name;
        toast.success(name + " accepted with success");

        this.confirmRemoval();

        this.setGamesActions(null);
    };

    declineGame = (e) => {
        // Decline and send to REST
        const {current} = this.state;
        const name = current.data.name;
        toast.info(name + " declined with success");

        this.confirmRemoval();

        this.setGamesActions(null);
    };

    detailsGame = (e) => {
        this.setClassicModal(true);
        this.setGamesActions(null);
    };

    usersAction = (e) => {
        const id = e.currentTarget.id;

        if (!id.includes("user")) return;

        const _id = parseInt(id.replace("user", ""));


        this.setState({
            current: {
                id: _id,
                type: "user",
                data: this.state.users[_id]
            }
        });

        this.setUserActions(e.currentTarget);
    };

    setUserActions = (v) => {
        this.setState({
            userActions: v
        })
    };

    banUser = (e) => {
        const {current} = this.state;
        const name = current.data.name;
        // REST

        toast.info(name + " removed with success");

        this.confirmRemoval();
        this.setUserBanModal(false);
    };


    confirmRemoval = () => {
        if (this.state.current === null)
            return;

        const {current} = this.state;
        const type = current.type;


        if (type === 'review') {
            const {reviews} = this.state;

            delete reviews[current.id];

            this.setState({
                reviews: reviews
            });

            toast.info("Review removed with success");

        } else if (type === "game_request") {
            const {games} = this.state;

            delete games[current.id];
            this.setState({
                games: games
            });

        } else if (type === "user") {
            const {users} = this.state;

            delete users[current.id];
            this.setState({
                users: users
            });
        }

        this.setState({
            current: null
        });

        this.setAnchorElLeft(null);
    };


    renderUsers() {
        const classes = this.props;
        const rows = [];
        const {users} = this.state;

        rows.push({
            "name": "header_users",
            "data": {
                "user": <span style={{"text-align": "center"}}> <b>Name</b> </span>,
                "username": <b>Username</b>,
                "email": <b>Email</b>,
                "action": <b>&nbsp;&nbsp;Action</b>
            }
        });


        Object.keys(users).map((id) => {
            rows.push({
                "name": users[id].name + "users",
                "data": {
                    "user": <div>
                        <GridContainer>
                            <GridItem xs={2} sm={2} md={2}>
                                <Avatar aria-label="recipe" className={classes.avatar}>
                                    <img src={users[id].photo}/>
                                </Avatar>
                            </GridItem>
                            <GridItem xs={6} sm={6} md={6} style={{"padding-top": "10px"}}>
                                {users[id].name}
                            </GridItem>
                        </GridContainer>
                    </div>,
                    "username": users[id].username,
                    "email": users[id].email,
                    "action": <Button justIcon simple color="primary" size="lg" id={"user" + id}
                                      onClick={this.usersAction}>
                        <SettingsIcon className={classes.icons}/>
                    </Button>
                }
            })
        });


        const title = "Users";
        const options = {
            "default": {"value": "SELLER", "label": "Sort by user"},
            "options": [{"value": "SELLER", "label": "Sort by user"}]
        };


        return this.renderSection(this.renderTable(rows), title, options);

    };

    renderGames() {
        const classes = this.props;
        const rows = [];
        const {games} = this.state;

        rows.push({
            "name": "header_game_request",
            "data": {
                "seller": <span style={{"text-align": "center"}}> <b>Seller</b> </span>,
                "name": <b>Game Name</b>,
                "price": <b>Game Price</b>,
                "description": <b>Game description</b>,
                "action": <b>&nbsp;&nbsp;Action</b>
            }
        });

        Object.keys(games).map((id) => {
            rows.push({
                "name": games[id].name + "_game_request",
                "data": {
                    "seller":
                        <div>
                            <GridContainer>
                                <GridItem xs={2} sm={2} md={2}>
                                    <Avatar aria-label="recipe" className={classes.avatar}>
                                        <img src={image1}/>
                                    </Avatar>
                                </GridItem>
                                <GridItem xs={6} sm={6} md={6} style={{"padding-top": "10px"}}>
                                    {games[id].seller.name}
                                </GridItem>
                            </GridContainer>
                        </div>,
                    "name": games[id].name,
                    "price": <span style={{color: "#3b3e48", fontSize: "15px", fontWeight: "bolder"}}>
                        {games[id].price} <i className="fas fa-euro-sign"></i> </span>,
                    "description": games[id].description,
                    "action": <Button justIcon simple color="primary" size="lg" id={"game_request" + id}
                                      onClick={this.gamesActions}>
                        <SettingsIcon className={classes.icons}/>
                    </Button>
                }
            })
        });

        const title = "Games Request";
        const options = {
            "default": {"value": "DATE", "label": "Sort by Date"},
            "options": [{"value": "DATE", "label": "Sort by Date"}, {
                "value": "Price",
                "label": "Sort by Price"
            }, {"value": "SELLER", "label": "Sort by Seller"}]
        };


        return this.renderSection(this.renderTable(rows), title, options);
    }


    renderReviews() {
        const classes = this.props;
        const rows = [];
        const {reviews} = this.state;

        rows.push({
            "name": "header_review",
            "data": {
                "user": <span style={{"text-align": "center"}}> <b>User</b> </span>,
                "gridScore": <b>Grid Score</b>,
                "review": <b>Review comment</b>,
                "action": <b>&nbsp;&nbsp;Action</b>
            }
        });


        Object.keys(reviews).map((id) => {
            rows.push({
                "name": reviews[id].name + "_review",
                "data": {
                    "user": <div>
                        <GridContainer>
                            <GridItem xs={4} sm={4} md={4}>
                                <Avatar aria-label="recipe" className={classes.avatar}>
                                    <img src={image1}/>
                                </Avatar>
                            </GridItem>
                            <GridItem xs={6} sm={6} md={6} style={{"padding-top": "10px"}}>
                                {reviews[id].name}
                            </GridItem>
                        </GridContainer>
                    </div>,
                    "gridScore": <span style={{color: "#4ec884", fontSize: "15px", fontWeight: "bolder"}}>
                        {reviews[id].score} <i class="far fa-star"></i> </span>,
                    "review": <span style={{color: "#3b3e48", fontSize: "15px", fontWeight: "bold"}}>
                        {reviews[id].review_text}
                    </span>,
                    "action": <Button justIcon simple color="primary" size="lg" id={"review" + id}
                                      name={reviews[id].name}
                                      onClick={(e) => {
                                          this.removeReview(e)
                                      }}>
                        <CloseIcon className={classes.icons}/>
                    </Button>
                }

            });
        });

        const title = "User reviews";
        const options = {
            "default": {"value": "DATE", "label": "Sort by Date"},
            "options": [{"value": "DATE", "label": "Sort by Date"}, {
                "value": "SCORE",
                "label": "Sort by Score"
            }, {"value": "USER", "label": "Sort by User"}]
        };

        return this.renderSection(this.renderTable(rows), title, options);
    }

    renderTable(rows) {
        return (
            <TableContainer component={Paper}>
                <Table style={{width: "100%"}} aria-label="simple table">
                    <TableBody>
                        {rows.map((row) => (
                            <TableRow hover id={row.name}>
                                {Object.keys(row.data).map((r) => {
                                    return (
                                        <TableCell align="left">{row.data[r]}</TableCell>
                                    )
                                })}
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        )
    }


    renderSection(table, title, options) {
        const {classes} = this.props;
        return (
            <div>
                <span>
                    <h2 style={{
                        color: "#999",
                        fontWeight: "bolder",
                        marginTop: "10px",
                        padding: "0 0"
                    }}>{title}
                    </h2>
                </span>
                <div className={classes.container}>
                    <GridContainer justify="center">
                        <GridItem xs={12} sm={12} md={12}>
                            <CustomInput
                                labelText="Search..."
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
                                        this.filterData(e, title)
                                    },
                                    autoComplete: "off"
                                }}
                            />
                        </GridItem>
                    </GridContainer>
                </div>
                <div style={{
                    color: "#000",
                    padding: "12px 0",
                    width: "20%"
                }}

                >
                    <Select
                        className="basic-single"
                        classNamePrefix="select"
                        isSearchable={false}
                        name="color"
                        defaultValue={options.default}
                        options={options.options}
                    />

                </div>
                {table}
                <div style={{marginTop: "20px"}}>
                    <div style={{margin: "auto", width: "40%"}}>
                        <Pagination count={1} variant="outlined"
                                    shape="rounded"/>
                    </div>
                </div>
            </div>
        )
    }

    renderReviewPopover() {
        const {classes} = this.props;

        return (
            <Popover
                classes={{
                    paper: classes.popover
                }}
                open={Boolean(this.state.anchorElLeft)}
                anchorEl={this.state.anchorElLeft}
                onClose={() => this.setAnchorElLeft(null)}
                anchorOrigin={{
                    vertical: "center",
                    horizontal: "right"
                }}
                transformOrigin={{
                    vertical: "center",
                    horizontal: "left"
                }}
            >
                <h3 className={classes.popoverHeader}>Are you sure you want
                    to remove {this.state.current === null ? '' : this.state.current.text} ?
                </h3>

                <div className={classes.popoverBody}
                     style={{"text-align": "center"}}>
                    <Button color="success" round size="sm"
                            onClick={this.confirmRemoval}>Yes</Button>
                    <Button color="danger" round size="sm"
                            onClick={() => this.setAnchorElLeft(null)}>No</Button>
                </div>

            </Popover>
        )
    }

    renderGameActionsPopover() {
        const {classes} = this.props;

        return (
            <Popover
                classes={{
                    paper: classes.popover
                }}

                open={Boolean(this.state.gamesActions)}
                anchorEl={this.state.gamesActions}
                onClose={() => this.setGamesActions(null)}
                anchorOrigin={{
                    vertical: "center",
                    horizontal: "right"
                }}
                transformOrigin={{
                    vertical: "center",
                    horizontal: "left"
                }}
            >
                <div className={classes.popoverBody} style={{"text-align": "center"}}>
                    <h3>Actions...</h3>
                    <GridContainer justify="center">
                        <GridItem xs={12} sm={12} md={12}>
                            <Button color="primary" round size="sm" onClick={this.detailsGame}>More
                                details...</Button>
                        </GridItem>
                        <GridItem xs={12} sm={12} md={6}>
                            <Button color="success" round size="sm" onClick={this.acceptGame}>Accept</Button>
                        </GridItem>
                        <GridItem xs={12} sm={12} md={6}>
                            <Button color="danger" round size="sm" onClick={this.declineGame}>Decline</Button>
                        </GridItem>

                    </GridContainer>
                </div>
            </Popover>
        )
    }


    renderUserActionsPopover() {
        const {classes} = this.props;

        return (
            <Popover
                classes={{
                    paper: classes.popover
                }}

                open={Boolean(this.state.userActions)}
                anchorEl={this.state.userActions}
                onClose={() => this.setUserActions(null)}
                anchorOrigin={{
                    vertical: "center",
                    horizontal: "right"
                }}
                transformOrigin={{
                    vertical: "center",
                    horizontal: "left"
                }}
            >
                <div className={classes.popoverBody} style={{"text-align": "center"}}>
                    <h3>Actions...</h3>
                    <GridContainer justify="center">
                        <GridItem xs={12} sm={12} md={6}>
                            <Button color="primary" round size="sm" onClick={(e) => {
                                window.open("/user/:user", "newTab");
                                this.setUserActions(null);
                            }}>More
                                details...</Button>
                        </GridItem>
                        <GridItem xs={12} sm={12} md={6}>
                            <Button color="danger" round size="sm" onClick={() => {
                                this.setUserBanModal(true);
                                this.setUserActions(null);
                            }}>Ban user</Button>
                        </GridItem>

                    </GridContainer>
                </div>
            </Popover>
        )
    }

    renderGameDetails(data) {
        const {classes} = this.props;

        if (data === undefined || Object.keys(data).length === 0) return;

        return (
            <GridContainer>
                <GridItem xs={12} sm={12} md={6}>
                    <img
                        src={data.image}
                        alt="..."
                        style={{width: "95%", height: "260px", marginTop: "28px"}}
                        className={
                            classes.imgRaised +
                            " " +
                            classes.imgRounded
                        }
                    />
                </GridItem>

                <GridItem xs={12} sm={12} md={6}>
                    <div style={{textAlign: "left"}}>
                        <h3 style={{color: "#3b3e48", fontWeight: "bolder"}}><b
                            style={{color: "#3b3e48"}}>{data.name}</b>
                        </h3>
                        <hr style={{color: "#999"}}></hr>
                    </div>
                    <div style={{textAlign: "left", marginTop: "30px"}}>
                        <span style={{color: "#999", fontSize: "15px"}}>
                            <b>Description:</b> <span style={{color: "#3b3e48"}}>{data.description}</span>
                        </span>
                    </div>
                </GridItem>

                <GridItem xs={12} sm={12} md={5}>
                    <div style={{textAlign: "left", marginTop: "20px"}}>
                        <span style={{color: "#999", fontSize: "12px"}}>
                            Seller
                        </span>
                    </div>
                    <div style={{textAlign: "left"}}>
                        <span style={{color: "#3b3e48", fontSize: "15px", fontWeight: "bolder"}}>
                            {data.seller.name}
                        </span>
                    </div>
                    <div style={{textAlign: "left"}}>
                        <span style={{color: "#4ec884", fontSize: "15px", fontWeight: "bolder"}}>
                            4 <i class="far fa-star"></i>
                        </span>
                        <span style={{
                            color: "#999",
                            fontSize: "15px",
                            fontWeight: "bolder",
                            marginLeft: "10px"
                        }}>
                            Grid Score
                        </span>
                    </div>
                    <div style={{textAlign: "left"}}>
                        <Button
                            color="danger"
                            size="sm"
                            style={{backgroundColor: "#ff3ea0"}}
                            href="https://www.youtube.com/watch?v=dQw4w9WgXcQ&ref=creativetim"
                            target="_blank"
                            rel="noopener noreferrer"
                        >
                            <i class="far fa-user"></i> Seller Profile
                        </Button>
                    </div>
                </GridItem>

                <GridItem xs={12} sm={12} md={5}>
                    <div style={{textAlign: "left", marginTop: "20px"}}>
                        <span style={{color: "#999", fontSize: "12px"}}>
                            Price
                        </span>
                    </div>
                    <div style={{textAlign: "left", marginTop: "10px"}}>
                        <span style={{color: "#f44336", fontSize: "40px", fontWeight: "bolder"}}>
                            {data.price}€
                        </span>
                    </div>
                    <div style={{textAlign: "left", marginTop: "8px"}}>
                        <span style={{color: "#3b3e48", fontSize: "20", fontWeight: "bolder"}}>
                            ( Steam Key )
                        </span>
                    </div>
                </GridItem>

            </GridContainer>
        )
    }


    renderModal(data) {
        const {classes} = this.props;
        return (
            <Dialog
                classes={{
                    root: classes.center,
                    paper: classes.modal
                }}
                open={this.state.classicModal}
                TransitionComponent={Transition}
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
                    >
                        <Close className={classes.modalClose}/>
                    </IconButton>
                    <h4 className={classes.modalTitle}>Game details</h4>
                </DialogTitle>
                <DialogContent
                    id="classic-modal-slide-description"
                    className={classes.modalBody}>
                    <div style={{"text-align": "center"}}>
                        {this.renderGameDetails(data)}
                    </div>

                </DialogContent>
                <DialogActions className={classes.modalFooter}>
                    <Button color="success" round size="sm" onClick={this.acceptGame}>Accept</Button>
                    <Button color="danger" round size="sm" onClick={this.declineGame}>Decline</Button>
                </DialogActions>
            </Dialog>
        )
    }

    setUserBanModal = (v) => {
        this.setState({
            userBanModel: v
        })
    };

    renderBanUserModal(data) {
        const {classes} = this.props;
        return (
            <Dialog
                classes={{
                    root: classes.center,
                    paper: classes.modal
                }}
                open={this.state.userBanModel}
                TransitionComponent={Transition}
                keepMounted
                onClose={() => this.setUserBanModal(false)}
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
                        onClick={() => this.setUserBanModal(false)}
                    >
                        <Close className={classes.modalClose}/>
                    </IconButton>
                    <h4 className={classes.modalTitle}>Do you really want to ban this user? Its an irreversible
                        operation!!</h4>
                </DialogTitle>
                <DialogActions className={classes.modalFooter}>
                    <Button color="success" round size="sm" onClick={this.banUser}>Yes</Button>
                    <Button color="danger" round size="sm" onClick={() => this.setUserBanModal(false)}>No</Button>
                </DialogActions>
            </Dialog>
        );
    }

    renderAddGame() {
        return (
            <div>
                <span>
                    <h2 style={{
                        color: "#999",
                        fontWeight: "bolder",
                        marginTop: "10px",
                        padding: "0 0"
                    }}>Add Game
                    </h2>
                </span>
                <div>
                    <Button color="primary" round size="lg" onClick={window.open("/sell-new-game", "newTab")}>Add Game</Button>
                </div>

            </div>
        )
    }

    render() {
        const {classes} = this.props;

        return (
            <div>
                <ToastContainer/>

                <LoggedHeader name="Jonas Pistolas" cart={true} wallet={0.00} heightChange={false}
                              username={"Jonas_PP"}/>
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
                                                }}>Admin Controls</h2>
                                            </GridItem>
                                        </GridContainer>

                                        <hr style={{color: "#999", opacity: "0.4"}}></hr>


                                        <GridContainer justify="center">
                                            <GridItem xs={12} sm={12} md={12}>
                                                <NavPills
                                                    color="rose"
                                                    tabs={[
                                                        {
                                                            tabButton: "Reviews",
                                                            tabIcon: "far fa-comment-alt",
                                                            tabContent: this.renderReviews()
                                                        },
                                                        {
                                                            tabButton: "Games Request",
                                                            tabIcon: "fas fa-gamepad",
                                                            tabContent: this.renderGames()
                                                        },
                                                        {
                                                            tabButton: "Add Game",
                                                            tabIcon: "fas fa-gamepad",
                                                            tabContent: this.renderAddGame()
                                                        },
                                                        {
                                                            tabButton: "Users",
                                                            tabIcon: "fas fa-users",
                                                            tabContent: this.renderUsers()
                                                        }
                                                    ]}
                                                />
                                            </GridItem>
                                        </GridContainer>
                                    </div>
                                </GridItem>
                            </GridContainer>
                        </div>
                    </div>
                </div>

                {this.renderReviewPopover()}
                {this.renderGameActionsPopover()}
                {this.renderUserActionsPopover()}
                {this.renderModal(this.state.current === null || this.state.type !== "game_request" ? {} : this.state.current.data)}
                {this.renderBanUserModal(this.state.current === null || this.state.type !== "user" ? {} : this.state.current.data)}


            </div>
        )
    }

}


export default withStyles(Object.assign({}, styles, javascriptStyles))(Admin);