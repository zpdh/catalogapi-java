export interface CategoryResponse {
    id: number;
    name: string;
    description: string;
}

export interface ProductResponse {
    id: number;
    name: string;
    description: string;
    price: number;
    stock: number;
    categoryId: number;
    categoryName: string;
}

export interface AuthResponse {
    token: string;
    email: string;
    role: string;
}

export interface CreateCategoryRequest {
    name: string;
    description?: string;
}

export interface UpdateCategoryRequest {
    name: string;
    description?: string;
}

export interface CreateProductRequest {
    name: string;
    description?: string;
    price: number;
    stock: number;
    categoryId: number;
}

export interface UpdateProductRequest {
    name: string;
    description?: string;
    price: number;
    stock: number;
    categoryId: number;
}

export interface AuthRequest {
    email: string;
    password: string;
}

export interface RegisterRequest {
    email: string;
    password: string;
}
