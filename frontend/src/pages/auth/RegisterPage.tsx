import { FormEvent, useState } from "react";
import { Link } from "react-router-dom";
import { Eye, EyeOff } from "lucide-react";
import { registerUser } from "../../services/authService";

const RegisterPage = () => {
    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        confirmPassword: "",
    });

    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;

        setFormData((previous) => ({
            ...previous,
            [name]: value,
        }));
    };

    const handleSubmit = async (event: FormEvent) => {
        event.preventDefault();

        setError("");
        setSuccess("");
        setIsLoading(true);

        if (formData.password !== formData.confirmPassword) {
            setError("Passwords do not match");
            setIsLoading(false);
            return;
        }

        try {
            await registerUser({
                firstName: formData.firstName,
                lastName: formData.lastName,
                email: formData.email,
                password: formData.password,
                roleId: 1,
            });

            setSuccess("Account created successfully!");
        } catch (error) {
            console.error("Registration failed:", error);
            setError("Registration failed. Please try again.");
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-background flex items-center justify-center px-4 py-10">
            <div className="w-full max-w-md rounded-2xl bg-surface p-8 shadow-lg border border-border">

                <div className="text-center mb-8">
                    <h1 className="text-3xl font-bold text-text-primary">
                        Create your account
                    </h1>

                    <p className="mt-2 text-sm text-text-secondary">
                        Join ShopSphere and start shopping today.
                    </p>
                </div>

                <form onSubmit={handleSubmit}>

                    {error && (
                        <p className="mb-4 rounded-lg border border-error/20 bg-error/10 px-4 py-3 text-sm text-error">
                            {error}
                        </p>
                    )}

                    {success && (
                        <p className="mb-4 rounded-lg border border-success/20 bg-success/10 px-4 py-3 text-sm text-success">
                            {success}
                        </p>
                    )}

                    <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">

                        <div>
                            <label
                                htmlFor="firstName"
                                className="mb-2 block text-sm font-medium text-text-primary"
                            >
                                First Name
                            </label>

                            <input
                                id="firstName"
                                type="text"
                                name="firstName"
                                placeholder="First Name"
                                value={formData.firstName}
                                onChange={handleChange}
                                className="w-full rounded-lg border border-border bg-surface px-4 py-3 text-text-primary outline-none transition placeholder:text-text-muted focus:border-primary-500 focus:ring-2 focus:ring-primary-500/20"
                            />
                        </div>

                        <div>
                            <label
                                htmlFor="lastName"
                                className="mb-2 block text-sm font-medium text-text-primary"
                            >
                                Last Name
                            </label>

                            <input
                                id="lastName"
                                type="text"
                                name="lastName"
                                placeholder="Last Name"
                                value={formData.lastName}
                                onChange={handleChange}
                                className="w-full rounded-lg border border-border bg-surface px-4 py-3 text-text-primary outline-none transition placeholder:text-text-muted focus:border-primary-500 focus:ring-2 focus:ring-primary-500/20"
                            />
                        </div>

                    </div>

                    <div className="mt-4">
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
                            className="w-full rounded-lg border border-border bg-surface px-4 py-3 text-text-primary outline-none transition placeholder:text-text-muted focus:border-primary-500 focus:ring-2 focus:ring-primary-500/20"
                        />
                    </div>

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
                                placeholder="Create a password"
                                value={formData.password}
                                onChange={handleChange}
                                className="w-full rounded-lg border border-border bg-surface px-4 py-3 pr-12 text-text-primary outline-none transition placeholder:text-text-muted focus:border-primary-500 focus:ring-2 focus:ring-primary-500/20"
                            />

                            <button
                                type="button"
                                onClick={() => setShowPassword(!showPassword)}
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

                    <div className="mt-4">
                        <label
                            htmlFor="confirmPassword"
                            className="mb-2 block text-sm font-medium text-text-primary"
                        >
                            Confirm Password
                        </label>

                        <div className="relative">

                            <input
                                id="confirmPassword"
                                type={
                                    showConfirmPassword
                                        ? "text"
                                        : "password"
                                }
                                name="confirmPassword"
                                placeholder="Confirm your password"
                                value={formData.confirmPassword}
                                onChange={handleChange}
                                className="w-full rounded-lg border border-border bg-surface px-4 py-3 pr-12 text-text-primary outline-none transition placeholder:text-text-muted focus:border-primary-500 focus:ring-2 focus:ring-primary-500/20"
                            />

                            <button
                                type="button"
                                onClick={() =>
                                    setShowConfirmPassword(
                                        !showConfirmPassword
                                    )
                                }
                                className="absolute right-3 top-1/2 -translate-y-1/2 text-text-muted transition hover:text-text-primary"
                                aria-label={
                                    showConfirmPassword
                                        ? "Hide confirmed password"
                                        : "Show confirmed password"
                                }
                            >
                                {showConfirmPassword ? (
                                    <EyeOff size={20} />
                                ) : (
                                    <Eye size={20} />
                                )}
                            </button>

                        </div>
                    </div>

                    <button
                        type="submit"
                        disabled={isLoading}
                        className="mt-6 w-full rounded-lg bg-primary-600 px-4 py-3 font-semibold text-white transition hover:bg-primary-700 disabled:cursor-not-allowed disabled:opacity-60"
                    >
                        {isLoading
                            ? "Creating Account..."
                            : "Create Account"}
                    </button>

                </form>

                <p className="mt-6 text-center text-sm text-text-secondary">
                    Already have an account?{" "}
                    <Link
                        to="/login"
                        className="font-medium text-primary-600 transition hover:text-primary-700"
                    >
                        Login
                    </Link>
                </p>

            </div>
        </div>
    );
};

export default RegisterPage;