
import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Link } from 'react-router-dom';
import apiClient from '../api/apiClient';
import { setPosts } from '../store';

const Board = () => {
    const dispatch = useDispatch();
    const login = useSelector(state=> state.board.login);
    const posts = useSelector(state=> state.board.posts);
    useEffect(()=>{
        const getPosts = async ()=>{
            try{
                const res = await apiClient.get("/board/postlist");
                dispatch(setPosts(res.data));
            }catch(err){
                console.log(err);
            }
        };
        getPosts();
    },[dispatch]);

  return (
    <div>
        <h3>Board</h3>
        <hr></hr>
        {login && <><Link to="board/newpost">NewPost</Link><hr></hr></>}
        {posts.length<1 && <h5>게시물이 없습니다.</h5>}
        {posts.length>0 && <>
            {posts.map(p=>
            <><div>
                <span  style={{display:'inline-block', width:"200px", marginLeft:"40px", marginRight:"50px" }}><Link to={"/board/detail/"+p.id}>{p.title}</Link></span>
                <span style={{width:"300px", display:'inline-block'}}>작성자 : {p.name}</span><span>{p.createdAt}</span>
                <hr></hr>
            </div></>)}
        </>}
    </div>
  )
}

export default Board;
