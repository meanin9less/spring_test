
import React from 'react'
import { useSelector } from 'react-redux'
import apiClient from '../api/apiClient';
import { Link, useNavigate } from 'react-router-dom';

const NewPost = () => {

    const login = useSelector(state=>state.board.login);
    const navigate =useNavigate();
    const submitHandler = async (e)=>{
        e.preventDefault();
        try{
            const res = await apiClient.post("/board/newpost",{
                username:login.username,
                name:login.username,
                title:e.target.title.value,
                text:e.target.text.value,
            });
            navigate("/");
            console.log(res.data);
        }catch(err){
            console.log(err);
        }
    }

return (
<div>
    <h3>NewPost</h3>
    <hr></hr>
    <form onSubmit={submitHandler}>
        <div>
            <input type='text' name='title' placeholder='Title' style={{ width: "400px" }}></input>
            <br></br>
            <br></br>
            <textarea name='text' style={{ width: "400px", height: "300px", overflow:'auto' }}></textarea>
            <br></br>
            <br></br>
            <button type='submit'>Post</button>&nbsp;|&nbsp;<Link to={"/"}>Back</Link>
        </div>
    </form>
</div>
)
}

export default NewPost