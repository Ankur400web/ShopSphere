import { BrowserRouter, Route, Routes } from "react-router-dom";
import RegisterPage from "../pages/auth/RegisterPage";
import HomePage from "../pages/home/HomePage";
import AuthLayout from "../layouts/AuthLayout";
import ShopLayout from "../layouts/ShopLayout";

const AppRoutes = () => {
    return (
        <BrowserRouter>
            <Routes>

                <Route element={<ShopLayout />}>
                    <Route path="/" element={<HomePage />} />
                </Route>

                <Route element={<AuthLayout />}>
                    <Route path="/register" element={<RegisterPage />} />
                </Route>

            </Routes>
        </BrowserRouter>
    );
};

export default AppRoutes;