import React, { useState } from "react";
import axios from "axios";
import { User } from "../Types/User";
import "../Styles/Register.css"; // <-- Import the CSS

export const initialState: User = {
  id: "",
  email: "",
  expiry_month: "",
  expiry_year: "",
  first: "",
  image_url: "",
  last: "",
  pan: "",
  password: "",
  phone: "",
  roles: "",
  username: "",
};

export const Register: React.FC = () => {
  const [form, setForm] = useState<User>(initialState);
  const [message, setMessage] = useState<string>("");
  const [showPopup, setShowPopup] = useState<boolean>(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // Fixed handleSubmit function for Register.tsx
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      // If no role is selected, default to ROLE_USER
      let roles = form.roles;
      if (!roles || roles.trim() === "") {
        roles = "ROLE_USER";
      }

      // ✅ Fix field names to match backend expectations
      const submitForm = {
        username: form.username,
        password: form.password,
        first: form.first,
        last: form.last,
        phone: form.phone || null,
        email: form.email,
        imageUrl: form.image_url || null,  // ✅ Changed from image_url to imageUrl
        pan: form.pan || null,
        expiryMonth: form.expiry_month ? parseInt(form.expiry_month) : null, // ✅ Changed to expiryMonth and convert to number
        expiryYear: form.expiry_year ? parseInt(form.expiry_year) : null,   // ✅ Changed to expiryYear and convert to number
        roles,
      };

      console.log('Sending registration data:', submitForm); // ✅ Debug logging

      const response = await fetch("/api/users", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(submitForm),
      });

      console.log('Response status:', response.status); // ✅ Debug logging
      console.log('Response headers:', response.headers); // ✅ Debug logging

      if (!response.ok) {
        // ✅ Better error handling - check if response has content
        const contentType = response.headers.get('content-type');
        let errorMessage = `Registration failed with status ${response.status}`;

        if (contentType && contentType.includes('application/json')) {
          try {
            const errorData = await response.json();
            errorMessage = errorData.message || errorMessage;
          } catch (jsonError) {
            console.error('Failed to parse error response as JSON:', jsonError);
          }
        } else {
          // Try to get text response
          try {
            const errorText = await response.text();
            errorMessage = errorText || errorMessage;
          } catch (textError) {
            console.error('Failed to get error response as text:', textError);
          }
        }
        throw new Error(errorMessage);
      }

      // ✅ Check if response has content before parsing JSON
      const contentType = response.headers.get('content-type');
      if (contentType && contentType.includes('application/json')) {
        const userData = await response.json();
        console.log('User created successfully:', userData);
      }

      setMessage("User registered successfully!");
      setShowPopup(true);
      setForm(initialState);
      setTimeout(() => setShowPopup(false), 3000);

    } catch (error: any) {
      console.error('Registration error:', error);
      setMessage(error.message || "Registration failed.");
      setShowPopup(true);
      setTimeout(() => setShowPopup(false), 3000);
    }
  };

  return (
    <div align="left">
      <h1>Register a new user</h1>
      <form onSubmit={handleSubmit}>
        <table>
          <tbody>
            <tr>
              <td colSpan={2}>
                <h2>Personal Information</h2>
              </td>
            </tr>
            <tr>
              <td>
                <b>* First name *</b>:
              </td>
              <td>
                <input
                  name="first"
                  placeholder="First Name"
                  value={form.first}
                  onChange={handleChange}
                  required
                />
              </td>
            </tr>
            <tr>
              <td>
                <b>* Last name *</b>:
              </td>
              <td>
                <input
                  name="last"
                  placeholder="Last Name"
                  value={form.last}
                  onChange={handleChange}
                  required
                />
              </td>
            </tr>
            <tr>
              <td>
                <b>* Username *</b>:
              </td>
              <td>
                <input
                  name="username"
                  placeholder="Username"
                  value={form.username}
                  onChange={handleChange}
                  required
                />
              </td>
            </tr>
            <tr>
              <td>
                <b>* Email *</b>:
              </td>
              <td>
                <input
                  name="email"
                  placeholder="Email"
                  value={form.email}
                  onChange={handleChange}
                  required
                />
              </td>
            </tr>
            <tr>
              <td>Phone:</td>
              <td>
                <input
                  name="phone"
                  placeholder="Phone"
                  value={form.phone}
                  onChange={handleChange}
                />
              </td>
            </tr>
            <tr>
              <td>Image URL:</td>
              <td>
                <input
                  name="image_url"
                  placeholder="Image URL"
                  value={form.image_url}
                  onChange={handleChange}
                />
              </td>
            </tr>
            <tr>
              <td colSpan={2}>
                <h2>Password</h2>
              </td>
            </tr>
            <tr>
              <td>
                <b>* Password *</b>:
              </td>
              <td>
                <input
                  name="password"
                  type="password"
                  placeholder="Password"
                  value={form.password}
                  onChange={handleChange}
                  required
                />
              </td>
            </tr>
            <tr>
              <td>
                <h2>Roles</h2>
              </td>
            </tr>
            <tr>
              <td colSpan={2}>Roles:</td>
              <td>
                <select
                  name="roles"
                  multiple
                  value={form.roles ? form.roles.split(",") : []}
                  onChange={(e) => {
                    const selected = Array.from(
                      e.target.selectedOptions,
                      (option) => option.value
                    );
                    setForm({ ...form, roles: selected.join(",") });
                  }}
                  style={{ minWidth: "120px", height: "70px" }}
                >
                  <option value="ROLE_ADMIN">ADMIN</option>
                  <option value="ROLE_USER">USER</option>
                  <option value="ROLE_SERVER">SERVER</option>
                </select>
                <div style={{ fontSize: "0.9em", color: "#555" }}>
                  (Hold Ctrl or Cmd to select multiple)
                </div>
              </td>
            </tr>
            <tr>
              <td colSpan={2}>
                <h2>Payment Information</h2>
              </td>
            </tr>
            <tr>
              <td>Card Number:</td>
              <td>
                <input
                  name="pan"
                  placeholder="PAN"
                  value={form.pan}
                  onChange={handleChange}
                />
              </td>
            </tr>
            <tr>
              <td>Expiry Month:</td>
              <td>
                <input
                  name="expiry_month"
                  placeholder="Expiry Month"
                  value={form.expiry_month}
                  onChange={handleChange}
                />
              </td>
            </tr>
            <tr>
              <td>Expiry Year:</td>
              <td>
                <input
                  name="expiry_year"
                  placeholder="Expiry Year"
                  value={form.expiry_year}
                  onChange={handleChange}
                />
              </td>
            </tr>
            <tr>
              <td colSpan={2} align="center">
                <button type="submit">Register</button>
              </td>
            </tr>
          </tbody>
        </table>
      </form>
      {showPopup && (
        <div
          className="popup-message"
          style={{ animation: "fadeInOut 3s" }}
        >
          {message}
        </div>
      )}
    </div>
  );
};