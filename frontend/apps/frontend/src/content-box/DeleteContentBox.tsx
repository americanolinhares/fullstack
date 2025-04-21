import React from 'react';
import "./ContentBox.css";
import {Product} from "../entities/Product";

interface ContentBoxProps {
  onSubmit: (id: number) => void;
  content: Product;
}

// @ts-ignore
const DeleteContentBox: React.FC<ContentBoxProps> = ({onSubmit, content }) => {
  const [product] = React.useState<Product>(content);

  const handleSubmit = () => {
      onSubmit(product.id);
  }

  return (
    <div className="content-box">
      <p>Name: {product.name}</p>
      <p>Description: {product.description}</p>
      <button onClick={handleSubmit}>Delete</button>
    </div>
  )
}

export default DeleteContentBox;
