import React from 'react';
import GameInfoScreen from '../screens/GameInfo';

import Enzyme from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import fetchMock from 'fetch-mock';

import baseURL from '../constants/baseURL'

Enzyme.configure({ adapter: new Adapter() });

describe('Fetching Data', () => {
    it('when getting game info update state', async () => {
        // Setup this.props.route.params.game.id
        var route = { params: { game: { id: 1 } } }
        const wrapper = shallow(<GameInfoScreen route={route} />);
        const instance = wrapper.instance();

        // Mocking
        const url = baseURL + "grid/game?id=" + route.params.game.id
        const mockResponse = {
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
        };

        const processedData = {
            id: 1,
            name: 'Grand Theft Auto V',
            description:
                'Rockstar Games went bigger, since their previous installment of the series. You get the complicated and realistic world-building from Liberty City of GTA4 in the setting of lively and diverse Los Santos, from an old fan favorite GTA San Andreas. 561 different vehicles (including every transport you can operate) and the amount is rising with every update. \n\nSimultaneous storytelling from three unique perspectives: \n\nFollow Michael, ex-criminal living his life of leisure away from the past, Franklin, a kid that seeks the better future, and Trevor, the exact past Michael is trying to run away from. \n\nGTA Online will provide a lot of additional challenge even for the experienced players, coming fresh from the story mode. Now you will have other players around that can help you just as likely as ruin your mission. Every GTA mechanic up to date can be experienced by players through the unique customizable character, and community content paired with the leveling system tends to keep everyone busy and engaged.',
            gameGenres:
                [{ id: 3, name: 'Adventure', description: null, games: [] },
                { id: 1, name: 'Action', description: null, games: [] }],
            releaseDate: '2013-09-17',
            gameKeys: [],
            coverUrl:
                'https://media.rawg.io/media/games/b11/b115b2bc6a5957a917bc7601f4abdda2.jpg',
            lowestPrice: -1,
            platforms: [],
            publisherName: '1C-SoftClub',
            developerNames: ['Rockstar North'],
            minimizedDescription:
                'Rockstar Games went bigger, since their previous installment of the series. You get the complicated and realistic world-building from Liberty City of GTA4 in the setting of lively and diverse Los Santos, from an old fan favorite GTA San Andreas. 561 different vehicles (including every transport you can operate)...',
            allDevelopers: 'Rockstar North',
            allGenres: 'Adventure, Action',
            allPlatforms: ''
        };


        fetchMock.get(url, mockResponse);

        // Call method
        await instance.getGameInfo();

        // Assertions
        expect(wrapper.state('game')).toEqual(processedData);

    });

});