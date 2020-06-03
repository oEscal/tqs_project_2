import React from 'react';
import { withNavigation } from '@react-navigation/compat';
import { StyleSheet, Dimensions, Image, TouchableWithoutFeedback } from 'react-native';
import { Block, Text, theme, Button, Icon } from 'galio-framework';

import materialTheme from '../constants/Theme';
import global from "../constants/global";
import { AsyncStorage } from 'react-native';

const { width } = Dimensions.get('screen');

class ProductKey extends React.Component {
  state = {
    inCart: false
  }

  async componentDidMount() {
    await this.setState({ inCart: !this.props.product.cart })
  }

  setCart = async (value) => {
    var value = await AsyncStorage.setItem('cart', value)
    return value
  }

  getCart = async () => {
    var value = await AsyncStorage.getItem('cart')
    return value
  }


  async addToCart(game) {
    var cart = []
    if (global.cart != null) {
      cart = global.cart.games
    }

    cart.push(game)

    await this.setCart(JSON.stringify({ "games": cart }))

    global.cart = JSON.parse(await this.getCart())

    await this.setState({ inCart: true })
  }

  async removeFromCart(game) {
    var cart = []
    if (global.cart != null) {
      for (var i = 0; i < global.cart.games.length; i++) {
        var foundGame = global.cart.games[i]
        if (game.id != foundGame.id) {
          cart.push(foundGame)
        }
      }
    }

    await this.setCart(JSON.stringify({ "games": cart }))
    global.cart = JSON.parse(await this.getCart())

    await this.setState({ inCart: false })
  }

  render() {
    const { navigation, product, horizontal, full, style, priceColor, imageStyle, page, showButtons } = this.props;
    const imageStyles = [styles.image, full ? styles.fullImage : styles.horizontalImage, imageStyle];

    var pageName = page
    if (pageName == null) {
      pageName = "GameInfo"
    }

    var button = null
    if(showButtons == null || showButtons){
      if (!this.state.inCart) {
        button = <Button
          shadowless
          style={styles.button}
          color={materialTheme.COLORS.BUTTON_COLOR}
          onPress={() => this.addToCart(product)}>
          <Text color={"#fff"}>
            <Icon size={16} name="shopping-cart" family="Feather" style={{ color: "#fff", paddingRight: 8 }} />
          Add to Cart
        </Text>
        </Button>
      } else {
        button = <Button
          shadowless
          style={styles.button}
          color={materialTheme.COLORS.ERROR}
          onPress={() => this.removeFromCart(product)}>
          <Text color={"#fff"}>
            <Icon size={16} name="shopping-cart" family="Feather" style={{ color: "#fff", paddingRight: 8 }} />
          Remove Cart
        </Text>
        </Button>
      }
    }
   
    return (
      <Block row={horizontal} card flex style={[styles.product, styles.shadow, style]}>
        <TouchableWithoutFeedback>
          <Block flex style={[styles.imageContainer, styles.shadow]}>
            <Image source={require('../assets/img/default_user.png')} style={imageStyles} />
          </Block>
        </TouchableWithoutFeedback>
        <TouchableWithoutFeedback>
          <Block flex space="between" style={styles.productDescription}>
            <Text size={14} style={styles.productTitle}>Seller: {product.gameKey.retailer}</Text>
            <Text size={10} muted={!priceColor} color={priceColor}>Grid Score:  {product.score}</Text>
            <Text size={14} style={styles.productTitle}>for <Text color={"#f44336"}>{product.gameKey.platform}</Text></Text>
            <Text size={18} color={"#f44336"}>{product.price}€</Text>
          </Block>
        </TouchableWithoutFeedback>
        <TouchableWithoutFeedback>
          <Block center>
            {button}
          </Block>
        </TouchableWithoutFeedback>
      </Block >
    );
  }
}

export default withNavigation(ProductKey);

const styles = StyleSheet.create({
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
  button: {
    width: width / 3,
    height: theme.SIZES.BASE * 2,
    marginBottom: 10,
    shadowRadius: 0,
    shadowOpacity: 0,
  },
});