import React from 'react';
import {Product} from "../entities/Product";
import "./ContentBox.css";

interface ContentBoxProps {
  content: Product;
}

const ReadContentBox: React.RC<ContentBoxProps> = ({content}) => {
  const [product, setProduct] = React.useState<Product>(content);

  return (
    <div className="content-box">
      <p>Name: {product.name}</p>
      <p>Description: {product.description}</p>
    </div>
  )
}

export default ReadContentBox;
