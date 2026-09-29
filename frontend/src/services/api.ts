const API_BASE_URL = "http://localhost:8080/api/v1";

const refreshAccessToken = async (): Promise<string> => {
    const refreshToken = localStorage.getItem("refreshToken");

    if (!refreshToken) {
        throw new Error("No refresh token available");
    }

    const response = await fetch(
        `${API_BASE_URL}/auth/refresh`,
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
        throw new Error("Refresh token expired or invalid");
    }

    const data = await response.json();

    localStorage.setItem("accessToken", data.accessToken);

    return data.accessToken;
};

export const api = {
    async post<T>(endpoint: string, data: unknown): Promise<T> {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error(`Request failed with status ${response.status}`);
        }

        return response.json();
    },

    async get<T>(endpoint: string): Promise<T> {
        const token = localStorage.getItem("accessToken");

        let response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        });

        if (response.status === 401) {
            const newAccessToken = await refreshAccessToken();

            response = await fetch(`${API_BASE_URL}${endpoint}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${newAccessToken}`,
                },
            });
        }

        if (!response.ok) {
            throw new Error(`Request failed with status ${response.status}`);
        }

        return response.json();
    },
};