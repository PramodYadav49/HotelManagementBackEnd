import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';

function Navbar() {
    const navigate = useNavigate();
    const isAuthenticated = ApiService.isAuthenticated();
    const userRole = ApiService.isAdmin() ? 'admin' : ApiService.isUser() ? 'user' : null;

    const handleLogout = () => {
        const isLogout = window.confirm('Are you sure you want to logout this user?');
        if (isLogout) {
            ApiService.logout();
            navigate('/home');
        }
    };

    return (
        <nav className="navbar">
            <div className="navbar-brand">
                <NavLink to="/home">DreamHotel</NavLink>
            </div>
            <ul className="navbar-ul">
                <li><NavLink to="/home" className={({ isActive }) => (isActive ? 'active' : '')}>Home</NavLink></li>
                <li><NavLink to="/rooms" className={({ isActive }) => (isActive ? 'active' : '')}>Rooms</NavLink></li>
                <li><NavLink to="/find-booking" className={({ isActive }) => (isActive ? 'active' : '')}>Find my Booking</NavLink></li>

                {userRole === 'user' && <li><NavLink to="/profile" className={({ isActive }) => (isActive ? 'active' : '')}>Profile</NavLink></li>}
                {userRole === 'admin' && <li><NavLink to="/admin" className={({ isActive }) => (isActive ? 'active' : '')}>Admin</NavLink></li>}

                {!isAuthenticated && (
                    <>
                        <li><NavLink to="/login" className={({ isActive }) => (isActive ? 'active' : '')}>Login</NavLink></li>
                        <li><NavLink to="/register" className={({ isActive }) => (isActive ? 'active' : '')}>Register</NavLink></li>
                    </>
                )}
                {isAuthenticated && (
                    <li>
                        <button onClick={handleLogout} className="logout-button">
                            Logout
                        </button>
                    </li>
                )}
            </ul>
        </nav>
    );
}

export default Navbar;