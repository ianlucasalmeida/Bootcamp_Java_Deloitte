const express = require('express');
const path = require('path');
const app = express();
 
const port = process.env.PORT || 3000;

// Liberando ao servidor o acesso a pasta
app.use(express.static(__dirname));

// Rota coringa atualizada para o Express 5 (Usando Regex /.*/ no lugar da string '*') String não funciona mais!
app.get(/.*/, (req, res) => {
    res.sendFile(path.join(__dirname, 'index.html'));
});

app.listen(port, () => {
    console.log(`Front-end rodando em http://localhost:${port}`);
    
});