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

import Game from "views/GameInfo/Game.js";
import GameSearch from "views/GameSearch/GameSearch.js";
import HomePage from "views/HomePage/HomePage.js";
import SellNewGame from "views/SellNewGame/SellNewGame.js";
import SellGame from "views/SellGame/SellGame.js";
import ProfilePage from "views/Profile/ProfilePage.js";

import Cart from "views/Cart/Cart.js";

var hist = createBrowserHistory();

ReactDOM.render(
  <Router history={hist}>
    <Switch>
        <Route path="/landing-page" component={LandingPage}/>
        <Route path="/profile-page" component={ProfilePage}/>
        <Route path="/login-page" component={LoginPage}/>
        <Route path="/components" component={Components}/>

        <Route path="/cart" component={Cart}/>
        <Route path="/games/info" component={Game} />
        <Route path="/games" component={GameSearch} />
        <Route path="/sell-game" component={SellGame} />
        <Route path="/sell-new-game" component={SellNewGame} />
        <Route path="/user" component={ProfilePage}/>
        <Route path="/" component={HomePage} />
    </Switch>
  </Router>,
  document.getElementById("root")
);
