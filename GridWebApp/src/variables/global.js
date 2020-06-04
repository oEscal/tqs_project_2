var loggedUser_temp
try{
    loggedUser_temp = JSON.parse(localStorage.getItem('loggedUser'));
}catch{
    loggedUser_temp = null
}

var cart_temp
try{
    cart_temp = JSON.parse(localStorage.getItem('cart'));
}catch{
    cart_temp = null
}
const global = {user: loggedUser_temp, cart: cart_temp}
export default global
