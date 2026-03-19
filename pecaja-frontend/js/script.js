const API_BASE = "http://localhost:8080";

// ==========================================
// 1. NAVEGAÇÃO E ORQUESTRAÇÃO
// ==========================================
async function navegar(pagina) {
    const viewPort = document.getElementById("view-port");

    document.querySelectorAll(".nav-link-custom").forEach((link) => link.classList.remove("active"));
    const linkAtivo = document.querySelector(`[data-page="${pagina}"]`);
    if (linkAtivo) linkAtivo.classList.add("active");

    try {
        const response = await fetch(`paginas/${pagina}.html`);
        viewPort.innerHTML = await response.text();

        // Gatilhos Automáticos para carregar dados
        if (pagina === "usuario") carregarDadosUsuarios();
        if (pagina === "produtos") carregarDadosProdutos();
        if (pagina === "historico") carregarHistoricoVendas();
    } catch (err) {
        viewPort.innerHTML = "<h2 class='text-danger text-center mt-5'>Erro ao carregar interface.</h2>";
    }
}

// ==========================================
// 2. MÓDULO: PRODUTOS / CATÁLOGO
// ==========================================

function alternarCamposProduto() {
    const tipo = document.getElementById("prod-tipo").value;
    const camposPeca = document.querySelectorAll(".campo-peca");
    const camposServico = document.querySelectorAll(".campo-servico");
    const btnIcon = document.querySelector(".btn-verde i");

    if (tipo === "peca") {
        camposPeca.forEach((el) => (el.style.display = "block"));
        camposServico.forEach((el) => (el.style.display = "none"));
        if (btnIcon) btnIcon.className = "bi bi-box-seam me-2";
    } else {
        camposPeca.forEach((el) => (el.style.display = "none"));
        camposServico.forEach((el) => (el.style.display = "block"));
        if (btnIcon) btnIcon.className = "bi bi-briefcase me-2";
    }
}

async function carregarDadosProdutos() {
    const grid = document.getElementById("grid-produtos");
    if (!grid) return;

    grid.innerHTML = '<p class="text-light text-center w-100 fw-bold">Consultando catálogo no servidor...</p>';

    try {
        const res = await fetch(`${API_BASE}/catalogo`);
        if (!res.ok) throw new Error("Falha na API");
        const produtos = await res.json();

        if (produtos.length === 0) {
            grid.innerHTML = `<div class="col-12 text-center text-light">O catálogo está vazio.</div>`;
            return;
        }

        grid.innerHTML = produtos.map((p) => {
            const nomeExibicao = p.descricao || `Item Sem Descrição`;
            const precoExibicao = p.precoBase ? `R$ ${p.precoBase.toFixed(2).replace(".", ",")}` : "Sob Consulta";
            
            const isServico = p.horasEstimadas != null && p.horasEstimadas > 0;
            const icone = isServico ? "bi-briefcase" : "bi-tools";

            return `
            <div class="col-12 col-md-6 col-lg-4">
                <div class="card-pecaja border-light">
                    <div class="d-flex justify-content-between align-items-start mb-3">
                        <i class="bi ${icone} fs-2" style="color:var(--verde-pecaja)"></i>
                        <span class="badge bg-light text-dark fw-bold">ID: ${p.id}</span>
                    </div>
                    <h5 class="text-white fw-bold text-truncate" title="${nomeExibicao}">${nomeExibicao}</h5>
                    <h4 class="mt-3 mb-1 fw-bold" style="color: var(--verde-pecaja);">${precoExibicao}</h4>
                    
                    <div class="small text-white mt-2 border-top border-secondary pt-2">
                        ${p.marca && p.marca !== 'Serviço' ? `<span>Marca: ${p.marca}</span><br>` : ""}
                        ${p.codigoFabricante && p.codigoFabricante !== 'Mão de Obra' ? `<span>Cód: ${p.codigoFabricante}</span><br>` : ""}
                        ${p.horasEstimadas ? `<span>Tempo Estimado: ${p.horasEstimadas}h</span>` : ""}
                    </div>
                </div>
            </div>`;
        }).join("");
    } catch (e) {
        grid.innerHTML = "<div class='alert alert-danger w-100 text-center fw-bold'>Erro de conexão com o servidor Java.</div>";
    }
}

