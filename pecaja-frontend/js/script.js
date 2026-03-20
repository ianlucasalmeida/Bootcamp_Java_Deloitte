//const API_BASE = "http://localhost:8080";
const API_BASE = "";

// ==========================================
// SISTEMA DE NOTIFICAÇÕES
// ==========================================
function mostrarAlerta(mensagem, sucesso = true) {
  const cor = sucesso ? "bg-success" : "bg-danger";
  const icone = sucesso
    ? "bi-check-circle-fill"
    : "bi-exclamation-triangle-fill";

  const toastHTML = `
        <div class="toast align-items-center text-white ${cor} border-0 position-fixed top-0 end-0 m-4 shadow-lg" 
             role="alert" aria-live="assertive" aria-atomic="true" style="z-index: 10000; min-width: 300px;">
            <div class="d-flex">
                <div class="toast-body fw-bold fs-6">
                    <i class="bi ${icone} me-2"></i> ${mensagem}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>
        </div>
    `;

  const container = document.createElement("div");
  container.innerHTML = toastHTML;
  const toastElement = container.firstElementChild;
  document.body.appendChild(toastElement);

  const toast = new bootstrap.Toast(toastElement, { delay: 4000 });
  toast.show();

  toastElement.addEventListener("hidden.bs.toast", () => toastElement.remove());
}

// ==========================================
// MÁSCARAS GLOBAIS
// ==========================================
function mascaraCPF(i) {
  let v = i.value.replace(/\D/g, "");
  v = v.replace(/(\d{3})(\d)/, "$1.$2");
  v = v.replace(/(\d{3})(\d)/, "$1.$2");
  v = v.replace(/(\d{3})(\d{1,2})$/, "$1-$2");
  i.value = v;
}

function mascaraTelefone(i) {
  let v = i.value.replace(/\D/g, "");
  v = v.replace(/^(\d{2})(\d)/g, "($1) $2");
  v = v.replace(/(\d)(\d{4})$/, "$1-$2");
  i.value = v;
}

function formatarCpfTela(cpf) {
  if (!cpf) return "N/A";
  return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
}

function formatarTelefoneTela(tel) {
  if (!tel) return "N/A";
  if (tel.length === 11)
    return tel.replace(/(\d{2})(\d{5})(\d{4})/, "($1) $2-$3");
  if (tel.length === 10)
    return tel.replace(/(\d{2})(\d{4})(\d{4})/, "($1) $2-$3");
  return tel;
}

