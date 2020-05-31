import { AsyncStorage } from 'react-native';

var loggedUser_temp
try{
    loggedUser_temp = AsyncStorage.getItem('loggedUser');
}catch{
    loggedUser_temp = null
}

var cart_temp
try{
    cart_temp = AsyncStorage.getItem('cart');
}catch{
    cart_temp = null
}
const global = {user: loggedUser_temp, cart: cart_temp}
export default global
