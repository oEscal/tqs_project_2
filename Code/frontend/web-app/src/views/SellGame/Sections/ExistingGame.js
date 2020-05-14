import React, {Component} from "react";
// @material-ui/core components
import {makeStyles} from "@material-ui/core/styles";
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
import {components} from "react-select";

export const colourOptions = [
    {value: 'ocean', label: 'Ocean', color: '#00B8D9', isFixed: true},
    {value: 'blue', label: 'Blue', color: '#0052CC', isDisabled: true},
    {value: 'purple', label: 'Purple', color: '#5243AA'},
    {value: 'red', label: 'Red', color: '#FF5630', isFixed: true},
    {value: 'orange', label: 'Orange', color: '#FF8B00'},
    {value: 'yellow', label: 'Yellow', color: '#FFC400'},
    {value: 'green', label: 'Green', color: '#36B37E'},
    {value: 'forest', label: 'Forest', color: '#00875A'},
    {value: 'slate', label: 'Slate', color: '#253858'},
    {value: 'silver', label: 'Silver', color: '#666666'},
];

export const flavourOptions = [
    {value: 'vanilla', label: 'Vanilla', rating: 'safe'},
    {value: 'chocolate', label: 'Chocolate', rating: 'good'},
    {value: 'strawberry', label: 'Strawberry', rating: 'wild'},
    {value: 'salted-caramel', label: 'Salted Caramel', rating: 'crazy'},
];

export const stateOptions = [
    {value: 'AL', label: 'Alabama'},
    {value: 'AK', label: 'Alaska'},
    {value: 'AS', label: 'American Samoa'},
    {value: 'AZ', label: 'Arizona'},
    {value: 'AR', label: 'Arkansas'},
    {value: 'CA', label: 'California'},
    {value: 'CO', label: 'Colorado'},
    {value: 'CT', label: 'Connecticut'},
    {value: 'DE', label: 'Delaware'},
    {value: 'DC', label: 'District Of Columbia'},
    {value: 'FM', label: 'Federated States Of Micronesia'},
    {value: 'FL', label: 'Florida'},
    {value: 'GA', label: 'Georgia'},
    {value: 'GU', label: 'Guam'},
    {value: 'HI', label: 'Hawaii'},
    {value: 'ID', label: 'Idaho'},
    {value: 'IL', label: 'Illinois'},
    {value: 'IN', label: 'Indiana'},
    {value: 'IA', label: 'Iowa'},
    {value: 'KS', label: 'Kansas'},
    {value: 'KY', label: 'Kentucky'},
    {value: 'LA', label: 'Louisiana'},
    {value: 'ME', label: 'Maine'},
    {value: 'MH', label: 'Marshall Islands'},
    {value: 'MD', label: 'Maryland'},
    {value: 'MA', label: 'Massachusetts'},
    {value: 'MI', label: 'Michigan'},
    {value: 'MN', label: 'Minnesota'},
    {value: 'MS', label: 'Mississippi'},
    {value: 'MO', label: 'Missouri'},
    {value: 'MT', label: 'Montana'},
    {value: 'NE', label: 'Nebraska'},
    {value: 'NV', label: 'Nevada'},
    {value: 'NH', label: 'New Hampshire'},
    {value: 'NJ', label: 'New Jersey'},
    {value: 'NM', label: 'New Mexico'},
    {value: 'NY', label: 'New York'},
    {value: 'NC', label: 'North Carolina'},
    {value: 'ND', label: 'North Dakota'},
    {value: 'MP', label: 'Northern Mariana Islands'},
    {value: 'OH', label: 'Ohio'},
    {value: 'OK', label: 'Oklahoma'},
    {value: 'OR', label: 'Oregon'},
    {value: 'PW', label: 'Palau'},
    {value: 'PA', label: 'Pennsylvania'},
    {value: 'PR', label: 'Puerto Rico'},
    {value: 'RI', label: 'Rhode Island'},
    {value: 'SC', label: 'South Carolina'},
    {value: 'SD', label: 'South Dakota'},
    {value: 'TN', label: 'Tennessee'},
    {value: 'TX', label: 'Texas'},
    {value: 'UT', label: 'Utah'},
    {value: 'VT', label: 'Vermont'},
    {value: 'VI', label: 'Virgin Islands'},
    {value: 'VA', label: 'Virginia'},
    {value: 'WA', label: 'Washington'},
    {value: 'WV', label: 'West Virginia'},
    {value: 'WI', label: 'Wisconsin'},
    {value: 'WY', label: 'Wyoming'},
];

export const optionLength = [
    {value: 1, label: 'general'},
    {
        value: 2,
        label:
            'Evil is the moment when I lack the strength to be true to the Good that compels me.',
    },
    {
        value: 3,
        label:
            "It is now an easy matter to spell out the ethic of a truth: 'Do all that you can to persevere in that which exceeds your perseverance. Persevere in the interruption. Seize in your being that which has seized and broken you.",
    },
];

export const dogOptions = [
    {id: 1, label: 'Chihuahua'},
    {id: 2, label: 'Bulldog'},
    {id: 3, label: 'Dachshund'},
    {id: 4, label: 'Akita'},
];


export const groupedOptions = [
    {
        label: 'Colours',
        options: colourOptions,
    },
    {
        label: 'Flavours',
        options: flavourOptions,
    },
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
            <VideogameAssetIcon className={classes.inputIconsColor}/>
        </components.DropdownIndicator>
    );
};

const colourStyles = {
    control: (styles, state) => ({
        ...styles, backgroundColor: 'white',
        boxShadow: "none",
        '&:hover': {border: '2px solid #9c27b0'},
        border: '1px solid lightgray'

    }),
    option: (styles, {data, isDisabled, isFocused, isSelected}) => {

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

                <div style={{padding: "12px 0"}}>
                    <AsyncSelect

                        cacheOptions
                        loadOptions={loadOptions}
                        defaultOptions
                        placeholder="Select a game..."
                        styles={colourStyles}
                        components={{DropdownIndicator}}
                        noOptionsMessage={() =>
                            <span>Cant find the game you want to sell?
                                &nbsp; &nbsp;<Button color="primary" size="sm"
                                                     round><a href="/sell-new-game" style={{color: "white"}}>Click here to request to add it</a></Button>
                            </span>
                        }
                    />
                </div>

                <div style={{"margin-top": "-25px"}}>
                    <input
                        id="hidden-input"
                        tabIndex={-1}
                        autoComplete="off"
                        style={{opacity: 0, height: 0}}
                        required={false}

                    />
                </div>


                {
                    <label htmlFor="normal-switch" className="switchClass" style={{"padding-top": "20px"}}>
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
                                <EuroSymbolIcon className={classes.inputIconsColor}/>
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
                                <CalendarTodayIcon className={classes.inputIconsColor}/>
                            </InputAdornment>
                        ),

                        autoComplete: "off"
                    }}
                />
                }
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
                                <VpnKeyIcon className={classes.inputIconsColor}/>
                            </InputAdornment>
                        ),
                        autoComplete: "off"
                    }}
                />
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
