import api from "./axios";
import type {
    ProductResponse,
    CreateProductRequest,
    UpdateProductRequest,
} from "../types";

export const getProducts = async (): Promise<ProductResponse[]> => {
    const response = await api.get<ProductResponse[]>("/products");
    return response.data;
};

export const getProductById = async (id: number): Promise<ProductResponse> => {
    const response = await api.get<ProductResponse>(`/products/${id}`);
    return response.data;
};

export const createProduct = async (
    request: CreateProductRequest,
): Promise<ProductResponse> => {
    const response = await api.post<ProductResponse>("/products", request);
    return response.data;
};

export const updateProduct = async (
    id: number,
    request: UpdateProductRequest,
): Promise<ProductResponse> => {
    const response = await api.put<ProductResponse>(`/products/${id}`, request);
    return response.data;
};

export const deleteProduct = async (id: number): Promise<void> => {
    await api.delete(`/products/${id}`);
};
