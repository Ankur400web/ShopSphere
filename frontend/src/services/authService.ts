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

export const registerUser = (data: RegisterRequest) => {
    return api.post<UserResponse>("/users/register", data);
};