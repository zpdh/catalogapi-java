import { useState } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import Navbar from "../components/shared/Navbar";
import { useAuthStore } from "../store/authStore";
import {
    useCategories,
    useCreateCategory,
    useUpdateCategory,
    useDeleteCategory,
} from "../hooks/useCategories";
import type { CategoryResponse } from "../types";
import { Pencil, Trash2, Plus, X } from "lucide-react";

const schema = z.object({
    name: z.string().min(1, "Name is required").max(100),
    description: z.string().max(255).optional(),
});

type FormData = z.infer<typeof schema>;

export default function Categories() {
    const isAdmin = useAuthStore((state) => state.isAdmin());
    const { data: categories, isLoading } = useCategories();
    const createCategory = useCreateCategory();
    const updateCategory = useUpdateCategory();
    const deleteCategory = useDeleteCategory();

    const [showForm, setShowForm] = useState(false);
    const [editing, setEditing] = useState<CategoryResponse | null>(null);

    const {
        register,
        handleSubmit,
        reset,
        formState: { errors, isSubmitting },
    } = useForm<FormData>({
        resolver: zodResolver(schema),
    });

    const openCreate = () => {
        setEditing(null);
        reset({ name: "", description: "" });
        setShowForm(true);
    };

    const openEdit = (category: CategoryResponse) => {
        setEditing(category);
        reset({ name: category.name, description: category.description });
        setShowForm(true);
    };

    const onSubmit = async (data: FormData) => {
        if (editing) {
            await updateCategory.mutateAsync({ id: editing.id, request: data });
        } else {
            await createCategory.mutateAsync(data);
        }
        setShowForm(false);
        reset();
    };

    const handleDelete = async (id: number) => {
        if (confirm("Delete this category?")) {
            await deleteCategory.mutateAsync(id);
        }
    };

    return (
        <div className="min-h-screen bg-gray-100">
            <Navbar />
            <div className="max-w-4xl mx-auto mt-10 p-6">
                <div className="flex items-center justify-between mb-6">
                    <h1 className="text-2xl font-bold">Categories</h1>
                    {isAdmin && (
                        <button
                            onClick={openCreate}
                            className="flex items-center gap-2 bg-gray-900 text-white px-4 py-2 rounded hover:bg-gray-700"
                        >
                            <Plus size={16} /> New Category
                        </button>
                    )}
                </div>

                {showForm && (
                    <div className="bg-white rounded-lg shadow-md p-6 mb-6">
                        <div className="flex justify-between items-center mb-4">
                            <h2 className="text-lg font-semibold">
                                {editing ? "Edit Category" : "New Category"}
                            </h2>
                            <button onClick={() => setShowForm(false)}>
                                <X size={18} />
                            </button>
                        </div>
                        <form
                            onSubmit={handleSubmit(onSubmit)}
                            className="flex flex-col gap-4"
                        >
                            <div>
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
                            <div>
                                <label className="block text-sm font-medium mb-1">
                                    Description
                                </label>
                                <input
                                    {...register("description")}
                                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gray-400"
                                />
                            </div>
                            <button
                                type="submit"
                                disabled={isSubmitting}
                                className="bg-gray-900 text-white py-2 rounded hover:bg-gray-700 disabled:opacity-50"
                            >
                                {isSubmitting ? "Saving..." : "Save"}
                            </button>
                        </form>
                    </div>
                )}

                <div className="bg-white rounded-lg shadow-md overflow-hidden">
                    {isLoading ? (
                        <p className="p-6 text-center text-gray-500">
                            Loading...
                        </p>
                    ) : categories?.length === 0 ? (
                        <p className="p-6 text-center text-gray-500">
                            No categories found.
                        </p>
                    ) : (
                        <table className="w-full">
                            <thead className="bg-gray-50 border-b">
                                <tr>
                                    <th className="text-left px-6 py-3 text-sm font-medium text-gray-500">
                                        Name
                                    </th>
                                    <th className="text-left px-6 py-3 text-sm font-medium text-gray-500">
                                        Description
                                    </th>
                                    {isAdmin && <th className="px-6 py-3" />}
                                </tr>
                            </thead>
                            <tbody className="divide-y">
                                {categories?.map((category) => (
                                    <tr
                                        key={category.id}
                                        className="hover:bg-gray-50"
                                    >
                                        <td className="px-6 py-4 font-medium">
                                            {category.name}
                                        </td>
                                        <td className="px-6 py-4 text-gray-500">
                                            {category.description}
                                        </td>
                                        {isAdmin && (
                                            <td className="px-6 py-4 flex gap-2 justify-end">
                                                <button
                                                    onClick={() =>
                                                        openEdit(category)
                                                    }
                                                    className="text-gray-600 hover:text-gray-900"
                                                >
                                                    <Pencil size={16} />
                                                </button>
                                                <button
                                                    onClick={() =>
                                                        handleDelete(
                                                            category.id,
                                                        )
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
