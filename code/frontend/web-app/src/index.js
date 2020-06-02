import React from "react";
import ReactDOM from "react-dom";
import {createBrowserHistory} from "history";
import {Router, Route, Switch} from "react-router-dom";

import "assets/scss/material-kit-react.scss?v=1.8.0";

// pages for this product
import Components from "views/Components/Components.js";
import LandingPage from "views/LandingPage/LandingPage.js";
//import ProfilePage from "views/ProfilePage/ProfilePage.js";
import LoginPage from "views/LoginPage/LoginPage.js";
import Signup from "views/LoginPage/Signup.js";


import Game from "views/GameInfo/Game.js";
import GameSearch from "views/GameSearch/GameSearch.js";
import HomePage from "views/HomePage/HomePage.js";
import SellNewGame from "views/SellNewGame/SellNewGame.js";
import SellGame from "views/SellGame/SellGame.js";
import ProfilePage from "views/Profile/ProfilePage.js";
import EditProfile from "views/Profile/EditProfile.js";
import Admin from "views/Admin/Admin.js";
import DashBoard from "views/Admin/DashBoard.js";

import Cart from "views/Cart/Cart.js";
<<<<<<< HEAD
import Wallet from "views/Wallet/Wallet.js";
=======
import GameReview from "views/Review/GameReview.js";
import UserReview from "views/Review/UserReview.js";

<<<<<<< HEAD
>>>>>>> 18ee9e6300b5b398b08b96595beceaf6628389a7
=======
>>>>>>> master
>>>>>>> a2f161283b22cfe5d4a2d129a2bfd1834a50b1e7
import Receipt from "views/Cart/Receipt.js";
import WishList from "views/WishList/WishList.js";


var hist = createBrowserHistory();

ReactDOM.render(
    <Router history={hist}>
        <Switch>
            <Route path="/landing-page" component={LandingPage}/>
            <Route path="/profile-page" component={ProfilePage}/>
            <Route path="/components" component={Components}/>

            <Route exact path="/login-page" component={LoginPage}/>
            <Route exact path="/signup-page" component={Signup}/>
            <Route exact path="/cart" component={Cart}/>
            <Route exact path="/cart/receipt" component={Receipt}/>
            <Route exact path="/games/info/:game" component={Game}/>
            <Route exact path="/games" component={GameSearch}/>
<<<<<<< HEAD
            <Route exact path="/wallet" component={Wallet}/>
=======
            <Route exact path="/review-game" component={GameReview}/>
            <Route exact path="/review-user" component={UserReview}/>
<<<<<<< HEAD
>>>>>>> 18ee9e6300b5b398b08b96595beceaf6628389a7
=======
>>>>>>> master
>>>>>>> a2f161283b22cfe5d4a2d129a2bfd1834a50b1e7
            <Route exact path="/user/:user" component={ProfilePage}/>
            <Route exact path="/user/:user/edit" component={EditProfile}/>
            <Route exact path="/sell-game" component={SellGame}/>
            <Route exact path="/sell-new-game" component={SellNewGame}/>
            <Route exact path="/wishlist" component={WishList}/>
            <Route exact path="/admin" component={Admin}/>
            <Route exact path="/" component={HomePage}/>
        </Switch>
    </Router>,
    document.getElementById("root")
);
