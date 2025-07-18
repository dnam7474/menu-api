import { useEffect, useState } from "react";
import type { Order } from "../Types/Order.tsx";
import '../Styles/OrderHistory.css';

export const OrderHistory = () => {
    const [orders, setOrders] = useState<Order[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetch("/api/orders")
            .then((res) => {
                if (!res.ok) throw new Error("Failed to fetch orders");
                return res.json();
            })
            .then((data) => {
                setOrders(data);
                console.log("Fetched orders:", data);
                setLoading(false);
            })
            .catch((err) => {
                setError(err.message);
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <div>Loading order history...</div>;
    } else {
        console.log("Orders:", orders);
    }
    if (error) return <div>Error: {error}</div>;
    // if (!orders || orders.length === 0) return <div>No past orders found.</div>;

    return (
        <div>
            <h2>Order History</h2>
            <table className="order-history-table">
            <thead>
                <tr>
                <th className="order-history-header">Order ID</th>
                <th className="order-history-header">Order Date</th>
                <th className="order-history-header">Delivery Date</th>
                </tr>
            </thead>
            <tbody>
                {orders.map((order) => (
                <tr key={order.id} className="order-history-row">
                    <td className="order-history-cell">
                        <a href={`/order-details/${order.id}`} className="order-history-link">{order.id}</a>
                    </td>
                    <td className="order-history-cell">{order.ordertime}</td>
                    <td className="order-history-cell">{order.pickuptime}</td>
                </tr>
                ))}
            </tbody>
            </table>
        </div>
    );
}
