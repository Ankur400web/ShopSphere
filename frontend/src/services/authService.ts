import {api} from "./api";

interface RegisterRequest {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    roleId: number;
}

interface UserResponse {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    roleName: string;
    status: string;
    createdAt: string;
    updatedAt: string;
}

interface LoginRequest {
    email: string;
    password: string;
}

interface LoginResponse {
    accessToken: string;
    refreshToken: string;
    tokenType: string;
    roleId: number;
    email: string;
    role: string;
    firstName: string;
}

export const registerUser = (data: RegisterRequest) => {
    return api.post<UserResponse>("/users/register", data);
};

export const loginUser = (data: LoginRequest) =>
    api.post<LoginResponse>("/auth/login", data);


export const logoutUser = async (): Promise<void> => {
    const refreshToken = localStorage.getItem("refreshToken");

    if (!refreshToken) {
        return;
    }

    const response = await fetch(
        "http://localhost:8080/api/v1/auth/logout",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                refreshToken,
            }),
        }
    );

    if (!response.ok) {
        throw new Error("Logout failed");
    }
};