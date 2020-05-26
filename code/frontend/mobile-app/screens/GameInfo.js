import React from 'react';
import { StyleSheet, Dimensions, ScrollView, Image, View } from 'react-native';
import { Button, Block, Text, Input, theme } from 'galio-framework';

import { Icon, Product, ProductKey } from '../components/';

const { width } = Dimensions.get('screen');

// Global Variables
import baseURL from '../constants/baseURL'
import global from "../constants/global";


export default class GameInfoScreen extends React.Component {
  state = {
    doneLoading: false,
    game: null,
    error: false
  }

  async getGameInfo() {
    var login_info = null
    if (global.user != null) {
      login_info = global.user.token
    }
    
    // Get All Games
    await fetch(baseURL + "grid/game?id=" + this.props.route.params.game.id, {
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
          localStorage.setItem('loggedUser', null);
          global.user = JSON.parse(localStorage.getItem('loggedUser'))

          this.setState({
            redirectLogin: true
          })

        } else {
          var description = data.description
          description = description.substring(3, description.length - 4)

          description = description.replace(/&#39;s/g, "'s")
          description = description.replace(/<p>/g, "\n")
          description = description.replace(/<\/p>/g, "")
          description = description.replace(/<h3>/g, "\n")
          description = description.replace(/<\/h3>/g, "")
          description = description.replace(/<br \/>/g, "\n")

          var minimizedDescription = description
          if (description.length > 315) {
            minimizedDescription = description.substring(0, 312) + "..."
          }
          data.minimizedDescription = minimizedDescription
          data.description = description

          var allDevelopers = ""
          data.developerNames.forEach(developer => {
            allDevelopers += developer + ", "
          })
          allDevelopers = allDevelopers.substring(0, allDevelopers.length - 2)
          data["allDevelopers"] = allDevelopers

          var allGenres = ""
          data.gameGenres.forEach(genre => {
            allGenres += genre.name + ", "
          })
          allGenres = allGenres.substring(0, allGenres.length - 2)
          data["allGenres"] = allGenres

          var allPlatforms = ""
          data.platforms.forEach(platform => {
            allGenres += platform + ", "
          })
          allPlatforms = allPlatforms.substring(0, allPlatforms.length - 2)
          data["allPlatforms"] = allPlatforms

          this.setState({ game: data })
        }
      })
      .catch(error => {
        console.log(error)
        this.setState({ error: true })
      });

  }


  async componentDidMount() {
    await this.getGameInfo()
    await this.setState({
      doneLoading: true
    })
  }

  renderInfo = () => {
    const imageStyles = [styles.image, styles.fullImage];

    return (
      <View>
        <Block flex style={[styles.imageContainer, styles.shadow]}>
          <Image source={{ uri: this.state.game.coverUrl }} style={imageStyles} />
        </Block>

        <Block flex left style={[styles.gameTitle]}>
          <Text size={25} style={styles.hr}>{this.state.game.name}</Text>
        </Block>

        <Block flex left style={[styles.gameTitle]}>
          <Text size={15}>
            <Text size={15} style={styles.description}>Description:</Text><Text size={15}>{this.state.game.minimizedDescription}</Text>
          </Text>
        </Block>

        <Block flex left style={[styles.gameTitle2]}>
          <Text size={20} style={[styles.gameTitle2, styles.description]}>
            <Image source={require('../assets/img/favicon.png')} style={styles.smallImg} /> Grid Score: <Text>5</Text>
          </Text>
        </Block>
      </View>
    )
  }

  renderSells = () => {
    var game = {
      "id": 19,
      "seller": "Jonas Pistolas",
      "sellerId": 1,
      "score": 5,
      "price": 5,
      "platform": "PC"
    }

    var forward = <Button shadowless style={styles.tab} >
      <Block row middle>
        <Text size={16} style={styles.tabTitle} color={"#999"}>Next Page</Text>
        <Icon size={16} name="navigate-next" family="MaterialIcons" style={{ paddingLeft: 8, color: "#999" }} />
      </Block>
    </Button>

    var back = <Button shadowless style={[styles.tab, styles.divider]}>
      <Block row middle>
        <Icon size={16} name="navigate-before" family="MaterialIcons" style={{ paddingRight: 8, color: "#999" }} />
        <Text size={16} style={styles.tabTitle} color={"#999"}>Previous Page</Text>
      </Block>
    </Button>

    return (
      <View>
        <Block flex left style={[styles.gameTitle3]}>
          <Text size={25} style={styles.hr}>Offers</Text>
        </Block>

        <Block flex row style={[styles.gameTitle2]}>
          <ProductKey product={game} style={{ marginRight: theme.SIZES.BASE }} />
          <ProductKey product={game} />
        </Block>
        <Block flex row style={[styles.gameTitle2]}>
          <ProductKey product={game} style={{ marginRight: theme.SIZES.BASE }} />
          <ProductKey product={game} />
        </Block>

        <Block row flex middle>
          {back}
          {forward}
        </Block>

      </View>
    )
  }

