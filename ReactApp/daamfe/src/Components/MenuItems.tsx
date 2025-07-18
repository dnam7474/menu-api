import { useEffect, useState, useContext } from "react";
import { MenuItem } from "../Types/MenuItem.tsx";
import { CartContext } from "../App.tsx";
import { JwtContext } from "../Types/JwtContext.tsx";
import '../Styles/MenuItems.css';

export const MenuItems = () => {
    const cart = useContext(CartContext);
    const { jwt } = useContext(JwtContext);
    const [menuItems, setMenuItems] = useState<MenuItem[]>([]);
    const [popup, setPopup] = useState<string | null>(null);

    useEffect(() => {
        const fetchMenuItems = async () => {
            try {
                const response = await fetch('/api/menuitems', {
                    headers: jwt ? { Authorization: `Bearer ${jwt}` } : {},
                });
                const data: menuItems = await response.json();
                setMenuItems(data);
            } catch (error) {
                console.error("Error fetching menu items:", error);
            }
        };

        fetchMenuItems();
    }, [jwt]);

    const handleAddToCart = (item: MenuItem) => {
        cart.addToCart(item);
        setPopup(`Added "${item.name}" to cart!`);
        setTimeout(() => setPopup(null), 2000);
    };

    return (
        <>
            <div className="card-container">
                {menuItems.map((item) => (
                    <div key={item.id} className="card-item">
                        <div className="title">{item.name}</div>
                        <div className="image"><img src={item.imageUrl} /></div>
                        <div className="description">{item.description}</div>
                        <div className="price">${item.price.toFixed(2)}</div>
                        <button className="add-button" onClick={() => handleAddToCart(item)}>Add</button>
                    </div>
                ))}
            </div>
            {popup && (
                <div className="popup-message">
                    {popup}
                </div>
            )}
        </>
    );
}