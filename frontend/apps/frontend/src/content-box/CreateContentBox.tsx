import React from 'react';
import "./ContentBox.css";

interface ContentBoxProps {
  onSubmit: (name: string, description: string) => void;
}

// @ts-ignore
const CreateContentBox: React.RC<ContentBoxProps> = ({onSubmit}) => {
  const [name, setName] = React.useState("");
  const [description, setDescription] = React.useState("");

  const handleSubmit = () => {
      onSubmit(name, description);
      setName("");
      setDescription("");
  }

  return (
    <div className="content-box">
      <input
        type="text"
        value={name}
        onChange={(e) => setName(e.target.value)}
        placeholder="Product Name"
      />
      <input
        type="text"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        placeholder="Product Description"
      />
      <button onClick={() => onSubmit(name, description)}>Create</button>
    </div>
  )
}

export default CreateContentBox;
