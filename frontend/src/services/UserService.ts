import { api } from "./api";

export interface UserResponse {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    roleName: string;
    status: string;
    createdAt: string;
    updatedAt: string;
}

export const getCurrentUser = () => {
    return api.get<UserResponse>("/users/me");
};