// ==========================================
// 1. NAVEGAÇÃO E ORQUESTRAÇÃO
// ==========================================
async function navegar(pagina) {
  const viewPort = document.getElementById("view-port");
  document
    .querySelectorAll(".nav-link-custom")
    .forEach((link) => link.classList.remove("active"));
  const linkAtivo = document.querySelector(`[data-page="${pagina}"]`);
  if (linkAtivo) linkAtivo.classList.add("active");

  try {
    const response = await fetch(`paginas/${pagina}.html`);
    viewPort.innerHTML = await response.text();
    if (pagina === "usuario") carregarDadosUsuarios();
    if (pagina === "produtos") carregarDadosProdutos();
    if (pagina === "historico") carregarHistoricoVendas();
  } catch (err) {
    viewPort.innerHTML =
      "<h2 class='text-danger text-center mt-5'>Erro ao carregar interface.</h2>";
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
  grid.innerHTML =
    '<p class="text-light text-center w-100 fw-bold">Consultando catálogo no servidor...</p>';

  try {
    const res = await fetch(`${API_BASE}/catalogo`);
    if (!res.ok) throw new Error("Falha");
    const produtos = await res.json();

    if (produtos.length === 0) {
      grid.innerHTML = `<div class="col-12 text-center text-light">O catálogo está vazio.</div>`;
      return;
    }

    grid.innerHTML = produtos
      .map((p) => {
        const nomeExibicao = p.descricao || `Item Sem Descrição`;
        const precoExibicao = p.precoBase
          ? `R$ ${p.precoBase.toFixed(2).replace(".", ",")}`
          : "Sob Consulta";
        const isServico = p.horasEstimadas != null && p.horasEstimadas > 0;
        const icone = isServico ? "bi-briefcase" : "bi-tools";

        const paramsEdicao = `${p.id}, \`${nomeExibicao}\`, ${p.precoBase || 0}, ${isServico ? "'servicos'" : "'pecas'"}`;

        return `
            <div class="col-12 col-md-6 col-lg-4">
                <div class="card-pecaja border-light h-100 d-flex flex-column">
                    <div class="d-flex justify-content-between align-items-start mb-3">
                        <i class="bi ${icone} fs-2" style="color:var(--verde-pecaja)"></i>
                        <span class="badge bg-light text-dark fw-bold">ID: ${p.id}</span>
                    </div>
                    <h5 class="text-white fw-bold text-truncate" title="${nomeExibicao}">${nomeExibicao}</h5>
                    <h4 class="mt-3 mb-1 fw-bold" style="color: var(--verde-pecaja);">${precoExibicao}</h4>
                    
                    <div class="small text-white mt-2 border-top border-secondary pt-2 flex-grow-1">
                        ${p.marca && p.marca !== "Serviço" ? `<span>Marca: ${p.marca}</span><br>` : ""}
                        ${p.codigoFabricante && p.codigoFabricante !== "Mão de Obra" ? `<span>Cód: ${p.codigoFabricante}</span><br>` : ""}
                        ${p.horasEstimadas ? `<span>Tempo Estimado: ${p.horasEstimadas}h</span>` : ""}
                    </div>
                    
                    <div class="mt-auto pt-3 border-top border-secondary d-flex justify-content-end gap-2">
                        <button class="btn btn-sm btn-outline-light" onclick="abrirModalEditarProduto(${paramsEdicao})" title="Editar">
                            <i class="bi bi-pencil"></i>
                        </button>
                        <button class="btn btn-sm btn-outline-danger" onclick="prepararExclusao(${p.id}, 'produto', \`${nomeExibicao}\`)" title="Excluir">
                            <i class="bi bi-trash"></i>
                        </button>
                    </div>
                </div>
            </div>`;
      })
      .join("");
  } catch (e) {
    grid.innerHTML =
      "<div class='alert alert-danger w-100 text-center fw-bold'>Erro de conexão com a API.</div>";
  }
}

async function salvarNovoProduto() {
  const tipo = document.getElementById("prod-tipo").value;
  const nomeInput = document.getElementById("prod-nome").value.trim();
  const precoInput = parseFloat(document.getElementById("prod-preco").value);

  if (
    !nomeInput ||
    nomeInput.length < 3 ||
    isNaN(precoInput) ||
    precoInput <= 0
  ) {
    mostrarAlerta("Preencha a descrição e um preço válido.", false);
    return;
  }

  let dadosProduto = {
    descricao: nomeInput,
    precoBase: precoInput,
    marca: "Serviço",
    codigoFabricante: "Mão de Obra",
    horasEstimadas: 0.0,
  };
  let endpoint = "";

  if (tipo === "peca") {
    const marca = document.getElementById("prod-marca").value.trim();
    const codigo = document.getElementById("prod-codigo").value.trim();
    if (!marca || !codigo) {
      mostrarAlerta("Marca e Código são obrigatórios para peças.", false);
      return;
    }
    dadosProduto.marca = marca;
    dadosProduto.codigoFabricante = codigo;
    delete dadosProduto.horasEstimadas;
    endpoint = "/catalogo/pecas";
  } else {
    const horas = parseFloat(document.getElementById("prod-horas").value);
    if (isNaN(horas) || horas <= 0) {
      mostrarAlerta("Informe as Horas do Serviço.", false);
      return;
    }
    dadosProduto.horasEstimadas = horas;
    endpoint = "/catalogo/servicos";
  }

  const btn = document.querySelector(".btn-verde");
  btn.disabled = true;

  try {
    const res = await fetch(`${API_BASE}${endpoint}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(dadosProduto),
    });
    if (res.ok) {
      mostrarAlerta("Produto cadastrado com sucesso!", true);
      navegar("produtos");
    } else {
      mostrarAlerta("Erro ao salvar no servidor.", false);
    }
  } catch (error) {
    mostrarAlerta("Falha de conexão com a API.", false);
  } finally {
    if (btn) btn.disabled = false;
  }
}

function abrirModalEditarProduto(id, descricao, preco, tipo) {
  document.getElementById("edit-prod-id").value = id;
  document.getElementById("edit-prod-tipo").value = tipo;
  document.getElementById("edit-prod-nome").value = descricao;
  document.getElementById("edit-prod-preco").value = preco;

  new bootstrap.Modal(document.getElementById("modalEditarProduto")).show();
}

async function salvarEdicaoProduto() {
  const id = document.getElementById("edit-prod-id").value;
  const descricao = document.getElementById("edit-prod-nome").value.trim();
  const precoBase = parseFloat(
    document.getElementById("edit-prod-preco").value,
  );

  if (!descricao || isNaN(precoBase)) {
    mostrarAlerta("Dados inválidos. Verifique os campos.", false);
    return;
  }

  try {
    const res = await fetch(`${API_BASE}/catalogo/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ descricao, precoBase }),
    });

    if (res.ok) {
      bootstrap.Modal.getInstance(
        document.getElementById("modalEditarProduto"),
      ).hide();
      carregarDadosProdutos();
      mostrarAlerta("Produto atualizado com sucesso!", true);
    } else {
      mostrarAlerta("Erro ao atualizar o item no banco de dados.", false);
    }
  } catch (e) {
    mostrarAlerta("Falha de conexão.", false);
  }
}

