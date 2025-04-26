import { Component, ChangeEvent } from "react";
import UserService from "../services/product.service";
import EventBus from "../common/EventBus";

type Props = {};

type Product = {
    id: number;
    name: string;
    description: string;
};

type State = {
    products: Product[];
    selectedProduct: Product | null;
    newProduct: Product;
    errorMessage?: string;
};

export default class Dashboard extends Component<Props, State> {
    constructor(props: Props) {
        super(props);

        this.state = {
            products: [],
            selectedProduct: null,
            newProduct: { id: 0, name: "", description: "" },
        };
    }

    componentDidMount() {
        this.fetchProducts();
    }

    fetchProducts = () => {
        UserService.getAllProducts().then(
            (response) => {
                this.setState({ products: response.data });
            },
            (error) => {
                console.error("Error when finding Products:", error);
                if (error.response && error.response.status === 401) {
                    EventBus.dispatch("logout");
                }
            }
        );
    };

    handleDelete = (id: number) => {
        UserService.deleteProduct(id).then(() => {
            this.fetchProducts();
        });
    };

    handleSelect = (product: Product) => {
        this.setState({ selectedProduct: { ...product } });
    };

    handleUpdateChange = (e: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        this.setState((prevState) => ({
            selectedProduct: {
                ...prevState.selectedProduct!,
                [name]: value,
            },
        }));
    };

    handleUpdate = () => {
        if (this.state.selectedProduct) {
            UserService.updateProduct(this.state.selectedProduct).then(
                () => {
                    this.setState({ selectedProduct: null, errorMessage: "" }); // Limpa as mensagens de erro
                    this.fetchProducts();
                },
                (error) => {
                    const errors = error.response?.data ?? [];
                    const errorMessage = errors.map((err: { message: string }) => err.message).join(" ");
                    this.setState({ errorMessage });
                }
            );
        }
    };

    handleAddChange = (e: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        this.setState((prevState) => ({
            newProduct: {
                ...prevState.newProduct,
                [name]: value,
            },
        }));
    };

    handleAdd = () => {
        UserService.createProduct(this.state.newProduct).then(
            () => {
                this.setState({
                    newProduct: { id: 0, name: "", description: "" },
                    errorMessage: ""
                });
                this.fetchProducts();
            },
            (error) => {
                const errors = error.response?.data || [];
                const errorMessage = errors.map((err: { message: string }) => err.message).join(" ");
                this.setState({ errorMessage });
            }
        );
    };

    clearErrorMessage = () => {
        this.setState({ errorMessage: "" });
    };

    render() {
        const { products, selectedProduct, newProduct } = this.state;

        return (
            <div className="container mt-4">
                <header className="jumbotron">
                    <h3 className="text-center">Product List</h3>
                </header>
                {this.state.errorMessage && (
                    <div className="alert alert-danger alert-dismissible" role="alert">
                        {this.state.errorMessage}
                        <button
                            type="button"
                            className="btn-close"
                            aria-label="Close"
                            onClick={this.clearErrorMessage}
                        ></button>
                    </div>
                )}
                <table className="table table-striped table-hover">
                    <thead className="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {products.map((product) => (
                        <tr key={product.id}>
                            <td>{product.id}</td>
                            <td>{product.name}</td>
                            <td>{product.description}</td>
                            <td>
                                <button
                                    className="btn btn-danger btn-sm me-2"
                                    onClick={() => this.handleDelete(product.id)}
                                >
                                    Delete
                                </button>
                                <button
                                    className="btn btn-primary btn-sm"
                                    onClick={() => this.handleSelect(product)}
                                >
                                    Select to Update
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>

                {selectedProduct && (
                    <div className="mt-4">
                        <h4>Update Produto</h4>
                        <form>
                            <div className="form-group mb-3">
                                <label>Name</label>
                                <input
                                    type="text"
                                    name="name"
                                    value={selectedProduct.name}
                                    onChange={this.handleUpdateChange}
                                    className="form-control"
                                />
                            </div>
                            <div className="form-group mb-3">
                                <label>Description</label>
                                <input
                                    type="text"
                                    name="description"
                                    value={selectedProduct.description}
                                    onChange={this.handleUpdateChange}
                                    className="form-control"
                                />
                            </div>
                            <button
                                type="button"
                                className="btn btn-success"
                                onClick={this.handleUpdate}
                            >
                                Update
                            </button>
                        </form>
                    </div>
                )}

                <div className="mt-4">
                    <h4>Create Product</h4>

                    <form>
                        <div className="form-group mb-3">
                            <label>Name</label>
                            <input
                                type="text"
                                name="name"
                                value={newProduct.name}
                                onChange={this.handleAddChange}
                                className="form-control"
                            />
                        </div>
                        <div className="form-group mb-3">
                            <label>Description</label>
                            <input
                                type="text"
                                name="description"
                                value={newProduct.description}
                                onChange={this.handleAddChange}
                                className="form-control"
                            />
                        </div>
                        <button
                            type="button"
                            className="btn btn-primary"
                            onClick={this.handleAdd}
                        >
                            Create
                        </button>
                    </form>
                </div>
            </div>
        );
    }
}