import { BrowserRouter, Route, Routes } from "react-router-dom";
import RegisterPage from "../pages/auth/RegisterPage";

const AppRoutes = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/register" element={<RegisterPage />} />
            </Routes>
        </BrowserRouter>
    );
};

export default AppRoutes;