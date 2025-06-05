import React from 'react'
import apiClient from '../api/apiClient';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setAccessToken, setLogin } from '../store';

const Login = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const submitHandler = async (e)=>{
        e.preventDefault();
        try{
            const res = await apiClient.post("/login", new URLSearchParams({
                username: "USER@" + e.target.username.value,
                password: e.target.password.value
            }));
            dispatch(setAccessToken(res.headers['authorization']));
            dispatch(setLogin({username: res.data['username'], role:res.data['role']}));

            alert("login : "+res.data['username']);
            navigate("/");

        }catch(err){
            console.log(err);
        }
    }


return (
<div>
    <form onSubmit={submitHandler}>
        <div>
            <label htmlFor='username'>아이디 : </label>
            <input id='username' name='username' type='username'></input>
        </div>
        <div>
            <label htmlFor='password'>비밀번호 : </label>
            <input id='password' name='password' type='password'></input>
        </div>
        <button type='submit'>로그인</button>
    </form>
</div>
)
}

export default Login