
import {  useSelector } from 'react-redux';
import { Link, useNavigate, useParams } from 'react-router-dom'
import axios from 'axios';
import apiClient from '../api/apiClient';

const DetailPost = () => {

    const {postid} = useParams();
    const navigate = useNavigate();
    const accessToken = useSelector(state=>state.board.accessToken);
    const post = useSelector(state=>state.board.posts.find(p=>p.id===Number(postid)));
    const login = useSelector(state=>state.board.login);
    if(!post){
        alert("post not found");
        navigate("/");
    }

    const handleClick = async (e)=>{
        e.preventDefault();
        try{
            const res = await apiClient.delete("board/deletepost",{
                params:{
                    id:Number(post.id),
                    username:login.username
                }
            });
            alert(res.data);
            navigate("/");
        }catch(err){
            console.log(err);
        }
    };

  return (
    <div style={{paddingLeft:"30px"}}>
        <h3>{post.title}</h3>
        <p style={{width:"400px", height:"400px", border:"1px solid #000"}}>{post.text}</p>
        <Link to={"/"}>Back</Link>
        {login && login.username===post.username && <>&nbsp;|&nbsp;<Link to={"/board/update/"+post.id}>Update</Link>&nbsp;|&nbsp;<button onClick={handleClick}>Delete</button></>}
    </div>
  )
};

export default DetailPost