// ==========================================
// 3. MÓDULO: CLIENTES (MÁSCARAS E CRUD)
// ==========================================
async function carregarDadosUsuarios() {
  const grid = document.getElementById("grid-usuarios");
  if (!grid) return;
  grid.innerHTML =
    '<p class="text-light text-center w-100 fw-bold">Buscando Clientes...</p>';

  try {
    const res = await fetch(`${API_BASE}/clientes`);
    const clientes = await res.json();
    if (clientes.length === 0) {
      grid.innerHTML = `<p class="w-100 text-center text-light">Nenhum cliente cadastrado.</p>`;
      return;
    }

    grid.innerHTML = clientes
      .map(
        (c) => `
            <div class="col-12 col-md-6 col-lg-4">
                <div class="card-pecaja h-100 d-flex flex-column">
                    <h5 class="text-white fw-bold mb-3 text-truncate"><i class="bi bi-person-circle me-2" style="color:var(--verde-pecaja)"></i>${c.nome}</h5>
                    <p class="small text-white mb-1"><i class="bi bi-envelope me-2"></i>${c.email}</p>
                    <p class="small text-white mb-1"><i class="bi bi-whatsapp me-2"></i>${formatarTelefoneTela(c.telefone)}</p>
                    <small class="text-light fw-bold mb-3">ID: ${c.id} | CPF: ${formatarCpfTela(c.cpf)}</small>
                    
                    <div class="mt-auto pt-3 border-top border-secondary d-flex justify-content-end gap-2">
                        <button class="btn btn-sm btn-outline-light" onclick="abrirModalEditarCliente(${c.id}, '${c.nome}', '${c.email}', '${c.telefone}', '${c.cpf}')">
                            <i class="bi bi-pencil"></i>
                        </button>
                        <button class="btn btn-sm btn-outline-danger" onclick="prepararExclusao(${c.id}, 'cliente', '${c.nome}')">
                            <i class="bi bi-trash"></i>
                        </button>
                    </div>
                </div>
            </div>`,
      )
      .join("");
  } catch (e) {
    grid.innerHTML =
      "<div class='alert alert-danger w-100 text-center fw-bold'>Erro ao carregar da API.</div>";
  }
}

async function salvarNovoCliente() {
  const nome = document.getElementById("nome").value.trim();
  const email = document.getElementById("email").value.trim();
  const cpf = document.getElementById("cpf").value.replace(/\D/g, "");
  const telefone = document.getElementById("telefone").value.replace(/\D/g, "");

  if (nome.length < 3 || cpf.length !== 11 || telefone.length < 10) {
    mostrarAlerta(
      "Preencha todos os dados corretamente. CPF exige 11 números.",
      false,
    );
    return;
  }

  try {
    const res = await fetch(`${API_BASE}/clientes`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nome, email, cpf, telefone }),
    });
    if (res.ok) {
      mostrarAlerta("Cliente cadastrado com sucesso!", true);
      navegar("usuario");
    } else {
      mostrarAlerta(
        "Erro! Verifique se CPF ou E-mail já estão cadastrados.",
        false,
      );
    }
  } catch (error) {
    mostrarAlerta("Erro de conexão com o banco de dados.", false);
  }
}

function abrirModalEditarCliente(id, nome, email, telefoneDB, cpfDB) {
  document.getElementById("edit-cliente-id").value = id;
  document.getElementById("edit-cliente-nome").value = nome;
  document.getElementById("edit-cliente-email").value = email;

  const inputTel = document.getElementById("edit-cliente-telefone");
  inputTel.value = telefoneDB;
  mascaraTelefone(inputTel);

  const inputCpf = document.getElementById("edit-cliente-cpf");
  inputCpf.value = cpfDB;
  mascaraCPF(inputCpf);

  new bootstrap.Modal(document.getElementById("modalEditarCliente")).show();
}

