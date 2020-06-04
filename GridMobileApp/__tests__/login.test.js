import React from 'react';
import Onboarding from '../screens/Onboarding';

import Enzyme from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import fetchMock from 'fetch-mock';

import baseURL from '../constants/baseURL'
import global from "../constants/global";
import { AsyncStorage } from 'react-native';

Enzyme.configure({ adapter: new Adapter() });

describe('Fetching Data', () => {
    beforeEach(() => {
        fetchMock.restore();
    })

    it('when logging in without inputting password and username then dont load', async () => {
        const wrapper = shallow(<Onboarding />);
        const instance = wrapper.instance();

        // Call method
        await instance.login();

        // Assertions
        expect(wrapper.state('processing')).toBeFalsy();
    });

    it('when logging in wrong user credentials, state error', async () => {
        // Setup
        const wrapper = shallow(<Onboarding />);
        const instance = wrapper.instance();

        wrapper.setState({ 'username': 'oof', 'password': 'oof' })

        // Mocking
        const url = baseURL + "grid/login";

        const mockResponse = 401

        fetchMock.post(url, mockResponse);

        // Call method
        await instance.login();

        // Assertions
        expect(wrapper.state('bad')).toBeTruthy();
    });

    it('when error occurs, state error', async () => {
        // Setup
        const wrapper = shallow(<Onboarding />);
        const instance = wrapper.instance();

        wrapper.setState({ 'username': 'oof', 'password': 'oof' })

        // Mocking
        const url = baseURL + "grid/login";

        const mockResponse = 500

        fetchMock.post(url, mockResponse);

        // Call method
        await instance.login();

        // Assertions
        expect(wrapper.state('error')).toBeTruthy();
    });

});