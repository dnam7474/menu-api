import React, { useState, useContext } from 'react';
import { JwtContext } from '../Types/JwtContext.tsx';
import { useNavigate } from 'react-router';

export const Login: React.FC = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState<string | null>(null);
    const { jwt, setJwt } = useContext(JwtContext);
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);

        try {
            const basicAuth = btoa(`${username}:${password}`);
            const response = await fetch('/login', {
                method: 'POST',
                headers: {
                    'Authorization': `Basic ${basicAuth}`,
                },
            });

            if (!response.ok) {
                throw new Error('Invalid credentials');
            }

            // Get the token as plain text
            const token = await response.text();
            setJwt(token);
            console.log('JWT:', token);
            navigate('/dinner-and-a-movie');
        } catch (err: any) {
            setError(err.message || 'Login failed');
        }
    };

    return (
        <div style={{ maxWidth: 400, margin: '2rem auto', padding: 24, border: '1px solid #ccc', borderRadius: 8 }}>
            <h2>Login</h2>
            {error && <div style={{ color: 'red', marginBottom: 12 }}>{error}</div>}
            {jwt ? (
                <div>
                    {/* <strong>JWT:</strong>
                    <pre style={{ wordBreak: 'break-all', background: '#f5f5f5', padding: 8 }}>{jwt}</pre> */}
                    <p>You are already logged in.</p>
                    <button
                        onClick={() => {
                            setJwt(null);
                            navigate('/');
                        }}
                        style={{ padding: '8px 16px', marginTop: 12 }}
                    >
                        Logout
                    </button>
                </div>
            ) : (
                <form onSubmit={handleSubmit}>
                    <div style={{ marginBottom: 12 }}>
                        <label>
                            Username:
                            <input
                                type="text"
                                value={username}
                                onChange={e => setUsername(e.target.value)}
                                required
                                style={{ width: '100%', padding: 8, marginTop: 4 }}
                            />
                        </label>
                    </div>
                    <div style={{ marginBottom: 12 }}>
                        <label>
                            Password:
                            <input
                                type="password"
                                value={password}
                                onChange={e => setPassword(e.target.value)}
                                required
                                style={{ width: '100%', padding: 8, marginTop: 4 }}
                            />
                        </label>
                    </div>
                    <button type="submit" style={{ padding: '8px 16px' }}>Login</button>
                </form>
            )}
        </div>
    );
};