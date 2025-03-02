document.getElementById("despesaForm").addEventListener("submit", function (event) {
    event.preventDefault();
    const descricao = document.getElementById("descricao").value;
    const valor = document.getElementById("valor").value;

    fetch("/despesas", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ descricao, valor })
    })
        .then(response => response.json())
        .then(() => {
            carregarDespesas();
            document.getElementById("descricao").value = "";
            document.getElementById("valor").value = "";
        });
});

function carregarDespesas() {
    fetch("/despesas")
        .then(response => response.json())
        .then(despesas => {
            const lista = document.getElementById("listaDespesas");
            lista.innerHTML = "";
            despesas.forEach(despesa => {
                const item = document.createElement("li");
                item.textContent = `${despesa.descricao} - R$ ${despesa.valor}`;
                lista.appendChild(item);
            });
        });
}

carregarDespesas();

// Validação de Login e Redirecionamento
document.querySelector("form").addEventListener("submit", function (event) {
    event.preventDefault();
    const usuario = document.getElementById("usuario").value;
    const senha = document.getElementById("senha").value;

    fetch("/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ usuario, senha })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Usuário ou senha inválidos");
            }
            return response.json();
        })
        .then(data => {
            if (data.tipo === "Administrador") {
                window.location.href = "diretor.html";
            } else if (data.tipo === "Comprador") {
                window.location.href = "comprador.html";
            }
        })
        .catch(error => alert(error.message));
});

const form = document.getElementById("cadastroForm");

form.addEventListener("submit", function (event) {
    event.preventDefault();

    const usuario = document.getElementById("usuario").value;
    const senha = document.getElementById("senha").value;
    const nome = document.getElementById("nome").value;
    const tipo = document.getElementById("tipo").value;

    fetch("http://localhost:8080/usuarios", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            nome: nome,
            usuario: usuario,
            senha: senha,
            tipo: tipo
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Erro ao cadastrar usuário");
        }
        return response.json();
    })
    .then(data => {
        alert("Usuário cadastrado com sucesso!");
        window.location.href = "/pagina-de-sucesso";
    })
    .catch(error => console.error("Erro:", error));
});

