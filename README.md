# SysML v2 Model Project

Este projeto contém exemplos e modelos baseados em **SysML v2**, integrados com a **Pilot Implementation**. O foco é a modelagem de sistemas utilizando as especificações mais recentes da linguagem em ambiente Java.

## 📂 Estrutura do Projeto

Abaixo, a organização dos diretórios principais:
- `src/main/java/sysml` : Local centralizado para os arquivos de modelos SysML v2.
- `src/main/resources/application.properties` : Configurações de caminhos e propriedades de execução.
- `pom.xml` : Gerenciamento de dependências via Maven.

## ⚙️ Setup do Ambiente

Siga os passos abaixo para configurar o ambiente de desenvolvimento corretamente.
## 1. Pré-requisitos
- JDK 23 instalado e configurado em: `Windows > Preferences > Java > Installed JREs` (no Eclipse).
- SysML v2 Pilot Implementation clonado e configurado localmente.

## 2. Configuração de Propriedades

- Edite o arquivo src/main/resources/application.properties com os caminhos da sua máquina:
```properties 
# Caminho absoluto até a pasta de modelos do projeto
app.baseFilePath=C:/.../sysml/src/main/java/sysml

# Caminho absoluto até a biblioteca padrão da Pilot Implementation
app.systemLibPath=C:/.../SySML-v2-Pilot-Implementation/sysml.library
```

## 3. Autenticação GitHub Packages (settings.xml)

Para baixar as dependências do SysML v2, você precisa configurar o Maven para acessar o GitHub Packages:
- Gere um Personal Access Token (classic) no GitHub com a permissão read:packages.
- Localize ou crie o arquivo settings.xml em `C:\Users\SEU_USER\.m2\`
- Adicione a configuração de servidor com suas credenciais:
```XML
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                              http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <servers>
        <server>
            <id>github-sysml</id>
            <username>SEU_USER_GITHUB</username>
            <password>SEU_TOKEN_GITHUB</password>
        </server>

        <server>
            <id>github-obp3</id>
            <username>SEU_USER_GITHUB</username>
            <password>SEU_TOKEN_GITHUB</password>
        </server>
    </servers>
</settings>
```

## 4. Atualização do Projeto
Após configurar o settings.xml e o pom.xml:
- Remova o arquivo sysml-lib3.jar da pasta /lib (caso exista).
- No Eclipse (ou sua IDE), clique com o botão direito no projeto.
Vá em Maven > Update Project...
Marque a opção Force Update of Snapshots/Releases e clique em OK.
