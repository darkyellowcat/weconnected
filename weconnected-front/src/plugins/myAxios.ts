import axios, {AxiosInstance} from "axios";

const myAxios: AxiosInstance = axios.create({
    baseURL: '',
});

myAxios.defaults.withCredentials = true; // 配置为true

// Add a request interceptor
myAxios.interceptors.request.use(function (config) {
    return config;
}, function (error) {
    return Promise.reject(error);
});

// Add a response interceptor
myAxios.interceptors.response.use(function (response) {
    if (response?.data?.code === 40100) {
        if (!window.location.pathname.includes('/user/login')) {
            const redirectUrl = encodeURIComponent(window.location.href);
            window.location.href = `/user/login?redirect=${redirectUrl}`;
        }
    }
    return response.data;
}, function (error) {
    if (error.response && error.response.status === 401) {
        if (!window.location.pathname.includes('/user/login')) {
            const redirectUrl = encodeURIComponent(window.location.href);
            window.location.href = `/user/login?redirect=${redirectUrl}`;
        }
    }
    return Promise.reject(error);
});

export default myAxios;
