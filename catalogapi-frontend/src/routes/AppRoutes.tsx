import { Routes, Route, Navigate } from "react-router-dom";
import ProtectedRoute from "../components/shared/ProtectedRoute";
import Login from "../pages/Login";
import Categories from "../pages/Categories";
import Products from "../pages/Products";
import Dashboard from "../pages/Dashboard";
import Register from "../pages/Register";
import Monitor from "../pages/Monitor";

export default function AppRoutes() {
    return (
        <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route element={<ProtectedRoute />}>
                <Route path="/dashboard" element={<Dashboard />} />
                <Route path="/categories" element={<Categories />} />
                <Route path="/products" element={<Products />} />
                <Route path="/monitor" element={<Monitor />} />
            </Route>
            <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
    );
}
