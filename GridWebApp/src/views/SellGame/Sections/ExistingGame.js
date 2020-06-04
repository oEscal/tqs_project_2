import React, { Component } from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import InputAdornment from "@material-ui/core/InputAdornment";
import EuroSymbolIcon from '@material-ui/icons/EuroSymbol';
import CalendarTodayIcon from '@material-ui/icons/CalendarToday';
import VpnKeyIcon from '@material-ui/icons/VpnKey';
import VideogameAssetIcon from '@material-ui/icons/VideogameAsset';
import Button from "components/CustomButtons/Button.js";
import CardFooter from "components/Card/CardFooter.js";
import CustomInput from "components/CustomInput/CustomInput.js";
import styles from "assets/jss/material-kit-react/views/loginPage.js";
import "assets/css/existGame.css"
import Switch from "react-switch";
import AsyncSelect from 'react-select/async';
import { components } from "react-select";

export const colourOptions = [
    { value: 'ocean', label: 'Ocean', color: '#00B8D9', isFixed: true },
    { value: 'blue', label: 'Blue', color: '#0052CC', isDisabled: true },
    { value: 'purple', label: 'Purple', color: '#5243AA' },
    { value: 'red', label: 'Red', color: '#FF5630', isFixed: true },
    { value: 'orange', label: 'Orange', color: '#FF8B00' },
    { value: 'yellow', label: 'Yellow', color: '#FFC400' },
    { value: 'green', label: 'Green', color: '#36B37E' },
    { value: 'forest', label: 'Forest', color: '#00875A' },
    { value: 'slate', label: 'Slate', color: '#253858' },
    { value: 'silver', label: 'Silver', color: '#666666' },
];


const filterColors = (inputValue) => {
    return colourOptions.filter(i =>
        i.label.toLowerCase().includes(inputValue.toLowerCase())
    );
};


const loadOptions = (inputValue, callback) => {
    setTimeout(() => {
        callback(filterColors(inputValue));
    }, 1000);
};

const DropdownIndicator = props => {
    const classes = useStyles();
    return (
        <components.DropdownIndicator {...props}>
            <VideogameAssetIcon className={classes.inputIconsColor} />
        </components.DropdownIndicator>
    );
};

const colourStyles = {
    control: (styles, state) => ({
        ...styles, backgroundColor: 'white',
        boxShadow: "none",
        '&:hover': { border: '2px solid #9c27b0' },
        border: '1px solid lightgray'

    }),
    option: (styles, { data, isDisabled, isFocused, isSelected }) => {

        return {
            ...styles,
            backgroundColor: 'white',
            color: 'black',
            cursor: isDisabled ? 'not-allowed' : 'default',
        };
    },
};

const handleInputChange = (newValue) => {
    console.log(newValue.replace(/\W/g, ''));
    document.getElementById("hidden-input").innerText = newValue.replace(/\W/g, '');
};


const useStyles = makeStyles(styles);

export default function ExistingGame(props) {

    const [cardAnimaton, setCardAnimation] = React.useState("cardHidden");
    setTimeout(function () {
        setCardAnimation("");
    }, 700);
    const classes = useStyles();

    const [checked, setChecked] = React.useState(false);

    return (
        <div className={classes[cardAnimaton]}>
            <form className={classes.form}>

                <div style={{ padding: "12px 0" }}>
                    <AsyncSelect

                        cacheOptions
                        loadOptions={loadOptions}
                        defaultOptions
                        placeholder="Select a game..."
                        styles={colourStyles}
                        components={{ DropdownIndicator }}
                        noOptionsMessage={() =>
                            <span>Cant find the game you want to sell?
                                &nbsp; &nbsp;<Button color="primary" size="sm"
                                    round><a href="/sell-new-game" style={{ color: "white" }}>Click here to request to add it</a></Button>
                            </span>
                        }
                    />
                </div>

                <CustomInput
                    labelText="Key"
                    id="key"
                    formControlProps={{
                        fullWidth: true,
                        required: true
                    }}
                    inputProps={{
                        type: "text",
                        endAdornment: (
                            <InputAdornment position="end">
                                <VpnKeyIcon className={classes.inputIconsColor} />
                            </InputAdornment>
                        ),
                        autoComplete: "off"
                    }}
                />

                <div style={{ "margin-top": "-25px" }}>
                    <input
                        id="hidden-input"
                        tabIndex={-1}
                        autoComplete="off"
                        style={{ opacity: 0, height: 0 }}
                        required={false}

                    />
                </div>


                {
                    <label htmlFor="normal-switch" className="switchClass" style={{ "padding-top": "20px" }}>
                        <span>Auction</span>&nbsp;&nbsp;&nbsp;&nbsp;
                        <Switch
                            checked={checked}
                            onChange={setChecked}
                            id="normal-switch"
                            className="react-switch"
                            onColor="#9c27b0"
                        />
                    </label>
                }

                <CustomInput

                    labelText="Starting Price"
                    id="price"
                    formControlProps={{
                        fullWidth: true,
                        required: true,

                    }}
                    inputProps={{
                        type: "number",
                        endAdornment: (
                            <InputAdornment position="end">
                                <EuroSymbolIcon className={classes.inputIconsColor} />
                            </InputAdornment>
                        ),
                        autoComplete: "off"
                    }}

                />

                {checked &&
                    <CustomInput
                        labelText="End Date"
                        id="date"
                        formControlProps={{
                            fullWidth: true,
                            required: true
                        }}
                        inputProps={{
                            defaultValue: new Date().toISOString().substr(0, 10),
                            type: "date",

                            endAdornment: (
                                <InputAdornment position="end">
                                    <CalendarTodayIcon className={classes.inputIconsColor} />
                                </InputAdornment>
                            ),

                            autoComplete: "off"
                        }}
                    />
                }
                
                <CardFooter className={classes.cardFooter}>
                    <Button
                        round
                        color="primary"
                        size="lg"
                        href="https://www.youtube.com/watch?v=dQw4w9WgXcQ&ref=creativetim"
                        target="_blank"
                        rel="noopener noreferrer"
                    >Confirm </Button>
                </CardFooter>
            </form>
        </div>
    );
}
