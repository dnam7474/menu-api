import React, { useEffect, useContext, useState } from 'react';
import { useNavigate } from 'react-router';
import { CartContext } from "../App.tsx";
import { CartItem } from "../Types/CartItem.tsx";
import { Order } from "../Types/Order.tsx";
import '../Styles/Checkout.css';

const Checkout: React.FC = () => {
    const { cartItems, clearCart } = useContext(CartContext);
    const navigate = useNavigate();

    let totalAmount = 0;
    for (const item of cartItems) {
        totalAmount += item.quantity * item.price;
        console.log(`Item: ${item.name}, Quantity: ${item.quantity}, Price: ${item.price}, Total: ${item.quantity * item.price}`);
    }
    const taxRate = parseFloat(import.meta.env.VITE_REACT_APP_TAX || '0') || 0;
    const defaultTip = parseFloat(import.meta.env.VITE_REACT_APP_TIP || '0') || 0;
    const [tip, setTip] = useState<number>(parseFloat((totalAmount * defaultTip).toFixed(2)));

    useEffect(() => {
        // Update tip if cart changes
        setTip(parseFloat((totalAmount * defaultTip).toFixed(2)));
        // eslint-disable-next-line
    }, [totalAmount]);

    const tax = totalAmount * taxRate;

    const calculateGrandTotal = () => {
        return totalAmount + tax + tip;
    };

    console.log('Total Amount:', totalAmount);
    console.log('Tax Rate:', taxRate);
    console.log('Tip:', tip);
    console.log('Tax:', tax);
    console.log('Grand Total:', calculateGrandTotal());

    const handleCheckout = () => {
        const order: Order = {
            ordertime: new Date().toISOString(),
            tip: tip,
            tax: tax,
            status: 'placed',
        };

        fetch('/api/orders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(order),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to place order');
                }
                return response.json();
            })
            .then((data) => {
                console.log('Order ID obtained:', data);
                const orderItems = cartItems.map((item) => ({
                    itemid: item.id,
                    price: item.quantity * item.price,
                    orderid: data.id,
                }));

                fetch(`/api/items/order/${data.id}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(orderItems),
                })
                    .then((response) => {
                        if (!response.ok) {
                            throw new Error('Failed to save order items');
                        }
                        return response.json();
                    })
                    .then(() => {
                        const popup = document.createElement('div');
                        popup.className = 'popup-message';
                        popup.textContent = `Order ${data.id} placed successfully!`;
                        popup.className = 'popup-message';
                        document.body.appendChild(popup);

                        setTimeout(() => {
                            popup.remove();
                            clearCart();
                            setTimeout(() => {
                                navigate(`/order-details/${data.id}`);
                            })
                        }, 3000);
                    })
                    .catch((error) => {
                        console.error('Error saving order items:', error);
                        const popup = document.createElement('div');
                        popup.className = 'popup-message';
                        popup.textContent = `Error saving order items. Please try again.`;
                        popup.className = 'popup-message';
                        document.body.appendChild(popup);

                        setTimeout(() => {
                            popup.remove();
                        }, 3000);
                    });
            })
            .catch((error) => {
                console.error('Error placing order:', error);
                console.error('Error saving order items:', error);
                        const popup = document.createElement('div');
                        popup.className = 'popup-message';
                        popup.textContent = `Error placing order. Please try again.`;
                        popup.className = 'popup-message';
                        document.body.appendChild(popup);

                        setTimeout(() => {
                            popup.remove();
                        }, 3000);
            });
    };

    return (
        <>
        <div className="checkout-container">
            <h2>Checkout</h2>
            <table className="cart-table" align="center">
                <thead>
                    <tr>
                        <th>Description</th>
                        <th>Quantity</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>
                    {cartItems.map((item: CartItem) => (
                        <tr key={item.id} className="cart-item-row">
                            <td>{item.name}</td>
                            <td>{item.quantity}</td>
                            <td align="right">${item.price.toFixed(2)}</td>
                        </tr>
                    ))}
                    <tr>
                        <td colSpan={2} align="right">
                            Tax: 
                        </td>
                        <td align="right">
                            ${tax.toFixed(2)}
                        </td>
                    </tr>
                    <tr>
                        <td colSpan={2} align="right">
                            Tip: 
                        </td>
                        <td align="right">
                            $<input
                                className="cart-tip-textbox"
                                type="number"
                                min="0"
                                step="0.01"
                                value={tip}
                                onChange={e => setTip(parseFloat(e.target.value) || 0)}
                            />
                        </td>
                    </tr>
                    <tr>
                        <td colSpan={3} align="right">
                            <h2 className="cart-total">Total: ${calculateGrandTotal().toFixed(2)}</h2>
                        </td>
                    </tr>
                </tbody>
            </table>
            <button onClick={handleCheckout}>Proceed to Checkout</button>
        </div>
        </>
    );
};

export default Checkout;