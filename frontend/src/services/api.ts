const API_BASE_URL = "http://localhost:8080/api/v1";

const refreshAccessToken = async (): Promise<string> => {
    const refreshToken = localStorage.getItem("refreshToken");

    if (!refreshToken) {
        throw new Error("No refresh token available");
    }

    const response = await fetch(`${API_BASE_URL}/auth/refresh`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            refreshToken,
        }),
    });

    if (!response.ok) {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("user");

        window.location.href = "/login";

        throw new Error("Refresh token expired or invalid");
    }

    const data = await response.json();

    localStorage.setItem("accessToken", data.accessToken);

    return data.accessToken;
};

const request = async <T>(
    endpoint: string,
    options: RequestInit = {}
): Promise<T> => {

    const accessToken = localStorage.getItem("accessToken");

    const headers = new Headers(options.headers);

    headers.set("Content-Type", "application/json");

    if (accessToken) {
        headers.set("Authorization", `Bearer ${accessToken}`);
    }

    let response = await fetch(`${API_BASE_URL}${endpoint}`, {
        ...options,
        headers,
    });

    if (response.status === 401) {
        const newAccessToken = await refreshAccessToken();

        headers.set("Authorization", `Bearer ${newAccessToken}`);

        response = await fetch(`${API_BASE_URL}${endpoint}`, {
            ...options,
            headers,
        });
    }

    if (!response.ok) {
        throw new Error(`Request failed with status ${response.status}`);
    }

    return response.json();
};

export const api = {

    async get<T>(endpoint: string): Promise<T> {
        return request<T>(endpoint, {
            method: "GET",
        });
    },

    async post<T>(endpoint: string, data: unknown): Promise<T> {
        return request<T>(endpoint, {
            method: "POST",
            body: JSON.stringify(data),
        });
    },

    async put<T>(endpoint: string, data: unknown): Promise<T> {
        return request<T>(endpoint, {
            method: "PUT",
            body: JSON.stringify(data),
        });
    },

    async patch<T>(endpoint: string, data: unknown): Promise<T> {
        return request<T>(endpoint, {
            method: "PATCH",
            body: JSON.stringify(data),
        });
    },

    async delete<T>(endpoint: string): Promise<T> {
        return request<T>(endpoint, {
            method: "DELETE",
        });
    },
};