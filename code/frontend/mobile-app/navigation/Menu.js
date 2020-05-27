import React from "react";
import { TouchableWithoutFeedback, ScrollView, StyleSheet, Image, View } from "react-native";
import { Block, Text, theme } from "galio-framework";
import { useSafeArea } from "react-native-safe-area-context";

import { Icon, Drawer as DrawerCustomItem } from '../components/';
import { Images, materialTheme } from "../constants/";

import { FontAwesome5 } from '@expo/vector-icons';

function CustomDrawerContent({
  drawerPosition,
  navigation,
  profile,
  focused,
  state,
  ...rest
}) {
  const insets = useSafeArea();
  const screens = [
    "Games",
    "Wishlist",
    "Sell",
    "Wallet",
    "Profile",
    "Components"
  ];
  return (
    <Block
      style={styles.container}
      forceInset={{ top: "always", horizontal: "never" }}
    >
      <Block flex={0.25} style={styles.header}>
        <TouchableWithoutFeedback
          onPress={() => navigation.navigate("Profile")}
        >
          <Block style={styles.profile}>
            <Image source={require("../assets/img/default_user.png")} style={styles.avatar} />
            <Text h5 color={"white"}>
              {profile.name}
            </Text>
          </Block>
        </TouchableWithoutFeedback>
        <Block row>
          <Block middle style={styles.pro2}>
            <Text size={16} color="white">
              {"0.0"}{" "}
              <FontAwesome5 name="wallet" size={14} color="white" />
            </Text>
          </Block>
          <Block middle style={styles.pro2}>
            <Text size={16} color={"#ffec4f"}>
              {profile.rating}{" "}
              <Icon name="shape-star" family="GalioExtra" size={14} />
            </Text>
          </Block>


        </Block>
      </Block>

      <Block flex style={{ paddingLeft: 7, paddingRight: 14, paddingTop: 15 }}>
        <View
          contentContainerStyle={[
            {
              paddingTop: insets.top * 0.4,
              paddingLeft: drawerPosition === "left" ? insets.left : 0,
              paddingRight: drawerPosition === "right" ? insets.right : 0
            }
          ]}
          showsVerticalScrollIndicator={false}
        >
          {screens.map((item, index) => {
            return (
              <DrawerCustomItem
                title={item}
                key={index}
                navigation={navigation}
                focused={state.index === index ? true : false}
              />
            );
          })}
        </View>
      </Block>

    </Block>
  );
}


const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  header: {
    backgroundColor: '#430b43',
    paddingHorizontal: 28,
    paddingBottom: theme.SIZES.BASE,
    paddingTop: theme.SIZES.BASE * 2,
    justifyContent: 'center',
  },
  footer: {
    paddingHorizontal: 28,
    justifyContent: 'flex-end'
  },
  profile: {
    marginBottom: theme.SIZES.BASE / 2,
  },
  avatar: {
    height: 40,
    width: 40,
    borderRadius: 20,
    marginBottom: theme.SIZES.BASE,
  },
  pro: {
    backgroundColor: materialTheme.COLORS.LABEL,
    paddingHorizontal: 6,
    marginRight: 8,
    borderRadius: 4,
    height: 19,
    width: 38,
  },
  pro2: {
    paddingHorizontal: 6,
    marginRight: 8,
    borderRadius: 4,
    height: 19,
  },
  seller: {
    marginRight: 16,
  }
});

export default CustomDrawerContent;
