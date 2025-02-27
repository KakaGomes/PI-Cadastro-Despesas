document.addEventListener("DOMContentLoaded", function() {
    const loginForm = document.getElementById("loginForm");
    const mensagemErro = document.getElementById("mensagemErro");

    if (loginForm) {
        loginForm.addEventListener("submit", function(event) {
            event.preventDefault();
            const usuario = document.getElementById("usuario").value;
            const senha = document.getElementById("senha").value;

            fetch("/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ usuario, senha }) // Certifique-se de que está enviando "usuario" e "senha"
            })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => { throw new Error(text); });
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
            .catch(error => {
                mensagemErro.style.display = "block";
                mensagemErro.textContent = error.message;
            });
        });
    }



    // Submissão de Despesas
    const despesaForm = document.getElementById("despesaForm");
    if (despesaForm) {
        despesaForm.addEventListener("submit", function(event) {
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
    }

    // Carregar lista de despesas
    function carregarDespesas() {
        fetch("/despesas")
            .then(response => response.json())
            .then(despesas => {
                const lista = document.getElementById("listaDespesas");
                if (lista) {
                    lista.innerHTML = "";
                    despesas.forEach(despesa => {
                        const item = document.createElement("li");
                        item.textContent = `${despesa.descricao} - R$ ${despesa.valor}`;
                        lista.appendChild(item);
                    });
                }
            });
    }
    carregarDespesas();
});
