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
    token: string;
    tokenType: string;
    roleId: number;
    email: string;
    role: string;
    firstName: string;
}

export const registerUser = (data: RegisterRequest) => {
    return api.post<UserResponse>("/users/register", data);
};

export const loginUser = (data: LoginRequest) => {
    return api.post<LoginResponse>("/auth/login", data);
};