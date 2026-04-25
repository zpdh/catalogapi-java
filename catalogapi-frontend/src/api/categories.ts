import api from "./axios";
import type {
    CategoryResponse,
    CreateCategoryRequest,
    UpdateCategoryRequest,
} from "../types";

export const getCategories = async (): Promise<CategoryResponse[]> => {
    const response = await api.get<CategoryResponse[]>("/categories");
    return response.data;
};

export const getCategoryById = async (
    id: number,
): Promise<CategoryResponse> => {
    const response = await api.get<CategoryResponse>(`/categories/${id}`);
    return response.data;
};

export const createCategory = async (
    request: CreateCategoryRequest,
): Promise<CategoryResponse> => {
    const response = await api.post<CategoryResponse>("/categories", request);
    return response.data;
};

export const updateCategory = async (
    id: number,
    request: UpdateCategoryRequest,
): Promise<CategoryResponse> => {
    const response = await api.put<CategoryResponse>(
        `/categories/${id}`,
        request,
    );
    return response.data;
};

export const deleteCategory = async (id: number): Promise<void> => {
    await api.delete(`/categories/${id}`);
};
