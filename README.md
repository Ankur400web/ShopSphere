# ShopSphere

ShopSphere is a full-stack e-commerce application built as a learning and portfolio project. The project was designed to explore how a production-style e-commerce backend can be structured using Java and Spring Boot, with PostgreSQL as the database.

> **Project Status:** Archived / Development Stopped  
> This version of ShopSphere is no longer being developed. A new implementation will be started separately.

---

## Overview

ShopSphere was designed as an e-commerce platform supporting:

- User authentication and authorization
- Product management
- Categories
- Shopping carts
- Inventory management
- Order creation
- Order items
- Shipping addresses
- Database migrations
- REST APIs
- JWT-based authentication
- PostgreSQL persistence

The primary goal of this project was to learn how to design and implement a realistic backend rather than simply build a basic CRUD application.

---

## Tech Stack

### Backend

- Java 25
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Spring Security
- OAuth2 Resource Server
- JWT
- Spring Validation
- Spring Actuator
- Spring Mail
- Spring Cache
- Flyway
- Lombok

### Database

- PostgreSQL

### Build Tool

- Maven

### Development Tools

- IntelliJ IDEA
- Git
- GitHub
- Postman

---

## Architecture

The backend followed a layered architecture:

```text
Client
  │
  ▼
Controller
  │
  ▼
Service
  │
  ▼
Repository
  │
  ▼
PostgreSQL
```

Security was handled through Spring Security and JWT authentication.

```text
Client
  │
  │ JWT
  ▼
Spring Security
  │
  ▼
Authenticated User
  │
  ▼
Controller
  │
  ▼
Service
  │
  ▼
Repository
```

---

## Main Modules

### User Module

Responsible for user-related functionality and authentication.

Planned/implemented responsibilities included:

- User registration
- User authentication
- User identification through the authenticated security context
- Role-based security
- JWT authentication

---

### Product Module

Responsible for products and product-related information.

The project included product persistence and category relationships.

---

### Category Module

Used to organize products into categories.

```text
Category
   │
   └── Products
```

---

### Cart Module

The cart system was implemented with separate cart and cart-item entities.

```text
User
 │
 └── Cart
      │
      ├── Cart Item
      │     └── Product
      │
      └── Cart Item
            └── Product
```

The cart implementation included:

- Cart creation
- Adding products
- Retrieving cart contents
- Cart items
- Product quantity handling
- Inventory interaction
- Unique cart/product constraints

---

### Inventory Module

Inventory tracking was implemented separately from products.

The inventory model included:

- Available quantity
- Reserved quantity
- Optimistic locking/version information

The intended flow was:

```text
Product
   │
   ▼
Inventory
   │
   ├── Available Quantity
   ├── Reserved Quantity
   └── Version
```

---

### Address Module

A dedicated address module was implemented and tested.

An address belongs to a specific authenticated user.

```text
User
 │
 ├── Address 1
 ├── Address 2
 └── Address 3
```

The implementation included:

- Create address
- Get user's addresses
- Get individual address
- Update address
- Delete address
- Set default address
- User ownership validation

Addresses were deliberately associated with the authenticated user instead of accepting a `userId` directly from the client.

---

### Order Module

An order system was implemented with:

- Orders
- Order items
- Order status
- Order totals
- Order retrieval
- User-specific order retrieval

The project reached the stage of integrating shipping addresses into orders.

The intended design was to store a **shipping-address snapshot** inside the order so that changing a user's saved address would not modify the historical address associated with an existing order.

---

## Database Migrations

Flyway was used for database versioning.

The project contained migrations for:

```text
V1 - Initial schema
V2 - Refresh tokens
V3 - Cart tables
V4 - Order tables
V5 - Address tables
V6 - Shipping address fields for orders
```

Database structure included tables such as:

```text
users
roles
products
categories
inventory
carts
cart_items
orders
order_items
addresses
revoked_at
flyway_schema_history
```

---

## Security Design