async function salvarNovoProduto() {
    const tipo = document.getElementById("prod-tipo").value;
    const nomeInput = document.getElementById("prod-nome").value.trim();
    const precoInput = parseFloat(document.getElementById("prod-preco").value);

    if (!nomeInput || nomeInput.length < 3 || isNaN(precoInput) || precoInput <= 0) {
        alert("Preencha a descrição (mín. 3 letras) e um preço válido.");
        return;
    }

    let dadosProduto = { 
        descricao: nomeInput, 
        precoBase: precoInput,
        marca: "Serviço",           
        codigoFabricante: "Mão de Obra", 
        horasEstimadas: 0.0
    };

    let endpoint = "";

    if (tipo === "peca") {
        const marca = document.getElementById("prod-marca").value.trim();
        const codigo = document.getElementById("prod-codigo").value.trim();
        if (!marca || !codigo) { alert("Marca e Código são obrigatórios para Peças."); return; }
        dadosProduto.marca = marca;
        dadosProduto.codigoFabricante = codigo;
        delete dadosProduto.horasEstimadas; 
        endpoint = "/catalogo/pecas";
    } else {
        const horas = parseFloat(document.getElementById("prod-horas").value);
        if (isNaN(horas) || horas <= 0) { alert("Informe as Horas Estimadas do Serviço."); return; }
        dadosProduto.horasEstimadas = horas;
        endpoint = "/catalogo/servicos";
    }

    const btn = document.querySelector(".btn-verde");
    btn.disabled = true;
    btn.innerText = "Processando...";

    try {
        const res = await fetch(`${API_BASE}${endpoint}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dadosProduto),
        });

        if (res.ok) {
            alert("Sucesso: Item adicionado ao Catálogo!");
            navegar("produtos");
        } else {
            alert("Erro 500: Verifique as constraints de banco de dados no Java.");
        }
    } catch (error) {
        alert("Falha de conexão com a API.");
    } finally {
        if (btn) { btn.disabled = false; btn.innerText = "Salvar no Catálogo"; }
    }
}

// ==========================================
// 3. MÓDULO: CLIENTES (ALTO CONTRASTE + VALIDAÇÃO)
// ==========================================
async function carregarDadosUsuarios() {
    const grid = document.getElementById("grid-usuarios");
    if (!grid) return;

    grid.innerHTML = '<p class="text-light text-center w-100 fw-bold">Buscando Clientes no H2...</p>';

    try {
        const res = await fetch(`${API_BASE}/clientes`);
        const clientes = await res.json();

        grid.innerHTML = clientes.map((c) => `
            <div class="col-12 col-md-6 col-lg-4">
                <div class="card-pecaja">
                    <h5 class="text-white fw-bold mb-3"><i class="bi bi-person-circle me-2" style="color:var(--verde-pecaja)"></i>${c.nome}</h5>
                    <p class="small text-white mb-1"><i class="bi bi-envelope me-2"></i>${c.email}</p>
                    <p class="small text-white mb-1"><i class="bi bi-whatsapp me-2"></i>${c.telefone}</p>
                    <small class="text-light fw-bold">ID: ${c.id} | CPF: ${c.cpf}</small>
                </div>
            </div>`).join("");
    } catch (e) {
        grid.innerHTML = "<div class='alert alert-danger w-100 text-center fw-bold'>Erro ao carregar clientes.</div>";
    }
}

async function salvarNovoCliente() {
    const nome = document.getElementById("nome").value.trim();
    const email = document.getElementById("email").value.trim();
    // Limpeza de caracteres especiais para o Java
    const cpf = document.getElementById("cpf").value.replace(/\D/g, "");
    const telefone = document.getElementById("telefone").value.replace(/\D/g, "");

    if (nome.length < 3) { alert("Nome muito curto!"); return; }
    if (cpf.length !== 11) { alert("CPF deve ter 11 dígitos numéricos."); return; }
    if (telefone.length < 10) { alert("Telefone inválido."); return; }

    const dados = { nome, email, cpf, telefone };
    const btn = document.querySelector(".btn-verde");
    btn.disabled = true;

    try {
        const res = await fetch(`${API_BASE}/clientes`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dados),
        });
        if (res.ok) { alert("Cliente cadastrado!"); navegar("usuario"); }
        else { alert("Erro: Verifique se o CPF ou E-mail já estão cadastrados."); }
    } catch (error) { alert("Erro de conexão."); } finally { if (btn) btn.disabled = false; }
}

// ==========================================
// 4. MÓDULO: VENDAS (CUIDADO NO PREENCHIMENTO)
// ==========================================
async function carregarHistoricoVendas() {
    const grid = document.getElementById("grid-historico");
    if (!grid) return;

    grid.innerHTML = '<p class="text-light text-center w-100 fw-bold">Carregando Histórico de Vendas...</p>';

    try {
        const res = await fetch(`${API_BASE}/vendas`);
        const vendas = await res.json();

        if (vendas.length === 0) {
            grid.innerHTML = `<p class="col-12 text-center text-white fw-bold">Nenhuma venda registrada.</p>`;
            return;
        }

        grid.innerHTML = vendas.map((v) => {
            const itens = v.itens || v.produtos || [];
            const totalVenda = itens.reduce((sum, p) => sum + (p.precoBase || 0), 0);
            const totalFormatado = `R$ ${totalVenda.toFixed(2).replace(".", ",")}`;

            return `
            <div class="col-12">
                <div class="card-pecaja d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-white mb-1 fw-bold"><i class="bi bi-receipt me-2" style="color:var(--verde-pecaja)"></i>Venda ID: ${v.id}</h6>
                        <p class="small text-white mb-0">Cliente: ${v.cliente ? v.cliente.nome : "N/A"}</p>
                        <small class="text-light fw-bold">${itens.length} item(ns) vendidos.</small>
                    </div>
                    <div class="text-end">
                        <h4 class="mb-0 fw-bold" style="color: var(--verde-pecaja);">${totalFormatado}</h4>
                    </div>
                </div>
            </div>`;
        }).join("");
    } catch (e) {
        grid.innerHTML = "<div class='alert alert-danger w-100 text-center fw-bold'>Erro ao compilar histórico.</div>";
    }
}

async function salvarNovaVenda() {
    const qtd = parseInt(document.getElementById("venda-quantidade").value);
    const prodId = parseInt(document.getElementById("venda-produto-id").value);
    const clienteId = parseInt(document.getElementById("venda-cliente-id").value);

    
    if (isNaN(clienteId) || clienteId <= 0 || isNaN(prodId) || prodId <= 0 || isNaN(qtd) || qtd <= 0) {
        alert("Erro: Preencha IDs válidos (positivos) e uma quantidade acima de zero.");
        return;
    }

    const btn = document.querySelector(".btn-verde");
    btn.disabled = true;
    const listaDeItens = Array(qtd).fill(prodId);

    try {
        const res = await fetch(`${API_BASE}/vendas`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ clienteId, itensIds: listaDeItens }),
        });
        if (res.ok) {
            alert("Venda registrada com sucesso!");
            navegar("historico");
        } else {
            alert("Erro: Verifique se o ID do Cliente e o ID do Produto estão corretos no Catálogo.");
        }
    } catch (error) { alert("Erro de conexão com o servidor."); } finally { if (btn) btn.disabled = false; }
}

window.onload = () => navegar("inicio");