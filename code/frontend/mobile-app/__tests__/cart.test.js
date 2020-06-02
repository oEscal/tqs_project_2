import React from 'react';
import CartScreen from '../screens/Cart';

import { AsyncStorage } from 'react-native';

import Enzyme from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import fetchMock from 'fetch-mock';

import baseURL from '../constants/baseURL'

Enzyme.configure({ adapter: new Adapter() });

describe('Fetching Data', () => {
    beforeEach(() => {
        fetchMock.restore();
    })

    it('when getting cart listings and cart is null then update state to empty array', async () => {
        // Setup this.props.route.params.game.id
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<CartScreen />);
        const instance = wrapper.instance();

        // Update user
        const user = {
            "id": 1,
            "username": "admin",
            "name": "Mr eheh uhuh",
            "email": "admin@adeus.com",
            "country": "ola",
            "password": null,
            "birthDate": "09/10/1999",
            "startDate": "28/05/2020",
            "admin": true,
            "photoUrl": null,
            "description": null,
            "creditCardNumber": null,
            "creditCardCSC": null,
            "creditCardOwner": null,
            "creditCardExpirationDate": null,
            "reviewGames": [],
            "reviewedUsers": [],
            "reviewUsers": [],
            "reportsOnGameReview": [],
            "reportsOnUserReview": [],
            "reportsThisUser": [],
            "reportsOnUser": [],
            "buys": [
                {
                    "id": 5,
                    "date": "2020-05-29",
                    "sellId": 4,
                    "gamerKey": "3tayk0lbDu",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "sellerId": 1,
                    "sellerName": "admin",
                    "gameId": 2,
                    "userId": 1
                }
            ],
            "wishList": [
                {
                    "id": 1,
                    "name": "Grand Theft Auto V",
                    "description": "<p>Rockstar Games went bigger, since their previous installment of the series. You get the complicated and realistic world-building from Liberty City of GTA4 in the setting of lively and diverse Los Santos, from an old fan favorite GTA San Andreas. 561 different vehicles (including every transport you can operate) and the amount is rising with every update. <br />\nSimultaneous storytelling from three unique perspectives: <br />\nFollow Michael, ex-criminal living his life of leisure away from the past, Franklin, a kid that seeks the better future, and Trevor, the exact past Michael is trying to run away from. <br />\nGTA Online will provide a lot of additional challenge even for the experienced players, coming fresh from the story mode. Now you will have other players around that can help you just as likely as ruin your mission. Every GTA mechanic up to date can be experienced by players through the unique customizable character, and community content paired with the leveling system tends to keep everyone busy and engaged.</p>",
                    "gameGenres": [
                        {
                            "id": 3,
                            "name": "Adventure",
                            "description": null
                        },
                        {
                            "id": 1,
                            "name": "Action",
                            "description": null
                        }
                    ],
                    "publisher": {
                        "id": 21,
                        "name": "1C-SoftClub",
                        "description": "<p>1C-SoftClub is the biggest Russian-based video game publisher, and translator.</p>\n<h3>History</h"
                    },
                    "developers": [
                        {
                            "id": 20,
                            "name": "Rockstar North"
                        }
                    ],
                    "releaseDate": "2013-09-17",
                    "gameKeys": [],
                    "coverUrl": "https://media.rawg.io/media/games/b11/b115b2bc6a5957a917bc7601f4abdda2.jpg",
                    "bestSell": null,
                    "platforms": []
                }
            ],
            "funds": 0.0,
            "birthDateStr": "10/10/1999",
            "startDateStr": "29/05/2020"
        }
        await wrapper.setState({ user: user });

        // Update Cart
        const cart = null
        await wrapper.setState({ cart: cart });

        // Call method
        await instance.getGameListings();

        // Assertions
        var expected = []
        var price = 0
        expect(wrapper.state('sellListings')).toEqual(expected);
        expect(wrapper.state('price')).toEqual(price);

    });

    it('when getting cart listings and cart is not empty then update state', async () => {
        // Setup this.props.route.params.game.id
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<CartScreen />);
        const instance = wrapper.instance();

        // Update user
        const user = {
            "id": 1,
            "username": "admin",
            "name": "Mr eheh uhuh",
            "email": "admin@adeus.com",
            "country": "ola",
            "password": null,
            "birthDate": "09/10/1999",
            "startDate": "28/05/2020",
            "admin": true,
            "photoUrl": null,
            "description": null,
            "creditCardNumber": null,
            "creditCardCSC": null,
            "creditCardOwner": null,
            "creditCardExpirationDate": null,
            "reviewGames": [],
            "reviewedUsers": [],
            "reviewUsers": [],
            "reportsOnGameReview": [],
            "reportsOnUserReview": [],
            "reportsThisUser": [],
            "reportsOnUser": [],
            "buys": [
                {
                    "id": 5,
                    "date": "2020-05-29",
                    "sellId": 4,
                    "gamerKey": "3tayk0lbDu",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "sellerId": 1,
                    "sellerName": "admin",
                    "gameId": 2,
                    "userId": 1
                }
            ],
            "wishList": [
                {
                    "id": 1,
                    "name": "Grand Theft Auto V",
                    "description": "<p>Rockstar Games went bigger, since their previous installment of the series. You get the complicated and realistic world-building from Liberty City of GTA4 in the setting of lively and diverse Los Santos, from an old fan favorite GTA San Andreas. 561 different vehicles (including every transport you can operate) and the amount is rising with every update. <br />\nSimultaneous storytelling from three unique perspectives: <br />\nFollow Michael, ex-criminal living his life of leisure away from the past, Franklin, a kid that seeks the better future, and Trevor, the exact past Michael is trying to run away from. <br />\nGTA Online will provide a lot of additional challenge even for the experienced players, coming fresh from the story mode. Now you will have other players around that can help you just as likely as ruin your mission. Every GTA mechanic up to date can be experienced by players through the unique customizable character, and community content paired with the leveling system tends to keep everyone busy and engaged.</p>",
                    "gameGenres": [
                        {
                            "id": 3,
                            "name": "Adventure",
                            "description": null
                        },
                        {
                            "id": 1,
                            "name": "Action",
                            "description": null
                        }
                    ],
                    "publisher": {
                        "id": 21,
                        "name": "1C-SoftClub",
                        "description": "<p>1C-SoftClub is the biggest Russian-based video game publisher, and translator.</p>\n<h3>History</h"
                    },
                    "developers": [
                        {
                            "id": 20,
                            "name": "Rockstar North"
                        }
                    ],
                    "releaseDate": "2013-09-17",
                    "gameKeys": [],
                    "coverUrl": "https://media.rawg.io/media/games/b11/b115b2bc6a5957a917bc7601f4abdda2.jpg",
                    "bestSell": null,
                    "platforms": []
                }
            ],
            "funds": 0.0,
            "birthDateStr": "10/10/1999",
            "startDateStr": "29/05/2020"
        }
        await wrapper.setState({ user: user });

        // Update Cart
        const cart = {
            games: [
                {
                    "id": 27,
                    "gameKey": {
                        "id": 27,
                        "retailer": "admin",
                        "platform": "Steam",
                        "gameName": "Portal 2",
                        "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                        "gameId": 2
                    },
                    "purchased": null,
                    "price": 10.0,
                    "date": "2020-05-29",
                    "userId": 1
                },
                {
                    "id": 28,
                    "gameKey": {
                        "id": 28,
                        "retailer": "admin",
                        "platform": "Steam",
                        "gameName": "Portal 2",
                        "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                        "gameId": 2
                    },
                    "purchased": null,
                    "price": 10.0,
                    "date": "2020-05-29",
                    "userId": 1
                },
            ]
        }
        await wrapper.setState({ cart: cart });

        // Call method
        await instance.getGameListings();

        // Assertions
        var expected = [{ "date": "2020-05-29", "gameKey": { "gameId": 2, "gameName": "Portal 2", "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg", "id": 27, "platform": "Steam", "retailer": "admin" }, "id": 27, "price": 10, "purchased": null, "userId": 1 }, { "date": "2020-05-29", "gameKey": { "gameId": 2, "gameName": "Portal 2", "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg", "id": 28, "platform": "Steam", "retailer": "admin" }, "id": 28, "price": 10, "purchased": null, "userId": 1 }]
        var price = 20.0
        expect(wrapper.state('sellListings')).toEqual(expected);
        expect(wrapper.state('price')).toEqual(price);

    });

});

