import React from 'react';
import { StyleSheet, Dimensions, ScrollView, Image, View } from 'react-native';
import { Button, Block, Text, Input, theme } from 'galio-framework';

import { Icon, Product, ProductKey } from '../components/';

const { width } = Dimensions.get('screen');

// Global Variables
import baseURL from '../constants/baseURL'
import global from "../constants/global";
import { AsyncStorage } from 'react-native';

import materialTheme from '../constants/Theme';
import ReceiptScreen from './Receipt';

export default class CartScreen extends React.Component {
  state = {
    doneLoading: false,
    error: false,
    processing: false,

    badCard: false,
    badUnavailable: false,
    badFunds: false,

    redirectLogin: false,
    redirectGames: false,

    sellListings: [],

    noSells: 0,

    price: 0

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

  async getGameListings() {
    var login_info = null
    if (global.user != null) {
      login_info = global.user.token
    }

    global.cart = await this.getCart()

    if (global.cart == null) {
      await this.setState({ sellListings: [] })

    } else {
      var price = 0
      for (var i = 0; i < global.cart.games.length; i++) {
        var tempKey = global.cart.games[i]
        price += tempKey.price
      }
      await this.setState({ sellListings: global.cart.games, price: price })

    }


  }

  async load() {
    await this.getGameListings()

    await this.setState({
      doneLoading: true
    })
  }

  async componentDidMount() {
    await this.load()

    this.props.navigation.addListener('focus', async () => {
      await this.load()
    });

  }

  changePageForward = async () => {
    var curPage = this.state.listingsPage
    curPage++
    await this.setState({
      listingsPage: curPage
    })

    await this.getGameListings()
  };

  changePageBackward = async () => {
    var curPage = this.state.listingsPage
    curPage--
    await this.setState({
      listingsPage: curPage
    })

    await this.getGameListings()
  };

  async buyWithWallet() {
    var error = false
    global.user = await this.getUser()

    console.log(global.user)

    if (global.user == null || parseFloat(global.user.funds) < parseFloat(this.state.price)) {
      error = true
      console.log("hi")
      await this.setState({ badFunds: true })
    } else {
      await this.setState({ badFunds: false })
    }

    /*
    if (!error) {
      this.confirmBuy(true)
    }
    */
  }

  async buyWithCard() {
    var error = false

    console.log("hi")
  
    global.user = await this.getUser()


    if (global.user != null) {
      var cardNumber = global.user.creditCardNumber
      var cardName = global.user.creditCardOwner
      var cardCVC = global.user.creditCardCSC
      var expiration = global.user.creditCardExpirationDate

      if (cardNumber == '' || cardCVC == '' || expiration == '' || cardName == '' || cardNumber == null || cardCVC == null || expiration == null || cardName == null) {
        this.setState({badCard: true})
        error = true
      }else{
        this.setState({badCard: false})
      }

    } else {
      this.setState({error: true})
      error = true
    }

    if (!error) {
      this.confirmBuy(false)
    }

  }

  async confirmBuy(withFunds) {
    global.cart = await this.getCart()
    global.user = await this.getUser()
    if (global.cart != null && global.cart.games.length > 0) {
      await this.setState({
        doneLoading: false,
      })

      var gameIds = []
      for (var i = 0; i < global.cart.games.length; i++) {
        gameIds.push(global.cart.games[i].id)
      }

      var user = -1
      if (global.user != null) {
        user = global.user.id
      }

      console.log(gameIds)

      var body = {
        "listingsId": gameIds,
        "userId": user,
        "withFunds": withFunds
      }

      var success = true

      var login_info = null
      global.user = await this.getUser()
      if (global.user != null) {
        login_info = global.user.token
      }


      await fetch(baseURL + "grid/buy-listing", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: login_info
        },
        body: JSON.stringify(body)
      })
        .then(response => {
          if (response.status === 401 || response.status === 404 || response.status === 400) {
            return response
          } else if (response.status === 200) {
            return response.json()
          }
          else throw new Error(response.status);
        })
        .then(data => {
          console.log(data)
          if (data.status === 401) { // Wrong token
            this.setUser(null)
            global.user = this.getUser()

            this.props.navigation.navigate("Onboarding")

            this.setState({
              redirectLogin: true
            })

          } else if (data.status === 404) { // Game not available
            this.setState({badUnavailable: true})

            //update cart
            global.cart = this.getCart()

            success = false

          } else if (data.status === 400) { // No funds
            this.setState({badFunds: true})

            success = false

          } else { // Successful 
            this.setCart(JSON.stringify({ "games": [] }))
            global.cart = this.getCart()

            this.props.navigation.navigate("Receipt", { games: data })

            this.setState({
              boughtKeys: data,
              badFunds: false,
              error: false,
              badCard: false,
              badUnavailable: false
            })
          }
        })
        .catch(error => {
          console.log(error)

        });

      console.log(success)
      await this.setState({
        doneLoading: true,
      })

    }
  }
  

  renderSells = () => {
    var games = []
    var payment = false
    if (this.state.sellListings == null || this.state.sellListings.length == 0) {
      games = <Block flex left style={[styles.gameTitle3]}>
        <Text size={18} >You haven't added any games to your shopping cart... Get shoppin'!</Text>
      </Block>
    } else {
      games.push(<Block flex left style={[styles.gameTitle3]}>
        <Text size={25} style={styles.hr}>Items in Cart</Text>
      </Block>
      )

      payment = true
      for (var i = 0; i < this.state.sellListings.length; i++) {
        var tempBlock = []
        var tempKey = this.state.sellListings[i]
        tempKey["cart"] = false
        tempBlock.push(<ProductKey product={tempKey} showButtons={false} />
        )

        games.push(<Block flex row style={[styles.gameTitle2]}>
          {tempBlock}
        </Block>)
      }

    }

    var extra
    if (this.state.processing) {
      extra = <Block left>
        <Text color="white" size={16}>Loading...</Text>
      </Block>
    } else if (this.state.error) {
      extra = <Block left>
        <Text color="red" size={16}>Sorry, an unexpected error as occurred...</Text>
      </Block>
    } else if (this.state.badFunds) {
      extra = <Block left>
        <Text color="red" size={16}>Oops, seems like you don't have enough funds...</Text>
      </Block>
    } else if (this.state.badCard) {
      extra = <Block left>
        <Text color="red" size={16}>Oops, seems like you don't have a credit card registered on your account...</Text>
      </Block>
    } else if (this.state.badUnavailable) {
      extra = <Block left>
        <Text color="red" size={16}>Oops, one of those games isn't available anymore...be faster next time  ¯\_(ツ)_/¯</Text>
      </Block>
    }



    return (
      <View>
        {games}
        {payment ? <Block flex left style={[styles.gameTitle3]}>
          <Text><Text size={25} style={styles.hr}>Total: </Text> <Text color="#f44336" size={30} style={{ fontWeight: "bold" }}>{this.state.price}€</Text></Text>
        </Block> : null}
        {extra}
        {payment ? this.renderPayment() : null}
      </View>
    )
  }

  renderPayment = () => {
    return (
      <View>
        

        <Block flex center style={{ marginTop: 25 }}>
          <Button
            shadowless
            style={styles.button}
            color={"#ed6f62"}
            onPress={() => this.buyWithWallet()}>
            Buy with Funds
          </Button>
        </Block>
        <Block flex center style={{ marginTop: 25 }}>
          <Button
            shadowless
            style={styles.button}
            color={materialTheme.COLORS.BUTTON_COLOR}
            onPress={() => this.buyWithCard()}>
            Buy with Card
        </Button>
        </Block>
      </View>

    )

  }

  render() {
    if (this.state.error) {
      return (
        <Block flex center style={styles.loading}>
          <Text size={20}>Sorry, an unexpected error has occured whilst retreiving your shopping cart...</Text>
        </Block>
      );
    }

    if (this.state.doneLoading) {
      return (
        <Block flex center style={styles.home}>
          <ScrollView
            showsVerticalScrollIndicator={false}
            contentContainerStyle={styles.products}>
            {this.renderSells()}
          </ScrollView>
        </Block>
      );
    } else {
      return (
        <Block flex center style={styles.loading}>
          <Text size={20}>Loading...</Text>
        </Block>
      );
    }

  }
}

