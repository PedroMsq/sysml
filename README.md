# SysML v2 Model Project

Este projeto contém exemplos e modelos baseados em **SysML v2**, integrados com a **Pilot Implementation**.

## 📂 Estrutura do projeto
- `src/main/java/sysml` → Onde ficam os modelos SysML v2.
- `src/main/resources/application.properties` → Arquivo de configuração do projeto.
- `pom.xml` → Configuração Maven.

---

## ⚙️ Setup do ambiente

### 1. Atualizar `application.properties`
O arquivo está em `src/main/resources/application.properties`.

Edite os seguintes valores:

```properties
# Caminho até a pasta main/java/sysml do projeto
app.baseFilePath=C:/.../sysml/src/main/java/sysml

# Caminho até a pasta sysml.library da instalação da Pilot Implementation
app.systemLibPath=C:/.../SySML-v2-Pilot-Implementation/sysml.library
```
Ajuste os caminhos conforme o ambiente local.

### 2. Atualizar `pom.xml`
No pom.xml, localize a seção de propriedades e ajuste o valor de <lib-systemPath> para o caminho do sysml-lib3.jar.
O sysml-lib3.jar deve estar fora da pasta do projeto.
