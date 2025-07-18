import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { format } from 'date-fns'
import type { Order } from "../Types/Order.tsx";
import type { OrderItem } from "../Types/OrderItem.tsx";
import '../Styles/OrderHistory.css';

type ItemDetails = {
  id: number;
  name: string;
  price: number;
};

type OrderInfo = {
  id: number;
  date: string;
  customer: string;
  // add other fields as needed
};

export const OrderDetails = () => {
  const { id } = useParams();
  const orderid = Number(id);
  const [orderInfo, setOrderInfo] = useState<OrderInfo | null>(null);
  const [orderItems, setOrderItems] = useState<OrderItem[]>([]);
  const [itemDetails, setItemDetails] = useState<Record<number, ItemDetails>>({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  // Fetch order info
  useEffect(() => {
    setLoading(true);
    Promise.all([
      fetch(`/api/orders/${orderid}`).then(res => {
        if (!res.ok) throw new Error("Failed to fetch order info");
        return res.json();
      }),
      fetch(`/api/items/order/${orderid}`).then(res => {
        if (!res.ok) throw new Error("Failed to fetch order items");
        return res.json();
      })
        ])
      .then(async ([orderData, itemsData]) => {
        setOrderInfo(orderData);
        console.log("Order Data:", orderData);
        setOrderItems(itemsData || []);
        console.log("Order Items:", itemsData);
        const items: OrderItem[] = itemsData || [];
        // Wait for all item details to be fetched
        const details: Record<number, ItemDetails> = {};
        await Promise.all(
          items.map(async (item: OrderItem) => {
            if (!details[item.itemid]) {
              const res = await fetch(`/api/menuitems/${item.itemid}`);
              if (res.ok) {
                const data = await res.json();
                details[item.itemid] = data;
              }
            }
          })
        );
        setItemDetails(details);
        console.log("Item Details:", details);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, [orderid]);

  if (loading) return <div>Loading order #{orderid}...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!orderInfo) return <div>No details found for order #{orderid}.</div>;

  return (
    <div className="order-details">
      <h2>Order #{orderInfo.id}</h2>
      <div>Order Date: {format(orderInfo.ordertime, 'yyyy-mm-dd HH:MM')}</div>
      <div>Delivered: {format(orderInfo.pickuptime, 'yyyy-mm-dd HH:MM')} - {orderInfo.location} </div>
      
      {orderInfo.pan ? (
        <div>Payment: Credit Card</div>
      ) : null}

      <div>
        <table className="order-history-table">
          <thead>
            <tr className="order-history-header">
              <th className="order-history-th">Item</th>
              <th className="order-history-th">Quantity</th>
              <th className="order-history-th">Item Price</th>
              <th className="order-history-th">Order Price</th>
            </tr>
          </thead>
          <tbody>
            {orderItems.map((item) => (
              <tr key={item.id} className="order-history-row">
          <td className="order-history-cell">{itemDetails[item.itemid]?.name || "Unknown Item"}</td>
          <td className="order-history-cell">
            {itemDetails[item.itemid]?.price && item.price
              ? Math.round(item.price / itemDetails[item.itemid].price)
              : "N/A"}
          </td>
          <td className="order-history-cell">
            {itemDetails[item.itemid]?.price !== undefined
              ? `$${itemDetails[item.itemid].price.toFixed(2)}`
              : "N/A"}
          </td>
          <td className="order-history-cell">${item.price.toFixed(2)}</td>
              </tr>
            ))}
          </tbody>
          <tfoot>
            <tr className="order-history-footer">
              <td className="order-history-cell order-history-totals" colSpan={3}>Tax: </td>
              <td className="order-history-cell">${(orderInfo.tax).toFixed(2)}</td>
            </tr><tr>
              <td className="order-history-cell order-history-totals" colSpan={3}>Tip: </td>
              <td className="order-history-cell">${(orderInfo.tip).toFixed(2)}</td>
            </tr>
            <tr>
              <td className="order-history-cell order-history-totals" colSpan={3}>Total: </td>
              <td className="order-history-cell">
                {(
                  orderItems
                    .map(item => item.price)
                    .concat([orderInfo.tax || 0, orderInfo.tip || 0])
                    .reduce((sum, val) => sum + val, 0)
                    .toFixed(2)
                )}
              </td>
            </tr>
          </tfoot>
        </table>
      </div>

    </div>
  );
};