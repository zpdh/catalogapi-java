import api from "./axios";
import type { AuthRequest, AuthResponse, RegisterRequest } from "../types";

export const login = async (request: AuthRequest): Promise<AuthResponse> => {
    const response = await api.post<AuthResponse>("/auth/login", request);
    return response.data;
};

export const register = async (
    request: RegisterRequest,
): Promise<AuthResponse> => {
    const response = await api.post<AuthResponse>("/auth/register", request);
    return response.data;
};
