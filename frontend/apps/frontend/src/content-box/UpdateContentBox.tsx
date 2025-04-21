import React from 'react';
import "./ContentBox.css";
import {Product} from "../entities/Product";

interface ContentBoxProps {
  onSubmit: (id: number) => void;
  content: Product;
}

// @ts-ignore
const UpdateContentBox: React.FC<ContentBoxProps> = ({onSubmit, content }) => {
  const [product, setProduct] = React.useState<Product>(content);

  const handleSubmit = () => {
      onSubmit(product);
  }

  return (
    <div className="content-box">
      <input
        type="text"
        value={product.name}
        onChange={(e) => setProduct({...product, name: e.target.value})}
        placeholder="Product Name"
      />
      <input
        type="text"
        value={product.description}
        onChange={(e) => setProduct({...product, description: e.target.value})}
        placeholder="Product Description"
      />
      <button onClick={handleSubmit}>Update</button>
    </div>
  )
}

export default UpdateContentBox;
