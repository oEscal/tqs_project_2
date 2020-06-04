import { AsyncStorage } from 'react-native';

async function getUser() {
    var value = await AsyncStorage.getItem('loggedUser')
    return value
}

async function getCart() {
    var value = await AsyncStorage.getItem('cart')
    return value
}


var loggedUser_temp
try {
    loggedUser_temp = JSON.parse(getUser());
} catch{
    loggedUser_temp = null
}

var cart_temp
try {
    cart_temp = JSON.parse(getCart());
} catch{
    cart_temp = null
}
const global = { user: loggedUser_temp, cart: cart_temp }
export default global
