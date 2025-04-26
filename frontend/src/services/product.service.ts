import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/';

class ProductService {

  getAllProducts() {
    return axios.get(API_URL + 'product', { headers: authHeader() });
  }

  deleteProduct(id: number) {
    return axios.delete(API_URL + `product/${id}`, { headers: authHeader() });
  }

  updateProduct(product: { id: number; name: string; description: string }) {
    return axios.put(API_URL + `product/${product.id}`, product, { headers: authHeader() });
  }

  createProduct(product: { name: string; description: string }) {
    return axios.post(API_URL + 'product', product, { headers: authHeader() });
  }
}

export default new ProductService();
