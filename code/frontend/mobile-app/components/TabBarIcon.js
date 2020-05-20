import { FontAwesome5 } from '@expo/vector-icons';
import * as React from 'react';

import Colors from '../constants/Colors';

export default function TabBarIcon(props) {
  return (
    <FontAwesome5
      name={props.name}
      size={30}
      style={{ marginBottom: -3,
       }}
      color={props.focused ? "rgb(253,27,163)" : Colors.tabIconDefault}
    />
  );
}