const styles = StyleSheet.create({
  home: {
    width: width,
  },
  loading: {
    width: width,
    marginTop: 100
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

  product: {
    backgroundColor: theme.COLORS.WHITE,
    marginVertical: theme.SIZES.BASE,
    borderWidth: 0,
    minHeight: 114,
  },
  productTitle: {
    flex: 1,
    flexWrap: 'wrap',
    paddingBottom: 6,
  },
  productDescription: {
    padding: theme.SIZES.BASE / 2,
  },
  imageContainer: {
    elevation: 1,
    marginTop: 20
  },
  image: {
    borderRadius: 3,
    marginHorizontal: theme.SIZES.BASE / 2,
    marginTop: -16,
  },
  horizontalImage: {
    height: 122,
    width: 'auto',
  },
  fullImage: {
    height: 215,
    width: width - theme.SIZES.BASE * 3,
  },
  shadow: {
    shadowColor: theme.COLORS.BLACK,
    shadowOffset: { width: 0, height: 2 },
    shadowRadius: 4,
    shadowOpacity: 0.1,
    elevation: 2,
  },
  fullImage: {
    height: 215,
    width: width - theme.SIZES.BASE * 3,
  },
  gameTitle: {
    marginTop: 25,
    fontWeight: "bold",
    color: "#3b3e48"
  },
  gameTitle2: {
    marginTop: 15,
    fontWeight: "bold",
    color: "#3b3e48"
  },
  gameTitle3: {
    marginTop: 20,
    fontWeight: "bold",
    color: "#999"
  },
  gameInfoTitle: {
    marginTop: 50,
  },
  gameInfo: {
    marginTop: 15,
    color: "#3b3e48"
  },
  hr: {
    color: "#999",
    borderBottomColor: '#999',
    borderBottomWidth: 2,
  },
  description: {
    color: "#999"
  },
  smallImg: {
    width: 25,
    height: 25
  },
  hr1: {
    color: "#999",
    borderBottomColor: "#fdf147",
    borderBottomWidth: 2,
  },
  hr2: {
    color: "#999",
    borderBottomColor: "#feec4c",
    borderBottomWidth: 2,
  },
  hr3: {
    color: "#999",
    borderBottomColor: '#f5c758',
    borderBottomWidth: 2,
  },
  hr4: {
    color: "#999",
    borderBottomColor: '#fca963',
    borderBottomWidth: 2,
  },
  hr5: {
    color: "#999",
    borderBottomColor: '#f77a71',
    borderBottomWidth: 2,
  },
  hr6: {
    color: "#999",
    borderBottomColor: '#fc4b8f',
    borderBottomWidth: 2,
  },
  hr7: {
    color: "#999",
    borderBottomColor: '#fc1bbe',
    borderBottomWidth: 2,
  },
});