  renderAuctions = () => {
    var game = {
      "id": 19,
      "seller": "Jonas Pistolas",
      "sellerId": 1,
      "score": 5,
      "price": 5,
      "platform": "PC"
    }

    var forward = <Button shadowless style={styles.tab} >
      <Block row middle>
        <Text size={16} style={styles.tabTitle} color={"#999"}>Next Page</Text>
        <Icon size={16} name="navigate-next" family="MaterialIcons" style={{ paddingLeft: 8, color: "#999" }} />
      </Block>
    </Button>

    var back = <Button shadowless style={[styles.tab, styles.divider]}>
      <Block row middle>
        <Icon size={16} name="navigate-before" family="MaterialIcons" style={{ paddingRight: 8, color: "#999" }} />
        <Text size={16} style={styles.tabTitle} color={"#999"}>Previous Page</Text>
      </Block>
    </Button>

    return (
      <View>
        <Block flex left style={[styles.gameTitle3]}>
          <Text size={25} style={styles.hr}>Auctions</Text>
        </Block>

        <Block flex row style={[styles.gameTitle2]}>
          <ProductKey product={game} style={{ marginRight: theme.SIZES.BASE }} />
          <ProductKey product={game} />
        </Block>

        <Block row flex middle>
          {back}
          {forward}
        </Block>

      </View>
    )
  }

  renderGameInfo = () => {
    const imageStyles = [styles.image, styles.fullImage];

    return (
      <View>
        <Block flex left style={[styles.gameTitle3]}>
          <Text size={25} style={styles.hr}>Game Details</Text>
        </Block>

        <Block flex left style={[styles.gameTitle]}>
          <Text size={25} style={styles.hr1}>Name</Text>
        </Block>
        <Block flex left style={[styles.gameInfo]}>
          <Text size={20} >{this.state.game.name}</Text>
        </Block>

        <Block flex left style={[styles.gameTitle]}>
          <Text size={25} style={styles.hr2}>Release Date</Text>
        </Block>
        <Block flex left style={[styles.gameInfo]}>
          <Text size={18} >{this.state.game.releaseDate}</Text>
        </Block>

        <Block flex left style={[styles.gameTitle]}>
          <Text size={25} style={styles.hr3}>Description</Text>
        </Block>
        <Block flex left style={[styles.gameInfo]}>
          <Text size={15} >{this.state.game.description}</Text>
        </Block>

        <Block flex left style={[styles.gameTitle]}>
          <Text size={25} style={styles.hr4}>Genres</Text>
        </Block>
        <Block flex left style={[styles.gameInfo]}>
          <Text size={18} >{this.state.game.allGenres}</Text>
        </Block>

        <Block flex left style={[styles.gameTitle]}>
          <Text size={25} style={styles.hr5}>Platforms</Text>
        </Block>
        <Block flex left style={[styles.gameInfo]}>
          <Text size={18} >{this.state.game.allPlatforms}</Text>
        </Block>


        <Block flex left style={[styles.gameTitle]}>
          <Text size={25} style={styles.hr6}>Developers</Text>
        </Block>
        <Block flex left style={[styles.gameInfo]}>
          <Text size={18} >{this.state.game.allDevelopers}</Text>
        </Block>

        <Block flex left style={[styles.gameTitle]}>
          <Text size={25} style={styles.hr7}>Publisher</Text>
        </Block>
        <Block flex left style={[styles.gameInfo]}>
          <Text size={18} >{this.state.game.publisherName}</Text>
        </Block>

      </View>
    )
  }

  render() {
    if (this.state.error) {
      return (
        <Block flex center style={styles.loading}>
          <Text size={20}>Sorry, an unexpected error has occured whilst retreiving this game's data...</Text>
        </Block>
      );
    }

    if (this.state.doneLoading) {
      return (
        <Block flex center style={styles.home}>
          <ScrollView
            showsVerticalScrollIndicator={false}
            contentContainerStyle={styles.products}>
            {this.renderInfo()}
            {this.renderSells()}
            {this.renderAuctions()}
            {this.renderGameInfo()}
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
    width: width,
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
    width: width,
    marginTop: 30,
    fontWeight: "bold",
    color: "#999"
  },
  gameInfoTitle: {
    width: width,
    marginTop: 50,
  },
  gameInfo: {
    width: width,
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
