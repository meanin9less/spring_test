import axios from "axios";
import { setAccessToken, store } from "../store";

const apiClient = axios.create({
    baseURL:"http://localhost:8080",
    headers:{
        "Content-Type":"application/json"
    },
    timeout: 3000,
    withCredentials: true
});

apiClient.interceptors.request.use((config) => { // config로 요청객체 들어옴
    if (config.data && config.data instanceof URLSearchParams) {
        config.headers["Content-Type"] = "application/x-www-form-urlencoded";
    }
    const accessToken = store.getState().board.accessToken; // redux에 저장된거 콜백함수에서는 use훅 사용 x 
    if(accessToken){
        config.headers["Authorization"] = accessToken;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

apiClient.interceptors.response.use((response) => response,
    async (error) => {
        const originalRequest = error.config; // 에러가 난 요청
        if(error.response && error.response.status === 456 && !originalRequest._retry) {// response가 없을 수도 있음 , 서버에 도달하지도 못했을경우 // _retry 처음엔 없음 이따 만들어줌, 무한루프 방지
            originalRequest._retry = true;
            try{
                const response = await axios.post("http://localhost:8080/reissue",null,{
                    withCredentials:true // refresh토큰 포함(쿠키에있음)
                });
                const access = response.headers['authorization'];
                store.dispatch(setAccessToken(access));
                console.log("만료된 요청 재시도");
                return apiClient(originalRequest); // 이 요청도 위에서 interceptor해서 스토어에서 방금 dispatch로 변한 새로운 access 토큰을 넣어서 보내줌

            }catch(err){
                console.log("refresh token invalid");
                return Promise.reject(error);
            }
        }
        return Promise.reject(error); // 비동기함수의 리턴 promise ; 
    });

export default apiClient;