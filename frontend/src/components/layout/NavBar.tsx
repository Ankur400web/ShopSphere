import SearchBar from "../common/SearchBar.tsx";
import NavLink from "../common/NavLink.tsx";
import IconButton from "../common/IconButton";
import { useState } from "react";
import { Menu, ShoppingCart, User, X } from "lucide-react";
import { Link } from "react-router-dom";

function NavBar() {
    const [menuOpen, setMenuOpen] = useState(false);
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
                    <Link to="/register">
                        <IconButton label="Account">
                            <User size={20} />
                        </IconButton>
                    </Link>

                    <IconButton label="Shopping cart">
                        <ShoppingCart size={20} />
                    </IconButton>

                    <button
                        type="button"
                        aria-label="Toggle navigation menu"
                        className="rounded-lg p-2 text-text-secondary hover:bg-background hover:text-primary-600 md:hidden"
                        onClick={() => setMenuOpen(!menuOpen)}
                    >
                        {menuOpen ? <X size={22} /> : <Menu size={22} />}
                    </button>
                </div>

            </div>

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