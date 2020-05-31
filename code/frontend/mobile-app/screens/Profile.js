import React from 'react';
import { StyleSheet, Dimensions, ScrollView, Image, ImageBackground, Platform, View } from 'react-native';
import { Block, Text, theme } from 'galio-framework';
import { LinearGradient } from 'expo-linear-gradient';

import { Icon, ProductBought } from '../components';
import { Images, materialTheme } from '../constants';
import { HeaderHeight } from "../constants/utils";

const { width, height } = Dimensions.get('screen');
const thumbMeasure = (width - 48 - 32) / 3;


import baseURL from '../constants/baseURL'
import global from "../constants/global";
import { AsyncStorage } from 'react-native';


export default class Profile extends React.Component {
  state = {
    redirectLogin: false,

    info: null,

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



  async getUserInfo() {
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
          console.log(data)
          this.setState({ info: data })
        }
      })
      .catch(error => {
        console.log(error)
        this.setState({ error: true })
      });

    await this.setState({ gamesLoaded: true })
  }

  async componentDidMount() {
    await this.getUserInfo();
    this.setState({ doneLoading: true, })

    this.props.navigation.addListener('focus', async () => {
      await this.getUserInfo();
      this.setState({ doneLoading: true, })
    });
  }


  render() {
    if (this.state.error) {
      return (
        <Block flex center style={styles.loading}>
          <Text size={20}>Sorry, an unexpected error has occured whilst retreiving your shopping cart...</Text>
        </Block>
      );
    }

    if (!this.state.doneLoading) {
      return (
        <Block flex center style={styles.loading}>
          <Text size={20}>Loading...</Text>
        </Block>
      );
    } else {
      var cardInfo = null

      if (this.state.info == null || this.state.info.creditCardNumber == "" || this.state.info.creditCardNumber == null) {
        cardInfo = <Block flex left style={[styles.gameInfo]}>
          <Text size={18} color="#999" >You haven't registered a Credit Card yet</Text>
        </Block>
      } else {
        cardInfo = []

        cardInfo.push(<Block flex left style={[styles.gameInfo]}>
          <Text size={18} color="#999" >Card Number: <Text size={18} color="#fc996b" style={{ fontWeight: "bold" }}>{this.state.info.creditCardNumber}</Text></Text>
        </Block>)

        cardInfo.push(<Block flex left style={[styles.gameInfo2]}>
          <Text size={18} color="#999" >Card Name: <Text size={18} color="#fc996b" style={{ fontWeight: "bold" }}>{this.state.info.creditCardOwner}</Text></Text>
        </Block>)

        cardInfo.push(<Block flex left style={[styles.gameInfo2]}>
          <Text size={18} color="#999" >CVC: <Text size={18} color="#fc996b" style={{ fontWeight: "bold" }}>{this.state.info.creditCardCSC}</Text></Text>
        </Block>)

        cardInfo.push(<Block flex left style={[styles.gameInfo2]}>
          <Text size={18} color="#999" >Expiration Date: <Text size={18} color="#fc996b" style={{ fontWeight: "bold" }}>{this.state.info.creditCardExpirationDate}</Text></Text>
        </Block>)
      }


      var items = []

      this.state.info.buys.forEach(game => {
        items.push(
          <Block flex row style={{ marginTop: 10, paddingTop: 0 }}>
            <ProductBought product={game} />
          </Block>
        )
      })

      if (items.length == 0) {
        empty = true
        items.push(
          <Block flex left style={[styles.gameInfo]}>
            <Text size={18} color="#999" >You haven't bought any games yet, what are you waiting for?</Text>
          </Block>
        )
      }
      var score = null

      if (this.state.info.score != null && this.state.info.score != -1) {
        score = <Text size={16} color={materialTheme.COLORS.WARNING}>
          {this.state.info.score} <Icon name="shape-star" family="GalioExtra" size={14} />
        </Text>
      }

      var admin = null
      if (this.state.info.admin) {
        admin = <Block middle style={styles.pro}>
          <Text size={13} color="white">Admin</Text>
        </Block>
      }
      return (
        <Block flex style={styles.profile}>
          <Block flex>
            <ImageBackground
              source={require("../assets/img/default_user.png")}
              style={styles.profileContainer}
              imageStyle={styles.profileImage}>
              <Block flex style={styles.profileDetails}>
                <Block style={styles.profileTexts}>
                  <Text color="white" size={28} style={{ paddingBottom: 8 }}>{this.state.info.username}</Text>
                  <Text color="white" size={15} style={{ paddingBottom: 8 }}>({this.state.info.name})</Text>
                  <Block row space="between">
                    <Block row>
                      {admin}
                      {score}
                    </Block>
                    <Block>
                      <Text color={theme.COLORS.MUTED} size={16}>
                        <Icon name="map-marker" family="font-awesome" color={theme.COLORS.MUTED} size={16} />
                        {` `} {this.state.info.country}
                      </Text>
                    </Block>
                  </Block>
                </Block>
                <LinearGradient colors={['rgba(0,0,0,0)', 'rgba(0,0,0,1)']} style={styles.gradient} />
              </Block>
            </ImageBackground>
          </Block>
          <Block flex style={styles.options}>
            <ScrollView showsVerticalScrollIndicator={false} contentContainerStyle={styles.products}>
              <Block row space="between" style={{ padding: theme.SIZES.BASE, }}>
                <Block middle>
                  <Text bold size={12} style={{ marginBottom: 8 }}>{this.state.info.buys.length}</Text>
                  <Text muted size={12}>Games</Text>
                  <Text muted size={12}>Purchased</Text>
                </Block>
                <Block middle>
                  <Text bold size={12} style={{ marginBottom: 8 }}>{this.state.info.sells.length}</Text>
                  <Text muted size={12}>Games put</Text>
                  <Text muted size={12}>for Sale</Text>
                </Block>
                <Block middle>
                  <Text bold size={12} style={{ marginBottom: 8 }}>{this.state.info.auctionsCreated.length}</Text>
                  <Text muted size={12}>Games put</Text>
                  <Text muted size={12}>on Auction</Text>
                </Block>
              </Block>
              <Block row space="between" style={{ padding: theme.SIZES.BASE, }}>
                <Block middle>
                  <Text bold size={12} style={{ marginBottom: 8 }}>{this.state.info.reviewUsers.length}</Text>
                  <Text muted size={12}>Reviewes</Text>
                  <Text muted size={12}>Received</Text>
                </Block>
                <Block middle>
                  <Text bold size={12} style={{ marginBottom: 8 }}>{this.state.info.reviewedUsers.length}</Text>
                  <Text muted size={12}>Users</Text>
                  <Text muted size={12}>Reviewed</Text>
                </Block>
                <Block middle>
                  <Text bold size={12} style={{ marginBottom: 8 }}>{this.state.info.reviewGames.length}</Text>
                  <Text muted size={12}>Games</Text>
                  <Text muted size={12}>Reviewed</Text>
                </Block>
              </Block>

              <Block flex center style={[styles.gameTitle3]}>
                <Text size={25} color="#fb6080">Credit Card Info</Text>
              </Block>

              {cardInfo}

              <Block flex center style={[styles.gameTitle4]}>
                <Text size={25} color="#fb6080">My Keys</Text>
              </Block>

              {items}

              <Block flex center style={[styles.gameTitle4]}>
                <Text size={10} color=""></Text>
              </Block>

            </ScrollView>
          </Block>
        </Block>
      );
    }
  }
}

