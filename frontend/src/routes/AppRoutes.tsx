import { BrowserRouter, Route, Routes } from "react-router-dom";
import RegisterPage from "../pages/auth/RegisterPage";
import HomePage from "../pages/home/HomePage";
import AuthLayout from "../layouts/AuthLayout";
import ShopLayout from "../layouts/ShopLayout";
import LoginPage from "../pages/auth/LoginPage";
import ProfilePage from "../pages/profile/ProfilePage";

const AppRoutes = () => {
    return (
        <BrowserRouter>
            <Routes>

                <Route element={<ShopLayout />}>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/profile" element={<ProfilePage />} />
                </Route>

                <Route element={<AuthLayout />}>
                    <Route path="/register" element={<RegisterPage />} />
                    <Route path="/login" element={<LoginPage />} />
                </Route>

            </Routes>
        </BrowserRouter>
    );
};

export default AppRoutes;