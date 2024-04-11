import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://my-twitter-clone.com/'
});

instance.interceptors.request.use(config => {
    // Generate or retrieve the bearer token dynamically
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
}, error => {
    return Promise.reject(error);
});

export default instance;
