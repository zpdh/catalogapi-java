import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
    createProduct,
    deleteProduct,
    getProducts,
    updateProduct,
} from "../api/products";
import type { CreateProductRequest, UpdateProductRequest } from "../types";

export const useProducts = () => {
    return useQuery({
        queryKey: ["products"],
        queryFn: getProducts,
    });
};

export const useCreateProduct = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (request: CreateProductRequest) => createProduct(request),
        onSuccess: () =>
            queryClient.invalidateQueries({ queryKey: ["products"] }),
    });
};

export const useUpdateProduct = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: ({
            id,
            request,
        }: {
            id: number;
            request: UpdateProductRequest;
        }) => updateProduct(id, request),
        onSuccess: () =>
            queryClient.invalidateQueries({ queryKey: ["products"] }),
    });
};

export const useDeleteProduct = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (id: number) => deleteProduct(id),
        onSuccess: () =>
            queryClient.invalidateQueries({ queryKey: ["products"] }),
    });
};