describe('Buy options', () => {
    beforeEach(() => {
        fetchMock.restore();
    })

    it('when buying with wallet and not enough funds then show error', async () => {
        // Setup this.props.route.params.game.id
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<CartScreen />);
        const instance = wrapper.instance();

        // Update user
        const user = {
            "id": 1,
            "username": "admin",
            "name": "Mr eheh uhuh",
            "email": "admin@adeus.com",
            "country": "ola",
            "password": null,
            "birthDate": "09/10/1999",
            "startDate": "28/05/2020",
            "admin": true,
            "photoUrl": null,
            "description": null,
            "creditCardNumber": null,
            "creditCardCSC": null,
            "creditCardOwner": null,
            "creditCardExpirationDate": null,
            "reviewGames": [],
            "reviewedUsers": [],
            "reviewUsers": [],
            "reportsOnGameReview": [],
            "reportsOnUserReview": [],
            "reportsThisUser": [],
            "reportsOnUser": [],
            "buys": [
                {
                    "id": 5,
                    "date": "2020-05-29",
                    "sellId": 4,
                    "gamerKey": "3tayk0lbDu",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "sellerId": 1,
                    "sellerName": "admin",
                    "gameId": 2,
                    "userId": 1
                }
            ],
            "wishList": [
                {
                    "id": 1,
                    "name": "Grand Theft Auto V",
                    "description": "<p>Rockstar Games went bigger, since their previous installment of the series. You get the complicated and realistic world-building from Liberty City of GTA4 in the setting of lively and diverse Los Santos, from an old fan favorite GTA San Andreas. 561 different vehicles (including every transport you can operate) and the amount is rising with every update. <br />\nSimultaneous storytelling from three unique perspectives: <br />\nFollow Michael, ex-criminal living his life of leisure away from the past, Franklin, a kid that seeks the better future, and Trevor, the exact past Michael is trying to run away from. <br />\nGTA Online will provide a lot of additional challenge even for the experienced players, coming fresh from the story mode. Now you will have other players around that can help you just as likely as ruin your mission. Every GTA mechanic up to date can be experienced by players through the unique customizable character, and community content paired with the leveling system tends to keep everyone busy and engaged.</p>",
                    "gameGenres": [
                        {
                            "id": 3,
                            "name": "Adventure",
                            "description": null
                        },
                        {
                            "id": 1,
                            "name": "Action",
                            "description": null
                        }
                    ],
                    "publisher": {
                        "id": 21,
                        "name": "1C-SoftClub",
                        "description": "<p>1C-SoftClub is the biggest Russian-based video game publisher, and translator.</p>\n<h3>History</h"
                    },
                    "developers": [
                        {
                            "id": 20,
                            "name": "Rockstar North"
                        }
                    ],
                    "releaseDate": "2013-09-17",
                    "gameKeys": [],
                    "coverUrl": "https://media.rawg.io/media/games/b11/b115b2bc6a5957a917bc7601f4abdda2.jpg",
                    "bestSell": null,
                    "platforms": []
                }
            ],
            "funds": 0.0,
            "birthDateStr": "10/10/1999",
            "startDateStr": "29/05/2020"
        }
        await wrapper.setState({ user: user });

        // Update Cart
        const cart = {
            games: [
                {
                    "id": 27,
                    "gameKey": {
                        "id": 27,
                        "retailer": "admin",
                        "platform": "Steam",
                        "gameName": "Portal 2",
                        "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                        "gameId": 2
                    },
                    "purchased": null,
                    "price": 10.0,
                    "date": "2020-05-29",
                    "userId": 1
                },
                {
                    "id": 28,
                    "gameKey": {
                        "id": 28,
                        "retailer": "admin",
                        "platform": "Steam",
                        "gameName": "Portal 2",
                        "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                        "gameId": 2
                    },
                    "purchased": null,
                    "price": 10.0,
                    "date": "2020-05-29",
                    "userId": 1
                },
            ]
        }
        await wrapper.setState({ cart: cart });

        // Call method
        await instance.buyWithWallet();

        // Assertions
        expect(wrapper.state('badFunds')).toBeTruthy;
    });

    it('when buying with wallet and enough funds then don\'t throw bad funds error', async () => {
        // Setup this.props.route.params.game.id
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<CartScreen />);
        const instance = wrapper.instance();

        // Update user
        const user = {
            "id": 1,
            "username": "admin",
            "name": "Mr eheh uhuh",
            "email": "admin@adeus.com",
            "country": "ola",
            "password": null,
            "birthDate": "09/10/1999",
            "startDate": "28/05/2020",
            "admin": true,
            "photoUrl": null,
            "description": null,
            "creditCardNumber": null,
            "creditCardCSC": null,
            "creditCardOwner": null,
            "creditCardExpirationDate": null,
            "reviewGames": [],
            "reviewedUsers": [],
            "reviewUsers": [],
            "reportsOnGameReview": [],
            "reportsOnUserReview": [],
            "reportsThisUser": [],
            "reportsOnUser": [],
            "buys": [
                {
                    "id": 5,
                    "date": "2020-05-29",
                    "sellId": 4,
                    "gamerKey": "3tayk0lbDu",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "sellerId": 1,
                    "sellerName": "admin",
                    "gameId": 2,
                    "userId": 1
                }
            ],
            "wishList": [
                {
                    "id": 1,
                    "name": "Grand Theft Auto V",
                    "description": "<p>Rockstar Games went bigger, since their previous installment of the series. You get the complicated and realistic world-building from Liberty City of GTA4 in the setting of lively and diverse Los Santos, from an old fan favorite GTA San Andreas. 561 different vehicles (including every transport you can operate) and the amount is rising with every update. <br />\nSimultaneous storytelling from three unique perspectives: <br />\nFollow Michael, ex-criminal living his life of leisure away from the past, Franklin, a kid that seeks the better future, and Trevor, the exact past Michael is trying to run away from. <br />\nGTA Online will provide a lot of additional challenge even for the experienced players, coming fresh from the story mode. Now you will have other players around that can help you just as likely as ruin your mission. Every GTA mechanic up to date can be experienced by players through the unique customizable character, and community content paired with the leveling system tends to keep everyone busy and engaged.</p>",
                    "gameGenres": [
                        {
                            "id": 3,
                            "name": "Adventure",
                            "description": null
                        },
                        {
                            "id": 1,
                            "name": "Action",
                            "description": null
                        }
                    ],
                    "publisher": {
                        "id": 21,
                        "name": "1C-SoftClub",
                        "description": "<p>1C-SoftClub is the biggest Russian-based video game publisher, and translator.</p>\n<h3>History</h"
                    },
                    "developers": [
                        {
                            "id": 20,
                            "name": "Rockstar North"
                        }
                    ],
                    "releaseDate": "2013-09-17",
                    "gameKeys": [],
                    "coverUrl": "https://media.rawg.io/media/games/b11/b115b2bc6a5957a917bc7601f4abdda2.jpg",
                    "bestSell": null,
                    "platforms": []
                }
            ],
            "funds": 30.0,
            "birthDateStr": "10/10/1999",
            "startDateStr": "29/05/2020"
        }
        await wrapper.setState({ user: user });

        // Update Cart
        const cart = {
            games: [
                {
                    "id": 27,
                    "gameKey": {
                        "id": 27,
                        "retailer": "admin",
                        "platform": "Steam",
                        "gameName": "Portal 2",
                        "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                        "gameId": 2
                    },
                    "purchased": null,
                    "price": 10.0,
                    "date": "2020-05-29",
                    "userId": 1
                },
                {
                    "id": 28,
                    "gameKey": {
                        "id": 28,
                        "retailer": "admin",
                        "platform": "Steam",
                        "gameName": "Portal 2",
                        "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                        "gameId": 2
                    },
                    "purchased": null,
                    "price": 10.0,
                    "date": "2020-05-29",
                    "userId": 1
                },
            ]
        }
        await wrapper.setState({ cart: cart });

        // Call method
        await instance.buyWithWallet();

        // Assertions
        expect(wrapper.state('badFunds')).toBeFalsy;
    });

    it('when buying with card and card not defined then show error', async () => {
        // Setup this.props.route.params.game.id
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<CartScreen />);
        const instance = wrapper.instance();

        // Update user
        const user = {
            "id": 1,
            "username": "admin",
            "name": "Mr eheh uhuh",
            "email": "admin@adeus.com",
            "country": "ola",
            "password": null,
            "birthDate": "09/10/1999",
            "startDate": "28/05/2020",
            "admin": true,
            "photoUrl": null,
            "description": null,
            "creditCardNumber": "213456789",
            "creditCardCSC": "123",
            "creditCardOwner": "oof",
            "creditCardExpirationDate": "18/06/2020",
            "reviewGames": [],
            "reviewedUsers": [],
            "reviewUsers": [],
            "reportsOnGameReview": [],
            "reportsOnUserReview": [],
            "reportsThisUser": [],
            "reportsOnUser": [],
            "buys": [
                {
                    "id": 5,
                    "date": "2020-05-29",
                    "sellId": 4,
                    "gamerKey": "3tayk0lbDu",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "sellerId": 1,
                    "sellerName": "admin",
                    "gameId": 2,
                    "userId": 1
                }
            ],
            "wishList": [
                {
                    "id": 1,
                    "name": "Grand Theft Auto V",
                    "description": "<p>Rockstar Games went bigger, since their previous installment of the series. You get the complicated and realistic world-building from Liberty City of GTA4 in the setting of lively and diverse Los Santos, from an old fan favorite GTA San Andreas. 561 different vehicles (including every transport you can operate) and the amount is rising with every update. <br />\nSimultaneous storytelling from three unique perspectives: <br />\nFollow Michael, ex-criminal living his life of leisure away from the past, Franklin, a kid that seeks the better future, and Trevor, the exact past Michael is trying to run away from. <br />\nGTA Online will provide a lot of additional challenge even for the experienced players, coming fresh from the story mode. Now you will have other players around that can help you just as likely as ruin your mission. Every GTA mechanic up to date can be experienced by players through the unique customizable character, and community content paired with the leveling system tends to keep everyone busy and engaged.</p>",
                    "gameGenres": [
                        {
                            "id": 3,
                            "name": "Adventure",
                            "description": null
                        },
                        {
                            "id": 1,
                            "name": "Action",
                            "description": null
                        }
                    ],
                    "publisher": {
                        "id": 21,
                        "name": "1C-SoftClub",
                        "description": "<p>1C-SoftClub is the biggest Russian-based video game publisher, and translator.</p>\n<h3>History</h"
                    },
                    "developers": [
                        {
                            "id": 20,
                            "name": "Rockstar North"
                        }
                    ],
                    "releaseDate": "2013-09-17",
                    "gameKeys": [],
                    "coverUrl": "https://media.rawg.io/media/games/b11/b115b2bc6a5957a917bc7601f4abdda2.jpg",
                    "bestSell": null,
                    "platforms": []
                }
            ],
            "funds": 0.0,
            "birthDateStr": "10/10/1999",
            "startDateStr": "29/05/2020"
        }
        await wrapper.setState({ user: user });

        // Update Cart
        const cart = {
            games: [
                {
                    "id": 27,
                    "gameKey": {
                        "id": 27,
                        "retailer": "admin",
                        "platform": "Steam",
                        "gameName": "Portal 2",
                        "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                        "gameId": 2
                    },
                    "purchased": null,
                    "price": 10.0,
                    "date": "2020-05-29",
                    "userId": 1
                },
                {
                    "id": 28,
                    "gameKey": {
                        "id": 28,
                        "retailer": "admin",
                        "platform": "Steam",
                        "gameName": "Portal 2",
                        "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                        "gameId": 2
                    },
                    "purchased": null,
                    "price": 10.0,
                    "date": "2020-05-29",
                    "userId": 1
                },
            ]
        }
        await wrapper.setState({ cart: cart });

        // Call method
        await instance.buyWithCard();

        // Assertions
        expect(wrapper.state('badCard')).toBeTruthy;
    });

    it('when buying with card and card defined then show error', async () => {
        // Setup this.props.route.params.game.id
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<CartScreen />);
        const instance = wrapper.instance();

        // Update user
        const user = {
            "id": 1,
            "username": "admin",
            "name": "Mr eheh uhuh",
            "email": "admin@adeus.com",
            "country": "ola",
            "password": null,
            "birthDate": "09/10/1999",
            "startDate": "28/05/2020",
            "admin": true,
            "photoUrl": null,
            "description": null,
            "creditCardNumber": null,
            "creditCardCSC": null,
            "creditCardOwner": null,
            "creditCardExpirationDate": null,
            "reviewGames": [],
            "reviewedUsers": [],
            "reviewUsers": [],
            "reportsOnGameReview": [],
            "reportsOnUserReview": [],
            "reportsThisUser": [],
            "reportsOnUser": [],
            "buys": [
                {
                    "id": 5,
                    "date": "2020-05-29",
                    "sellId": 4,
                    "gamerKey": "3tayk0lbDu",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "sellerId": 1,
                    "sellerName": "admin",
                    "gameId": 2,
                    "userId": 1
                }
            ],
            "wishList": [
                {
                    "id": 1,
                    "name": "Grand Theft Auto V",
                    "description": "<p>Rockstar Games went bigger, since their previous installment of the series. You get the complicated and realistic world-building from Liberty City of GTA4 in the setting of lively and diverse Los Santos, from an old fan favorite GTA San Andreas. 561 different vehicles (including every transport you can operate) and the amount is rising with every update. <br />\nSimultaneous storytelling from three unique perspectives: <br />\nFollow Michael, ex-criminal living his life of leisure away from the past, Franklin, a kid that seeks the better future, and Trevor, the exact past Michael is trying to run away from. <br />\nGTA Online will provide a lot of additional challenge even for the experienced players, coming fresh from the story mode. Now you will have other players around that can help you just as likely as ruin your mission. Every GTA mechanic up to date can be experienced by players through the unique customizable character, and community content paired with the leveling system tends to keep everyone busy and engaged.</p>",
                    "gameGenres": [
                        {
                            "id": 3,
                            "name": "Adventure",
                            "description": null
                        },
                        {
                            "id": 1,
                            "name": "Action",
                            "description": null
                        }
                    ],
                    "publisher": {
                        "id": 21,
                        "name": "1C-SoftClub",
                        "description": "<p>1C-SoftClub is the biggest Russian-based video game publisher, and translator.</p>\n<h3>History</h"
                    },
                    "developers": [
                        {
                            "id": 20,
                            "name": "Rockstar North"
                        }
                    ],
                    "releaseDate": "2013-09-17",
                    "gameKeys": [],
                    "coverUrl": "https://media.rawg.io/media/games/b11/b115b2bc6a5957a917bc7601f4abdda2.jpg",
                    "bestSell": null,
                    "platforms": []
                }
            ],
            "funds": 0.0,
            "birthDateStr": "10/10/1999",
            "startDateStr": "29/05/2020"
        }
        await wrapper.setState({ user: user });

        // Update Cart
        const cart = {
            games: [
                {
                    "id": 27,
                    "gameKey": {
                        "id": 27,
                        "retailer": "admin",
                        "platform": "Steam",
                        "gameName": "Portal 2",
                        "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                        "gameId": 2
                    },
                    "purchased": null,
                    "price": 10.0,
                    "date": "2020-05-29",
                    "userId": 1
                },
                {
                    "id": 28,
                    "gameKey": {
                        "id": 28,
                        "retailer": "admin",
                        "platform": "Steam",
                        "gameName": "Portal 2",
                        "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                        "gameId": 2
                    },
                    "purchased": null,
                    "price": 10.0,
                    "date": "2020-05-29",
                    "userId": 1
                },
            ]
        }
        await wrapper.setState({ cart: cart });

        // Call method
        await instance.buyWithCard();

        // Assertions
        expect(wrapper.state('badCard')).toBeFalsy;
    });
});

