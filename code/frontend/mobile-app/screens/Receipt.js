import React from 'react';
import { StyleSheet, Dimensions, ScrollView, Image, View } from 'react-native';
import { Button, Block, Text, Input, theme } from 'galio-framework';

import { Icon, Product, ProductBought } from '../components/';

const { width } = Dimensions.get('screen');

// Global Variables
import baseURL from '../constants/baseURL'
import global from "../constants/global";
import { AsyncStorage } from 'react-native';

export default class ReceiptScreen extends React.Component {
  state = {
    doneLoading: false,
    game: null,
    error: false,

    redirectLogin: false,
    redirectGames: false,
  }

  setUser = async (value) => {
    var value = await AsyncStorage.setItem('loggedUser', value)
  }

  setCart = async (value) => {
    var value = await AsyncStorage.setItem('cart', value)
    return value
  }


  getCart = async () => {
    var value = await AsyncStorage.getItem('cart')
    return value
  }



  async load() {
    await this.setState({
      doneLoading: true
    })
  }

  async componentDidMount() {
    await this.load()
  }


  renderSells = () => {
    var games = []


    var bought = this.props.route.params.games
    if (bought == null || bought == 0) {
      games = <Block flex left style={[styles.gameTitle3]}>
        <Text size={18} >Wait, did you actually buy something?</Text>
      </Block>
    } else {
      games.push(<Block flex left style={[styles.gameTitle3]}>
        <Text size={25} style={styles.hr}>Here's your games. <Text color="#f44336">GLHF</Text></Text>
      </Block>
      )

      for (var i = 0; i < bought.length; i++) {
        var tempKey = bought[i]

        console.log(tempKey)
       

        games.push(<Block flex row style={[styles.gameTitle2]}>
          <ProductBought product={tempKey}/>
        </Block>)
      }
    }


    return (
      <View>
        {games}
      </View>
    )
  }


  render() {
    if (this.state.error) {
      return (
        <Block flex center style={styles.loading}>
          <Text size={20}>Sorry, an unexpected error has occured whilst retreiving your receipt...</Text>
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
