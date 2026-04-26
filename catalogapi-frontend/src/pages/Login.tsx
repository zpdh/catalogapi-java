import { useState } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { useNavigate, Link } from "react-router-dom";
import { useAuthStore } from "../store/authStore";
import { login } from "../api/auth";

const schema = z.object({
    email: z.string().email("Invalid email"),
    password: z.string().min(1, "Password is required"),
});

type FormData = z.infer<typeof schema>;

export default function Login() {
    const navigate = useNavigate();
    const setAuth = useAuthStore((state) => state.setAuth);
    const [errorMessage, setErrorMessage] = useState<string | null>(null);

    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting },
    } = useForm<FormData>({
        resolver: zodResolver(schema),
    });

    const onSubmit = async (data: FormData) => {
        try {
            const response = await login(data);
            setAuth(response);
            navigate("/dashboard");
        } catch(error) {
            setErrorMessage("Invalid email or password");
        }
    };

    return (
        <div className="min-h-screen bg-gray-100 flex items-center justify-center">
            <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
                <h1 className="text-2xl font-bold mb-6 text-center">Login</h1>
                <form
                    onSubmit={handleSubmit(onSubmit)}
                    className="flex flex-col gap-4"
                >
                    <div>
                        <label className="block text-sm font-medium mb-1">
                            Email
                        </label>
                        <input
                            {...register("email")}
                            type="email"
                            className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gray-400"
                        />
                        {errors.email && (
                            <p className="text-red-500 text-sm mt-1">
                                {errors.email.message}
                            </p>
                        )}
                    </div>
                    <div>
                        <label className="block text-sm font-medium mb-1">
                            Password
                        </label>
                        <input
                            {...register("password")}
                            type="password"
                            className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gray-400"
                        />
                        {errors.password && (
                            <p className="text-red-500 text-sm mt-1">
                                {errors.password.message}
                            </p>
                        )}
                    </div>
                    {errorMessage && (
                        <p className="text-red-500 text-sm text-center">
                            {errorMessage}
                        </p>
                    )}
                    <button
                        type="submit"
                        disabled={isSubmitting}
                        className="bg-gray-900 text-white py-2 rounded hover:bg-gray-700 disabled:opacity-50"
                    >
                        {isSubmitting ? "Logging in..." : "Login"}
                    </button>
                </form>
                <p className="text-center text-sm mt-4">
                    Don't have an account?{" "}
                    <Link to="/register" className="underline">
                        Register
                    </Link>
                </p>
            </div>
        </div>
    );
}
