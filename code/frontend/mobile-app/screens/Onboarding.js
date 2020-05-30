import React from 'react';
import { ImageBackground, StyleSheet, StatusBar, Dimensions, Platform } from 'react-native';
import { Block, Button, Text, theme, Input } from 'galio-framework';
import { Icon } from '../components/';

const { height, width } = Dimensions.get('screen');

import materialTheme from '../constants/Theme';
import Images from '../constants/Images';

// Global Variables
import baseURL from '../constants/baseURL'
import global from "../constants/global";
import { AsyncStorage } from 'react-native';


export default class Onboarding extends React.Component {
  state = {
    redirectLogin: false,

    processing: false,
    username: "",
    password: "",
    error: false,
    bad: false
  }

  getUser = async () => {
    var value = await AsyncStorage.getItem('loggedUser')
    return value
  }

  getCart = async () => {
    var value = await AsyncStorage.getItem('cart')
    return value
  }

  setUser = async (value) => {
    var value = await AsyncStorage.setItem('loggedUser', value)
  }

  setCart = async (value) => {
    var value = await AsyncStorage.setItem('cart', value)
    return value
  }



  async login() {

    const username = this.state.username;
    const password = this.state.password;

    const login_info = "Basic " + Base64.btoa(username + ":" + password);

    var error = false

    // Check if fields were filled
    if (username == "" || password == "") {
      error = true
    }


    if (!error) {
      await this.setState({
        processing: true
      })

      var success = false

      // Proceed to login
      await fetch(baseURL + "grid/login", {
        method: "POST",
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
          if (data.status === 401) { // Wrong Credentials
            this.setState({
              bad: true
            })
          } else { // Successful Login
            data["token"] = login_info

            this.setUser(JSON.stringify(data))
          
            global.user = this.getUser()

            this.setState({
              bad: false,
              error: false
            })
            success = true
          }
        })
        .catch(error => {
          console.log(error)
          this.setState({
            error: true
          })
        });

      await this.setState({
        processing: false,
        success: success
      })

      const { navigation } = this.props;

      if (success) {
        navigation.navigate("App")
      }
    }
  }

  async componentDidMount() {
  }

  render() {

    var extra = <Block left>
      <Text color="white" size={16}></Text>
    </Block>

    if (this.state.processing) {
      extra = <Block left>
        <Text color="white" size={16}>Loading...</Text>
      </Block>
    } else if (this.state.error) {
      extra = <Block left>
        <Text color="red" size={16}>Sorry, an unexpected error as occurred...</Text>
      </Block>
    } else if (this.state.bad) {
      extra = <Block left>
        <Text color="red" size={16}>Oops, those credentials seem to be incorrect!</Text>
      </Block>
    }

    return (
      <Block flex style={styles.container}>
        <StatusBar barStyle="light-content" />
        <Block flex center>
          <ImageBackground
            source={require('../assets/img/mobile_background.png')}
            style={{ height: height, width: width, zIndex: 1 }}
          />
        </Block>
        <Block flex space="between" style={styles.padded}>
          <Block flex space="around" style={{ zIndex: 2 }}>
            <Block>
              <Block>
                <Text color="white" size={60}></Text>
              </Block>
            </Block>
            <Block center>
              <Input
                right
                color="black"
                style={styles.search}
                placeholder="Username"
                onChangeText={(val) => this.setState({ username: val })}
                iconContent={<Icon size={16} color={theme.COLORS.MUTED} name="user" family="antdesign" />}
              />
              <Input
                right
                color="black"
                style={styles.search}
                placeholder="Password"
                password={true}
                onChangeText={(val) => this.setState({ password: val })}
                iconContent={<Icon size={16} color={theme.COLORS.MUTED} name="lock" family="antdesign" />}
              />
              {extra}

              <Button
                shadowless
                style={styles.button}
                color={materialTheme.COLORS.BUTTON_COLOR}
                onPress={() => this.login()}>
                Login
              </Button>
            </Block>
          </Block>
        </Block>
      </Block>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: theme.COLORS.BLACK,
  },
  padded: {
    paddingHorizontal: theme.SIZES.BASE * 2,
    position: 'relative',
    bottom: theme.SIZES.BASE,
  },
  button: {
    width: width - theme.SIZES.BASE * 4,
    height: theme.SIZES.BASE * 3,
    shadowRadius: 0,
    shadowOpacity: 0,
    marginTop: 20
  },
  search: {
    borderWidth: 1,
    borderRadius: 3,
  },
});

const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';
const Base64 = {
  btoa: (input: string = '') => {
    let str = input;
    let output = '';

    for (let block = 0, charCode, i = 0, map = chars;
      str.charAt(i | 0) || (map = '=', i % 1);
      output += map.charAt(63 & block >> 8 - i % 1 * 8)) {

      charCode = str.charCodeAt(i += 3 / 4);

      if (charCode > 0xFF) {
        throw new Error("'btoa' failed: The string to be encoded contains characters outside of the Latin1 range.");
      }

      block = block << 8 | charCode;
    }

    return output;
  },

  atob: (input: string = '') => {
    let str = input.replace(/=+$/, '');
    let output = '';

    if (str.length % 4 == 1) {
      throw new Error("'atob' failed: The string to be decoded is not correctly encoded.");
    }
    for (let bc = 0, bs = 0, buffer, i = 0;
      buffer = str.charAt(i++);

      ~buffer && (bs = bc % 4 ? bs * 64 + buffer : buffer,
        bc++ % 4) ? output += String.fromCharCode(255 & bs >> (-2 * bc & 6)) : 0
    ) {
      buffer = chars.indexOf(buffer);
    }

    return output;
  }
};