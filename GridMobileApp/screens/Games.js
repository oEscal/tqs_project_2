import React from 'react';
import { StyleSheet, Dimensions, ScrollView } from 'react-native';
import { Button, Block, Text, Input, theme } from 'galio-framework';
import { Icon, Product } from '../components/';
const { width } = Dimensions.get('screen');
import products from '../constants/products';

// Global Variables
import baseURL from '../constants/baseURL'
import global from "../constants/global";
import { AsyncStorage } from 'react-native';

export default class GamesScreen extends React.Component {
  state = {
    redirectLogin: false,

    games: [],
    curPage: 1,
    noPages: 1,
    noGames: 0,

    gamesLoaded: false,
    totalNumberOfPages: 1,
    searchParam: ""
  }

  setUser = async (value) => {
    var value = await AsyncStorage.setItem('loggedUser', value)
  }

  getUser = async () => {
    var value = await AsyncStorage.getItem('loggedUser')
    return JSON.parse(value)
  }


  setCart = async (value) => {
    var value = await AsyncStorage.setItem('cart', value)
    return value
  }


  getCart = async () => {
    var value = await AsyncStorage.getItem('cart')
    return JSON.parse(value)
  }

  async searchGame() {
    if (this.state.searchParam == "") {
      this.allGames()
    } else {
      var login_info = null
      if (global.user != null) {
        login_info = global.user.token
      }

      await this.setState({ gamesLoaded: false })

      // Get All Games
      await fetch(baseURL + "grid/games/name?name=" + this.state.searchParam, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: login_info
        }
      })
        .then(response => {
          if (response.status === 401) {
            return response
          } else if (response.status === 200) {
            return response.json()
          }
          else throw new Error(response.status);
        })
        .then(data => {
          if (data.status === 401) { // Wrong token
            this.setUser(null)
            global.user = this.getUser()

            this.props.navigation.navigate("Onboarding")

            this.setState({
              redirectLogin: true
            })

          } else {
            this.setState({
              noPages: 0,
              totalNumberOfPages: -1,
              games: data
            })
          }
        })
        .catch(error => {
          console.log(error)
        });

