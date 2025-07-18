import React from 'react';
import { Link } from 'react-router';
import '../Styles/Header.css';

const Header: React.FC = () => {
    return (
        <>
        <div className="header">
            <nav className="nav">
                <ul className="navList">
                    <li className="navItem">
                        <Link to="/dinner-and-a-movie" className="navLink">
                            Dinner
                        </Link>
                    </li>
                    <li className="navItem">
                        <Link to="/register" className="navLink">
                            Register
                        </Link>
                    </li>
                    <li className="navItem">
                        <Link to="/checkout" className="navLink">
                            Checkout
                        </Link>
                    </li>
                    <li className="navItem">
                        <Link to="/past-orders" className="navLink">
                            Past Orders
                        </Link>
                    </li>
                    <li className="navItem">
                        <Link to="/login" className="navLink">
                            Login
                        </Link>
                    </li>
                </ul>
            </nav>
        </div>
        </>
    );
};



export default Header;