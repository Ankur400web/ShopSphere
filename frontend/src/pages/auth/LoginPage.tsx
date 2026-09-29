    import { useState } from "react";
    import type { ChangeEvent, FormEvent } from "react";
    import { Link, useNavigate } from "react-router-dom";
    import { Eye, EyeOff } from "lucide-react";
    import { loginUser } from "../../services/authService";

    const LoginPage = () => {
        const navigate = useNavigate();

        const [formData, setFormData] = useState({
            email: "",
            password: "",
        });

        const [isLoading, setIsLoading] = useState(false);
        const [error, setError] = useState("");
        const [showPassword, setShowPassword] = useState(false);

        const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
            const { name, value } = event.target;

            setFormData((previous) => ({
                ...previous,
                [name]: value,
            }));
        };

        const handleSubmit = async (event: FormEvent) => {
            event.preventDefault();

            setError("");
            setIsLoading(true);

            try {
                const response = await loginUser({
                    email: formData.email,
                    password: formData.password,
                });
                localStorage.setItem("accessToken", response.accessToken);
                localStorage.setItem("refreshToken", response.refreshToken);

                localStorage.setItem(
                    "user",
                    JSON.stringify({
                        firstName: response.firstName,
                        email: response.email,
                        role: response.role,
                    })
                );

                navigate("/");
            } catch (error) {
                console.error("Login failed:", error);
                setError("Invalid email or password.");
            } finally {
                setIsLoading(false);
            }
        };

        return (
            <div className="min-h-screen bg-background flex items-center justify-center px-4 py-10">
                <div className="w-full max-w-md rounded-2xl bg-surface p-8 shadow-lg border border-border">

                    {/* Header */}
                    <div className="text-center mb-8">
                        <h1 className="text-3xl font-bold text-text-primary">
                            Welcome back
                        </h1>

                        <p className="mt-2 text-sm text-text-secondary">
                            Login to your ShopSphere account.
                        </p>
                    </div>

                    <form onSubmit={handleSubmit}>

                        {/* Error */}
                        {error && (
                            <p className="mb-4 rounded-lg border border-error/20 bg-error/10 px-4 py-3 text-sm text-error">
                                {error}
                            </p>
                        )}

                        {/* Email */}
                        <div>
                            <label
                                htmlFor="email"
                                className="mb-2 block text-sm font-medium text-text-primary"
                            >
                                Email Address
                            </label>

                            <input
                                id="email"
                                type="email"
                                name="email"
                                placeholder="Enter your email"
                                value={formData.email}
                                onChange={handleChange}
                                required
                                className="w-full rounded-lg border border-border bg-surface px-4 py-3 text-text-primary outline-none transition placeholder:text-text-muted focus:border-primary-500 focus:ring-2 focus:ring-primary-500/20"
                            />
                        </div>

                        {/* Password */}
                        <div className="mt-4">
                            <label
                                htmlFor="password"
                                className="mb-2 block text-sm font-medium text-text-primary"
                            >
                                Password
                            </label>

                            <div className="relative">
                                <input
                                    id="password"
                                    type={showPassword ? "text" : "password"}
                                    name="password"
                                    placeholder="Enter your password"
                                    value={formData.password}
                                    onChange={handleChange}
                                    required
                                    className="w-full rounded-lg border border-border bg-surface px-4 py-3 pr-12 text-text-primary outline-none transition placeholder:text-text-muted focus:border-primary-500 focus:ring-2 focus:ring-primary-500/20"
                                />

                                <button
                                    type="button"
                                    onClick={() =>
                                        setShowPassword(!showPassword)
                                    }
                                    className="absolute right-3 top-1/2 -translate-y-1/2 text-text-muted transition hover:text-text-primary"
                                    aria-label={
                                        showPassword
                                            ? "Hide password"
                                            : "Show password"
                                    }
                                >
                                    {showPassword ? (
                                        <EyeOff size={20} />
                                    ) : (
                                        <Eye size={20} />
                                    )}
                                </button>
                            </div>
                        </div>

                        {/* Login button */}
                        <button
                            type="submit"
                            disabled={isLoading}
                            className="mt-6 w-full rounded-lg bg-primary-600 px-4 py-3 font-semibold text-white transition hover:bg-primary-700 disabled:cursor-not-allowed disabled:opacity-60"
                        >
                            {isLoading ? "Logging in..." : "Login"}
                        </button>

                    </form>

                    {/* Register */}
                    <p className="mt-6 text-center text-sm text-text-secondary">
                        Don't have an account?{" "}
                        <Link
                            to="/register"
                            className="font-medium text-primary-600 transition hover:text-primary-700"
                        >
                            Create an account
                        </Link>
                    </p>

                </div>
            </div>
        );
    };

    export default LoginPage;