      await this.setState({ gamesLoaded: true })
    }
  }

  async allGames() {
    var login_info = null
    if (global.user != null) {
      login_info = global.user.token
    }

    await this.setState({ gamesLoaded: false })

    // Get All Games
    await fetch(baseURL + "grid/games/all?page=" + (this.state.curPage - 1), {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: login_info
      }
    })
      .then(response => {
        if (response.status === 401) {
          return response
        } else if (response.status === 200) {
          return response.json()
        }
        else throw new Error(response.status);
      })
      .then(data => {
        if (data.status === 401) { // Wrong token
          AsyncStorage.setItem('loggedUser', null);
          global.user = JSON.parse(AsyncStorage.getItem('loggedUser'))

          this.setState({
            redirectLogin: true
          })

        } else {
          if (data.first || this.state.totalNumberOfPages == -1) {
            this.setState({
              noPages: data.totalPages,
              noGames: data.totalElements,
              totalNumberOfPages: data.totalPages
            })

          }
          this.setState({ games: data.content })
        }
      })
      .catch(error => {
        console.log(error)
      });

    await this.setState({ gamesLoaded: true })
  }

  changePageForward = async () => {
    var curPage = this.state.curPage
    curPage++
    await this.setState({
      curPage: curPage
    })

    await this.allGames()
  };

  changePageBackward = async () => {
    var curPage = this.state.curPage
    curPage--
    await this.setState({
      curPage: curPage
    })

    await this.allGames()
  };


  async componentDidMount() {
    await this.allGames()
    this.setState({ doneLoading: true })

    this.props.navigation.addListener('focus', async () => {
      await this.allGames()
      this.setState({ doneLoading: true })
    });
  }

  renderSearch = () => {
    return (
      <Input
        right
        color="black"
        style={styles.search}
        placeholder="Looking for something in specific?"
        onChangeText={(search) => this.setState({ searchParam: search })}
        onSubmitEditing={() => this.searchGame()}
        iconContent={<Icon size={16} color={theme.COLORS.MUTED} name="magnifying-glass" family="entypo" />}
      />
    )
  }

  renderProducts = () => {
    var loadedItems = null
    var empty = false

    if (this.state.gamesLoaded) {
      var items = []

      this.state.games.forEach(game => {
        items.push(
          <Product product={game} key={game.id} horizontal />
        )
      })

      if (items.length == 0) {
        empty = true
        items.push(
          <Text size={20}>Sorry, there don't seem to be any games like that in the Grid...</Text>
        )
      }
      loadedItems = items
    } else {
      loadedItems = <Text size={20}>Loading...</Text>
    }

    var pagination = null
    if (this.state.gamesLoaded && !empty && this.state.totalNumberOfPages != -1) {
      var back = null
      var forward = null
      if (this.state.curPage == 1) {
        back = <Button shadowless style={[styles.tab, styles.divider]}>
          <Block id="backPage" row middle>
            <Icon size={16} name="navigate-before" family="MaterialIcons" style={{ paddingRight: 8, color: "#999" }} />
            <Text size={16} style={styles.tabTitle} color={"#999"}>Previous Page</Text>
          </Block>
        </Button>
      } else {
        back = <Button id="backPage" shadowless style={[styles.tab, styles.divider]} onPress={() => this.changePageBackward()}>
          <Block row middle>
            <Icon size={16} name="navigate-before" family="MaterialIcons" style={{ paddingRight: 8, color: "#fd24ac" }} />
            <Text size={16} style={styles.tabTitle} color={"#fd24ac"}>Previous Page</Text>
          </Block>
        </Button>
      }

      if (this.state.curPage == this.state.totalNumberOfPages) {
        forward = <Button id="nextPage" shadowless style={styles.tab} >
          <Block row middle>
            <Text size={16} style={styles.tabTitle} color={"#999"}>Next Page</Text>
            <Icon size={16} name="navigate-next" family="MaterialIcons" style={{ paddingLeft: 8, color: "#999" }} />
          </Block>
        </Button>
      } else {
        forward = <Button id="nextPage" shadowless style={styles.tab} onPress={() => this.changePageForward()}>
          <Block row middle>
            <Text size={16} style={styles.tabTitle} color={"#fd24ac"}>Next Page</Text>
            <Icon size={16} name="navigate-next" family="MaterialIcons" style={{ paddingLeft: 8, color: "#fd24ac" }} />
          </Block>
        </Button>
      }

      pagination = <Block row flex middle>
        {back}
        {forward}
      </Block>
    }


    return (
      <ScrollView
        showsVerticalScrollIndicator={false}
        contentContainerStyle={styles.products}>
        <Block flex>
          {loadedItems}
        </Block>
        {pagination}
      </ScrollView>
    )
  }

  render() {
    return (
      <Block flex center style={styles.home}>
        {this.renderSearch()}
        {this.renderProducts()}
      </Block>
    );
  }
}

const styles = StyleSheet.create({
  home: {
    width: width,
  },
  search: {
    height: 48,
    width: width - 32,
    marginHorizontal: 16,
    borderWidth: 1,
    borderRadius: 3,
  },
  header: {
    backgroundColor: theme.COLORS.WHITE,
    shadowColor: theme.COLORS.BLACK,
    shadowOffset: {
      width: 0,
      height: 2
    },
    shadowRadius: 8,
    shadowOpacity: 0.2,
    elevation: 4,
    zIndex: 2,
  },
  tabs: {
    marginBottom: 24,
    marginTop: 10,
    elevation: 4,
  },
  search: {
    height: 48,
    width: width - 32,
    marginHorizontal: 16,
    borderWidth: 1,
    borderRadius: 3,
  },
  tab: {
    backgroundColor: theme.COLORS.TRANSPARENT,
    width: width * 0.50,
    borderRadius: 0,
    borderWidth: 0,
    height: 24,
    elevation: 0,
  },
  tabTitle: {
    lineHeight: 19,
    fontWeight: '300'
  },
  divider: {
    borderRightWidth: 0.3,
    borderRightColor: theme.COLORS.MUTED,
  },
  products: {
    width: width - theme.SIZES.BASE * 2,
    paddingVertical: theme.SIZES.BASE * 2,
  },
});
