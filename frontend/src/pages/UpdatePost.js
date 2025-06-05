import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import apiClient from '../api/apiClient';
import { setPosts } from '../store';

const UpdatePost = () => {

    const {postid} = useParams();
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const login = useSelector(state=>state.board.login);
    const post = useSelector(state=>state.board.posts.find(p=>p.id===Number(postid)));
    const [title, setTitle] = useState(post.title);
    const [text, setText] = useState(post.text);

    const submitHandler = async (e)=>{
        e.preventDefault();
        try{
            const res = await apiClient.post("/board/updatepost",{
                id:Number(post.id),
                title:title,
                text:text,
                username:login.username
            });
            const res2 = await apiClient.get("/board/postlist");
            dispatch(setPosts(res2.data));
            navigate("/board/detail/"+postid);
        }catch(err){
            console.log(err);
        }
    }

    return (
        <div>
            <h3>UpdatePost</h3>
            <hr></hr>
            <form onSubmit={submitHandler}>
                <div style={{ paddingLeft: "30px" }}>
                    <input type='text' name='title' placeholder='Title' style={{ width: "400px" }} value={title} onChange={(e)=>setTitle(e.target.value)}></input>
                    <br></br>
                    <br></br>
                    <textarea name='text' value={text} onChange={(e)=>setText(e.target.value)} style={{width: "400px", height: "300px", overflow: 'auto'}}></textarea>
                    <br></br>
                    <br></br>
                    <button type='submit'>Update</button>
                </div>
            </form>
        </div>
    )
}

export default UpdatePost