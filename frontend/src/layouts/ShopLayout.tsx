import { Outlet } from "react-router-dom";
import Navbar from "../components/layout/NavBar.tsx";

const ShopLayout = () => {
    return (
        <>
            <Navbar />
            <Outlet />
        </>
    );
};

export default ShopLayout;