# Shopping MCP Server

MCP (Model Context Protocol) server for managing a shopping cart system, compatible with Claude Desktop.

## 🎯 Description

This project exposes MCP tools that allow Claude Desktop to interact with a shopping cart management system via a PostgreSQL database.

### Available Tools

- **`create_cart`**: Create a new empty cart
- **`get_cart_by_id`**: Retrieve a cart by its UUID
- **`add_product_to_cart`**: Add a product to an existing cart
- **`remove_product_from_cart`**: Remove a product from a cart

## 🏗️ Architecture

- **Framework**: Spring Boot 3.5.8 + Spring AI 1.1.0
- **Language**: Kotlin
- **Database**: PostgreSQL
- **Protocol**: MCP (Model Context Protocol)
- **Mode**: STDIO for communication with Claude Desktop

## 📋 Prerequisites

- **Java 21** (JDK 21 or higher)
- **Docker** and **Docker Compose** (for PostgreSQL)
- **Maven** (included via Maven Wrapper)
- **Claude Desktop** (to test MCP tools)

## 🚀 Installation and Setup

### 1. Navigate to the project directory

```bash
cd /home/tom/projects/personnal-projects/mcp
```

### 2. Start PostgreSQL

The project uses Docker Compose to manage PostgreSQL:

```bash
docker compose up -d
```

Verify the container is running:

```bash
docker ps | grep postgres
```

You should see:
```
mcp-postgres-1   Up X minutes   0.0.0.0:5432->5432/tcp
```

### 3. Compile the application

```bash
./mvnw clean package -DskipTests
```

The compilation should display:
```
[INFO] BUILD SUCCESS
[INFO] Total time: ~5s
```

The JAR will be created in: `target/mcp-0.0.1-SNAPSHOT.jar`

### 4. Test the application (optional)

To verify the application starts correctly:

```bash
java -jar target/mcp-0.0.1-SNAPSHOT.jar
```

You should see in the logs:
```
Started McpApplicationKt in X.XXX seconds
```

Press `Ctrl+C` to stop the application.

## 🧪 Testing MCP Tools

Once Claude Desktop is configured, test the tools by asking Claude these questions:

### Create a cart
```
Can you create a new cart for me?
```

Claude will use the `create_cart` tool and return a UUID.

### Retrieve a cart
```
Can you retrieve the cart with ID [UUID]?
```

### Add a product
```
Can you add product [product-UUID] to cart [cart-UUID] with a quantity of 2?
```

### Remove a product
```
Can you remove product [product-UUID] from cart [cart-UUID]?
```

## 📁 Project Structure

```
mcp/
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── com/eni/demo/mcp/
│   │   │       ├── McpApplication.kt              # Entry point
│   │   │       ├── application/
│   │   │       │   └── service/                   # Business services
│   │   │       │       ├── CartService.kt
│   │   │       │       └── ProductService.kt
│   │   │       ├── domain/                        # Domain models
│   │   │       │   ├── cart/
│   │   │       │   │   ├── model/
│   │   │       │   │   └── spi/
│   │   │       │   └── product/
│   │   │       │       ├── model/
│   │   │       │       └── spi/
│   │   │       └── infrastructure/
│   │   │           ├── claude/
│   │   │           │   └── adapters/
│   │   │           │       └── CartMcpAdapter.kt  # MCP Adapter
│   │   │           └── jpa/                       # Persistence
│   │   │               ├── adapters/
│   │   │               ├── model/
│   │   │               └── repositories/
│   │   └── resources/
│   │       └── application.yaml                   # Spring configuration
│   └── test/
├── compose.yaml                                   # Docker Compose for PostgreSQL
├── pom.xml                                        # Maven configuration
└── README.md
```

## 🐛 Troubleshooting

### MCP server doesn't appear in Claude Desktop

1. **Check the configuration**:
   ```bash
   cat ~/.config/Claude/claude_desktop_config.json
   ```

2. **Check Claude Desktop logs**:
   ```bash
   tail -f ~/.config/Claude/logs/*.log
   ```

3. **Test the application manually**:
   ```bash
   java -jar target/mcp-0.0.1-SNAPSHOT.jar
   ```

### PostgreSQL connection error

1. **Check that PostgreSQL is running**:
   ```bash
   docker ps | grep postgres
   ```

2. **If the container is not started**:
   ```bash
   docker compose up -d
   ```

3. **Check container logs**:
   ```bash
   docker logs mcp-postgres-1
   ```

### JDK compilation error

The project includes a `.mvn/jvm.config` file to work around issues with certain JDKs (especially Microsoft JDK):

```
--add-opens=java.base/jdk.internal.crac=ALL-UNNAMED
--add-opens=java.base/jdk.crac.impl=ALL-UNNAMED
--add-opens=java.base/jdk.internal.jimage=ALL-UNNAMED
```

### Application starts then stops immediately

Verify that:
1. PostgreSQL is running
2. The `shopping_mcp` database exists
3. Credentials are correct in `application.yaml`

## 📚 Additional Documentation

- [Spring AI MCP Documentation](https://docs.spring.io/spring-ai/reference/)
- [Model Context Protocol Specification](https://modelcontextprotocol.io/)
- [Claude Desktop Documentation](https://claude.ai/desktop)

## 🤝 Contributing

This project follows a hexagonal architecture (ports & adapters):
- **Domain**: Pure business logic
- **Application**: Use cases and orchestration
- **Infrastructure**: Technical implementations (JPA, MCP, etc.)

## 📝 Important Notes

- The MCP server uses **STDIO** (Standard Input/Output) mode to communicate with Claude Desktop
- The application disables the Tomcat web server (`web-application-type: none`) as it's not needed
- Logs are minimized to avoid polluting STDIO communication with Claude
- PostgreSQL is required at startup - the application will fail without an available database

## 🎉 Ready!

Once everything is configured, you can interact with your shopping cart system directly from Claude Desktop using natural language!

Example:
```
You: "Create a new cart for me"
Claude: *uses create_cart*
        "I've created a new cart with ID: 123e4567-e89b-12d3-a456-426614174000"
```

