import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
    createCategory,
    deleteCategory,
    getCategories,
    updateCategory,
} from "../api/categories";
import type { CreateCategoryRequest, UpdateCategoryRequest } from "../types";

export const useCategories = () => {
    return useQuery({
        queryKey: ["categories"],
        queryFn: getCategories,
    });
};

export const useCreateCategory = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (request: CreateCategoryRequest) => createCategory(request),
        onSuccess: () =>
            queryClient.invalidateQueries({ queryKey: ["categories"] }),
    });
};

export const useUpdateCategory = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: ({
            id,
            request,
        }: {
            id: number;
            request: UpdateCategoryRequest;
        }) => updateCategory(id, request),
        onSuccess: () =>
            queryClient.invalidateQueries({ queryKey: ["categories"] }),
    });
};

export const useDeleteCategory = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (id: number) => deleteCategory(id),
        onSuccess: () =>
            queryClient.invalidateQueries({ queryKey: ["categories"] }),
    });
};
