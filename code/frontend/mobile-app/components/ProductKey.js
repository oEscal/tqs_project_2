import React from 'react';
import { withNavigation } from '@react-navigation/compat';
import { StyleSheet, Dimensions, Image, TouchableWithoutFeedback } from 'react-native';
import { Block, Text, theme, Button, Icon } from 'galio-framework';

import materialTheme from '../constants/Theme';

const { width } = Dimensions.get('screen');

class ProductKey extends React.Component {
  render() {
    const { navigation, product, horizontal, full, style, priceColor, imageStyle, page } = this.props;
    const imageStyles = [styles.image, full ? styles.fullImage : styles.horizontalImage, imageStyle];

    var pageName = page
    if (pageName == null) {
      pageName = "GameInfo"
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
            <Text size={14} style={styles.productTitle}>Seller: {product.seller}</Text>
            <Text size={10} muted={!priceColor} color={priceColor}>Seller's Greed Score:  {product.score}</Text>
            <Text size={18} color={"#f44336"}>{product.price}€</Text>
          </Block>
        </TouchableWithoutFeedback>
        <TouchableWithoutFeedback>
          <Block center>
            <Button
              shadowless
              style={styles.button}
              color={materialTheme.COLORS.BUTTON_COLOR}
              onPress={() => navigation.navigate('App')}>
              <Text color={"#fff"}>
                <Icon size={16} name="shopping-cart" family="Feather" style={{ color: "#fff", paddingRight: 8 }} />
                Add to Cart
              </Text>
            </Button>
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