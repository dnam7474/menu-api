import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { JwtProvider } from './Types/JwtContext.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <JwtProvider>
    <App />
    </JwtProvider>
  </StrictMode>,
)
