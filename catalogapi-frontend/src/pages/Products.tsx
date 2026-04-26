import { useState } from "react";
import { useForm, type Resolver } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import Navbar from "../components/shared/Navbar";
import { useAuthStore } from "../store/authStore";
import {
    useProducts,
    useCreateProduct,
    useUpdateProduct,
    useDeleteProduct,
} from "../hooks/useProducts";
import { useCategories } from "../hooks/useCategories";
import type { ProductResponse } from "../types";
import { Pencil, Trash2, Plus, X } from "lucide-react";

const schema = z.object({
    name: z.string().min(1, "Name is required").max(100),
    description: z.string().max(255).optional(),
    price: z.preprocess(
        (val) => parseFloat(String(val)),
        z.number().positive("Price must be positive"),
    ),
    stock: z.preprocess(
        (val) => parseInt(String(val)),
        z.number().int().min(0, "Stock must be zero or positive"),
    ),
    categoryId: z.preprocess(
        (val) => parseInt(String(val)),
        z.number().min(1, "Category is required"),
    ),
});

type FormData = z.infer<typeof schema>;

export default function Products() {
    const isAdmin = useAuthStore((state) => state.isAdmin());
    const { data: products, isLoading } = useProducts();
    const { data: categories } = useCategories();
    const createProduct = useCreateProduct();
    const updateProduct = useUpdateProduct();
    const deleteProduct = useDeleteProduct();

    const [showForm, setShowForm] = useState(false);
    const [editing, setEditing] = useState<ProductResponse | null>(null);

    const {
        register,
        handleSubmit,
        reset,
        formState: { errors, isSubmitting },
    } = useForm<FormData>({
        resolver: zodResolver(schema) as Resolver<FormData>,
    });

    const openCreate = () => {
        setEditing(null);
        reset({ name: "", description: "", price: 0, stock: 0, categoryId: 0 });
        setShowForm(true);
    };

    const openEdit = (product: ProductResponse) => {
        setEditing(product);
        reset({
            name: product.name,
            description: product.description,
            price: product.price,
            stock: product.stock,
            categoryId: product.categoryId,
        });
        setShowForm(true);
    };

    const onSubmit = async (data: FormData) => {
        if (editing) {
            await updateProduct.mutateAsync({ id: editing.id, request: data });
        } else {
            await createProduct.mutateAsync(data);
        }
        setShowForm(false);
        reset();
    };

    const handleDelete = async (id: number) => {
        if (confirm("Delete this product?")) {
            await deleteProduct.mutateAsync(id);
        }
    };

    return (
        <div className="min-h-screen bg-gray-100">
            <Navbar />
            <div className="max-w-5xl mx-auto mt-10 p-6">
                <div className="flex items-center justify-between mb-6">
                    <h1 className="text-2xl font-bold">Products</h1>
                    {isAdmin && (
                        <button
                            onClick={openCreate}
                            className="flex items-center gap-2 bg-gray-900 text-white px-4 py-2 rounded hover:bg-gray-700"
                        >
                            <Plus size={16} /> New Product
                        </button>
                    )}
                </div>

                {showForm && (
                    <div className="bg-white rounded-lg shadow-md p-6 mb-6">
                        <div className="flex justify-between items-center mb-4">
                            <h2 className="text-lg font-semibold">
                                {editing ? "Edit Product" : "New Product"}
                            </h2>
                            <button onClick={() => setShowForm(false)}>
                                <X size={18} />
                            </button>
                        </div>
                        <form
                            onSubmit={handleSubmit(onSubmit)}
                            className="grid grid-cols-2 gap-4"
                        >
                            <div className="col-span-2">
                                <label className="block text-sm font-medium mb-1">
                                    Name
                                </label>
                                <input
                                    {...register("name")}
                                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gray-400"
                                />
                                {errors.name && (
                                    <p className="text-red-500 text-sm mt-1">
                                        {errors.name.message}
                                    </p>
                                )}
                            </div>
                            <div className="col-span-2">
                                <label className="block text-sm font-medium mb-1">
                                    Description
                                </label>
                                <input
                                    {...register("description")}
                                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gray-400"
                                />
                            </div>
                            <div>
                                <label className="block text-sm font-medium mb-1">
                                    Price
                                </label>
                                <input
                                    {...register("price")}
                                    type="number"
                                    step="0.01"
                                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gray-400"
                                />
                                {errors.price && (
                                    <p className="text-red-500 text-sm mt-1">
                                        {errors.price.message}
                                    </p>
                                )}
                            </div>
                            <div>
                                <label className="block text-sm font-medium mb-1">
                                    Stock
                                </label>
                                <input
                                    {...register("stock")}
                                    type="number"
                                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gray-400"
                                />
                                {errors.stock && (
                                    <p className="text-red-500 text-sm mt-1">
                                        {errors.stock.message}
                                    </p>
                                )}
                            </div>
                            <div className="col-span-2">
                                <label className="block text-sm font-medium mb-1">
                                    Category
                                </label>
                                <select
                                    {...register("categoryId")}
                                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gray-400"
                                >
                                    <option value={0}>Select a category</option>
                                    {categories?.map((category) => (
                                        <option
                                            key={category.id}
                                            value={category.id}
                                        >
                                            {category.name}
                                        </option>
                                    ))}
                                </select>
                                {errors.categoryId && (
                                    <p className="text-red-500 text-sm mt-1">
                                        {errors.categoryId.message}
                                    </p>
                                )}
                            </div>
                            <div className="col-span-2">
                                <button
                                    type="submit"
                                    disabled={isSubmitting}
                                    className="w-full bg-gray-900 text-white py-2 rounded hover:bg-gray-700 disabled:opacity-50"
                                >
                                    {isSubmitting ? "Saving..." : "Save"}
                                </button>
                            </div>
                        </form>
                    </div>
                )}

                <div className="bg-white rounded-lg shadow-md overflow-hidden">
                    {isLoading ? (
                        <p className="p-6 text-center text-gray-500">
                            Loading...
                        </p>
                    ) : products?.length === 0 ? (
                        <p className="p-6 text-center text-gray-500">
                            No products found.
                        </p>
                    ) : (
                        <table className="w-full">
                            <thead className="bg-gray-50 border-b">
                                <tr>
                                    <th className="text-left px-6 py-3 text-sm font-medium text-gray-500">
                                        Name
                                    </th>
                                    <th className="text-left px-6 py-3 text-sm font-medium text-gray-500">
                                        Category
                                    </th>
                                    <th className="text-left px-6 py-3 text-sm font-medium text-gray-500">
                                        Price
                                    </th>
                                    <th className="text-left px-6 py-3 text-sm font-medium text-gray-500">
                                        Stock
                                    </th>
                                    {isAdmin && <th className="px-6 py-3" />}
                                </tr>
                            </thead>
                            <tbody className="divide-y">
                                {products?.map((product) => (
                                    <tr
                                        key={product.id}
                                        className="hover:bg-gray-50"
                                    >
                                        <td className="px-6 py-4 font-medium">
                                            {product.name}
                                        </td>
                                        <td className="px-6 py-4 text-gray-500">
                                            {product.categoryName}
                                        </td>
                                        <td className="px-6 py-4">
                                            R$ {product.price.toFixed(2)}
                                        </td>
                                        <td className="px-6 py-4">
                                            {product.stock}
                                        </td>
                                        {isAdmin && (
                                            <td className="px-6 py-4 flex gap-2 justify-end">
                                                <button
                                                    onClick={() =>
                                                        openEdit(product)
                                                    }
                                                    className="text-gray-600 hover:text-gray-900"
                                                >
                                                    <Pencil size={16} />
                                                </button>
                                                <button
                                                    onClick={() =>
                                                        handleDelete(product.id)
                                                    }
                                                    className="text-red-400 hover:text-red-600"
                                                >
                                                    <Trash2 size={16} />
                                                </button>
                                            </td>
                                        )}
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    )}
                </div>
            </div>
        </div>
    );
}
