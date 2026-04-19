import { useLocalStorage } from "@vueuse/core";

const TOKEN_KEY = "mis-admin-token";

const tokenStorage = useLocalStorage<string | null>(TOKEN_KEY, null);

export const getToken = () => tokenStorage.value;

export const setToken = (token: string) => {
  tokenStorage.value = token;
};

export const removeToken = () => {
  tokenStorage.value = null;
};
