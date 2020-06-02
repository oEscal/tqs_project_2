import React from 'react';
import ProfileScreen from '../screens/Profile';

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

    it('when getting profile then update info state', async () => {
        const wrapper = shallow(<ProfileScreen />);
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

        // Mocking
        const url = baseURL + "grid/private/user-info?username=" + user.username
        fetchMock.get(url, user);

        await instance.getUserInfo()

        // Assertions
        expect(wrapper.state('info')).toEqual(user);
        expect(wrapper.state('redirectLogin')).toBeFalsy();
    })

    it('when getting profile and error then update error state', async () => {
        const wrapper = shallow(<ProfileScreen />);
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

        // Mocking
        const url = baseURL + "grid/private/user-info?username=" + user.username
        fetchMock.get(url, 404);

        await instance.getUserInfo()

        // Assertions
        expect(wrapper.state('error')).toBeTruthy();
    })

    it('when getting profile and wrong token error then update error state', async () => {
        const wrapper = shallow(<ProfileScreen />);
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

        // Mocking
        const url = baseURL + "grid/private/user-info?username=" + user.username
        fetchMock.get(url, 401);

        await instance.getUserInfo()

        // Assertions
        expect(wrapper.state('redirectLogin')).toBeTruthy();
    })

    it('when getting profile and user is null then redirect to login', async () => {
        const wrapper = shallow(<ProfileScreen />);
        const instance = wrapper.instance();

        // Update user
        const user = null
        await wrapper.setState({ user: user });

        await instance.getUserInfo()

        // Assertions
        expect(wrapper.state('redirectLogin')).toBeTruthy();
    })
});