const styles = StyleSheet.create({
  profile: {
    marginTop: Platform.OS === 'android' ? -HeaderHeight : 0,
    marginBottom: -HeaderHeight * 2,
  },
  profileImage: {
    width: width,
    height: height / 2 + 3,
  },
  profileContainer: {
    width: width,
    height: height / 2,
  },
  profileDetails: {
    paddingTop: theme.SIZES.BASE * 4,
    justifyContent: 'flex-end',
    position: 'relative',
  },
  profileTexts: {
    paddingHorizontal: theme.SIZES.BASE * 2,
    paddingVertical: theme.SIZES.BASE * 2,
    zIndex: 2
  },
  pro: {
    backgroundColor: materialTheme.COLORS.LABEL,
    paddingHorizontal: 6,
    marginRight: theme.SIZES.BASE / 2,
    borderRadius: 4,
    height: 19,
    width: 50,
  },
  seller: {
    marginRight: theme.SIZES.BASE / 2,
  },
  options: {
    position: 'relative',
    marginHorizontal: theme.SIZES.BASE,
    marginTop: -theme.SIZES.BASE * 7,
    borderTopLeftRadius: 13,
    borderTopRightRadius: 13,
    backgroundColor: theme.COLORS.WHITE,
    shadowColor: 'black',
    shadowOffset: { width: 0, height: 0 },
    shadowRadius: 8,
    shadowOpacity: 0.2,
    zIndex: 2,
    paddingBottom: 50,
    height: 1000
  },
  thumb: {
    borderRadius: 4,
    marginVertical: 4,
    alignSelf: 'center',
    width: thumbMeasure,
    height: thumbMeasure
  },
  gradient: {
    zIndex: 1,
    left: 0,
    right: 0,
    bottom: 0,
    height: '30%',
    position: 'absolute',
  },

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
  gameTitle4: {
    marginTop: 60,
    marginBottom: 30,
    fontWeight: "bold",
    color: "#999"
  },
  gameInfoTitle: {
    marginTop: 50,
  },
  gameInfo: {
    marginTop: 50,
    color: "#3b3e48"
  },
  gameInfo2: {
    marginTop: 30,
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