describe('Confirm Buy', () => {
    beforeEach(() => {
        fetchMock.restore();
    })

    it('when buying and available then update state', async () => {
        // Setup this.props.route.params.game.id
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<CartScreen />);
        const instance = wrapper.instance();

        // Update user
        const user = {
            "id": 1,
            "username": "admin",
            "name": "Mr eheh uhuh",
            "email": "admin@adeus.com",
            "country": "ola",
            "password": null,
            "birthDate": "09/10/1999",
            "startDate": "28/05/2020",
            "admin": true,
            "photoUrl": null,
            "description": null,
            "creditCardNumber": null,
            "creditCardCSC": null,
            "creditCardOwner": null,
            "creditCardExpirationDate": null,
            "reviewGames": [],
            "reviewedUsers": [],
            "reviewUsers": [],
            "reportsOnGameReview": [],
            "reportsOnUserReview": [],
            "reportsThisUser": [],
            "reportsOnUser": [],
            "buys": [
                {
                    "id": 5,
                    "date": "2020-05-29",
                    "sellId": 4,
                    "gamerKey": "3tayk0lbDu",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "sellerId": 1,
                    "sellerName": "admin",
                    "gameId": 2,
                    "userId": 1
                }
            ],
            "wishList": [
                {
                    "id": 1,
                    "name": "Grand Theft Auto V",
                    "description": "<p>Rockstar Games went bigger, since their previous installment of the series. You get the complicated and realistic world-building from Liberty City of GTA4 in the setting of lively and diverse Los Santos, from an old fan favorite GTA San Andreas. 561 different vehicles (including every transport you can operate) and the amount is rising with every update. <br />\nSimultaneous storytelling from three unique perspectives: <br />\nFollow Michael, ex-criminal living his life of leisure away from the past, Franklin, a kid that seeks the better future, and Trevor, the exact past Michael is trying to run away from. <br />\nGTA Online will provide a lot of additional challenge even for the experienced players, coming fresh from the story mode. Now you will have other players around that can help you just as likely as ruin your mission. Every GTA mechanic up to date can be experienced by players through the unique customizable character, and community content paired with the leveling system tends to keep everyone busy and engaged.</p>",
                    "gameGenres": [
                        {
                            "id": 3,
                            "name": "Adventure",
                            "description": null
                        },
                        {
                            "id": 1,
                            "name": "Action",
                            "description": null
                        }
                    ],
                    "publisher": {
                        "id": 21,
                        "name": "1C-SoftClub",
                        "description": "<p>1C-SoftClub is the biggest Russian-based video game publisher, and translator.</p>\n<h3>History</h"
                    },
                    "developers": [
                        {
                            "id": 20,
                            "name": "Rockstar North"
                        }
                    ],
                    "releaseDate": "2013-09-17",
                    "gameKeys": [],
                    "coverUrl": "https://media.rawg.io/media/games/b11/b115b2bc6a5957a917bc7601f4abdda2.jpg",
                    "bestSell": null,
                    "platforms": []
                }
            ],
            "funds": 0.0,
            "birthDateStr": "10/10/1999",
            "startDateStr": "29/05/2020"
        }
        await wrapper.setState({ user: user });

        // Update Cart
        const cart = {
            games: [
                {
                    "id": 27,
                    "gameKey": {
                        "id": 27,
                        "retailer": "admin",
                        "platform": "Steam",
                        "gameName": "Portal 2",
                        "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                        "gameId": 2
                    },
                    "purchased": null,
                    "price": 10.0,
                    "date": "2020-05-29",
                    "userId": 1
                },
                {
                    "id": 28,
                    "gameKey": {
                        "id": 28,
                        "retailer": "admin",
                        "platform": "Steam",
                        "gameName": "Portal 2",
                        "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                        "gameId": 2
                    },
                    "purchased": null,
                    "price": 10.0,
                    "date": "2020-05-29",
                    "userId": 1
                },
            ]
        }
        await wrapper.setState({ cart: cart });


        // Mocking
        const url = baseURL + "grid/buy-listing"
        const mockResponse = [{"key": "top"}]

        fetchMock.post(url, mockResponse);

        // Call method
        await instance.confirmBuy(true);

        // Assertions
        expect(wrapper.state('boughtKeys')).toEqual(mockResponse);
    });

    it('when buying and account invalid then redirect', async () => {
        // Setup this.props.route.params.game.id
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<CartScreen />);
        const instance = wrapper.instance();

        // Update user
        const user = {
            "id": 1,
            "username": "admin",
            "name": "Mr eheh uhuh",
            "email": "admin@adeus.com",
            "country": "ola",
            "password": null,
            "birthDate": "09/10/1999",
            "startDate": "28/05/2020",
            "admin": true,
            "photoUrl": null,
            "description": null,
            "creditCardNumber": null,
            "creditCardCSC": null,
            "creditCardOwner": null,
            "creditCardExpirationDate": null,
            "reviewGames": [],
            "reviewedUsers": [],
            "reviewUsers": [],
            "reportsOnGameReview": [],
            "reportsOnUserReview": [],
            "reportsThisUser": [],
            "reportsOnUser": [],
            "buys": [
                {
                    "id": 5,
                    "date": "2020-05-29",
                    "sellId": 4,
                    "gamerKey": "3tayk0lbDu",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "sellerId": 1,
                    "sellerName": "admin",
                    "gameId": 2,
                    "userId": 1
                }
            ],
            "wishList": [
                {
                    "id": 1,
                    "name": "Grand Theft Auto V",
                    "description": "<p>Rockstar Games went bigger, since their previous installment of the series. You get the complicated and realistic world-building from Liberty City of GTA4 in the setting of lively and diverse Los Santos, from an old fan favorite GTA San Andreas. 561 different vehicles (including every transport you can operate) and the amount is rising with every update. <br />\nSimultaneous storytelling from three unique perspectives: <br />\nFollow Michael, ex-criminal living his life of leisure away from the past, Franklin, a kid that seeks the better future, and Trevor, the exact past Michael is trying to run away from. <br />\nGTA Online will provide a lot of additional challenge even for the experienced players, coming fresh from the story mode. Now you will have other players around that can help you just as likely as ruin your mission. Every GTA mechanic up to date can be experienced by players through the unique customizable character, and community content paired with the leveling system tends to keep everyone busy and engaged.</p>",
                    "gameGenres": [
                        {
                            "id": 3,
                            "name": "Adventure",
                            "description": null
                        },
                        {
                            "id": 1,
                            "name": "Action",
                            "description": null
                        }
                    ],
                    "publisher": {
                        "id": 21,
                        "name": "1C-SoftClub",
                        "description": "<p>1C-SoftClub is the biggest Russian-based video game publisher, and translator.</p>\n<h3>History</h"
                    },
                    "developers": [
                        {
                            "id": 20,
                            "name": "Rockstar North"
                        }
                    ],
                    "releaseDate": "2013-09-17",
                    "gameKeys": [],
                    "coverUrl": "https://media.rawg.io/media/games/b11/b115b2bc6a5957a917bc7601f4abdda2.jpg",
                    "bestSell": null,
                    "platforms": []
                }
            ],
            "funds": 0.0,
            "birthDateStr": "10/10/1999",
            "startDateStr": "29/05/2020"
        }
        await wrapper.setState({ user: user });

        // Update Cart
        const cart = {
            games: [
                {
                    "id": 27,
                    "gameKey": {
                        "id": 27,
                        "retailer": "admin",
                        "platform": "Steam",
                        "gameName": "Portal 2",
                        "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                        "gameId": 2
                    },
                    "purchased": null,
                    "price": 10.0,
                    "date": "2020-05-29",
                    "userId": 1
                },
                {
                    "id": 28,
                    "gameKey": {
                        "id": 28,
                        "retailer": "admin",
                        "platform": "Steam",
                        "gameName": "Portal 2",
                        "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                        "gameId": 2
                    },
                    "purchased": null,
                    "price": 10.0,
                    "date": "2020-05-29",
                    "userId": 1
                },
            ]
        }
        await wrapper.setState({ cart: cart });


        // Mocking
        const url = baseURL + "grid/buy-listing"
        const mockResponse = 401

        fetchMock.post(url, mockResponse);

        // Call method
        await instance.confirmBuy(true);

        // Assertions
        expect(wrapper.state('redirectLogin')).toBeTruthy;
    });

    it('when buying and not available then show error', async () => {
        // Setup this.props.route.params.game.id
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<CartScreen />);
        const instance = wrapper.instance();

        // Update user
        const user = {
            "id": 1,
            "username": "admin",
            "name": "Mr eheh uhuh",
            "email": "admin@adeus.com",
            "country": "ola",
            "password": null,
            "birthDate": "09/10/1999",
            "startDate": "28/05/2020",
            "admin": true,
            "photoUrl": null,
            "description": null,
            "creditCardNumber": null,
            "creditCardCSC": null,
            "creditCardOwner": null,
            "creditCardExpirationDate": null,
            "reviewGames": [],
            "reviewedUsers": [],
            "reviewUsers": [],
            "reportsOnGameReview": [],
            "reportsOnUserReview": [],
            "reportsThisUser": [],
            "reportsOnUser": [],
            "buys": [
                {
                    "id": 5,
                    "date": "2020-05-29",
                    "sellId": 4,
                    "gamerKey": "3tayk0lbDu",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "sellerId": 1,
                    "sellerName": "admin",
                    "gameId": 2,
                    "userId": 1
                }
            ],
            "wishList": [
                {
                    "id": 1,
                    "name": "Grand Theft Auto V",
                    "description": "<p>Rockstar Games went bigger, since their previous installment of the series. You get the complicated and realistic world-building from Liberty City of GTA4 in the setting of lively and diverse Los Santos, from an old fan favorite GTA San Andreas. 561 different vehicles (including every transport you can operate) and the amount is rising with every update. <br />\nSimultaneous storytelling from three unique perspectives: <br />\nFollow Michael, ex-criminal living his life of leisure away from the past, Franklin, a kid that seeks the better future, and Trevor, the exact past Michael is trying to run away from. <br />\nGTA Online will provide a lot of additional challenge even for the experienced players, coming fresh from the story mode. Now you will have other players around that can help you just as likely as ruin your mission. Every GTA mechanic up to date can be experienced by players through the unique customizable character, and community content paired with the leveling system tends to keep everyone busy and engaged.</p>",
                    "gameGenres": [
                        {
                            "id": 3,
                            "name": "Adventure",
                            "description": null
                        },
                        {
                            "id": 1,
                            "name": "Action",
                            "description": null
                        }
                    ],
                    "publisher": {
                        "id": 21,
                        "name": "1C-SoftClub",
                        "description": "<p>1C-SoftClub is the biggest Russian-based video game publisher, and translator.</p>\n<h3>History</h"
                    },
                    "developers": [
                        {
                            "id": 20,
                            "name": "Rockstar North"
                        }
                    ],
                    "releaseDate": "2013-09-17",
                    "gameKeys": [],
                    "coverUrl": "https://media.rawg.io/media/games/b11/b115b2bc6a5957a917bc7601f4abdda2.jpg",
                    "bestSell": null,
                    "platforms": []
                }
            ],
            "funds": 0.0,
            "birthDateStr": "10/10/1999",
            "startDateStr": "29/05/2020"
        }
        await wrapper.setState({ user: user });

        // Update Cart
        const cart = {
            games: [
                {
                    "id": 27,
                    "gameKey": {
                        "id": 27,
                        "retailer": "admin",
                        "platform": "Steam",
                        "gameName": "Portal 2",
                        "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                        "gameId": 2
                    },
                    "purchased": null,
                    "price": 10.0,
                    "date": "2020-05-29",
                    "userId": 1
                },
                {
                    "id": 28,
                    "gameKey": {
                        "id": 28,
                        "retailer": "admin",
                        "platform": "Steam",
                        "gameName": "Portal 2",
                        "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                        "gameId": 2
                    },
                    "purchased": null,
                    "price": 10.0,
                    "date": "2020-05-29",
                    "userId": 1
                },
            ]
        }
        await wrapper.setState({ cart: cart });


        // Mocking
        const url = baseURL + "grid/buy-listing"
        const mockResponse = 404

        fetchMock.post(url, mockResponse);

        // Call method
        await instance.confirmBuy(true);

        // Assertions
        expect(wrapper.state('badUnavailable')).toBeTruthy;
    });
});