ShopSphere used Spring Security with JWT-based authentication.

The application was designed so that resources belonging to a user were accessed through the authenticated user's identity rather than trusting a user ID supplied by the client.

For example:

```text
JWT
 │
 ▼
SecurityContext
 │
 ▼
Authenticated User
 │
 ▼
User ID
 │
 ▼
User-owned resources
```

This approach was used for resources such as:

- Carts
- Addresses
- Orders

---

## API Design

The application followed REST-style API conventions.

Examples of endpoints implemented during development:

```text
/api/addresses
/api/addresses/{addressId}
/api/addresses/{addressId}/default
```

The address module supported:

```text
POST    /api/addresses
GET     /api/addresses
GET     /api/addresses/{id}
PUT     /api/addresses/{id}
PATCH   /api/addresses/{id}/default
DELETE  /api/addresses/{id}
```

---

## Testing

API functionality was tested using Postman during development.

Address testing included:

- Creating addresses
- Retrieving addresses
- Updating addresses
- Deleting addresses
- Setting default addresses
- Validation
- Authentication
- User ownership/security checks

Security testing specifically verified that one authenticated user could not access another user's address.

---

## Project Structure

The backend followed a modular package structure similar to:

```text
src/
└── main/
    ├── java/
    │   └── com/
    │       └── e_commerce/
    │           └── ShopSphere/
    │               ├── address/
    │               │   ├── controller/
    │               │   ├── dto/
    │               │   ├── entity/
    │               │   ├── repository/
    │               │   └── service/
    │               │
    │               ├── cart/
    │               ├── category/
    │               ├── config/
    │               ├── enums/
    │               ├── http/
    │               ├── inventory/
    │               ├── order/
    │               │   ├── controller/
    │               │   ├── dto/
    │               │   ├── entity/
    │               │   ├── repository/
    │               │   └── service/
    │               │
    │               ├── product/
    │               ├── user/
    │               └── ShopSphereApplication.java
    │
    └── resources/
        ├── db/
        │   └── migration/
        └── application.yaml
```

---

## What Was Completed

The following areas were developed during this version:

- [x] Spring Boot project setup
- [x] PostgreSQL integration
- [x] Flyway database migrations
- [x] User system
- [x] JWT/security infrastructure
- [x] Product system
- [x] Category system
- [x] Inventory system
- [x] Cart system
- [x] Cart items
- [x] Order entities
- [x] Order items
- [x] Order service
- [x] Order DTOs
- [x] Order repositories
- [x] Address module
- [x] Address CRUD
- [x] Default address functionality
- [x] User-address ownership checks
- [x] API testing with Postman

---

## What Was Not Completed

The project was stopped before reaching a finished production-ready state.

Remaining work included:

- [ ] Complete shipping-address integration with checkout
- [ ] Complete end-to-end order flow
- [ ] Payment integration
- [ ] Payment verification/webhooks
- [ ] Order payment state management
- [ ] Order cancellation/refund flow
- [ ] Advanced inventory handling
- [ ] Product search
- [ ] Product filtering
- [ ] Pagination and sorting
- [ ] Product image management
- [ ] Email notifications
- [ ] Frontend completion
- [ ] Production deployment
- [ ] Automated integration testing
- [ ] CI/CD pipeline
- [ ] Production monitoring
- [ ] Production security hardening

---

## Lessons From This Version

This implementation was primarily a learning project and helped explore several backend concepts:

- Layered architecture
- REST API design
- Spring Security
- JWT authentication
- JPA relationships
- PostgreSQL
- Database migrations
- Entity/DTO separation
- Repository patterns
- Transaction management
- Inventory reservation
- Optimistic locking
- User resource ownership
- E-commerce cart architecture
- Order architecture
- Address management
- API testing

---

## Why This Version Was Archived

This implementation reached a point where continuing development became less efficient than restructuring the project from the beginning.

The next ShopSphere implementation will be treated as a fresh version with