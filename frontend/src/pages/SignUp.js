import React from 'react'
import apiClient from '../api/apiClient';
import { useNavigate } from 'react-router-dom';

const SignUp = () => {

    const navigate = useNavigate();

    const submitHandler = async (e)=>{
        e.preventDefault();
        try{
            await apiClient.post("/join", {
                username:"USER@"+e.target.username.value,
                password:e.target.password.value,
                name:e.target.name.value
            });
            alert("회원가입되었습니다.");
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
        <div>
            <label htmlFor='name'>이름 : </label>
            <input id='name' name='name' type='name'></input>
        </div>
        <button>가입</button>
    </form>
</div>
)
}

export default SignUp