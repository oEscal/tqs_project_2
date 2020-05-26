import React from 'react';
import GamesScreen from '../screens/Games';

import Enzyme from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import fetchMock from 'fetch-mock';

import baseURL from '../constants/baseURL'

Enzyme.configure({ adapter: new Adapter() });


describe('Test page increment and decrement directly', () => {
    it('should update the curPage state incrementing it by one', () => {
        const wrapper = Enzyme.shallow(<GamesScreen />);
        const instance = wrapper.instance();

        expect(wrapper.state('curPage')).toBe(1);
        instance.changePageForward();
        expect(wrapper.state('curPage')).toBe(2);
    });

    it('should update the curPage state decrementing it by one', () => {
        const wrapper = Enzyme.shallow(<GamesScreen />);
        const instance = wrapper.instance();

        expect(wrapper.state('curPage')).toBe(1);
        instance.changePageBackward();
        expect(wrapper.state('curPage')).toBe(0);
    });
});


describe('Fetching Data', () => {
    it('when getting all games should fetch json and update state', async () => {
        // Setup
        const wrapper = shallow(<GamesScreen />);
        const instance = wrapper.instance();

        // Mocking
        const url = baseURL + "grid/all?page=" + (wrapper.state('curPage') - 1);
        const mockResponse = {
            "content": [
                {
                    "id": 1,
                    "name": "Grand Theft Auto V",
                    "description": "<p>Rockstar Games went bigger, since their previous installment of the series. You get the complicated and realistic world-building from Liberty City of GTA4 in the setting of lively and diverse Los Santos, from an old fan favorite GTA San Andreas. 561 different vehicles (including every transport you can operate) and the amount is rising with every update. <br />\nSimultaneous storytelling from three unique perspectives: <br />\nFollow Michael, ex-criminal living his life of leisure away from the past, Franklin, a kid that seeks the better future, and Trevor, the exact past Michael is trying to run away from. <br />\nGTA Online will provide a lot of additional challenge even for the experienced players, coming fresh from the story mode. Now you will have other players around that can help you just as likely as ruin your mission. Every GTA mechanic up to date can be experienced by players through the unique customizable character, and community content paired with the leveling system tends to keep everyone busy and engaged.</p>",
                    "gameGenres": [
                        {
                            "id": 3,
                            "name": "Adventure",
                            "description": null,
                            "games": []
                        },
                        {
                            "id": 1,
                            "name": "Action",
                            "description": null,
                            "games": []
                        }
                    ],
                    "releaseDate": "2013-09-17",
                    "gameKeys": [],
                    "coverUrl": "https://media.rawg.io/media/games/b11/b115b2bc6a5957a917bc7601f4abdda2.jpg",
                    "lowestPrice": -1.0,
                    "platforms": [],
                    "publisherName": "1C-SoftClub",
                    "developerNames": [
                        "Rockstar North"
                    ]
                }
            ],
            "pageable": {
                "sort": {
                    "sorted": false,
                    "unsorted": true,
                    "empty": true
                },
                "offset": 0,
                "pageNumber": 0,
                "pageSize": 18,
                "paged": true,
                "unpaged": false
            },
            "totalPages": 1,
            "totalElements": 1,
            "last": false,
            "number": 0,
            "sort": {
                "sorted": false,
                "unsorted": true,
                "empty": true
            },
            "size": 18,
            "first": true,
            "numberOfElements": 18,
            "empty": false

        };


        fetchMock.get(url, mockResponse);

        // Call method
        await instance.allGames();

        // Assertions
        expect(wrapper.state('gamesLoaded')).toBeTruthy();
        expect(wrapper.state('noPages')).toBe(mockResponse.totalPages);
        expect(wrapper.state('noGames')).toBe(mockResponse.totalElements);
        expect(wrapper.state('totalNumberOfPages')).toBe(mockResponse.totalPages);
        expect(wrapper.state('games')).toEqual(mockResponse.content);

    });

    it('when searching by game name should fetch json and update state', async () => {
        // Setup
        const wrapper = shallow(<GamesScreen />);
        const instance = wrapper.instance();

        wrapper.setState({ 'searchParam': 'grand' })

        // Mocking
        const url = baseURL + "grid/name?name=" + wrapper.state('searchParam');
        const mockResponse = [
            {
                "id": 1,
                "name": "Grand Theft Auto V",
                "description": "<p>Rockstar Games went bigger, since their previous installment of the series. You get the complicated and realistic world-building from Liberty City of GTA4 in the setting of lively and diverse Los Santos, from an old fan favorite GTA San Andreas. 561 different vehicles (including every transport you can operate) and the amount is rising with every update. <br />\nSimultaneous storytelling from three unique perspectives: <br />\nFollow Michael, ex-criminal living his life of leisure away from the past, Franklin, a kid that seeks the better future, and Trevor, the exact past Michael is trying to run away from. <br />\nGTA Online will provide a lot of additional challenge even for the experienced players, coming fresh from the story mode. Now you will have other players around that can help you just as likely as ruin your mission. Every GTA mechanic up to date can be experienced by players through the unique customizable character, and community content paired with the leveling system tends to keep everyone busy and engaged.</p>",
                "gameGenres": [
                    {
                        "id": 3,
                        "name": "Adventure",
                        "description": null,
                        "games": []
                    },
                    {
                        "id": 1,
                        "name": "Action",
                        "description": null,
                        "games": []
                    }
                ],
                "releaseDate": "2013-09-17",
                "gameKeys": [],
                "coverUrl": "https://media.rawg.io/media/games/b11/b115b2bc6a5957a917bc7601f4abdda2.jpg",
                "lowestPrice": -1.0,
                "platforms": [],
                "publisherName": "1C-SoftClub",
                "developerNames": [
                    "Rockstar North"
                ]
            },
            {
                "id": 38,
                "name": "Grand Theft Auto: San Andreas",
                "description": "<p>Grand Theft Auto - San Andreas is the seventh entry in the series in the GTA franchise, but only second big title after GTA - Vice City. Setting up in fictional state San Andreas, you follow the story of CJ, a member of one of the multiple gangs in the city. CJ&#39;s family is being attacked in drive shooting which resulted in the death of CJ&#39;s mother, so he returns to home from Liberty City. Meeting the rest of the family at his mom&#39;s funeral, he decides to rebuild the gang and gain control of the state.</p>\n<p>The game makes a brilliant connection with missions and open world actions that you are able to do around the cities. You can steal cars, buy guns, hunt for collectables and do some side quests, while different characters give you specific missions in order to push the plot forward. Streets are filled with people as well as plenty of vehicles to steal. Fictional brands of cars, tanks, bikes are available to be stolen from any place around the city. Armoury contains pistols, rifles, hand-machine guns or a rocket launcher as well as melee weapons giving player freedom in anything he&#39;s doing in GTA.</p>",
                "gameGenres": [
                    {
                        "id": 3,
                        "name": "Adventure",
                        "description": null,
                        "games": []
                    },
                    {
                        "id": 1,
                        "name": "Action",
                        "description": null,
                        "games": []
                    }
                ],
                "releaseDate": "2004-10-26",
                "gameKeys": [],
                "coverUrl": "https://media.rawg.io/media/games/1bb/1bb86c35ffa3eb0d299b01a7c65bf908.jpg",
                "lowestPrice": -1.0,
                "platforms": [],
                "publisherName": "1C-SoftClub",
                "developerNames": [
                    "War Drum Studios",
                    "Rockstar North"
                ]
            }
        ];


        fetchMock.get(url, mockResponse);

        // Call method
        await instance.searchGame();

        // Assertions
        expect(wrapper.state('gamesLoaded')).toBeTruthy();
        expect(wrapper.state('noPages')).toBe(0);
        expect(wrapper.state('totalNumberOfPages')).toBe(-1);
        expect(wrapper.state('games')).toEqual(mockResponse);
    });
});