export const api = (url, options = {}) => {
  let token = null; // declare here so it's visible everywhere

  if (process.client) {
    token = localStorage.getItem("token");
  }

  return $fetch(url, {
    baseURL: "http://localhost:8080",
    headers: {
      ...(options.headers || {}),
      Authorization: token ? `Bearer ${token}` : "",
    },
    ...options,
  });
};
