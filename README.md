# notification

Microsserviço especialista no disparo de notificações transacionais por e-mail, desacoplado da regra de negócio principal sob o princípio de **Responsabilidade Única (SRP)**.

## Responsabilidades

- Receber o payload de uma tarefa agendada e enviar um e-mail de notificação ao usuário correspondente.
- Renderizar o conteúdo do e-mail a partir de um template **Thymeleaf**.

## Stack

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 23 |
| Framework | Spring Boot 3.5.5 |
| E-mail | Spring Boot Starter Mail (SMTP) |
| Template | Thymeleaf |
| Container | Docker |

## Endpoint principal (`/email`)

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/email` | Recebe os dados da tarefa (`TasksDTO`) e envia o e-mail de notificação |

## Configuração

Variáveis definidas em `src/main/resources/application.yaml`:

```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username:   # definir via variável de ambiente
    password:   # definir via variável de ambiente (senha de app)
    protocol: smtp

envio:
  email:
    remetente: seuemail@gmail.com
    nomeRemetente: 'Seu Nome'

server:
  port: 8082
```

> As credenciais de e-mail (`username`/`password`) não devem ser versionadas — defina-as como variáveis de ambiente ou em um `application-local.yaml` ignorado pelo Git.

## 🐳 Docker

O `Dockerfile` faz build multi-stage com Gradle e roda o `.jar` final em `openjdk:23-jdk` — não é necessário ter Gradle/JDK instalado na máquina, só o Docker.

```bash
docker build -t notification-service .

docker run --rm -p 8082:8082 \
  -e SPRING_MAIL_USERNAME=seu_email@gmail.com \
  -e SPRING_MAIL_PASSWORD=sua_senha_de_app \
  notification-service
```

> ⚠️ **Atenção:** o `Dockerfile` declara `EXPOSE 8083`, mas a aplicação escuta na porta definida em `application.yaml` (`server.port: 8082`). `EXPOSE` é só metadado — o mapeamento que efetivamente funciona é `-p 8082:8082`, como acima. Ajustar o `EXPOSE` para `8082` é um bom acerto para uma próxima revisão.

Este serviço não tem `docker-composer.yml` próprio — para subir o ecossistema completo (com `user`, `task-scheduler` e `bff-task-scheduler`), use o compose disponível no repositório **bff-task-scheduler**.

## Como rodar localmente (sem Docker)

```bash
./gradlew bootRun
```

Aplicação sobe em `http://localhost:8082`.

## Integração com outros serviços

É acionado via `OpenFeign` pelo **bff-task-scheduler**, que dispara o e-mail de notificação quando uma tarefa está prestes a vencer (rotina agendada a cada 5 minutos).
