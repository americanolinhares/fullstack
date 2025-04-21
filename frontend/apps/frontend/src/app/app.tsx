

import "./app.css";

import React from 'react';
import {Product} from "../entities/Product";
import VerticalContainer from '../vertical-container/VerticalContainer';
import ReadContentBox from "../content-box/ReadContentBox";
import CreateContentBox from "../content-box/CreateContentBox";
import DeleteContentBox from "../content-box/DeleteContentBox";
import UpdateContentBox from "../content-box/UpdateContentBox";

export function App() {

  const [products, setProducts] = React.useState<Product[]>([]);

  React.useEffect(() => {
    fetch("http://localhost:8080/product", {
      method: "GET"
    }).then(response => {
      if (!response.ok) {
        return null;
      }
      return response.json();
    }).then(data => {
      if (data !== null) {
        setProducts(data);
      }
    })
  }, []);

  const handleCreateSubmit = (name: string, description: string) => {
    const newProduct = {
      name,
      description
    }
    fetch("http://localhost:8080/product", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(newProduct)
    }).then(response => {
      if (!response.ok) {
        return null;
      }
      return response.json();
    }).then(data => {
      if (data !== null) {
        setProducts([...products, data]);
      }
    })
  }

  const handleDeleteSubmit = (id: number) => {
    fetch(`http://localhost:8080/product/${id}`, {
      method: "DELETE"
    }).then(response => {
      if (!response.ok) {
        return null;
      }
      return response.json();
    }).then(data => {
      if (data !== null) {
        setProducts(products.filter(product => product.id !== id));
      }
    })
  }

  const handleUpdateSubmit = (productToUpdate: Product) => {
    fetch(`http://localhost:8080/product/${productToUpdate.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({name: productToUpdate.name, description: productToUpdate.description} as Product)
    }).then(response => {
      if (!response.ok) {
        return null;
      }
      return response.json();
    }).then(data => {
      if (data !== null) {
        setProducts(products.map(product => product.id === data.id ? {
          ...product,
          name: data.name,
          description: data.description
        } : product));
      }
    })
  }

  return (
    <div className="main-component">
      <div>
        <VerticalContainer>
          {
            <div>
              <h2>Create</h2>
              <CreateContentBox onSubmit={handleCreateSubmit}/>
            </div>
          }
        </VerticalContainer>
      </div>
      <div>
        <VerticalContainer>
          {
            <div>
              <h2>Read</h2>
              {
                products.map(product => <ReadContentBox content={product}
                                                        key={`${product.id}-${product.name}-${product.description}`}/>)
              }
            </div>
          }
        </VerticalContainer>
      </div>
      <div>
        <VerticalContainer>
          {
            <div>
              <h2>Update</h2>
              {
                products.map(product => <UpdateContentBox content={product}
                                                          onSubmit={handleUpdateSubmit}
                                                          key={`${product.id}-${product.name}-${product.description}`}/>)
              }
            </div>
          }
        </VerticalContainer>
      </div>
      <div>
        <VerticalContainer>
          {
            <div>
              <h2>Delete</h2>
              {
                products.map(product => <DeleteContentBox content={product}
                                                          onSubmit={handleDeleteSubmit}
                                                          key={`${product.id}-${product.name}-${product.description}`}/>)
              }
            </div>
          }
        </VerticalContainer>
      </div>
    </div>
  );
}

export default App;

