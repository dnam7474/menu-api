import React, { createContext, useState } from 'react';

type JwtContextType = {
  jwt: string | null;
  setJwt: (token: string | null) => void;
};

export const JwtContext = createContext<JwtContextType>({
  jwt: null,
  setJwt: () => {},
});

export const JwtProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [jwt, setJwt] = useState<string | null>(null);

  return (
    <JwtContext.Provider value={{ jwt, setJwt }}>
      {children}
    </JwtContext.Provider>
  );
};