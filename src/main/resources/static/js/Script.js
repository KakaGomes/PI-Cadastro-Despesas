document.getElementById("despesaForm").addEventListener("submit", function(event) {
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
