import SearchBar from "../common/SearchBar.tsx";
import NavLink from "../common/NavLink.tsx";
import IconButton from "../common/IconButton";
import { Menu, ShoppingCart, User, X, LogOut } from "lucide-react";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import { logoutUser } from "../../services/authService";

function NavBar() {
    const [menuOpen, setMenuOpen] = useState(false);
    const [accountOpen, setAccountOpen] = useState(false);

    const navigate = useNavigate();

    const storedUser = localStorage.getItem("user");

    const user = storedUser
        ? JSON.parse(storedUser)
        : null;

    const handleLogout = async () => {
        try {
            await logoutUser();
        } catch (error) {
            console.error("Logout failed:", error);
        } finally {
            localStorage.removeItem("accessToken");
            localStorage.removeItem("refreshToken");
            localStorage.removeItem("user");

            setAccountOpen(false);
            navigate("/login");
        }
    };

    return (
        <nav className="border-b border-border bg-surface">

            {/* Container */}
            <div className="mx-auto flex max-w-7xl items-center gap-8 px-6 py-4">

                {/* Brand */}
                <div>
                    <a
                        href="/"
                        className="text-xl font-bold text-text-primary"
                    >
                        <span>ShopSphere</span>
                    </a>
                </div>

                {/* Navigation */}
                <div className="hidden items-center gap-6 md:flex">
                    <NavLink label="Home" href="/" />
                    <NavLink label="Categories" href="/categories" />
                    <NavLink label="Products" href="/products" />
                </div>

                {/* Search */}
                <div className="hidden flex-1 md:block">
                    <SearchBar />
                </div>

                {/* Actions */}
                <div className="flex items-center gap-2">

                    {/* Account */}
                    {user ? (
                        <div className="relative">

                            <button
                                type="button"
                                onClick={() =>
                                    setAccountOpen(!accountOpen)
                                }
                                className="flex items-center gap-2 px-2 py-1"
                            >
                                <div className="leading-tight text-left">
                                    <p className="text-xs text-text-secondary">
                                        Hello,
                                    </p>

                                    <p className="text-sm font-semibold text-text-primary">
                                        {user.firstName}
                                    </p>
                                </div>
                            </button>

                            {/* Account Dropdown */}
                            {accountOpen && (
                                <div className="absolute right-0 top-full z-50 mt-2 w-44 rounded-lg border border-border bg-surface py-2 shadow-lg">

                                    <Link
                                        to="/profile"
                                        onClick={() =>
                                            setAccountOpen(false)
                                        }
                                        className="block px-4 py-2 text-sm text-text-primary hover:bg-background"
                                    >
                                        Profile
                                    </Link>

                                    <button
                                        type="button"
                                        onClick={handleLogout}
                                        className="flex w-full items-center gap-2 px-4 py-2 text-sm text-error hover:bg-background"
                                    >
                                        <LogOut size={16} />
                                        Logout
                                    </button>

                                </div>
                            )}

                        </div>
                    ) : (
                        <Link to="/login">
                            <IconButton label="Account">
                                <User size={20} />
                            </IconButton>
                        </Link>
                    )}

                    {/* Shopping Cart */}
                    <IconButton label="Shopping cart">
                        <ShoppingCart size={20} />
                    </IconButton>

                    {/* Mobile Menu */}
                    <button
                        type="button"
                        aria-label="Toggle navigation menu"
                        className="rounded-lg p-2 text-text-secondary hover:bg-background hover:text-primary-600 md:hidden"
                        onClick={() => setMenuOpen(!menuOpen)}
                    >
                        {menuOpen ? (
                            <X size={22} />
                        ) : (
                            <Menu size={22} />
                        )}
                    </button>

                </div>

            </div>

            {/* Mobile Navigation */}
            {menuOpen && (
                <div className="border-t border-border bg-surface md:hidden">
                    <div className="mx-auto flex max-w-7xl flex-col gap-4 px-6 py-4">
                        <NavLink label="Home" href="/" />
                        <NavLink label="Categories" href="/categories" />
                        <NavLink label="Products" href="/products" />
                    </div>
                </div>
            )}

        </nav>
    );
}

export default NavBar;