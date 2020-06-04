import React, {Component} from 'react';
import {LineChart, Line, CartesianGrid, XAxis, YAxis, Tooltip, ResponsiveContainer, Legend, Brush} from 'recharts';


const data = [
    {
        "name": "1/5/2020",
        "transaction": 4000,
        "sale": 2400,
        "amt": 2400
    },
    {
        "name": "2/5/2020",
        "transaction": 3000,
        "sale": 1398,
        "amt": 2210
    },
    {
        "name": "3/5/2020",
        "transaction": 2000,
        "sale": 9800,
        "amt": 2290
    },
    {
        "name": "4/5/2020",
        "transaction": 2780,
        "sale": 3908,
        "amt": 2000
    },
    {
        "name": "5/5/2020",
        "transaction": 1890,
        "sale": 4800,
        "amt": 2181
    },
    {
        "name": "6/5/2020",
        "transaction": 2390,
        "sale": 3800,
        "amt": 2500
    },
    {
        "name": "7/5/2020",
        "transaction": 3490,
        "sale": 4300,
        "amt": 2100
    }
];

class DashBoard extends Component {
    constructor(props) {
        super(props);
    }


    render() {
        return (
            <div>

                <ResponsiveContainer width="100%" height={350}>
                    <LineChart width={730} height={250} data={data}
                               margin={{top: 5, right: 30, left: 20, bottom: 5}}>
                        <CartesianGrid strokeDasharray="3 3"/>
                        <XAxis dataKey="name"/>
                        <YAxis/>
                        <Tooltip/>
                        <Legend/>
                        <Line name="Number of transactions" type="monotone" dataKey="transaction" stroke="#8884d8"/>
                        <Line name="Sales number" type="monotone" dataKey="sale" stroke="#82ca9d"/>

                        <Brush dataKey="name" height={50} stroke="#1da1f2">
                            <LineChart data={data}>
                                <Line name="Number of transactions" type="monotone" dataKey="transaction"
                                      stroke="#f77737" strokeWidth={3}/>
                                <Line name="Sales number" type="monotone" dataKey="sale" stroke="#63c2de"
                                      strokeWidth={3}/>
                            </LineChart>
                        </Brush>


                    </LineChart>
                </ResponsiveContainer>
            </div>
        )
    }

}


export default DashBoard;