async function salvarEdicaoCliente() {
  const id = document.getElementById("edit-cliente-id").value;
  const nome = document.getElementById("edit-cliente-nome").value.trim();
  const email = document.getElementById("edit-cliente-email").value.trim();
  const telefone = document
    .getElementById("edit-cliente-telefone")
    .value.replace(/\D/g, "");
  const cpf = document
    .getElementById("edit-cliente-cpf")
    .value.replace(/\D/g, "");

  if (nome.length < 3 || cpf.length !== 11 || telefone.length < 10) {
    mostrarAlerta("Preencha CPF com 11 dígitos e Telefone com DDD.", false);
    return;
  }

  try {
    const res = await fetch(`${API_BASE}/clientes/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nome, email, telefone, cpf }),
    });
    if (res.ok) {
      bootstrap.Modal.getInstance(
        document.getElementById("modalEditarCliente"),
      ).hide();
      carregarDadosUsuarios();
      mostrarAlerta("Dados do cliente atualizados!", true);
    } else {
      mostrarAlerta("Erro ao atualizar o cliente.", false);
    }
  } catch (e) {
    mostrarAlerta("Falha de comunicação com o servidor.", false);
  }
}

// ==========================================
// 4. SISTEMA UNIVERSAL DE EXCLUSÃO
// ==========================================
function prepararExclusao(id, tipoOrigem, nomeDoItem) {
  const textoModal = document.getElementById("texto-modal-exclusao");
  textoModal.innerHTML = `Deseja realmente apagar <strong>${nomeDoItem}</strong>?`;

  const btnExcluir = document.getElementById("btn-confirmar-exclusao");
  btnExcluir.onclick = () => executarExclusao(id, tipoOrigem);

  new bootstrap.Modal(document.getElementById("modalExclusao")).show();
}

async function executarExclusao(id, tipoOrigem) {
  let endpoint =
    tipoOrigem === "cliente" ? `/clientes/${id}` : `/catalogo/${id}`;

  try {
    const res = await fetch(`${API_BASE}${endpoint}`, { method: "DELETE" });
    if (res.ok) {
      bootstrap.Modal.getInstance(
        document.getElementById("modalExclusao"),
      ).hide();
      if (tipoOrigem === "cliente") carregarDadosUsuarios();
      else carregarDadosProdutos();
      mostrarAlerta("Item excluído permanentemente.", true);
    } else {
      bootstrap.Modal.getInstance(
        document.getElementById("modalExclusao"),
      ).hide();
      mostrarAlerta(
        "O Java não permitiu a exclusão (Violação de Integridade).",
        false,
      );
    }
  } catch (e) {
    mostrarAlerta("Erro de resposta do Servidor.", false);
  }
}

// ==========================================
// 5. MÓDULO: VENDAS E HISTÓRICO
// ==========================================
async function carregarHistoricoVendas() {
  const grid = document.getElementById("grid-historico");
  if (!grid) return;
  grid.innerHTML =
    '<p class="text-light text-center w-100 fw-bold">Carregando Histórico...</p>';
  try {
    const res = await fetch(`${API_BASE}/vendas`);
    const vendas = await res.json();
    if (vendas.length === 0) {
      grid.innerHTML = `<p class="col-12 text-center text-white fw-bold">Nenhuma venda registrada até o momento.</p>`;
      return;
    }

    grid.innerHTML = vendas
      .map((v) => {
        const itens = v.itens || v.produtos || [];
        const total = itens.reduce((sum, p) => sum + (p.precoBase || 0), 0);
        return `
            <div class="col-12">
                <div class="card-pecaja d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-white mb-1 fw-bold"><i class="bi bi-receipt me-2" style="color:var(--verde-pecaja)"></i>Venda ID: ${v.id}</h6>
                        <p class="small text-white mb-0">Cliente: ${v.cliente ? v.cliente.nome : "N/A"}</p>
                        <small class="text-light fw-bold">${itens.length} item(ns) vendidos.</small>
                    </div>
                    <div class="text-end">
                        <h4 class="mb-0 fw-bold" style="color: var(--verde-pecaja);">R$ ${total.toFixed(2).replace(".", ",")}</h4>
                    </div>
                </div>
            </div>`;
      })
      .join("");
  } catch (e) {
    grid.innerHTML =
      "<div class='alert alert-danger w-100 text-center fw-bold'>Erro ao processar histórico.</div>";
  }
}

async function salvarNovaVenda() {
  const qtd = parseInt(document.getElementById("venda-quantidade").value);
  const prodId = parseInt(document.getElementById("venda-produto-id").value);
  const clienteId = parseInt(document.getElementById("venda-cliente-id").value);

  if (isNaN(clienteId) || isNaN(prodId) || isNaN(qtd) || qtd <= 0) {
    mostrarAlerta("Preencha IDs válidos numéricos e a quantidade.", false);
    return;
  }

  const btn = document.querySelector(".btn-verde");
  btn.disabled = true;

  try {
    const res = await fetch(`${API_BASE}/vendas`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ clienteId, itensIds: Array(qtd).fill(prodId) }),
    });
    if (res.ok) {
      mostrarAlerta("Venda processada e estoque baixado!", true);
      navegar("historico");
    } else {
      mostrarAlerta(
        "Falha: Verifique se os IDs do Cliente e Produto existem no Banco.",
        false,
      );
    }
  } catch (error) {
    mostrarAlerta("Erro de comunicação com o servidor.", false);
  } finally {
    if (btn) btn.disabled = false;
  }
}

window.onload = () => navegar("inicio");
