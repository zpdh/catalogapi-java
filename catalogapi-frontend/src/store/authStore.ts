import { create } from "zustand";
import { persist } from "zustand/middleware";
import type { AuthResponse } from "../types";

interface AuthState {
    token: string | null;
    email: string | null;
    role: string | null;
    setAuth: (auth: AuthResponse) => void;
    clearAuth: () => void;
    isAuthenticated: () => boolean;
    isAdmin: () => boolean;
}

export const useAuthStore = create<AuthState>()(
    persist(
        (set, get) => ({
            token: null,
            email: null,
            role: null,

            setAuth: (auth: AuthResponse) =>
                set({
                    token: auth.token,
                    email: auth.email,
                    role: auth.role,
                }),

            clearAuth: () =>
                set({
                    token: null,
                    email: null,
                    role: null,
                }),

            isAuthenticated: () => !!get().token,
            isAdmin: () => get().role === "ADMIN",
        }),
        {
            name: "auth-storage",
        },
    ),
);
