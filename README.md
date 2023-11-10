# Zendesk assignment (marcie-ticket-service)

This is a microservice application providing functionalities to list tickets, add a comment to a ticket, list comments on a ticket, and render comments after selecting a specific ticket.

## Features

- **List Tickets**: Fetches all the available tickets from Zendesk.
- **Add Comment to Ticket**: Allows adding a comment to a specific ticket.
- **List Comments**: Fetches all comments for a specific ticket.
- **Render Comments**: Renders comments after a specific ticket is selected.

## Prerequisite

The microservice uses the Zendesk API for its functionalities. In order to run this microservice, you need to have access to the Zendesk API.

## Getting Started

1. If you have access to the Zendesk API, you will need to have the following details:

    * Zendesk Subdomain
    * Zendesk Username
    * Zendesk Password/API token

2. After you get these details, set the following environment variables in your .env file or system environment:

    ```
    export SUBDOMAIN=your_zendesk_subdomain
    export USERNAME=your_zendesk_username
    export PASSWORD=your_zendesk_password
    ```

Replace `your_zendesk_subdomain`, `your_zendesk_username`, and `your_zendesk_password` with your actual Zendesk subdomain, username and password/API token respectively.

3. Ensure that you've properly set these environment variables before running the application.

## Running the application

After setting the environment variables, you can run your service using your preferred method. For example, if you're running the service using a `main` method or from a packaged jar, you may use:

```sh
java -jar your-service.jar
```

Replace `your-service.jar` with the actual name of your jar file.

## Endpoints

The service exposes the following endpoints for interaction:

1. `GET /api/v2/tickets`: List all the tickets.
2. `POST /api/v2/tickets/{id}/comments`: Add a comment to the ticket with the given `id`.
3. `GET /api/v2/tickets/{id}/comments`: List all comments for the ticket with the given `id`.

---

Please ensure to replace placeholders with actual values as per your project structure and guidelines.