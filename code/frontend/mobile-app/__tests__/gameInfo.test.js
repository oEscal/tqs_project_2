import React from 'react';
import GameInfoScreen from '../screens/GameInfo';

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

    it('when getting game info update state', async () => {
        // Setup this.props.route.params.game.id
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<GameInfoScreen route={route} />);
        const instance = wrapper.instance();

        // Mocking
        const url = baseURL + "grid/game?id=" + route.params.game.id
        const mockResponse = {
            "id": 2,
            "name": "Portal 2",
            "description": "<p>Portal 2 is a first-person puzzle game developed by Valve Corporation and released on April 19, 2011 on Steam, PS3 and Xbox 360. It was published by Valve Corporation in digital form and by Electronic Arts in physical form. </p>\n<p>Its plot directly follows the first game&#39;s, taking place in the Half-Life universe. You play as Chell, a test subject in a research facility formerly ran by the company Aperture Science, but taken over by an evil AI that turned upon its creators, GladOS. After defeating GladOS at the end of the first game but failing to escape the facility, Chell is woken up from a stasis chamber by an AI personality core, Wheatley, as the unkempt complex is falling apart. As the two attempt to navigate through the ruins and escape, they stumble upon GladOS, and accidentally re-activate her...</p>\n<p>Portal 2&#39;s core mechanics are very similar to the first game&#39;s ; the player must make their way through several test chambers which involve puzzles. For this purpose, they possess a Portal Gun, a weapon capable of creating teleportation portals on white surfaces. This seemingly simple mechanic and its subtleties coupled with the many different puzzle elements that can appear in puzzles allows the game to be easy to start playing, yet still feature profound gameplay. The sequel adds several new puzzle elements, such as gel that can render surfaces bouncy or allow you to accelerate when running on them.</p>\n<p>The game is often praised for its gameplay, its memorable dialogue and writing and its aesthetic. Both games in the series are responsible for inspiring most puzzle games succeeding them, particularly first-person puzzle games. The series, its characters and even its items such as the portal gun and the companion cube have become a cultural icon within gaming communities.</p>\n<p>Portal 2 also features a co-op mode where two players take on the roles of robots being led through tests by GladOS, as well as an in-depth level editor.</p>",
            "gameGenres": [
                {
                    "id": 9,
                    "name": "Puzzle",
                    "description": null
                },
                {
                    "id": 6,
                    "name": "Shooter",
                    "description": null
                }
            ],
            "publisher": {
                "id": 1,
                "name": "Electronic Arts",
                "description": "<p>Electronic Arts is an American video game publisher based in Redwood City, California.</p>\n<h3>Hi"
            },
            "developers": [
                {
                    "id": 3,
                    "name": "Valve Software"
                }
            ],
            "releaseDate": "2011-04-19",
            "gameKeys": [
                {
                    "id": 18,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 19,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 5,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 26,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 2,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 21,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 17,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 9,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 11,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 42,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 20,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 31,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 4,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 1,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 16,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 23,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 28,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 33,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 8,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 14,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 40,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 13,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 27,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 6,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 32,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 3,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 29,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 41,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 43,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 22,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 30,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 34,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 36,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 12,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 35,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 38,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 39,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 10,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 25,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 24,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 37,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 7,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                },
                {
                    "id": 15,
                    "retailer": "admin",
                    "platform": "Steam",
                    "gameName": "Portal 2",
                    "gamePhoto": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
                    "gameId": 2
                }
            ],
            "coverUrl": "https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg",
            "bestSell": {
                "id": 42,
                "gameKey": {
                    "id": 42,
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
            "platforms": [
                "Steam"
            ]
        };

        const processedData = {
            id: 2,
            name: 'Portal 2',
            description:
                'Portal 2 is a first-person puzzle game developed by Valve Corporation and released on April 19, 2011 on Steam, PS3 and Xbox 360. It was published by Valve Corporation in digital form and by Electronic Arts in physical form. \n\nIts plot directly follows the first game\'s, taking place in the Half-Life universe. You play as Chell, a test subject in a research facility formerly ran by the company Aperture Science, but taken over by an evil AI that turned upon its creators, GladOS. After defeating GladOS at the end of the first game but failing to escape the facility, Chell is woken up from a stasis chamber by an AI personality core, Wheatley, as the unkempt complex is falling apart. As the two attempt to navigate through the ruins and escape, they stumble upon GladOS, and accidentally re-activate her...\n\nPortal 2\'s core mechanics are very similar to the first game\'s ; the player must make their way through several test chambers which involve puzzles. For this purpose, they possess a Portal Gun, a weapon capable of creating teleportation portals on white surfaces. This seemingly simple mechanic and its subtleties coupled with the many different puzzle elements that can appear in puzzles allows the game to be easy to start playing, yet still feature profound gameplay. The sequel adds several new puzzle elements, such as gel that can render surfaces bouncy or allow you to accelerate when running on them.\n\nThe game is often praised for its gameplay, its memorable dialogue and writing and its aesthetic. Both games in the series are responsible for inspiring most puzzle games succeeding them, particularly first-person puzzle games. The series, its characters and even its items such as the portal gun and the companion cube have become a cultural icon within gaming communities.\n\nPortal 2 also features a co-op mode where two players take on the roles of robots being led through tests by GladOS, as well as an in-depth level editor.',
            gameGenres:
                [{ id: 9, name: 'Puzzle', description: null },
                { id: 6, name: 'Shooter', description: null }],
            publisher:
            {
                id: 1,
                name: 'Electronic Arts',
                description:
                    '<p>Electronic Arts is an American video game publisher based in Redwood City, California.</p>\n<h3>Hi'
            },
            developers: [{ id: 3, name: 'Valve Software' }],
            releaseDate: '2011-04-19',
            gameKeys:
                [{
                    id: 18,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 19,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 5,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 26,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 2,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 21,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 17,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 9,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 11,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 42,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 20,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 31,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 4,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 1,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 16,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 23,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 28,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 33,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 8,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 14,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 40,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 13,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 27,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 6,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 32,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 3,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 29,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 41,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 43,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 22,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 30,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 34,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 36,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 12,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 35,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 38,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 39,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 10,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 25,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 24,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 37,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 7,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                {
                    id: 15,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                }],
            coverUrl:
                'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
            bestSell:
            {
                id: 42,
                gameKey:
                {
                    id: 42,
                    retailer: 'admin',
                    platform: 'Steam',
                    gameName: 'Portal 2',
                    gamePhoto:
                        'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                    gameId: 2
                },
                purchased: null,
                price: 10,
                date: '2020-05-29',
                userId: 1
            },
            platforms: ['Steam'],
            minimizedDescription:
                'Portal 2 is a first-person puzzle game developed by Valve Corporation and released on April 19, 2011 on Steam, PS3 and Xbox 360. It was published by Valve Corporation in digital form and by Electronic Arts in physical form. \n\nIts plot directly follows the first game\'s, taking place in the Half-Life universe. Yo...',
            allDevelopers: 'Valve Software',
            allGenres: 'Puzzle, Shooter',
            allPlatforms: ''
        };

        fetchMock.get(url, mockResponse);

        // Call method
        await instance.getGameInfo();

        // Assertions
        expect(wrapper.state('game')).toEqual(processedData);

    });

    it('when getting game listings update state', async () => {
        // Setup this.props.route.params.game.id
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<GameInfoScreen route={route} />);
        const instance = wrapper.instance();

        // Mocking
        const url = baseURL + "grid/sell-listing?gameId=" + route.params.game.id + "&page=0"
        const mockResponse = {
            "content": [
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
                {
                    "id": 29,
                    "gameKey": {
                        "id": 29,
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
                    "id": 30,
                    "gameKey": {
                        "id": 30,
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
                    "id": 31,
                    "gameKey": {
                        "id": 31,
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
                    "id": 32,
                    "gameKey": {
                        "id": 32,
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
                }
            ],
            "pageable": {
                "sort": {
                    "unsorted": true,
                    "sorted": false,
                    "empty": true
                },
                "offset": 0,
                "pageNumber": 0,
                "pageSize": 6,
                "paged": true,
                "unpaged": false
            },
            "totalPages": 3,
            "totalElements": 17,
            "last": false,
            "number": 0,
            "sort": {
                "unsorted": true,
                "sorted": false,
                "empty": true
            },
            "size": 6,
            "first": true,
            "numberOfElements": 6,
            "empty": false
        }

        const processedData = [{
            id: 27,
            gameKey:
            {
                id: 27,
                retailer: 'admin',
                platform: 'Steam',
                gameName: 'Portal 2',
                gamePhoto:
                    'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                gameId: 2
            },
            purchased: null,
            price: 10,
            date: '2020-05-29',
            userId: 1,
            cart: true
        },
        {
            id: 28,
            gameKey:
            {
                id: 28,
                retailer: 'admin',
                platform: 'Steam',
                gameName: 'Portal 2',
                gamePhoto:
                    'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                gameId: 2
            },
            purchased: null,
            price: 10,
            date: '2020-05-29',
            userId: 1,
            cart: true
        },
        {
            id: 29,
            gameKey:
            {
                id: 29,
                retailer: 'admin',
                platform: 'Steam',
                gameName: 'Portal 2',
                gamePhoto:
                    'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                gameId: 2
            },
            purchased: null,
            price: 10,
            date: '2020-05-29',
            userId: 1,
            cart: true
        },
        {
            id: 30,
            gameKey:
            {
                id: 30,
                retailer: 'admin',
                platform: 'Steam',
                gameName: 'Portal 2',
                gamePhoto:
                    'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                gameId: 2
            },
            purchased: null,
            price: 10,
            date: '2020-05-29',
            userId: 1,
            cart: true
        },
        {
            id: 31,
            gameKey:
            {
                id: 31,
                retailer: 'admin',
                platform: 'Steam',
                gameName: 'Portal 2',
                gamePhoto:
                    'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                gameId: 2
            },
            purchased: null,
            price: 10,
            date: '2020-05-29',
            userId: 1,
            cart: true
        },
        {
            id: 32,
            gameKey:
            {
                id: 32,
                retailer: 'admin',
                platform: 'Steam',
                gameName: 'Portal 2',
                gamePhoto:
                    'https://media.rawg.io/media/games/328/3283617cb7d75d67257fc58339188742.jpg',
                gameId: 2
            },
            purchased: null,
            price: 10,
            date: '2020-05-29',
            userId: 1,
            cart: true
        }]

        fetchMock.get(url, mockResponse);

        // Call method
        await instance.getGameListings();

        // Assertions
        expect(wrapper.state('sellListings')).toEqual(processedData);
    })

    it('when add to wishlist finish loading and update state', async () => {
        // Setup this.props.route.params.game.id
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<GameInfoScreen route={route} />);
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
        const url = baseURL + "grid/add-wish-list?game_id=" + route.params.game.id + "&user_id=1"
        fetchMock.post(url, 200);

        // Call method
        await instance.addToWishlist();

        // Assertions
        expect(wrapper.state('doneLoading')).toBeTruthy();
        expect(wrapper.state('redirectLogin')).toBeFalsy();
    })

    it('when bad user redirect login if tries to get game', async () => {
        // Setup this.props.route.params.game.id
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<GameInfoScreen route={route} />);
        const instance = wrapper.instance();

        // Mocking
        const url = baseURL + "grid/game?id=" + route.params.game.id
        const mockResponse = 401

        fetchMock.get(url, mockResponse);

        // Call method
        await instance.getGameInfo();

        // Assertions
        expect(wrapper.state('redirectLogin')).toBeTruthy();
    })

    it('when bad user redirect login if tries to get listings', async () => {
        // Setup this.props.route.params.game.id
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<GameInfoScreen route={route} />);
        const instance = wrapper.instance();

        // Mocking
        const url = baseURL + "grid/sell-listing?gameId=" + route.params.game.id + "&page=0"
        const mockResponse = 401

        fetchMock.get(url, mockResponse);

        // Call method
        await instance.getGameListings();

        // Assertions
        expect(wrapper.state('redirectLogin')).toBeTruthy();

    })

    it('when bad user redirect login if tries to add to wishlist', async () => {
        // Setup this.props.route.params.game.id
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<GameInfoScreen route={route} />);
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
        const url = baseURL + "grid/add-wish-list?game_id=" + route.params.game.id + "&user_id=1"
        const mockResponse = 401

        fetchMock.post(url, mockResponse);

        // Call method
        await instance.addToWishlist();

        // Assertions
        expect(wrapper.state('redirectLogin')).toBeTruthy();

    })
});

describe('Test page increment and decrement directly', () => {
    it('should update the listingsPage state incrementing it by one', () => {
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<GameInfoScreen route={route} />);
        const instance = wrapper.instance();

        expect(wrapper.state('listingsPage')).toBe(1);
        instance.changePageForward();
        expect(wrapper.state('listingsPage')).toBe(2);
    });

    it('should update the listingsPage state decrementing it by one', () => {
        var route = { params: { game: { id: 2 } } }
        const wrapper = shallow(<GameInfoScreen route={route} />);
        const instance = wrapper.instance();

        expect(wrapper.state('listingsPage')).toBe(1);
        instance.changePageBackward();
        expect(wrapper.state('listingsPage')).toBe(0);
    });
});