
import React, { useEffect } from 'react'
import apiClient from '../api/apiClient'
import { useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom';
import { setAccessToken, setLogin } from '../store';

const Logout = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    useEffect(() => {
        const logout = async () => {
            try {
                await apiClient.post("/logout1", null);
            } catch (err) {
                console.log(err);
            }
        }

        logout();
        dispatch(setAccessToken(null));
        dispatch(setLogin(null));
        alert("logout");
        navigate("/");
    }, [dispatch, navigate]);
    return (
        <div>Logout</div>
    )
}

export default Logout