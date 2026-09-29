import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { LogOut } from "lucide-react";

import { logoutUser } from "../../services/authService";

import {
    getCurrentUser,
    type UserResponse,
} from "../../services/UserService.ts";

const ProfilePage = () => {
    const [user, setUser] = useState<UserResponse | null>(null);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState("");

    const navigate = useNavigate();

    const handleLogout = async () => {
        try {
            await logoutUser();
        } catch (error) {
            console.error("Logout failed:", error);
        } finally {
            localStorage.removeItem("accessToken");
            localStorage.removeItem("refreshToken");
            localStorage.removeItem("user");

            navigate("/login");
        }
    };

    useEffect(() => {
        const loadUser = async () => {
            try {
                const response = await getCurrentUser();
                setUser(response);
            } catch (error) {
                console.error("Failed to load profile:", error);
                setError("Failed to load your profile.");
            } finally {
                setIsLoading(false);
            }
        };

        loadUser();
    }, []);

    if (isLoading) {
        return (
            <main className="min-h-screen bg-background px-4 py-10">
                <div className="mx-auto max-w-4xl">
                    <p className="text-text-secondary">
                        Loading profile...
                    </p>
                </div>
            </main>
        );
    }

    if (error) {
        return (
            <main className="min-h-screen bg-background px-4 py-10">
                <div className="mx-auto max-w-4xl">
                    <p className="text-error">{error}</p>
                </div>
            </main>
        );
    }

    if (!user) {
        return null;
    }

    return (
        <main className="min-h-screen bg-background px-4 py-10">
            <div className="mx-auto max-w-4xl">

                {/* Page Header */}
                <div className="mb-8">
                    <h1 className="text-3xl font-bold text-text-primary">
                        My Profile
                    </h1>

                    <p className="mt-2 text-text-secondary">
                        View your ShopSphere account information.
                    </p>
                </div>

                {/* Profile Card */}
                <div className="rounded-2xl border border-border bg-surface p-8 shadow-sm">

                    {/* Profile Header */}
                    <div className="flex items-center gap-5 border-b border-border pb-6">

                        {/* Avatar */}
                        <div className="flex h-16 w-16 items-center justify-center rounded-full bg-primary-100 text-xl font-bold text-primary-600">
                            {user.firstName.charAt(0).toUpperCase()}
                        </div>

                        {/* Name and Role */}
                        <div>
                            <h2 className="text-xl font-semibold text-text-primary">
                                {user.firstName} {user.lastName}
                            </h2>

                            <p className="mt-1 text-sm text-text-secondary">
                                {user.roleName}
                            </p>
                        </div>
                    </div>

                    {/* User Information */}
                    <div className="grid gap-6 py-6 md:grid-cols-2">

                        {/* Email */}
                        <div>
                            <p className="text-sm text-text-muted">
                                Email Address
                            </p>

                            <p className="mt-1 font-medium text-text-primary">
                                {user.email}
                            </p>
                        </div>

                        {/* Account Status */}
                        <div>
                            <p className="text-sm text-text-muted">
                                Account Status
                            </p>

                            <p className="mt-1 font-medium text-success">
                                {user.status}
                            </p>
                        </div>

                        {/* Member Since */}
                        <div>
                            <p className="text-sm text-text-muted">
                                Member Since
                            </p>

                            <p className="mt-1 font-medium text-text-primary">
                                {new Date(user.createdAt).toLocaleDateString()}
                            </p>
                        </div>

                        {/* Last Updated */}
                        <div>
                            <p className="text-sm text-text-muted">
                                Last Updated
                            </p>

                            <p className="mt-1 font-medium text-text-primary">
                                {new Date(user.updatedAt).toLocaleDateString()}
                            </p>
                        </div>
                    </div>

                    <div className="border-t border-border pt-6">
                        <button
                            type="button"
                            onClick={handleLogout}
                            className="inline-flex items-center gap-2 rounded-lg bg-error px-4 py-3 font-semibold text-white transition hover:opacity-90"
                        >
                            <LogOut size={18} />
                            Logout
                        </button>
                    </div>
                </div>
            </div>
        </main>
    );
};

export default ProfilePage;