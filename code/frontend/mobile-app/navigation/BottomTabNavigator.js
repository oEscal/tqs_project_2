import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import * as React from 'react';

import TabBarIcon from '../components/TabBarIcon';
import HomeScreen from '../screens/HomeScreen';
import LinksScreen from '../screens/LinksScreen';

const BottomTab = createBottomTabNavigator();
const INITIAL_ROUTE_NAME = 'Home';

export default function BottomTabNavigator({ navigation, route }) {
  // Set the header title on the parent stack navigator depending on the
  // currently active tab. Learn more in the documentation:
  // https://reactnavigation.org/docs/en/screen-options-resolution.html
  navigation.setOptions({ headerTitle: getHeaderTitle(route) });

  return (
    <BottomTab.Navigator initialRouteName={INITIAL_ROUTE_NAME}>
      <BottomTab.Screen
        name="Sell"
        component={LinksScreen}
        options={{
          title: 'Sell',
          tabBarIcon: ({ focused }) => <TabBarIcon focused={focused} name="money-bill-wave" />,
        }}
      />
      <BottomTab.Screen
        name="Cart"
        component={LinksScreen}
        options={{
          title: 'Cart',
          tabBarIcon: ({ focused }) => <TabBarIcon focused={focused} name="shopping-cart" />,
        }}
      />
      <BottomTab.Screen
        name="Games"
        component={HomeScreen}
        options={{
          title: 'Games',
          tabBarIcon: ({ focused }) => <TabBarIcon focused={focused} name="gamepad" />,
        }}
      />
      <BottomTab.Screen
        name="Wallet"
        component={LinksScreen}
        options={{
          title: 'Wallet',
          tabBarIcon: ({ focused }) => <TabBarIcon focused={focused} name="wallet" />,
        }}
      />
      <BottomTab.Screen
        name="Profile"
        component={LinksScreen}
        options={{
          title: 'Profile',
          tabBarIcon: ({ focused }) => <TabBarIcon focused={focused} name="user" />,
        }}
      />
    </BottomTab.Navigator>
  );
}

function getHeaderTitle(route) {
  const routeName = route.state?.routes[route.state.index]?.name ?? INITIAL_ROUTE_NAME;

  switch (routeName) {
    case 'Home':
      return 'How to get started';
    case 'Links':
      return 'Links to learn more';
  }
}
