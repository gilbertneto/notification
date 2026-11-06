# Notification Service

Microsserviço responsável pelo envio de notificações por e-mail aos usuários da plataforma de agendamento de tarefas, utilizando templates HTML.

## Responsabilidades

- Envio de e-mails transacionais (ex.: lembretes de tarefas, confirmações)
- Renderização de templates HTML dinâmicos para as mensagens enviadas
- Exposição de endpoint(s) REST para disparo de notificações a partir de outros serviços (ex.: `task-scheduler`)

## Stack técnica

| Categoria | Tecnologia |
|---|---|
| Linguagem | Java 23 |
| Framework | Spring Boot 3.5.5 |
| Envio de e-mail | Spring Boot Starter Mail |
| Template engine | Thymeleaf (templates HTML) |
| Build | Gradle |
| Testes | JUnit 5 |
| CI/CD | GitHub Actions |
| Boilerplate | Lombok |

## Arquitetura

Este serviço é consumido pelos demais microsserviços da plataforma (principalmente pelo `task-scheduler`) sempre que uma notificação precisa ser enviada ao usuário. A separação em um serviço dedicado permite isolar a lógica de envio de e-mails (incluindo templates e configuração de SMTP) do restante do domínio de negócio.

## Como executar localmente

### Pré-requisitos
- Java 23 (JDK)
- Conta/servidor SMTP para envio de e-mails (ex.: Gmail SMTP, Mailtrap para testes)
- Gradle (o wrapper `./gradlew` já está incluso no projeto)

### Passos

```bash
# Clonar o repositório
git clone https://github.com/gilbertneto/notification.git
cd notification

# Build
./gradlew build

# Executar
./gradlew bootRun
```

### Configuração

Defina as seguintes variáveis de ambiente (ou configure em `application.properties`/`application.yml`):

```
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=<usuario-smtp>
MAIL_PASSWORD=<senha-ou-app-password>
MAIL_FROM=noreply@taskscheduler.com
```

## Templates

Os templates de e-mail (HTML) ficam localizados em `src/main/resources/templates`, seguindo a convenção do Thymeleaf. Novos tipos de notificação podem ser adicionados criando um novo template e o respectivo método de envio.

## Testes

```bash
./gradlew test
```

## CI/CD

O repositório possui pipeline configurada em `.github/workflows`, executando build e testes automaticamente a cada push/pull request.

## Roadmap / melhorias futuras

- [x] Documentar endpoints via OpenAPI/Swagger
- [ ] Adicionar suporte a outros canais de notificação (SMS, push, webhook)
- [ ] Adicionar fila de mensagens para reprocessamento de envios com falha
- [ ] Adicionar Dockerfile e instruções de execução via container
