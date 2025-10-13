

# Guia Completo de Configuração do Datomic Pro

Este guia descreve passo a passo como instalar, configurar e conectar um projeto ao **Datomic Pro**.
O foco é ajudar novos usuários a realizar todo o processo do zero, em um ambiente **Linux**.

---

## Parte 1 — Instalando e Configurando o Datomic Pro

### 1. Criar uma conta no Datomic Pro

Acesse o link abaixo e crie sua conta gratuita:

👉 [https://my.datomic.com/account/create](https://my.datomic.com/account/create)

---

### 2. Fazer o download do Datomic Pro

Após criar sua conta, acesse a página de downloads:

👉 [https://my.datomic.com/downloads/pro](https://my.datomic.com/downloads/pro)

Baixe a **última versão estável** disponível do Datomic Pro
(exemplo: `datomic-pro-1.0.6711.zip`).

---

### 3. Extrair o arquivo e acessar a pasta

No terminal, execute:

```bash
unzip datomic-pro-1.0.6711.zip
cd datomic-pro-1.0.6711
```

---

### 4. Instalar o Maven

O Maven é necessário para instalar as dependências do Datomic.

```bash
sudo apt install maven
```

---

### 5. Instalar o Datomic Pro

Dentro da pasta extraída, rode:

```bash
bin/maven-install
```

Esse comando instala o Datomic localmente via Maven.

---

### 6. Criar o arquivo de configuração

Copie o modelo de configuração padrão:

```bash
cp config/samples/dev-transactor-template.properties config
```

---

### 7. Adicionar sua License Key

#### 1. Solicitar a licença

Acesse sua conta Datomic para obter a chave de licença:

👉 [https://my.datomic.com/account](https://my.datomic.com/account)

#### 2. Copiar a chave recebida por e-mail

Você receberá uma chave parecida com esta:

```
license-key=FJHWl6aopraa10LstB+v6ehNzajP07vOu1/tE8qF+6wFIUPfJy\
oAAexOiTE862wAw38pPqcCkaCpv6Z+wTjsgp+Js4iCh94AzorARfxSpzniCCmN\
oxKI8Lwog3z8LglmKqXuHVXf+L6iUTkEoNvJuuUFZj9nZqE7oVv7jMwLdecYoU\
u/3++InzrCZ1uh6wvwCUYgB7zDtlv+ISZf+YdAns1w8DJMacU2KqD0B2AMBbPW\
6LrqfgOr7ACrozkkhLx1/SWm8SAIamFXoe/gPM+g98i1hzE4vstJsFf4r2sNmO\
zKWR6DETJqUuv0/J6UlKspv/R/W1tzuYosJ81YHipiMw==
```

#### 3. Adicionar a licença ao arquivo de configuração

Abra o arquivo de configuração copiado:

```bash
nano config/dev-transactor-template.properties
```

Cole sua license key (use `Ctrl + Shift + V` para colar).

Salve e feche o arquivo (`Ctrl + O`, `Enter`, `Ctrl + X`).

---

### 8. Iniciar o banco de dados (Transactor)

Para iniciar o Datomic:

```bash
bin/transactor config/dev-transactor-template.properties
```

Se tudo estiver correto, o Datomic iniciará o **Transactor**, o componente responsável por gerenciar transações no banco de dados.

### 9. Criando suas tabelas:

Após incializar o transactor, será incializado um serviço que roda no seguinte endereço:

```clojure
datomic:dev://localhost:4334/corenet
```

Utilizando o passo anterior, você deve acessar um arquivo (no meu caso o core.clj) e adicionar os seguintes trechos de código:

```clojure
(ns corenet-backend.core
  (:require [datomic.api :as d])
  (:gen-class))

(def db-uri "datomic:dev://localhost:4334/corenet")

(d/create-database db-uri)

(def conn (d/connect db-uri))
```

---

## Parte 2 — Conectando seu Projeto ao Datomic

Após configurar e iniciar o Datomic, o próximo passo é integrar o banco ao seu projeto (exemplo: um projeto **Clojure** usando **Leiningen**).

---

### 1. Criar uma chave GPG

O Datomic utiliza o GPG para criptografar credenciais de acesso ao repositório privado.

Instale e gere sua chave:

```bash
sudo apt install gnupg
gpg --full-generate-key
```

A seguir, selecione as opções conforme o exemplo:

```
Please select what kind of key you want:
   (1) RSA and RSA
Your selection? 1

RSA keys may be between 1024 and 4096 bits long.
What keysize do you want? (3072)
Requested keysize is 3072 bits

Key is valid for? (0)
Is this correct? (y/N) y

Real name: Victor
Email address: taroni8430@gta5hx.com
Comment: blablabla

You selected this USER-ID:
    "Victor (blablabla) <taroni8430@gta5hx.com>"

Change (N)ame, (C)omment, (E)mail or (O)kay/(Q)uit? o
```

Sua chave será criada e registrada no GPG.

---

### 2. Criar e criptografar o arquivo de credenciais

Crie o arquivo com suas credenciais Datomic:

```bash
nano ~/.lein/credentials.clj
```

Cole o conteúdo obtido no site do Datomic:

```clojure
{#"my\.datomic\.com"
 {:username "taroni8430@gta5hx.com"
  :password "02a7b4b0-64e6-42a4-b8f5-07a55aabcf0b"}}
```

Salve o arquivo e depois criptografe-o com sua chave GPG:

```bash
gpg -e -r "taroni8430@gta5hx.com" ~/.lein/credentials.clj
```

Isso criará o arquivo `~/.lein/credentials.clj.gpg`, que será usado pelo Leiningen para autenticação segura.

---

### 3. Adicionar repositório e dependências ao projeto

Abra o arquivo `project.clj` do seu projeto:

```bash
nano project.clj
```

Adicione as seguintes configurações:

```clojure
:repositories {"my.datomic.com" {:url "https://my.datomic.com/repo"
                                 :creds :gpg}}

:dependencies [[org.clojure/clojure "1.11.1"]
               [com.datomic/datomic-pro "1.0.6711"]]
```

---

### 4. Baixar as dependências

Por fim, baixe todas as dependências do projeto:

```bash
lein deps
```

Após esse comando, o Leiningen fará o download do Datomic Pro e suas dependências, utilizando as credenciais criptografadas.

---

**Obs**: Caso não queira passar por toda essa etapa de criação de uma chave GPG, você pode adicionar diretamente no arquivo de project.clj (Não recomendado):

```clojure
:repositories {"my.datomic.com" {:url "https://my.datomic.com/repo"
                                 :username "taroni8430@gta5hx.com"
                                 :password "02a7b4b0-64e6-42a4-b8f5-07a55aabcf0b"}}

```
---

### Fontes de estudo:
https://cesar-alcancio.medium.com/primeiros-passos-utilizando-datomic-b9c5252731d

https://youtu.be/kDzUdXBqoEY?si=mlwv-KtRLN8RjvLO

https://www.youtube.com/live/uGxTcHcjq78?si=YcvRnXluDsCZyirB

https://docs.datomic.com/peer-tutorial/peer-tutorial.html

https://docs.datomic.com/peer-tutorial/transactor.html

https://docs.datomic.com/peer-tutorial/connect-to-a-database.html

https://docs.datomic.com/peer-tutorial/transact-schema.html

https://maven.apache.org/install.html

https://medium.com/@adamneilson/datomic-pro-and-leiningen-8e6913c9eb06

