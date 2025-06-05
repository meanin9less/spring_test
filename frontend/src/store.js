

import {createSlice, configureStore, combineReducers} from "@reduxjs/toolkit";
import { persistReducer, persistStore } from "redux-persist";

import storage from "redux-persist/lib/storage";


const boardSlice=createSlice({
    name:"board",
    initialState:{
        accessToken:null,
        login:null,         
        posts:[],
    },
    reducers:{
        setAccessToken:(state, action)=>{
            state.accessToken = action.payload;
        },
        setLogin:(state, action)=>{
            state.login = action.payload;
        },
        setPosts:(state, action)=>{
            state.posts = action.payload;
        }
    }
});

const persistConfig = {
    key:"root",
    version:2, 
    storage,
    whitelist:['board'], // 슬라이스 개수만큼 배열에 추가
};

const rootReducer = combineReducers({
    board:boardSlice.reducer,
    // 슬라이스 만큼 리듀서 추가가능
});

const persistedReducer = persistReducer(persistConfig, rootReducer);

export const store=configureStore({ //스토어 준비
    reducer: persistedReducer,
});


export const persistor = persistStore(store); // persistor 스토어로 실제 활동하는 놈

export const { setLogin, setAccessToken, setPosts}=boardSlice.actions;