import { Link, useNavigate } from "react-router-dom";
import { useAuthStore } from "../../store/authStore";
import { LogOut, Package, Tag, LayoutDashboard } from "lucide-react";

export default function Navbar() {
    const { email, role, clearAuth } = useAuthStore();
    const navigate = useNavigate();

    const handleLogout = () => {
        clearAuth();
        navigate("/login");
    };

    return (
        <nav className="bg-gray-900 text-white px-6 py-4 flex items-center justify-between">
            <div className="flex items-center gap-6">
                <span className="font-bold text-lg">CatalogAPI</span>
                <Link
                    to="/dashboard"
                    className="flex items-center gap-1 hover:text-gray-300"
                >
                    <LayoutDashboard size={16} />
                    Dashboard
                </Link>
                <Link
                    to="/categories"
                    className="flex items-center gap-1 hover:text-gray-300"
                >
                    <Tag size={16} />
                    Categories
                </Link>
                <Link
                    to="/products"
                    className="flex items-center gap-1 hover:text-gray-300"
                >
                    <Package size={16} />
                    Products
                </Link>
            </div>
            <div className="flex items-center gap-4">
                <span className="text-sm text-gray-400">
                    {email} — {role}
                </span>
                <button
                    onClick={handleLogout}
                    className="flex items-center gap-1 hover:text-red-400"
                >
                    <LogOut size={16} />
                    Logout
                </button>
            </div>
        </nav>
    );
}
