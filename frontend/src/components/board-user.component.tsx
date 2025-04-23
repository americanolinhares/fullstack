import { Component, ChangeEvent } from "react";
import UserService from "../services/user.service";
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
};

export default class BoardUser extends Component<Props, State> {
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
        UserService.getUserBoard().then(
            (response) => {
                this.setState({ products: response.data });
            },
            (error) => {
                console.error("Erro ao buscar produtos:", error);
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
            UserService.updateProduct(this.state.selectedProduct).then(() => {
                this.setState({ selectedProduct: null });
                this.fetchProducts();
            });
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
        UserService.addProduct(this.state.newProduct).then(() => {
            this.setState({ newProduct: { id: 0, name: "", description: "" } });
            this.fetchProducts();
        });
    };

    render() {
        const { products, selectedProduct, newProduct } = this.state;

        return (
            <div className="container mt-4">
                <header className="jumbotron">
                    <h3 className="text-center">Lista de Produtos</h3>
                </header>
                <table className="table table-striped table-hover">
                    <thead className="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Descrição</th>
                        <th>Ações</th>
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
                                    Deletar
                                </button>
                                <button
                                    className="btn btn-primary btn-sm"
                                    onClick={() => this.handleSelect(product)}
                                >
                                    Selecionar
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>

                {selectedProduct && (
                    <div className="mt-4">
                        <h4>Atualizar Produto</h4>
                        <form>
                            <div className="form-group mb-3">
                                <label>Nome</label>
                                <input
                                    type="text"
                                    name="name"
                                    value={selectedProduct.name}
                                    onChange={this.handleUpdateChange}
                                    className="form-control"
                                />
                            </div>
                            <div className="form-group mb-3">
                                <label>Descrição</label>
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
                                Atualizar
                            </button>
                        </form>
                    </div>
                )}

                <div className="mt-4">
                    <h4>Adicionar Novo Produto</h4>
                    <form>
                        <div className="form-group mb-3">
                            <label>Nome</label>
                            <input
                                type="text"
                                name="name"
                                value={newProduct.name}
                                onChange={this.handleAddChange}
                                className="form-control"
                            />
                        </div>
                        <div className="form-group mb-3">
                            <label>Descrição</label>
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
                            Adicionar
                        </button>
                    </form>
                </div>
            </div>
        );
    }
}