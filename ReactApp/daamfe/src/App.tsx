import { useState, createContext, useContext } from 'react'
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router'
import { MenuItems } from './Components/MenuItems.tsx'
import Header from './Components/Header.tsx'
import { CartItem } from './Types/CartItem.tsx'
import Checkout from './Components/Checkout.tsx'
import {OrderHistory} from './Components/OrderHistory.tsx'
import { OrderDetails } from './Components/OrderDetails.tsx'
import {Register} from './Components/Register.tsx'
import { Login } from './Components/Login.tsx'
import { JwtContext, JwtProvider } from './Types/JwtContext.tsx';
import './App.css'

export const CartContext = createContext({
  cartItems: [] as MenuItem[],
  addToCart: (item: MenuItem) => {},
  removeFromCart: (id: number) => {},
  clearCart: () => {},
});


function App() {
  const [cartItems, setCartItems] = useState<CartItem[]>([]);
  console.log("Added to cart:", cartItems);
  const jwt = useContext(JwtContext)?.jwt;
  
  // const menuItems: menuItems = [];

  const addToCart = (item: CartItem) => {
    setCartItems((prevItems) => {
      const existingItem = prevItems.find((cartItem) => cartItem.id === item.id);
      if (existingItem) {
        return prevItems.map((cartItem) =>
          cartItem.id === item.id
            ? { ...cartItem, quantity: (cartItem.quantity || 1) + 1 }
            : cartItem
        );
      } else {
        return [...prevItems, { ...item, quantity: 1 }];
      }
    });
      // setCartItems((prevItems) => [...prevItems, item]);
  };

  const removeFromCart = (id: number) => {
      setCartItems((prevItems) => prevItems.filter((item) => item.id !== id));
  };

  const clearCart = () => {
    setCartItems([]);
  };
  
  return (
  <>
    <h1>Dinner and a Movie</h1>
    <h3>User: {(() => {
    if  (!jwt) return "Not logged in";
      try {
        // Decode JWT payload
        const payload = JSON.parse(atob(jwt.split('.')[1]));
        return payload.username || "Unknown";
      } catch {
        return "Invalid token";
      }
    })()}
    </h3>
    <CartContext.Provider value={{ cartItems, addToCart, removeFromCart }}>
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<h2>Welcome to Dinner and a Movie</h2>} />
        <Route path="/dinner-and-a-movie" element=<MenuItems /> />
        <Route path="/register" element=<Register /> />
        <Route path="/checkout" element=<Checkout /> />
        <Route path="/past-orders" element=<OrderHistory /> />
        <Route path="/order-details/:id" element={<OrderDetails />} />
        <Route path="/login" element={<Login />} />
      </Routes>
    </Router>
    </CartContext.Provider>
  </>
  )
}

export default App
