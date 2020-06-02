import React from 'react';
import { StyleSheet, Dimensions, ScrollView } from 'react-native';
import { Button, Block, Text, Input, theme } from 'galio-framework';

import { Icon, Product } from '../components/';

const { width } = Dimensions.get('screen');
import products from '../constants/products';

import baseURL from '../constants/baseURL'
import global from "../constants/global";
import { AsyncStorage } from 'react-native';

export default class WishlistScreen extends React.Component {

  state = {
    redirectLogin: false,

    games: [],

    gamesLoaded: false,
    error: false,
    doneLoading: false
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

  async getWishlist() {
    var login_info = null

    global.user = await this.getUser()
    if (global.user != null) {
      login_info = global.user.token
    }

    await this.setState({ gamesLoaded: false })

    // Get All Games
    await fetch(baseURL + "grid/private/user-info?username=" + global.user.username, {
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
          this.setState({ games: data.wishList })
        }
      })
      .catch(error => {
        console.log(error)
        this.setState({ error: true })
      });

    await this.setState({ gamesLoaded: true })
  }

  async componentDidMount() {
    await this.getWishlist();
    this.setState({ doneLoading: true, })

    this.props.navigation.addListener('focus', async () => {
      await this.getWishlist();
      this.setState({ doneLoading: true, })
    });
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
          <Text size={20}>Hmmm, seems like you haven't added any games to your wishlist yet</Text>
        )
      }
      loadedItems = items
    } else {
      loadedItems = <Text size={20}>Loading...</Text>
    }


    return (
      <ScrollView
        showsVerticalScrollIndicator={false}
        contentContainerStyle={styles.products}>
        <Block flex>
          {loadedItems}
        </Block>
      </ScrollView>
    )
  }


  render() {
    return (
      <Block flex center style={styles.home}>
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
