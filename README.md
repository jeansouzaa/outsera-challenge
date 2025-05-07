# 📁 CSV Reader – Intervalos de Premiação dos Produtores

Este projeto realiza a leitura de um arquivo `.csv` contendo dados sobre premiações no cinema, identifica os produtores vencedores e calcula os intervalos entre suas vitórias, retornando os que têm os **menores** e **maiores** intervalos.

## ⚙️ Configuração e Execução

Siga os passos abaixo para configurar e executar o projeto corretamente:

### 1. Definir caminho do arquivo CSV
Abra o arquivo de propriedades localizado em:

```
src/main/resources/application.properties
```

Altere o valor da variável `csv.file.path` para o caminho completo onde o arquivo `.csv` está salvo:

```properties
csv.file.path=/caminho/completo/do/seu/arquivo.csv
```

### 2. (Opcional) Alterar a porta do servidor
Caso a porta padrão `8080` já esteja em uso, defina uma nova porta HTTP no mesmo arquivo `application.properties`:

```properties
server.port=8081
```

### 3. Compilar o projeto
No terminal, na raiz do projeto, execute:

```bash
./mvnw clean package
```

Ou, se estiver utilizando o Maven instalado no sistema:

```bash
mvn clean package
```

### 4. Executar a aplicação
Ainda na pasta raiz, execute:

```bash
java -jar target/csvReader-0.0.1-SNAPSHOT.jar
```

### 5. Acessar o resultado
Abra o navegador e acesse a seguinte URL (substitua `[porta]` pela porta configurada):

```
http://localhost:[porta]/producers/intervals
```

Você verá o resultado dos produtores com o menor e maior intervalo entre prêmios.

---

## 📄 Exemplo de Resposta JSON

Ao acessar o endpoint `/producers/intervals`, você receberá uma resposta semelhante a esta:

```json
{
  "min": [
    {
      "producer": "Producer A",
      "interval": 1,
      "previousWin": 2001,
      "followingWin": 2002
    }
  ],
  "max": [
    {
      "producer": "Producer B",
      "interval": 13,
      "previousWin": 1990,
      "followingWin": 2003
    }
  ]
}
```

### 🔍 Significado dos campos:
- **producer**: Nome do produtor vencedor.
- **interval**: Intervalo de anos entre vitórias.
- **previousWin**: Ano da vitória anterior.
- **followingWin**: Ano da vitória seguinte.


